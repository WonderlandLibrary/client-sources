/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public class MirrorFilter
extends AbstractBufferedImageOp {
    private float opacity = 1.0f;
    private float centreY = 0.5f;
    private float distance;
    private float angle;
    private float rotation;
    private float gap;

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

    public void setGap(float f) {
        this.gap = f;
    }

    public float getGap() {
        return this.gap;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setCentreY(float f) {
        this.centreY = f;
    }

    public float getCentreY() {
        return this.centreY;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        BufferedImage bufferedImage3 = bufferedImage;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = (int)(this.centreY * (float)n2);
        int n4 = (int)(this.gap * (float)n2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        Shape shape = graphics2D.getClip();
        graphics2D.clipRect(0, 0, n, n3);
        graphics2D.drawRenderedImage(bufferedImage, null);
        graphics2D.setClip(shape);
        graphics2D.clipRect(0, n3 + n4, n, n2 - n3 - n4);
        graphics2D.translate(0, 2 * n3 + n4);
        graphics2D.scale(1.0, -1.0);
        graphics2D.drawRenderedImage(bufferedImage, null);
        graphics2D.setPaint(new GradientPaint(0.0f, 0.0f, new Color(1.0f, 0.0f, 0.0f, 0.0f), 0.0f, n3, new Color(0.0f, 1.0f, 0.0f, this.opacity)));
        graphics2D.setComposite(AlphaComposite.getInstance(6));
        graphics2D.fillRect(0, 0, n, n3);
        graphics2D.setClip(shape);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Effects/Mirror...";
    }
}

