/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;
import java.awt.Color;

public class HSBAdjustFilter
extends PointFilter {
    public float hFactor;
    public float sFactor;
    public float bFactor;
    private float[] hsb = new float[3];

    public HSBAdjustFilter() {
        this(0.0f, 0.0f, 0.0f);
    }

    public HSBAdjustFilter(float f, float f2, float f3) {
        this.hFactor = f;
        this.sFactor = f2;
        this.bFactor = f3;
        this.canFilterIndexColorModel = true;
    }

    public void setHFactor(float f) {
        this.hFactor = f;
    }

    public float getHFactor() {
        return this.hFactor;
    }

    public void setSFactor(float f) {
        this.sFactor = f;
    }

    public float getSFactor() {
        return this.sFactor;
    }

    public void setBFactor(float f) {
        this.bFactor = f;
    }

    public float getBFactor() {
        return this.bFactor;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        Color.RGBtoHSB(n5, n6, n7, this.hsb);
        this.hsb[0] = this.hsb[0] + this.hFactor;
        while (this.hsb[0] < 0.0f) {
            this.hsb[0] = (float)((double)this.hsb[0] + Math.PI * 2);
        }
        this.hsb[1] = this.hsb[1] + this.sFactor;
        if (this.hsb[1] < 0.0f) {
            this.hsb[1] = 0.0f;
        } else if ((double)this.hsb[1] > 1.0) {
            this.hsb[1] = 1.0f;
        }
        this.hsb[2] = this.hsb[2] + this.bFactor;
        if (this.hsb[2] < 0.0f) {
            this.hsb[2] = 0.0f;
        } else if ((double)this.hsb[2] > 1.0) {
            this.hsb[2] = 1.0f;
        }
        n3 = Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
        return n4 | n3 & 0xFFFFFF;
    }

    public String toString() {
        return "Colors/Adjust HSB...";
    }
}

