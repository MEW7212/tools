package com.example.tools.tasks;

import com.example.tools.service.ChangeInterfaceIdService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Log4j2
public class ChangeInterfaceIdToWaterIdTask {
    /**
     * 變更做法，先抓 excel 上面資訊再撈取 DB 內有 interface_id 的 table 進行更新
     */

    @Autowired
    ChangeInterfaceIdService changeInterfaceIdService;

    @Value("${excel.file.path}")
    String filePath;

    public void run() {

        try {
            log.info("Start update...");

            List<String> tables = changeInterfaceIdService.getAllTablesWithInterfaceId(); // 取得所有包含 interface_id 的 table

            log.info("have to update table total: {}", tables.size());

            XSSFWorkbook wb = new XSSFWorkbook(filePath);
            Sheet sheet = wb.getSheetAt(0);
            int rowLen = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowLen; i++) {
                Row row = sheet.getRow(i);
                // 把 cell 轉成 string 格式，cell 若沒有值是 null
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(CellType.STRING);
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(CellType.STRING);
                }

                String oldInterfaceId = row.getCell(5).getStringCellValue();
                String newInterfaceId = row.getCell(6).getStringCellValue();

                for (String table : tables) {
                    changeInterfaceIdService.updateTables(table, oldInterfaceId, newInterfaceId);
                }

                log.info("old: {} to new: {} finished.", oldInterfaceId, newInterfaceId);
            }

            wb.close();
            log.info("End update...");

        } catch (IOException e) {
            log.error("ScheduledTask run error: ", e);
        }

    }
}
