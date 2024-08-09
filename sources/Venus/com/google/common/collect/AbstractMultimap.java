/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultimap<K, V>
implements Multimap<K, V> {
    private transient Collection<Map.Entry<K, V>> entries;
    private transient Set<K> keySet;
    private transient Multiset<K> keys;
    private transient Collection<V> values;
    private transient Map<K, Collection<V>> asMap;

    AbstractMultimap() {
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        for (Collection<V> collection : this.asMap().values()) {
            if (!collection.contains(object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean containsEntry(@Nullable Object object, @Nullable Object object2) {
        Collection<V> collection = this.asMap().get(object);
        return collection != null && collection.contains(object2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        Collection<V> collection = this.asMap().get(object);
        return collection != null && collection.remove(object2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(@Nullable K k, @Nullable V v) {
        return this.get(k).add(v);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(@Nullable K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            Collection collection = (Collection)iterable;
            return !collection.isEmpty() && this.get(k).addAll(collection);
        }
        Iterator<V> iterator2 = iterable.iterator();
        return iterator2.hasNext() && Iterators.addAll(this.get(k), iterator2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean bl = false;
        for (Map.Entry<K, V> entry : multimap.entries()) {
            bl |= this.put(entry.getKey(), entry.getValue());
        }
        return bl;
    }

    @Override
    @CanIgnoreReturnValue
    public Collection<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        Collection collection = this.removeAll(k);
        this.putAll(k, iterable);
        return collection;
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> collection = this.entries;
        return collection == null ? (this.entries = this.createEntries()) : collection;
    }

    Collection<Map.Entry<K, V>> createEntries() {
        if (this instanceof SetMultimap) {
            return new EntrySet(this, null);
        }
        return new Entries(this, null);
    }

    abstract Iterator<Map.Entry<K, V>> entryIterator();

    Spliterator<Map.Entry<K, V>> entrySpliterator() {
        return Spliterators.spliterator(this.entryIterator(), (long)this.size(), this instanceof SetMultimap ? 1 : 0);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        return set == null ? (this.keySet = this.createKeySet()) : set;
    }

    Set<K> createKeySet() {
        return new Maps.KeySet<K, Collection<V>>(this.asMap());
    }

    @Override
    public Multiset<K> keys() {
        Multiset<K> multiset = this.keys;
        return multiset == null ? (this.keys = this.createKeys()) : multiset;
    }

    Multiset<K> createKeys() {
        return new Multimaps.Keys(this);
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = this.values;
        return collection == null ? (this.values = this.createValues()) : collection;
    }

    Collection<V> createValues() {
        return new Values(this);
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entries().iterator());
    }

    Spliterator<V> valueSpliterator() {
        return Spliterators.spliterator(this.valueIterator(), (long)this.size(), 0);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<Collection<V>>> map = this.asMap;
        return map == null ? (this.asMap = this.createAsMap()) : map;
    }

    abstract Map<K, Collection<V>> createAsMap();

    @Override
    public boolean equals(@Nullable Object object) {
        return Multimaps.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return this.asMap().hashCode();
    }

    public String toString() {
        return this.asMap().toString();
    }

    class Values
    extends AbstractCollection<V> {
        final AbstractMultimap this$0;

        Values(AbstractMultimap abstractMultimap) {
            this.this$0 = abstractMultimap;
        }

        @Override
        public Iterator<V> iterator() {
            return this.this$0.valueIterator();
        }

        @Override
        public Spliterator<V> spliterator() {
            return this.this$0.valueSpliterator();
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.this$0.containsValue(object);
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }
    }

    private class EntrySet
    extends Entries
    implements Set<Map.Entry<K, V>> {
        final AbstractMultimap this$0;

        private EntrySet(AbstractMultimap abstractMultimap) {
            this.this$0 = abstractMultimap;
            super(abstractMultimap, null);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        EntrySet(AbstractMultimap abstractMultimap, 1 var2_2) {
            this(abstractMultimap);
        }
    }

    private class Entries
    extends Multimaps.Entries<K, V> {
        final AbstractMultimap this$0;

        private Entries(AbstractMultimap abstractMultimap) {
            this.this$0 = abstractMultimap;
        }

        @Override
        Multimap<K, V> multimap() {
            return this.this$0;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return this.this$0.entryIterator();
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return this.this$0.entrySpliterator();
        }

        Entries(AbstractMultimap abstractMultimap, 1 var2_2) {
            this(abstractMultimap);
        }
    }
}

