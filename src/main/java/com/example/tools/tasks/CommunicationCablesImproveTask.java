package com.example.tools.tasks;

import com.example.tools.model.Mtrread;
import com.example.tools.repository.CommuicationCableDocumentRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class CommunicationCablesImproveTask {

    @Value("${communication.target.file}")
    String target;

    private Map<String, String> map= new HashMap<>();

    @Autowired
    CommuicationCableDocumentRepository commuicationCableDocumentRepository;

    public void run() {
        formMap();

        List<Mtrread> list = new ArrayList<>();

        int k = 0;
        for (String key : map.keySet()) {
            k++;
            try {
                list = commuicationCableDocumentRepository.getData(key);

                if (list.isEmpty()) {
                    log.info("{}, {} is empty.", k, key);
                    continue;
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(0);
                cell0.setCellValue("介面編號");
                Cell cell1 = row0.createCell(1);
                cell1.setCellValue("紀錄時間");
                Cell cell2 = row0.createCell(2);
                cell2.setCellValue("瞬間值");
                Cell cell3 = row0.createCell(3);
                cell3.setCellValue("流量");

                Row row = null;
                Cell cell = null;
                for(int r = 1, i = 0; i < list.size(); r++, i++){
                    row = sheet.createRow(r);
                    for(int c = 0; c < 4; c++){
                        cell = row.createCell(c);
                        switch (c) {
                            case 0:
                                cell.setCellValue(list.get(i).getInterfaceId());
                                break;
                            case 1:
                                cell.setCellValue(list.get(i).getRcvtime());
                                break;
                            case 2:
                                cell.setCellValue(list.get(i).getValue_i());
                                break;
                            case 3:
                                cell.setCellValue(list.get(i).getValue_c());
                                break;
                        }
                    }
                }

                try {
                    String file = target + map.get(key) + ".xlsx";
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.flush();
                    fos.close();

                    log.info("{}, file : {}", k, file);
                } catch (IOException e) {
                    log.error("CommunicationCablesImproveTask FileOutputStream error: ", e);
                }

            } catch (Exception e) {
                log.error("CommunicationCablesImproveTask error: ", e);
            }
        }

        log.info("finished.");
    }

    private void formMap() {
        this.map.put("WA200310046791", "臺北市大安區臥龍街２４０號之２");
        this.map.put("WA200410031385", "臺北市信義區信義路五段１５０巷３４２弄２號總表");
        this.map.put("WA202008000525", "臺北市大安區忠孝東路三段４６號四樓");
        this.map.put("WA202008000527", "臺北市大安區忠孝東路三段４６號六樓");
        this.map.put("WA202401000103", "臺北市大安區忠孝東路三段９８號總表");
        this.map.put("WA202312000662", "臺北市大安區復興南路一段３４２號總表");
        this.map.put("131080902017", "臺北市中山區林森北路與金山街東西向快速道路橋下");
        this.map.put("WA200609010425", "臺北市中山區林森北路３８０號");
        this.map.put("WA202312000773", "臺北市大同區延平北路二段２７２巷15及17號等總表");
        this.map.put("WA200408029518", "臺北市萬華區水源路１９９號");
        this.map.put("WA202401000211", "臺北市萬華區西藏路１２５巷３１號地下停車場");
        this.map.put("WA201005000935", "臺北市萬華區環河南路二段３３８號B1");
        this.map.put("A-22-0550498", "新北市新店區溪洲路３１２號");
        this.map.put("WA202312000809", "新北市中和區建三路５６號");
        this.map.put("WA201412000645", "新北市中和區中正路２９１號");
        this.map.put("WA202312000385", "新北市中和區南山路２６０-２６２號");
        this.map.put("WA200408036816", "新北市中和區立言街３８號");
        this.map.put("WA202312000573", "臺北市內湖區民權東路六段１３５巷２８號至32號總表");
        this.map.put("WA200108011465", "臺北市內湖區金湖路８３號一至六樓");
        this.map.put("H-14-1000311", "新北市新店區央北一路１６號之總表");
        this.map.put("WA202110000012", "新北市新店區順德街１２８號之公共專用1樓");
        this.map.put("WA202309001090", "臺北市文山區興隆路三段２６９巷3號至9號總表");
        this.map.put("WA200510016409", "臺北市士林區承德路四段１７５號地下層");
        this.map.put("WA200807000121", "臺北市文山區萬壽路75之7及75之8號總表");
        this.map.put("WA200404042095", "臺北市文山區政大二街１７１巷１２號");
        this.map.put("WA200404038643", "臺北市文山區政大二街１７０號");
        this.map.put("WA200503041575", "臺北市文山區政大二街２１５巷19至25號總表");
        this.map.put("WA200702032542", "臺北市文山區政大二街２１５巷14至18號總表");
        this.map.put("131081206008", "臺北市北投區福美路208至216號總表");
        this.map.put("WA200706005905", "臺北市北投區竹子湖路１６號邊竹子湖派出所旁登山步道口(北投1號公廁)");
    }
}
