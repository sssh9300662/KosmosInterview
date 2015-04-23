package com.payeasy.pcashclose.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


import org.apache.commons.lang.builder.ToStringBuilder;

import com.payeasy.pcashclose.service.util.ActType;
import com.payeasy.pcashclose.service.util.PointType;

public class PCashCloseDTO implements Serializable {
    
    private static final long serialVersionUID = 5713504212736291674L;
    
    //=== ���n��� ==========================================

    private ActType actType;
    
    private PointType pointType;
    
    private Long memNum; 
    
    private Long empNum;
    
    private Long pplNum;
    
    private Timestamp pplDate;
    
    //=== ���ڻݭn���� ========================================
    
    private Long webNum; 
    
    private BigDecimal pplLamount; 
    
    private String pcmAccountType; 
    
    //=== �����ݭn���� ========================================
    
    private Long pplNumCancel;
    
    //=== �����P�x�Ȥ��ʶR�ݭn���� =================================
    
    private BigDecimal pplBamount; 
    
    //=== �x�ȻP�ʶR�ݭn���� =====================================
   
    private String pcsAccountType;
    
    private String pcsWebNum; 
    
    private Long pcsNum;
    
    private Long pesNum;
    
    private Timestamp pesValidDate; 

    //=== Getter & Setter =================================
    
    /**
     * PCash�ϥ�����(���n���)
     * @param actType
     */
    public void setActType(ActType actType) {
        this.actType = actType;
    }

    public ActType getActType() {
        return actType;
    }

    /**
     * �|���y����(data from PC_PCASH_LOG)
     * @param memNum
     */
    public void setMemNum(Long memNum) {
        this.memNum = memNum;
    }

    public Long getMemNum() {
        return memNum;
    }

    /**
     * ����ӷ�����(���ڥ����ǤJ)
     * @param webNum
     */
    public void setWebNum(Long webNum) {
        this.webNum = webNum;
    }

    public Long getWebNum() {
        return webNum;
    }

    /**
     * ����(data from PC_PCASH_LOG)
     * @param pplLamount
     */
    public void setPplLamount(BigDecimal pplLamount) {
        this.pplLamount = pplLamount;
    }

    public BigDecimal getPplLamount() {
        return pplLamount;
    }

    /**
     * ���x�b��s��(data from PC_CLEARING_MERCHANT)
     * @param pcmAccountType
     */
    public void setPcmAccountType(String pcmAccountType) {
        this.pcmAccountType = pcmAccountType;
    }

    public String getPcmAccountType() {
        return pcmAccountType;
    }

    /**
     * �[��(data from PC_PCASH_LOG)
     * @param pplBamount
     */
    public void setPplBamount(BigDecimal pplBamount) {
        this.pplBamount = pplBamount;
    }

    public BigDecimal getPplBamount() {
        return pplBamount;
    }

    /**
     * ���ʱb�����O(data from PC_COM_STOREACT)
     * @param pcsAccountType
     */
    public void setPcsAccountType(String pcsAccountType) {
        this.pcsAccountType = pcsAccountType;
    }

    public String getPcsAccountType() {
        return pcsAccountType;
    }

    /**
     * ���ʭ������(data from PC_COM_STOREACT)
     * @param pcsWebNum
     */
    public void setPcsWebNum(String pcsWebNum) {
        this.pcsWebNum = pcsWebNum;
    }

    public String getPcsWebNum() {
        return pcsWebNum;
    }

    /**
     * �x�ȲM���(data from PC_EMP_STOREACT)
     * @param pesValidDate
     */
    public void setPesValidDate(Timestamp pesValidDate) {
        this.pesValidDate = pesValidDate;
    }

    public Timestamp getPesValidDate() {
        return pesValidDate;
    }
    
    /**
     * PCash�ϥάy����(PC_PCASH_LOG PK)
     * @param pplNum
     */
    public void setPplNum(Long pplNum) {
        this.pplNum = pplNum;
    }

    public Long getPplNum() {
        return pplNum;
    }
    
    /**
     * ���������ڨ�PCash�ϥάy����(data from PC_PCASH_LOG)
     * @param pplNumCancel
     */
    public void setPplNumCancel(Long pplNumCancel) {
        this.pplNumCancel = pplNumCancel;
    }

    public Long getPplNumCancel() {
        return pplNumCancel;
    }
    
    /**
     * ���u�y����(data from PC_PCASH_LOG)
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
     * PCash�ϥΤ��(data from PC_PCASH_LOG)
     * @param pplDate
     */
    public void setPplDate(Timestamp pplDate) {
        this.pplDate = pplDate;
    }

    public Timestamp getPplDate() {
        return pplDate;
    }
    
    /**
     * ���ʬy����(data from PC_COM_STOREACT)
     * @param pcsNum
     */
    public void setPcsNum(Long pcsNum) {
        this.pcsNum = pcsNum;
    }

    public Long getPcsNum() {
        return pcsNum;
    }
    
    /**
     * ���u�x�ȧǸ�(data from PC_EMP_STOREACT)
     * @param pesNum
     */
    public void setPesNum(Long pesNum) {
        this.pesNum = pesNum;
    }

    public Long getPesNum() {
        return pesNum;
    }
    
    /**
     * PCash�I������(���n���)
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
