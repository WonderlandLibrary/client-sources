/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2CharFunction;
import java.io.Serializable;

public abstract class AbstractInt2CharFunction
implements Int2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractInt2CharFunction() {
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

