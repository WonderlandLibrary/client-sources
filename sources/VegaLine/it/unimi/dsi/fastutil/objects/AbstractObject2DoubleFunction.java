/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractObject2DoubleFunction<K>
implements Object2DoubleFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractObject2DoubleFunction() {
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
    public double put(K key, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double removeDouble(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Double get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Double.valueOf(this.getDouble(k)) : null;
    }

    @Override
    @Deprecated
    public Double put(K ok, Double ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        double v = this.put(k, (double)ov);
        return containsKey ? Double.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Double remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        double v = this.removeDouble(k);
        return containsKey ? Double.valueOf(v) : null;
    }
}

