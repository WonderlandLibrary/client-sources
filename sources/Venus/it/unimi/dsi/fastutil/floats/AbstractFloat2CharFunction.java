/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2CharFunction;
import java.io.Serializable;

public abstract class AbstractFloat2CharFunction
implements Float2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractFloat2CharFunction() {
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

