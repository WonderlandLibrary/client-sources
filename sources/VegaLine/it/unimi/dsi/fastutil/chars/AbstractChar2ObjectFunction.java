/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
import java.io.Serializable;

public abstract class AbstractChar2ObjectFunction<V>
implements Char2ObjectFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractChar2ObjectFunction() {
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
    public V put(char key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(char key) {
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
        return this.containsKey(((Character)ok).charValue());
    }

    @Override
    public V get(Object ok) {
        if (ok == null) {
            return null;
        }
        char k = ((Character)ok).charValue();
        return this.containsKey(k) ? (V)this.get(k) : null;
    }

    @Override
    @Deprecated
    public V put(Character ok, V ov) {
        char k = ok.charValue();
        boolean containsKey = this.containsKey(k);
        V v = this.put(k, ov);
        return (V)(containsKey ? v : null);
    }

    @Override
    public V remove(Object ok) {
        if (ok == null) {
            return null;
        }
        char k = ((Character)ok).charValue();
        boolean containsKey = this.containsKey(k);
        V v = this.remove(k);
        return (V)(containsKey ? v : null);
    }
}

