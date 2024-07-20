/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
import java.io.Serializable;

public abstract class AbstractLong2FloatFunction
implements Long2FloatFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractLong2FloatFunction() {
    }

    @Override
    public void defaultReturnValue(float rv) {
        this.defRetValue = rv;
    }

    @Override
    public float defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public float put(long key, float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float remove(long key) {
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
    public Float get(Object ok) {
        if (ok == null) {
            return null;
        }
        long k = (Long)ok;
        return this.containsKey(k) ? Float.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Float put(Long ok, Float ov) {
        long k = ok;
        boolean containsKey = this.containsKey(k);
        float v = this.put(k, ov.floatValue());
        return containsKey ? Float.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Float remove(Object ok) {
        if (ok == null) {
            return null;
        }
        long k = (Long)ok;
        boolean containsKey = this.containsKey(k);
        float v = this.remove(k);
        return containsKey ? Float.valueOf(v) : null;
    }
}

