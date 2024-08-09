/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import java.io.Serializable;

public abstract class AbstractDouble2ObjectFunction<V>
implements Double2ObjectFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractDouble2ObjectFunction() {
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

