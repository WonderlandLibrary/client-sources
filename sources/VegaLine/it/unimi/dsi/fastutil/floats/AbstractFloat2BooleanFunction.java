/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractFloat2BooleanFunction
implements Float2BooleanFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractFloat2BooleanFunction() {
    }

    @Override
    public void defaultReturnValue(boolean rv) {
        this.defRetValue = rv;
    }

    @Override
    public boolean defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public boolean put(float key, boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(float key) {
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
    public Boolean get(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        return this.containsKey(k) ? Boolean.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Boolean put(Float ok, Boolean ov) {
        float k = ok.floatValue();
        boolean containsKey = this.containsKey(k);
        boolean v = this.put(k, (boolean)ov);
        return containsKey ? Boolean.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Boolean remove(Object ok) {
        if (ok == null) {
            return null;
        }
        float k = ((Float)ok).floatValue();
        boolean containsKey = this.containsKey(k);
        boolean v = this.remove(k);
        return containsKey ? Boolean.valueOf(v) : null;
    }
}

