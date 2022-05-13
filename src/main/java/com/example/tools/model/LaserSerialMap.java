package com.example.tools.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LaserSerialMap implements Serializable {
    private static final long serialVersionUID = 302655447047824755L;
    String laserNumber;
    String serialNumber;
}
