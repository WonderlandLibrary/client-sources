/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.management.UserListEntry;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class UserList<K, V extends UserListEntry<K>> {
    protected static final Logger LOGGER = LogManager.getLogger();
    private static final Gson field_232645_b_ = new GsonBuilder().setPrettyPrinting().create();
    private final File saveFile;
    private final Map<String, V> values = Maps.newHashMap();

    public UserList(File file) {
        this.saveFile = file;
    }

    public File getSaveFile() {
        return this.saveFile;
    }

    public void addEntry(V v) {
        this.values.put(this.getObjectKey(((UserListEntry)v).getValue()), v);
        try {
            this.writeChanges();
        } catch (IOException iOException) {
            LOGGER.warn("Could not save the list after adding a user.", (Throwable)iOException);
        }
    }

    @Nullable
    public V getEntry(K k) {
        this.removeExpired();
        return (V)((UserListEntry)this.values.get(this.getObjectKey(k)));
    }

    public void removeEntry(K k) {
        this.values.remove(this.getObjectKey(k));
        try {
            this.writeChanges();
        } catch (IOException iOException) {
            LOGGER.warn("Could not save the list after removing a user.", (Throwable)iOException);
        }
    }

    public void removeEntry(UserListEntry<K> userListEntry) {
        this.removeEntry(userListEntry.getValue());
    }

    public String[] getKeys() {
        return this.values.keySet().toArray(new String[this.values.size()]);
    }

    public boolean isEmpty() {
        return this.values.size() < 1;
    }

    protected String getObjectKey(K k) {
        return k.toString();
    }

    protected boolean hasEntry(K k) {
        return this.values.containsKey(this.getObjectKey(k));
    }

    private void removeExpired() {
        ArrayList<Object> arrayList = Lists.newArrayList();
        for (Object object : this.values.values()) {
            if (!((UserListEntry)object).hasBanExpired()) continue;
            arrayList.add(((UserListEntry)object).getValue());
        }
        for (Object object : arrayList) {
            this.values.remove(this.getObjectKey(object));
        }
    }

    protected abstract UserListEntry<K> createEntry(JsonObject var1);

    public Collection<V> getEntries() {
        return this.values.values();
    }

    public void writeChanges() throws IOException {
        JsonArray jsonArray = new JsonArray();
        this.values.values().stream().map(UserList::lambda$writeChanges$0).forEach(jsonArray::add);
        try (BufferedWriter bufferedWriter = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);){
            field_232645_b_.toJson((JsonElement)jsonArray, (Appendable)bufferedWriter);
        }
    }

    public void readSavedFile() throws IOException {
        if (this.saveFile.exists()) {
            try (BufferedReader bufferedReader = Files.newReader(this.saveFile, StandardCharsets.UTF_8);){
                JsonArray jsonArray = field_232645_b_.fromJson((Reader)bufferedReader, JsonArray.class);
                this.values.clear();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "entry");
                    UserListEntry<K> userListEntry = this.createEntry(jsonObject);
                    if (userListEntry.getValue() == null) continue;
                    this.values.put(this.getObjectKey(userListEntry.getValue()), userListEntry);
                }
            }
        }
    }

    private static JsonObject lambda$writeChanges$0(UserListEntry userListEntry) {
        return Util.make(new JsonObject(), userListEntry::onSerialization);
    }
}

