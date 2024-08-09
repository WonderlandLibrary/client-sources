/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.GaussianFilter;
import java.awt.image.BufferedImage;

public class HighPassFilter
extends GaussianFilter {
    public HighPassFilter() {
        this.radius = 10.0f;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        bufferedImage.getRGB(0, 0, n, n2, nArray, 0, n);
        if (this.radius > 0.0f) {
            HighPassFilter.convolveAndTranspose(this.kernel, nArray, nArray2, n, n2, this.alpha, this.alpha && this.premultiplyAlpha, false, CLAMP_EDGES);
            HighPassFilter.convolveAndTranspose(this.kernel, nArray2, nArray, n2, n, this.alpha, false, this.alpha && this.premultiplyAlpha, CLAMP_EDGES);
        }
        bufferedImage.getRGB(0, 0, n, n2, nArray2, 0, n);
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = nArray2[n3];
                int n5 = n4 >> 16 & 0xFF;
                int n6 = n4 >> 8 & 0xFF;
                int n7 = n4 & 0xFF;
                int n8 = nArray[n3];
                int n9 = n8 >> 16 & 0xFF;
                int n10 = n8 >> 8 & 0xFF;
                int n11 = n8 & 0xFF;
                n5 = (n5 + 255 - n9) / 2;
                n6 = (n6 + 255 - n10) / 2;
                n7 = (n7 + 255 - n11) / 2;
                nArray[n3] = n4 & 0xFF000000 | n5 << 16 | n6 << 8 | n7;
                ++n3;
            }
        }
        bufferedImage2.setRGB(0, 0, n, n2, nArray, 0, n);
        return bufferedImage2;
    }

    @Override
    public String toString() {
        return "Blur/High Pass...";
    }
}

