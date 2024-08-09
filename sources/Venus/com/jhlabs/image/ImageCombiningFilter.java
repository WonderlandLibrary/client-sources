/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class ImageCombiningFilter {
    public int filterRGB(int n, int n2, int n3, int n4) {
        int n5 = n3 >> 24 & 0xFF;
        int n6 = n3 >> 16 & 0xFF;
        int n7 = n3 >> 8 & 0xFF;
        int n8 = n3 & 0xFF;
        int n9 = n4 >> 24 & 0xFF;
        int n10 = n4 >> 16 & 0xFF;
        int n11 = n4 >> 8 & 0xFF;
        int n12 = n4 & 0xFF;
        int n13 = PixelUtils.clamp(n6 + n10);
        int n14 = PixelUtils.clamp(n6 + n10);
        int n15 = PixelUtils.clamp(n6 + n10);
        return n5 << 24 | n13 << 16 | n14 << 8 | n15;
    }

    public ImageProducer filter(Image image, Image image2, int n, int n2, int n3, int n4) {
        int[] nArray = new int[n3 * n4];
        int[] nArray2 = new int[n3 * n4];
        int[] nArray3 = new int[n3 * n4];
        PixelGrabber pixelGrabber = new PixelGrabber(image, n, n2, n3, n4, nArray, 0, n3);
        PixelGrabber pixelGrabber2 = new PixelGrabber(image2, n, n2, n3, n4, nArray2, 0, n3);
        try {
            pixelGrabber.grabPixels();
            pixelGrabber2.grabPixels();
        } catch (InterruptedException interruptedException) {
            System.err.println("interrupted waiting for pixels!");
            return null;
        }
        if ((pixelGrabber.status() & 0x80) != 0) {
            System.err.println("image fetch aborted or errored");
            return null;
        }
        if ((pixelGrabber2.status() & 0x80) != 0) {
            System.err.println("image fetch aborted or errored");
            return null;
        }
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                int n5 = i * n3 + j;
                nArray3[n5] = this.filterRGB(n + j, n2 + i, nArray[n5], nArray2[n5]);
            }
        }
        return new MemoryImageSource(n3, n4, nArray3, 0, n3);
    }
}

