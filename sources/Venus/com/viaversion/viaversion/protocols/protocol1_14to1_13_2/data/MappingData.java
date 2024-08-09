/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;

public class MappingData
extends MappingDataBase {
    private IntSet motionBlocking;
    private IntSet nonFullBlocks;

    public MappingData() {
        super("1.13.2", "1.14");
    }

    @Override
    public void loadExtras(CompoundTag compoundTag) {
        CompoundTag compoundTag2 = MappingDataLoader.loadNBT("heightmap-1.14.nbt");
        IntArrayTag intArrayTag = (IntArrayTag)compoundTag2.get("motionBlocking");
        this.motionBlocking = new IntOpenHashSet(intArrayTag.getValue());
        if (Via.getConfig().isNonFullBlockLightFix()) {
            IntArrayTag intArrayTag2 = (IntArrayTag)compoundTag2.get("nonFullBlocks");
            this.nonFullBlocks = new IntOpenHashSet(intArrayTag2.getValue());
        }
    }

    public IntSet getMotionBlocking() {
        return this.motionBlocking;
    }

    public IntSet getNonFullBlocks() {
        return this.nonFullBlocks;
    }
}

