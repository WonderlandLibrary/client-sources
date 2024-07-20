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
    public void defaultReturnValue(char rv) {
        this.defRetValue = rv;
    }

    @Override
    public char defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public char put(float key, char value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char remove(float key) {
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
    @Deprecated
    public Character get(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        return this.containsKey(k) ? Character.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Character put(Float ok, Character ov) {
        float k = ok.floatValue();
        boolean containsKey = this.containsKey(k);
        char v = this.put(k, ov.charValue());
        return containsKey ? Character.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Character remove(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        boolean containsKey = this.containsKey(k);
        char v = this.remove(k);
        return containsKey ? Character.valueOf(v) : null;
    }
}

