/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;

public class SaturationFilter
extends PointFilter {
    public float amount = 1.0f;

    public SaturationFilter() {
    }

    public SaturationFilter(float f) {
        this.amount = f;
        this.canFilterIndexColorModel = true;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        if (this.amount != 1.0f) {
            int n4 = n3 & 0xFF000000;
            int n5 = n3 >> 16 & 0xFF;
            int n6 = n3 >> 8 & 0xFF;
            int n7 = n3 & 0xFF;
            int n8 = (n5 + n6 + n7) / 3;
            n5 = PixelUtils.clamp((int)((float)n8 + this.amount * (float)(n5 - n8)));
            n6 = PixelUtils.clamp((int)((float)n8 + this.amount * (float)(n6 - n8)));
            n7 = PixelUtils.clamp((int)((float)n8 + this.amount * (float)(n7 - n8)));
            return n4 | n5 << 16 | n6 << 8 | n7;
        }
        return n3;
    }

    public String toString() {
        return "Colors/Saturation...";
    }
}

