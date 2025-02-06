package com.example.tools.repository;

import com.example.tools.model.DrinkingStationInfo;

import java.util.List;

public interface GatherDataRepository {

    List<DrinkingStationInfo> getDrinkingInfo();

    boolean checkSQLConnect();
}
