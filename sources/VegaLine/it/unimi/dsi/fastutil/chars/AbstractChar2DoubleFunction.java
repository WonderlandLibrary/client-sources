/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractChar2DoubleFunction
implements Char2DoubleFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractChar2DoubleFunction() {
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
    public double put(char key, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double remove(char key) {
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
        return this.containsKey(((Character)ok).charValue());
    }

    @Override
    @Deprecated
    public Double get(Object ok) {
        if (ok == null) {
            return null;
        }
        char k = ((Character)ok).charValue();
        return this.containsKey(k) ? Double.valueOf(this.get(k)) : null;
    }

    @Override
    @Deprecated
    public Double put(Character ok, Double ov) {
        char k = ok.charValue();
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
        char k = ((Character)ok).charValue();
        boolean containsKey = this.containsKey(k);
        double v = this.remove(k);
        return containsKey ? Double.valueOf(v) : null;
    }
}

