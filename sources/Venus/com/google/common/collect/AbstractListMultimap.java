/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractListMultimap<K, V>
extends AbstractMapBasedMultimap<K, V>
implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract List<V> createCollection();

    @Override
    List<V> createUnmodifiableEmptyCollection() {
        return ImmutableList.of();
    }

    @Override
    public List<V> get(@Nullable K k) {
        return (List)super.get(k);
    }

    @Override
    @CanIgnoreReturnValue
    public List<V> removeAll(@Nullable Object object) {
        return (List)super.removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    public List<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        return (List)super.replaceValues(k, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(@Nullable K k, @Nullable V v) {
        return super.put(k, v);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public Collection get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection replaceValues(@Nullable Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    Collection createCollection() {
        return this.createCollection();
    }

    @Override
    Collection createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }
}

