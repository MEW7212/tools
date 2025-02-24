package com.example.tools.tasks;

import com.example.tools.model.DrinkingStationData;
import com.example.tools.model.DrinkingStationInfo;
import com.example.tools.repository.GatherDataRepository;
import com.example.tools.repository.MeterRead2TaipeiRepository;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class DrinkingStationReportTask {

    @Autowired
    GatherDataRepository gatherDataRepository;

    @Autowired
    MeterRead2TaipeiRepository meterRead2TaipeiRepository;

    @Autowired
    Utility utility;

    public void run() throws IOException, ParseException {
        getDrinkingStationData();
    }

    public void getDrinkingStationData() throws IOException {
        // 取得直飲台 interface_id、sttn_name 對應
        List<DrinkingStationInfo> drinkingStationInfos = gatherDataRepository.getDrinkingInfo();

        int counter = 0;
        for (DrinkingStationInfo drinkingStationInfo : drinkingStationInfos) {
            // 在做驗收報告時，上面的介面號碼要根據需要產出的號碼改，drinkingStationDatas 方法裡面的 sql 查詢日期也要更改
            List<DrinkingStationData> drinkingStationDatas =
                    meterRead2TaipeiRepository.getDrinkingStationDataByInterfaceId(drinkingStationInfo.getInterfaceId());

            if (!drinkingStationDatas.isEmpty()) {
                counter++;
                log.info("{}-{} : {} 筆資料", drinkingStationInfo.getSttnName(), drinkingStationInfo.getInterfaceId(),
                        drinkingStationDatas.size());
                List<String> meterInfo = new ArrayList<>();
                /* 舊的
                meterInfo.add("介面編號,紀錄時間,瞬間流量,累積值,訊號源," +
                        "接收時間,電壓,訊號強度,漏水天數,負載天數," +
                        "靜止天數,反向天數,磁干擾天數,電力不足天數,開關次數," +
                        "正向瞬間,正向累積,反向瞬間,反向累積");
                */
                meterInfo.add("管理單位,監測站,傳訊點,瞬間值,積算值,電池電壓,開關次數,紀錄時間");
                String unit = classifyRegion(drinkingStationInfo.getUnitId());
                String nodeName = drinkingStationInfo.getSttnName();
                String sqnc = "流量";

                for (DrinkingStationData drinkingStationData : drinkingStationDatas) {
                    // 1140221000000 -> 114/02/21 00:00:00
                    try {
                        String date = drinkingStationData.getRcvtime().substring(0, 3)
                                + "/" + drinkingStationData.getRcvtime().substring(3, 5)
                                + "/" + drinkingStationData.getRcvtime().substring(5, 7)
                                + " " + drinkingStationData.getRcvtime().substring(7, 9)
                                + ":" + drinkingStationData.getRcvtime().substring(9, 11)
                                + ":" + drinkingStationData.getRcvtime().substring(11, 13);
                        StringBuilder sb = new StringBuilder();
                        sb.append(unit);
                        sb.append(",");
                        sb.append(nodeName);
                        sb.append(",");
                        sb.append(sqnc);
                        sb.append(",");
                        sb.append(drinkingStationData.getValueI());
                        sb.append(",");
                        sb.append(drinkingStationData.getValueC());
                        sb.append(",");
                        sb.append(drinkingStationData.getBatVolt());
                        sb.append(",");
                        sb.append(drinkingStationData.getOpenAmt());
                        sb.append(",");
                        sb.append(date);

                        meterInfo.add(sb.toString());
                    } catch (Exception e) {
                        log.error("transfer data error: ", e);
                    }
                }
                utility.createCSVFile(meterInfo, "", nodeName + ".csv");
            } else {
                log.info("{} 沒有資料", drinkingStationInfo.getInterfaceId());
            }
        }
        log.info("{} 點位轉換完畢", counter);
    }

    private String classifyRegion(String unitId) {
        String region = "";
        switch (unitId) {
            case "0D51":
                region = "東區分處";
                break;
            case "0D52":
                region = "西區分處";
                break;
            case "0D53":
                region = "南區分處";
                break;
            case "0D54":
                region = "北區分處";
                break;
            case "0D55":
                region = "陽明分處";
                break;
        }
        return region;
    }
}
