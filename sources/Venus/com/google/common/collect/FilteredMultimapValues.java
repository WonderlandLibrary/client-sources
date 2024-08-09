/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredMultimapValues<K, V>
extends AbstractCollection<V> {
    @Weak
    private final FilteredMultimap<K, V> multimap;

    FilteredMultimapValues(FilteredMultimap<K, V> filteredMultimap) {
        this.multimap = Preconditions.checkNotNull(filteredMultimap);
    }

    @Override
    public Iterator<V> iterator() {
        return Maps.valueIterator(this.multimap.entries().iterator());
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.multimap.containsValue(object);
    }

    @Override
    public int size() {
        return this.multimap.size();
    }

    @Override
    public boolean remove(@Nullable Object object) {
        Predicate<Map.Entry<K, V>> predicate = this.multimap.entryPredicate();
        Iterator<Map.Entry<K, V>> iterator2 = this.multimap.unfiltered().entries().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry = iterator2.next();
            if (!predicate.apply(entry) || !Objects.equal(entry.getValue(), object)) continue;
            iterator2.remove();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.in(collection))));
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection)))));
    }

    @Override
    public void clear() {
        this.multimap.clear();
    }
}

