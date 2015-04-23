package com.payeasy.pcashclose.service.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PCashDetailDTO extends PCashCommonDataDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1976004053499818927L;
    
    private Long pdtNum;
    
    private Long pplNum;
    
    private BigDecimal pdtBamount; 
    
    private BigDecimal pdtLamount;
    
    private Timestamp pdtDate;
    
    private Timestamp pplDate;
    
    private Timestamp pesValidDate;
    
    private String pcsAccountType;
    
    private String pcsWebNum;
    
    //=== Getter & Setter ==================================

    public Long getPplNum() {
        return pplNum;
    }

    public void setPplNum(Long pplNum) {
        this.pplNum = pplNum;
    }

    public BigDecimal getPdtBamount() {
        return pdtBamount;
    }

    public void setPdtBamount(BigDecimal pdtBamount) {
        this.pdtBamount = pdtBamount;
    }

    public BigDecimal getPdtLamount() {
        return pdtLamount;
    }

    public void setPdtLamount(BigDecimal pdtLamount) {
        this.pdtLamount = pdtLamount;
    }

    public Timestamp getPdtDate() {
        return pdtDate;
    }

    public void setPdtDate(Timestamp pdtDate) {
        this.pdtDate = pdtDate;
    }

    public Timestamp getPplDate() {
        return pplDate;
    }

    public void setPplDate(Timestamp pplDate) {
        this.pplDate = pplDate;
    }
    
    public void setPcsWebNum(String pcsWebNum) {
        this.pcsWebNum = pcsWebNum;
    }

    public String getPcsWebNum() {
        return pcsWebNum;
    }

    public void setPesValidDate(Timestamp pesValidDate) {
        this.pesValidDate = pesValidDate;
    }

    public Timestamp getPesValidDate() {
        return pesValidDate;
    }

    public void setPcsAccountType(String pcsAccountType) {
        this.pcsAccountType = pcsAccountType;
    }

    public String getPcsAccountType() {
        return pcsAccountType;
    }
    
    public void setPdtNum(Long pdtNum) {
        this.pdtNum = pdtNum;
    }

    public Long getPdtNum() {
        return pdtNum;
    }
    
    public String toString(){
        return new ToStringBuilder(this)
            .append("pdtNum", this.pdtNum)
            .append("memNum", super.getMemNum())
            .append("empNum", super.getEmpNum())
            .append("pesNum", super.getPesNum())
            .append("pcsNum", super.getPcsNum())
            .append("origPcsNum", super.getOrigPcsNum())
            .append("pplNum", this.pplNum)
            .append("pplDate", this.pplDate)
            .append("pdtDate", this.pdtDate)
            .append("pdtBamount", this.pdtBamount)
            .append("pdtLamount", this.pdtLamount)
            .append("pcsAccountType", this.pcsAccountType)
            .append("pesValidDate", this.pesValidDate)
            .append("pcsWebNum", this.pcsWebNum)
            .toString();
    }

}
