/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TwirlFilter
extends TransformFilter {
    private float angle = 0.0f;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float radius = 100.0f;
    private float radius2 = 0.0f;
    private float icentreX;
    private float icentreY;

    public TwirlFilter() {
        this.setEdgeAction(1);
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
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
            f3 = (float)Math.sqrt(f3);
            float f4 = (float)Math.atan2(f2, f) + this.angle * (this.radius - f3) / this.radius;
            fArray[0] = this.icentreX + f3 * (float)Math.cos(f4);
            fArray[1] = this.icentreY + f3 * (float)Math.sin(f4);
        }
    }

    public String toString() {
        return "Distort/Twirl...";
    }
}

