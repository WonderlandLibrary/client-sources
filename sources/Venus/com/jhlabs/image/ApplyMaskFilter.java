/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ApplyMaskFilter
extends AbstractBufferedImageOp {
    private BufferedImage destination;
    private BufferedImage maskImage;

    public ApplyMaskFilter() {
    }

    public ApplyMaskFilter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.maskImage = bufferedImage;
        this.destination = bufferedImage2;
    }

    public void setDestination(BufferedImage bufferedImage) {
        this.destination = bufferedImage;
    }

    public BufferedImage getDestination() {
        return this.destination;
    }

    public void setMaskImage(BufferedImage bufferedImage) {
        this.maskImage = bufferedImage;
    }

    public BufferedImage getMaskImage() {
        return this.maskImage;
    }

    public static void composeThroughMask(Raster raster, WritableRaster writableRaster, Raster raster2) {
        int n = raster.getMinX();
        int n2 = raster.getMinY();
        int n3 = raster.getWidth();
        int n4 = raster.getHeight();
        int[] nArray = null;
        int[] nArray2 = null;
        int[] nArray3 = null;
        for (int i = 0; i < n4; ++i) {
            nArray = raster.getPixels(n, n2, n3, 1, nArray);
            nArray2 = raster2.getPixels(n, n2, n3, 1, nArray2);
            nArray3 = writableRaster.getPixels(n, n2, n3, 1, nArray3);
            int n5 = n;
            for (int j = 0; j < n3; ++j) {
                int n6 = nArray[n5];
                int n7 = nArray3[n5];
                int n8 = nArray[n5 + 1];
                int n9 = nArray3[n5 + 1];
                int n10 = nArray[n5 + 2];
                int n11 = nArray3[n5 + 2];
                int n12 = nArray[n5 + 3];
                int n13 = nArray3[n5 + 3];
                float f = (float)nArray2[n5 + 3] / 255.0f;
                float f2 = 1.0f - f;
                nArray3[n5] = (int)(f * (float)n6 + f2 * (float)n7);
                nArray3[n5 + 1] = (int)(f * (float)n8 + f2 * (float)n9);
                nArray3[n5 + 2] = (int)(f * (float)n10 + f2 * (float)n11);
                nArray3[n5 + 3] = (int)(f * (float)n12 + f2 * (float)n13);
                n5 += 4;
            }
            writableRaster.setPixels(n, n2, n3, 1, nArray3);
            ++n2;
        }
    }

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
        if (this.destination != null && this.maskImage != null) {
            ApplyMaskFilter.composeThroughMask(bufferedImage.getRaster(), bufferedImage2.getRaster(), this.maskImage.getRaster());
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Keying/Key...";
    }
}

