/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class TileImageFilter
extends AbstractBufferedImageOp {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    public TileImageFilter() {
        this(32, 32);
    }

    public TileImageFilter(int n, int n2) {
        this.width = n;
        this.height = n2;
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
        for (int i = 0; i < this.height; i += n2) {
            for (int j = 0; j < this.width; j += n) {
                ((Graphics2D)object).drawImage(bufferedImage, null, j, i);
            }
        }
        ((Graphics)object).dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Tile";
    }
}

