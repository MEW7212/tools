package com.example.tools.tasks;

import com.example.tools.model.DsmInfo;
import com.example.tools.service.DsmInsertService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class AddDsmPositionTask {
    @Autowired
    DsmInsertService dsmInsertService;

    @Value("${dsm.excel.path}")
    private String excelPath;

    @Value("${dsm.excel.page}")
    private int page;

    private static Map<String, String> districtMap = new HashMap<String, String>() {
        {
            put("東區", "0D51");
            put("西區", "0D52");
            put("南區", "0D53");
            put("北區", "0D54");
            put("陽明", "0D55");
        }
    };

    public void run() throws IOException {
        List<DsmInfo> dsmInfoList = new ArrayList<>();
        XSSFWorkbook wb = new XSSFWorkbook(excelPath);
        Sheet sheet = wb.getSheetAt(page - 1); // 因為活頁簿是從 0 開始

        int rowLen = sheet.getPhysicalNumberOfRows();
        log.info("Insert position into DB numbers : {}", rowLen - 1);

        // 跳過第0行欄位
        for (int i = 1; i < rowLen; i++) {
            DsmInfo obj = new DsmInfo();
            Row row = sheet.getRow(i);
            obj.setNo(String.valueOf(row.getCell(0).getNumericCellValue()));

            obj.setUnitId(districtMap.get(row.getCell(1).getStringCellValue()));
            obj.setName(row.getCell(3).getStringCellValue());
            obj.setAddress(row.getCell(4).getStringCellValue());
            obj.setBrand(row.getCell(5).getStringCellValue());
            obj.setModel(row.getCell(6).getStringCellValue());
            obj.setBore(row.getCell(7).getNumericCellValue());

            // 使用 getNumericCellValue 會導致數字變成科學記，如 1.5556E11，因此先將此格轉化為字串格式再取值
            row.getCell(8).setCellType(CellType.STRING);
            obj.setObjNum(row.getCell(8).getStringCellValue());
            row.getCell(9).setCellType(CellType.STRING);
            obj.setInterfaceId(row.getCell(9).getStringCellValue());

            if (row.getCell(11).getStringCellValue() != null) {
                obj.setSim(row.getCell(11).getStringCellValue());
            }else {
                obj.setSim(null);
            }
            obj.setLng(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
            obj.setLat(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
            dsmInfoList.add(obj);
        }

        for (DsmInfo dsmInfo : dsmInfoList) {
            dsmInsertService.insert(dsmInfo);

            // TODO 還要寫入 155、238
        }

        wb.close();

        log.info("Finished.");
    }

}
