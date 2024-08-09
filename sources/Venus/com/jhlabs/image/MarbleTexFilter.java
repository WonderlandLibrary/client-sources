/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.Noise;

public class MarbleTexFilter
extends PointFilter {
    private float scale = 32.0f;
    private float stretch = 1.0f;
    private float angle = 0.0f;
    private float turbulence = 1.0f;
    private float turbulenceFactor = 0.5f;
    private Colormap colormap;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setStretch(float f) {
        this.stretch = f;
    }

    public float getStretch() {
        return this.stretch;
    }

    public void setAngle(float f) {
        this.angle = f;
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        this.m00 = f2;
        this.m01 = f3;
        this.m10 = -f3;
        this.m11 = f2;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    public void setTurbulenceFactor(float f) {
        this.turbulenceFactor = f;
    }

    public float getTurbulenceFactor() {
        return this.turbulenceFactor;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f;
        float f2 = this.m00 * (float)n + this.m01 * (float)n2;
        float f3 = this.m10 * (float)n + this.m11 * (float)n2;
        f2 /= this.scale * this.stretch;
        f3 /= this.scale;
        int n4 = n3 & 0xFF000000;
        if (this.colormap != null) {
            float f4 = this.turbulenceFactor * Noise.turbulence2(f2, f3, this.turbulence);
            float f5 = 3.0f * this.turbulenceFactor * f4 + f3;
            f5 = (float)Math.sin((double)f5 * Math.PI);
            float f6 = (float)Math.sin(40.0 * (double)f4);
            f5 = (float)((double)f5 + 0.2 * (double)f6);
            return this.colormap.getColor(f5);
        }
        float f7 = this.turbulenceFactor * Noise.turbulence2(f2, f3, this.turbulence);
        float f8 = (float)Math.sin(Math.sin(8.0 * (double)f7 + (double)(7.0f * f2) + 3.0 * (double)f3));
        float f9 = f = Math.abs(f8);
        float f10 = (float)Math.sin(40.0 * (double)f7);
        f10 = Math.abs(f10);
        float f11 = 0.6f * f10 + 0.3f;
        float f12 = 0.2f * f10 + 0.8f;
        float f13 = 0.15f * f10 + 0.85f;
        float f14 = 0.5f * (float)Math.pow(Math.abs(f), 0.3);
        f = (float)Math.pow(0.5 * ((double)f + 1.0), 0.6) * f11;
        f9 = (float)Math.pow(0.5 * ((double)f9 + 1.0), 0.6) * f12;
        float f15 = (0.5f * f + 0.35f * f9) * 2.0f * f14;
        float f16 = (0.25f * f + 0.35f * f9) * 2.0f * f14;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n5 = PixelUtils.clamp((int)((float)n5 * f15));
        n6 = PixelUtils.clamp((int)((float)n6 * (f14 *= Math.max(f, f9) * f13)));
        n7 = PixelUtils.clamp((int)((float)n7 * f16));
        return n3 & 0xFF000000 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Texture/Marble Texture...";
    }
}

