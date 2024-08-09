/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.image.BufferedImage;

public class DisplaceFilter
extends TransformFilter {
    private float amount = 1.0f;
    private BufferedImage displacementMap = null;
    private int[] xmap;
    private int[] ymap;
    private int dw;
    private int dh;

    public void setDisplacementMap(BufferedImage bufferedImage) {
        this.displacementMap = bufferedImage;
    }

    public BufferedImage getDisplacementMap() {
        return this.displacementMap;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = bufferedImage.getWidth();
        int n8 = bufferedImage.getHeight();
        BufferedImage bufferedImage3 = this.displacementMap != null ? this.displacementMap : bufferedImage;
        this.dw = bufferedImage3.getWidth();
        this.dh = bufferedImage3.getHeight();
        int[] nArray = new int[this.dw * this.dh];
        this.getRGB(bufferedImage3, 0, 0, this.dw, this.dh, nArray);
        this.xmap = new int[this.dw * this.dh];
        this.ymap = new int[this.dw * this.dh];
        int n9 = 0;
        for (n6 = 0; n6 < this.dh; ++n6) {
            for (n5 = 0; n5 < this.dw; ++n5) {
                n4 = nArray[n9];
                n3 = n4 >> 16 & 0xFF;
                n2 = n4 >> 8 & 0xFF;
                n = n4 & 0xFF;
                nArray[n9] = (n3 + n2 + n) / 8;
                ++n9;
            }
        }
        n9 = 0;
        for (n6 = 0; n6 < this.dh; ++n6) {
            n5 = (n6 + this.dh - 1) % this.dh * this.dw;
            n4 = n6 * this.dw;
            n3 = (n6 + 1) % this.dh * this.dw;
            for (n2 = 0; n2 < this.dw; ++n2) {
                n = (n2 + this.dw - 1) % this.dw;
                int n10 = n2;
                int n11 = (n2 + 1) % this.dw;
                this.xmap[n9] = nArray[n + n5] + nArray[n + n4] + nArray[n + n3] - nArray[n11 + n5] - nArray[n11 + n4] - nArray[n11 + n3];
                this.ymap[n9] = nArray[n + n3] + nArray[n10 + n3] + nArray[n11 + n3] - nArray[n + n5] - nArray[n10 + n5] - nArray[n11 + n5];
                ++n9;
            }
        }
        nArray = null;
        bufferedImage2 = super.filter(bufferedImage, bufferedImage2);
        this.ymap = null;
        this.xmap = null;
        return bufferedImage2;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = n;
        float f2 = n2;
        int n3 = n2 % this.dh * this.dw + n % this.dw;
        fArray[0] = (float)n + this.amount * (float)this.xmap[n3];
        fArray[1] = (float)n2 + this.amount * (float)this.ymap[n3];
    }

    public String toString() {
        return "Distort/Displace...";
    }
}

