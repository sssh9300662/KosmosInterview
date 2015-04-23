package com.payeasy.pcashclose.service.ejb;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.payeasy.pcashclose.service.bean.service.PCashListServiceBean;
import com.payeasy.pcashclose.service.command.PCashCloseCommand;
import com.payeasy.pcashclose.service.command.PCashCloseCommandFactory;
import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PCashCloseValidator;
import com.payeasy.pcashclose.service.util.PointType;

@Stateless(mappedName = "ejb/PCashCloseSF", name = "PCashCloseSF")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class PCashCloseSF implements PCashCloseSFRemote {

    private final static Logger logger = Logger.getLogger(PCashCloseSF.class);
    
    @Autowired
    private PCashCloseCommandFactory pcashCloseCommandFactory;
    
    @Autowired
    private PCashCloseValidator pcashCloseValidator;
    
    @Autowired
    private PCashListServiceBean pcashListServiceBean; 
    
    public void closePCash(PCashCloseDTO pcashCloseDTO) throws EJBException {
        
        Long pplNum = pcashCloseDTO.getPplNum();
        ActType actType = pcashCloseDTO.getActType();

        logger.info("################## " +
                    "Start close PC_PCASH_LOG by pplNum [" + pplNum + "] " +
                    "and pcashCloseDTO [" + pcashCloseDTO + "]" +
                    "##################");

         try{
            
            pcashCloseValidator.checkPCashCloseDTOData(pcashCloseDTO);
            
            PCashCloseCommand pcashClosecommand = pcashCloseCommandFactory.createCommand(actType, pplNum);
            pcashClosecommand.closePCash(pcashCloseDTO);
            
        } catch (PCashCloseException e) {
            
            logger.error("close pcashLog by pplNum [" + pplNum + "] and actType [" + actType + "] " +
                         "- catch PCashCloseException", e);
            
            throw new EJBException("close pcashLog by pplNum [" + pplNum + "] and actType [" + actType + "] " +
                                   "- catch PCashCloseException", e);
            
        } catch (Exception e) {
            
            logger.error("close pcashLog by pplNum [" + pplNum + "] and actType [" + actType + "] " +
                         "- catch Exception", e);
            
            throw new EJBException("close pcashLog by pplNum [" + pplNum + "] and actType [" + actType + "] " +
                                   "- catch Exception", e);
            
        }

        logger.info("################## " +
                    "Finish close PC_PCASH_LOG by pplNum [" + pplNum + "] " +
                    "and pcashCloseDTO [" + pcashCloseDTO + "]" +
                    "##################");
        
    }

    public BigDecimal queryMemPCashListBalance(Long memNum, PointType pointType) throws PCashCloseException {
            
        logger.info("################## Start query PC_PCASH_LIST by memNum [" + memNum + "] " +
        		    "and pointType [" + pointType + "] ##################");
        
        BigDecimal memPcashListBalance =  pcashListServiceBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
        logger.info("Member [" + memNum + "] has [" + memPcashListBalance + "] pcash");
        
        logger.info("################## Finish query PC_PCASH_LIST by memNum [" + memNum + "] " +
                    "and pointType [" + pointType + "] ##################");
        
        return memPcashListBalance;
        
    }


}
