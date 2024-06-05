/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataLoader {
    private static final Map<String, JsonObject> MAPPINGS_CACHE = new ConcurrentHashMap<String, JsonObject>();
    private static boolean cacheJsonMappings;

    public static boolean isCacheJsonMappings() {
        return cacheJsonMappings;
    }

    public static void enableMappingsCache() {
        cacheJsonMappings = true;
    }

    public static Map<String, JsonObject> getMappingsCache() {
        return MAPPINGS_CACHE;
    }

    public static @Nullable JsonObject loadFromDataDir(String name) {
        File file = new File(Via.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return MappingDataLoader.loadData(name);
        }
        try (FileReader reader = new FileReader(file);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)reader, JsonObject.class);
            return jsonObject;
        }
        catch (JsonSyntaxException e) {
            Via.getPlatform().getLogger().warning(name + " is badly formatted!");
            e.printStackTrace();
            return null;
        }
        catch (JsonIOException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static @Nullable JsonObject loadData(String name) {
        return MappingDataLoader.loadData(name, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static @Nullable JsonObject loadData(String name, boolean cacheIfEnabled) {
        JsonObject cached;
        if (cacheJsonMappings && (cached = MAPPINGS_CACHE.get(name)) != null) {
            return cached;
        }
        InputStream stream = MappingDataLoader.getResource(name);
        if (stream == null) {
            return null;
        }
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            JsonObject object = GsonUtil.getGson().fromJson((Reader)reader, JsonObject.class);
            if (cacheIfEnabled && cacheJsonMappings) {
                MAPPINGS_CACHE.put(name, object);
            }
            JsonObject jsonObject = object;
            return jsonObject;
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException iOException) {}
        }
    }

    public static void mapIdentifiers(Int2IntBiMap output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        Iterator<Map.Entry<String, JsonElement>> iterator = oldIdentifiers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            int value = MappingDataLoader.mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers, warnOnMissing);
            if (value == -1) continue;
            output.put(Integer.parseInt(entry.getKey()), value);
        }
    }

    @Deprecated
    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers) {
        MappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, null);
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        Iterator<Map.Entry<String, JsonElement>> iterator = oldIdentifiers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            int value = MappingDataLoader.mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers, warnOnMissing);
            if (value == -1) continue;
            output[Integer.parseInt((String)entry.getKey())] = value;
        }
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
        MappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, diffIdentifiers, true);
    }

    private static int mapIdentifierEntry(Map.Entry<String, JsonElement> entry, Object2IntMap newIdentifierMap, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
        JsonElement diffElement;
        int value = newIdentifierMap.getInt(entry.getValue().getAsString());
        if (value != -1) return value;
        if (diffIdentifiers != null && (diffElement = diffIdentifiers.get(entry.getKey())) != null) {
            value = newIdentifierMap.getInt(diffElement.getAsString());
        }
        if (value != -1) return value;
        if (!warnOnMissing || Via.getConfig().isSuppressConversionWarnings()) {
            if (!Via.getManager().isDebug()) return -1;
        }
        Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
        return -1;
    }

    @Deprecated
    public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, boolean warnOnMissing) {
        MappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, null, warnOnMissing);
    }

    /*
     * Unable to fully structure code
     */
    public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
        newIdentifierMap = MappingDataLoader.arrayToMap(newIdentifiers);
        i = 0;
        while (i < oldIdentifiers.size()) {
            block7: {
                block6: {
                    oldIdentifier = oldIdentifiers.get(i);
                    mappedId = newIdentifierMap.getInt(oldIdentifier.getAsString());
                    if (mappedId != -1) ** GOTO lbl-1000
                    if (diffIdentifiers == null || (diffElement = diffIdentifiers.get(oldIdentifier.getAsString())) == null) break block6;
                    mappedName = diffElement.getAsString();
                    if (mappedName.isEmpty()) break block7;
                    mappedId = newIdentifierMap.getInt(mappedName);
                }
                ** if (mappedId != -1) goto lbl-1000
lbl-1000:
                // 1 sources

                {
                    if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        Via.getPlatform().getLogger().warning((String)("No key for " + oldIdentifier + " :( "));
                    } else {
                        ** GOTO lbl15
                    }
lbl15:
                    // 3 sources

                    ** GOTO lbl18
                }
lbl-1000:
                // 2 sources

                {
                    output[i] = mappedId;
                }
            }
            ++i;
        }
    }

    public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
        Object2IntOpenHashMap<String> map = new Object2IntOpenHashMap<String>(object.size(), 0.99f);
        map.defaultReturnValue(-1);
        Iterator<Map.Entry<String, JsonElement>> iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            map.put(entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
        }
        return map;
    }

    public static Object2IntMap<String> arrayToMap(JsonArray array) {
        Object2IntOpenHashMap<String> map = new Object2IntOpenHashMap<String>(array.size(), 0.99f);
        map.defaultReturnValue(-1);
        int i = 0;
        while (i < array.size()) {
            map.put(array.get(i).getAsString(), i);
            ++i;
        }
        return map;
    }

    public static @Nullable InputStream getResource(String name) {
        return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
    }
}

