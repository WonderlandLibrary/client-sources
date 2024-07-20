/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
import java.io.Serializable;

public abstract class AbstractShort2ByteFunction
implements Short2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractShort2ByteFunction() {
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
    public byte put(short key, byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte remove(short key) {
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
    @Deprecated
    public Byte get(Object ok) {
        if (ok == null) {
            return null;
        }
        short k = (Short)ok;
        return this.containsKey(k) ? Byte.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Byte put(Short ok, Byte ov) {
        short k = ok;
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
        short k = (Short)ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.remove(k);
        return containsKey ? Byte.valueOf(v) : null;
    }
}

