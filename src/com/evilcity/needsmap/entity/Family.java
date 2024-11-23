package com.evilcity.needsmap.entity;

import com.evilcity.needsmap.utils.DataUtils;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

import static com.evilcity.needsmap.Database.getDatabase;

public class Family extends Entity {
    protected Family(Document raw) {
        super(raw);
    }

    private static Family[] convert(List<Document> raw) {
        Family[] users = new Family[raw.size()];
        for (int i = 0; i < users.length; i++) {
            users[i] = new Family(raw.get(i));
        }
        return users;
    }
    public String getID() {
        return raw.getString("ID");
    }

    private static Family convertSingle(List<Document> raw) {
        return raw.size() > 0 ? new Family(raw.get(0)) : null;
    }

    public static Family fetchByID(String id) {
        return convertSingle(Entity.fetch("users", "id", id, 0, 1));
    }
    public static Family register(String username, String password) {
        Document d = new Document()
                .append("ID", UUID.randomUUID().toString());
        getDatabase().getCollection("users").insertOne(d);
        return new Family(d);
    }
}
