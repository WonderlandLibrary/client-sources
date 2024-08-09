/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
import java.io.Serializable;

public abstract class AbstractShort2ObjectFunction<V>
implements Short2ObjectFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractShort2ObjectFunction() {
    }

    @Override
    public void defaultReturnValue(V v) {
        this.defRetValue = v;
    }

    @Override
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}

