/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class ErodeFilter
extends BinaryFilter {
    private int threshold = 2;

    public ErodeFilter() {
        this.newColor = -1;
    }

    public void setThreshold(int n) {
        this.threshold = n;
    }

    public int getThreshold() {
        return this.threshold;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < this.iterations; ++i) {
            int n3 = 0;
            if (i > 0) {
                int[] nArray3 = nArray;
                nArray = nArray2;
                nArray2 = nArray3;
            }
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n; ++k) {
                    int n4 = nArray[j * n + k];
                    if (this.blackFunction.isBlack(n4)) {
                        int n5 = 0;
                        for (int i2 = -1; i2 <= 1; ++i2) {
                            int n6 = j + i2;
                            if (0 > n6 || n6 >= n2) continue;
                            int n7 = n6 * n;
                            for (int i3 = -1; i3 <= 1; ++i3) {
                                int n8;
                                int n9 = k + i3;
                                if (i2 == 0 && i3 == 0 || 0 > n9 || n9 >= n || this.blackFunction.isBlack(n8 = nArray[n7 + n9])) continue;
                                ++n5;
                            }
                        }
                        if (n5 >= this.threshold) {
                            n4 = this.colormap != null ? this.colormap.getColor((float)i / (float)this.iterations) : this.newColor;
                        }
                    }
                    nArray2[n3++] = n4;
                }
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Binary/Erode...";
    }
}

