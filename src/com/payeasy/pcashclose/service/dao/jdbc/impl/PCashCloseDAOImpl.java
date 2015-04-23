package com.payeasy.pcashclose.service.dao.jdbc.impl;

import java.math.BigDecimal;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.dao.DataAccessException;

import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseDAO;
import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.util.PointType;



public class PCashCloseDAOImpl extends JdbcDaoSupport implements PCashCloseDAO {

        
    public int insertPCashList(PCashListDTO pcashListDTO, PointType pointType) throws DataAccessException {

        
        final String insertPCashListSql =
            "/*                          " +
            " * project : PCashCloseLib  " +
            " * author  : Henry          " +
            " * modified: 2012-06-27     " +
            " * purpose : 新增會員PCASH活動餘額 " +
            " */                         " +
            " INSERT INTO PC_" + pointType + "_LIST  " +
            "             ( PLS_NUM, EMP_NUM, MEM_NUM, PCS_NUM, PLS_DATE, PLS_BLANCE, PPL_CLOSE_DATE, " +
            "               ORIG_PCS_NUM, PES_NUM, PCS_ACCOUNT_TYPE, PCS_WEB_NUM, PES_VALID_DATE )" +
            " VALUES      ( PC_" + pointType + "_LIST_COUNTER.NEXTVAL, ?, ?, ?, SYSDATE, ?, SYSDATE, ?, ?, ?, ?, ? )";
        
        Object[] params = new Object[] {
                                        pcashListDTO.getEmpNum(), pcashListDTO.getMemNum(), 
                                        pcashListDTO.getPcsNum(), pcashListDTO.getPlsBlance(),
                                        pcashListDTO.getOrigPcsNum(), pcashListDTO.getPesNum(),
                                        pcashListDTO.getPcsAccountType(), pcashListDTO.getPcsWebNum(),
                                        pcashListDTO.getPesValidDate()
                                        };
        
        int insertCount = this.getJdbcTemplate().update(insertPCashListSql, params);
        
        return insertCount;

    }

    
    public int deletePCashList(Long plsNum, PointType pointType) throws DataAccessException {
        
        final String deletePCashListSql =
            "/*                                 " +
            " * project : PCashCloseLib         " +
            " * author  : Henry                 " +
            " * modified: 2012-06-21            " +
            " * purpose : 刪除特定的PC_PCASH_LIST   " +
            " */                                " +
            " DELETE FROM PC_" + pointType + "_LIST WHERE PLS_NUM = ? ";
        
        Object[] params = new Object[] {plsNum};
        
        int deleteCount = this.getJdbcTemplate().update(deletePCashListSql, params);
        
        return deleteCount;
    }

    
    public int updatePCashList(Long plsNum, BigDecimal pdtLamount, PointType pointType)
            throws DataAccessException {
        
        final String updatePCashListSql =
            "/*                              " +
            " * project : PCashCloseLib      " +
            " * author  : Henry              " +
            " * modified: 2012-06-21         " +
            " * purpose : 更新PC_PCASH_LIST餘額    " +
            " */                             " +
            " UPDATE PC_" + pointType + "_LIST           " +
            " SET PLS_BLANCE = (PLS_BLANCE - ?), PPL_CLOSE_DATE = SYSDATE " +
            " WHERE PLS_NUM = ? " +
            " AND PLS_BLANCE - ? >= 0";
        
        Object[] params = new Object[] {pdtLamount, plsNum, pdtLamount};
        
        int updateCount = this.getJdbcTemplate().update(updatePCashListSql, params);
        
        return updateCount;
    }

    
    
    public int insertPCashDetail(PCashDetailDTO pcashDetailDTO, PointType pointType) throws DataAccessException {

        final String insertPCashDetailSql =
            "/*                                " +
            " * project : PCashCloseLib        " +
            " * author  : Henry                " +
            " * modified: 2012-06-21           " +
            " * purpose : 新增PC_PCASH_DETAIL    " +
            " */                               " +
            " INSERT INTO PC_" + pointType + "_DETAIL " +
            "             (PDT_NUM, EMP_NUM, MEM_NUM, PCS_NUM, PPL_NUM, PDT_BAMOUNT, PDT_LAMOUNT," +
            "              PDT_DATE, PPL_DATE, ORIG_PCS_NUM, PES_NUM, PCS_ACCOUNT_TYPE," +
            "              PCS_WEB_NUM, PES_VALID_DATE)" +
            " VALUES (PC_" + pointType + "_DETAIL_COUNTER.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?)";
        
        Object[] params = new Object[] {
                pcashDetailDTO.getEmpNum(), pcashDetailDTO.getMemNum(), 
                pcashDetailDTO.getPcsNum(), pcashDetailDTO.getPplNum(),
                pcashDetailDTO.getPdtBamount(), pcashDetailDTO.getPdtLamount(),
                pcashDetailDTO.getPplDate(), pcashDetailDTO.getOrigPcsNum(), 
                pcashDetailDTO.getPesNum(), pcashDetailDTO.getPcsAccountType(), 
                pcashDetailDTO.getPcsWebNum(), pcashDetailDTO.getPesValidDate()
                };

        int insertCount = this.getJdbcTemplate().update(insertPCashDetailSql, params);
        
        return insertCount;

    }

    
    public int insertPCashDetailTemp(PCashDetailDTO pcashDetailDTO, PointType pointType)
            throws DataAccessException {
        
        final String insertPCashDetailTempSql =
            "/*                                     " +
            " * project : PCashCloseLib             " +
            " * author  : Henry                     " +
            " * modified: 2012-06-21                " +
            " * purpose : 新增PC_PCASH_DETAIL_TEMP    " +
            " */                                    " +
            " INSERT INTO PC_" + pointType + "_DETAIL_TEMP " +
            "             (PDT_NUM, EMP_NUM, MEM_NUM, PCS_NUM, PPL_NUM, PDT_BAMOUNT, PDT_LAMOUNT," +
            "              PDT_DATE, PPL_DATE, ORIG_PCS_NUM, PES_NUM, PDTT_DATE, PCS_ACCOUNT_TYPE," +
            "              PCS_WEB_NUM, PES_VALID_DATE)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?,?)";
        
        Object[] params = new Object[] {
                pcashDetailDTO.getPdtNum(),
                pcashDetailDTO.getEmpNum(), 
                pcashDetailDTO.getMemNum(), 
                pcashDetailDTO.getPcsNum(), 
                pcashDetailDTO.getPplNum(),
                pcashDetailDTO.getPdtBamount(), 
                pcashDetailDTO.getPdtLamount(), 
                pcashDetailDTO.getPdtDate(), 
                pcashDetailDTO.getPplDate(), 
                pcashDetailDTO.getOrigPcsNum(), 
                pcashDetailDTO.getPesNum(), 
                pcashDetailDTO.getPcsAccountType(), 
                pcashDetailDTO.getPcsWebNum(),
                pcashDetailDTO.getPesValidDate()
                };

        int insertCount = this.getJdbcTemplate().update(insertPCashDetailTempSql, params);
        
        return insertCount;
        
    }

    
    public int deletePCashDetailTemp(Long pplNum, PointType pointType) throws DataAccessException {
        
        final String deletePCashDetailTempSql =
            "/*                          " +
            " * project : PCashCloseLib  " +
            " * author  : Henry          " +
            " * modified: 2012-06-29     " +
            " * purpose : 刪除扣款取消暫存檔              " +
            " */                         " +
            " DELETE FROM PC_" + pointType + "_DETAIL_TEMP WHERE PPL_NUM = ? ";
        
        Object[] params = new Object[] {pplNum};
        
        int deleteCount = this.getJdbcTemplate().update(deletePCashDetailTempSql, params);
        
        return deleteCount;
    }

}
