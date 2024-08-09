/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.SortedSetMultimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
abstract class AbstractSortedSetMultimap<K, V>
extends AbstractSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract SortedSet<V> createCollection();

    @Override
    SortedSet<V> createUnmodifiableEmptyCollection() {
        Comparator comparator = this.valueComparator();
        if (comparator == null) {
            return Collections.unmodifiableSortedSet(this.createCollection());
        }
        return ImmutableSortedSet.emptySet(this.valueComparator());
    }

    @Override
    public SortedSet<V> get(@Nullable K k) {
        return (SortedSet)super.get((Object)k);
    }

    @Override
    @CanIgnoreReturnValue
    public SortedSet<V> removeAll(@Nullable Object object) {
        return (SortedSet)super.removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    public SortedSet<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        return (SortedSet)super.replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    @CanIgnoreReturnValue
    public Set replaceValues(@Nullable Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public Set removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    public Set get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    Set createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }

    @Override
    Set createCollection() {
        return this.createCollection();
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

