/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.CompoundFunction2D;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import com.jhlabs.math.TurbulenceFunction;

public class MarbleFunction
extends CompoundFunction2D {
    public MarbleFunction() {
        super(new TurbulenceFunction(new Noise(), 6.0f));
    }

    public MarbleFunction(Function2D function2D) {
        super(function2D);
    }

    @Override
    public float evaluate(float f, float f2) {
        return (float)Math.pow(0.5 * (Math.sin(8.0 * (double)this.basis.evaluate(f, f2)) + 1.0), 0.77);
    }
}

