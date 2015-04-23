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
    
    private List<PCashDetailDTO> pcashDetailCancelList = new ArrayList<PCashDetailDTO>(); //�s�W��PC_PCASH_DTAIL
    
    private List<PCashDetailDTO> pcashDetailTempList = new ArrayList<PCashDetailDTO>(); //�s�W��PC_PCASH_DETAIL_TEMP
    

    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException {
        
        setPCashCloseDTOAndCommonAttriburte(pcashCloseDTO);
        cancelDeductionAmount = pcashCloseDTO.getPplBamount();
        
        logger.info("***** Start close pcashCancel ***** " + getPCashCloseInfoLog());
        
        //=== �d�X�q��bPC_PCASH_DETAIL�����ڸ�� =================================================

        List<PCashDetailDTO> origPCashDetailDTOs = pcashDetailDomainBean.queryPCashDetailByPplNumCancel(pcashCloseDTO.getPplNumCancel(), pointType);
        
        if(origPCashDetailDTOs.isEmpty()){ 
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DETAIL_EMPTY_CODE +
                                          PCashCloseConstant.PCASH_DETAIL_EMPTY +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== �d�X ���q��bPC_PCASH_DETAIL_TEMP�����ڸ�� (�Ĥ@���O�Ū�,�p�G���e���������ڷ|����) ================
        
        List<PCashDetailDTO> origPCashDetailTempDTOs = pcashDetailDomainBean.queryPCashDetailTempByPplNumCancel(pcashCloseDTO.getPplNumCancel(), pointType);

        //=== �P�_�O�_���i�f�^�����ɬ��� ============================================================
        
        if(origPCashDetailTempDTOs.isEmpty() && origPCashDetailDTOs.isEmpty()){
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_NO_CANCEL_RETURN_DETAIL_CODE +
                                          PCashCloseConstant.PCASH_NO_CANCEL_RETURN_DETAIL +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== ���Ұf�^����ƬO�_�X�k  ====================================================================
        
        logger.info("Close pcashCancel by pplNum [" + pplNum + "], cancelAmount[" + cancelDeductionAmount + "] " +
        		    ",[" + origPCashDetailDTOs.size() + "] pcashDetail " +
        		    "and [" + origPCashDetailTempDTOs.size() + "] pcashDetailTemp");
        
        if(origPCashDetailTempDTOs.isEmpty() && (!origPCashDetailDTOs.isEmpty())){//�Ĥ@����������
            
            createPcashDetailCancelAndTempList(null, origPCashDetailDTOs);
            
        } else if((!origPCashDetailTempDTOs.isEmpty()) && (!origPCashDetailDTOs.isEmpty())){//�D�Ĥ@���������
            
            createPcashDetailCancelAndTempList(origPCashDetailTempDTOs, origPCashDetailDTOs);
            
        } else {
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DEATIL_ILLEGAL_DATA_CODE +
                                          PCashCloseConstant.PCASH_DEATIL_ILLEGAL_DATA +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== ����e����  ======================================================================
        
        if (!(new BigDecimal(0).equals(cancelDeductionAmount))) {//���B��������
            
            throw new PCashCloseException(ActType.CANCEL_PCASH + 
                                          PCashCloseConstant.PCASH_DEATIL_NOT_ENOUGH_CODE +
                                          PCashCloseConstant.PCASH_DEATIL_NOT_ENOUGH +
                                          getPCashCloseInfoLog());
            
        }
        
        //=== ���� & ����  ======================================================================
        
        if(!pcashDetailCancelList.isEmpty()){
            insertPCashDetailAndList();
        }
        
        pcashDetailDomainBean.deletePCashDetailTemp(pcashCloseDTO.getPplNumCancel(), pointType); //�R��PC_CPASH_DETAIL_TEMP
        
        if(!pcashDetailTempList.isEmpty()){
            insertPCashDetailTemp(pcashCloseDTO.getPplNumCancel());
        }
        
        logger.info("***** Finish close pcashCancel ***** pplNum [" + pplNum + "] and memNum [" + memNum + "]");
    }

    
    private void createPcashDetailCancelAndTempList(
            List<PCashDetailDTO> origPCashDetailTempDTOs,
            List<PCashDetailDTO> origPCashDetailDTOs) {
        
        List<PCashDetailDTO> pcashDetailDTOs = null;//������������ɬ���
        
        if(origPCashDetailTempDTOs == null){//�HPC_PCASH_DEATL��Ƭ��f�^���(��������)
            pcashDetailDTOs = origPCashDetailDTOs;
        } else {//�HPC_PCASH_DEATL_TEMP��Ƭ��f�^���(�D��������)
            pcashDetailDTOs = origPCashDetailTempDTOs;
        }
        
        //=== ���ͦ��ڨ����P�ѤU����� ===========================================================================
        
        for(int i = 0; i < pcashDetailDTOs.size(); i++ ){
            
            PCashDetailDTO pcashDetailTempDTO = createPCashDetailTempDTO(pcashDetailDTOs.get(i));
            
            PCashDetailDTO pcashDetailDTO = pcashDetailDTOs.get(i);
            pcashDetailDTO.setPcsNum(PCashCloseConstant.PCASH_CANCEL_PCSNUM);//�P�h����
            pcashDetailDTO.setPplNum(pcashCloseDTO.getPplNum());// ���������q�檺 PPL_NUM
            pcashDetailDTO.setPplDate(pcashCloseDTO.getPplDate());// ���������q�檺 PPL_DATE
            
            if((cancelDeductionAmount.compareTo(pcashDetailDTO.getPdtLamount())) >= 0){//�������B  >= �������ɬ������B
                
                //�������ɧ����f�^
                pcashDetailDTO.setPdtBamount(pcashDetailDTO.getPdtLamount());
                pcashDetailDTO.setPdtLamount(new BigDecimal(0));
                cancelDeductionAmount = cancelDeductionAmount.subtract(pcashDetailDTO.getPdtBamount());
                
                pcashDetailCancelList.add(pcashDetailDTO);
                
                if (new BigDecimal(0).equals(cancelDeductionAmount)) {//���B�w��������
                    break;
                }
                
            }else{//�����p�󦹵����ɬ������B(��������)
                
                //�����f�^
                pcashDetailDTO.setPdtBamount(cancelDeductionAmount);
                pcashDetailTempDTO.setPdtLamount(pcashDetailDTO.getPdtLamount()
                        .subtract(cancelDeductionAmount));// �������ɬ����Ѿl���ڪ��B
                pcashDetailDTO.setPdtLamount(new BigDecimal(0));
                pcashDetailTempDTO.setPdtBamount(new BigDecimal(0));
                
                pcashDetailCancelList.add(pcashDetailDTO);
                pcashDetailTempList.add(pcashDetailTempDTO);
                
                cancelDeductionAmount = new BigDecimal(0);
                
                break;
                
            } 
            
        }
        
        //=== �N�������쪺��Ʃ�ipcashDetailTempList ==============================================================
        
        if(pcashDetailCancelList.size() < pcashDetailDTOs.size()){//��������
            
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
        pcashDetailTempDTO.setPdtDate(pcashDetailDTO.getPdtDate());//������ɬ�����PDT_DATE
        pcashDetailTempDTO.setPplDate(pcashDetailDTO.getPplDate());//������ɬ�����PPL_DATE
        pcashDetailTempDTO.setPesNum(pcashDetailDTO.getPesNum());
        pcashDetailTempDTO.setPplNum(pcashCloseDTO.getPplNumCancel());
        pcashDetailTempDTO.setPcsAccountType(pcashDetailDTO.getPcsAccountType());
        pcashDetailTempDTO.setPcsWebNum(pcashDetailDTO.getPcsWebNum());
        pcashDetailTempDTO.setPesValidDate(pcashDetailDTO.getPesValidDate());
        
        return pcashDetailTempDTO;
        
    }

    private void insertPCashDetailAndList() throws PCashCloseException {
        
        //=== �s�WPC_PCASH_DETAIL ===========================================================================
        
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
        
        //=== �s�WPC_CPASH_DETAIL_TEMP =================================================
        
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
        pcashListDTO.setPcsNum(PCashCloseConstant.PCASH_CANCEL_PCSNUM);//�P�h���ʬy����
        pcashListDTO.setOrigPcsNum(pcashDetailDTO.getOrigPcsNum());
        pcashListDTO.setPlsBlance(pcashDetailDTO.getPdtBamount());
        pcashListDTO.setPesNum(pcashDetailDTO.getPesNum());
        pcashListDTO.setPcsAccountType(pcashDetailDTO.getPcsAccountType());
        pcashListDTO.setPcsWebNum(pcashDetailDTO.getPcsWebNum());
        pcashListDTO.setPesValidDate(pcashDetailDTO.getPesValidDate());
        
        return pcashListDTO;
        
    }

}
