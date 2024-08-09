/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class GrayscaleFilter
extends PointFilter {
    public GrayscaleFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n3 = n5 * 77 + n6 * 151 + n7 * 28 >> 8;
        return n4 | n3 << 16 | n3 << 8 | n3;
    }

    public String toString() {
        return "Colors/Grayscale";
    }
}

