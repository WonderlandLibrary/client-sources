/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class FourColorFilter
extends PointFilter {
    private int width;
    private int height;
    private int colorNW;
    private int colorNE;
    private int colorSW;
    private int colorSE;
    private int rNW;
    private int gNW;
    private int bNW;
    private int rNE;
    private int gNE;
    private int bNE;
    private int rSW;
    private int gSW;
    private int bSW;
    private int rSE;
    private int gSE;
    private int bSE;

    public FourColorFilter() {
        this.setColorNW(-65536);
        this.setColorNE(-65281);
        this.setColorSW(-16776961);
        this.setColorSE(-16711681);
    }

    public void setColorNW(int n) {
        this.colorNW = n;
        this.rNW = n >> 16 & 0xFF;
        this.gNW = n >> 8 & 0xFF;
        this.bNW = n & 0xFF;
    }

    public int getColorNW() {
        return this.colorNW;
    }

    public void setColorNE(int n) {
        this.colorNE = n;
        this.rNE = n >> 16 & 0xFF;
        this.gNE = n >> 8 & 0xFF;
        this.bNE = n & 0xFF;
    }

    public int getColorNE() {
        return this.colorNE;
    }

    public void setColorSW(int n) {
        this.colorSW = n;
        this.rSW = n >> 16 & 0xFF;
        this.gSW = n >> 8 & 0xFF;
        this.bSW = n & 0xFF;
    }

    public int getColorSW() {
        return this.colorSW;
    }

    public void setColorSE(int n) {
        this.colorSE = n;
        this.rSE = n >> 16 & 0xFF;
        this.gSE = n >> 8 & 0xFF;
        this.bSE = n & 0xFF;
    }

    public int getColorSE() {
        return this.colorSE;
    }

    @Override
    public void setDimensions(int n, int n2) {
        this.width = n;
        this.height = n2;
        super.setDimensions(n, n2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f = (float)n / (float)this.width;
        float f2 = (float)n2 / (float)this.height;
        float f3 = (float)this.rNW + (float)(this.rNE - this.rNW) * f;
        float f4 = (float)this.rSW + (float)(this.rSE - this.rSW) * f;
        int n4 = (int)(f3 + (f4 - f3) * f2 + 0.5f);
        f3 = (float)this.gNW + (float)(this.gNE - this.gNW) * f;
        f4 = (float)this.gSW + (float)(this.gSE - this.gSW) * f;
        int n5 = (int)(f3 + (f4 - f3) * f2 + 0.5f);
        f3 = (float)this.bNW + (float)(this.bNE - this.bNW) * f;
        f4 = (float)this.bSW + (float)(this.bSE - this.bSW) * f;
        int n6 = (int)(f3 + (f4 - f3) * f2 + 0.5f);
        return 0xFF000000 | n4 << 16 | n5 << 8 | n6;
    }

    public String toString() {
        return "Texture/Four Color Fill...";
    }
}

