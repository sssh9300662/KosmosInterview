package com.payeasy.pcashclose.service.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.payeasy.pcashclose.service.dao.jdbc.PCashCloseQueryDAO;
import com.payeasy.pcashclose.service.dto.PCashDetailDTO;
import com.payeasy.pcashclose.service.dto.PCashListDTO;
import com.payeasy.pcashclose.service.util.PointType;



public class PCashCloseQueryDAOImpl extends JdbcDaoSupport implements PCashCloseQueryDAO {
    
    @SuppressWarnings("unchecked")
    public List<PCashListDTO> queryPCashListByMemNumAndPcmAccountType(Long memNum, Long webNum, String[] pcmAccountType, PointType pointType)throws DataAccessException {
        
        final String queryPCashListByMemNumSql =
            "/*                          " +
            " * project : PCashCloseLib    " +
            " * author  : Henry   " +
            " * modified: 2012-06-18     " +
            " * purpose : 查詢會員PCash活動餘額(根據帳戶別,清算日,先進先出排序)   " +
            " */                         " +
            " SELECT PLS_NUM, MEM_NUM, PCS_NUM, PLS_DATE, PLS_BLANCE, EMP_NUM, " +
            "        PPL_CLOSE_DATE, ORIG_PCS_NUM, PES_NUM, PCS_ACCOUNT_TYPE, PCS_WEB_NUM, PES_VALID_DATE　" +
            " FROM PC_" + pointType + "_LIST " +
            " WHERE PLS_BLANCE > 0 " +
            " AND MEM_NUM = ? " +
            " AND (PCS_WEB_NUM IS NULL OR ((PCS_WEB_NUM LIKE '" + webNum +",%') OR (PCS_WEB_NUM LIKE '%," + webNum +",%') OR (PCS_WEB_NUM LIKE '%," + webNum +"')))" +
            " AND (PES_VALID_DATE > SYSDATE OR PES_VALID_DATE IS NULL) ";
        
        final String queryPCashListOrderSql = " ORDER BY PES_VALID_DATE ASC, PCS_WEB_NUM ASC, PCS_ACCOUNT_TYPE ASC, PLS_NUM ASC ";
            
        StringBuffer queryPCashListByMemNumAndPcmAccountTypeSql = new StringBuffer();
        
        List<Object> argsList = new ArrayList<Object>();
        queryPCashListByMemNumAndPcmAccountTypeSql.append(queryPCashListByMemNumSql);
        argsList.add(memNum);
        
        queryPCashListByMemNumAndPcmAccountTypeSql.append(" AND PCS_ACCOUNT_TYPE IN ( ");
            
        for(int i = 0; i < pcmAccountType.length; i++){

            if(i == (pcmAccountType.length-1)){
                queryPCashListByMemNumAndPcmAccountTypeSql.append("? )");
            } else {
                queryPCashListByMemNumAndPcmAccountTypeSql.append("?,");
            }
            
            argsList.add(pcmAccountType[i]);
        }
        
        queryPCashListByMemNumAndPcmAccountTypeSql.append(queryPCashListOrderSql);
        
        return this.getJdbcTemplate().query(queryPCashListByMemNumAndPcmAccountTypeSql.toString(), 
                                            argsList.toArray(), new BeanPropertyRowMapper(PCashListDTO.class));
        
    }


    
    public String queryPCashListBalanceByMemNum(Long memNum, PointType pointType) throws DataAccessException {
        
        final String queryPCashListAmountSql =
            "/*                          " +
            " * project : PCashCloseLib    " +
            " * author  : Henry   " +
            " * modified: 2012-06-28     " +
            " * purpose : 查詢會員活動餘額總計   " +
            " */                         " +
            " SELECT NVL(SUM(PLS_BLANCE),0) AS TOTAL_PCASH " +
            " FROM PC_" + pointType + "_LIST " +
            " WHERE MEM_NUM = ? " +
            " AND (PES_VALID_DATE > SYSDATE OR PES_VALID_DATE IS NULL) " +
            " GROUP BY MEM_NUM ";
        
        Object[] params = {memNum};
        
        return this.getJdbcTemplate().queryForMap(queryPCashListAmountSql, params).get("TOTAL_PCASH").toString();
    }
        
    
    @SuppressWarnings("unchecked")
    public List<PCashDetailDTO> queryPCashDetailByPplNumCancel(Long pplNumCancel, PointType pointType)throws DataAccessException {
        
        final String queryPCashDetailByPplNumCancelSql =
            "/*                          " +
            " * project : PCashCloseLib    " +
            " * author  : Henry   " +
            " * modified: 2012-06-29     " +
            " * purpose : 查詢被取消的扣款之關檔紀錄   " +
            " */                         " +
            " SELECT PDT.PCS_NUM, PDT.PES_NUM, PDT.ORIG_PCS_NUM, PDT.PDT_LAMOUNT, PDT.PDT_BAMOUNT, PDT.EMP_NUM, " +
            "        PDT.PCS_ACCOUNT_TYPE, PDT.PCS_WEB_NUM, PDT.PES_VALID_DATE, " +
            "        PDT.MEM_NUM, PDT.PPL_DATE, PDT.PPL_NUM, PDT.PDT_NUM, PDT.PDT_DATE " +
            " FROM PC_" + pointType + "_DETAIL PDT " +
            " WHERE PDT.PPL_NUM = ? " + //PPL_NUM_CANCEL
            " ORDER BY PDT.PDT_NUM " ;// order by 被扣點的順序
        
        Object[] params = {pplNumCancel};
        
        return this.getJdbcTemplate().query(queryPCashDetailByPplNumCancelSql, params, 
                                                     new BeanPropertyRowMapper(PCashDetailDTO.class));
        
    }

    
    @SuppressWarnings("unchecked")
    public List<PCashDetailDTO> queryPCashDetailTempByPplNumCancel(Long pplNumCancel, PointType pointType)throws DataAccessException {
        
        final String queryPCashDetailTempByPplNumCancelSql =
            "/*                          " +
            " * project : PCashCloseLib    " +
            " * author  : Henry   " +
            " * modified: 2012-06-29     " +
            " * purpose : 查詢先前已取消的扣款關檔紀錄   " +
            " */                         " +
            "       SELECT PDT.PCS_NUM, PDT.PES_NUM, PDT.ORIG_PCS_NUM, PDT_LAMOUNT, PDT_BAMOUNT, " +
            "              PDT.PCS_ACCOUNT_TYPE, PDT.PCS_WEB_NUM, PDT.PES_VALID_DATE, " +
            "              PDT.EMP_NUM, PDT.MEM_NUM, PDT.PPL_DATE, PDT.PPL_NUM, PDT.PDT_NUM, PDT.PDT_DATE " +
            "       FROM PC_" + pointType + "_DETAIL_TEMP PDT " +
            "       WHERE PDT.PPL_NUM = ? " + //PPL_NUM_CANCEL
            "       ORDER BY PDT.PDT_NUM "; // order by 被扣點的順序
        
        Object[] params = {pplNumCancel};
        
        return this.getJdbcTemplate().query(queryPCashDetailTempByPplNumCancelSql, params, 
                                                     new BeanPropertyRowMapper(PCashDetailDTO.class));
        
    }

    
    public String queryPCashListBalanceByPlsNum(Long plsNum, PointType pointType) throws DataAccessException {
        
        final String queryPCashListAmountByPlsNumSql =
            "/*                          " +
            " * project : PCashCloseLib    " +
            " * author  : Henry   " +
            " * modified: 2012-06-28     " +
            " * purpose : 查詢會員活動餘額總計   " +
            " */                         " +
            " SELECT NVL(SUM(PLS_BLANCE),0) AS TOTAL_PCASH " +
            " FROM PC_" + pointType + "_LIST " +
            " WHERE PLS_NUM = ? " +
            " AND (PES_VALID_DATE > SYSDATE OR PES_VALID_DATE IS NULL) " +
            " GROUP BY PLS_NUM ";
        
        Object[] params = {plsNum};

        return this.getJdbcTemplate().queryForMap(queryPCashListAmountByPlsNumSql, params).get("TOTAL_PCASH").toString();
    }



}
