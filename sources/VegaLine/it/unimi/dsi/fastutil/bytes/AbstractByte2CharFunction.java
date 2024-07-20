/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
import java.io.Serializable;

public abstract class AbstractByte2CharFunction
implements Byte2CharFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractByte2CharFunction() {
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
    public char put(byte key, char value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char remove(byte key) {
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
        return this.containsKey((Byte)ok);
    }

    @Override
    @Deprecated
    public Character get(Object ok) {
        if (ok == null) {
            return null;
        }
        byte k = (Byte)ok;
        return this.containsKey(k) ? Character.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Character put(Byte ok, Character ov) {
        byte k = ok;
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
        byte k = (Byte)ok;
        boolean containsKey = this.containsKey(k);
        char v = this.remove(k);
        return containsKey ? Character.valueOf(v) : null;
    }
}

