package com.example.tools.tasks;

import com.example.tools.service.LaserSerialMapService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@Log4j2
public class LaserSerialMapTasks {
    @Autowired
    LaserSerialMapService laserSerialMapService;

    private String file = "D:\\Penghu\\testData\\【V3】對照表(門號+卡號+序號+雷雕+箱號+錯峰+棧板)-澎湖案最終版清單介面+水量計.xlsx";

    public void run() {
        try {
            // xlsx檔使用 XSSFWorkbook
            XSSFWorkbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(2); // 設定要第幾個表，從0開始 MINI N
            //int rowLen = sheet.getPhysicalNumberOfRows(); // 總共行數

            Iterator<Row> rowit = sheet.iterator();
            if (rowit.hasNext()) {
                rowit.next();  // skip table head
            }

            while (rowit.hasNext()) {
                Row row = rowit.next();
                Cell laser = row.getCell(1);  // laser
                Cell serial = row.getCell(5);  // serial

                laserSerialMapService.insert(laser.getStringCellValue(), serial.getStringCellValue(), "MINI-N");
            }
            log.info("mini n insert ok.");

            Sheet sheet2 = wb.getSheetAt(3); // 設定要第幾個表，從0開始 NCT
            //int rowLen2 = sheet2.getPhysicalNumberOfRows(); // 總共行數

            Iterator<Row> rowit2 = sheet2.iterator();
            if (rowit2.hasNext()) {
                rowit2.next();  // skip table head
            }

            while (rowit2.hasNext()) {
                Row row = rowit2.next();
                Cell laser = row.getCell(1);  // laser
                Cell serial = row.getCell(2);  // serial

                // 這邊number轉string數據會怪怪的 還好很少可以用手改
                // TODO 下次再改好一點
                laserSerialMapService.insert(laser.getStringCellValue(), String.valueOf(serial.getNumericCellValue()), "NCT");
            }
            log.info("NCT insert ok.");


        } catch (Exception e) {
            log.info("error : ", e);
        }
    }
}
