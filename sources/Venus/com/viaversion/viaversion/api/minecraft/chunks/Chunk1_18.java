/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Chunk1_18
implements Chunk {
    protected final int x;
    protected final int z;
    protected ChunkSection[] sections;
    protected CompoundTag heightMap;
    protected final List<BlockEntity> blockEntities;

    public Chunk1_18(int n, int n2, ChunkSection[] chunkSectionArray, CompoundTag compoundTag, List<BlockEntity> list) {
        this.x = n;
        this.z = n2;
        this.sections = chunkSectionArray;
        this.heightMap = compoundTag;
        this.blockEntities = list;
    }

    @Override
    public boolean isBiomeData() {
        return true;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public boolean isFullChunk() {
        return false;
    }

    @Override
    public boolean isIgnoreOldLightData() {
        return true;
    }

    @Override
    public void setIgnoreOldLightData(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBitmask() {
        return 1;
    }

    @Override
    public void setBitmask(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable BitSet getChunkMask() {
        return null;
    }

    @Override
    public void setChunkMask(BitSet bitSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Override
    public void setSections(ChunkSection[] chunkSectionArray) {
        this.sections = chunkSectionArray;
    }

    @Override
    public int @Nullable [] getBiomeData() {
        return null;
    }

    @Override
    public void setBiomeData(int @Nullable [] nArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable CompoundTag getHeightMap() {
        return this.heightMap;
    }

    @Override
    public void setHeightMap(CompoundTag compoundTag) {
        this.heightMap = compoundTag;
    }

    @Override
    public List<CompoundTag> getBlockEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BlockEntity> blockEntities() {
        return this.blockEntities;
    }
}

