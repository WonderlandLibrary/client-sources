/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class MapColorsFilter
extends PointFilter {
    private int oldColor;
    private int newColor;

    public MapColorsFilter() {
        this(-1, -16777216);
    }

    public MapColorsFilter(int n, int n2) {
        this.canFilterIndexColorModel = true;
        this.oldColor = n;
        this.newColor = n2;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        if (n3 == this.oldColor) {
            return this.newColor;
        }
        return n3;
    }
}

