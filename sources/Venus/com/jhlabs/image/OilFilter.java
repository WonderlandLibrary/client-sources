/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class OilFilter
extends WholeImageFilter {
    private int range = 3;
    private int levels = 256;

    public void setRange(int n) {
        this.range = n;
    }

    public int getRange() {
        return this.range;
    }

    public void setLevels(int n) {
        this.levels = n;
    }

    public int getLevels() {
        return this.levels;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[this.levels];
        int[] nArray3 = new int[this.levels];
        int[] nArray4 = new int[this.levels];
        int[] nArray5 = new int[this.levels];
        int[] nArray6 = new int[this.levels];
        int[] nArray7 = new int[this.levels];
        int[] nArray8 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4;
                int n5;
                int n6;
                int n7;
                for (n7 = 0; n7 < this.levels; ++n7) {
                    nArray7[n7] = 0;
                    nArray6[n7] = 0;
                    nArray5[n7] = 0;
                    nArray4[n7] = 0;
                    nArray3[n7] = 0;
                    nArray2[n7] = 0;
                }
                for (n7 = -this.range; n7 <= this.range; ++n7) {
                    n6 = i + n7;
                    if (0 > n6 || n6 >= n2) continue;
                    n5 = n6 * n;
                    for (n4 = -this.range; n4 <= this.range; ++n4) {
                        int n8 = j + n4;
                        if (0 > n8 || n8 >= n) continue;
                        int n9 = nArray[n5 + n8];
                        int n10 = n9 >> 16 & 0xFF;
                        int n11 = n9 >> 8 & 0xFF;
                        int n12 = n9 & 0xFF;
                        int n13 = n10 * this.levels / 256;
                        int n14 = n11 * this.levels / 256;
                        int n15 = n12 * this.levels / 256;
                        int n16 = n13;
                        nArray5[n16] = nArray5[n16] + n10;
                        int n17 = n14;
                        nArray6[n17] = nArray6[n17] + n11;
                        int n18 = n15;
                        nArray7[n18] = nArray7[n18] + n12;
                        int n19 = n13;
                        nArray2[n19] = nArray2[n19] + 1;
                        int n20 = n14;
                        nArray3[n20] = nArray3[n20] + 1;
                        int n21 = n15;
                        nArray4[n21] = nArray4[n21] + 1;
                    }
                }
                n7 = 0;
                n6 = 0;
                n5 = 0;
                for (n4 = 1; n4 < this.levels; ++n4) {
                    if (nArray2[n4] > nArray2[n7]) {
                        n7 = n4;
                    }
                    if (nArray3[n4] > nArray3[n6]) {
                        n6 = n4;
                    }
                    if (nArray4[n4] <= nArray4[n5]) continue;
                    n5 = n4;
                }
                n7 = nArray5[n7] / nArray2[n7];
                n6 = nArray6[n6] / nArray3[n6];
                n5 = nArray7[n5] / nArray4[n5];
                nArray8[n3] = nArray[n3] & 0xFF000000 | n7 << 16 | n6 << 8 | n5;
                ++n3;
            }
        }
        return nArray8;
    }

    public String toString() {
        return "Stylize/Oil...";
    }
}

