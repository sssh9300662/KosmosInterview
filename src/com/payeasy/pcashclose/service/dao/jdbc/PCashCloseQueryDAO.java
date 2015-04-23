package com.payeasy.pcashclose.service.dao.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.util.PointType;



public interface PCashCloseQueryDAO {

    public List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType(Long memNum, Long webNum, String[] pcmAccountType, PointType pointType)throws DataAccessException;

    public List<PCashDetailDTO> queryPCashDetailByPplNumCancel(Long pplNumCancel, PointType pointType)throws DataAccessException;
    
    public List<PCashDetailDTO> queryPCashDetailTempByPplNumCancel(Long pplNumCancel, PointType pointType)throws DataAccessException;

    public String queryPCashListBalanceByPlsNum(Long plsNum, PointType pointType)throws DataAccessException;
    
    public String queryPCashListBalanceByMemNum(Long memNum, PointType pointType) throws DataAccessException;

}
