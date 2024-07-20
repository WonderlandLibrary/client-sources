/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
import java.io.Serializable;

public abstract class AbstractByte2ByteFunction
implements Byte2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractByte2ByteFunction() {
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
    public byte put(byte key, byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte remove(byte key) {
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
    public Byte get(Object ok) {
        if (ok == null) {
            return null;
        }
        byte k = (Byte)ok;
        return this.containsKey(k) ? Byte.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Byte put(Byte ok, Byte ov) {
        byte k = ok;
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
        byte k = (Byte)ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.remove(k);
        return containsKey ? Byte.valueOf(v) : null;
    }
}

