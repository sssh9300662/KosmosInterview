package com.payeasy.pcashclose.service.bean.domain;

import java.math.BigDecimal;
import java.util.List;

import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PointType;

public interface PCashListDomainBean {

    public int insertPCashList(PCashListDTO pcashListDTO, PointType pointType) throws PCashCloseException;
    
    public int deletePCashList(Long plsNum, PointType pointType)throws PCashCloseException;

    public int updatePCashList(Long plsNum, BigDecimal pdtLamount, PointType pointType)throws PCashCloseException;
    
    public List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType(Long memNum, Long webNum, String[] pcmAccountType, PointType pointType)throws PCashCloseException;

    public BigDecimal queryPCashListBalanceByPlsNum(Long plsNum, PointType pointType) throws PCashCloseException;

    public BigDecimal queryPCashListBalanceByMemNum(Long memNum, PointType pointType)throws PCashCloseException;

}
