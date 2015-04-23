package com.payeasy.pcashclose.service.bean.service;

import java.math.BigDecimal;

import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PointType;

public interface PCashListServiceBean {
    
    public BigDecimal queryPCashListBalanceByMemNum(Long memNum, PointType pointType)throws PCashCloseException;

}
