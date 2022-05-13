package com.example.tools.tasks;

import com.example.tools.service.NewPositionBCTService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Log4j2
@Component
public class GetNewBCTNumberFromNASTasks {
    @Autowired
    NewPositionBCTService newPositionBCTService;

    // NAS上點位資訊
    private String filePath = "D:\\job\\新bct的amr_no變成水號\\點位資料2022-6600.xlsx";
    // 看要抓第幾張表
    private int sheet_number = 3;
    // 哪一場案子
    private String position_case = "6600";
    // 第幾批次
    private String position_batch = "52";

    public void run() {
        Map<String, String> map = new HashMap<>();
        try {
            // xlsx檔使用 XSSFWorkbook
            XSSFWorkbook wb = new XSSFWorkbook(filePath);
            Sheet sheet = wb.getSheetAt(sheet_number); // 設定要第幾個表，從0開始所以批次要+2(中間還有欣奇岩) 原有的 6600就要+2
            int rowLen = sheet.getPhysicalNumberOfRows(); // 總共行數

            Iterator<Row> rowit = sheet.iterator();
            if (rowit.hasNext()) {
                rowit.next();  // skip table head
            }

            while (rowit.hasNext()) {
                Row row = rowit.next();
                Cell device = row.getCell(13);  // 傳輸設備
                Cell bct_number = row.getCell(12);  // bct_number
                if (device.getStringCellValue().equals("BCT")) {
                    map.put(bct_number.getStringCellValue(), bct_number.getStringCellValue());
                }
            }

            Collection<String> bct_numbers = map.values();

            for (String s : bct_numbers) {
                boolean check = newPositionBCTService.checkIfExistOrNot(s);
                if (check) {
                    log.info("{} existed. *****Continue*****", s);
                    continue;
                } else {
                    newPositionBCTService.insert(s, position_case, position_batch);
                    log.info("bct_number : {} insert success.", s);
                }
            }

            log.info("finished");

        } catch (Exception e) {
            log.info("GetNewBCTNumberFromNASTasks : ", e);
        }
    }
}
