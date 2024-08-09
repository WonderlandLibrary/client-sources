/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class Flush3DFilter
extends WholeImageFilter {
    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = nArray[i * n + j];
                if (n4 != -16777216 && i > 0 && j > 0) {
                    int n5 = 0;
                    if (nArray[i * n + j - 1] == -16777216) {
                        ++n5;
                    }
                    if (nArray[(i - 1) * n + j] == -16777216) {
                        ++n5;
                    }
                    if (nArray[(i - 1) * n + j - 1] == -16777216) {
                        ++n5;
                    }
                    if (n5 >= 2) {
                        n4 = -1;
                    }
                }
                nArray2[n3++] = n4;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Stylize/Flush 3D...";
    }
}

