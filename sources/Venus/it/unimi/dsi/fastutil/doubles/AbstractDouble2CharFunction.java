/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
import java.io.Serializable;

public abstract class AbstractDouble2CharFunction
implements Double2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractDouble2CharFunction() {
    }

    @Override
    public void defaultReturnValue(char c) {
        this.defRetValue = c;
    }

    @Override
    public char defaultReturnValue() {
        return this.defRetValue;
    }
}

