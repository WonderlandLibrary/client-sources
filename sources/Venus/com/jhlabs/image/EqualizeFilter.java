/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Histogram;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class EqualizeFilter
extends WholeImageFilter {
    private int[][] lut;

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        Histogram histogram = new Histogram(nArray, n, n2, 0, n);
        if (histogram.getNumSamples() > 0) {
            float f = 255.0f / (float)histogram.getNumSamples();
            this.lut = new int[3][256];
            for (n3 = 0; n3 < 3; ++n3) {
                int n4;
                this.lut[n3][0] = histogram.getFrequency(n3, 0);
                for (n4 = 1; n4 < 256; ++n4) {
                    this.lut[n3][n4] = this.lut[n3][n4 - 1] + histogram.getFrequency(n3, n4);
                }
                for (n4 = 0; n4 < 256; ++n4) {
                    this.lut[n3][n4] = Math.round((float)this.lut[n3][n4] * f);
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

    private int filterRGB(int n, int n2, int n3) {
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
        return "Colors/Equalize";
    }
}

