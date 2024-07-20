/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2LongFunction;
import java.io.Serializable;

public abstract class AbstractObject2LongFunction<K>
implements Object2LongFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractObject2LongFunction() {
    }

    @Override
    public void defaultReturnValue(long rv) {
        this.defRetValue = rv;
    }

    @Override
    public long defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public long put(K key, long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long removeLong(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Long get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Long.valueOf(this.getLong(k)) : null;
    }

    @Override
    @Deprecated
    public Long put(K ok, Long ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        long v = this.put(k, (long)ov);
        return containsKey ? Long.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Long remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        long v = this.removeLong(k);
        return containsKey ? Long.valueOf(v) : null;
    }
}

