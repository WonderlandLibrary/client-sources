/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
import java.io.Serializable;

public abstract class AbstractReference2ByteFunction<K>
implements Reference2ByteFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractReference2ByteFunction() {
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
    public byte put(K key, byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte removeByte(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Byte get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Byte.valueOf(this.getByte(k)) : null;
    }

    @Override
    @Deprecated
    public Byte put(K ok, Byte ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.put(k, (byte)ov);
        return containsKey ? Byte.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Byte remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        byte v = this.removeByte(k);
        return containsKey ? Byte.valueOf(v) : null;
    }
}

