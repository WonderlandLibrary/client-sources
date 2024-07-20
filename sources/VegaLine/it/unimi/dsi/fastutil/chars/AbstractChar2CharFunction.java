/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharFunction;
import java.io.Serializable;

public abstract class AbstractChar2CharFunction
implements Char2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractChar2CharFunction() {
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
    public char put(char key, char value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char remove(char key) {
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
    @Deprecated
    public Character get(Object ok) {
        if (ok == null) {
            return null;
        }
        char k = ((Character)ok).charValue();
        return this.containsKey(k) ? Character.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Character put(Character ok, Character ov) {
        char k = ok.charValue();
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
        char k = ((Character)ok).charValue();
        boolean containsKey = this.containsKey(k);
        char v = this.remove(k);
        return containsKey ? Character.valueOf(v) : null;
    }
}

