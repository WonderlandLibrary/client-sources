/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class ScaleFilter
extends AbstractBufferedImageOp {
    private int width;
    private int height;

    public ScaleFilter() {
        this(32, 32);
    }

    public ScaleFilter(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Object object;
        if (bufferedImage2 == null) {
            object = bufferedImage.getColorModel();
            bufferedImage2 = new BufferedImage((ColorModel)object, ((ColorModel)object).createCompatibleWritableRaster(this.width, this.height), ((ColorModel)object).isAlphaPremultiplied(), null);
        }
        object = bufferedImage.getScaledInstance(this.width, this.height, 16);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawImage((Image)object, 0, 0, this.width, this.height, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Distort/Scale";
    }
}

