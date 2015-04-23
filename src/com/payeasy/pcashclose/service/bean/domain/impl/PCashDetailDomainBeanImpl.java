package com.payeasy.pcashclose.service.bean.domain.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.payeasy.pcashclose.service.bean.domain.PCashDetailDomainBean;
import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseDAO;
import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseQueryDAO;
import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PCashCloseConstant;
import com.payeasy.pcashclose.service.util.PointType;

public class PCashDetailDomainBeanImpl implements PCashDetailDomainBean {

    PCashCloseDAO pcashCloseDAO;

    PCashCloseQueryDAO pcashCloseQueryDAO;

    public int deletePCashDetailTemp(Long pplNum, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseDAO.deletePCashDetailTemp(pplNum, pointType);
            
        }catch (DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_TEMP_DELETE_DBERROR_CODE + 
                                             PCashCloseConstant.PCASH_DETAIL_TEMP_DELETE_DBERROR +
                                             "pplNumCancel [" + pplNum + "]", ex);
            
        }
    }

    public int insertPCashDetail(PCashDetailDTO pcashDetailDTO, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseDAO.insertPCashDetail(pcashDetailDTO, pointType);
            
        }catch (DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_INSERT_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_DETAIL_INSERT_DBERROR +
                                          "pcashDetailDTO [" + pcashDetailDTO + "]", ex);
            
        }
    }

    public int insertPCashDetailTemp(PCashDetailDTO pcashDetailDTO, PointType pointType) throws PCashCloseException {
        
       try{
            
            return pcashCloseDAO.insertPCashDetailTemp(pcashDetailDTO, pointType);
            
        }catch (DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_TEMP_INSERT_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_DETAIL_TEMP_INSERT_DBERROR +
                                          "pcashDetailDTO [" + pcashDetailDTO + "]", ex);
            
        }
    }
    
    public List<PCashDetailDTO> queryPCashDetailByPplNumCancel(Long pplNumCancel, PointType pointType)throws PCashCloseException {
        
        try{
            
            return pcashCloseQueryDAO.queryPCashDetailByPplNumCancel(pplNumCancel, pointType);
            
        }catch (DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_QUERY_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_DETAIL_QUERY_DBERROR +
                                          "pplNumCancel [" + pplNumCancel + "]", ex);
            
        }
        
    }
    
    public List<PCashDetailDTO> queryPCashDetailTempByPplNumCancel(Long pplNumCancel, PointType pointType) throws PCashCloseException {
        
        try{
            
            return pcashCloseQueryDAO.queryPCashDetailTempByPplNumCancel(pplNumCancel, pointType);
            
        }catch (DataAccessException ex){
            
            throw new PCashCloseException(PCashCloseConstant.PCASH_DETAIL_TEMP_QUERY_DBERROR_CODE + 
                                          PCashCloseConstant.PCASH_DETAIL_TEMP_QUERY_DBERROR +
                                          "pplNumCancel [" + pplNumCancel + "]", ex);
            
        }
    }
    
    //=== Getter & Setter ================================================================================
    
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
