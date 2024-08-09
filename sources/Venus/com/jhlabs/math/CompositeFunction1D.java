/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function1D;

public class CompositeFunction1D
implements Function1D {
    private Function1D f1;
    private Function1D f2;

    public CompositeFunction1D(Function1D function1D, Function1D function1D2) {
        this.f1 = function1D;
        this.f2 = function1D2;
    }

    @Override
    public float evaluate(float f) {
        return this.f1.evaluate(this.f2.evaluate(f));
    }
}

