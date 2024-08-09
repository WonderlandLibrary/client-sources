/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;

public class HalftoneFilter
extends AbstractBufferedImageOp {
    private float softness = 0.1f;
    private boolean invert;
    private boolean monochrome;
    private BufferedImage mask;

    public void setSoftness(float f) {
        this.softness = f;
    }

    public float getSoftness() {
        return this.softness;
    }

    public void setMask(BufferedImage bufferedImage) {
        this.mask = bufferedImage;
    }

    public BufferedImage getMask() {
        return this.mask;
    }

    public void setInvert(boolean bl) {
        this.invert = bl;
    }

    public boolean getInvert() {
        return this.invert;
    }

    public void setMonochrome(boolean bl) {
        this.monochrome = bl;
    }

    public boolean getMonochrome() {
        return this.monochrome;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        if (this.mask == null) {
            return bufferedImage2;
        }
        int n3 = this.mask.getWidth();
        int n4 = this.mask.getHeight();
        float f = 255.0f * this.softness;
        int[] nArray = new int[n];
        int[] nArray2 = new int[n3];
        for (int i = 0; i < n2; ++i) {
            this.getRGB(bufferedImage, 0, i, n, 1, nArray);
            this.getRGB(this.mask, 0, i % n4, n3, 1, nArray2);
            for (int j = 0; j < n; ++j) {
                int n5;
                int n6;
                int n7;
                int n8 = nArray2[j % n3];
                int n9 = nArray[j];
                if (this.invert) {
                    n8 ^= 0xFFFFFF;
                }
                if (this.monochrome) {
                    n7 = PixelUtils.brightness(n8);
                    n6 = PixelUtils.brightness(n9);
                    float f2 = 1.0f - ImageMath.smoothStep((float)n6 - f, (float)n6 + f, n7);
                    n5 = (int)(255.0f * f2);
                    nArray[j] = n9 & 0xFF000000 | n5 << 16 | n5 << 8 | n5;
                    continue;
                }
                n7 = n9 >> 16 & 0xFF;
                n6 = n9 >> 8 & 0xFF;
                int n10 = n9 & 0xFF;
                n5 = n8 >> 16 & 0xFF;
                int n11 = n8 >> 8 & 0xFF;
                int n12 = n8 & 0xFF;
                int n13 = (int)(255.0f * (1.0f - ImageMath.smoothStep((float)n7 - f, (float)n7 + f, n5)));
                int n14 = (int)(255.0f * (1.0f - ImageMath.smoothStep((float)n6 - f, (float)n6 + f, n11)));
                int n15 = (int)(255.0f * (1.0f - ImageMath.smoothStep((float)n10 - f, (float)n10 + f, n12)));
                nArray[j] = n9 & 0xFF000000 | n13 << 16 | n14 << 8 | n15;
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Stylize/Halftone...";
    }
}

