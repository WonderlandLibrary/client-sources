/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Int2IntMapBiMappings;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiHashMap;
import com.viaversion.viaversion.util.Key;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingData
extends MappingDataBase {
    private final Map<String, int[]> blockTags = new HashMap<String, int[]>();
    private final Map<String, int[]> itemTags = new HashMap<String, int[]>();
    private final Map<String, int[]> fluidTags = new HashMap<String, int[]>();
    private final BiMap<Short, String> oldEnchantmentsIds = HashBiMap.create();
    private final Map<String, String> translateMapping = new HashMap<String, String>();
    private final Map<String, String> mojangTranslation = new HashMap<String, String>();
    private final BiMap<String, String> channelMappings = HashBiMap.create();

    public MappingData() {
        super("1.12", "1.13");
    }

    @Override
    protected void loadExtras(CompoundTag compoundTag) {
        String[] stringArray;
        Object object;
        String[] iOException;
        JsonObject jsonObject;
        this.loadTags(this.blockTags, (CompoundTag)compoundTag.get("block_tags"));
        this.loadTags(this.itemTags, (CompoundTag)compoundTag.get("item_tags"));
        this.loadTags(this.fluidTags, (CompoundTag)compoundTag.get("fluid_tags"));
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("legacy_enchantments");
        this.loadEnchantments(this.oldEnchantmentsIds, compoundTag2);
        if (Via.getConfig().isSnowCollisionFix()) {
            this.blockMappings.setNewId(1248, 3416);
        }
        if (Via.getConfig().isInfestedBlocksFix()) {
            this.blockMappings.setNewId(1552, 1);
            this.blockMappings.setNewId(1553, 14);
            this.blockMappings.setNewId(1554, 3983);
            this.blockMappings.setNewId(1555, 3984);
            this.blockMappings.setNewId(1556, 3985);
            this.blockMappings.setNewId(1557, 3986);
        }
        if ((jsonObject = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json")) != null) {
            for (Map.Entry<String, JsonElement> stringArray22 : jsonObject.entrySet()) {
                iOException = stringArray22.getKey();
                object = stringArray22.getValue().getAsString();
                if (!MappingData.isValid1_13Channel((String)object)) {
                    Via.getPlatform().getLogger().warning("Channel '" + (String)object + "' is not a valid 1.13 plugin channel, please check your configuration!");
                    continue;
                }
                this.channelMappings.put((String)iOException, (String)object);
            }
        }
        Map map = (Map)GsonUtil.getGson().fromJson((Reader)new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")), new TypeToken<Map<String, String>>(this){
            final MappingData this$0;
            {
                this.this$0 = mappingData;
            }
        }.getType());
        try {
            iOException = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8);
            object = null;
            try {
                stringArray = CharStreams.toString((Readable)iOException).split("\n");
            } catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            } finally {
                if (iOException != null) {
                    if (object != null) {
                        try {
                            iOException.close();
                        } catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        iOException.close();
                    }
                }
            }
        } catch (IOException iOException2) {
            throw new RuntimeException(iOException2);
        }
        for (String string : stringArray) {
            String[] stringArray2;
            if (string.isEmpty() || (stringArray2 = string.split("=", 2)).length != 2) continue;
            String string2 = stringArray2[0];
            String string3 = stringArray2[5].replaceAll("%(\\d\\$)?d", "%$1s").trim();
            this.mojangTranslation.put(string2, string3);
            if (!map.containsKey(string2)) continue;
            String string4 = (String)map.get(string2);
            this.translateMapping.put(string2, string4 != null ? string4 : string2);
        }
    }

    @Override
    protected @Nullable Mappings loadMappings(CompoundTag compoundTag, String string) {
        if (string.equals("blocks")) {
            return super.loadMappings(compoundTag, "blockstates");
        }
        if (string.equals("blockstates")) {
            return null;
        }
        return super.loadMappings(compoundTag, string);
    }

    @Override
    protected @Nullable BiMappings loadBiMappings(CompoundTag compoundTag, String string) {
        if (string.equals("items")) {
            return (BiMappings)MappingDataLoader.loadMappings(compoundTag, "items", MappingData::lambda$loadBiMappings$0, Int2IntBiHashMap::put, MappingData::lambda$loadBiMappings$1);
        }
        return super.loadBiMappings(compoundTag, string);
    }

    public static String validateNewChannel(String string) {
        if (!MappingData.isValid1_13Channel(string)) {
            return null;
        }
        int n = string.indexOf(58);
        if (n == -1) {
            return "minecraft:" + string;
        }
        if (n == 0) {
            return "minecraft" + string;
        }
        return string;
    }

    public static boolean isValid1_13Channel(String string) {
        return string.matches("([0-9a-z_.-]+:)?[0-9a-z_/.-]+");
    }

    private void loadTags(Map<String, int[]> map, CompoundTag compoundTag) {
        for (Map.Entry<String, Tag> entry : compoundTag.entrySet()) {
            IntArrayTag intArrayTag = (IntArrayTag)entry.getValue();
            map.put(Key.namespaced(entry.getKey()), intArrayTag.getValue());
        }
    }

    private void loadEnchantments(Map<Short, String> map, CompoundTag compoundTag) {
        for (Map.Entry<String, Tag> entry : compoundTag.entrySet()) {
            map.put(Short.parseShort(entry.getKey()), ((StringTag)entry.getValue()).getValue());
        }
    }

    public Map<String, int[]> getBlockTags() {
        return this.blockTags;
    }

    public Map<String, int[]> getItemTags() {
        return this.itemTags;
    }

    public Map<String, int[]> getFluidTags() {
        return this.fluidTags;
    }

    public BiMap<Short, String> getOldEnchantmentsIds() {
        return this.oldEnchantmentsIds;
    }

    public Map<String, String> getTranslateMapping() {
        return this.translateMapping;
    }

    public Map<String, String> getMojangTranslation() {
        return this.mojangTranslation;
    }

    public BiMap<String, String> getChannelMappings() {
        return this.channelMappings;
    }

    private static Int2IntMapBiMappings lambda$loadBiMappings$1(Int2IntBiHashMap int2IntBiHashMap, int n) {
        return Int2IntMapBiMappings.of(int2IntBiHashMap);
    }

    private static Int2IntBiHashMap lambda$loadBiMappings$0(int n) {
        Int2IntBiHashMap int2IntBiHashMap = new Int2IntBiHashMap(n);
        int2IntBiHashMap.defaultReturnValue(-1);
        return int2IntBiHashMap;
    }
}

