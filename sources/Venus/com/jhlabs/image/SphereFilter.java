/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SphereFilter
extends TransformFilter {
    private float a = 0.0f;
    private float b = 0.0f;
    private float a2 = 0.0f;
    private float b2 = 0.0f;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float refractionIndex = 1.5f;
    private float icentreX;
    private float icentreY;

    public SphereFilter() {
        this.setEdgeAction(1);
        this.setRadius(100.0f);
    }

    public void setRefractionIndex(float f) {
        this.refractionIndex = f;
    }

    public float getRefractionIndex() {
        return this.refractionIndex;
    }

    public void setRadius(float f) {
        this.a = f;
        this.b = f;
    }

    public float getRadius() {
        return this.a;
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

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        this.icentreX = (float)n * this.centreX;
        this.icentreY = (float)n2 * this.centreY;
        if (this.a == 0.0f) {
            this.a = n / 2;
        }
        if (this.b == 0.0f) {
            this.b = n2 / 2;
        }
        this.a2 = this.a * this.a;
        this.b2 = this.b * this.b;
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = (float)n2 - this.icentreY;
        float f2 = f * f;
        float f3 = (float)n - this.icentreX;
        float f4 = f3 * f3;
        if (f2 >= this.b2 - this.b2 * f4 / this.a2) {
            fArray[0] = n;
            fArray[1] = n2;
        } else {
            float f5 = 1.0f / this.refractionIndex;
            float f6 = (float)Math.sqrt((1.0f - f4 / this.a2 - f2 / this.b2) * (this.a * this.b));
            float f7 = f6 * f6;
            float f8 = (float)Math.acos((double)f3 / Math.sqrt(f4 + f7));
            float f9 = 1.5707964f - f8;
            float f10 = (float)Math.asin(Math.sin(f9) * (double)f5);
            f10 = 1.5707964f - f8 - f10;
            fArray[0] = (float)n - (float)Math.tan(f10) * f6;
            float f11 = (float)Math.acos((double)f / Math.sqrt(f2 + f7));
            f9 = 1.5707964f - f11;
            f10 = (float)Math.asin(Math.sin(f9) * (double)f5);
            f10 = 1.5707964f - f11 - f10;
            fArray[1] = (float)n2 - (float)Math.tan(f10) * f6;
        }
    }

    public String toString() {
        return "Distort/Sphere...";
    }
}

