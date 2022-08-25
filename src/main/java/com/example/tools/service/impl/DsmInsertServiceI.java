package com.example.tools.service.impl;

import com.example.tools.model.DsmInfo;
import com.example.tools.repository.DsmInsertRepository;
import com.example.tools.service.DsmInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DsmInsertServiceI implements DsmInsertService {
    @Autowired
    DsmInsertRepository dsmInsertRepository;

    @Override
    public void insert(DsmInfo dsmInfo){
        dsmInsertRepository.insertRel1(dsmInfo);
        dsmInsertRepository.insertRel2(dsmInfo);
        dsmInsertRepository.insertMtrloc(dsmInfo);
        dsmInsertRepository.insertSttnloc(dsmInfo);
    }
}
