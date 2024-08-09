/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.GaussianFilter;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class ShadowFilter
extends AbstractBufferedImageOp {
    private float radius = 5.0f;
    private float angle = 4.712389f;
    private float distance = 5.0f;
    private float opacity = 0.5f;
    private boolean addMargins = false;
    private boolean shadowOnly = false;
    private int shadowColor = -16777216;

    public ShadowFilter() {
    }

    public ShadowFilter(float f, float f2, float f3, float f4) {
        this.radius = f;
        this.angle = (float)Math.atan2(f3, f2);
        this.distance = (float)Math.sqrt(f2 * f2 + f3 * f3);
        this.opacity = f4;
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

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setShadowColor(int n) {
        this.shadowColor = n;
    }

    public int getShadowColor() {
        return this.shadowColor;
    }

    public void setAddMargins(boolean bl) {
        this.addMargins = bl;
    }

    public boolean getAddMargins() {
        return this.addMargins;
    }

    public void setShadowOnly(boolean bl) {
        this.shadowOnly = bl;
    }

    public boolean getShadowOnly() {
        return this.shadowOnly;
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage bufferedImage) {
        Rectangle rectangle = new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        if (this.addMargins) {
            float f = this.distance * (float)Math.cos(this.angle);
            float f2 = -this.distance * (float)Math.sin(this.angle);
            rectangle.width += (int)(Math.abs(f) + 2.0f * this.radius);
            rectangle.height += (int)(Math.abs(f2) + 2.0f * this.radius);
        }
        return rectangle;
    }

    @Override
    public Point2D getPoint2D(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Double();
        }
        if (this.addMargins) {
            float f = this.distance * (float)Math.cos(this.angle);
            float f2 = -this.distance * (float)Math.sin(this.angle);
            float f3 = Math.max(0.0f, this.radius - f2);
            float f4 = Math.max(0.0f, this.radius - f);
            point2D2.setLocation(point2D.getX() + (double)f4, point2D.getY() + (double)f3);
        } else {
            point2D2.setLocation(point2D.getX(), point2D.getY());
        }
        return point2D2;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        float f = this.distance * (float)Math.cos(this.angle);
        float f2 = -this.distance * (float)Math.sin(this.angle);
        if (bufferedImage2 == null) {
            if (this.addMargins) {
                ColorModel colorModel = bufferedImage.getColorModel();
                bufferedImage2 = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(bufferedImage.getWidth() + (int)(Math.abs(f) + this.radius), bufferedImage.getHeight() + (int)(Math.abs(f2) + this.radius)), colorModel.isAlphaPremultiplied(), null);
            } else {
                bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
            }
        }
        float f3 = (float)(this.shadowColor >> 16 & 0xFF) / 255.0f;
        float f4 = (float)(this.shadowColor >> 8 & 0xFF) / 255.0f;
        float f5 = (float)(this.shadowColor & 0xFF) / 255.0f;
        float[][] fArrayArray = new float[][]{{0.0f, 0.0f, 0.0f, f3}, {0.0f, 0.0f, 0.0f, f4}, {0.0f, 0.0f, 0.0f, f5}, {0.0f, 0.0f, 0.0f, this.opacity}};
        BufferedImage bufferedImage3 = new BufferedImage(n, n2, 2);
        new BandCombineOp(fArrayArray, null).filter(bufferedImage.getRaster(), bufferedImage3.getRaster());
        bufferedImage3 = new GaussianFilter(this.radius).filter(bufferedImage3, null);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(3, this.opacity));
        if (this.addMargins) {
            float f6 = this.radius / 2.0f;
            float f7 = Math.max(0.0f, this.radius - f2);
            float f8 = Math.max(0.0f, this.radius - f);
            graphics2D.translate(f8, f7);
        }
        graphics2D.drawRenderedImage(bufferedImage3, AffineTransform.getTranslateInstance(f, f2));
        if (!this.shadowOnly) {
            graphics2D.setComposite(AlphaComposite.SrcOver);
            graphics2D.drawRenderedImage(bufferedImage, null);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Stylize/Drop Shadow...";
    }
}

