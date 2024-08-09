/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class ReduceNoiseFilter
extends WholeImageFilter {
    private int smooth(int[] nArray) {
        int n = 0;
        int n2 = 0;
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        for (int i = 0; i < 9; ++i) {
            if (i == 4) continue;
            if (nArray[i] < n3) {
                n3 = nArray[i];
                n = i;
            }
            if (nArray[i] <= n4) continue;
            n4 = nArray[i];
            n2 = i;
        }
        if (nArray[4] < n3) {
            return nArray[n];
        }
        if (nArray[4] > n4) {
            return nArray[n2];
        }
        return nArray[4];
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[9];
        int[] nArray3 = new int[9];
        int[] nArray4 = new int[9];
        int[] nArray5 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = 0;
                int n5 = nArray[n3];
                int n6 = n5 >> 16 & 0xFF;
                int n7 = n5 >> 8 & 0xFF;
                int n8 = n5 & 0xFF;
                for (int k = -1; k <= 1; ++k) {
                    int n9;
                    int n10 = i + k;
                    if (0 <= n10 && n10 < n2) {
                        n9 = n10 * n;
                        for (int i2 = -1; i2 <= 1; ++i2) {
                            int n11 = j + i2;
                            if (0 <= n11 && n11 < n) {
                                int n12 = nArray[n9 + n11];
                                nArray2[n4] = n12 >> 16 & 0xFF;
                                nArray3[n4] = n12 >> 8 & 0xFF;
                                nArray4[n4] = n12 & 0xFF;
                            } else {
                                nArray2[n4] = n6;
                                nArray3[n4] = n7;
                                nArray4[n4] = n8;
                            }
                            ++n4;
                        }
                        continue;
                    }
                    for (n9 = -1; n9 <= 1; ++n9) {
                        nArray2[n4] = n6;
                        nArray3[n4] = n7;
                        nArray4[n4] = n8;
                        ++n4;
                    }
                }
                nArray5[n3] = nArray[n3] & 0xFF000000 | this.smooth(nArray2) << 16 | this.smooth(nArray3) << 8 | this.smooth(nArray4);
                ++n3;
            }
        }
        return nArray5;
    }

    public String toString() {
        return "Blur/Smooth";
    }
}

