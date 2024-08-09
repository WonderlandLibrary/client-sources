/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.composite.AddComposite;
import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ErodeAlphaFilter;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.RescaleFilter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ShineFilter
extends AbstractBufferedImageOp {
    private float radius = 5.0f;
    private float angle = 5.4977875f;
    private float distance = 5.0f;
    private float bevel = 0.5f;
    private boolean shadowOnly = false;
    private int shineColor = -1;
    private float brightness = 0.2f;
    private float softness = 0.0f;

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

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setBevel(float f) {
        this.bevel = f;
    }

    public float getBevel() {
        return this.bevel;
    }

    public void setShineColor(int n) {
        this.shineColor = n;
    }

    public int getShineColor() {
        return this.shineColor;
    }

    public void setShadowOnly(boolean bl) {
        this.shadowOnly = bl;
    }

    public boolean getShadowOnly() {
        return this.shadowOnly;
    }

    public void setBrightness(float f) {
        this.brightness = f;
    }

    public float getBrightness() {
        return this.brightness;
    }

    public void setSoftness(float f) {
        this.softness = f;
    }

    public float getSoftness() {
        return this.softness;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        float f = this.distance * (float)Math.cos(this.angle);
        float f2 = -this.distance * (float)Math.sin(this.angle);
        BufferedImage bufferedImage3 = new BufferedImage(n, n2, 2);
        ErodeAlphaFilter erodeAlphaFilter = new ErodeAlphaFilter(this.bevel * 10.0f, 0.75f, 0.1f);
        bufferedImage3 = erodeAlphaFilter.filter(bufferedImage, null);
        BufferedImage bufferedImage4 = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage4.createGraphics();
        graphics2D.setColor(new Color(this.shineColor));
        graphics2D.fillRect(0, 0, n, n2);
        graphics2D.setComposite(AlphaComposite.DstIn);
        graphics2D.drawRenderedImage(bufferedImage3, null);
        graphics2D.setComposite(AlphaComposite.DstOut);
        graphics2D.translate(f, f2);
        graphics2D.drawRenderedImage(bufferedImage3, null);
        graphics2D.dispose();
        bufferedImage4 = new GaussianFilter(this.radius).filter(bufferedImage4, null);
        bufferedImage4 = new RescaleFilter(3.0f * this.brightness).filter(bufferedImage4, bufferedImage4);
        graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawRenderedImage(bufferedImage, null);
        graphics2D.setComposite(new AddComposite(1.0f));
        graphics2D.drawRenderedImage(bufferedImage4, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Stylize/Shine...";
    }
}

