/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class KeyFilter
extends AbstractBufferedImageOp {
    private float hTolerance = 0.0f;
    private float sTolerance = 0.0f;
    private float bTolerance = 0.0f;
    private BufferedImage destination;
    private BufferedImage cleanImage;

    public void setHTolerance(float f) {
        this.hTolerance = f;
    }

    public float getHTolerance() {
        return this.hTolerance;
    }

    public void setSTolerance(float f) {
        this.sTolerance = f;
    }

    public float getSTolerance() {
        return this.sTolerance;
    }

    public void setBTolerance(float f) {
        this.bTolerance = f;
    }

    public float getBTolerance() {
        return this.bTolerance;
    }

    public void setDestination(BufferedImage bufferedImage) {
        this.destination = bufferedImage;
    }

    public BufferedImage getDestination() {
        return this.destination;
    }

    public void setCleanImage(BufferedImage bufferedImage) {
        this.cleanImage = bufferedImage;
    }

    public BufferedImage getCleanImage() {
        return this.cleanImage;
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
        if (this.destination != null && this.cleanImage != null) {
            float[] fArray = null;
            float[] fArray2 = null;
            int[] nArray = null;
            int[] nArray2 = null;
            int[] nArray3 = null;
            for (int i = 0; i < n2; ++i) {
                nArray = this.getRGB(bufferedImage, 0, i, n, 1, nArray);
                nArray2 = this.getRGB(this.destination, 0, i, n, 1, nArray2);
                nArray3 = this.getRGB(this.cleanImage, 0, i, n, 1, nArray3);
                for (int j = 0; j < n; ++j) {
                    int n4 = nArray[j];
                    int n5 = nArray2[j];
                    int n6 = nArray3[j];
                    int n7 = n4 >> 16 & 0xFF;
                    int n8 = n4 >> 8 & 0xFF;
                    int n9 = n4 & 0xFF;
                    int n10 = n6 >> 16 & 0xFF;
                    int n11 = n6 >> 8 & 0xFF;
                    int n12 = n6 & 0xFF;
                    nArray[j] = Math.abs((fArray = Color.RGBtoHSB(n7, n9, n8, fArray))[0] - (fArray2 = Color.RGBtoHSB(n10, n12, n11, fArray2))[0]) < this.hTolerance && Math.abs(fArray[1] - fArray2[1]) < this.sTolerance && Math.abs(fArray[2] - fArray2[2]) < this.bTolerance ? n5 : n4;
                }
                this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
            }
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Keying/Key...";
    }
}

