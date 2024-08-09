/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;

public class ChannelMixFilter
extends PointFilter {
    private int blueGreen;
    private int redBlue;
    private int greenRed;
    private int intoR;
    private int intoG;
    private int intoB;

    public ChannelMixFilter() {
        this.canFilterIndexColorModel = true;
    }

    public void setBlueGreen(int n) {
        this.blueGreen = n;
    }

    public int getBlueGreen() {
        return this.blueGreen;
    }

    public void setRedBlue(int n) {
        this.redBlue = n;
    }

    public int getRedBlue() {
        return this.redBlue;
    }

    public void setGreenRed(int n) {
        this.greenRed = n;
    }

    public int getGreenRed() {
        return this.greenRed;
    }

    public void setIntoR(int n) {
        this.intoR = n;
    }

    public int getIntoR() {
        return this.intoR;
    }

    public void setIntoG(int n) {
        this.intoG = n;
    }

    public int getIntoG() {
        return this.intoG;
    }

    public void setIntoB(int n) {
        this.intoB = n;
    }

    public int getIntoB() {
        return this.intoB;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        int n8 = PixelUtils.clamp((this.intoR * (this.blueGreen * n6 + (255 - this.blueGreen) * n7) / 255 + (255 - this.intoR) * n5) / 255);
        int n9 = PixelUtils.clamp((this.intoG * (this.redBlue * n7 + (255 - this.redBlue) * n5) / 255 + (255 - this.intoG) * n6) / 255);
        int n10 = PixelUtils.clamp((this.intoB * (this.greenRed * n5 + (255 - this.greenRed) * n6) / 255 + (255 - this.intoB) * n7) / 255);
        return n4 | n8 << 16 | n9 << 8 | n10;
    }

    public String toString() {
        return "Colors/Mix Channels...";
    }
}

