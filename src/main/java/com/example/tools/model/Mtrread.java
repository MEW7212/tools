package com.example.tools.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mtrread implements Serializable {
    private static final long serialVersionUID = -8167757184115188460L;
    private String interfaceId;
    private long rcvtime;
    private double value_i;
    private double value_c;
}
