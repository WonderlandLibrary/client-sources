/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class CropFilter
extends AbstractBufferedImageOp {
    private int x;
    private int y;
    private int width;
    private int height;

    public CropFilter() {
        this(0, 0, 32, 32);
    }

    public CropFilter(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
    }

    public void setX(int n) {
        this.x = n;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int n) {
        this.y = n;
    }

    public int getY() {
        return this.y;
    }

    public void setWidth(int n) {
        this.width = n;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int n) {
        this.height = n;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Object object;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            object = bufferedImage.getColorModel();
            bufferedImage2 = new BufferedImage((ColorModel)object, ((ColorModel)object).createCompatibleWritableRaster(this.width, this.height), ((ColorModel)object).isAlphaPremultiplied(), null);
        }
        object = bufferedImage2.createGraphics();
        ((Graphics2D)object).drawRenderedImage(bufferedImage, AffineTransform.getTranslateInstance(-this.x, -this.y));
        ((Graphics)object).dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Distort/Crop";
    }
}

