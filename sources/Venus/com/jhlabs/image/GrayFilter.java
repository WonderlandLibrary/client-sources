/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class GrayFilter
extends PointFilter {
    public GrayFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n5 = (n5 + 255) / 2;
        n6 = (n6 + 255) / 2;
        n7 = (n7 + 255) / 2;
        return n4 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Colors/Gray Out";
    }
}

