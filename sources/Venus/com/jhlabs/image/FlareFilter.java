/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.Noise;
import java.awt.geom.Point2D;

public class FlareFilter
extends PointFilter {
    private int rays = 50;
    private float radius;
    private float baseAmount = 1.0f;
    private float ringAmount = 0.2f;
    private float rayAmount = 0.1f;
    private int color = -1;
    private int width;
    private int height;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float ringWidth = 1.6f;
    private float linear = 0.03f;
    private float gauss = 0.006f;
    private float mix = 0.5f;
    private float falloff = 6.0f;
    private float sigma;
    private float icentreX;
    private float icentreY;

    public FlareFilter() {
        this.setRadius(50.0f);
    }

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
    }

    public void setRingWidth(float f) {
        this.ringWidth = f;
    }

    public float getRingWidth() {
        return this.ringWidth;
    }

    public void setBaseAmount(float f) {
        this.baseAmount = f;
    }

    public float getBaseAmount() {
        return this.baseAmount;
    }

    public void setRingAmount(float f) {
        this.ringAmount = f;
    }

    public float getRingAmount() {
        return this.ringAmount;
    }

    public void setRayAmount(float f) {
        this.rayAmount = f;
    }

    public float getRayAmount() {
        return this.rayAmount;
    }

    public void setCentre(Point2D point2D) {
        this.centreX = (float)point2D.getX();
        this.centreY = (float)point2D.getY();
    }

    public Point2D getCentre() {
        return new Point2D.Float(this.centreX, this.centreY);
    }

    public void setRadius(float f) {
        this.radius = f;
        this.sigma = f / 3.0f;
    }

    public float getRadius() {
        return this.radius;
    }

    @Override
    public void setDimensions(int n, int n2) {
        this.width = n;
        this.height = n2;
        this.icentreX = this.centreX * (float)n;
        this.icentreY = this.centreY * (float)n2;
        super.setDimensions(n, n2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f;
        float f2 = (float)n - this.icentreX;
        float f3 = (float)n2 - this.icentreY;
        float f4 = (float)Math.sqrt(f2 * f2 + f3 * f3);
        float f5 = (float)Math.exp(-f4 * f4 * this.gauss) * this.mix + (float)Math.exp(-f4 * this.linear) * (1.0f - this.mix);
        f5 *= this.baseAmount;
        if (f4 > this.radius + this.ringWidth) {
            f5 = ImageMath.lerp((f4 - (this.radius + this.ringWidth)) / this.falloff, f5, 0.0f);
        }
        if (f4 < this.radius - this.ringWidth || f4 > this.radius + this.ringWidth) {
            f = 0.0f;
        } else {
            f = Math.abs(f4 - this.radius) / this.ringWidth;
            f = 1.0f - f * f * (3.0f - 2.0f * f);
            f *= this.ringAmount;
        }
        f5 += f;
        float f6 = (float)Math.atan2(f2, f3) + (float)Math.PI;
        f6 = (ImageMath.mod(f6 / (float)Math.PI * 17.0f + 1.0f + Noise.noise1(f6 * 10.0f), 1.0f) - 0.5f) * 2.0f;
        f6 = Math.abs(f6);
        f6 = (float)Math.pow(f6, 5.0);
        float f7 = this.rayAmount * f6 / (1.0f + f4 * 0.1f);
        f5 += f7;
        f5 = ImageMath.clamp(f5, 0.0f, 1.0f);
        return ImageMath.mixColors(f5, n3, this.color);
    }

    public String toString() {
        return "Stylize/Flare...";
    }
}

