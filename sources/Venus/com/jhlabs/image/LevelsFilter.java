/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Histogram;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class LevelsFilter
extends WholeImageFilter {
    private int[][] lut;
    private float lowLevel = 0.0f;
    private float highLevel = 1.0f;
    private float lowOutputLevel = 0.0f;
    private float highOutputLevel = 1.0f;

    public void setLowLevel(float f) {
        this.lowLevel = f;
    }

    public float getLowLevel() {
        return this.lowLevel;
    }

    public void setHighLevel(float f) {
        this.highLevel = f;
    }

    public float getHighLevel() {
        return this.highLevel;
    }

    public void setLowOutputLevel(float f) {
        this.lowOutputLevel = f;
    }

    public float getLowOutputLevel() {
        return this.lowOutputLevel;
    }

    public void setHighOutputLevel(float f) {
        this.highOutputLevel = f;
    }

    public float getHighOutputLevel() {
        return this.highOutputLevel;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        Histogram histogram = new Histogram(nArray, n, n2, 0, n);
        if (histogram.getNumSamples() > 0) {
            float f = 255.0f / (float)histogram.getNumSamples();
            this.lut = new int[3][256];
            float f2 = this.lowLevel * 255.0f;
            float f3 = this.highLevel * 255.0f;
            if (f2 == f3) {
                f3 += 1.0f;
            }
            for (n3 = 0; n3 < 3; ++n3) {
                for (int i = 0; i < 256; ++i) {
                    this.lut[n3][i] = PixelUtils.clamp((int)(255.0f * (this.lowOutputLevel + (this.highOutputLevel - this.lowOutputLevel) * ((float)i - f2) / (f3 - f2))));
                }
            }
        } else {
            this.lut = null;
        }
        n3 = 0;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                nArray[n3] = this.filterRGB(j, i, nArray[n3]);
                ++n3;
            }
        }
        this.lut = null;
        return nArray;
    }

    public int filterRGB(int n, int n2, int n3) {
        if (this.lut != null) {
            int n4 = n3 & 0xFF000000;
            int n5 = this.lut[0][n3 >> 16 & 0xFF];
            int n6 = this.lut[1][n3 >> 8 & 0xFF];
            int n7 = this.lut[2][n3 & 0xFF];
            return n4 | n5 << 16 | n6 << 8 | n7;
        }
        return n3;
    }

    public String toString() {
        return "Colors/Levels...";
    }
}

