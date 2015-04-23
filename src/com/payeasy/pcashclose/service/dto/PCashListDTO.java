package com.payeasy.pcashclose.service.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PCashListDTO extends PCashCommonDataDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3099820952048566289L;

    private Long plsNum;  
    
    private BigDecimal plsBlance; 
    
    private Timestamp pplCloseDate;
    
    private Timestamp pesValidDate;
    
    private Timestamp plsDate;
    
    private String pcsWebNum;
    
    private String pcsAccountType;
    
    //=== Getter & Setter ================================================
    
    public Long getPlsNum() {
        return plsNum;
    }

    public void setPlsNum(Long plsNum) {
        this.plsNum = plsNum;
    }

    public BigDecimal getPlsBlance() {
        return plsBlance;
    }

    public void setPlsBlance(BigDecimal plsBlance) {
        this.plsBlance = plsBlance;
    }
    
    public void setPplCloseDate(Timestamp pplCloseDate) {
        this.pplCloseDate = pplCloseDate;
    }

    public Timestamp getPplCloseDate() {
        return pplCloseDate;
    }
    
    public void setPesValidDate(Timestamp pesValidDate) {
        this.pesValidDate = pesValidDate;
    }

    public Timestamp getPesValidDate() {
        return pesValidDate;
    }
    
    public void setPcsWebNum(String pcsWebNum) {
        this.pcsWebNum = pcsWebNum;
    }

    public String getPcsWebNum() {
        return pcsWebNum;
    }

    public void setPcsAccountType(String pcsAccountType) {
        this.pcsAccountType = pcsAccountType;
    }

    public String getPcsAccountType() {
        return pcsAccountType;
    }
    
    public void setPlsDate(Timestamp plsDate) {
        this.plsDate = plsDate;
    }

    public Timestamp getPlsDate() {
        return plsDate;
    }


    public String toString(){
        return new ToStringBuilder(this)
            .append("memNum", super.getMemNum())
            .append("empNum", super.getEmpNum())
            .append("pesNum", super.getPesNum())
            .append("pcsNum", super.getPcsNum())
            .append("origPcsNum", super.getOrigPcsNum())
            .append("plsNum", this.plsNum)
            .append("plsBlance", this.plsBlance)
            .append("pplCloseDate", this.pplCloseDate)
            .append("pcsWebNum", this.pcsWebNum)
            .append("pcsAccountType", this.pcsAccountType)
            .append("pesValidDate", this.pesValidDate)
            .append("plsDate", this.plsDate)
            .toString();
    }



}
