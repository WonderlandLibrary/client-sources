/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2CharFunction;
import java.io.Serializable;

public abstract class AbstractObject2CharFunction<K>
implements Object2CharFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected char defRetValue;

    protected AbstractObject2CharFunction() {
    }

    @Override
    public void defaultReturnValue(char rv) {
        this.defRetValue = rv;
    }

    @Override
    public char defaultReturnValue() {
        return this.defRetValue;
    }

    @Override
    public char put(K key, char value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char removeChar(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Character get(Object ok) {
        Object k = ok;
        return this.containsKey(k) ? Character.valueOf(this.getChar(k)) : null;
    }

    @Override
    @Deprecated
    public Character put(K ok, Character ov) {
        K k = ok;
        boolean containsKey = this.containsKey(k);
        char v = this.put(k, ov.charValue());
        return containsKey ? Character.valueOf(v) : null;
    }

    @Override
    @Deprecated
    public Character remove(Object ok) {
        Object k = ok;
        boolean containsKey = this.containsKey(k);
        char v = this.removeChar(k);
        return containsKey ? Character.valueOf(v) : null;
    }
}

