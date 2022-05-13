package com.example.tools.repository;

import com.example.tools.model.ReceivePacket;

import java.util.List;

public interface ReceivePacketRepository {
    List<ReceivePacket> findAllPacket();
}
