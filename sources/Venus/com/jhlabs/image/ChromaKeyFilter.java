/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ChromaKeyFilter
extends AbstractBufferedImageOp {
    private float hTolerance = 0.0f;
    private float sTolerance = 0.0f;
    private float bTolerance = 0.0f;
    private int color;

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

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
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
        float[] fArray = null;
        float[] fArray2 = null;
        int n4 = this.color;
        int n5 = n4 >> 16 & 0xFF;
        int n6 = n4 >> 8 & 0xFF;
        int n7 = n4 & 0xFF;
        fArray2 = Color.RGBtoHSB(n5, n7, n6, fArray2);
        int[] nArray = null;
        for (int i = 0; i < n2; ++i) {
            nArray = this.getRGB(bufferedImage, 0, i, n, 1, nArray);
            for (int j = 0; j < n; ++j) {
                int n8 = nArray[j];
                int n9 = n8 >> 16 & 0xFF;
                int n10 = n8 & 0xFF;
                int n11 = n8 >> 8 & 0xFF;
                nArray[j] = Math.abs((fArray = Color.RGBtoHSB(n9, n10, n11, fArray))[0] - fArray2[0]) < this.hTolerance && Math.abs(fArray[1] - fArray2[1]) < this.sTolerance && Math.abs(fArray[2] - fArray2[2]) < this.bTolerance ? n8 & 0xFFFFFF : n8;
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Keying/Chroma Key...";
    }
}

