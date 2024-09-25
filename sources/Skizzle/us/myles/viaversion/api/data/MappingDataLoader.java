/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.ViaVersion.util.Int2IntBiMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonIOException;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonSyntaxException;

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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static JsonObject loadFromDataDir(String name) {
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
        catch (IOException | JsonIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static JsonObject loadData(String name) {
        return MappingDataLoader.loadData(name, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public static JsonObject loadData(String name, boolean cacheIfEnabled) {
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

    public static void mapIdentifiers(Int2IntBiMap output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
            int value = MappingDataLoader.mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
            if (value == -1) continue;
            output.put(Integer.parseInt(entry.getKey()), value);
        }
    }

    public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers) {
        MappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, null);
    }

    public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
            int value = MappingDataLoader.mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
            if (value == -1) continue;
            output[Integer.parseInt((String)entry.getKey())] = (short)value;
        }
    }

    private static int mapIdentifierEntry(Map.Entry<String, JsonElement> entry, Object2IntMap newIdentifierMap, @Nullable JsonObject diffIdentifiers) {
        int value = newIdentifierMap.getInt(entry.getValue().getAsString());
        if (value == -1) {
            JsonElement diffElement;
            if (diffIdentifiers != null && (diffElement = diffIdentifiers.get(entry.getKey())) != null) {
                value = newIdentifierMap.getInt(diffElement.getAsString());
            }
            if (value == -1) {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                }
                return -1;
            }
        }
        return value;
    }

    public static void mapIdentifiers(short[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, boolean warnOnMissing) {
        MappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, null, warnOnMissing);
    }

    public static void mapIdentifiers(short[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.arrayToMap(newIdentifiers);
        for (int i = 0; i < oldIdentifiers.size(); ++i) {
            JsonElement oldIdentifier = oldIdentifiers.get(i);
            int mappedId = newIdentifierMap.getInt(oldIdentifier.getAsString());
            if (mappedId == -1) {
                JsonElement diffElement;
                if (diffIdentifiers != null && (diffElement = diffIdentifiers.get(oldIdentifier.getAsString())) != null) {
                    String mappedName = diffElement.getAsString();
                    if (mappedName.isEmpty()) continue;
                    mappedId = newIdentifierMap.getInt(mappedName);
                }
                if (mappedId == -1) {
                    if ((!warnOnMissing || Via.getConfig().isSuppressConversionWarnings()) && !Via.getManager().isDebug()) continue;
                    Via.getPlatform().getLogger().warning("No key for " + oldIdentifier + " :( ");
                    continue;
                }
            }
            output[i] = (short)mappedId;
        }
    }

    public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
        Object2IntOpenHashMap<String> map = new Object2IntOpenHashMap<String>(object.size(), 1.0f);
        map.defaultReturnValue(-1);
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            map.put(entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
        }
        return map;
    }

    public static Object2IntMap<String> arrayToMap(JsonArray array) {
        Object2IntOpenHashMap<String> map = new Object2IntOpenHashMap<String>(array.size(), 1.0f);
        map.defaultReturnValue(-1);
        for (int i = 0; i < array.size(); ++i) {
            map.put(array.get(i).getAsString(), i);
        }
        return map;
    }

    @Nullable
    public static InputStream getResource(String name) {
        return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
    }
}

