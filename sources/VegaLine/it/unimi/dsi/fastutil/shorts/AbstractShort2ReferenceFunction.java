/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
import java.io.Serializable;

public abstract class AbstractShort2ReferenceFunction<V>
implements Short2ReferenceFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractShort2ReferenceFunction() {
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
    public V put(short key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(short key) {
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
        return this.containsKey((Short)ok);
    }

    @Override
    public V get(Object ok) {
        if (ok == null) {
            return null;
        }
        short k = (Short)ok;
        return this.containsKey(k) ? (V)this.get(k) : null;
    }

    @Override
    @Deprecated
    public V put(Short ok, V ov) {
        short k = ok;
        boolean containsKey = this.containsKey(k);
        V v = this.put(k, ov);
        return (V)(containsKey ? v : null);
    }

    @Override
    public V remove(Object ok) {
        if (ok == null) {
            return null;
        }
        short k = (Short)ok;
        boolean containsKey = this.containsKey(k);
        V v = this.remove(k);
        return (V)(containsKey ? v : null);
    }
}

