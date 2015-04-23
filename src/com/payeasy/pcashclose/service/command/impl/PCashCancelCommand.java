package com.payeasy.pcashclose.service.command.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;


public class PCashCancelCommand extends AbstractPCashCloseCommand {

    private BigDecimal cancelDeductionAmount;
    
    private List<PCashDetailDTO> pcashDetailCancelList = new ArrayList<PCashDetailDTO>(); //新增至PC_PCASH_DTAIL
    
    private List<PCashDetailDTO> pcashDetailTempList = new ArrayList<PCashDetailDTO>(); //新增至PC_PCASH_DETAIL_TEMP
    

    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException {
        
        setPCashCloseDTOAndCommonAttriburte(pcashCloseDTO);
        cancelDeductionAmount = pcashCloseDTO.getPplBamount();
        
        logger.info("***** Start close pcashCancel ***** " + getPCashCloseInfoLog());
        
        //=== 查出訂單在PC_PCASH_DETAIL的扣款資料 =================================================

        List<PCashDetailDTO> origPCashDetailDTOs = pcashDetailDomainBean.queryPCashDetailByPplNumCancel(pcashCloseDTO.getPplNumCancel(), pointType);
        
        if(origPCashDetailDTOs.isEmpty()){ 
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DETAIL_EMPTY_CODE +
                                          PCashCloseConstant.PCASH_DETAIL_EMPTY +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== 查出 此訂單在PC_PCASH_DETAIL_TEMP的扣款資料 (第一次是空的,如果先前有取消扣款會有值) ================
        
        List<PCashDetailDTO> origPCashDetailTempDTOs = pcashDetailDomainBean.queryPCashDetailTempByPplNumCancel(pcashCloseDTO.getPplNumCancel(), pointType);

        //=== 判斷是否有可逆回的關檔紀錄 ============================================================
        
        if(origPCashDetailTempDTOs.isEmpty() && origPCashDetailDTOs.isEmpty()){
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_NO_CANCEL_RETURN_DETAIL_CODE +
                                          PCashCloseConstant.PCASH_NO_CANCEL_RETURN_DETAIL +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== 驗證逆回的資料是否合法  ====================================================================
        
        logger.info("Close pcashCancel by pplNum [" + pplNum + "], cancelAmount[" + cancelDeductionAmount + "] " +
        		    ",[" + origPCashDetailDTOs.size() + "] pcashDetail " +
        		    "and [" + origPCashDetailTempDTOs.size() + "] pcashDetailTemp");
        
        if(origPCashDetailTempDTOs.isEmpty() && (!origPCashDetailDTOs.isEmpty())){//第一次取消扣款
            
            createPcashDetailCancelAndTempList(null, origPCashDetailDTOs);
            
        } else if((!origPCashDetailTempDTOs.isEmpty()) && (!origPCashDetailDTOs.isEmpty())){//非第一次執行取消
            
            createPcashDetailCancelAndTempList(origPCashDetailTempDTOs, origPCashDetailDTOs);
            
        } else {
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DEATIL_ILLEGAL_DATA_CODE +
                                          PCashCloseConstant.PCASH_DEATIL_ILLEGAL_DATA +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== 結算前驗證  ======================================================================
        
        if (!(new BigDecimal(0).equals(cancelDeductionAmount))) {//金額未取消完
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DEATIL_NOT_ENOUGH_CODE +
                                          PCashCloseConstant.PCASH_DEATIL_NOT_ENOUGH +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== 關檔 & 結算  ======================================================================
        
        if(!pcashDetailCancelList.isEmpty()){
            insertPCashDetailAndList();
        }
        
        pcashDetailDomainBean.deletePCashDetailTemp(pcashCloseDTO.getPplNumCancel(), pointType); //刪除PC_CPASH_DETAIL_TEMP
        
        if(!pcashDetailTempList.isEmpty()){
            insertPCashDetailTemp(pcashCloseDTO.getPplNumCancel());
        }
        
        logger.info("***** Finish close pcashCancel ***** pplNum [" + pplNum + "] and memNum [" + memNum + "]");
    }

    
    private void createPcashDetailCancelAndTempList(
            List<PCashDetailDTO> origPCashDetailTempDTOs,
            List<PCashDetailDTO> origPCashDetailDTOs) {
        
        List<PCashDetailDTO> pcashDetailDTOs = null;//執行取消的關檔紀錄
        
        if(origPCashDetailTempDTOs == null){//以PC_PCASH_DEATL資料為逆回資料(首次取消)
            pcashDetailDTOs = origPCashDetailDTOs;
        } else {//以PC_PCASH_DEATL_TEMP資料為逆回資料(非首次取消)
            pcashDetailDTOs = origPCashDetailTempDTOs;
        }
        
        //=== 產生扣款取消與剩下的資料 ===========================================================================
        
        for(int i = 0; i < pcashDetailDTOs.size(); i++ ){
            
            PCashDetailDTO pcashDetailTempDTO = createPCashDetailTempDTO(pcashDetailDTOs.get(i));
            
            PCashDetailDTO pcashDetailDTO = pcashDetailDTOs.get(i);
            pcashDetailDTO.setPcsNum(PCashCloseConstant.PCASH_CANCEL_PCSNUM);//銷退活動
            pcashDetailDTO.setPplNum(pcashCloseDTO.getPplNum());// 換成取消訂單的 PPL_NUM
            pcashDetailDTO.setPplDate(pcashCloseDTO.getPplDate());// 換成取消訂單的 PPL_DATE
            
            if((cancelDeductionAmount.compareTo(pcashDetailDTO.getPdtLamount())) >= 0){//取消金額  >= 此筆關檔紀錄金額
                
                //此筆關檔完全逆回
                pcashDetailDTO.setPdtBamount(pcashDetailDTO.getPdtLamount());
                pcashDetailDTO.setPdtLamount(new BigDecimal(0));
                cancelDeductionAmount = cancelDeductionAmount.subtract(pcashDetailDTO.getPdtBamount());
                
                pcashDetailCancelList.add(pcashDetailDTO);
                
                if (new BigDecimal(0).equals(cancelDeductionAmount)) {//金額已全部取消
                    break;
                }
                
            }else{//取消小於此筆關檔紀錄金額(部份取消)
                
                //部分逆回
                pcashDetailDTO.setPdtBamount(cancelDeductionAmount);
                pcashDetailTempDTO.setPdtLamount(pcashDetailDTO.getPdtLamount()
                        .subtract(cancelDeductionAmount));// 此筆關檔紀錄剩餘扣款金額
                pcashDetailDTO.setPdtLamount(new BigDecimal(0));
                pcashDetailTempDTO.setPdtBamount(new BigDecimal(0));
                
                pcashDetailCancelList.add(pcashDetailDTO);
                pcashDetailTempList.add(pcashDetailTempDTO);
                
                cancelDeductionAmount = new BigDecimal(0);
                
                break;
                
            } 
            
        }
        
        //=== 將未取消到的資料放進pcashDetailTempList ==============================================================
        
        if(pcashDetailCancelList.size() < pcashDetailDTOs.size()){//部分取消
            
            int noCancelPcashDetailDTOIndex = pcashDetailCancelList.size();
            
            for(int i = noCancelPcashDetailDTOIndex; i < pcashDetailDTOs.size(); i++){
                
                PCashDetailDTO pcashDetailTempDTO = createPCashDetailTempDTO(pcashDetailDTOs.get(i));
                
                pcashDetailTempList.add(pcashDetailTempDTO);
                
            }
        }
    }
    
    private PCashDetailDTO createPCashDetailTempDTO(PCashDetailDTO pcashDetailDTO) {
        
        PCashDetailDTO pcashDetailTempDTO = new PCashDetailDTO();
        
        pcashDetailTempDTO.setPdtNum(pcashDetailDTO.getPdtNum());
        pcashDetailTempDTO.setEmpNum(pcashDetailDTO.getEmpNum());
        pcashDetailTempDTO.setMemNum(pcashDetailDTO.getMemNum());
        pcashDetailTempDTO.setPdtBamount(pcashDetailDTO.getPdtBamount());
        pcashDetailTempDTO.setPdtLamount(pcashDetailDTO.getPdtLamount());
        pcashDetailTempDTO.setOrigPcsNum(pcashDetailDTO.getOrigPcsNum());
        pcashDetailTempDTO.setPcsNum(PCashCloseConstant.PCASH_CANCEL_PCSNUM);
        pcashDetailTempDTO.setPdtDate(pcashDetailDTO.getPdtDate());//原先關檔紀錄的PDT_DATE
        pcashDetailTempDTO.setPplDate(pcashDetailDTO.getPplDate());//原先關檔紀錄的PPL_DATE
        pcashDetailTempDTO.setPesNum(pcashDetailDTO.getPesNum());
        pcashDetailTempDTO.setPplNum(pcashCloseDTO.getPplNumCancel());
        pcashDetailTempDTO.setPcsAccountType(pcashDetailDTO.getPcsAccountType());
        pcashDetailTempDTO.setPcsWebNum(pcashDetailDTO.getPcsWebNum());
        pcashDetailTempDTO.setPesValidDate(pcashDetailDTO.getPesValidDate());
        
        return pcashDetailTempDTO;
        
    }

    private void insertPCashDetailAndList() throws PCashCloseException {
        
        //=== 新增PC_PCASH_DETAIL ===========================================================================
        
        for(int i = 0; i < pcashDetailCancelList.size(); i++){
            
            insertPCashDetail(ActType.CANCEL_PCASH, pcashDetailCancelList.get(i));
            
            PCashListDTO pcashListDTO = generatePCashListDTO(pcashDetailCancelList.get(i));
            
            logger.info("Insert PC_PCASH_LIST by pcashListDTO [" + pcashListDTO + "]");
            
            if(pcashListDomainBean.insertPCashList(pcashListDTO, pointType) == 0){
                throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_UPDATE_ERROR_CODE + 
                                              PCashCloseConstant.PCASH_LIST_UPDATE_ERROR +
                                              "pcashListDTO [" + pcashListDTO + "]");
            }
            
        }
        
    }

    private void insertPCashDetailTemp(Long pplNumCancel) throws PCashCloseException {
        
        //=== 新增PC_CPASH_DETAIL_TEMP =================================================
        
        for(int i = 0; i < pcashDetailTempList.size(); i++){
            
            logger.info("Insert PC_PCASH_DETAIL_TEMP by pcashDetailTempDTO [" + pcashDetailTempList.get(i) + "]");
            
            if(pcashDetailDomainBean.insertPCashDetailTemp(pcashDetailTempList.get(i), pointType) == 0){
                throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_TEMP_INSERT_ERROR_CODE + 
                                              PCashCloseConstant.PCASH_DETAIL_TEMP_INSERT_ERROR +
                                              "pplNumCancel is [" + pplNumCancel + "] and pplNum is [" + pplNum + "]");
            }
            
        }
        
    }

    private PCashListDTO generatePCashListDTO(PCashDetailDTO pcashDetailDTO) {
        
        PCashListDTO pcashListDTO = new PCashListDTO();
        
        pcashListDTO.setEmpNum(pcashDetailDTO.getEmpNum());
        pcashListDTO.setMemNum(pcashDetailDTO.getMemNum());
        pcashListDTO.setPcsNum(PCashCloseConstant.PCASH_CANCEL_PCSNUM);//銷退活動流水號
        pcashListDTO.setOrigPcsNum(pcashDetailDTO.getOrigPcsNum());
        pcashListDTO.setPlsBlance(pcashDetailDTO.getPdtBamount());
        pcashListDTO.setPesNum(pcashDetailDTO.getPesNum());
        pcashListDTO.setPcsAccountType(pcashDetailDTO.getPcsAccountType());
        pcashListDTO.setPcsWebNum(pcashDetailDTO.getPcsWebNum());
        pcashListDTO.setPesValidDate(pcashDetailDTO.getPesValidDate());
        
        return pcashListDTO;
        
    }

}
