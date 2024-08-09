/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.Gradient;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;

public class TextureFilter
extends PointFilter {
    private float scale = 32.0f;
    private float stretch = 1.0f;
    private float angle = 0.0f;
    public float amount = 1.0f;
    public float turbulence = 1.0f;
    public float gain = 0.5f;
    public float bias = 0.5f;
    public int operation;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;
    private Colormap colormap = new Gradient();
    private Function2D function = new Noise();

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setFunction(Function2D function2D) {
        this.function = function2D;
    }

    public Function2D getFunction() {
        return this.function;
    }

    public void setOperation(int n) {
        this.operation = n;
    }

    public int getOperation() {
        return this.operation;
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
        float f3 = (double)this.turbulence == 1.0 ? Noise.noise2(f, f2) : Noise.turbulence2(f /= this.scale, f2 /= this.scale * this.stretch, this.turbulence);
        f3 = f3 * 0.5f + 0.5f;
        f3 = ImageMath.gain(f3, this.gain);
        f3 = ImageMath.bias(f3, this.bias);
        f3 *= this.amount;
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
        if (this.operation != 0) {
            n4 = PixelUtils.combinePixels(n3, n4, this.operation);
        }
        return n4;
    }

    public String toString() {
        return "Texture/Noise...";
    }
}

