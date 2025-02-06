package com.example.tools.tasks;

import com.mongodb.client.*;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        deleteNode();
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
}
