package com.payeasy.pcashclose.service.command.impl;

import org.apache.log4j.Logger;

import com.payeasy.pcashclose.service.bean.domain.PCashDetailDomainBean;
import com.payeasy.pcashclose.service.bean.domain.PCashListDomainBean;
import com.payeasy.pcashclose.service.command.PCashCloseCommand;
import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;
import com.payeasy.pcashclose.service.util.PointType;

public abstract class AbstractPCashCloseCommand implements PCashCloseCommand {

    protected static final Logger logger = Logger.getLogger(AbstractPCashCloseCommand.class);
    
    //=== Bean ====================================================

    protected PCashDetailDomainBean pcashDetailDomainBean;
    
    protected PCashListDomainBean pcashListDomainBean;
    
    //=== Attribute ===============================================

    protected Long memNum;
    
    protected Long pplNum;
    
    protected PCashCloseDTO pcashCloseDTO;
    
    protected PCashDetailDTO pcashDetailDTO;
    
    protected PointType pointType;

    
    protected void setPCashCloseDTOAndCommonAttriburte(PCashCloseDTO pcashCloseDTO){
        
        this.pcashCloseDTO = pcashCloseDTO;
        memNum = pcashCloseDTO.getMemNum();
        pplNum = pcashCloseDTO.getPplNum();
        pointType = pcashCloseDTO.getPointType();
        
    }
    
    protected PCashDetailDTO createPCashDetailDTO(){
        
        PCashDetailDTO pcashDetailDTO = new PCashDetailDTO();
        
        pcashDetailDTO.setEmpNum(pcashCloseDTO.getEmpNum());
        pcashDetailDTO.setMemNum(pcashCloseDTO.getMemNum());
        pcashDetailDTO.setPesNum(pcashCloseDTO.getPesNum());
        pcashDetailDTO.setPplNum(pcashCloseDTO.getPplNum());
        pcashDetailDTO.setPplDate(pcashCloseDTO.getPplDate());
        pcashDetailDTO.setPdtBamount(pcashCloseDTO.getPplBamount());
        pcashDetailDTO.setPdtLamount(pcashCloseDTO.getPplLamount());
        pcashDetailDTO.setPcsNum(pcashCloseDTO.getPcsNum());
        pcashDetailDTO.setOrigPcsNum(pcashCloseDTO.getPcsNum());
        pcashDetailDTO.setPcsWebNum(pcashCloseDTO.getPcsWebNum());
        pcashDetailDTO.setPcsAccountType(pcashCloseDTO.getPcsAccountType());
        pcashDetailDTO.setPesValidDate(pcashCloseDTO.getPesValidDate());
        
        return pcashDetailDTO;
        
    }


    protected void insertPCashDetail(ActType actType, PCashDetailDTO pcashDetailDTO) throws PCashCloseException {
        
        logger.info("Insert PC_PCASH_DETAIL by pplNum [" + pplNum + "], memNum [" + memNum + "], " +
        		    "pointType [" + pointType + "], pcashDetailDTO [" + pcashDetailDTO + "] and actType [" + actType + "]");
            
        if(pcashDetailDomainBean.insertPCashDetail(pcashDetailDTO, pointType) == 0){
            
                throw new PCashCloseException(actType +
                                              PCashCloseConstant.PCASH_DETAIL_INSERT_ERROR_CODE + 
                                              PCashCloseConstant.PCASH_DETAIL_INSERT_ERROR +
                                              " pplNum [" + pplNum + "], memNum [" + memNum + "], " +
                                              " pcashDetailDTO [" + pcashDetailDTO + "]");
                
        }
        
    }
    
    protected String getPCashCloseInfoLog(){
        
        return " pplNum [" + pplNum + "], pointType [" + pointType + "] and memNum [" + memNum + "] ";
        
    }

    
    //=== Getter & Setter ========================================================================

    public PCashDetailDomainBean getPcashDetailDomainBean() {
        return pcashDetailDomainBean;
    }

    public void setPcashDetailDomainBean(PCashDetailDomainBean pcashDetailDomainBean) {
        this.pcashDetailDomainBean = pcashDetailDomainBean;
    }

    public PCashListDomainBean getPcashListDomainBean() {
        return pcashListDomainBean;
    }

    public void setPcashListDomainBean(PCashListDomainBean pcashListDomainBean) {
        this.pcashListDomainBean = pcashListDomainBean;
    }


}
