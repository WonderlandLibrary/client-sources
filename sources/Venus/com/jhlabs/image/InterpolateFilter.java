/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class InterpolateFilter
extends AbstractBufferedImageOp {
    private BufferedImage destination;
    private float interpolation;

    public void setDestination(BufferedImage bufferedImage) {
        this.destination = bufferedImage;
    }

    public BufferedImage getDestination() {
        return this.destination;
    }

    public void setInterpolation(float f) {
        this.interpolation = f;
    }

    public float getInterpolation() {
        return this.interpolation;
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
        if (this.destination != null) {
            n = Math.min(n, this.destination.getWidth());
            n2 = Math.min(n2, this.destination.getWidth());
            int[] nArray = null;
            int[] nArray2 = null;
            for (int i = 0; i < n2; ++i) {
                nArray = this.getRGB(bufferedImage, 0, i, n, 1, nArray);
                nArray2 = this.getRGB(this.destination, 0, i, n, 1, nArray2);
                for (int j = 0; j < n; ++j) {
                    int n4 = nArray[j];
                    int n5 = nArray2[j];
                    int n6 = n4 >> 24 & 0xFF;
                    int n7 = n4 >> 16 & 0xFF;
                    int n8 = n4 >> 8 & 0xFF;
                    int n9 = n4 & 0xFF;
                    int n10 = n5 >> 24 & 0xFF;
                    int n11 = n5 >> 16 & 0xFF;
                    int n12 = n5 >> 8 & 0xFF;
                    int n13 = n5 & 0xFF;
                    n7 = PixelUtils.clamp(ImageMath.lerp(this.interpolation, n7, n11));
                    n8 = PixelUtils.clamp(ImageMath.lerp(this.interpolation, n8, n12));
                    n9 = PixelUtils.clamp(ImageMath.lerp(this.interpolation, n9, n13));
                    nArray[j] = n6 << 24 | n7 << 16 | n8 << 8 | n9;
                }
                this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
            }
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Effects/Interpolate...";
    }
}

