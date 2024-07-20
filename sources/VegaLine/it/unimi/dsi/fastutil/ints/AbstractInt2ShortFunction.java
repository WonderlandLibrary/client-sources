/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
import java.io.Serializable;

public abstract class AbstractInt2ShortFunction
implements Int2ShortFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractInt2ShortFunction() {
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
    public short put(int key, short value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short remove(int key) {
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
    public Short get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Short.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Short put(Integer ok, Short ov) {
        int k = ok;
        boolean containsKey = this.containsKey(k);
        short v = this.put(k, (short)ov);
        return containsKey ? Short.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Short remove(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        short v = this.remove(k);
        return containsKey ? Short.valueOf(v) : null;
    }
}

