package com.payeasy.pcashclose.service.util;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;


import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;

public class PCashCloseValidator {

    private static final Logger logger = Logger.getLogger(PCashCloseValidator.class);
    
    public void checkPCashCloseDTOData(PCashCloseDTO pcashCloseDTO) throws PCashCloseException{
        
        ActType actType = pcashCloseDTO.getActType();
        
        try {
            
            Assert.notNull(pcashCloseDTO.getActType(), "actType is required");
            Assert.notNull(pcashCloseDTO.getPointType(), "pointType is required");
            Assert.notNull(pcashCloseDTO.getMemNum(), "memNum is required");
            Assert.notNull(pcashCloseDTO.getPplNum(), "pplNum is required");
            Assert.notNull(pcashCloseDTO.getPplDate(), "pplDate is required");
            Assert.notNull(pcashCloseDTO.getPplBamount(), "pplBamount is required");
            Assert.notNull(pcashCloseDTO.getPplLamount(), "pplLamount is required");  
            
            if(ActType.DEDUCT_PCASH.equals(actType)){ 
                
                checkPCashCloseDTOForDeduction(pcashCloseDTO); 
                
            }else if(ActType.CANCEL_PCASH.equals(actType)){
                
                checkPCashCloseDTOForCancel(pcashCloseDTO);
                
            }else if((ActType.SAVE_PCASH.equals(actType)) || (ActType.BUY_PCASH.equals(actType))){
                
                checkPCashCloseDTOForSaving(pcashCloseDTO);
                
            }
            
        } catch (PCashCloseException ex) {
            
            logger.error(checkPCashCloseDTOCommonErrorLog(pcashCloseDTO), ex);

            throw ex;
            
        }catch (Exception e) {
            
            logger.error(checkPCashCloseDTOUnExceptedErrorLog(pcashCloseDTO), e);

            throw new PCashCloseException(actType + checkPCashCloseDTOUnExceptedErrorLog(pcashCloseDTO), e);
            
        }

    }

    private void checkPCashCloseDTOForCancel(PCashCloseDTO pcashCloseDTO) throws Exception {
        
        ActType actType = pcashCloseDTO.getActType();
        
        Assert.notNull(pcashCloseDTO.getPplNumCancel(), "pplNumCancel is required");
        
        if(!(pcashCloseDTO.getPplBamount().longValue() > 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID +
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO)+ ", pplBamount(加項金額)必須大於0");
            
        }
        
        if(!(pcashCloseDTO.getPplLamount().longValue() == 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID + 
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO) + ", pplLamount(扣項金額)必須為0");
            
        }
        
    }

    private void checkPCashCloseDTOForSaving(PCashCloseDTO pcashCloseDTO) throws Exception {
        
        ActType actType = pcashCloseDTO.getActType();
        
        Assert.notNull(pcashCloseDTO.getPcsNum(), "pcsNum is required");
        Assert.notNull(pcashCloseDTO.getPesNum(), "pesNum is required");
        Assert.hasText(pcashCloseDTO.getPcsAccountType(), "pcsAccountType must not empty");
        
        if(!(pcashCloseDTO.getPplBamount().longValue() > 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID +
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO)+ ", pplBamount(加項金額)必須大於0");
            
        }
        
        if(!(pcashCloseDTO.getPplLamount().longValue() == 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID + 
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO) + ", pplLamount(扣項金額)必須為0");
            
        }
        
    }

    private void checkPCashCloseDTOForDeduction(PCashCloseDTO pcashCloseDTO) throws Exception {

        ActType actType = pcashCloseDTO.getActType();
        
        Assert.hasText(pcashCloseDTO.getPcmAccountType(), "pcmAccountType must not empty");
        Assert.notNull(pcashCloseDTO.getWebNum(), "webNum is required");
        
        if(!(pcashCloseDTO.getPplLamount().longValue() > 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID + 
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO) + ", pplLamount(扣款金額)必須大於0");
            
        }
        
        if(!(pcashCloseDTO.getPplBamount().longValue() == 0L)){
            
            throw new PCashCloseException(actType + 
                                          PCashCloseConstant.PCASH_DATA_INVALID_CODE +
                                          PCashCloseConstant.PCASH_DATA_INVALID + 
                                          checkPCashCloseDTOCommonErrorLog(pcashCloseDTO) + ", pplBamount必須為0");
            
        }
        
    }
    
    private String checkPCashCloseDTOCommonErrorLog(PCashCloseDTO pcashCloseDTO){
        
        return " Check pcashCloseDTO [" + pcashCloseDTO + "] fail!! ";
        
    }
    
    private String checkPCashCloseDTOUnExceptedErrorLog(PCashCloseDTO pcashCloseDTO){
        
        return checkPCashCloseDTOCommonErrorLog(pcashCloseDTO) + " 檢查PCashCloseDTO發生未預期錯誤 !!";
        
    }
}
