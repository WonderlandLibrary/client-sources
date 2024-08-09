/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;

public class GradientWipeFilter
extends AbstractBufferedImageOp {
    private float density = 0.0f;
    private float softness = 0.0f;
    private boolean invert;
    private BufferedImage mask;

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

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
        float f = this.density * (1.0f + this.softness);
        float f2 = 255.0f * (f - this.softness);
        float f3 = 255.0f * f;
        int[] nArray = new int[n];
        int[] nArray2 = new int[n3];
        for (int i = 0; i < n2; ++i) {
            this.getRGB(bufferedImage, 0, i, n, 1, nArray);
            this.getRGB(this.mask, 0, i % n4, n3, 1, nArray2);
            for (int j = 0; j < n; ++j) {
                int n5 = nArray2[j % n3];
                int n6 = nArray[j];
                int n7 = PixelUtils.brightness(n5);
                float f4 = ImageMath.smoothStep(f2, f3, n7);
                int n8 = (int)(255.0f * f4);
                if (this.invert) {
                    n8 = 255 - n8;
                }
                nArray[j] = n8 << 24 | n6 & 0xFFFFFF;
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Transitions/Gradient Wipe...";
    }
}

