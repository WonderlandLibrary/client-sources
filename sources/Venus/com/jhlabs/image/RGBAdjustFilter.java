/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;

public class RGBAdjustFilter
extends PointFilter {
    public float rFactor;
    public float gFactor;
    public float bFactor;

    public RGBAdjustFilter() {
        this(0.0f, 0.0f, 0.0f);
    }

    public RGBAdjustFilter(float f, float f2, float f3) {
        this.rFactor = 1.0f + f;
        this.gFactor = 1.0f + f2;
        this.bFactor = 1.0f + f3;
        this.canFilterIndexColorModel = true;
    }

    public void setRFactor(float f) {
        this.rFactor = 1.0f + f;
    }

    public float getRFactor() {
        return this.rFactor - 1.0f;
    }

    public void setGFactor(float f) {
        this.gFactor = 1.0f + f;
    }

    public float getGFactor() {
        return this.gFactor - 1.0f;
    }

    public void setBFactor(float f) {
        this.bFactor = 1.0f + f;
    }

    public float getBFactor() {
        return this.bFactor - 1.0f;
    }

    public int[] getLUT() {
        int[] nArray = new int[256];
        for (int i = 0; i < 256; ++i) {
            nArray[i] = this.filterRGB(0, 0, i << 24 | i << 16 | i << 8 | i);
        }
        return nArray;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n5 = PixelUtils.clamp((int)((float)n5 * this.rFactor));
        n6 = PixelUtils.clamp((int)((float)n6 * this.gFactor));
        n7 = PixelUtils.clamp((int)((float)n7 * this.bFactor));
        return n4 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Colors/Adjust RGB...";
    }
}

