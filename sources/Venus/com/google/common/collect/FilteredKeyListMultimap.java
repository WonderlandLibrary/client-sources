/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import com.google.common.collect.FilteredKeyMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredKeyListMultimap<K, V>
extends FilteredKeyMultimap<K, V>
implements ListMultimap<K, V> {
    FilteredKeyListMultimap(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        super(listMultimap, predicate);
    }

    @Override
    public ListMultimap<K, V> unfiltered() {
        return (ListMultimap)super.unfiltered();
    }

    @Override
    public List<V> get(K k) {
        return (List)super.get(k);
    }

    @Override
    public List<V> removeAll(@Nullable Object object) {
        return (List)super.removeAll(object);
    }

    @Override
    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (List)super.replaceValues(k, iterable);
    }

    @Override
    public Collection get(Object object) {
        return this.get(object);
    }

    @Override
    public Collection removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    public Multimap unfiltered() {
        return this.unfiltered();
    }

    @Override
    public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }
}

