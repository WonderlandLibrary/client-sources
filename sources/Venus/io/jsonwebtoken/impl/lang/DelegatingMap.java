/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.lang.Assert;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DelegatingMap<K, V, T extends Map<K, V>>
implements Map<K, V> {
    protected T DELEGATE;

    protected DelegatingMap(T t) {
        this.setDelegate(t);
    }

    protected void setDelegate(T t) {
        this.DELEGATE = (Map)Assert.notNull(t, "Delegate cannot be null.");
    }

    @Override
    public int size() {
        return this.DELEGATE.size();
    }

    @Override
    public Collection<V> values() {
        return this.DELEGATE.values();
    }

    @Override
    public V get(Object object) {
        return this.DELEGATE.get(object);
    }

    @Override
    public boolean isEmpty() {
        return this.DELEGATE.isEmpty();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.DELEGATE.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.DELEGATE.containsValue(object);
    }

    @Override
    public V put(K k, V v) {
        return this.DELEGATE.put(k, v);
    }

    @Override
    public V remove(Object object) {
        return this.DELEGATE.remove(object);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.DELEGATE.putAll(map);
    }

    @Override
    public void clear() {
        this.DELEGATE.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.DELEGATE.keySet();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.DELEGATE.entrySet();
    }
}

