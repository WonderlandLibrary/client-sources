/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public abstract class WholeImageFilter
extends AbstractBufferedImageOp {
    protected Rectangle transformedSpace;
    protected Rectangle originalSpace;

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Object object;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        WritableRaster writableRaster = bufferedImage.getRaster();
        this.originalSpace = new Rectangle(0, 0, n, n2);
        this.transformedSpace = new Rectangle(0, 0, n, n2);
        this.transformSpace(this.transformedSpace);
        if (bufferedImage2 == null) {
            object = bufferedImage.getColorModel();
            bufferedImage2 = new BufferedImage((ColorModel)object, ((ColorModel)object).createCompatibleWritableRaster(this.transformedSpace.width, this.transformedSpace.height), ((ColorModel)object).isAlphaPremultiplied(), null);
        }
        object = bufferedImage2.getRaster();
        int[] nArray = this.getRGB(bufferedImage, 0, 0, n, n2, null);
        nArray = this.filterPixels(n, n2, nArray, this.transformedSpace);
        this.setRGB(bufferedImage2, 0, 0, this.transformedSpace.width, this.transformedSpace.height, nArray);
        return bufferedImage2;
    }

    protected void transformSpace(Rectangle rectangle) {
    }

    protected abstract int[] filterPixels(int var1, int var2, int[] var3, Rectangle var4);
}

