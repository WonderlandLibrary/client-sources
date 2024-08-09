/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.TransformFilter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class KaleidoscopeFilter
extends TransformFilter {
    private float angle = 0.0f;
    private float angle2 = 0.0f;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private int sides = 3;
    private float radius = 0.0f;
    private float icentreX;
    private float icentreY;

    public KaleidoscopeFilter() {
        this.setEdgeAction(1);
    }

    public void setSides(int n) {
        this.sides = n;
    }

    public int getSides() {
        return this.sides;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle2(float f) {
        this.angle2 = f;
    }

    public float getAngle2() {
        return this.angle2;
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

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.icentreX = (float)bufferedImage.getWidth() * this.centreX;
        this.icentreY = (float)bufferedImage.getHeight() * this.centreY;
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        double d = (float)n - this.icentreX;
        double d2 = (float)n2 - this.icentreY;
        double d3 = Math.sqrt(d * d + d2 * d2);
        double d4 = Math.atan2(d2, d) - (double)this.angle - (double)this.angle2;
        d4 = ImageMath.triangle((float)(d4 / Math.PI * (double)this.sides * 0.5));
        if (this.radius != 0.0f) {
            double d5 = Math.cos(d4);
            double d6 = (double)this.radius / d5;
            d3 = d6 * (double)ImageMath.triangle((float)(d3 / d6));
        }
        fArray[0] = (float)((double)this.icentreX + d3 * Math.cos(d4 += (double)this.angle));
        fArray[1] = (float)((double)this.icentreY + d3 * Math.sin(d4));
    }

    public String toString() {
        return "Distort/Kaleidoscope...";
    }
}

