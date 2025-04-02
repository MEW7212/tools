package com.example.tools.tasks;

import com.example.tools.model.Kimwater;
import com.mongodb.client.*;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class MangoDBTask {

    @Value("${mangodb.uri}")
    String mangoDBUri;

    String dbName = "juno";

    public void run() {
        //deleteCollections();
        //addColumns();
        //updateNode();
        //deleteNode();
        addNode();
    }

    private void addColumns() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("willow-pond");
            MongoCollection<Document> collection = database.getCollection("fennel-kimwater");

            // 新增多欄位
            Document update = new Document("$set", new Document("name", "")
                    .append("newMeterNo", "")
                    .append("baseFlow", ""))
                    ;

            // 更新
            collection.updateMany(new Document(), update);

            log.info("所有文檔已新增欄位");
        }
    }

    private void addNode() {
        try (MongoClient mongoClient = MongoClients.create(mangoDBUri)) {
            MongoDatabase database = mongoClient.getDatabase("willow-pond");
            MongoCollection<Document> collection = database.getCollection("fennel-kimwater");

            // 從金門施工 excel 抓取點位
            List<Kimwater> list = getKimwaterDataFromExcel();

            for (Kimwater kimwater: list) {
                // 新增多欄位
                Document newDocument = new Document()
                        .append("waterNo", kimwater.getWaterNo())
                        .append("meterNo", kimwater.getMeterNo())
                        .append("area", "金門")
                        .append("grp", "金門")
                        .append("lat", 0.0)
                        .append("lng", 0.0)
                        .append("txsn", "")
                        .append("remark", "")
                        .append("workedAt", "")
                        .append("updatedAt", "")
                        .append("photo0", "")
                        .append("photo2", "")
                        .append("photo3", "")
                        .append("photo4", "")
                        .append("eid", "")
                        .append("stat", 0)
                        .append("address", kimwater.getAddress())
                        .append("baseFlow", "")
                        .append("name", kimwater.getName())
                        .append("newMeterNo", "");

                // 插入到 Collection
                collection.insertOne(newDocument);
            }

            log.info("✅ 插入成功");

        } catch (IOException e) {
            log.error("MangoDBTask addNode error: ", e);
        }
    }

    private void updateNode() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("willow-pond");
            MongoCollection<Document> collection = database.getCollection("fennel-kimwater");

            // 更新 stat 欄位為 10 的所有文件，將其改為 0
            Document filter = new Document("stat", 10);
            Document update = new Document("$set", new Document("stat", 0));

            // 執行更新
            long updatedCount = collection.updateMany(filter, update).getModifiedCount();

            log.info("Updated " + updatedCount + " documents.");
        }
    }

    private void deleteNode() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("willow-pond");
            MongoCollection<Document> collection = database.getCollection("fennel-kimwater");

            // 獲取所有文件的游標，限制數量並排序（如按 `_id` 排序）
            MongoCursor<Document> cursor = collection.find()
                    .sort(new Document("_id", 1)) // 按 `_id` 升序排序
                    .skip(10) // 跳過前10筆文件
                    .iterator();

            // 刪除多餘的文件
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                collection.deleteOne(new Document("_id", doc.get("_id")));
            }
            log.info("已保留前10筆文件，其餘文件已刪除。");
        }
    }

    private void deleteCollections() {
        // Connect to the MongoDB server
        try (MongoClient mongoClient = MongoClients.create(mangoDBUri)) {
            // Access the specified database
            MongoDatabase db = mongoClient.getDatabase(dbName);

            // List of tables to keep
            List<String> tablesToKeep = Arrays.asList("account", "deployment", "deployment_copy1", "shipping-pallet",
                    "install_insta-polling", "firmware_upgrade", "neon", "teletext");

            // Get all table names
            List<String> allTables = db.listCollectionNames().into(new ArrayList<>());

            // Build the list of tables to delete
            List<String> tablesToDelete = allTables.stream()
                    .filter(table -> !tablesToKeep.contains(table))
                    .collect(Collectors.toList());

            // Delete tables
            tablesToDelete.forEach(table -> {
                if (allTables.contains(table)) {
                    db.getCollection(table).drop();
                    log.info("Dropped collection: " + table);
                } else {
                    log.info("Collection does not exist: " + table);
                }
            });

            log.info("delete finished");
        } catch (Exception e) {
            log.error("MangoDBTask error: ", e);
        }
    }

    private List<Kimwater> getKimwaterDataFromExcel() throws IOException {
        POIFSFileSystem fileSystem = new POIFSFileSystem(new File("D:\\apps\\tools\\ref\\kimwater\\第02批次派工_機關_1140317-add.XLS"), true);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        Sheet sheet = workbook.getSheetAt(0);

        List<Kimwater> list = new ArrayList<>();
        int rowLen = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowLen; i++) {
            Row row = sheet.getRow(i);
            Cell cWaterNo = row.getCell(1);
            Cell cName = row.getCell(2);
            Cell cAddress = row.getCell(3);
            Cell cMeterNo = row.getCell(4);

            Kimwater kimwater = new Kimwater();
            if (cWaterNo != null) {
                cWaterNo.setCellType(CellType.STRING);
                kimwater.setWaterNo(cWaterNo.getStringCellValue());
            }
            if (cName != null) {
                cName.setCellType(CellType.STRING);
                kimwater.setName(cName.getStringCellValue());
            }
            if (cAddress != null) {
                cAddress.setCellType(CellType.STRING);
                kimwater.setAddress(cAddress.getStringCellValue());
            }
            if (cMeterNo != null) {
                cMeterNo.setCellType(CellType.STRING);
                kimwater.setMeterNo(cMeterNo.getStringCellValue());
            }

            list.add(kimwater);
        }

        return list;
    }
}
