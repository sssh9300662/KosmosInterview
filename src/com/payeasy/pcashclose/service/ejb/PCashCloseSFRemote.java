package com.payeasy.pcashclose.service.ejb;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PointType;

@Remote
public interface PCashCloseSFRemote {

    public void closePCash(PCashCloseDTO pcashCloseDTO) throws EJBException;

    public BigDecimal queryMemPCashListBalance(Long memNum, PointType pointType)throws PCashCloseException ;
}
