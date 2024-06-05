/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class VBMappingDataLoader {
    public static JsonObject loadFromDataDir(String name) {
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return VBMappingDataLoader.loadData(name);
        }
        try (FileReader reader = new FileReader(file);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)reader, JsonObject.class);
            return jsonObject;
        }
        catch (JsonSyntaxException e) {
            ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
            e.printStackTrace();
            ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
            return VBMappingDataLoader.loadData(name);
        }
        catch (JsonIOException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject loadData(String name) {
        InputStream stream = VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);
        try (InputStreamReader reader = new InputStreamReader(stream);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)reader, JsonObject.class);
            return jsonObject;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        Iterator<Map.Entry<String, JsonElement>> iterator = oldIdentifiers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getValue().getAsString();
            int mappedId = newIdentifierMap.getInt(key);
            if (mappedId == -1) {
                if (diffIdentifiers != null) {
                    int dataIndex;
                    String diffValue;
                    JsonPrimitive diffValueJson = diffIdentifiers.getAsJsonPrimitive(key);
                    String string = diffValue = diffValueJson != null ? diffValueJson.getAsString() : null;
                    if (diffValue == null && (dataIndex = key.indexOf(91)) != -1 && (diffValueJson = diffIdentifiers.getAsJsonPrimitive(key.substring(0, dataIndex))) != null && (diffValue = diffValueJson.getAsString()).endsWith("[")) {
                        diffValue = diffValue + key.substring(dataIndex + 1);
                    }
                    if (diffValue != null) {
                        mappedId = newIdentifierMap.getInt(diffValue);
                    }
                }
                if (mappedId == -1) {
                    if ((!warnOnMissing || Via.getConfig().isSuppressConversionWarnings()) && !Via.getManager().isDebug()) continue;
                    ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                    continue;
                }
            }
            output[Integer.parseInt((String)entry.getKey())] = mappedId;
        }
    }

    public static Map<String, String> objectToNamespacedMap(JsonObject object) {
        HashMap<String, String> mappings = new HashMap<String, String>(object.size());
        Iterator<Map.Entry<String, JsonElement>> iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            String value;
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            if (key.indexOf(58) == -1) {
                key = "minecraft:" + key;
            }
            if ((value = entry.getValue().getAsString()).indexOf(58) == -1) {
                value = "minecraft:" + value;
            }
            mappings.put(key, value);
        }
        return mappings;
    }

    public static Map<String, String> objectToMap(JsonObject object) {
        HashMap<String, String> mappings = new HashMap<String, String>(object.size());
        Iterator<Map.Entry<String, JsonElement>> iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            mappings.put(entry.getKey(), entry.getValue().getAsString());
        }
        return mappings;
    }

    public static Int2ObjectMap<MappedItem> loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping, boolean warnOnMissing) {
        Int2ObjectOpenHashMap<MappedItem> itemMapping = new Int2ObjectOpenHashMap<MappedItem>(diffMapping.size(), 0.99f);
        Object2IntMap<String> newIdenfierMap = MappingDataLoader.indexedObjectToMap(newMapping);
        Object2IntMap<String> oldIdenfierMap = MappingDataLoader.indexedObjectToMap(oldMapping);
        for (Map.Entry<String, JsonElement> entry : diffMapping.entrySet()) {
            JsonObject object = entry.getValue().getAsJsonObject();
            String mappedIdName = object.getAsJsonPrimitive("id").getAsString();
            int mappedId = newIdenfierMap.getInt(mappedIdName);
            if (mappedId == -1) {
                if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                ViaBackwards.getPlatform().getLogger().warning("No key for " + mappedIdName + " :( ");
                continue;
            }
            int oldId = oldIdenfierMap.getInt(entry.getKey());
            if (oldId == -1) {
                if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                ViaBackwards.getPlatform().getLogger().warning("No old entry for " + mappedIdName + " :( ");
                continue;
            }
            String name = object.getAsJsonPrimitive("name").getAsString();
            itemMapping.put(oldId, new MappedItem(mappedId, name));
        }
        if (!warnOnMissing) return itemMapping;
        if (Via.getConfig().isSuppressConversionWarnings()) return itemMapping;
        Iterator iterator = oldIdenfierMap.object2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            Object2IntMap.Entry entry = (Object2IntMap.Entry)iterator.next();
            if (newIdenfierMap.containsKey(entry.getKey()) || itemMapping.containsKey(entry.getIntValue())) continue;
            ViaBackwards.getPlatform().getLogger().warning("No item mapping for " + (String)entry.getKey() + " :( ");
        }
        return itemMapping;
    }
}

