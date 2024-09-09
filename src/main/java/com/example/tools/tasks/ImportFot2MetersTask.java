package com.example.tools.tasks;

import com.example.tools.service.Fot2MdpfService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class ImportFot2MetersTask {

    @Value("${fot2.construction.file}")
    String filepath;

    @Autowired
    Fot2MdpfService fot2MdpfService;

    public void run() {
        List<String> meters = new ArrayList<>();

        try (XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filepath))){
            Sheet sheet = wb.getSheetAt(0);
            int rowLen = sheet.getPhysicalNumberOfRows();
            log.info("rowLen: {}", rowLen);

            // 跳過第0行欄位
            for (int i = 1; i < rowLen;i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(15) != null) {
                    row.getCell(15).setCellType(CellType.STRING);

                    String meter = row.getCell(15).getStringCellValue();
                    meters.add(meter);
                }
            }
        } catch (IOException e) {
            log.error("wb error: ", e);
        }

        log.info("meters size: {}", meters.size());
        // 填入 table 中
        for (String meter: meters) {
            // select meter from table
            if (fot2MdpfService.findMeter(meter)) {
                // true 是沒資料要 insert
                fot2MdpfService.insert(meter);

                log.info("{} insert true", meter);
            }
        }

        log.info("ImportFot2MetersTask run complete.");
    }
}
