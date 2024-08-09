/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class OutlineFilter
extends BinaryFilter {
    public OutlineFilter() {
        this.newColor = -1;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = nArray[i * n + j];
                if (this.blackFunction.isBlack(n4)) {
                    int n5 = 0;
                    for (int k = -1; k <= 1; ++k) {
                        int n6 = i + k;
                        if (0 > n6 || n6 >= n2) continue;
                        int n7 = n6 * n;
                        for (int i2 = -1; i2 <= 1; ++i2) {
                            int n8 = j + i2;
                            if ((k != 0 || i2 != 0) && 0 <= n8 && n8 < n) {
                                int n9 = nArray[n7 + n8];
                                if (!this.blackFunction.isBlack(n9)) continue;
                                ++n5;
                                continue;
                            }
                            ++n5;
                        }
                    }
                    if (n5 == 9) {
                        n4 = this.newColor;
                    }
                }
                nArray2[n3++] = n4;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Binary/Outline...";
    }
}

