/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class DiffusionFilter
extends WholeImageFilter {
    private static final int[] diffusionMatrix = new int[]{0, 0, 0, 0, 0, 7, 3, 5, 1};
    private int[] matrix;
    private int sum = 16;
    private boolean serpentine = true;
    private boolean colorDither = true;
    private int levels = 6;

    public DiffusionFilter() {
        this.setMatrix(diffusionMatrix);
    }

    public void setSerpentine(boolean bl) {
        this.serpentine = bl;
    }

    public boolean getSerpentine() {
        return this.serpentine;
    }

    public void setColorDither(boolean bl) {
        this.colorDither = bl;
    }

    public boolean getColorDither() {
        return this.colorDither;
    }

    public void setMatrix(int[] nArray) {
        this.matrix = nArray;
        this.sum = 0;
        for (int i = 0; i < nArray.length; ++i) {
            this.sum += nArray[i];
        }
    }

    public int[] getMatrix() {
        return this.matrix;
    }

    public void setLevels(int n) {
        this.levels = n;
    }

    public int getLevels() {
        return this.levels;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        int[] nArray2 = new int[n * n2];
        int n4 = 0;
        int[] nArray3 = new int[this.levels];
        for (int i = 0; i < this.levels; ++i) {
            nArray3[i] = n3 = 255 * i / (this.levels - 1);
        }
        int[] nArray4 = new int[256];
        for (n3 = 0; n3 < 256; ++n3) {
            nArray4[n3] = this.levels * n3 / 256;
        }
        for (n3 = 0; n3 < n2; ++n3) {
            int n5;
            boolean bl;
            boolean bl2 = bl = this.serpentine && (n3 & 1) == 1;
            if (bl) {
                n4 = n3 * n + n - 1;
                n5 = -1;
            } else {
                n4 = n3 * n;
                n5 = 1;
            }
            for (int i = 0; i < n; ++i) {
                int n6 = nArray[n4];
                int n7 = n6 >> 16 & 0xFF;
                int n8 = n6 >> 8 & 0xFF;
                int n9 = n6 & 0xFF;
                if (!this.colorDither) {
                    n8 = n9 = (n7 + n8 + n9) / 3;
                    n7 = n9;
                }
                int n10 = nArray3[nArray4[n7]];
                int n11 = nArray3[nArray4[n8]];
                int n12 = nArray3[nArray4[n9]];
                nArray2[n4] = n6 & 0xFF000000 | n10 << 16 | n11 << 8 | n12;
                int n13 = n7 - n10;
                int n14 = n8 - n11;
                int n15 = n9 - n12;
                for (int j = -1; j <= 1; ++j) {
                    int n16 = j + n3;
                    if (0 > n16 || n16 >= n2) continue;
                    for (int k = -1; k <= 1; ++k) {
                        int n17;
                        int n18 = k + i;
                        if (0 > n18 || n18 >= n || (n17 = bl ? this.matrix[(j + 1) * 3 - k + 1] : this.matrix[(j + 1) * 3 + k + 1]) == 0) continue;
                        int n19 = bl ? n4 - k : n4 + k;
                        n6 = nArray[n19];
                        n7 = n6 >> 16 & 0xFF;
                        n8 = n6 >> 8 & 0xFF;
                        n9 = n6 & 0xFF;
                        nArray[n19] = nArray[n19] & 0xFF000000 | PixelUtils.clamp(n7 += n13 * n17 / this.sum) << 16 | PixelUtils.clamp(n8 += n14 * n17 / this.sum) << 8 | PixelUtils.clamp(n9 += n15 * n17 / this.sum);
                    }
                }
                n4 += n5;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Colors/Diffusion Dither...";
    }
}

