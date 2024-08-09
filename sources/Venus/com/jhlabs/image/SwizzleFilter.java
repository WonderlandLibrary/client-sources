/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;

public class SwizzleFilter
extends PointFilter {
    private int[] matrix = new int[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    public void setMatrix(int[] nArray) {
        this.matrix = nArray;
    }

    public int[] getMatrix() {
        return this.matrix;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 24 & 0xFF;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n4 = this.matrix[0] * n4 + this.matrix[1] * n5 + this.matrix[2] * n6 + this.matrix[3] * n7 + this.matrix[4] * 255;
        n5 = this.matrix[5] * n4 + this.matrix[6] * n5 + this.matrix[7] * n6 + this.matrix[8] * n7 + this.matrix[9] * 255;
        n6 = this.matrix[10] * n4 + this.matrix[11] * n5 + this.matrix[12] * n6 + this.matrix[13] * n7 + this.matrix[14] * 255;
        n7 = this.matrix[15] * n4 + this.matrix[16] * n5 + this.matrix[17] * n6 + this.matrix[18] * n7 + this.matrix[19] * 255;
        n4 = PixelUtils.clamp(n4);
        n5 = PixelUtils.clamp(n5);
        n6 = PixelUtils.clamp(n6);
        n7 = PixelUtils.clamp(n7);
        return n4 << 24 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Channels/Swizzle...";
    }
}

