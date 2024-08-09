/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class InvertAlphaFilter
extends PointFilter {
    public InvertAlphaFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        return n3 ^ 0xFF000000;
    }

    public String toString() {
        return "Alpha/Invert";
    }
}

