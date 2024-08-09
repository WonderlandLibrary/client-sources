/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.FilteredMultimapValues;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
class FilteredKeyMultimap<K, V>
extends AbstractMultimap<K, V>
implements FilteredMultimap<K, V> {
    final Multimap<K, V> unfiltered;
    final Predicate<? super K> keyPredicate;

    FilteredKeyMultimap(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.unfiltered = Preconditions.checkNotNull(multimap);
        this.keyPredicate = Preconditions.checkNotNull(predicate);
    }

    @Override
    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    @Override
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.keyPredicateOnEntries(this.keyPredicate);
    }

    @Override
    public int size() {
        int n = 0;
        for (Collection collection : this.asMap().values()) {
            n += collection.size();
        }
        return n;
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        if (this.unfiltered.containsKey(object)) {
            Object object2 = object;
            return this.keyPredicate.apply(object2);
        }
        return true;
    }

    @Override
    public Collection<V> removeAll(Object object) {
        return this.containsKey(object) ? this.unfiltered.removeAll(object) : this.unmodifiableEmptyCollection();
    }

    Collection<V> unmodifiableEmptyCollection() {
        if (this.unfiltered instanceof SetMultimap) {
            return ImmutableSet.of();
        }
        return ImmutableList.of();
    }

    @Override
    public void clear() {
        this.keySet().clear();
    }

    @Override
    Set<K> createKeySet() {
        return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
    }

    @Override
    public Collection<V> get(K k) {
        if (this.keyPredicate.apply(k)) {
            return this.unfiltered.get(k);
        }
        if (this.unfiltered instanceof SetMultimap) {
            return new AddRejectingSet(k);
        }
        return new AddRejectingList(k);
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    Collection<Map.Entry<K, V>> createEntries() {
        return new Entries(this);
    }

    @Override
    Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
    }

    @Override
    Multiset<K> createKeys() {
        return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
    }

    class Entries
    extends ForwardingCollection<Map.Entry<K, V>> {
        final FilteredKeyMultimap this$0;

        Entries(FilteredKeyMultimap filteredKeyMultimap) {
            this.this$0 = filteredKeyMultimap;
        }

        @Override
        protected Collection<Map.Entry<K, V>> delegate() {
            return Collections2.filter(this.this$0.unfiltered.entries(), this.this$0.entryPredicate());
        }

        @Override
        public boolean remove(@Nullable Object object) {
            Map.Entry entry;
            if (object instanceof Map.Entry && this.this$0.unfiltered.containsKey((entry = (Map.Entry)object).getKey()) && this.this$0.keyPredicate.apply(entry.getKey())) {
                return this.this$0.unfiltered.remove(entry.getKey(), entry.getValue());
            }
            return true;
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class AddRejectingList<K, V>
    extends ForwardingList<V> {
        final K key;

        AddRejectingList(K k) {
            this.key = k;
        }

        @Override
        public boolean add(V v) {
            this.add(0, v);
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            this.addAll(0, collection);
            return false;
        }

        @Override
        public void add(int n, V v) {
            Preconditions.checkPositionIndex(n, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }

        @Override
        @CanIgnoreReturnValue
        public boolean addAll(int n, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(n, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }

        @Override
        protected List<V> delegate() {
            return Collections.emptyList();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class AddRejectingSet<K, V>
    extends ForwardingSet<V> {
        final K key;

        AddRejectingSet(K k) {
            this.key = k;
        }

        @Override
        public boolean add(V v) {
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }

        @Override
        protected Set<V> delegate() {
            return Collections.emptySet();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

