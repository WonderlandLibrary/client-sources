/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class FeedbackFilter
extends AbstractBufferedImageOp {
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float distance;
    private float angle;
    private float rotation;
    private float zoom;
    private float startAlpha = 1.0f;
    private float endAlpha = 1.0f;
    private int iterations;

    public FeedbackFilter() {
    }

    public FeedbackFilter(float f, float f2, float f3, float f4) {
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

    public void setStartAlpha(float f) {
        this.startAlpha = f;
    }

    public float getStartAlpha() {
        return this.startAlpha;
    }

    public void setEndAlpha(float f) {
        this.endAlpha = f;
    }

    public float getEndAlpha() {
        return this.endAlpha;
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

    public void setIterations(int n) {
        this.iterations = n;
    }

    public int getIterations() {
        return this.iterations;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        float f = (float)bufferedImage.getWidth() * this.centreX;
        float f2 = (float)bufferedImage.getHeight() * this.centreY;
        float f3 = (float)Math.sqrt(f * f + f2 * f2);
        float f4 = (float)((double)this.distance * Math.cos(this.angle));
        float f5 = (float)((double)this.distance * -Math.sin(this.angle));
        float f6 = (float)Math.exp(this.zoom);
        float f7 = this.rotation;
        if (this.iterations == 0) {
            Graphics2D graphics2D = bufferedImage2.createGraphics();
            graphics2D.drawRenderedImage(bufferedImage, null);
            graphics2D.dispose();
            return bufferedImage2;
        }
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawImage(bufferedImage, null, null);
        for (int i = 0; i < this.iterations; ++i) {
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.setComposite(AlphaComposite.getInstance(3, ImageMath.lerp((float)i / (float)(this.iterations - 1), this.startAlpha, this.endAlpha)));
            graphics2D.translate(f + f4, f2 + f5);
            graphics2D.scale(f6, f6);
            if (this.rotation != 0.0f) {
                graphics2D.rotate(f7);
            }
            graphics2D.translate(-f, -f2);
            graphics2D.drawImage(bufferedImage, null, null);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Effects/Feedback...";
    }
}

