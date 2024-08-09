/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.FullMappingsBase;
import com.viaversion.viaversion.api.data.IdentityMappings;
import com.viaversion.viaversion.api.data.IntArrayMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.opennbt.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappingDataLoader {
    private static final byte DIRECT_ID = 0;
    private static final byte SHIFTS_ID = 1;
    private static final byte CHANGES_ID = 2;
    private static final byte IDENTITY_ID = 3;
    private static final Map<String, CompoundTag> MAPPINGS_CACHE = new HashMap<String, CompoundTag>();
    private static boolean cacheValid = true;

    @Deprecated
    public static void enableMappingsCache() {
    }

    public static void clearCache() {
        MAPPINGS_CACHE.clear();
        cacheValid = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static @Nullable JsonObject loadFromDataDir(String string) {
        File file = new File(Via.getPlatform().getDataFolder(), string);
        if (!file.exists()) {
            return MappingDataLoader.loadData(string);
        }
        try (FileReader fileReader = new FileReader(file);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)fileReader, JsonObject.class);
            return jsonObject;
        } catch (JsonSyntaxException jsonSyntaxException) {
            Via.getPlatform().getLogger().warning(string + " is badly formatted!");
            throw new RuntimeException(jsonSyntaxException);
        } catch (JsonIOException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static @Nullable JsonObject loadData(String string) {
        InputStream inputStream = MappingDataLoader.getResource(string);
        if (inputStream == null) {
            return null;
        }
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);){
            JsonObject jsonObject = GsonUtil.getGson().fromJson((Reader)inputStreamReader, JsonObject.class);
            return jsonObject;
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static @Nullable CompoundTag loadNBT(String string, boolean bl) {
        if (!cacheValid) {
            return MappingDataLoader.loadNBTFromFile(string);
        }
        CompoundTag compoundTag = MAPPINGS_CACHE.get(string);
        if (compoundTag != null) {
            return compoundTag;
        }
        compoundTag = MappingDataLoader.loadNBTFromFile(string);
        if (bl && compoundTag != null) {
            MAPPINGS_CACHE.put(string, compoundTag);
        }
        return compoundTag;
    }

    public static @Nullable CompoundTag loadNBT(String string) {
        return MappingDataLoader.loadNBT(string, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static @Nullable CompoundTag loadNBTFromFile(String string) {
        InputStream inputStream = MappingDataLoader.getResource(string);
        if (inputStream == null) {
            return null;
        }
        try (InputStream inputStream2 = inputStream;){
            CompoundTag compoundTag = NBTIO.readTag(inputStream2);
            return compoundTag;
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static @Nullable Mappings loadMappings(CompoundTag compoundTag, String string) {
        return MappingDataLoader.loadMappings(compoundTag, string, MappingDataLoader::lambda$loadMappings$0, MappingDataLoader::lambda$loadMappings$1, IntArrayMappings::of);
    }

    @Beta
    public static <M extends Mappings, V> @Nullable Mappings loadMappings(CompoundTag compoundTag, String string, MappingHolderSupplier<V> mappingHolderSupplier, AddConsumer<V> addConsumer, MappingsSupplier<M, V> mappingsSupplier) {
        V v;
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get(string);
        if (compoundTag2 == null) {
            return null;
        }
        ByteTag byteTag = (ByteTag)compoundTag2.get("id");
        IntTag intTag = (IntTag)compoundTag2.get("mappedSize");
        byte by = byteTag.asByte();
        if (by == 0) {
            IntArrayTag intArrayTag = (IntArrayTag)compoundTag2.get("val");
            return IntArrayMappings.of(intArrayTag.getValue(), intTag.asInt());
        }
        if (by == 1) {
            int n;
            int n2;
            IntArrayTag intArrayTag = (IntArrayTag)compoundTag2.get("at");
            IntArrayTag intArrayTag2 = (IntArrayTag)compoundTag2.get("to");
            IntTag intTag2 = (IntTag)compoundTag2.get("size");
            int[] nArray = intArrayTag.getValue();
            int[] nArray2 = intArrayTag2.getValue();
            int n3 = intTag2.asInt();
            v = mappingHolderSupplier.get(n3);
            if (nArray[0] != 0) {
                n2 = nArray[0];
                for (n = 0; n < n2; ++n) {
                    addConsumer.addTo(v, n, n);
                }
            }
            for (n2 = 0; n2 < nArray.length; ++n2) {
                n = nArray[n2];
                int n4 = n2 == nArray.length - 1 ? n3 : nArray[n2 + 1];
                int n5 = nArray2[n2];
                for (int i = n; i < n4; ++i) {
                    addConsumer.addTo(v, i, n5++);
                }
            }
        } else if (by == 2) {
            IntArrayTag intArrayTag = (IntArrayTag)compoundTag2.get("at");
            IntArrayTag intArrayTag3 = (IntArrayTag)compoundTag2.get("val");
            IntTag intTag3 = (IntTag)compoundTag2.get("size");
            boolean bl = compoundTag2.get("nofill") == null;
            int[] nArray = intArrayTag.getValue();
            int[] nArray3 = intArrayTag3.getValue();
            v = mappingHolderSupplier.get(intTag3.asInt());
            for (int i = 0; i < nArray.length; ++i) {
                int n = nArray[i];
                if (bl) {
                    int n6;
                    for (int j = n6 = i != 0 ? nArray[i - 1] + 1 : 0; j < n; ++j) {
                        addConsumer.addTo(v, j, j);
                    }
                }
                addConsumer.addTo(v, n, nArray3[i]);
            }
        } else {
            if (by == 3) {
                IntTag intTag4 = (IntTag)compoundTag2.get("size");
                return new IdentityMappings(intTag4.asInt(), intTag.asInt());
            }
            throw new IllegalArgumentException("Unknown serialization strategy: " + by);
        }
        return mappingsSupplier.create(v, intTag.asInt());
    }

    public static FullMappings loadFullMappings(CompoundTag compoundTag, CompoundTag compoundTag2, CompoundTag compoundTag3, String string) {
        ListTag listTag = (ListTag)compoundTag2.get(string);
        ListTag listTag2 = (ListTag)compoundTag3.get(string);
        if (listTag == null || listTag2 == null) {
            return null;
        }
        Mappings mappings = MappingDataLoader.loadMappings(compoundTag, string);
        if (mappings == null) {
            mappings = new IdentityMappings(listTag.size(), listTag2.size());
        }
        return new FullMappingsBase(listTag.getValue().stream().map(MappingDataLoader::lambda$loadFullMappings$2).collect(Collectors.toList()), listTag2.getValue().stream().map(MappingDataLoader::lambda$loadFullMappings$3).collect(Collectors.toList()), mappings);
    }

    @Deprecated
    public static void mapIdentifiers(int[] nArray, JsonObject jsonObject, JsonObject jsonObject2, @Nullable JsonObject jsonObject3, boolean bl) {
        Object2IntMap<String> object2IntMap = MappingDataLoader.indexedObjectToMap(jsonObject2);
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            int n = Integer.parseInt(entry.getKey());
            int n2 = MappingDataLoader.mapIdentifierEntry(n, entry.getValue().getAsString(), object2IntMap, jsonObject3, bl);
            if (n2 == -1) continue;
            nArray[n] = n2;
        }
    }

    private static int mapIdentifierEntry(int n, String string, Object2IntMap<String> object2IntMap, @Nullable JsonObject jsonObject, boolean bl) {
        int n2 = object2IntMap.getInt(string);
        if (n2 == -1) {
            JsonElement jsonElement;
            if (jsonObject != null && ((jsonElement = jsonObject.get(string)) != null || (jsonElement = jsonObject.get(Integer.toString(n))) != null)) {
                String string2 = jsonElement.getAsString();
                if (string2.isEmpty()) {
                    return 1;
                }
                n2 = object2IntMap.getInt(string2);
            }
            if (n2 == -1) {
                if (bl && !Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("No key for " + string + " :( ");
                }
                return 1;
            }
        }
        return n2;
    }

    @Deprecated
    public static void mapIdentifiers(int[] nArray, JsonArray jsonArray, JsonArray jsonArray2, @Nullable JsonObject jsonObject, boolean bl) {
        Object2IntMap<String> object2IntMap = MappingDataLoader.arrayToMap(jsonArray2);
        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonElement jsonElement = jsonArray.get(i);
            int n = MappingDataLoader.mapIdentifierEntry(i, jsonElement.getAsString(), object2IntMap, jsonObject, bl);
            if (n == -1) continue;
            nArray[i] = n;
        }
    }

    public static Object2IntMap<String> indexedObjectToMap(JsonObject jsonObject) {
        Object2IntOpenHashMap<String> object2IntOpenHashMap = new Object2IntOpenHashMap<String>(jsonObject.size(), 0.99f);
        object2IntOpenHashMap.defaultReturnValue(-1);
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            object2IntOpenHashMap.put(entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
        }
        return object2IntOpenHashMap;
    }

    public static Object2IntMap<String> arrayToMap(JsonArray jsonArray) {
        Object2IntOpenHashMap<String> object2IntOpenHashMap = new Object2IntOpenHashMap<String>(jsonArray.size(), 0.99f);
        object2IntOpenHashMap.defaultReturnValue(-1);
        for (int i = 0; i < jsonArray.size(); ++i) {
            object2IntOpenHashMap.put(jsonArray.get(i).getAsString(), i);
        }
        return object2IntOpenHashMap;
    }

    public static @Nullable InputStream getResource(String string) {
        return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + string);
    }

    private static String lambda$loadFullMappings$3(Tag tag) {
        return (String)tag.getValue();
    }

    private static String lambda$loadFullMappings$2(Tag tag) {
        return (String)tag.getValue();
    }

    private static void lambda$loadMappings$1(int[] nArray, int n, int n2) {
        nArray[n] = n2;
    }

    private static int[] lambda$loadMappings$0(int n) {
        int[] nArray = new int[n];
        Arrays.fill(nArray, -1);
        return nArray;
    }

    @FunctionalInterface
    public static interface MappingsSupplier<T extends Mappings, V> {
        public T create(V var1, int var2);
    }

    @FunctionalInterface
    public static interface MappingHolderSupplier<T> {
        public T get(int var1);
    }

    @FunctionalInterface
    public static interface AddConsumer<T> {
        public void addTo(T var1, int var2, int var3);
    }
}

