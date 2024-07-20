/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
import java.io.Serializable;

public abstract class AbstractReference2ShortFunction<K>
implements Reference2ShortFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractReference2ShortFunction() {
    }

    @Override
    public void defaultReturnValue(short rv) {
        this.defRetValue = rv;
    }

    @Override
    public short defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public short put(K key, short value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short removeShort(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Short get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Short.valueOf(this.getShort(k)) : null;
    }

    @Override
    @Deprecated
    public Short put(K ok, Short ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        short v = this.put(k, (short)ov);
        return containsKey ? Short.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Short remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        short v = this.removeShort(k);
        return containsKey ? Short.valueOf(v) : null;
    }
}

