/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.TransformFilter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class CircleFilter
extends TransformFilter {
    private float radius = 10.0f;
    private float height = 20.0f;
    private float angle = 0.0f;
    private float spreadAngle = (float)Math.PI;
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float icentreX;
    private float icentreY;
    private float iWidth;
    private float iHeight;

    public CircleFilter() {
        this.setEdgeAction(0);
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setSpreadAngle(float f) {
        this.spreadAngle = f;
    }

    public float getSpreadAngle() {
        return this.spreadAngle;
    }

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
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
        this.iWidth = bufferedImage.getWidth();
        this.iHeight = bufferedImage.getHeight();
        this.icentreX = this.iWidth * this.centreX;
        this.icentreY = this.iHeight * this.centreY;
        this.iWidth -= 1.0f;
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = (float)n - this.icentreX;
        float f2 = (float)n2 - this.icentreY;
        float f3 = (float)Math.atan2(-f2, -f) + this.angle;
        float f4 = (float)Math.sqrt(f * f + f2 * f2);
        f3 = ImageMath.mod(f3, (float)Math.PI * 2);
        fArray[0] = this.iWidth * f3 / (this.spreadAngle + 1.0E-5f);
        fArray[1] = this.iHeight * (1.0f - (f4 - this.radius) / (this.height + 1.0E-5f));
    }

    public String toString() {
        return "Distort/Circle...";
    }
}

