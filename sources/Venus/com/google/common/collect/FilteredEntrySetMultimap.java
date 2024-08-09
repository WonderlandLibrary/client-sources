/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import com.google.common.collect.FilteredEntryMultimap;
import com.google.common.collect.FilteredSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@GwtCompatible
final class FilteredEntrySetMultimap<K, V>
extends FilteredEntryMultimap<K, V>
implements FilteredSetMultimap<K, V> {
    FilteredEntrySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        super(setMultimap, predicate);
    }

    @Override
    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap)this.unfiltered;
    }

    @Override
    public Set<V> get(K k) {
        return (Set)super.get(k);
    }

    @Override
    public Set<V> removeAll(Object object) {
        return (Set)super.removeAll(object);
    }

    @Override
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set)super.replaceValues(k, iterable);
    }

    @Override
    Set<Map.Entry<K, V>> createEntries() {
        return Sets.filter(this.unfiltered().entries(), this.entryPredicate());
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return (Set)super.entries();
    }

    @Override
    Collection createEntries() {
        return this.createEntries();
    }

    @Override
    public Collection get(Object object) {
        return this.get(object);
    }

    @Override
    public Collection removeAll(Object object) {
        return this.removeAll(object);
    }

    @Override
    public Multimap unfiltered() {
        return this.unfiltered();
    }

    @Override
    public Collection entries() {
        return this.entries();
    }

    @Override
    public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }
}

