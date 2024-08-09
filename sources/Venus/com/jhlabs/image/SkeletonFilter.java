/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class SkeletonFilter
extends BinaryFilter {
    private static final byte[] skeletonTable = new byte[]{0, 0, 0, 1, 0, 0, 1, 3, 0, 0, 3, 1, 1, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 3, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 3, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 1, 3, 1, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 1, 3, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 1, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0};

    public SkeletonFilter() {
        this.newColor = -1;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        int n3 = 0;
        int n4 = -16777216;
        int n5 = -1;
        for (int i = 0; i < this.iterations; ++i) {
            n3 = 0;
            for (int j = 0; j < 2; ++j) {
                for (int k = 1; k < n2 - 1; ++k) {
                    int n6 = k * n + 1;
                    for (int i2 = 1; i2 < n - 1; ++i2) {
                        int n7 = nArray[n6];
                        if (n7 == n4) {
                            int n8 = 0;
                            if (nArray[n6 - n - 1] == n4) {
                                n8 |= 1;
                            }
                            if (nArray[n6 - n] == n4) {
                                n8 |= 2;
                            }
                            if (nArray[n6 - n + 1] == n4) {
                                n8 |= 4;
                            }
                            if (nArray[n6 + 1] == n4) {
                                n8 |= 8;
                            }
                            if (nArray[n6 + n + 1] == n4) {
                                n8 |= 0x10;
                            }
                            if (nArray[n6 + n] == n4) {
                                n8 |= 0x20;
                            }
                            if (nArray[n6 + n - 1] == n4) {
                                n8 |= 0x40;
                            }
                            if (nArray[n6 - 1] == n4) {
                                n8 |= 0x80;
                            }
                            byte by = skeletonTable[n8];
                            if (j == 1) {
                                if (by == 2 || by == 3) {
                                    n7 = this.colormap != null ? this.colormap.getColor((float)i / (float)this.iterations) : this.newColor;
                                    ++n3;
                                }
                            } else if (by == 1 || by == 3) {
                                n7 = this.colormap != null ? this.colormap.getColor((float)i / (float)this.iterations) : this.newColor;
                                ++n3;
                            }
                        }
                        nArray2[n6++] = n7;
                    }
                }
                if (j != 0) continue;
                nArray = nArray2;
                nArray2 = new int[n * n2];
            }
            if (n3 == 0) break;
        }
        return nArray2;
    }

    public String toString() {
        return "Binary/Skeletonize...";
    }
}

