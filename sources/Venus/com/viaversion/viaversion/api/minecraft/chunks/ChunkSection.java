/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ChunkSection {
    public static final int SIZE = 4096;
    public static final int BIOME_SIZE = 64;

    public static int index(int n, int n2, int n3) {
        return n2 << 8 | n3 << 4 | n;
    }

    public static int xFromIndex(int n) {
        return n & 0xF;
    }

    public static int yFromIndex(int n) {
        return n >> 8 & 0xF;
    }

    public static int zFromIndex(int n) {
        return n >> 4 & 0xF;
    }

    @Deprecated
    default public int getFlatBlock(int n) {
        return this.palette(PaletteType.BLOCKS).idAt(n);
    }

    @Deprecated
    default public int getFlatBlock(int n, int n2, int n3) {
        return this.getFlatBlock(ChunkSection.index(n, n2, n3));
    }

    @Deprecated
    default public void setFlatBlock(int n, int n2) {
        this.palette(PaletteType.BLOCKS).setIdAt(n, n2);
    }

    @Deprecated
    default public void setFlatBlock(int n, int n2, int n3, int n4) {
        this.setFlatBlock(ChunkSection.index(n, n2, n3), n4);
    }

    @Deprecated
    default public int getBlockWithoutData(int n, int n2, int n3) {
        return this.getFlatBlock(n, n2, n3) >> 4;
    }

    @Deprecated
    default public int getBlockData(int n, int n2, int n3) {
        return this.getFlatBlock(n, n2, n3) & 0xF;
    }

    @Deprecated
    default public void setBlockWithData(int n, int n2, int n3, int n4, int n5) {
        this.setFlatBlock(ChunkSection.index(n, n2, n3), n4 << 4 | n5 & 0xF);
    }

    @Deprecated
    default public void setBlockWithData(int n, int n2, int n3) {
        this.setFlatBlock(n, n2 << 4 | n3 & 0xF);
    }

    @Deprecated
    default public void setPaletteIndex(int n, int n2) {
        this.palette(PaletteType.BLOCKS).setPaletteIndexAt(n, n2);
    }

    @Deprecated
    default public int getPaletteIndex(int n) {
        return this.palette(PaletteType.BLOCKS).paletteIndexAt(n);
    }

    @Deprecated
    default public int getPaletteSize() {
        return this.palette(PaletteType.BLOCKS).size();
    }

    @Deprecated
    default public int getPaletteEntry(int n) {
        return this.palette(PaletteType.BLOCKS).idByIndex(n);
    }

    @Deprecated
    default public void setPaletteEntry(int n, int n2) {
        this.palette(PaletteType.BLOCKS).setIdByIndex(n, n2);
    }

    @Deprecated
    default public void replacePaletteEntry(int n, int n2) {
        this.palette(PaletteType.BLOCKS).replaceId(n, n2);
    }

    @Deprecated
    default public void addPaletteEntry(int n) {
        this.palette(PaletteType.BLOCKS).addId(n);
    }

    @Deprecated
    default public void clearPalette() {
        this.palette(PaletteType.BLOCKS).clear();
    }

    public int getNonAirBlocksCount();

    public void setNonAirBlocksCount(int var1);

    default public boolean hasLight() {
        return this.getLight() != null;
    }

    public @Nullable ChunkSectionLight getLight();

    public void setLight(@Nullable ChunkSectionLight var1);

    public @Nullable DataPalette palette(PaletteType var1);

    public void addPalette(PaletteType var1, DataPalette var2);

    public void removePalette(PaletteType var1);
}

