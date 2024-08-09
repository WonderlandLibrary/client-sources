/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransferFilter;

public class ContrastFilter
extends TransferFilter {
    private float brightness = 1.0f;
    private float contrast = 1.0f;

    @Override
    protected float transferFunction(float f) {
        f *= this.brightness;
        f = (f - 0.5f) * this.contrast + 0.5f;
        return f;
    }

    public void setBrightness(float f) {
        this.brightness = f;
        this.initialized = false;
    }

    public float getBrightness() {
        return this.brightness;
    }

    public void setContrast(float f) {
        this.contrast = f;
        this.initialized = false;
    }

    public float getContrast() {
        return this.contrast;
    }

    public String toString() {
        return "Colors/Contrast...";
    }
}

