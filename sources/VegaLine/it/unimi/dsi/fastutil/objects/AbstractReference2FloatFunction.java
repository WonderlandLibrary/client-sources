/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
import java.io.Serializable;

public abstract class AbstractReference2FloatFunction<K>
implements Reference2FloatFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractReference2FloatFunction() {
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
    public float put(K key, float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float removeFloat(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Float get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Float.valueOf(this.getFloat(k)) : null;
    }

    @Override
    @Deprecated
    public Float put(K ok, Float ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        float v = this.put(k, ov.floatValue());
        return containsKey ? Float.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Float remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        float v = this.removeFloat(k);
        return containsKey ? Float.valueOf(v) : null;
    }
}

