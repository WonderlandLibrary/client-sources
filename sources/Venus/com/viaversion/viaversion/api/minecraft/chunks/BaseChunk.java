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

public class BaseChunk
implements Chunk {
    protected final int x;
    protected final int z;
    protected final boolean fullChunk;
    protected boolean ignoreOldLightData;
    protected BitSet chunkSectionBitSet;
    protected int bitmask;
    protected ChunkSection[] sections;
    protected int[] biomeData;
    protected CompoundTag heightMap;
    protected final List<CompoundTag> blockEntities;

    public BaseChunk(int n, int n2, boolean bl, boolean bl2, @Nullable BitSet bitSet, ChunkSection[] chunkSectionArray, int @Nullable [] nArray, @Nullable CompoundTag compoundTag, List<CompoundTag> list) {
        this.x = n;
        this.z = n2;
        this.fullChunk = bl;
        this.ignoreOldLightData = bl2;
        this.chunkSectionBitSet = bitSet;
        this.sections = chunkSectionArray;
        this.biomeData = nArray;
        this.heightMap = compoundTag;
        this.blockEntities = list;
    }

    public BaseChunk(int n, int n2, boolean bl, boolean bl2, int n3, ChunkSection[] chunkSectionArray, int[] nArray, CompoundTag compoundTag, List<CompoundTag> list) {
        this(n, n2, bl, bl2, null, chunkSectionArray, nArray, compoundTag, list);
        this.bitmask = n3;
    }

    public BaseChunk(int n, int n2, boolean bl, boolean bl2, int n3, ChunkSection[] chunkSectionArray, int[] nArray, List<CompoundTag> list) {
        this(n, n2, bl, bl2, n3, chunkSectionArray, nArray, null, list);
    }

    @Override
    public boolean isBiomeData() {
        return this.biomeData != null;
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
        return this.fullChunk;
    }

    @Override
    public boolean isIgnoreOldLightData() {
        return this.ignoreOldLightData;
    }

    @Override
    public void setIgnoreOldLightData(boolean bl) {
        this.ignoreOldLightData = bl;
    }

    @Override
    public int getBitmask() {
        return this.bitmask;
    }

    @Override
    public void setBitmask(int n) {
        this.bitmask = n;
    }

    @Override
    public @Nullable BitSet getChunkMask() {
        return this.chunkSectionBitSet;
    }

    @Override
    public void setChunkMask(BitSet bitSet) {
        this.chunkSectionBitSet = bitSet;
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
        return this.biomeData;
    }

    @Override
    public void setBiomeData(int @Nullable [] nArray) {
        this.biomeData = nArray;
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
        return this.blockEntities;
    }

    @Override
    public List<BlockEntity> blockEntities() {
        throw new UnsupportedOperationException();
    }
}

