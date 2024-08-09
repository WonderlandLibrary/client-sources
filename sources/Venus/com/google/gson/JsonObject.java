/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class JsonObject
extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap(false);

    @Override
    public JsonObject deepCopy() {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : this.members.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue().deepCopy());
        }
        return jsonObject;
    }

    public void add(String string, JsonElement jsonElement) {
        this.members.put(string, jsonElement == null ? JsonNull.INSTANCE : jsonElement);
    }

    public JsonElement remove(String string) {
        return this.members.remove(string);
    }

    public void addProperty(String string, String string2) {
        this.add(string, string2 == null ? JsonNull.INSTANCE : new JsonPrimitive(string2));
    }

    public void addProperty(String string, Number number) {
        this.add(string, number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void addProperty(String string, Boolean bl) {
        this.add(string, bl == null ? JsonNull.INSTANCE : new JsonPrimitive(bl));
    }

    public void addProperty(String string, Character c) {
        this.add(string, c == null ? JsonNull.INSTANCE : new JsonPrimitive(c));
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public Set<String> keySet() {
        return this.members.keySet();
    }

    public int size() {
        return this.members.size();
    }

    public boolean isEmpty() {
        return this.members.size() == 0;
    }

    public boolean has(String string) {
        return this.members.containsKey(string);
    }

    public JsonElement get(String string) {
        return this.members.get(string);
    }

    public JsonPrimitive getAsJsonPrimitive(String string) {
        return (JsonPrimitive)this.members.get(string);
    }

    public JsonArray getAsJsonArray(String string) {
        return (JsonArray)this.members.get(string);
    }

    public JsonObject getAsJsonObject(String string) {
        return (JsonObject)this.members.get(string);
    }

    public Map<String, JsonElement> asMap() {
        return this.members;
    }

    public boolean equals(Object object) {
        return object == this || object instanceof JsonObject && ((JsonObject)object).members.equals(this.members);
    }

    public int hashCode() {
        return this.members.hashCode();
    }

    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}

