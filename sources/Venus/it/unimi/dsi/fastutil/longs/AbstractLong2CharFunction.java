/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2CharFunction;
import java.io.Serializable;

public abstract class AbstractLong2CharFunction
implements Long2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractLong2CharFunction() {
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

