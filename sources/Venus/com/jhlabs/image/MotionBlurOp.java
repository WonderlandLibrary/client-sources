/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MotionBlurOp
extends AbstractBufferedImageOp {
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float distance;
    private float angle;
    private float rotation;
    private float zoom;

    public MotionBlurOp() {
    }

    public MotionBlurOp(float f, float f2, float f3, float f4) {
        this.distance = f;
        this.angle = f2;
        this.rotation = f3;
        this.zoom = f4;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setDistance(float f) {
        this.distance = f;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setRotation(float f) {
        this.rotation = f;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public float getZoom() {
        return this.zoom;
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

    private int log2(int n) {
        int n2 = 1;
        int n3 = 0;
        while (n2 < n) {
            n2 *= 2;
            ++n3;
        }
        return n3;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        BufferedImage bufferedImage3 = bufferedImage;
        float f = (float)bufferedImage.getWidth() * this.centreX;
        float f2 = (float)bufferedImage.getHeight() * this.centreY;
        float f3 = (float)Math.sqrt(f * f + f2 * f2);
        float f4 = (float)((double)this.distance * Math.cos(this.angle));
        float f5 = (float)((double)this.distance * -Math.sin(this.angle));
        float f6 = this.zoom;
        float f7 = this.rotation;
        float f8 = this.distance + Math.abs(this.rotation * f3) + this.zoom * f3;
        int n = this.log2((int)f8);
        f4 /= f8;
        f5 /= f8;
        f6 /= f8;
        f7 /= f8;
        if (n == 0) {
            Graphics2D graphics2D = bufferedImage2.createGraphics();
            graphics2D.drawRenderedImage(bufferedImage, null);
            graphics2D.dispose();
            return bufferedImage2;
        }
        BufferedImage bufferedImage4 = this.createCompatibleDestImage(bufferedImage, null);
        for (int i = 0; i < n; ++i) {
            Graphics2D graphics2D = bufferedImage4.createGraphics();
            graphics2D.drawImage(bufferedImage3, null, null);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
            graphics2D.translate(f + f4, f2 + f5);
            graphics2D.scale(1.0001 + (double)f6, 1.0001 + (double)f6);
            if (this.rotation != 0.0f) {
                graphics2D.rotate(f7);
            }
            graphics2D.translate(-f, -f2);
            graphics2D.drawImage(bufferedImage2, null, null);
            graphics2D.dispose();
            BufferedImage bufferedImage5 = bufferedImage2;
            bufferedImage2 = bufferedImage4;
            bufferedImage4 = bufferedImage5;
            bufferedImage3 = bufferedImage2;
            f4 *= 2.0f;
            f5 *= 2.0f;
            f6 *= 2.0f;
            f7 *= 2.0f;
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Blur/Faster Motion Blur...";
    }
}

