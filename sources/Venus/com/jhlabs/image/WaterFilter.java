/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class WaterFilter
extends TransformFilter {
    private float wavelength = 16.0f;
    private float amplitude = 10.0f;
    private float phase = 0.0f;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float radius = 50.0f;
    private float radius2 = 0.0f;
    private float icentreX;
    private float icentreY;

    public WaterFilter() {
        this.setEdgeAction(1);
    }

    public void setWavelength(float f) {
        this.wavelength = f;
    }

    public float getWavelength() {
        return this.wavelength;
    }

    public void setAmplitude(float f) {
        this.amplitude = f;
    }

    public float getAmplitude() {
        return this.amplitude;
    }

    public void setPhase(float f) {
        this.phase = f;
    }

    public float getPhase() {
        return this.phase;
    }

    public void setCentreX(float f) {
        this.centreX = f;
    }

    public float getCentreX() {
        return this.centreX;
    }

    public void setCentreY(float f) {
        this.centreY = f;
    }

    public float getCentreY() {
        return this.centreY;
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
    }

    public float getRadius() {
        return this.radius;
    }

    private boolean inside(int n, int n2, int n3) {
        return n2 <= n && n <= n3;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.icentreX = (float)bufferedImage.getWidth() * this.centreX;
        this.icentreY = (float)bufferedImage.getHeight() * this.centreY;
        if (this.radius == 0.0f) {
            this.radius = Math.min(this.icentreX, this.icentreY);
        }
        this.radius2 = this.radius * this.radius;
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = (float)n - this.icentreX;
        float f2 = (float)n2 - this.icentreY;
        float f3 = f * f + f2 * f2;
        if (f3 > this.radius2) {
            fArray[0] = n;
            fArray[1] = n2;
        } else {
            float f4 = (float)Math.sqrt(f3);
            float f5 = this.amplitude * (float)Math.sin(f4 / this.wavelength * ((float)Math.PI * 2) - this.phase);
            f5 *= (this.radius - f4) / this.radius;
            if (f4 != 0.0f) {
                f5 *= this.wavelength / f4;
            }
            fArray[0] = (float)n + f * f5;
            fArray[1] = (float)n2 + f2 * f5;
        }
    }

    public String toString() {
        return "Distort/Water Ripples...";
    }
}

