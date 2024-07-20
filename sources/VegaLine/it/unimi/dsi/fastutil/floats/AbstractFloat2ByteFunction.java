/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
import java.io.Serializable;

public abstract class AbstractFloat2ByteFunction
implements Float2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractFloat2ByteFunction() {
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
    public byte put(float key, byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte remove(float key) {
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
    public Byte get(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        return this.containsKey(k) ? Byte.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Byte put(Float ok, Byte ov) {
        float k = ok.floatValue();
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
        float k = ((Float)ok).floatValue();
        boolean containsKey = this.containsKey(k);
        byte v = this.remove(k);
        return containsKey ? Byte.valueOf(v) : null;
    }
}

