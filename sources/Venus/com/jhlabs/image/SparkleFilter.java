/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import java.util.Random;

public class SparkleFilter
extends PointFilter {
    private int rays = 50;
    private int radius = 25;
    private int amount = 50;
    private int color = -1;
    private int randomness = 25;
    private int width;
    private int height;
    private int centreX;
    private int centreY;
    private long seed = 371L;
    private float[] rayLengths;
    private Random randomNumbers = new Random();

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
    }

    public void setRandomness(int n) {
        this.randomness = n;
    }

    public int getRandomness() {
        return this.randomness;
    }

    public void setAmount(int n) {
        this.amount = n;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setRays(int n) {
        this.rays = n;
    }

    public int getRays() {
        return this.rays;
    }

    public void setRadius(int n) {
        this.radius = n;
    }

    public int getRadius() {
        return this.radius;
    }

    @Override
    public void setDimensions(int n, int n2) {
        this.width = n;
        this.height = n2;
        this.centreX = n / 2;
        this.centreY = n2 / 2;
        super.setDimensions(n, n2);
        this.randomNumbers.setSeed(this.seed);
        this.rayLengths = new float[this.rays];
        for (int i = 0; i < this.rays; ++i) {
            this.rayLengths[i] = (float)this.radius + (float)this.randomness / 100.0f * (float)this.radius * (float)this.randomNumbers.nextGaussian();
        }
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f = n - this.centreX;
        float f2 = n2 - this.centreY;
        float f3 = f * f + f2 * f2;
        float f4 = (float)Math.atan2(f2, f);
        float f5 = (f4 + (float)Math.PI) / ((float)Math.PI * 2) * (float)this.rays;
        int n4 = (int)f5;
        float f6 = f5 - (float)n4;
        if (this.radius != 0) {
            float f7 = ImageMath.lerp(f6, this.rayLengths[n4 % this.rays], this.rayLengths[(n4 + 1) % this.rays]);
            float f8 = f7 * f7 / (f3 + 1.0E-4f);
            f8 = (float)Math.pow(f8, (double)(100 - this.amount) / 50.0);
            f6 -= 0.5f;
            f6 = 1.0f - f6 * f6;
            f6 *= f8;
        }
        f6 = ImageMath.clamp(f6, 0.0f, 1.0f);
        return ImageMath.mixColors(f6, n3, this.color);
    }

    public String toString() {
        return "Stylize/Sparkle...";
    }
}

