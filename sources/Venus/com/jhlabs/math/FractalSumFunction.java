/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.CompoundFunction2D;
import com.jhlabs.math.Function2D;

public class FractalSumFunction
extends CompoundFunction2D {
    private float octaves = 1.0f;

    public FractalSumFunction(Function2D function2D) {
        super(function2D);
    }

    @Override
    public float evaluate(float f, float f2) {
        float f3 = 0.0f;
        for (float f4 = 1.0f; f4 <= this.octaves; f4 *= 2.0f) {
            f3 += this.basis.evaluate(f4 * f, f4 * f2) / f4;
        }
        return f3;
    }
}

