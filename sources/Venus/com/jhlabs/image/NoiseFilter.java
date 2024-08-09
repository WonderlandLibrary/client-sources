/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import java.util.Random;

public class NoiseFilter
extends PointFilter {
    public static final int GAUSSIAN = 0;
    public static final int UNIFORM = 1;
    private int amount = 25;
    private int distribution = 1;
    private boolean monochrome = false;
    private float density = 1.0f;
    private Random randomNumbers = new Random();

    public void setAmount(int n) {
        this.amount = n;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setDistribution(int n) {
        this.distribution = n;
    }

    public int getDistribution() {
        return this.distribution;
    }

    public void setMonochrome(boolean bl) {
        this.monochrome = bl;
    }

    public boolean getMonochrome() {
        return this.monochrome;
    }

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

    private int random(int n) {
        if ((n += (int)((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (double)(2.0f * this.randomNumbers.nextFloat() - 1.0f)) * (double)this.amount)) < 0) {
            n = 0;
        } else if (n > 255) {
            n = 255;
        }
        return n;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        if (this.randomNumbers.nextFloat() <= this.density) {
            int n4 = n3 & 0xFF000000;
            int n5 = n3 >> 16 & 0xFF;
            int n6 = n3 >> 8 & 0xFF;
            int n7 = n3 & 0xFF;
            if (this.monochrome) {
                int n8 = (int)((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (double)(2.0f * this.randomNumbers.nextFloat() - 1.0f)) * (double)this.amount);
                n5 = PixelUtils.clamp(n5 + n8);
                n6 = PixelUtils.clamp(n6 + n8);
                n7 = PixelUtils.clamp(n7 + n8);
            } else {
                n5 = this.random(n5);
                n6 = this.random(n6);
                n7 = this.random(n7);
            }
            return n4 | n5 << 16 | n6 << 8 | n7;
        }
        return n3;
    }

    public String toString() {
        return "Stylize/Add Noise...";
    }
}

