package com.example.tools.tasks;

import com.example.tools.model.DrinkingStationData;
import com.example.tools.model.DrinkingStationInfo;
import com.example.tools.repository.GatherDataRepository;
import com.example.tools.repository.MeterRead2TaipeiRepository;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Value("${drinking.station.file.target}")
    String fileTarget;

    public void run() throws IOException, ParseException {
        getDrinkingStationData();
    }

    public void getDrinkingStationData() throws IOException {
        // 取得直飲台 interface_id、sttn_name 對應
        List<DrinkingStationInfo> drinkingStationInfos = gatherDataRepository.getDrinkingInfo();

        int counter = 0;
        XSSFWorkbook wb = new XSSFWorkbook();

        for (int k = 0; k < drinkingStationInfos.size(); k++) {
            // 在做驗收報告時，上面的介面號碼要根據需要產出的號碼改，drinkingStationDatas 方法裡面的 sql 查詢日期也要更改
            List<DrinkingStationData> drinkingStationDatas =
                    meterRead2TaipeiRepository.getDrinkingStationDataByInterfaceId(drinkingStationInfos.get(k).getInterfaceId());

            if (!drinkingStationDatas.isEmpty()) {
                counter++;
                log.info("{}-{} : {} 筆資料", drinkingStationInfos.get(k).getSttnName(), drinkingStationInfos.get(k).getInterfaceId(),
                        drinkingStationDatas.size());
                List<String> meterInfo = new ArrayList<>();
                /* 舊的
                meterInfo.add("介面編號,紀錄時間,瞬間流量,累積值,訊號源," +
                        "接收時間,電壓,訊號強度,漏水天數,負載天數," +
                        "靜止天數,反向天數,磁干擾天數,電力不足天數,開關次數," +
                        "正向瞬間,正向累積,反向瞬間,反向累積");
                */
                //meterInfo.add("管理單位,監測站,傳訊點,瞬間值,積算值,電池電壓,開關次數,紀錄時間,日期,時間,用水量,按壓次數,星期,平假日");
                meterInfo.add("管理單位");
                meterInfo.add("監測站");
                meterInfo.add("傳訊點");
                meterInfo.add("瞬間值");
                meterInfo.add("積算值");
                meterInfo.add("電池電壓");
                meterInfo.add("開關次數");
                meterInfo.add("紀錄時間");
                meterInfo.add("日期");
                meterInfo.add("時間");
                meterInfo.add("用水量");
                meterInfo.add("按壓次數");
                meterInfo.add("星期");
                meterInfo.add("平假日");

                // 創建分頁
                // 填入標頭
                Sheet sheet = wb.createSheet(drinkingStationInfos.get(k).getSttnName());
                Row row = sheet.createRow(0);
                Cell cell;
                for (int i = 0; i < meterInfo.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(meterInfo.get(i));
                }

                String unit = classifyRegion(drinkingStationInfos.get(k).getUnitId());
                String nodeName = drinkingStationInfos.get(k).getSttnName();
                String sqnc = "流量";

                //for (DrinkingStationData drinkingStationData : drinkingStationDatas) {
                int j = 1;
                for (int i = 0; i < drinkingStationDatas.size(); i++) {
                    // 1140221000000 -> 114/02/21 00:00:00
                    try {
                        String date = drinkingStationDatas.get(i).getRcvtime().substring(0, 3)
                                + "-" + drinkingStationDatas.get(i).getRcvtime().substring(3, 5)
                                + "-" + drinkingStationDatas.get(i).getRcvtime().substring(5, 7)
                                + " " + drinkingStationDatas.get(i).getRcvtime().substring(7, 9)
                                + ":" + drinkingStationDatas.get(i).getRcvtime().substring(9, 11)
                                + ":" + drinkingStationDatas.get(i).getRcvtime().substring(11, 13);
                        String week = transformDateToWeek(date);
                        String normalOrWeekend = categoryWeekend(week);
                        String valueI = drinkingStationDatas.get(i).getValueI();
                        String valueC = drinkingStationDatas.get(i).getValueC();
                        String bat = drinkingStationDatas.get(i).getBatVolt();
                        String openAmt = drinkingStationDatas.get(i).getOpenAmt();
                        String waterConsumption = i == 0 ? String.valueOf(0) : calculateWaterConsumption(valueC,
                                drinkingStationDatas.get(i - 1).getValueC());
                        String pressCount = i == 0 ? String.valueOf(0) : calculatePressCount(openAmt,
                                drinkingStationDatas.get(i - 1).getOpenAmt());

                        row = sheet.createRow(j);
                        cell = row.createCell(0);
                        cell.setCellValue(unit);
                        cell = row.createCell(1);
                        cell.setCellValue(nodeName);
                        cell = row.createCell(2);
                        cell.setCellValue(sqnc);
                        cell = row.createCell(3);
                        cell.setCellValue(valueI);
                        cell = row.createCell(4);
                        cell.setCellValue(valueC);
                        cell = row.createCell(5);
                        cell.setCellValue(bat);
                        cell = row.createCell(6);
                        cell.setCellValue(openAmt);
                        cell = row.createCell(7);
                        cell.setCellValue(date);
                        cell = row.createCell(8);
                        cell.setCellValue(date.substring(0, 9));
                        cell = row.createCell(9);
                        cell.setCellValue(date.substring(9, 18));
                        cell = row.createCell(10);
                        cell.setCellValue(waterConsumption);
                        cell = row.createCell(11);
                        cell.setCellValue(pressCount);
                        cell = row.createCell(12);
                        cell.setCellValue(week);
                        cell = row.createCell(13);
                        cell.setCellValue(normalOrWeekend);

                        j++;

                        /*
                        StringBuilder sb = new StringBuilder();
                        sb.append(unit);
                        sb.append(",");
                        sb.append(nodeName);
                        sb.append(",");
                        sb.append(sqnc);
                        sb.append(",");
                        sb.append(valueI);
                        sb.append(",");
                        sb.append(valueC);
                        sb.append(",");
                        sb.append(bat);
                        sb.append(",");
                        sb.append(openAmt);
                        sb.append(",");
                        sb.append(date);
                        sb.append(",");
                        sb.append(date, 0, 9);
                        sb.append(",");
                        sb.append(date, 9, 18);
                        sb.append(",");
                        sb.append(waterConsumption); // 用水量
                        sb.append(",");
                        sb.append(pressCount); // 按壓次數
                        sb.append(",");
                        sb.append(week);
                        sb.append(",");
                        sb.append(normalOrWeekend);

                        meterInfo.add(sb.toString());
                         */
                    } catch (Exception e) {
                        log.error("transfer data error: ", e);
                    }
                }

                //utility.createCSVFile(meterInfo, "", nodeName + ".csv");
            } else {
                log.info("{} 沒有資料", drinkingStationInfos.get(k).getInterfaceId());
            }
        }

        saveWorkbook(wb, fileTarget);
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

    private String transformDateToWeek(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    return "星期日";
                case Calendar.MONDAY:
                    return "星期一";
                case Calendar.TUESDAY:
                    return "星期二";
                case Calendar.WEDNESDAY:
                    return "星期三";
                case Calendar.THURSDAY:
                    return "星期四";
                case Calendar.FRIDAY:
                    return "星期五";
                case Calendar.SATURDAY:
                    return "星期六";
                default:
                    return "";
            }
        } catch (Exception e) {
            log.error("DrinkingStationReportTask transformDateToWeek error: ", e);
            return "";
        }
    }

    private String categoryWeekend(String week) {
        if ("星期六".equals(week) || "星期日".equals(week)) {
            return "假日";
        } else {
            if (week.isEmpty() || week == null) return "";
            return "平日";
        }
    }

    private String calculateWaterConsumption(String valueC, String lastValueC) {
        BigDecimal nowValueC = new BigDecimal(valueC);
        BigDecimal oldValueC = new BigDecimal(lastValueC);
        BigDecimal multi = new BigDecimal("1000000");

        return nowValueC.subtract(oldValueC).multiply(multi).stripTrailingZeros().toPlainString();
    }

    private String calculatePressCount(String openAmt, String lastOpenAmt) {
        BigDecimal nowOpenAmt = new BigDecimal(openAmt);
        BigDecimal oldOpenAmt = new BigDecimal(lastOpenAmt);

        return nowOpenAmt.subtract(oldOpenAmt).toString();
    }

    public static boolean saveWorkbook(XSSFWorkbook wb, String fileTarget) {
        try {
            // 先確認資料夾是否存在，沒有就創建
            File file = new File(fileTarget);
            if (!file.exists()) {
                boolean created = file.mkdirs();
                if (!created) {
                    log.error("file target: {} create fail.", fileTarget);
                    return false;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


            String filename = fileTarget + sdf.format(new Date()) + "-直飲台.xlsx";
            FileOutputStream fos =
                    new FileOutputStream(filename);
            wb.write(fos);
            fos.flush();
            fos.close();
            log.info("generate file successful, filename: {}", filename);
            return true;
        } catch (IOException e) {
            log.error("write xlsx file error: ", e);
            return false;
        }
    }
}
