package com.payeasy.pcashclose.service.util;

    /**
     * 對應PC_PCASH_LOG的PPL_ACTTYPE 
     * 3:PCash儲值 
     * 12:PaymentGateway消費 
     * 13:PaymentGateway消費取消
     * 14:PCash購買
     * @author henry_shiu
     *
     */
    public enum ActType {

        SAVE_PCASH("3"),
        DEDUCT_PCASH("12"), 
        CANCEL_PCASH("13"),
        BUY_PCASH("14");

        private String actType;

        private ActType(String actType) {
            this.actType = actType;
        }

        public String getActType() {
            return this.actType;
        }

    }

