/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractDouble2DoubleFunction
implements Double2DoubleFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractDouble2DoubleFunction() {
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

