package com.evilcity.needsmap.entity;

import org.bson.Document;

import java.util.List;
import java.util.UUID;

import static com.evilcity.needsmap.Database.getDatabase;

public class MapObject extends Entity {
    protected MapObject(Document raw) {
        super(raw);
    }

    private static MapObject[] convert(List<Document> raw) {
        MapObject[] users = new MapObject[raw.size()];
        for (int i = 0; i < users.length; i++) {
            users[i] = new MapObject(raw.get(i));
        }
        return users;
    }
    public String getID() {
        return raw.getString("ID");
    }
    public String getLon() {
        return raw.getString("lon");
    }
    public String getLat() {
        return raw.getString("lat");
    }
    public String getName() {
        return raw.getString("name");
    }

    private static MapObject convertSingle(List<Document> raw) {
        return raw.size() > 0 ? new MapObject(raw.get(0)) : null;
    }

    public static MapObject fetchByID(String id) {
        return convertSingle(Entity.fetch("objects", "ID", id, 0, 1));
    }
    public static MapObject[] fetch() {
        return convert(Entity.fetch("objects", "family", "global", 0, 200));
    }
    public static MapObject register(String lon, String lat, String name) {
        Document d = new Document()
                .append("ID", UUID.randomUUID().toString())
                .append("lon", lon)
                .append("lat", lat)
                .append("name", name)
                .append("family", "global");
        getDatabase().getCollection("objects").insertOne(d);
        return new MapObject(d);
    }
}
