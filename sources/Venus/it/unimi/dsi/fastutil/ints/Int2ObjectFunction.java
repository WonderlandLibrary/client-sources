/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Int2ObjectFunction<V>
extends Function<Integer, V>,
IntFunction<V> {
    @Override
    default public V apply(int n) {
        return this.get(n);
    }

    @Override
    default public V put(int n, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(int var1);

    default public V remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Integer n, V v) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        V v2 = this.put(n2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        V v = this.get(n);
        return (V)(v != this.defaultReturnValue() || this.containsKey(n) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? (V)this.remove(n) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
    }

    default public void defaultReturnValue(V v) {
        throw new UnsupportedOperationException();
    }

    default public V defaultReturnValue() {
        return null;
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Integer)object, (V)object2);
    }
}

