/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import java.io.Serializable;

public abstract class AbstractInt2ObjectFunction<V>
implements Int2ObjectFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractInt2ObjectFunction() {
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
    public V put(int key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(int key) {
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
        return this.containsKey((Integer)ok);
    }

    @Override
    public V get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? (V)this.get(k) : null;
    }

    @Override
    @Deprecated
    public V put(Integer ok, V ov) {
        int k = ok;
        boolean containsKey = this.containsKey(k);
        V v = this.put(k, ov);
        return (V)(containsKey ? v : null);
    }

    @Override
    public V remove(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        V v = this.remove(k);
        return (V)(containsKey ? v : null);
    }
}

