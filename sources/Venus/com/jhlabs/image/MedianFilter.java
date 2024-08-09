/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class MedianFilter
extends WholeImageFilter {
    private int median(int[] nArray) {
        int n;
        int n2;
        for (n2 = 0; n2 < 4; ++n2) {
            n = 0;
            int n3 = 0;
            for (int i = 0; i < 9; ++i) {
                if (nArray[i] <= n) continue;
                n = nArray[i];
                n3 = i;
            }
            nArray[n3] = 0;
        }
        n = 0;
        for (n2 = 0; n2 < 9; ++n2) {
            if (nArray[n2] <= n) continue;
            n = nArray[n2];
        }
        return n;
    }

    private int rgbMedian(int[] nArray, int[] nArray2, int[] nArray3) {
        int n = 0;
        int n2 = Integer.MAX_VALUE;
        for (int i = 0; i < 9; ++i) {
            int n3 = 0;
            for (int j = 0; j < 9; ++j) {
                n3 += Math.abs(nArray[i] - nArray[j]);
                n3 += Math.abs(nArray2[i] - nArray2[j]);
                n3 += Math.abs(nArray3[i] - nArray3[j]);
            }
            if (n3 >= n2) continue;
            n2 = n3;
            n = i;
        }
        return n;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[9];
        int[] nArray3 = new int[9];
        int[] nArray4 = new int[9];
        int[] nArray5 = new int[9];
        int[] nArray6 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = 0;
                for (int k = -1; k <= 1; ++k) {
                    int n5 = i + k;
                    if (0 > n5 || n5 >= n2) continue;
                    int n6 = n5 * n;
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        int n7;
                        int n8 = j + i2;
                        if (0 > n8 || n8 >= n) continue;
                        nArray2[n4] = n7 = nArray[n6 + n8];
                        nArray3[n4] = n7 >> 16 & 0xFF;
                        nArray4[n4] = n7 >> 8 & 0xFF;
                        nArray5[n4] = n7 & 0xFF;
                        ++n4;
                    }
                }
                while (n4 < 9) {
                    nArray2[n4] = -16777216;
                    nArray5[n4] = 0;
                    nArray4[n4] = 0;
                    nArray3[n4] = 0;
                    ++n4;
                }
                nArray6[n3++] = nArray2[this.rgbMedian(nArray3, nArray4, nArray5)];
            }
        }
        return nArray6;
    }

    public String toString() {
        return "Blur/Median";
    }
}

