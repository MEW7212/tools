package com.example.tools.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class newPositionBCT implements Serializable {
    private static final long serialVersionUID = -1522597553657702993L;
    private String bct_number;
    private String position_case;
    private String position_batch;
}
