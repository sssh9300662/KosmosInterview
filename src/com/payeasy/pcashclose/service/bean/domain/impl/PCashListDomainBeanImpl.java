package com.payeasy.pcashclose.service.bean.domain.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.payeasy.pcashclose.service.bean.domain.PCashListDomainBean;
import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseDAO;
import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseQueryDAO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;
import com.payeasy.pcashclose.service.util.PointType;

public class PCashListDomainBeanImpl implements PCashListDomainBean {

    private static final Logger logger = Logger.getLogger(PCashListDomainBeanImpl.class);
    
    private PCashCloseDAO pcashCloseDAO;

    private PCashCloseQueryDAO pcashCloseQueryDAO;

    public int deletePCashList(Long plsNum, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseDAO.deletePCashList(plsNum, pointType);
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_DELETE_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_DELETE_DBERROR +
                                          "plsNum [" + plsNum + "]", ex);
            
        }

    }

    public int insertPCashList(PCashListDTO pcashListDTO, PointType pointType)throws PCashCloseException {
        
        try{
            
            return pcashCloseDAO.insertPCashList(pcashListDTO, pointType);
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_INSERT_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_INSERT_DBERROR +
                                          "pcashListDTO [" + pcashListDTO + "]", ex);
            
        }
    }

    public int updatePCashList(Long plsNum, BigDecimal pdtLamount, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseDAO.updatePCashList(plsNum, pdtLamount, pointType);
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_UPDATE_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_UPDATE_DBERROR +
                                          " plsNum [" + plsNum + "] and pointType [" + pointType + "]", ex);
            
        }
    }
    
    public List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType(Long memNum, Long webNum, String[] pcmAccountType, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseQueryDAO.queryPCashListByMemNumAndPcmAccountType(memNum, webNum, pcmAccountType, pointType);
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_QUERY_DBERROR_CODE + 
                                             PCashCloseConstant.PCASH_LIST_QUERY_DBERROR +
                                             " memNum [" + memNum + "] and pointType [" + pointType + "]", ex);
            
        }
    }
    
    public BigDecimal queryPCashListBalanceByMemNum(Long memNum, PointType pointType)throws PCashCloseException {
        
        try{
            
            return new BigDecimal(pcashCloseQueryDAO.queryPCashListBalanceByMemNum(memNum, pointType));
            
        }catch(EmptyResultDataAccessException e){
            
            logger.info("Member [" + memNum + "] has zero pcashList balance !!");
            return new BigDecimal(0);
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_QUERY_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_QUERY_DBERROR +
                                          "memNum [" + memNum + "] and pointType [" + pointType + "]", ex);
            
        }
    }

    public BigDecimal queryPCashListBalanceByPlsNum(Long plsNum, PointType pointType) throws PCashCloseException {
        
        try{
            
           return new BigDecimal(pcashCloseQueryDAO.queryPCashListBalanceByPlsNum(plsNum, pointType));
            
        }catch(DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_LIST_QUERY_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_LIST_QUERY_DBERROR +
                                          "plsNum [" + plsNum + "] and pointType [" + pointType + "]", ex);
            
        }

    }
    
    //=== Getter & Setter ===========================================================
    
    public PCashCloseDAO getPcashCloseDAO() {
        return pcashCloseDAO;
    }

    public void setPcashCloseDAO(PCashCloseDAO pcashCloseDAO) {
        this.pcashCloseDAO = pcashCloseDAO;
    }

    public PCashCloseQueryDAO getPcashCloseQueryDAO() {
        return pcashCloseQueryDAO;
    }

    public void setPcashCloseQueryDAO(PCashCloseQueryDAO pcashCloseQueryDAO) {
        this.pcashCloseQueryDAO = pcashCloseQueryDAO;
    }

}
