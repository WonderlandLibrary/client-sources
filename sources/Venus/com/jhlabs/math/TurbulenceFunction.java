/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.CompoundFunction2D;
import com.jhlabs.math.Function2D;

public class TurbulenceFunction
extends CompoundFunction2D {
    private float octaves;

    public TurbulenceFunction(Function2D function2D, float f) {
        super(function2D);
        this.octaves = f;
    }

    public void setOctaves(float f) {
        this.octaves = f;
    }

    public float getOctaves() {
        return this.octaves;
    }

    @Override
    public float evaluate(float f, float f2) {
        float f3 = 0.0f;
        for (float f4 = 1.0f; f4 <= this.octaves; f4 *= 2.0f) {
            f3 += Math.abs(this.basis.evaluate(f4 * f, f4 * f2)) / f4;
        }
        return f3;
    }
}

