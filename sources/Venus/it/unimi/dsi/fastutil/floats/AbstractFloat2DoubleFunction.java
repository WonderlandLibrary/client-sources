/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractFloat2DoubleFunction
implements Float2DoubleFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractFloat2DoubleFunction() {
    }

    @Override
    public void defaultReturnValue(double d) {
        this.defRetValue = d;
    }

    @Override
    public double defaultReturnValue() {
        return this.defRetValue;
    }
}

