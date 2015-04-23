package com.payeasy.pcashclose.service.util;

public class PCashCloseConstant {
    
    /**
     *  PCashClose Exception
     */
    public static final String PCASH_DATA_INVALID_CODE = " PCD000 ";
    public static final String PCASH_DATA_INVALID = " PCashCloseDTO傳入值不合法 ";
    
    public static final String PCASH_INVALID_ACTTYPE_CODE = " PC001 ";
    public static final String PCASH_INVALID_ACTTYPE = " 無此PCash結算型態!! "; 
    
    public static final String PCASH_DB_ERROR_CODE = " PC003 ";
    public static final String PCASH_DB_ERROR = " PCash結算發生資料庫錯誤!! ";
    
    public static final String PCASH_RUNTIME_EXCEPTION_CODE = " PC005 ";
    public static final String PCASH_RUNTIME_EXCEPTION = " 結算發生未預期錯誤 "; 
    
    public static final String PCASH_DETAIL_EMPTY_CODE = " PC007 ";
    public static final String PCASH_DETAIL_EMPTY = " 會員無PCash交易關檔紀錄 ";
    
    public static final String PCASH_DETAIL_TEMP_EMPTY_CODE = " PC008 ";
    public static final String PCASH_DETAIL_TEMP_EMPTY = " 找不到PC_PCASH_DETAIL_TEMP資料，同筆扣款取消重複提交  ";
    
    public static final String PCASH_LIST_EMPTY_CODE = " PC009 ";
    public static final String PCASH_LIST_EMPTY = " 會員無PCash活動餘額紀錄 ";
    
    public static final String PCASH_LIST_NOUPDATE_CODE = " PC010 ";
    public static final String PCASH_LIST_NOUPDATE = " PC_PCASH_LIST無異動!! ";
    
    public static final String PCASH_DETAIL_NOUPDATE_CODE = " PC011 ";
    public static final String PCASH_DETAIL_NOUPDATE = " PC_PCASH_DETAIL無異動!! ";
    
    public static final String PCASH_DEDUCTION_NOTENOUGH_CODE = " PC013 ";
    public static final String PCASH_DEDUCTION_NOTENOUGH = " 活動總餘額不足消費!! ";
    
    public static final String PCASH_DETAIL_EMPTY_CANCEL_DATA_CODE = " PC014 ";
    public static final String PCASH_DETAIL_EMPTY_CANCEL_DATA = " 未產生扣款回加資料!! ";
    
    public static final String PCASH_NO_CANCEL_RETURN_DETAIL_CODE = " PC015 ";
    public static final String PCASH_NO_CANCEL_RETURN_DETAIL = " 無可逆回的扣款關檔紀錄!! ";
    
    public static final String PCASH_DEATIL_NOT_ENOUGH_CODE = " PC016 ";
    public static final String PCASH_DEATIL_NOT_ENOUGH = " 關檔紀錄金額不足!! ";
    
    public static final String PCASH_DEATIL_ILLEGAL_DATA_CODE = " PC017 ";
    public static final String PCASH_DEATIL_ILLEGAL_DATA = " 關檔紀錄值不合法，無法取消扣款 ";
    
    public static final String PCASH_DETAIL_INSERT_ERROR_CODE = " PC019 ";
    public static final String PCASH_DETAIL_INSERT_ERROR = " PC_PCASH_DETAIL無新增!! ";
    
    public static final String PCASH_LOG_UPDATE_ERROR_CODE = " PC020 ";
    public static final String PCASH_LOG_UPDATE_ERROR = " PC_PCASG_LOG未異動為關檔!! ";
    
    public static final String PCASH_LIST_UPDATE_ERROR_CODE = " PC021 ";
    public static final String PCASH_LIST_UPDATE_ERROR = " PC_PCASH_LIST未異動!! ";
    
    public static final String PCASH_DETAIL_TEMP_DELETE_ERROR_CODE = " PC022 ";
    public static final String PCASH_DETAIL_TEMP_DELETE_ERROR = " PC_PCASH_DETAIL_TEMP未刪除!!檢查是否有重複取消 ";
    
    public static final String PCASH_DETAIL_TEMP_INSERT_ERROR_CODE = " PC023 ";
    public static final String PCASH_DETAIL_TEMP_INSERT_ERROR = " PC_PCASH_DETAIL_TEMP未新增!! ";
    
    public static final String PCASH_EMPTY_DATA_ERROR_CODE = " PC024 ";
    public static final String PCASH_EMPTY_DATA_ERROR = " 無PCash活動資料!! ";
    
    public static final String PCASH_ACCOUNT_TYPE_EMPTY_CODE = " PC025 ";
    public static final String PCASH_ACCOUNT_TYPE_EMPTY = " PCash儲值無PCASH_ACCOUNT_TYPE!! ";
    
    /**
     *  PCashCloseDAO Exception
     */
    public static final String PCASH_DETAIL_INSERT_DBERROR_CODE = " PCD001 ";
    public static final String PCASH_DETAIL_INSERT_DBERROR = " 新增PC_PCASH_DETAIL發生資料庫錯誤 !! ";
    
    public static final String PCASH_LOG_UPDATE_DBERROR_CODE = " PCD002 ";
    public static final String PCASH_LOG_UPDATE_DBERROR = " 更新PC_PCASH_LOG發生資料庫錯誤 !! ";
    
    public static final String PCASH_LOG_QUERY_DBERROR_CODE = " PCD003 ";
    public static final String PCASH_LOG_QUERY_DBERROR = " 查詢PC_PCASH_LOG發生資料庫錯誤 !! ";
    
    public static final String PCASH_LIST_QUERY_DBERROR_CODE = " PCD005 ";
    public static final String PCASH_LIST_QUERY_DBERROR = " 查詢PC_PCASH_LIST發生資料庫錯誤 ";
    
    public static final String PCASH_LIST_DELETE_DBERROR_CODE = " PCD006 ";
    public static final String PCASH_LIST_DELETE_DBERROR = " 刪除PC_PCASH_LIST發生資料庫錯誤 ";
    
    public static final String PCASH_LIST_UPDATE_DBERROR_CODE = " PCD007 ";
    public static final String PCASH_LIST_UPDATE_DBERROR = " 更新PC_PCASH_LIST發生資料庫錯誤 ";
    
    public static final String PCASH_LIST_INSERT_DBERROR_CODE = " PCD008 ";
    public static final String PCASH_LIST_INSERT_DBERROR = " 新增PC_PCASH_LIST發生資料庫錯誤 ";
    
    public static final String PCASH_DETAIL_TEMP_INSERT_DBERROR_CODE = " PCD009 ";
    public static final String PCASH_DETAIL_TEMP_INSERT_DBERROR = " 新增PC_PCASH_DETAIL_TEMP發生資料庫錯誤 !! ";
    
    public static final String PCASH_DETAIL_TEMP_DELETE_DBERROR_CODE = " PCD010 ";
    public static final String PCASH_DETAIL_TEMP_DELETE_DBERROR = " 刪除PC_PCASH_DETAIL_TEMP發生資料庫錯誤 !! ";
    
    public static final String PCASH_DETAIL_TEMP_QUERY_DBERROR_CODE = " PCD011 ";
    public static final String PCASH_DETAIL_TEMP_QUERY_DBERROR = " 查詢PC_PCASH_DETAIL_TEMP發生資料庫錯誤 !! ";
    
    public static final String PCASH_DETAIL_QUERY_DBERROR_CODE = " PCD012 ";
    public static final String PCASH_DETAIL_QUERY_DBERROR = " 查詢PC_PCASH_DETAIL發生資料庫錯誤 !! ";
    
    //購買PCash活動代號
    public static final Long PCASH_BUY_PCSNUM = new Long(1883);
    
    //取消PCash預設PCS_NUM
    public static final Long PCASH_CANCEL_PCSNUM = new Long(0);

    //PCash關檔優先權
    public static final Long LAST_CLOSE_PRIORITY =  2L;
    
    public static final Long COMMON_CLOSE_PRIORITY =  1L;
    
    public static final Long FIRST_CLOSE_PRIORITY =  0L;
    
    //PCash有效日期
    public static final String PCASH_NO_DEADLINE =  "0";
    
    public static final String PCASH_YAER_DEADLINE =  "1";
    
    public static final String PCASH_ASSIGNED_DEADLINE =  "2";
    
    public static final String PCASH_QUATER_DEADLINE =  "3";
    
    //公司流水號
    public static final String PAY_EASY_COMNUM = "1";
   
    
    
}
