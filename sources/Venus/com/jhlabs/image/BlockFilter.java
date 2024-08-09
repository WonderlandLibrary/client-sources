/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class BlockFilter
extends AbstractBufferedImageOp {
    private int blockSize = 2;

    public BlockFilter() {
    }

    public BlockFilter(int n) {
        this.blockSize = n;
    }

    public void setBlockSize(int n) {
        this.blockSize = n;
    }

    public int getBlockSize() {
        return this.blockSize;
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
        int[] nArray = new int[this.blockSize * this.blockSize];
        for (int i = 0; i < n2; i += this.blockSize) {
            for (int j = 0; j < n; j += this.blockSize) {
                int n4;
                int n5;
                int n6;
                int n7 = Math.min(this.blockSize, n - j);
                int n8 = Math.min(this.blockSize, n2 - i);
                int n9 = n7 * n8;
                this.getRGB(bufferedImage, j, i, n7, n8, nArray);
                int n10 = 0;
                int n11 = 0;
                int n12 = 0;
                int n13 = 0;
                for (n6 = 0; n6 < n8; ++n6) {
                    for (n5 = 0; n5 < n7; ++n5) {
                        n4 = nArray[n13];
                        n10 += n4 >> 16 & 0xFF;
                        n11 += n4 >> 8 & 0xFF;
                        n12 += n4 & 0xFF;
                        ++n13;
                    }
                }
                n4 = n10 / n9 << 16 | n11 / n9 << 8 | n12 / n9;
                n13 = 0;
                for (n6 = 0; n6 < n8; ++n6) {
                    for (n5 = 0; n5 < n7; ++n5) {
                        nArray[n13] = nArray[n13] & 0xFF000000 | n4;
                        ++n13;
                    }
                }
                this.setRGB(bufferedImage2, j, i, n7, n8, nArray);
            }
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Pixellate/Mosaic...";
    }
}

