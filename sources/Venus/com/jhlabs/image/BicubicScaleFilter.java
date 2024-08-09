/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class BicubicScaleFilter
extends AbstractBufferedImageOp {
    private int width;
    private int height;

    public BicubicScaleFilter() {
        this(32, 32);
    }

    public BicubicScaleFilter(int n, int n2) {
        this.width = n;
        this.height = n2;
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
        ((Graphics2D)object).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        ((Graphics)object).drawImage(bufferedImage, 0, 0, this.width, this.height, null);
        ((Graphics)object).dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Distort/Bicubic Scale";
    }
}

