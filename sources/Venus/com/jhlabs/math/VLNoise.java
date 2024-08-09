/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;

public class VLNoise
implements Function2D {
    private float distortion = 10.0f;

    public void setDistortion(float f) {
        this.distortion = f;
    }

    public float getDistortion() {
        return this.distortion;
    }

    @Override
    public float evaluate(float f, float f2) {
        float f3 = Noise.noise2(f + 0.5f, f2) * this.distortion;
        float f4 = Noise.noise2(f, f2 + 0.5f) * this.distortion;
        return Noise.noise2(f + f3, f2 + f4);
    }
}

