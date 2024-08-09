/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class LifeFilter
extends BinaryFilter {
    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                int n4 = nArray[i * n + j];
                int n5 = n4 & 0xFF000000;
                int n6 = 0;
                for (int k = -1; k <= 1; ++k) {
                    int n7 = i + k;
                    if (0 > n7 || n7 >= n2) continue;
                    int n8 = n7 * n;
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        int n9;
                        int n10 = j + i2;
                        if (k == 0 && i2 == 0 || 0 > n10 || n10 >= n || !this.blackFunction.isBlack(n9 = nArray[n8 + n10])) continue;
                        ++n6;
                    }
                }
                if (this.blackFunction.isBlack(n4)) {
                    nArray2[n3++] = n6 == 2 || n6 == 3 ? n4 : -1;
                    continue;
                }
                nArray2[n3++] = n6 == 3 ? -16777216 : n4;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Binary/Life";
    }
}

