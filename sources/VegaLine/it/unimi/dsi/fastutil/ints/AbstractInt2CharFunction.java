/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2CharFunction;
import java.io.Serializable;

public abstract class AbstractInt2CharFunction
implements Int2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractInt2CharFunction() {
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
    public char put(int key, char value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char remove(int key) {
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
    @Deprecated
    public Character get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Character.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Character put(Integer ok, Character ov) {
        int k = ok;
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
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        char v = this.remove(k);
        return containsKey ? Character.valueOf(v) : null;
    }
}

