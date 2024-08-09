/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

public enum PaletteType {
    BLOCKS(4096, 8),
    BIOMES(64, 3);

    private final int size;
    private final int highestBitsPerValue;

    private PaletteType(int n2, int n3) {
        this.size = n2;
        this.highestBitsPerValue = n3;
    }

    public int size() {
        return this.size;
    }

    public int highestBitsPerValue() {
        return this.highestBitsPerValue;
    }
}

