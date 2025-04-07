package com.example.tools.repository;

import com.example.tools.model.DsmInfo;

public interface DsmInsertRepository {

    int selectRel1(String interfaceId);

    int insertRel1(DsmInfo dsmInfo);

    int selectRel2(String interfaceId);

    int insertRel2(DsmInfo dsmInfo);

    int selectMtrloc(String interfaceId);

    int insertMtrloc(DsmInfo dsmInfo);

    int selectSttnloc(String interfaceId);

    int insertSttnloc(DsmInfo dsmInfo);

    int selectRel1c(String sttnId);

    int insertRel1c(DsmInfo dsmInfo);

    int selectDrinkingStationRel2(String sttnId);

    int insertDrinkingStationRel2(DsmInfo dsmInfo);

    int insertDrinkingStationMtrloc(DsmInfo dsmInfo);

    int insertSttnlocDrinkingStation(DsmInfo dsmInfo);
}
