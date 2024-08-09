/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import java.awt.image.BufferedImage;

public class ErodeAlphaFilter
extends PointFilter {
    private float threshold;
    private float softness = 0.0f;
    protected float radius = 5.0f;
    private float lowerThreshold;
    private float upperThreshold;

    public ErodeAlphaFilter() {
        this(3.0f, 0.75f, 0.0f);
    }

    public ErodeAlphaFilter(float f, float f2, float f3) {
        this.radius = f;
        this.threshold = f2;
        this.softness = f3;
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

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        bufferedImage2 = new GaussianFilter((int)this.radius).filter(bufferedImage, null);
        this.lowerThreshold = 255.0f * (this.threshold - this.softness * 0.5f);
        this.upperThreshold = 255.0f * (this.threshold + this.softness * 0.5f);
        return super.filter(bufferedImage2, bufferedImage2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 24 & 0xFF;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        if (n4 == 255) {
            return 1;
        }
        float f = ImageMath.smoothStep(this.lowerThreshold, this.upperThreshold, n4);
        if ((n4 = (int)(f * 255.0f)) < 0) {
            n4 = 0;
        } else if (n4 > 255) {
            n4 = 255;
        }
        return n4 << 24 | 0xFFFFFF;
    }

    public String toString() {
        return "Alpha/Erode...";
    }
}

