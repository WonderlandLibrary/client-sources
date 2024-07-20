/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2LongFunction;
import java.io.Serializable;

public abstract class AbstractLong2LongFunction
implements Long2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractLong2LongFunction() {
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
    public long put(long key, long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long remove(long key) {
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
        return this.containsKey((Long)ok);
    }

    @Override
    @Deprecated
    public Long get(Object ok) {
        if (ok == null) {
            return null;
        }
        long k = (Long)ok;
        return this.containsKey(k) ? Long.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Long put(Long ok, Long ov) {
        long k = ok;
        boolean containsKey = this.containsKey(k);
        long v = this.put(k, (long)ov);
        return containsKey ? Long.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Long remove(Object ok) {
        if (ok == null) {
            return null;
        }
        long k = (Long)ok;
        boolean containsKey = this.containsKey(k);
        long v = this.remove(k);
        return containsKey ? Long.valueOf(v) : null;
    }
}

