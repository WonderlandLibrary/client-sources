/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class MaskFilter
extends PointFilter {
    private int mask;

    public MaskFilter() {
        this(-16711681);
    }

    public MaskFilter(int n) {
        this.canFilterIndexColorModel = true;
        this.setMask(n);
    }

    public void setMask(int n) {
        this.mask = n;
    }

    public int getMask() {
        return this.mask;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        return n3 & this.mask;
    }

    public String toString() {
        return "Mask";
    }
}

