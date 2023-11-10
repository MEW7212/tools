package com.example.tools.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BctIdMapping implements Serializable {
    private static final long serialVersionUID = 6899820087480418735L;
    private String bctNumber;
    private int slotIndex;
    private int stationIndex;
    private int objectIndex;
    private String waterNumber;
    private String enable;
}
