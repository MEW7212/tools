package com.example.tools.service.impl;

import com.example.tools.model.DsmInfo;
import com.example.tools.repository.DsmInsertRepository;
import com.example.tools.service.DsmInsertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DsmInsertServiceI implements DsmInsertService {
    @Autowired
    DsmInsertRepository dsmInsertRepository;

    @Override
    public void insert(DsmInfo dsmInfo){
        int cnt = 0;
        cnt = dsmInsertRepository.selectRel1(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertRel1(dsmInfo);
        } else {
            log.info("{} has rel1: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectRel2(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertRel2(dsmInfo);
        } else {
            log.info("{} has rel2: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectMtrloc(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertMtrloc(dsmInfo);
        } else {
            log.info("{} has mtrloc: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectSttnloc(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertSttnloc(dsmInfo);
        } else {
            log.info("{} has sttnloc: {}", dsmInfo.getInterfaceId(), cnt);
        }

        log.info("no.{} {} ok.", dsmInfo.getNo(), dsmInfo.getInterfaceId());
    }

    @Override
    public void insertDrinkingStation(DsmInfo dsmInfo){
        int cnt = 0;
        cnt = dsmInsertRepository.selectRel1c(dsmInfo.getSttnId());
        if (cnt == 0) {
            dsmInsertRepository.insertRel1c(dsmInfo);
        } else {
            log.info("{} has rel1c: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectDrinkingStationRel2(dsmInfo.getSttnId());
        if (cnt == 0) {
            dsmInsertRepository.insertDrinkingStationRel2(dsmInfo);
        } else {
            log.info("{} has rel2: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectMtrloc(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertDrinkingStationMtrloc(dsmInfo);
        } else {
            log.info("{} has mtrloc: {}", dsmInfo.getInterfaceId(), cnt);
        }

        cnt = dsmInsertRepository.selectSttnloc(dsmInfo.getInterfaceId());
        if (cnt == 0) {
            dsmInsertRepository.insertSttnlocDrinkingStation(dsmInfo);
        } else {
            log.info("{} has sttnloc: {}", dsmInfo.getInterfaceId(), cnt);
        }

        log.info("no.{} {} ok.", dsmInfo.getNo(), dsmInfo.getSttnId());
    }
}
