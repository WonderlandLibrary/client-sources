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
    public void defaultReturnValue(V rv) {
        this.defRetValue = rv;
    }

    @Override
    public V defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public V put(double key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(double key) {
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
        return this.containsKey((Double)ok);
    }

    @Override
    public V get(Object ok) {
        if (ok == null) {
            return null;
        }
        double k = (Double)ok;
        return this.containsKey(k) ? (V)this.get(k) : null;
    }

    @Override
    @Deprecated
    public V put(Double ok, V ov) {
        double k = ok;
        boolean containsKey = this.containsKey(k);
        V v = this.put(k, ov);
        return (V)(containsKey ? v : null);
    }

    @Override
    public V remove(Object ok) {
        if (ok == null) {
            return null;
        }
        double k = (Double)ok;
        boolean containsKey = this.containsKey(k);
        V v = this.remove(k);
        return (V)(containsKey ? v : null);
    }
}

