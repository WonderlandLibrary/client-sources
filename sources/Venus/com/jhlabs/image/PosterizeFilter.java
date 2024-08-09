/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class PosterizeFilter
extends PointFilter {
    private int numLevels;
    private int[] levels;
    private boolean initialized = false;

    public PosterizeFilter() {
        this.setNumLevels(6);
    }

    public void setNumLevels(int n) {
        this.numLevels = n;
        this.initialized = false;
    }

    public int getNumLevels() {
        return this.numLevels;
    }

    protected void initialize() {
        this.levels = new int[256];
        if (this.numLevels != 1) {
            for (int i = 0; i < 256; ++i) {
                this.levels[i] = 255 * (this.numLevels * i / 256) / (this.numLevels - 1);
            }
        }
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        if (!this.initialized) {
            this.initialized = true;
            this.initialize();
        }
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n5 = this.levels[n5];
        n6 = this.levels[n6];
        n7 = this.levels[n7];
        return n4 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Colors/Posterize...";
    }
}

