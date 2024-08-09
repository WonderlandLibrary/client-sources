/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class FillFilter
extends PointFilter {
    private int fillColor;

    public FillFilter() {
        this(-16777216);
    }

    public FillFilter(int n) {
        this.fillColor = n;
    }

    public void setFillColor(int n) {
        this.fillColor = n;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        return this.fillColor;
    }
}

