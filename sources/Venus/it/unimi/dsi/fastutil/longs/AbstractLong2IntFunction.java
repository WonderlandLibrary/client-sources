/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2IntFunction;
import java.io.Serializable;

public abstract class AbstractLong2IntFunction
implements Long2IntFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractLong2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int n) {
        this.defRetValue = n;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}

