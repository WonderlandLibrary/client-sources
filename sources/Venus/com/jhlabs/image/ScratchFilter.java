/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ScratchFilter
extends AbstractBufferedImageOp {
    private float density = 0.1f;
    private float angle;
    private float angleVariation = 1.0f;
    private float width = 0.5f;
    private float length = 0.5f;
    private int color = -1;
    private int seed = 0;

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngleVariation(float f) {
        this.angleVariation = f;
    }

    public float getAngleVariation() {
        return this.angleVariation;
    }

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

    public void setLength(float f) {
        this.length = f;
    }

    public float getLength() {
        return this.length;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getWidth() {
        return this.width;
    }

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
    }

    public void setSeed(int n) {
        this.seed = n;
    }

    public int getSeed() {
        return this.seed;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = (int)(this.density * (float)n * (float)n2 / 100.0f);
        float f = this.length * (float)n;
        Random random2 = new Random(this.seed);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(new Color(this.color));
        graphics2D.setStroke(new BasicStroke(this.width));
        for (int i = 0; i < n3; ++i) {
            float f2 = (float)n * random2.nextFloat();
            float f3 = (float)n2 * random2.nextFloat();
            float f4 = this.angle + (float)Math.PI * 2 * (this.angleVariation * (random2.nextFloat() - 0.5f));
            float f5 = (float)Math.sin(f4) * f;
            float f6 = (float)Math.cos(f4) * f;
            float f7 = f2 - f6;
            float f8 = f3 - f5;
            float f9 = f2 + f6;
            float f10 = f3 + f5;
            graphics2D.drawLine((int)f7, (int)f8, (int)f9, (int)f10);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Render/Scratches...";
    }
}

