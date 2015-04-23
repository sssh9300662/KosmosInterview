package com.payeasy.pcashclose.service.command.impl;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;


public class PCashSavingCommand extends AbstractPCashCloseCommand {
    
    private PCashListDTO pcashListDTO;

    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException {
        
        setPCashCloseDTOAndCommonAttriburte(pcashCloseDTO);
        pcashDetailDTO = createPCashDetailDTO();
        pcashListDTO = createPCashListDTO(pcashCloseDTO);
        
        logger.info("***** Start close pcashSaving *****" + getPCashCloseInfoLog());

        //更新結算需要的DTO
        if((ActType.BUY_PCASH).equals(pcashCloseDTO.getActType())){
            
            pcashListDTO.setPcsNum(PCashCloseConstant.PCASH_BUY_PCSNUM);
            pcashListDTO.setOrigPcsNum(PCashCloseConstant.PCASH_BUY_PCSNUM);
            
            pcashDetailDTO.setPcsNum(PCashCloseConstant.PCASH_BUY_PCSNUM);
            pcashDetailDTO.setOrigPcsNum(PCashCloseConstant.PCASH_BUY_PCSNUM);
            
        }
        
        logger.info("Insert PC_" + pointType + "_LIST by pplNum [" + pplNum + "], memNum [" + memNum + "] " +
        		    "and pcashListDTO [" + pcashListDTO + "]");
            
        if(pcashListDomainBean.insertPCashList(pcashListDTO, pointType) == 0 ){
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_UPDATE_ERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_UPDATE_ERROR +
                                          getPCashCloseInfoLog() +
                                          "pcashListDTO [" + pcashListDTO + "]");
        }
            
        insertPCashDetail(ActType.SAVE_PCASH, pcashDetailDTO);
        
        logger.info("***** Finish close pcashSaving *****" + getPCashCloseInfoLog());
      
    }
    

    private PCashListDTO createPCashListDTO(PCashCloseDTO pcashCloseDTO) {
        
        PCashListDTO pcashListDTO = new PCashListDTO();
        
        pcashListDTO.setEmpNum(pcashCloseDTO.getEmpNum());
        pcashListDTO.setMemNum(pcashCloseDTO.getMemNum());
        pcashListDTO.setPesNum(pcashCloseDTO.getPesNum());
        pcashListDTO.setPcsNum(pcashCloseDTO.getPcsNum());
        pcashListDTO.setOrigPcsNum(pcashCloseDTO.getPcsNum());
        pcashListDTO.setPlsBlance(pcashCloseDTO.getPplBamount());
        pcashListDTO.setPcsWebNum(pcashCloseDTO.getPcsWebNum());
        pcashListDTO.setPcsAccountType(pcashCloseDTO.getPcsAccountType());
        pcashListDTO.setPesValidDate(pcashCloseDTO.getPesValidDate());
        
        return pcashListDTO;
        
    }


}
