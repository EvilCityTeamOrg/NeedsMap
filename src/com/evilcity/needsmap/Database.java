package com.evilcity.needsmap;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    private static Logger log = LoggerFactory.getLogger("DB");
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    public static void onServerStart() {
        String uri = "mongodb://" + ServerStart.getStringArgument("dbLogin")
                + ":" + ServerStart.getStringArgument("dbPassword")
                + "@" + ServerStart.getStringArgument("dbIP") + "/?authSource="
                + ServerStart.getStringArgument("authDB");
        log.info("Connecting to MongoDB..");
        try {
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("NeedsMap");
        } catch (Exception e) {
            log.error("MongoDB connection failed!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
