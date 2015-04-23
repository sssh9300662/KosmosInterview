package com.payeasy.pcashclose.service.util;

import java.util.LinkedHashMap;
import java.util.Map;
    /**
     * 對應PC_COM_STOREACT的point_ACTTYPE 
     * 1:PCash
     * 2:HappyE 
     * 3:新光點
     * @author henry_shiu
     *
     */
    public enum PointType {

        PCASH("1"),
        HAPPYE("2"), 
        SKM("3");
        
        private final static Map<String, PointType> VALUES = new LinkedHashMap<String, PointType>();

        static {
            for (PointType pointType : PointType.values()) {
                VALUES.put(pointType.getValue(), pointType);
            }
        }

        private String pointType;

        private PointType(String pointType) {
            this.pointType = pointType;
        }

        public String getValue() {
            return this.pointType;
        }

        public static final PointType fromValue(String value) {
            
            if (VALUES.containsKey(value)) {
                return VALUES.get(value);
            }

            throw new IllegalArgumentException("fromValue(value = " + value + ") - PointType is not exist");
        }

    }
