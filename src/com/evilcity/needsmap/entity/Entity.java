package com.evilcity.needsmap.entity;

import com.evilcity.needsmap.Database;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.evilcity.needsmap.Database.getDatabase;

public abstract class Entity {
    private Document raw;

    protected Entity(Document raw) {
        this.raw = raw;
    }

    protected static List<Document> fetch(String collection, String property, String value, int offset, int limit) {
        FindIterable<Document> iterable = getDatabase().getCollection(collection)
                .find(Filters.eq(property, value))
                .skip(offset)
                .limit(limit);
        ArrayList<Document> list = new ArrayList<>();
        for (Document d : iterable) {
            list.add(d);
        }
        return list;
    }
}
