package com.payeasy.pcashclose.service.bean.service.impl;

import java.math.BigDecimal;

import com.payeasy.pcashclose.service.bean.domain.PCashListDomainBean;
import com.payeasy.pcashclose.service.bean.service.PCashListServiceBean;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PointType;


public class PCashListServiceBeanImpl implements PCashListServiceBean {
    
    PCashListDomainBean pcashListDomainBean;
    
    public BigDecimal queryPCashListBalanceByMemNum(Long memNum, PointType pointType) throws PCashCloseException {
        
        return pcashListDomainBean.queryPCashListBalanceByMemNum(memNum, pointType);
        
    }
    
    //=== Getter & Setter ============================================================
    

    public PCashListDomainBean getPcashListDomainBean() {
        return pcashListDomainBean;
    }

    public void setPcashListDomainBean(PCashListDomainBean pcashListDomainBean) {
        this.pcashListDomainBean = pcashListDomainBean;
    }

   

   

}
