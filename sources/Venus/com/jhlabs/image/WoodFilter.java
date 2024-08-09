/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.Noise;

public class WoodFilter
extends PointFilter {
    private float scale = 200.0f;
    private float stretch = 10.0f;
    private float angle = 1.5707964f;
    private float rings = 0.5f;
    private float turbulence = 0.0f;
    private float fibres = 0.5f;
    private float gain = 0.8f;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;
    private Colormap colormap = new LinearColormap(-1719148, -6784175);

    public void setRings(float f) {
        this.rings = f;
    }

    public float getRings() {
        return this.rings;
    }

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

    public void setFibres(float f) {
        this.fibres = f;
    }

    public float getFibres() {
        return this.fibres;
    }

    public void setGain(float f) {
        this.gain = f;
    }

    public float getGain() {
        return this.gain;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4;
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        float f3 = Noise.noise2(f /= this.scale, f2 /= this.scale * this.stretch);
        f3 += 0.1f * this.turbulence * Noise.noise2(f * 0.05f, f2 * 20.0f);
        f3 = f3 * 0.5f + 0.5f;
        f3 *= this.rings * 50.0f;
        f3 -= (float)((int)f3);
        f3 *= 1.0f - ImageMath.smoothStep(this.gain, 1.0f, f3);
        f3 += this.fibres * Noise.noise2(f * this.scale, f2 * 50.0f);
        int n5 = n3 & 0xFF000000;
        if (this.colormap != null) {
            n4 = this.colormap.getColor(f3);
        } else {
            n4 = PixelUtils.clamp((int)(f3 * 255.0f));
            int n6 = n4 << 16;
            int n7 = n4 << 8;
            int n8 = n4;
            n4 = n5 | n6 | n7 | n8;
        }
        return n4;
    }

    public String toString() {
        return "Texture/Wood...";
    }
}

