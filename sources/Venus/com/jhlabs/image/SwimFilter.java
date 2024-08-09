/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import com.jhlabs.math.Noise;

public class SwimFilter
extends TransformFilter {
    private float scale = 32.0f;
    private float stretch = 1.0f;
    private float angle = 0.0f;
    private float amount = 1.0f;
    private float turbulence = 1.0f;
    private float time = 0.0f;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
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

    public void setTime(float f) {
        this.time = f;
    }

    public float getTime() {
        return this.time;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        f /= this.scale;
        f2 /= this.scale * this.stretch;
        if (this.turbulence == 1.0f) {
            fArray[0] = (float)n + this.amount * Noise.noise3(f + 0.5f, f2, this.time);
            fArray[1] = (float)n2 + this.amount * Noise.noise3(f, f2 + 0.5f, this.time);
        } else {
            fArray[0] = (float)n + this.amount * Noise.turbulence3(f + 0.5f, f2, this.turbulence, this.time);
            fArray[1] = (float)n2 + this.amount * Noise.turbulence3(f, f2 + 0.5f, this.turbulence, this.time);
        }
    }

    public String toString() {
        return "Distort/Swmpp...";
    }
}

