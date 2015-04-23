package com.payeasy.pcashclose.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


import org.apache.commons.lang.builder.ToStringBuilder;

import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PointType;

public class PCashCloseDTO implements Serializable {
    
    private static final long serialVersionUID = 5713504212736291674L;
    
    //=== 必要資料 ==========================================

    private ActType actType;
    
    private PointType pointType;
    
    private Long memNum; 
    
    private Long empNum;
    
    private Long pplNum;
    
    private Timestamp pplDate;
    
    //=== 扣款需要欄位值 ========================================
    
    private Long webNum; 
    
    private BigDecimal pplLamount; 
    
    private String pcmAccountType; 
    
    //=== 取消需要欄位值 ========================================
    
    private Long pplNumCancel;
    
    //=== 取消與儲值及購買需要欄位值 =================================
    
    private BigDecimal pplBamount; 
    
    //=== 儲值與購買需要欄位值 =====================================
   
    private String pcsAccountType;
    
    private String pcsWebNum; 
    
    private Long pcsNum;
    
    private Long pesNum;
    
    private Timestamp pesValidDate; 

    //=== Getter & Setter =================================
    
    /**
     * PCash使用類型(必要資料)
     * @param actType
     */
    public void setActType(ActType actType) {
        this.actType = actType;
    }

    public ActType getActType() {
        return actType;
    }

    /**
     * 會員流水號(data from PC_PCASH_LOG)
     * @param memNum
     */
    public void setMemNum(Long memNum) {
        this.memNum = memNum;
    }

    public Long getMemNum() {
        return memNum;
    }

    /**
     * 交易來源網站(扣款必須傳入)
     * @param webNum
     */
    public void setWebNum(Long webNum) {
        this.webNum = webNum;
    }

    public Long getWebNum() {
        return webNum;
    }

    /**
     * 扣項(data from PC_PCASH_LOG)
     * @param pplLamount
     */
    public void setPplLamount(BigDecimal pplLamount) {
        this.pplLamount = pplLamount;
    }

    public BigDecimal getPplLamount() {
        return pplLamount;
    }

    /**
     * 平台帳戶群組(data from PC_CLEARING_MERCHANT)
     * @param pcmAccountType
     */
    public void setPcmAccountType(String pcmAccountType) {
        this.pcmAccountType = pcmAccountType;
    }

    public String getPcmAccountType() {
        return pcmAccountType;
    }

    /**
     * 加項(data from PC_PCASH_LOG)
     * @param pplBamount
     */
    public void setPplBamount(BigDecimal pplBamount) {
        this.pplBamount = pplBamount;
    }

    public BigDecimal getPplBamount() {
        return pplBamount;
    }

    /**
     * 活動帳戶類別(data from PC_COM_STOREACT)
     * @param pcsAccountType
     */
    public void setPcsAccountType(String pcsAccountType) {
        this.pcsAccountType = pcsAccountType;
    }

    public String getPcsAccountType() {
        return pcsAccountType;
    }

    /**
     * 活動限制網站(data from PC_COM_STOREACT)
     * @param pcsWebNum
     */
    public void setPcsWebNum(String pcsWebNum) {
        this.pcsWebNum = pcsWebNum;
    }

    public String getPcsWebNum() {
        return pcsWebNum;
    }

    /**
     * 儲值清算日(data from PC_EMP_STOREACT)
     * @param pesValidDate
     */
    public void setPesValidDate(Timestamp pesValidDate) {
        this.pesValidDate = pesValidDate;
    }

    public Timestamp getPesValidDate() {
        return pesValidDate;
    }
    
    /**
     * PCash使用流水號(PC_PCASH_LOG PK)
     * @param pplNum
     */
    public void setPplNum(Long pplNum) {
        this.pplNum = pplNum;
    }

    public Long getPplNum() {
        return pplNum;
    }
    
    /**
     * 取消的扣款其PCash使用流水號(data from PC_PCASH_LOG)
     * @param pplNumCancel
     */
    public void setPplNumCancel(Long pplNumCancel) {
        this.pplNumCancel = pplNumCancel;
    }

    public Long getPplNumCancel() {
        return pplNumCancel;
    }
    
    /**
     * 員工流水號(data from PC_PCASH_LOG)
     * @param empNum
     */
    public void setEmpNum(Long empNum) {
        this.empNum = empNum;
    }

    public Long getEmpNum() {
        
        if(empNum == null){ 
            return 0L;
        }
        
        return empNum;
    }
    
    /**
     * PCash使用日期(data from PC_PCASH_LOG)
     * @param pplDate
     */
    public void setPplDate(Timestamp pplDate) {
        this.pplDate = pplDate;
    }

    public Timestamp getPplDate() {
        return pplDate;
    }
    
    /**
     * 活動流水號(data from PC_COM_STOREACT)
     * @param pcsNum
     */
    public void setPcsNum(Long pcsNum) {
        this.pcsNum = pcsNum;
    }

    public Long getPcsNum() {
        return pcsNum;
    }
    
    /**
     * 員工儲值序號(data from PC_EMP_STOREACT)
     * @param pesNum
     */
    public void setPesNum(Long pesNum) {
        this.pesNum = pesNum;
    }

    public Long getPesNum() {
        return pesNum;
    }
    
    /**
     * PCash點數類型(必要資料)
     * @param pointType
     */
    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    public PointType getPointType() {
        return pointType;
    }
    
    public String toString(){
        return new ToStringBuilder(this)
            .append("actType", this.actType)
            .append("pointType", this.pointType)
            .append("memNum", this.memNum)
            .append("empNum", this.empNum)
            .append("pcsNum", this.pcsNum)
            .append("pesNum", this.pesNum)
            .append("pplNum", this.pplNum)
            .append("pplNumCancel", this.pplNumCancel)
            .append("webNum", this.webNum)
            .append("pplLamount", this.pplLamount)
            .append("pplBamount", this.pplBamount)
            .append("pcsAccountType", this.pcsAccountType)
            .append("pcmAccountType", this.pcmAccountType)
            .append("pesValidDate", this.pesValidDate)
            .append("pcsWebNum", this.pcsWebNum)
            .append("pplDate", this.pplDate)
            .toString();
    }

}
