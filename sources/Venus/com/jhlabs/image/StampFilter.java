/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import java.awt.image.BufferedImage;

public class StampFilter
extends PointFilter {
    private float threshold;
    private float softness = 0.0f;
    private float radius = 5.0f;
    private float lowerThreshold3;
    private float upperThreshold3;
    private int white = -1;
    private int black = -16777216;

    public StampFilter() {
        this(0.5f);
    }

    public StampFilter(float f) {
        this.setThreshold(f);
    }

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setThreshold(float f) {
        this.threshold = f;
    }

    public float getThreshold() {
        return this.threshold;
    }

    public void setSoftness(float f) {
        this.softness = f;
    }

    public float getSoftness() {
        return this.softness;
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
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        bufferedImage2 = new GaussianFilter((int)this.radius).filter(bufferedImage, null);
        this.lowerThreshold3 = 765.0f * (this.threshold - this.softness * 0.5f);
        this.upperThreshold3 = 765.0f * (this.threshold + this.softness * 0.5f);
        return super.filter(bufferedImage2, bufferedImage2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        int n8 = n5 + n6 + n7;
        float f = ImageMath.smoothStep(this.lowerThreshold3, this.upperThreshold3, n8);
        return ImageMath.mixColors(f, this.black, this.white);
    }

    public String toString() {
        return "Stylize/Stamp...";
    }
}

