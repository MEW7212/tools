package com.example.tools.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReceivePacket {
    private String meter_id;
    private String packet_content;
    private Timestamp receive_time;
}
