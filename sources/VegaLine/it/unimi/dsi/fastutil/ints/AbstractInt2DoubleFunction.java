/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractInt2DoubleFunction
implements Int2DoubleFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractInt2DoubleFunction() {
    }

    @Override
    public void defaultReturnValue(double rv) {
        this.defRetValue = rv;
    }

    @Override
    public double defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public double put(int key, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double remove(int key) {
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
    public Double get(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        return this.containsKey(k) ? Double.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Double put(Integer ok, Double ov) {
        int k = ok;
        boolean containsKey = this.containsKey(k);
        double v = this.put(k, (double)ov);
        return containsKey ? Double.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Double remove(Object ok) {
        if (ok == null) {
            return null;
        }
        int k = (Integer)ok;
        boolean containsKey = this.containsKey(k);
        double v = this.remove(k);
        return containsKey ? Double.valueOf(v) : null;
    }
}

