package com.payeasy.pcashclose.service.command;

import com.payeasy.pcashclose.service.dto.PCashCloseDTO;
import com.payeasy.pcashclose.service.exception.PCashCloseException;

public interface PCashCloseCommand {

    /**
     * ���浲��
     * @param pplNum PCash�ϥΰO���y����
     * @param actType PCash�ϥ�����
     * @throws PCashCloseException �����޿���~
     * @throws PCashCloseDAOException ��Ʀs�����~
     */
    public void closePCash(PCashCloseDTO pcashCloseDTO) throws PCashCloseException;
}
