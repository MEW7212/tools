package com.example.tools.tasks;

import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Log4j2
@Component
public class ModifyAndRewriteExcelTasks {

    @Autowired
    Utility utility;

    public void run() throws IOException {
        List<List<String>> list = utility.readCsv("D:\\migration\\getFrom1201to0228\\rel2對應.csv");

        //log.info(list.get(1).get(0));
        String path = "D:\\migration\\getFrom1201to0228\\id.xlsx";
        FileInputStream fis = new FileInputStream(path);

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowit = sheet.iterator();

        if (rowit.hasNext()) {
            rowit.next();  // skip table head
        }

        while (rowit.hasNext()) {
            Row row = rowit.next();
            Cell status = row.getCell(8);
            if (status != null) continue;
            else { // 如果是null代表還沒有創建那個單元格，所以必須製作一個
                status = row.createCell(8);
            }
            /*
            if (status.getStringCellValue().contains("停用")) {
                continue;
            }

             */

            Cell waterNo = row.getCell(5);

            for (int i = 0; i < list.size(); i++) {
                if (("\"" + waterNo.getStringCellValue() + "\"").contains(list.get(i).get(0))) {
                    status.setCellValue(list.get(i).get(1));
                }
            }
        }

        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        wb.close();

        log.info("finished");
    }
}

