package com.example.tools.tasks;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;
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
        deleteCollections();
    }

    private void deleteCollections() {
        // Connect to the MongoDB server
        try (MongoClient mongoClient = MongoClients.create(mangoDBUri)) {
            // Access the specified database
            MongoDatabase db = mongoClient.getDatabase(dbName);

            // List of tables to keep
            List<String> tablesToKeep = Arrays.asList("account", "deployment", "deployment_copy1", "firmware_upgrade", "neon", "teletext");

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
