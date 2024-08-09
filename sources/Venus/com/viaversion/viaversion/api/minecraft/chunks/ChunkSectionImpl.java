/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLightImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import java.util.EnumMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ChunkSectionImpl
implements ChunkSection {
    private final EnumMap<PaletteType, DataPalette> palettes = new EnumMap(PaletteType.class);
    private ChunkSectionLight light;
    private int nonAirBlocksCount;

    public ChunkSectionImpl() {
    }

    public ChunkSectionImpl(boolean bl) {
        this.addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096));
        if (bl) {
            this.light = new ChunkSectionLightImpl();
        }
    }

    public ChunkSectionImpl(boolean bl, int n) {
        this.addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096, n));
        if (bl) {
            this.light = new ChunkSectionLightImpl();
        }
    }

    @Override
    public int getNonAirBlocksCount() {
        return this.nonAirBlocksCount;
    }

    @Override
    public void setNonAirBlocksCount(int n) {
        this.nonAirBlocksCount = n;
    }

    @Override
    public @Nullable ChunkSectionLight getLight() {
        return this.light;
    }

    @Override
    public void setLight(@Nullable ChunkSectionLight chunkSectionLight) {
        this.light = chunkSectionLight;
    }

    @Override
    public DataPalette palette(PaletteType paletteType) {
        return this.palettes.get((Object)paletteType);
    }

    @Override
    public void addPalette(PaletteType paletteType, DataPalette dataPalette) {
        this.palettes.put(paletteType, dataPalette);
    }

    @Override
    public void removePalette(PaletteType paletteType) {
        this.palettes.remove((Object)paletteType);
    }
}

