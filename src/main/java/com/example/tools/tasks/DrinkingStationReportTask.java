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
                log.info("{} : {} 筆資料", drinkingStationInfo.getInterfaceId(), drinkingStationDatas.size());
                String nodeName = drinkingStationInfo.getSttnName();
                List<String> meterInfo = new ArrayList<>();
                meterInfo.add("介面編號,紀錄時間,瞬間流量,累積值,訊號源," +
                        "接收時間,電壓,訊號強度,漏水天數,負載天數," +
                        "靜止天數,反向天數,磁干擾天數,電力不足天數,開關次數," +
                        "正向瞬間,正向累積,反向瞬間,反向累積");

                for (DrinkingStationData drinkingStationData : drinkingStationDatas) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(drinkingStationData.getInterfaceId());
                    sb.append(",");
                    sb.append(drinkingStationData.getRcvtime());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueI());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueC());
                    sb.append(",");
                    sb.append(drinkingStationData.getInterfaceType());
                    sb.append(",");
                    sb.append(drinkingStationData.getDvcSndTime());
                    sb.append(",");
                    sb.append(drinkingStationData.getBatVolt());
                    sb.append(",");
                    sb.append(drinkingStationData.getComStrg());
                    sb.append(",");
                    sb.append(drinkingStationData.getLday());
                    sb.append(",");
                    sb.append(drinkingStationData.getNday());
                    sb.append(",");
                    sb.append(drinkingStationData.getOday());
                    sb.append(",");
                    sb.append(drinkingStationData.getUday());
                    sb.append(",");
                    sb.append(drinkingStationData.getHday());
                    sb.append(",");
                    sb.append(drinkingStationData.getBday());
                    sb.append(",");
                    sb.append(drinkingStationData.getOpenAmt());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueIN());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueCN());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueIU());
                    sb.append(",");
                    sb.append(drinkingStationData.getValueCU());

                    meterInfo.add(sb.toString());
                }
                utility.createCSVFile(meterInfo, "", nodeName + ".csv");
            } else {
                log.info("{} 沒有資料", drinkingStationInfo.getInterfaceId());
            }
        }
        log.info("{} 點位轉換完畢", counter);
    }
}
