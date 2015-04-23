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
        deductionAmount = pcashCloseDTO.getPplLamount(); //�P�_���ڶi��
        pcmAccountType = pcashCloseDTO.getPcmAccountType();
        
        logger.info("***** Start close pcashDeduction ***** " + getPCashCloseInfoLog());
        
        //=== ���榩�� =============================================
        
        deductPCash();
        
        //=== ���ڧ��ˬd ============================================
        
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
     * ���榩�ڵ���
     * author henry
     * @throws PCashCloseException (������I�s����i��ROLLBACK)
     */
    private void deductPCash() throws PCashCloseException {
        
        //���o���ڶ��ǱƧǫ᪺�|�����ʾl�B(PC_PCASH_LIST)
        List<PCashListDTO> pcashListDTOs = queryPCashListByMemNumAndPcmAccountType();
        
        //pcashListDTOs���ťN�����A�L�i����   
        if(pcashListDTOs == null || pcashListDTOs.isEmpty()){
            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_LIST_EMPTY_CODE +
                                          PCashCloseConstant.PCASH_LIST_EMPTY +
                                          getPCashCloseInfoLog());
            
        }
        
        int pcashListSize = pcashListDTOs.size();
        
        logger.info("Deduct pcash get [" + pcashListSize + "] pcashList " +
        		    "and pcashListDTOs is [" + pcashListDTOs + "]");
        
        //�|���l�B�ˬd
        BigDecimal memPCashBalance = pcashListDomainBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
        if(!((memPCashBalance.compareTo(deductionAmount)) >= 0)){
            
            throw new PCashCloseException(ActType.DEDUCT_PCASH + 
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH_CODE +
                                          PCashCloseConstant.PCASH_DEDUCTION_NOTENOUGH +
                                          getPCashCloseInfoLog());
            
        }
        
        for(int i = 0; i < pcashListDTOs.size(); i++ ){
            
            currentPCashListDTO = pcashListDTOs.get(i); //���o��e�i�榩�ڪ����ʾl�B
            currentPCashListBalance = currentPCashListDTO.getPlsBlance(); //����{�b���ʾl�B
            
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

            if(deductionAmount.compareTo(currentPCashListDTO.getPlsBlance()) >= 0){//���� >= ���ʾl�B
                
                pcashDetailDTO.setPdtLamount(currentPCashListDTO.getPlsBlance());//�]�w���ɬ������ڪ��B(���ʾl�B)
                deductionAmount = deductionAmount.subtract(currentPCashListDTO.getPlsBlance());
                currentPCashListDTO.setPlsBlance(new BigDecimal(0));
                
                //��s���ʾl�B  
                updatePCashList();  
                
                if (new BigDecimal(0).equals(deductionAmount)){
                    break;
                }
                
            }else{//���� < ���ʾl�B
                
                pcashDetailDTO.setPdtLamount(deductionAmount);//�]�w���ɬ������ڪ��B(����)
                currentPCashListDTO.setPlsBlance(currentPCashListDTO.getPlsBlance().subtract(deductionAmount));//�|�����ʾl�B = ��e�l�B - ���ڪ��B
                deductionAmount = new BigDecimal(0);//����
                
                //��s���ʾl�B
                updatePCashList();
                
                break;
             
            }               
        }
        
    }

    private List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType() throws PCashCloseException {

        //���x�b�����O�Ƨ�
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
     * ��sPCash���ʾl�B
     * @param pcashListDTO PCash���ʾl�B��ʸ��DTO
     * @throws PCashCloseException �����޿���~
     * @throws PCashCloseDAOException ��Ʀs�����~
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
        
        //���ʾl�B������ �N��@�w����
        if(updateCount == 0){ 
            
            logger.error("Cannot update PC_" + pointType + "_LIST by pplNum [" + pplNum + "]" +
            		     "and pcashListDTO [" + currentPCashListDTO + "], " + 
            		     "try deduct pcash again - " +
            		     "orignal deduction amount is [" + pcashCloseDTO.getPplLamount() + "]," +
            		     "current deduction amount is [" + pcashDetailDTO.getPdtLamount()+ "]," +
            		     "pcashList balance is [" + currentPCashListBalance + "]"
            		     );
            
            checkPCashDeduction();//�T�{�O�_�i���s���榩�ڵ���
            
        }else {//�l�B���ʦ��\ �s�W���ɰO��
            
            insertPCashDetail(ActType.DEDUCT_PCASH, pcashDetailDTO);
            
        }
        
    }
    
    
    private void checkPCashDeduction() throws PCashCloseException {
        
        BigDecimal memPCashBalance = pcashListDomainBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
        if((memPCashBalance.compareTo(pcashCloseDTO.getPplLamount())) >= 0){

            pcashDetailDTO = createPCashDetailDTO();//���]�������ɬ�����DTO
            deductionAmount = pcashCloseDTO.getPplLamount(); //���]���ڪ��B
            
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
