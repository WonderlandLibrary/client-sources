/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.data.MappedItem;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectMap;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntMap;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonIOException;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonPrimitive;
import us.myles.viaversion.libs.gson.JsonSyntaxException;

public class VBMappingDataLoader {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
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
        catch (IOException | JsonIOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
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

    public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers) {
        VBMappingDataLoader.mapIdentifiers(output, oldIdentifiers, newIdentifiers, diffIdentifiers, true);
    }

    public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
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
            output[Integer.parseInt((String)entry.getKey())] = (short)mappedId;
        }
    }

    public static Map<String, String> objectToMap(JsonObject object) {
        HashMap<String, String> mappings = new HashMap<String, String>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String value;
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

    public static Int2ObjectMap<MappedItem> loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping) {
        return VBMappingDataLoader.loadItemMappings(oldMapping, newMapping, diffMapping, false);
    }

    public static Int2ObjectMap<MappedItem> loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping, boolean warnOnMissing) {
        Int2ObjectOpenHashMap<MappedItem> itemMapping = new Int2ObjectOpenHashMap<MappedItem>(diffMapping.size(), 1.0f);
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
        if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) {
            for (Object2IntMap.Entry entry : oldIdenfierMap.object2IntEntrySet()) {
                if (newIdenfierMap.containsKey(entry.getKey()) || itemMapping.containsKey(entry.getIntValue())) continue;
                ViaBackwards.getPlatform().getLogger().warning("No item mapping for " + (String)entry.getKey() + " :( ");
            }
        }
        return itemMapping;
    }
}

