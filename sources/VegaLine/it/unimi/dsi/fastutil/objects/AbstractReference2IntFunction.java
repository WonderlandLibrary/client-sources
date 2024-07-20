/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
import java.io.Serializable;

public abstract class AbstractReference2IntFunction<K>
implements Reference2IntFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractReference2IntFunction() {
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
    public int put(K key, int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int removeInt(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Integer get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Integer.valueOf(this.getInt(k)) : null;
    }

    @Override
    @Deprecated
    public Integer put(K ok, Integer ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        int v = this.put(k, (int)ov);
        return containsKey ? Integer.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Integer remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        int v = this.removeInt(k);
        return containsKey ? Integer.valueOf(v) : null;
    }
}

