/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MappingData
extends MappingDataBase {
    private final Map<String, CompoundTag> dimensionDataMap = new HashMap<String, CompoundTag>();
    private CompoundTag dimensionRegistry;

    public MappingData() {
        super("1.16", "1.16.2");
    }

    @Override
    public void loadExtras(CompoundTag compoundTag) {
        try {
            this.dimensionRegistry = BinaryTagIO.readInputStream(MappingDataLoader.getResource("dimension-registry-1.16.2.nbt"));
        } catch (IOException iOException) {
            Via.getPlatform().getLogger().severe("Error loading dimension registry:");
            iOException.printStackTrace();
        }
        ListTag listTag = (ListTag)((CompoundTag)this.dimensionRegistry.get("minecraft:dimension_type")).get("value");
        for (Tag tag : listTag) {
            CompoundTag compoundTag2 = (CompoundTag)tag;
            CompoundTag compoundTag3 = new CompoundTag((Map<String, Tag>)((CompoundTag)compoundTag2.get("element")).getValue());
            this.dimensionDataMap.put(((StringTag)compoundTag2.get("name")).getValue(), compoundTag3);
        }
    }

    public Map<String, CompoundTag> getDimensionDataMap() {
        return this.dimensionDataMap;
    }

    public CompoundTag getDimensionRegistry() {
        return this.dimensionRegistry.clone();
    }
}

