/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil;

@FunctionalInterface
public interface Function<K, V>
extends java.util.function.Function<K, V> {
    @Override
    default public V apply(K k) {
        return this.get(k);
    }

    default public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(Object var1);

    default public V getOrDefault(Object object, V v) {
        V v2 = this.get(object);
        return v2 != null || this.containsKey(object) ? v2 : v;
    }

    default public boolean containsKey(Object object) {
        return false;
    }

    default public V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    default public int size() {
        return 1;
    }

    default public void clear() {
        throw new UnsupportedOperationException();
    }
}

