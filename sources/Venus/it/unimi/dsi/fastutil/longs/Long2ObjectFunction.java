/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongFunction;

@FunctionalInterface
public interface Long2ObjectFunction<V>
extends Function<Long, V>,
LongFunction<V> {
    @Override
    default public V apply(long l) {
        return this.get(l);
    }

    @Override
    default public V put(long l, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(long var1);

    default public V remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Long l, V v) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        V v2 = this.put(l2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        V v = this.get(l);
        return (V)(v != this.defaultReturnValue() || this.containsKey(l) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? (V)this.remove(l) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
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
        return this.put((Long)object, (V)object2);
    }
}

