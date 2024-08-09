/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMultimap<K, V>
extends ForwardingObject
implements Multimap<K, V> {
    protected ForwardingMultimap() {
    }

    @Override
    protected abstract Multimap<K, V> delegate();

    @Override
    public Map<K, Collection<V>> asMap() {
        return this.delegate().asMap();
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public boolean containsEntry(@Nullable Object object, @Nullable Object object2) {
        return this.delegate().containsEntry(object, object2);
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.delegate().containsKey(object);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return this.delegate().containsValue(object);
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        return this.delegate().entries();
    }

    @Override
    public Collection<V> get(@Nullable K k) {
        return this.delegate().get(k);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public Multiset<K> keys() {
        return this.delegate().keys();
    }

    @Override
    public Set<K> keySet() {
        return this.delegate().keySet();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(K k, V v) {
        return this.delegate().put(k, v);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        return this.delegate().putAll(k, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return this.delegate().putAll(multimap);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        return this.delegate().remove(object, object2);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection<V> removeAll(@Nullable Object object) {
        return this.delegate().removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return this.delegate().replaceValues(k, iterable);
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

