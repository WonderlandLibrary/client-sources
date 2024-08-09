/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class SmartBlurFilter
extends AbstractBufferedImageOp {
    private int hRadius = 5;
    private int vRadius = 5;
    private int threshold = 10;

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        this.getRGB(bufferedImage, 0, 0, n, n2, nArray);
        Kernel kernel = GaussianFilter.makeKernel(this.hRadius);
        this.thresholdBlur(kernel, nArray, nArray2, n, n2, false);
        this.thresholdBlur(kernel, nArray2, nArray, n2, n, false);
        this.setRGB(bufferedImage2, 0, 0, n, n2, nArray);
        return bufferedImage2;
    }

    private void thresholdBlur(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl) {
        boolean bl2 = false;
        float[] fArray = kernel.getKernelData(null);
        int n3 = kernel.getWidth();
        int n4 = n3 / 2;
        for (int i = 0; i < n2; ++i) {
            int n5 = i * n;
            int n6 = i;
            for (int j = 0; j < n; ++j) {
                int n7;
                int n8;
                int n9;
                float f = 0.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.0f;
                int n10 = n4;
                int n11 = nArray[n5 + j];
                int n12 = n11 >> 24 & 0xFF;
                int n13 = n11 >> 16 & 0xFF;
                int n14 = n11 >> 8 & 0xFF;
                int n15 = n11 & 0xFF;
                float f5 = 0.0f;
                float f6 = 0.0f;
                float f7 = 0.0f;
                float f8 = 0.0f;
                for (n9 = -n4; n9 <= n4; ++n9) {
                    float f9 = fArray[n10 + n9];
                    if (f9 == 0.0f) continue;
                    n8 = j + n9;
                    if (0 > n8 || n8 >= n) {
                        n8 = j;
                    }
                    n7 = nArray[n5 + n8];
                    int n16 = n7 >> 24 & 0xFF;
                    int n17 = n7 >> 16 & 0xFF;
                    int n18 = n7 >> 8 & 0xFF;
                    int n19 = n7 & 0xFF;
                    int n20 = n12 - n16;
                    if (n20 >= -this.threshold && n20 <= this.threshold) {
                        f4 += f9 * (float)n16;
                        f5 += f9;
                    }
                    if ((n20 = n13 - n17) >= -this.threshold && n20 <= this.threshold) {
                        f += f9 * (float)n17;
                        f6 += f9;
                    }
                    if ((n20 = n14 - n18) >= -this.threshold && n20 <= this.threshold) {
                        f2 += f9 * (float)n18;
                        f7 += f9;
                    }
                    if ((n20 = n15 - n19) < -this.threshold || n20 > this.threshold) continue;
                    f3 += f9 * (float)n19;
                    f8 += f9;
                }
                f4 = f5 == 0.0f ? (float)n12 : f4 / f5;
                f = f6 == 0.0f ? (float)n13 : f / f6;
                f2 = f7 == 0.0f ? (float)n14 : f2 / f7;
                f3 = f8 == 0.0f ? (float)n15 : f3 / f8;
                n9 = bl ? PixelUtils.clamp((int)((double)f4 + 0.5)) : 255;
                int n21 = PixelUtils.clamp((int)((double)f + 0.5));
                n8 = PixelUtils.clamp((int)((double)f2 + 0.5));
                n7 = PixelUtils.clamp((int)((double)f3 + 0.5));
                nArray2[n6] = n9 << 24 | n21 << 16 | n8 << 8 | n7;
                n6 += n2;
            }
        }
    }

    public void setHRadius(int n) {
        this.hRadius = n;
    }

    public int getHRadius() {
        return this.hRadius;
    }

    public void setVRadius(int n) {
        this.vRadius = n;
    }

    public int getVRadius() {
        return this.vRadius;
    }

    public void setRadius(int n) {
        this.hRadius = this.vRadius = n;
    }

    public int getRadius() {
        return this.hRadius;
    }

    public void setThreshold(int n) {
        this.threshold = n;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public String toString() {
        return "Blur/Smart Blur...";
    }
}

