package com.example.tools.service.impl;


import com.example.tools.repository.LaserSerialMapRepository;
import com.example.tools.service.LaserSerialMapService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class LaserSerialMapServiceI implements LaserSerialMapService {

    @Autowired
    LaserSerialMapRepository laserSerialMapRepository;

    @Override
    public int insert(String laser, String serial, String device){
        try {
            return laserSerialMapRepository.insert(laser, serial, device);
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
