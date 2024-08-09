/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DissolveFilter
extends PointFilter {
    private float density = 1.0f;
    private float softness = 0.0f;
    private float minDensity;
    private float maxDensity;
    private Random randomNumbers;

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

    public void setSoftness(float f) {
        this.softness = f;
    }

    public float getSoftness() {
        return this.softness;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        float f = (1.0f - this.density) * (1.0f + this.softness);
        this.minDensity = f - this.softness;
        this.maxDensity = f;
        this.randomNumbers = new Random(0L);
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 24 & 0xFF;
        float f = this.randomNumbers.nextFloat();
        float f2 = ImageMath.smoothStep(this.minDensity, this.maxDensity, f);
        return (int)((float)n4 * f2) << 24 | n3 & 0xFFFFFF;
    }

    public String toString() {
        return "Stylize/Dissolve...";
    }
}

