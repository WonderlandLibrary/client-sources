/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

abstract class ReferenceMap<K, V>
implements Map<K, V> {
    private final Map<K, Reference<V>> delegate;

    protected ReferenceMap(Map<K, Reference<V>> map) {
        this.delegate = map;
    }

    abstract Reference<V> fold(V var1);

    private V unfold(Reference<V> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.delegate.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object object) {
        return this.unfold(this.delegate.get(object));
    }

    @Override
    public V put(K k, V v) {
        return this.unfold(this.delegate.put(k, this.fold(v)));
    }

    @Override
    public V remove(Object object) {
        return this.unfold(this.delegate.remove(object));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.delegate.put(entry.getKey(), this.fold(entry.getValue()));
        }
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}

