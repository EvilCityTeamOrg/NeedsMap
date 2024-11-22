package com.evilcity.needsmap.entity;

import com.evilcity.needsmap.utils.DataUtils;
import org.bson.Document;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.evilcity.needsmap.Database.getDatabase;

public class User extends Entity {
    protected User(Document raw) {
        super(raw);
    }

    private static User[] convert(List<Document> raw) {
        User[] users = new User[raw.size()];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User(raw.get(i));
        }
        return users;
    }
    private static User convertSingle(List<Document> raw) {
        return raw.size() > 0 ? new User(raw.get(0)) : null;
    }

    public static User fetchByID(String id) {
        return convertSingle(Entity.fetch("users", "id", id, 0, 1));
    }
    public static User fetchByUsername(String username) {
        return convertSingle(Entity.fetch("users", "username", username, 0, 1));
    }
    public static User register(String username, String password) {
        Document d = new Document()
                .append("ID", UUID.randomUUID().toString())
                .append("Username", username)
                .append("Password", DataUtils.stringHash(password));
        getDatabase().getCollection("users").insertOne(d);
        return new User(d);
    }
}
