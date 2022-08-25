package com.example.tools.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DsmInfo implements Serializable {
    private static final long serialVersionUID = 6805287723870418615L;
    private String no;
    private String unitId;
    private String name;
    private String address;
    private String brand; // 廠牌
    private String model; // 型號
    private double bore; // 口徑
    private String objNum; // 表號
    private String interfaceId;
    private String sim;
    private BigDecimal lat;
    private BigDecimal lng;
}
