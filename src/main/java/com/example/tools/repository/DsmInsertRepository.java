package com.example.tools.repository;

import com.example.tools.model.DsmInfo;

public interface DsmInsertRepository {
    int insertRel1(DsmInfo dsmInfo);
    int insertRel2(DsmInfo dsmInfo);
    int insertMtrloc(DsmInfo dsmInfo);
    int insertSttnloc(DsmInfo dsmInfo);
}
