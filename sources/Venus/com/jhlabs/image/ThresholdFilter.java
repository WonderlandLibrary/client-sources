/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;

public class ThresholdFilter
extends PointFilter {
    private int lowerThreshold;
    private int upperThreshold;
    private int white = 0xFFFFFF;
    private int black = 0;

    public ThresholdFilter() {
        this(127);
    }

    public ThresholdFilter(int n) {
        this.setLowerThreshold(n);
        this.setUpperThreshold(n);
    }

    public void setLowerThreshold(int n) {
        this.lowerThreshold = n;
    }

    public int getLowerThreshold() {
        return this.lowerThreshold;
    }

    public void setUpperThreshold(int n) {
        this.upperThreshold = n;
    }

    public int getUpperThreshold() {
        return this.upperThreshold;
    }

    public void setWhite(int n) {
        this.white = n;
    }

    public int getWhite() {
        return this.white;
    }

    public void setBlack(int n) {
        this.black = n;
    }

    public int getBlack() {
        return this.black;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = PixelUtils.brightness(n3);
        float f = ImageMath.smoothStep(this.lowerThreshold, this.upperThreshold, n4);
        return n3 & 0xFF000000 | ImageMath.mixColors(f, this.black, this.white) & 0xFFFFFF;
    }

    public String toString() {
        return "Stylize/Threshold...";
    }
}

