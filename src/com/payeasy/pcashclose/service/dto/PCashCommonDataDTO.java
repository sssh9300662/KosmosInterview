package com.payeasy.pcashclose.service.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PCashCommonDataDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5097185888463121778L;

    private Long memNum; 
    
    private Long pesNum;
    
    private Long pcsNum;
    
    private Long empNum;
    
    private Long origPcsNum;
    
    //=== Getter & Setter =================================================
    
    public Long getMemNum() {
        return memNum;
    }

    public void setMemNum(Long memNum) {
        this.memNum = memNum;
    }

    public Long getPesNum() {
        return pesNum;
    }

    public void setPesNum(Long pesNum) {
        this.pesNum = pesNum;
    }

    public Long getPcsNum() {
        return pcsNum;
    }

    public void setPcsNum(Long pcsNum) {
        this.pcsNum = pcsNum;
    }

    public Long getEmpNum() {
        return empNum;
    }

    public void setEmpNum(Long empNum) {
        this.empNum = empNum;
    }

    public Long getOrigPcsNum() {
        return origPcsNum;
    }

    public void setOrigPcsNum(Long origPcsNum) {
        this.origPcsNum = origPcsNum;
    }
    
    public String toString(){
        return new ToStringBuilder(this)
            .append("memNum", this.memNum)
            .append("empNum", this.empNum)
            .append("pesNum", this.pesNum)
            .append("pcsNum", this.pcsNum)
            .append("origPcsNum", this.origPcsNum)
            .toString();
    }

}
