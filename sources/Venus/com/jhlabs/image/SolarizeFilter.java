/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransferFilter;

public class SolarizeFilter
extends TransferFilter {
    @Override
    protected float transferFunction(float f) {
        return f > 0.5f ? 2.0f * (f - 0.5f) : 2.0f * (0.5f - f);
    }

    public String toString() {
        return "Colors/Solarize";
    }
}

