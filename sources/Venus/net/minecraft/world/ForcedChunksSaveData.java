/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class ForcedChunksSaveData
extends WorldSavedData {
    private LongSet chunks = new LongOpenHashSet();

    public ForcedChunksSaveData() {
        super("chunks");
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        this.chunks = new LongOpenHashSet(compoundNBT.getLongArray("Forced"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putLongArray("Forced", this.chunks.toLongArray());
        return compoundNBT;
    }

    public LongSet getChunks() {
        return this.chunks;
    }
}

