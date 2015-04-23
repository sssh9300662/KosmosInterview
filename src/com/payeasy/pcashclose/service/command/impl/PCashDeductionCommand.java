package com.payeasy.pcashclose.service.command.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;

public class PCashDeductionCommand extends AbstractPCashCloseCommand {

    //=== Common attribute ===========================================

    private PCashListDTO currentPCashListDTO;
    
    private BigDecimal deductionAmount;
    
    private BigDecimal currentPCashListBalance;
    
    private String pcmAccountType;

    //=== Execute methods ============================================
    
    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException {

        setPCashCloseDTOAndCommonAttriburte(pcashCloseDTO);
        pcashDetailDTO = createPCashDetailDTO();
        deductionAmount = pcashCloseDTO.getPplLamount(); //判斷扣款進度
        pcmAccountType = pcashCloseDTO.getPcmAccountType();
        
        logger.info("***** Start close pcashDeduction ***** " + getPCashCloseInfoLog());
        
        //=== 執行扣款 =============================================
        
        deductPCash();
        
        //=== 扣款完檢查 ============================================
        
        if (!(new BigDecimal(0).equals(deductionAmount))){
            
            logger.error("Finish pcash deduction by pplNum [" + pplNum + "] " +
            		     "but deductionAmount remain [" + deductionAmount + "]");
            
            logger.info("***** Fail close pcashDeduction ***** " + getPCashCloseInfoLog());
            
            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH_CODE +
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH +
                                          getPCashCloseInfoLog());
            
        }
        
        logger.info("***** Finish close pcashDeduction ***** " + getPCashCloseInfoLog());

    }

    /**
     * 執行扣款結算
     * author henry
     * @throws PCashCloseException (丟錯給呼叫元件進行ROLLBACK)
     */
    private void deductPCash() throws PCashCloseException {
        
        //取得扣款順序排序後的會員活動餘額(PC_PCASH_LIST)
        List<PCashListDTO> pcashListDTOs = queryPCashListByMemNumAndPcmAccountType();
        
        //pcashListDTOs為空代表有錯，無可扣款   
        if(pcashListDTOs == null || pcashListDTOs.isEmpty()){
            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_LIST_EMPTY_CODE +
                                          PCashCloseConstant.PCASH_LIST_EMPTY +
                                          getPCashCloseInfoLog());
            
        }
        
        int pcashListSize = pcashListDTOs.size();
        
        logger.info("Deduct pcash get [" + pcashListSize + "] pcashList " +
        		    "and pcashListDTOs is [" + pcashListDTOs + "]");
        
        //會員餘額檢查
        BigDecimal memPCashBalance = pcashListDomainBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
        if(!((memPCashBalance.compareTo(deductionAmount)) >= 0)){
            
            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH_CODE +
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH +
                                          getPCashCloseInfoLog());
            
        }
        
        for(int i = 0; i < pcashListDTOs.size(); i++ ){
            
            currentPCashListDTO = pcashListDTOs.get(i); //取得當前進行扣款的活動餘額
            currentPCashListBalance = currentPCashListDTO.getPlsBlance(); //抓取現在活動餘額
            
            /*
             * pplDate,pplNum, pdt
             */
            pcashDetailDTO.setPesNum(currentPCashListDTO.getPesNum());
            pcashDetailDTO.setPcsNum(currentPCashListDTO.getPcsNum());
            pcashDetailDTO.setOrigPcsNum(currentPCashListDTO.getOrigPcsNum());
            pcashDetailDTO.setEmpNum(currentPCashListDTO.getEmpNum());
            pcashDetailDTO.setPcsAccountType(currentPCashListDTO.getPcsAccountType());
            pcashDetailDTO.setPcsWebNum(currentPCashListDTO.getPcsWebNum());
            pcashDetailDTO.setPesValidDate(currentPCashListDTO.getPesValidDate());

            if(deductionAmount.compareTo(currentPCashListDTO.getPlsBlance()) >= 0){//扣款 >= 活動餘額
                
                pcashDetailDTO.setPdtLamount(currentPCashListDTO.getPlsBlance());//設定關檔紀錄扣款金額(活動餘額)
                deductionAmount = deductionAmount.subtract(currentPCashListDTO.getPlsBlance());
                currentPCashListDTO.setPlsBlance(new BigDecimal(0));
                
                //更新活動餘額  
                updatePCashList();  
                
                if (new BigDecimal(0).equals(deductionAmount)){
                    break;
                }
                
            }else{//扣款 < 活動餘額
                
                pcashDetailDTO.setPdtLamount(deductionAmount);//設定關檔紀錄扣款金額(全扣)
                currentPCashListDTO.setPlsBlance(currentPCashListDTO.getPlsBlance().subtract(deductionAmount));//會員活動餘額 = 當前餘額 - 扣款金額
                deductionAmount = new BigDecimal(0);//扣完
                
                //更新活動餘額
                updatePCashList();
                
                break;
             
            }               
        }
        
    }

    private List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType() throws PCashCloseException {

        //平台帳戶類別排序
        String[] pcmAccountTypeArray = null;
        pcmAccountTypeArray = pcmAccountType.split(",");
        
        if(pcmAccountTypeArray.length == 0){
            throw new PCashCloseException(PCashCloseConstant.PCASH_ACCOUNT_TYPE_EMPTY_CODE + 
                                          PCashCloseConstant.PCASH_ACCOUNT_TYPE_EMPTY +
                                          "pcmAccountType [" + pcmAccountType + "] and pointType [" + pointType + "]"); 
        }
        
        Arrays.sort(pcmAccountTypeArray);
        
        return pcashListDomainBean.queryPCashListByMemNumAndPcmAccountType(memNum, pcashCloseDTO.getWebNum(), pcmAccountTypeArray, pointType);
        
    }
    /*
    private void updatePCashList(PCashListDTO pcashListDTO) throws PCashCloseException, PCashCloseDAOException {
        
        logger.info("Update PC_PCASH_LIST by pcashListDTO [" + pcashListDTO + "]");
        
        int updateCount = 0;
        
        if(new BigDecimal(0).equals(pcashListDTO.getPlsBlance())){
            updateCount = pcashListDomainBean.deletePCashList(pcashListDTO.getPlsNum());
        } else {
            updateCount = pcashListDomainBean.updatePCashList(pcashListDTO.getPlsNum(), pcashDetailDTO.getPdtLamount());
        }
        
        if(updateCount == 0){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_UPDATE_ERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_UPDATE_ERROR +
                                          "pcashListDTO [" + pcashListDTO + "]");
            
        }
    }*/

 
    /**
     * 更新PCash活動餘額
     * @param pcashListDTO PCash活動餘額更動資料DTO
     * @throws PCashCloseException 結算邏輯錯誤
     * @throws PCashCloseDAOException 資料存取錯誤
     */
    private void updatePCashList() throws PCashCloseException {

        logger.info("Update PC_" + pointType + "_LIST by pplNum [" + pplNum + "], " +
        		    "currentPCashListDTO [" + currentPCashListDTO + "], " +
                    "deductionAmount [" + pcashDetailDTO.getPdtLamount()+ "], " +
                    "currentPCashListBalance [" + currentPCashListBalance + "]");
        
        int updateCount = pcashListDomainBean.updatePCashList(currentPCashListDTO.getPlsNum(),
                                                              pcashDetailDTO.getPdtLamount(), pointType);

            
        logger.info("Update [" + updateCount + "] PC_" + pointType + "_LIST by pplNum [" + pplNum + "] " +
        		    "and pcashListDTO [" + currentPCashListDTO + "]");
        
        //TEST LOG
        logger.info("Update PC_" + pointType + "_LIST by pplNum [" + pplNum + "] " +
        		    "which plsNum is [" + currentPCashListDTO.getPlsNum() + "] " +
        		    "remain [" + pcashListDomainBean.queryPCashListBalanceByPlsNum(currentPCashListDTO.getPlsNum(), pointType) + "] ");
        
        //活動餘額未異動 代表一定有錯
        if(updateCount == 0){ 
            
            logger.error("Cannot update PC_" + pointType + "_LIST by pplNum [" + pplNum + "]" +
            		     "and pcashListDTO [" + currentPCashListDTO + "], " + 
            		     "try deduct pcash again - " +
            		     "orignal deduction amount is [" + pcashCloseDTO.getPplLamount() + "]," +
            		     "current deduction amount is [" + pcashDetailDTO.getPdtLamount()+ "]," +
            		     "pcashList balance is [" + currentPCashListBalance + "]"
            		     );
            
            checkPCashDeduction();//確認是否可重新執行扣款結算
            
        }else {//餘額異動成功 新增關檔記錄
            
            insertPCashDetail(ActType.DEDUCT_PCASH, pcashDetailDTO);
            
        }
        
    }
    
    
    private void checkPCashDeduction() throws PCashCloseException {
        
        BigDecimal memPCashBalance = pcashListDomainBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
        if((memPCashBalance.compareTo(pcashCloseDTO.getPplLamount())) >= 0){

            pcashDetailDTO = createPCashDetailDTO();//重設異動關檔紀錄的DTO
            deductionAmount = pcashCloseDTO.getPplLamount(); //重設扣款金額
            
            deductPCash();

        } else {

            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH_CODE + 
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH +
                                          getPCashCloseInfoLog() +
                                          " - deductionAmount is [" + pcashDetailDTO.getPdtLamount() + "]," +
                                          " memPCashBalance is [" + memPCashBalance + "]," +
                                          " and pcashListDTO [" + currentPCashListDTO + "]");

        }

    }


}
