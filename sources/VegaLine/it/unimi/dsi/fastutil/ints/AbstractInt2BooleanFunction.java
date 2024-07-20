/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractInt2BooleanFunction
implements Int2BooleanFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractInt2BooleanFunction() {
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
    public boolean put(int key, boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(int key) {
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
    public Boolean get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Boolean.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Boolean put(Integer ok, Boolean ov) {
        int k = ok;
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
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        boolean v = this.remove(k);
        return containsKey ? Boolean.valueOf(v) : null;
    }
}

