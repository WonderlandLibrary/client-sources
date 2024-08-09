/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

@FunctionalInterface
public interface Object2ReferenceFunction<K, V>
extends Function<K, V> {
    @Override
    default public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object var1);

    @Override
    default public V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    default public void defaultReturnValue(V v) {
        throw new UnsupportedOperationException();
    }

    default public V defaultReturnValue() {
        return null;
    }
}

