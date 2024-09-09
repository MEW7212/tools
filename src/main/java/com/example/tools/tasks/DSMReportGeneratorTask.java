package com.example.tools.tasks;

import com.example.tools.model.DSM;
import com.example.tools.repository.GatherDataRepository;
import com.example.tools.repository.MeterRead2TaipeiRepository;
import com.example.tools.repository.TimeTmpRepository;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DSMReportGeneratorTask {

    @Autowired
    GatherDataRepository gatherDataRepository;

    @Autowired
    TimeTmpRepository timeTmpRepository;

    @Autowired
    MeterRead2TaipeiRepository meterRead2TaipeiRepository;

    @Autowired
    Utility utility;

    public void run() throws IOException, ParseException {
        if (gatherDataRepository.checkSQLConnect()) {
            log.info("MSSQL連接成功");
        } else {
            log.info("MSSQL連接失敗");
        }

        if (timeTmpRepository.checkMySQLConnect()) {
            log.info("MySQL連接成功");
        } else {
            log.info("MySQL連接失敗");
        }

        //serialsNumberGenerator();
        // objnumToMeter();
        getDSMData();
        // getLDay();
        // genL2Data();
        //getL2DataSqnc2();
    }

    public void getDSMData() throws IOException {
        String[] interfaceIds = {
                "113238002491",
                "113238002490",
                "113238002486",
                "113238002489",
                "113238002487",
                "113238002495",
                "113238002496",
                "113238002485",
                "113238002483",
                "113238002492",
                "113238002482",
                "113238002493",
                "113231202918",
                "113238002484",
                "113238002473",
                "113238002456",
                "113238002469",
                "113238002475",
                "113238002474",
                "113238002470",
                "113238002477",
                "113238002476",
                "113231202919",
                "113238002472",
                "113238002479",
                "113238002480",
                "113238002481",
                "113238002471",
                "113238002488",
                "113238002478"
        };
        int counter = 0;
        for (String interfaceId : interfaceIds) {
            // 在做驗收報告時，上面的介面號碼要根據需要產出的號碼改，getDSMByInterfaceId 方法裡面的 sql 查詢日期也要更改
            List<DSM> dsms = meterRead2TaipeiRepository.getDSMByInterfaceId(interfaceId);

            if (!dsms.isEmpty()) {
                counter++;
                log.info("{} {}", interfaceId, dsms.size());
                String nodeName = dsms.get(0).getLocation();
                List<String> meterInfo = new ArrayList<>();
                meterInfo.add("管理單位,監測站,傳訊點,瞬間值,積算值,電池電壓,傳訊時間");
                // "西區分處","DSM新北市永和區中山路1段213號","反向流量","26.64","6787.55","3.57","110/12/18 00:00:00"

                for (DSM dsm : dsms) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(dsm.getUnitID());
                    sb.append(",");
                    sb.append(dsm.getLocation());
                    sb.append(",");
                    sb.append(dsm.getSqnc());
                    sb.append(",");
                    sb.append(dsm.getValueI());
                    sb.append(",");
                    sb.append(dsm.getValueC());
                    sb.append(",");
                    sb.append(dsm.getBat());
                    sb.append(",");
                    sb.append(dsm.getRcvtime());
                    meterInfo.add(sb.toString());
                }
                utility.createCSVFile(meterInfo, "", nodeName + ".csv");
            } else {
                log.info("{} 沒有資料", interfaceId);
            }
        }
        log.info("{}點位轉換完畢", counter);
    }

}
