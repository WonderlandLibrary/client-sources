/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
import java.io.Serializable;

public abstract class AbstractByte2IntFunction
implements Byte2IntFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractByte2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int rv) {
        this.defRetValue = rv;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public int put(byte key, int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remove(byte key) {
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
    public Integer get(Object ok) {
        if (ok == null) {
            return null;
        }
        byte k = (Byte)ok;
        return this.containsKey(k) ? Integer.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Integer put(Byte ok, Integer ov) {
        byte k = ok;
        boolean containsKey = this.containsKey(k);
        int v = this.put(k, (int)ov);
        return containsKey ? Integer.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Integer remove(Object ok) {
        if (ok == null) {
            return null;
        }
        byte k = (Byte)ok;
        boolean containsKey = this.containsKey(k);
        int v = this.remove(k);
        return containsKey ? Integer.valueOf(v) : null;
    }
}

