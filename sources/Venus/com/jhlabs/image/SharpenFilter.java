/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ConvolveFilter;

public class SharpenFilter
extends ConvolveFilter {
    private static float[] sharpenMatrix = new float[]{0.0f, -0.2f, 0.0f, -0.2f, 1.8f, -0.2f, 0.0f, -0.2f, 0.0f};

    public SharpenFilter() {
        super(sharpenMatrix);
    }

    @Override
    public String toString() {
        return "Blur/Sharpen";
    }
}

