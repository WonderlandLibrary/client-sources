/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransferFilter;

public class GammaFilter
extends TransferFilter {
    private float rGamma;
    private float gGamma;
    private float bGamma;

    public GammaFilter() {
        this(1.0f);
    }

    public GammaFilter(float f) {
        this(f, f, f);
    }

    public GammaFilter(float f, float f2, float f3) {
        this.setGamma(f, f2, f3);
    }

    public void setGamma(float f, float f2, float f3) {
        this.rGamma = f;
        this.gGamma = f2;
        this.bGamma = f3;
        this.initialized = false;
    }

    public void setGamma(float f) {
        this.setGamma(f, f, f);
    }

    public float getGamma() {
        return this.rGamma;
    }

    @Override
    protected void initialize() {
        this.rTable = this.makeTable(this.rGamma);
        this.gTable = this.gGamma == this.rGamma ? this.rTable : this.makeTable(this.gGamma);
        this.bTable = this.bGamma == this.rGamma ? this.rTable : (this.bGamma == this.gGamma ? this.gTable : this.makeTable(this.bGamma));
    }

    private int[] makeTable(float f) {
        int[] nArray = new int[256];
        for (int i = 0; i < 256; ++i) {
            int n = (int)(255.0 * Math.pow((double)i / 255.0, 1.0 / (double)f) + 0.5);
            if (n > 255) {
                n = 255;
            }
            nArray[i] = n;
        }
        return nArray;
    }

    public String toString() {
        return "Colors/Gamma...";
    }
}

