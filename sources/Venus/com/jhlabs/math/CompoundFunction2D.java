/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function2D;

public abstract class CompoundFunction2D
implements Function2D {
    protected Function2D basis;

    public CompoundFunction2D(Function2D function2D) {
        this.basis = function2D;
    }

    public void setBasis(Function2D function2D) {
        this.basis = function2D;
    }

    public Function2D getBasis() {
        return this.basis;
    }
}

