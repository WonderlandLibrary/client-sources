/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
import java.io.Serializable;

public abstract class AbstractInt2ByteFunction
implements Int2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractInt2ByteFunction() {
    }

    @Override
    public void defaultReturnValue(byte rv) {
        this.defRetValue = rv;
    }

    @Override
    public byte defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public byte put(int key, byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte remove(int key) {
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
    public Byte get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Byte.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Byte put(Integer ok, Byte ov) {
        int k = ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.put(k, (byte)ov);
        return containsKey ? Byte.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Byte remove(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.remove(k);
        return containsKey ? Byte.valueOf(v) : null;
    }
}

