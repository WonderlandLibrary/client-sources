/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class MinimumFilter
extends WholeImageFilter {
    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = -1;
                for (int k = -1; k <= 1; ++k) {
                    int n5 = i + k;
                    if (0 > n5 || n5 >= n2) continue;
                    int n6 = n5 * n;
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        int n7 = j + i2;
                        if (0 > n7 || n7 >= n) continue;
                        n4 = PixelUtils.combinePixels(n4, nArray[n6 + n7], 2);
                    }
                }
                nArray2[n3++] = n4;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Blur/Minimum";
    }
}

