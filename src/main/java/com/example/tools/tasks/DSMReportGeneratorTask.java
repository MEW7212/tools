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
                "113238030078",
                "113238030130",
                "113238030131",
                "113238030123",
                "113248002921",
                "113238030088",
                "113248002938",
                "113238030133",
                "113238030082",
                "113238030187",
                "113238030099",
                "113238030083",
                "113248002945",
                "113238030122",
                "113238030085",
                "113248002911",
                "113238030127",
                "113238030140",
                "113238030114",
                "113238030113",
                "113241203374",
                "113238030139",
                "113238030126",
                "113238030135",
                "113238030132",
                "113238030112",
                "113238030096",
                "113238030111",
                "113248002924",
                "113248002914",
                "113241203371",
                "113238030115",
                "113238030185",
                "113248002937",
                "113238030100",
                "113238030141",
                "113248002922",
                "113248002923",
                "113238030095",
                "113248002919",
                "113248002943",
                "113248002941",
                "113238030080",
                "113241203369",
                "113231202890",
                "113241203368",
                "113238030097",
                "113248002947",
                "113238030137",
                "113241203373",
                "113248002913",
                "113238030138",
                "113238030089",
                "113238030087",
                "113238030136",
                "113238030124",
                "113238030098",
                "113238030093",
                "113238030092",
                "113238030128",
                "113238030094",
                "113238030081",
                "113238030125",
                "113231202885",
                "113238030084",
                "113241203370",
                "113238030086",
                "113238030079",
                "113238030129",
                "113248002944",
                "113238030090",
                "113238030091",
                "113238030109",
                "113241203367",
                "113241203366",
                "113231202884",
                "113241203372",
                "113238030103",
                "113248002942",
                "113248002946"
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
