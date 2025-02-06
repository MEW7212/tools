package com.example.tools.repository;

import com.example.tools.model.DSM;
import com.example.tools.model.DrinkingStationData;

import java.util.List;

public interface MeterRead2TaipeiRepository {

    List<DSM> getDSMByInterfaceId(String interfaceId);

    List<DrinkingStationData> getDrinkingStationDataByInterfaceId(String interfaceId);

}
