/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractObject2BooleanFunction<K>
implements Object2BooleanFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractObject2BooleanFunction() {
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
    public boolean put(K key, boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeBoolean(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Boolean get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Boolean.valueOf(this.getBoolean(k)) : null;
    }

    @Override
    @Deprecated
    public Boolean put(K ok, Boolean ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        boolean v = this.put(k, (boolean)ov);
        return containsKey ? Boolean.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Boolean remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        boolean v = this.removeBoolean(k);
        return containsKey ? Boolean.valueOf(v) : null;
    }
}

