/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractDouble2DoubleFunction
implements Double2DoubleFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractDouble2DoubleFunction() {
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
    public double put(double key, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double remove(double key) {
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
        return this.containsKey((Double)ok);
    }

    @Override
    @Deprecated
    public Double get(Object ok) {
        if (ok == null) {
            return null;
        }
        double k = (Double)ok;
        return this.containsKey(k) ? Double.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Double put(Double ok, Double ov) {
        double k = ok;
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
        double k = (Double)ok;
        boolean containsKey = this.containsKey(k);
        double v = this.remove(k);
        return containsKey ? Double.valueOf(v) : null;
    }
}

