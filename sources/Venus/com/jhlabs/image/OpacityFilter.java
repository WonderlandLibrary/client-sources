/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class OpacityFilter
extends PointFilter {
    private int opacity;
    private int opacity24;

    public OpacityFilter() {
        this(136);
    }

    public OpacityFilter(int n) {
        this.setOpacity(n);
    }

    public void setOpacity(int n) {
        this.opacity = n;
        this.opacity24 = n << 24;
    }

    public int getOpacity() {
        return this.opacity;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        if ((n3 & 0xFF000000) != 0) {
            return n3 & 0xFFFFFF | this.opacity24;
        }
        return n3;
    }

    public String toString() {
        return "Colors/Transparency...";
    }
}

