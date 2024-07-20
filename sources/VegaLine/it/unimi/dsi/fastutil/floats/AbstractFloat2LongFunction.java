/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import java.io.Serializable;

public abstract class AbstractFloat2LongFunction
implements Float2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractFloat2LongFunction() {
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
    public long put(float key, long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long remove(float key) {
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
    public Long get(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        return this.containsKey(k) ? Long.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Long put(Float ok, Long ov) {
        float k = ok.floatValue();
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
        float k = ((Float)ok).floatValue();
        boolean containsKey = this.containsKey(k);
        long v = this.remove(k);
        return containsKey ? Long.valueOf(v) : null;
    }
}

