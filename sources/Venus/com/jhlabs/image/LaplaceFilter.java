/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;

public class LaplaceFilter
extends AbstractBufferedImageOp {
    private void brightness(int[] nArray) {
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            nArray[i] = (n2 + n3 + n4) / 3;
        }
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int[] nArray;
        int n;
        int n2;
        int n3 = bufferedImage.getWidth();
        int n4 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray2 = null;
        int[] nArray3 = null;
        int[] nArray4 = null;
        int[] nArray5 = new int[n3];
        nArray2 = this.getRGB(bufferedImage, 0, 0, n3, 1, nArray2);
        nArray3 = this.getRGB(bufferedImage, 0, 0, n3, 1, nArray3);
        this.brightness(nArray2);
        this.brightness(nArray3);
        for (n2 = 0; n2 < n4; ++n2) {
            if (n2 < n4 - 1) {
                nArray4 = this.getRGB(bufferedImage, 0, n2 + 1, n3, 1, nArray4);
                this.brightness(nArray4);
            }
            nArray5[n3 - 1] = -16777216;
            nArray5[0] = -16777216;
            for (int i = 1; i < n3 - 1; ++i) {
                int n5;
                n = nArray3[i - 1];
                int n6 = nArray2[i];
                int n7 = nArray4[i];
                int n8 = nArray3[i + 1];
                int n9 = nArray3[i];
                int n10 = Math.max(Math.max(n, n6), Math.max(n7, n8));
                int n11 = Math.min(Math.min(n, n6), Math.min(n7, n8));
                int n12 = (int)(0.5f * (float)Math.max(n10 - n9, n9 - n11));
                nArray5[i] = n5 = nArray2[i - 1] + nArray2[i] + nArray2[i + 1] + nArray3[i - 1] - 8 * nArray3[i] + nArray3[i + 1] + nArray4[i - 1] + nArray4[i] + nArray4[i + 1] > 0 ? n12 : 128 + n12;
            }
            this.setRGB(bufferedImage2, 0, n2, n3, 1, nArray5);
            nArray = nArray2;
            nArray2 = nArray3;
            nArray3 = nArray4;
            nArray4 = nArray;
        }
        nArray2 = this.getRGB(bufferedImage2, 0, 0, n3, 1, nArray2);
        nArray3 = this.getRGB(bufferedImage2, 0, 0, n3, 1, nArray3);
        for (n2 = 0; n2 < n4; ++n2) {
            if (n2 < n4 - 1) {
                nArray4 = this.getRGB(bufferedImage2, 0, n2 + 1, n3, 1, nArray4);
            }
            nArray5[n3 - 1] = -16777216;
            nArray5[0] = -16777216;
            for (int i = 1; i < n3 - 1; ++i) {
                n = nArray3[i];
                n = n <= 128 && (nArray2[i - 1] > 128 || nArray2[i] > 128 || nArray2[i + 1] > 128 || nArray3[i - 1] > 128 || nArray3[i + 1] > 128 || nArray4[i - 1] > 128 || nArray4[i] > 128 || nArray4[i + 1] > 128) ? (n >= 128 ? n - 128 : n) : 0;
                nArray5[i] = 0xFF000000 | n << 16 | n << 8 | n;
            }
            this.setRGB(bufferedImage2, 0, n2, n3, 1, nArray5);
            nArray = nArray2;
            nArray2 = nArray3;
            nArray3 = nArray4;
            nArray4 = nArray;
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Edges/Laplace...";
    }
}

