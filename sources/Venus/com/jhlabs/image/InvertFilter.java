/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class InvertFilter
extends PointFilter {
    public InvertFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        return n4 | ~n3 & 0xFFFFFF;
    }

    public String toString() {
        return "Colors/Invert";
    }
}

