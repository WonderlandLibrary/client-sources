/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
import java.io.Serializable;

public abstract class AbstractShort2IntFunction
implements Short2IntFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractShort2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int rv) {
        this.defRetValue = rv;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public int put(short key, int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remove(short key) {
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
        return this.containsKey((Short)ok);
    }

    @Override
    @Deprecated
    public Integer get(Object ok) {
        if (ok == null) {
            return null;
        }
        short k = (Short)ok;
        return this.containsKey(k) ? Integer.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Integer put(Short ok, Integer ov) {
        short k = ok;
        boolean containsKey = this.containsKey(k);
        int v = this.put(k, (int)ov);
        return containsKey ? Integer.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Integer remove(Object ok) {
        if (ok == null) {
            return null;
        }
        short k = (Short)ok;
        boolean containsKey = this.containsKey(k);
        int v = this.remove(k);
        return containsKey ? Integer.valueOf(v) : null;
    }
}

