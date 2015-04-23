package com.payeasy.pcashclose.service.command;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;

public interface PCashCloseCommand {

    /**
     * 執行結算
     * @param pplNum PCash使用記錄流水號
     * @param actType PCash使用類型
     * @throws PCashCloseException 結算邏輯錯誤
     * @throws PCashCloseDAOException 資料存取錯誤
     */
    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException;
}
