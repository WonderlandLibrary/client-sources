/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class PointFilter
extends AbstractBufferedImageOp {
    protected boolean canFilterIndexColorModel = false;

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        WritableRaster writableRaster = bufferedImage.getRaster();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        WritableRaster writableRaster2 = bufferedImage2.getRaster();
        this.setDimensions(n, n2);
        int[] nArray = new int[n];
        for (int i = 0; i < n2; ++i) {
            int n4;
            if (n3 == 2) {
                writableRaster.getDataElements(0, i, n, 1, nArray);
                for (n4 = 0; n4 < n; ++n4) {
                    nArray[n4] = this.filterRGB(n4, i, nArray[n4]);
                }
                writableRaster2.setDataElements(0, i, n, 1, nArray);
                continue;
            }
            bufferedImage.getRGB(0, i, n, 1, nArray, 0, n);
            for (n4 = 0; n4 < n; ++n4) {
                nArray[n4] = this.filterRGB(n4, i, nArray[n4]);
            }
            bufferedImage2.setRGB(0, i, n, 1, nArray, 0, n);
        }
        return bufferedImage2;
    }

    public void setDimensions(int n, int n2) {
    }

    public abstract int filterRGB(int var1, int var2, int var3);
}

