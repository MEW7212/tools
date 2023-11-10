package com.example.tools.tasks;

import com.example.tools.service.MdpfService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Add1800BigUsersWaterNumTask {
    @Autowired
    MdpfService mdpfService;

    public void run() throws IOException {
        List<String> sttnIdArrays = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\tools\\ref\\2023-06資料北水1809戶府屬機關amr.xlsx");
        for (int i = 0; i < 3; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int rowLen = sheet.getPhysicalNumberOfRows();
            for (int j = 1; j < rowLen; j++) {
                Row row = sheet.getRow(j);
                sttnIdArrays.add(row.getCell(0).getStringCellValue());
            }
        }

        for (String sttnId : sttnIdArrays) {
            mdpfService.insert1800SttnId(sttnId);
        }

        log.info("finished");
    }
}
