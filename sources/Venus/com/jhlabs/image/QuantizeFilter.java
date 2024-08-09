/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.OctTreeQuantizer;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class QuantizeFilter
extends WholeImageFilter {
    protected static final int[] matrix = new int[]{0, 0, 0, 0, 0, 7, 3, 5, 1};
    private int sum = 16;
    private boolean dither;
    private int numColors = 256;
    private boolean serpentine = true;

    public void setNumColors(int n) {
        this.numColors = Math.min(Math.max(n, 8), 256);
    }

    public int getNumColors() {
        return this.numColors;
    }

    public void setDither(boolean bl) {
        this.dither = bl;
    }

    public boolean getDither() {
        return this.dither;
    }

    public void setSerpentine(boolean bl) {
        this.serpentine = bl;
    }

    public boolean getSerpentine() {
        return this.serpentine;
    }

    public void quantize(int[] nArray, int[] nArray2, int n, int n2, int n3, boolean bl, boolean bl2) {
        int n4 = n * n2;
        OctTreeQuantizer octTreeQuantizer = new OctTreeQuantizer();
        octTreeQuantizer.setup(n3);
        octTreeQuantizer.addPixels(nArray, 0, n4);
        int[] nArray3 = octTreeQuantizer.buildColorTable();
        if (!bl) {
            for (int i = 0; i < n4; ++i) {
                nArray2[i] = nArray3[octTreeQuantizer.getIndexForColor(nArray[i])];
            }
        } else {
            int n5 = 0;
            for (int i = 0; i < n2; ++i) {
                int n6;
                boolean bl3;
                boolean bl4 = bl3 = bl2 && (i & 1) == 1;
                if (bl3) {
                    n5 = i * n + n - 1;
                    n6 = -1;
                } else {
                    n5 = i * n;
                    n6 = 1;
                }
                for (int j = 0; j < n; ++j) {
                    int n7;
                    int n8 = nArray[n5];
                    nArray2[n5] = n7 = nArray3[octTreeQuantizer.getIndexForColor(n8)];
                    int n9 = n8 >> 16 & 0xFF;
                    int n10 = n8 >> 8 & 0xFF;
                    int n11 = n8 & 0xFF;
                    int n12 = n7 >> 16 & 0xFF;
                    int n13 = n7 >> 8 & 0xFF;
                    int n14 = n7 & 0xFF;
                    int n15 = n9 - n12;
                    int n16 = n10 - n13;
                    int n17 = n11 - n14;
                    for (int k = -1; k <= 1; ++k) {
                        int n18 = k + i;
                        if (0 > n18 || n18 >= n2) continue;
                        for (int i2 = -1; i2 <= 1; ++i2) {
                            int n19;
                            int n20 = i2 + j;
                            if (0 > n20 || n20 >= n || (n19 = bl3 ? matrix[(k + 1) * 3 - i2 + 1] : matrix[(k + 1) * 3 + i2 + 1]) == 0) continue;
                            int n21 = bl3 ? n5 - i2 : n5 + i2;
                            n8 = nArray[n21];
                            n9 = n8 >> 16 & 0xFF;
                            n10 = n8 >> 8 & 0xFF;
                            n11 = n8 & 0xFF;
                            nArray[n21] = PixelUtils.clamp(n9 += n15 * n19 / this.sum) << 16 | PixelUtils.clamp(n10 += n16 * n19 / this.sum) << 8 | PixelUtils.clamp(n11 += n17 * n19 / this.sum);
                        }
                    }
                    n5 += n6;
                }
            }
        }
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        this.quantize(nArray, nArray2, n, n2, this.numColors, this.dither, this.serpentine);
        return nArray2;
    }

    public String toString() {
        return "Colors/Quantize...";
    }
}

