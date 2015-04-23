package com.payeasy.pcashclose.service.dao.jdbc;

import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;

import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.util.PointType;



public interface PCashCloseDAO {

    public int insertPCashList(PCashListDTO pcashListDTO, PointType pointType) throws DataAccessException;

    public int insertPCashDetail(PCashDetailDTO pcashDetailDTO, PointType pointType)throws DataAccessException;
    
    public int deletePCashList(Long plsNum, PointType pointType)throws DataAccessException;

    public int updatePCashList(Long plsNum, BigDecimal pdtLamount, PointType pointType)throws DataAccessException;

    public int deletePCashDetailTemp(Long pplNum, PointType pointType)throws DataAccessException;

    public int insertPCashDetailTemp(PCashDetailDTO pCashDetailDTO, PointType pointType)throws DataAccessException;

}
