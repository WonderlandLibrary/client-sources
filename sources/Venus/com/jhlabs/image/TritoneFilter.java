/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import java.awt.image.BufferedImage;

public class TritoneFilter
extends PointFilter {
    private int shadowColor = -16777216;
    private int midColor = -7829368;
    private int highColor = -1;
    private int[] lut;

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        float f;
        int n;
        this.lut = new int[256];
        for (n = 0; n < 128; ++n) {
            f = (float)n / 127.0f;
            this.lut[n] = ImageMath.mixColors(f, this.shadowColor, this.midColor);
        }
        for (n = 128; n < 256; ++n) {
            f = (float)(n - 127) / 128.0f;
            this.lut[n] = ImageMath.mixColors(f, this.midColor, this.highColor);
        }
        bufferedImage2 = super.filter(bufferedImage, bufferedImage2);
        this.lut = null;
        return bufferedImage2;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        return this.lut[PixelUtils.brightness(n3)];
    }

    public void setShadowColor(int n) {
        this.shadowColor = n;
    }

    public int getShadowColor() {
        return this.shadowColor;
    }

    public void setMidColor(int n) {
        this.midColor = n;
    }

    public int getMidColor() {
        return this.midColor;
    }

    public void setHighColor(int n) {
        this.highColor = n;
    }

    public int getHighColor() {
        return this.highColor;
    }

    public String toString() {
        return "Colors/Tritone...";
    }
}

