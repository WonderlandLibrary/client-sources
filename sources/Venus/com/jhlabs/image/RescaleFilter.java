/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransferFilter;

public class RescaleFilter
extends TransferFilter {
    private float scale = 1.0f;

    public RescaleFilter() {
    }

    public RescaleFilter(float f) {
        this.scale = f;
    }

    @Override
    protected float transferFunction(float f) {
        return f * this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
        this.initialized = false;
    }

    public float getScale() {
        return this.scale;
    }

    public String toString() {
        return "Colors/Rescale...";
    }
}

