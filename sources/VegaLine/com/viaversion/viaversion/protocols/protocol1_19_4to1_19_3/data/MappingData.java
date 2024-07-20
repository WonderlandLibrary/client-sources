/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data;

import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.io.IOException;

public final class MappingData
extends MappingDataBase {
    private CompoundTag damageTypesRegistry;

    public MappingData() {
        super("1.19.3", "1.19.4");
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        try {
            this.damageTypesRegistry = BinaryTagIO.readInputStream(MappingDataLoader.getResource("damage-types-1.19.4.nbt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompoundTag damageTypesRegistry() {
        return this.damageTypesRegistry.clone();
    }
}

