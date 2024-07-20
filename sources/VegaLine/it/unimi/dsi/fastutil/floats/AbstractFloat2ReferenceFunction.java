/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
import java.io.Serializable;

public abstract class AbstractFloat2ReferenceFunction<V>
implements Float2ReferenceFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractFloat2ReferenceFunction() {
    }

    @Override
    public void defaultReturnValue(V rv) {
        this.defRetValue = rv;
    }

    @Override
    public V defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public V put(float key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(float key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.containsKey(((Float)ok).floatValue());
    }

    @Override
    public V get(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        return this.containsKey(k) ? (V)this.get(k) : null;
    }

    @Override
    @Deprecated
    public V put(Float ok, V ov) {
        float k = ok.floatValue();
        boolean containsKey = this.containsKey(k);
        V v = this.put(k, ov);
        return (V)(containsKey ? v : null);
    }

    @Override
    public V remove(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        boolean containsKey = this.containsKey(k);
        V v = this.remove(k);
        return (V)(containsKey ? v : null);
    }
}

