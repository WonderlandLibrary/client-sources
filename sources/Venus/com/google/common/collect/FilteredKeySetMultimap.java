/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import com.google.common.collect.FilteredKeyMultimap;
import com.google.common.collect.FilteredSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredKeySetMultimap<K, V>
extends FilteredKeyMultimap<K, V>
implements FilteredSetMultimap<K, V> {
    FilteredKeySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
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
    public Set<Map.Entry<K, V>> entries() {
        return (Set)super.entries();
    }

    @Override
    Set<Map.Entry<K, V>> createEntries() {
        return new EntrySet(this);
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

    class EntrySet
    extends FilteredKeyMultimap.Entries
    implements Set<Map.Entry<K, V>> {
        final FilteredKeySetMultimap this$0;

        EntrySet(FilteredKeySetMultimap filteredKeySetMultimap) {
            this.this$0 = filteredKeySetMultimap;
            super(filteredKeySetMultimap);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }
    }
}

