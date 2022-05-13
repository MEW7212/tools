package com.example.tools.service.impl;

import com.example.tools.model.ReceivePacket;
import com.example.tools.repository.ReceivePacketRepository;
import com.example.tools.service.ReceivePacketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class ReceivePacketServiceI implements ReceivePacketService {
    @Autowired
    ReceivePacketRepository receivePacketRepository;

    public List<ReceivePacket> findAllPacket() {
        try {
            return receivePacketRepository.findAllPacket();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
