package com.example.tools.tasks;

import com.example.tools.service.MdpfService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Log4j2
public class BctMappingAndAddressCorrespondTask {
    @Autowired
    MdpfService mdpfService;

    @Value("${bct.file.path}")
    String path;

    public void run() {
        try {
            mdpfService.setMapFromBctIdMapping();

            FileInputStream fileInputStream = new FileInputStream(path);
            FileInputStream fis = fileInputStream;

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rowLen = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rowLen; i++) {
                Row row = sheet.getRow(i);

                String meterNum = row.getCell(4).getStringCellValue();
                String bctNumber = mdpfService.getBctNumber(meterNum);
                if (!bctNumber.isEmpty()) {
                    Cell cell = row.getCell(5);
                    if (cell == null) {
                        cell = row.createCell(5);
                    }
                    cell.setCellValue(bctNumber);
                }
            }

            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            workbook.close();
            fos.close();

            log.info("finished.");

        } catch (IOException e) {
            log.error("BctMappingAndAddressCorresponseTask run error: ", e);
        }
    }
}
