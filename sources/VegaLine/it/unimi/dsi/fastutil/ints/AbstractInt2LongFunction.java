/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import java.io.Serializable;

public abstract class AbstractInt2LongFunction
implements Int2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractInt2LongFunction() {
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
    public long put(int key, long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long remove(int key) {
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
    public Long get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Long.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Long put(Integer ok, Long ov) {
        int k = ok;
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
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        long v = this.remove(k);
        return containsKey ? Long.valueOf(v) : null;
    }
}

