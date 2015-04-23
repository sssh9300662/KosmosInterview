package com.payeasy.pcashclose.service.bean.domain;

import java.util.List;

import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;
import com.payeasy.pcashclose.service.util.PointType;

public interface PCashDetailDomainBean {

    public int insertPCashDetail(PCashDetailDTO pcashDetailDTO, PointType pointType)throws PCashCloseException;
    
    public int deletePCashDetailTemp(Long pplNum, PointType pointType)throws PCashCloseException;

    public int insertPCashDetailTemp(PCashDetailDTO pcashDetailDTO, PointType pointType)throws PCashCloseException;
    
    public List<PCashDetailDTO> queryPCashDetailByPplNumCancel(Long pplNumCancel, PointType pointType)throws PCashCloseException;

    public List<PCashDetailDTO> queryPCashDetailTempByPplNumCancel(Long pplNumCancel, PointType pointType)throws PCashCloseException;
}
