package com.payeasy.pcashclose.service.util;

    /**
     * ����PC_PCASH_LOG��PPL_ACTTYPE 
     * 3:PCash�x�� 
     * 12:PaymentGateway���O 
     * 13:PaymentGateway���O����
     * 14:PCash�ʶR
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

