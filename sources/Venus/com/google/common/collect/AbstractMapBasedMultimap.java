/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMapBasedMultimap<K, V>
extends AbstractMultimap<K, V>
implements Serializable {
    private transient Map<K, Collection<V>> map;
    private transient int totalSize;
    private static final long serialVersionUID = 2447537837011683357L;

    protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.map = map;
    }

    final void setMap(Map<K, Collection<V>> map) {
        this.map = map;
        this.totalSize = 0;
        for (Collection<Collection<V>> collection : map.values()) {
            Preconditions.checkArgument(!collection.isEmpty());
            this.totalSize += collection.size();
        }
    }

    Collection<V> createUnmodifiableEmptyCollection() {
        return AbstractMapBasedMultimap.unmodifiableCollectionSubclass(this.createCollection());
    }

    abstract Collection<V> createCollection();

    Collection<V> createCollection(@Nullable K k) {
        return this.createCollection();
    }

    Map<K, Collection<V>> backingMap() {
        return this.map;
    }

    @Override
    public int size() {
        return this.totalSize;
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean put(@Nullable K k, @Nullable V v) {
        Collection<V> collection = this.map.get(k);
        if (collection == null) {
            collection = this.createCollection(k);
            if (collection.add(v)) {
                ++this.totalSize;
                this.map.put(k, collection);
                return false;
            }
            throw new AssertionError((Object)"New Collection violated the Collection spec");
        }
        if (collection.add(v)) {
            ++this.totalSize;
            return false;
        }
        return true;
    }

    private Collection<V> getOrCreateCollection(@Nullable K k) {
        Collection<V> collection = this.map.get(k);
        if (collection == null) {
            collection = this.createCollection(k);
            this.map.put(k, collection);
        }
        return collection;
    }

    @Override
    public Collection<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        Iterator<V> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return this.removeAll(k);
        }
        Collection<V> collection = this.getOrCreateCollection(k);
        Collection<V> collection2 = this.createCollection();
        collection2.addAll(collection);
        this.totalSize -= collection.size();
        collection.clear();
        while (iterator2.hasNext()) {
            if (!collection.add(iterator2.next())) continue;
            ++this.totalSize;
        }
        return AbstractMapBasedMultimap.unmodifiableCollectionSubclass(collection2);
    }

    @Override
    public Collection<V> removeAll(@Nullable Object object) {
        Collection<V> collection = this.map.remove(object);
        if (collection == null) {
            return this.createUnmodifiableEmptyCollection();
        }
        Collection<V> collection2 = this.createCollection();
        collection2.addAll(collection);
        this.totalSize -= collection.size();
        collection.clear();
        return AbstractMapBasedMultimap.unmodifiableCollectionSubclass(collection2);
    }

    static <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        if (collection instanceof NavigableSet) {
            return Sets.unmodifiableNavigableSet((NavigableSet)collection);
        }
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet)collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set)collection);
        }
        if (collection instanceof List) {
            return Collections.unmodifiableList((List)collection);
        }
        return Collections.unmodifiableCollection(collection);
    }

    @Override
    public void clear() {
        for (Collection<V> collection : this.map.values()) {
            collection.clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }

    @Override
    public Collection<V> get(@Nullable K k) {
        Collection<V> collection = this.map.get(k);
        if (collection == null) {
            collection = this.createCollection(k);
        }
        return this.wrapCollection(k, collection);
    }

    Collection<V> wrapCollection(@Nullable K k, Collection<V> collection) {
        if (collection instanceof NavigableSet) {
            return new WrappedNavigableSet(this, k, (NavigableSet)collection, null);
        }
        if (collection instanceof SortedSet) {
            return new WrappedSortedSet(this, k, (SortedSet)collection, null);
        }
        if (collection instanceof Set) {
            return new WrappedSet(this, k, (Set)collection);
        }
        if (collection instanceof List) {
            return this.wrapList(k, (List)collection, null);
        }
        return new WrappedCollection(this, k, collection, null);
    }

    private List<V> wrapList(@Nullable K k, List<V> list, @Nullable WrappedCollection wrappedCollection) {
        return list instanceof RandomAccess ? new RandomAccessWrappedList(this, k, list, wrappedCollection) : new WrappedList(this, k, list, wrappedCollection);
    }

    private static <E> Iterator<E> iteratorOrListIterator(Collection<E> collection) {
        return collection instanceof List ? ((List)collection).listIterator() : collection.iterator();
    }

    @Override
    Set<K> createKeySet() {
        if (this.map instanceof NavigableMap) {
            return new NavigableKeySet(this, (NavigableMap)this.map);
        }
        if (this.map instanceof SortedMap) {
            return new SortedKeySet(this, (SortedMap)this.map);
        }
        return new KeySet(this, this.map);
    }

    private void removeValuesForKey(Object object) {
        Collection<V> collection = Maps.safeRemove(this.map, object);
        if (collection != null) {
            int n = collection.size();
            collection.clear();
            this.totalSize -= n;
        }
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    Iterator<V> valueIterator() {
        return new Itr<V>(this){
            final AbstractMapBasedMultimap this$0;
            {
                this.this$0 = abstractMapBasedMultimap;
                super(abstractMapBasedMultimap);
            }

            @Override
            V output(K k, V v) {
                return v;
            }
        };
    }

    @Override
    Spliterator<V> valueSpliterator() {
        return CollectSpliterators.flatMap(this.map.values().spliterator(), Collection::spliterator, 64, this.size());
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new Itr<Map.Entry<K, V>>(this){
            final AbstractMapBasedMultimap this$0;
            {
                this.this$0 = abstractMapBasedMultimap;
                super(abstractMapBasedMultimap);
            }

            @Override
            Map.Entry<K, V> output(K k, V v) {
                return Maps.immutableEntry(k, v);
            }

            @Override
            Object output(Object object, Object object2) {
                return this.output(object, object2);
            }
        };
    }

    @Override
    Spliterator<Map.Entry<K, V>> entrySpliterator() {
        return CollectSpliterators.flatMap(this.map.entrySet().spliterator(), AbstractMapBasedMultimap::lambda$entrySpliterator$1, 64, this.size());
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.map.forEach((arg_0, arg_1) -> AbstractMapBasedMultimap.lambda$forEach$3(biConsumer, arg_0, arg_1));
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        if (this.map instanceof NavigableMap) {
            return new NavigableAsMap(this, (NavigableMap)this.map);
        }
        if (this.map instanceof SortedMap) {
            return new SortedAsMap(this, (SortedMap)this.map);
        }
        return new AsMap(this, this.map);
    }

    private static void lambda$forEach$3(BiConsumer biConsumer, Object object, Collection collection) {
        collection.forEach(arg_0 -> AbstractMapBasedMultimap.lambda$null$2(biConsumer, object, arg_0));
    }

    private static void lambda$null$2(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
    }

    private static Spliterator lambda$entrySpliterator$1(Map.Entry entry) {
        Object k = entry.getKey();
        Collection collection = (Collection)entry.getValue();
        return CollectSpliterators.map(collection.spliterator(), arg_0 -> AbstractMapBasedMultimap.lambda$null$0(k, arg_0));
    }

    private static Map.Entry lambda$null$0(Object object, Object object2) {
        return Maps.immutableEntry(object, object2);
    }

    static Map access$000(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.map;
    }

    static Iterator access$100(Collection collection) {
        return AbstractMapBasedMultimap.iteratorOrListIterator(collection);
    }

    static int access$210(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.totalSize--;
    }

    static int access$208(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.totalSize++;
    }

    static int access$200(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.totalSize;
    }

    static int access$202(AbstractMapBasedMultimap abstractMapBasedMultimap, int n) {
        abstractMapBasedMultimap.totalSize = n;
        return abstractMapBasedMultimap.totalSize;
    }

    static List access$300(AbstractMapBasedMultimap abstractMapBasedMultimap, Object object, List list, WrappedCollection wrappedCollection) {
        return abstractMapBasedMultimap.wrapList(object, list, wrappedCollection);
    }

    static void access$400(AbstractMapBasedMultimap abstractMapBasedMultimap, Object object) {
        abstractMapBasedMultimap.removeValuesForKey(object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    class NavigableAsMap
    extends SortedAsMap
    implements NavigableMap<K, Collection<V>> {
        final AbstractMapBasedMultimap this$0;

        NavigableAsMap(AbstractMapBasedMultimap abstractMapBasedMultimap, NavigableMap<K, Collection<V>> navigableMap) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, navigableMap);
        }

        NavigableMap<K, Collection<V>> sortedMap() {
            return (NavigableMap)super.sortedMap();
        }

        @Override
        public Map.Entry<K, Collection<V>> lowerEntry(K k) {
            Map.Entry entry = this.sortedMap().lowerEntry(k);
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public K lowerKey(K k) {
            return this.sortedMap().lowerKey(k);
        }

        @Override
        public Map.Entry<K, Collection<V>> floorEntry(K k) {
            Map.Entry entry = this.sortedMap().floorEntry(k);
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public K floorKey(K k) {
            return this.sortedMap().floorKey(k);
        }

        @Override
        public Map.Entry<K, Collection<V>> ceilingEntry(K k) {
            Map.Entry entry = this.sortedMap().ceilingEntry(k);
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public K ceilingKey(K k) {
            return this.sortedMap().ceilingKey(k);
        }

        @Override
        public Map.Entry<K, Collection<V>> higherEntry(K k) {
            Map.Entry entry = this.sortedMap().higherEntry(k);
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public K higherKey(K k) {
            return this.sortedMap().higherKey(k);
        }

        @Override
        public Map.Entry<K, Collection<V>> firstEntry() {
            Map.Entry entry = this.sortedMap().firstEntry();
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public Map.Entry<K, Collection<V>> lastEntry() {
            Map.Entry entry = this.sortedMap().lastEntry();
            return entry == null ? null : this.wrapEntry(entry);
        }

        @Override
        public Map.Entry<K, Collection<V>> pollFirstEntry() {
            return this.pollAsMapEntry(this.entrySet().iterator());
        }

        @Override
        public Map.Entry<K, Collection<V>> pollLastEntry() {
            return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
        }

        Map.Entry<K, Collection<V>> pollAsMapEntry(Iterator<Map.Entry<K, Collection<V>>> iterator2) {
            if (!iterator2.hasNext()) {
                return null;
            }
            Map.Entry entry = iterator2.next();
            Collection collection = this.this$0.createCollection();
            collection.addAll(entry.getValue());
            iterator2.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.unmodifiableCollectionSubclass(collection));
        }

        @Override
        public NavigableMap<K, Collection<V>> descendingMap() {
            return new NavigableAsMap(this.this$0, this.sortedMap().descendingMap());
        }

        @Override
        public NavigableSet<K> keySet() {
            return (NavigableSet)super.keySet();
        }

        @Override
        NavigableSet<K> createKeySet() {
            return new NavigableKeySet(this.this$0, this.sortedMap());
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return this.keySet();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }

        @Override
        public NavigableMap<K, Collection<V>> subMap(K k, K k2) {
            return this.subMap(k, true, k2, true);
        }

        @Override
        public NavigableMap<K, Collection<V>> subMap(K k, boolean bl, K k2, boolean bl2) {
            return new NavigableAsMap(this.this$0, this.sortedMap().subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableMap<K, Collection<V>> headMap(K k) {
            return this.headMap(k, true);
        }

        @Override
        public NavigableMap<K, Collection<V>> headMap(K k, boolean bl) {
            return new NavigableAsMap(this.this$0, this.sortedMap().headMap(k, bl));
        }

        @Override
        public NavigableMap<K, Collection<V>> tailMap(K k) {
            return this.tailMap(k, false);
        }

        @Override
        public NavigableMap<K, Collection<V>> tailMap(K k, boolean bl) {
            return new NavigableAsMap(this.this$0, this.sortedMap().tailMap(k, bl));
        }

        @Override
        SortedSet createKeySet() {
            return this.createKeySet();
        }

        @Override
        public SortedSet keySet() {
            return this.keySet();
        }

        @Override
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        SortedMap sortedMap() {
            return this.sortedMap();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
    }

    private class SortedAsMap
    extends AsMap
    implements SortedMap<K, Collection<V>> {
        SortedSet<K> sortedKeySet;
        final AbstractMapBasedMultimap this$0;

        SortedAsMap(AbstractMapBasedMultimap abstractMapBasedMultimap, SortedMap<K, Collection<V>> sortedMap) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, sortedMap);
        }

        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap)this.submap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        public K firstKey() {
            return this.sortedMap().firstKey();
        }

        @Override
        public K lastKey() {
            return this.sortedMap().lastKey();
        }

        @Override
        public SortedMap<K, Collection<V>> headMap(K k) {
            return new SortedAsMap(this.this$0, this.sortedMap().headMap(k));
        }

        @Override
        public SortedMap<K, Collection<V>> subMap(K k, K k2) {
            return new SortedAsMap(this.this$0, this.sortedMap().subMap(k, k2));
        }

        @Override
        public SortedMap<K, Collection<V>> tailMap(K k) {
            return new SortedAsMap(this.this$0, this.sortedMap().tailMap(k));
        }

        @Override
        public SortedSet<K> keySet() {
            SortedSet sortedSet = this.sortedKeySet;
            return sortedSet == null ? (this.sortedKeySet = this.createKeySet()) : sortedSet;
        }

        @Override
        SortedSet<K> createKeySet() {
            return new SortedKeySet(this.this$0, this.sortedMap());
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
    }

    private class AsMap
    extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        final transient Map<K, Collection<V>> submap;
        final AbstractMapBasedMultimap this$0;

        AsMap(AbstractMapBasedMultimap abstractMapBasedMultimap, Map<K, Collection<V>> map) {
            this.this$0 = abstractMapBasedMultimap;
            this.submap = map;
        }

        @Override
        protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new AsMapEntries(this);
        }

        @Override
        public boolean containsKey(Object object) {
            return Maps.safeContainsKey(this.submap, object);
        }

        @Override
        public Collection<V> get(Object object) {
            Collection collection = Maps.safeGet(this.submap, object);
            if (collection == null) {
                return null;
            }
            Object object2 = object;
            return this.this$0.wrapCollection(object2, collection);
        }

        @Override
        public Set<K> keySet() {
            return this.this$0.keySet();
        }

        @Override
        public int size() {
            return this.submap.size();
        }

        @Override
        public Collection<V> remove(Object object) {
            Collection collection = this.submap.remove(object);
            if (collection == null) {
                return null;
            }
            Collection collection2 = this.this$0.createCollection();
            collection2.addAll(collection);
            AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) - collection.size());
            collection.clear();
            return collection2;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return this == object || this.submap.equals(object);
        }

        @Override
        public int hashCode() {
            return this.submap.hashCode();
        }

        @Override
        public String toString() {
            return this.submap.toString();
        }

        @Override
        public void clear() {
            if (this.submap == AbstractMapBasedMultimap.access$000(this.this$0)) {
                this.this$0.clear();
            } else {
                Iterators.clear(new AsMapIterator(this));
            }
        }

        Map.Entry<K, Collection<V>> wrapEntry(Map.Entry<K, Collection<V>> entry) {
            Object k = entry.getKey();
            return Maps.immutableEntry(k, this.this$0.wrapCollection(k, entry.getValue()));
        }

        @Override
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        public Object get(Object object) {
            return this.get(object);
        }

        class AsMapIterator
        implements Iterator<Map.Entry<K, Collection<V>>> {
            final Iterator<Map.Entry<K, Collection<V>>> delegateIterator;
            Collection<V> collection;
            final AsMap this$1;

            AsMapIterator(AsMap asMap) {
                this.this$1 = asMap;
                this.delegateIterator = this.this$1.submap.entrySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return this.delegateIterator.hasNext();
            }

            @Override
            public Map.Entry<K, Collection<V>> next() {
                Map.Entry entry = this.delegateIterator.next();
                this.collection = entry.getValue();
                return this.this$1.wrapEntry(entry);
            }

            @Override
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$202(this.this$1.this$0, AbstractMapBasedMultimap.access$200(this.this$1.this$0) - this.collection.size());
                this.collection.clear();
            }

            @Override
            public Object next() {
                return this.next();
            }
        }

        class AsMapEntries
        extends Maps.EntrySet<K, Collection<V>> {
            final AsMap this$1;

            AsMapEntries(AsMap asMap) {
                this.this$1 = asMap;
            }

            @Override
            Map<K, Collection<V>> map() {
                return this.this$1;
            }

            @Override
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator(this.this$1);
            }

            @Override
            public Spliterator<Map.Entry<K, Collection<V>>> spliterator() {
                return CollectSpliterators.map(this.this$1.submap.entrySet().spliterator(), this.this$1::wrapEntry);
            }

            @Override
            public boolean contains(Object object) {
                return Collections2.safeContains(this.this$1.submap.entrySet(), object);
            }

            @Override
            public boolean remove(Object object) {
                if (!this.contains(object)) {
                    return true;
                }
                Map.Entry entry = (Map.Entry)object;
                AbstractMapBasedMultimap.access$400(this.this$1.this$0, entry.getKey());
                return false;
            }
        }
    }

    private abstract class Itr<T>
    implements Iterator<T> {
        final Iterator<Map.Entry<K, Collection<V>>> keyIterator;
        K key;
        Collection<V> collection;
        Iterator<V> valueIterator;
        final AbstractMapBasedMultimap this$0;

        Itr(AbstractMapBasedMultimap abstractMapBasedMultimap) {
            this.this$0 = abstractMapBasedMultimap;
            this.keyIterator = AbstractMapBasedMultimap.access$000(abstractMapBasedMultimap).entrySet().iterator();
            this.key = null;
            this.collection = null;
            this.valueIterator = Iterators.emptyModifiableIterator();
        }

        abstract T output(K var1, V var2);

        @Override
        public boolean hasNext() {
            return this.keyIterator.hasNext() || this.valueIterator.hasNext();
        }

        @Override
        public T next() {
            if (!this.valueIterator.hasNext()) {
                Map.Entry entry = this.keyIterator.next();
                this.key = entry.getKey();
                this.collection = entry.getValue();
                this.valueIterator = this.collection.iterator();
            }
            return this.output(this.key, this.valueIterator.next());
        }

        @Override
        public void remove() {
            this.valueIterator.remove();
            if (this.collection.isEmpty()) {
                this.keyIterator.remove();
            }
            AbstractMapBasedMultimap.access$210(this.this$0);
        }
    }

    class NavigableKeySet
    extends SortedKeySet
    implements NavigableSet<K> {
        final AbstractMapBasedMultimap this$0;

        NavigableKeySet(AbstractMapBasedMultimap abstractMapBasedMultimap, NavigableMap<K, Collection<V>> navigableMap) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, navigableMap);
        }

        NavigableMap<K, Collection<V>> sortedMap() {
            return (NavigableMap)super.sortedMap();
        }

        @Override
        public K lower(K k) {
            return this.sortedMap().lowerKey(k);
        }

        @Override
        public K floor(K k) {
            return this.sortedMap().floorKey(k);
        }

        @Override
        public K ceiling(K k) {
            return this.sortedMap().ceilingKey(k);
        }

        @Override
        public K higher(K k) {
            return this.sortedMap().higherKey(k);
        }

        @Override
        public K pollFirst() {
            return Iterators.pollNext(this.iterator());
        }

        @Override
        public K pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }

        @Override
        public NavigableSet<K> descendingSet() {
            return new NavigableKeySet(this.this$0, this.sortedMap().descendingMap());
        }

        @Override
        public Iterator<K> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<K> headSet(K k) {
            return this.headSet((K)k, true);
        }

        @Override
        public NavigableSet<K> headSet(K k, boolean bl) {
            return new NavigableKeySet(this.this$0, this.sortedMap().headMap(k, bl));
        }

        @Override
        public NavigableSet<K> subSet(K k, K k2) {
            return this.subSet((K)k, true, (K)k2, true);
        }

        @Override
        public NavigableSet<K> subSet(K k, boolean bl, K k2, boolean bl2) {
            return new NavigableKeySet(this.this$0, this.sortedMap().subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableSet<K> tailSet(K k) {
            return this.tailSet((K)k, false);
        }

        @Override
        public NavigableSet<K> tailSet(K k, boolean bl) {
            return new NavigableKeySet(this.this$0, this.sortedMap().tailMap(k, bl));
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        SortedMap sortedMap() {
            return this.sortedMap();
        }
    }

    private class SortedKeySet
    extends KeySet
    implements SortedSet<K> {
        final AbstractMapBasedMultimap this$0;

        SortedKeySet(AbstractMapBasedMultimap abstractMapBasedMultimap, SortedMap<K, Collection<V>> sortedMap) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, sortedMap);
        }

        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap)super.map();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        public K first() {
            return this.sortedMap().firstKey();
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(this.this$0, this.sortedMap().headMap(k));
        }

        @Override
        public K last() {
            return this.sortedMap().lastKey();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(this.this$0, this.sortedMap().subMap(k, k2));
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(this.this$0, this.sortedMap().tailMap(k));
        }
    }

    private class KeySet
    extends Maps.KeySet<K, Collection<V>> {
        final AbstractMapBasedMultimap this$0;

        KeySet(AbstractMapBasedMultimap abstractMapBasedMultimap, Map<K, Collection<V>> map) {
            this.this$0 = abstractMapBasedMultimap;
            super(map);
        }

        @Override
        public Iterator<K> iterator() {
            Iterator iterator2 = this.map().entrySet().iterator();
            return new Iterator<K>(this, iterator2){
                Map.Entry<K, Collection<V>> entry;
                final Iterator val$entryIterator;
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    this.val$entryIterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$entryIterator.hasNext();
                }

                @Override
                public K next() {
                    this.entry = (Map.Entry)this.val$entryIterator.next();
                    return this.entry.getKey();
                }

                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.entry != null);
                    Collection collection = this.entry.getValue();
                    this.val$entryIterator.remove();
                    AbstractMapBasedMultimap.access$202(this.this$1.this$0, AbstractMapBasedMultimap.access$200(this.this$1.this$0) - collection.size());
                    collection.clear();
                }
            };
        }

        @Override
        public Spliterator<K> spliterator() {
            return this.map().keySet().spliterator();
        }

        @Override
        public boolean remove(Object object) {
            int n = 0;
            Collection collection = (Collection)this.map().remove(object);
            if (collection != null) {
                n = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) - n);
            }
            return n > 0;
        }

        @Override
        public void clear() {
            Iterators.clear(this.iterator());
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.map().keySet().containsAll(collection);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return this == object || this.map().keySet().equals(object);
        }

        @Override
        public int hashCode() {
            return this.map().keySet().hashCode();
        }
    }

    private class RandomAccessWrappedList
    extends WrappedList
    implements RandomAccess {
        final AbstractMapBasedMultimap this$0;

        RandomAccessWrappedList(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, @Nullable List<V> list, WrappedCollection wrappedCollection) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, k, list, wrappedCollection);
        }
    }

    private class WrappedList
    extends WrappedCollection
    implements List<V> {
        final AbstractMapBasedMultimap this$0;

        WrappedList(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, @Nullable List<V> list, WrappedCollection wrappedCollection) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, k, list, wrappedCollection);
        }

        List<V> getListDelegate() {
            return (List)this.getDelegate();
        }

        @Override
        public boolean addAll(int n, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return true;
            }
            int n2 = this.size();
            boolean bl = this.getListDelegate().addAll(n, collection);
            if (bl) {
                int n3 = this.getDelegate().size();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) + (n3 - n2));
                if (n2 == 0) {
                    this.addToMap();
                }
            }
            return bl;
        }

        @Override
        public V get(int n) {
            this.refreshIfEmpty();
            return this.getListDelegate().get(n);
        }

        @Override
        public V set(int n, V v) {
            this.refreshIfEmpty();
            return this.getListDelegate().set(n, v);
        }

        @Override
        public void add(int n, V v) {
            this.refreshIfEmpty();
            boolean bl = this.getDelegate().isEmpty();
            this.getListDelegate().add(n, v);
            AbstractMapBasedMultimap.access$208(this.this$0);
            if (bl) {
                this.addToMap();
            }
        }

        @Override
        public V remove(int n) {
            this.refreshIfEmpty();
            Object v = this.getListDelegate().remove(n);
            AbstractMapBasedMultimap.access$210(this.this$0);
            this.removeIfEmpty();
            return v;
        }

        @Override
        public int indexOf(Object object) {
            this.refreshIfEmpty();
            return this.getListDelegate().indexOf(object);
        }

        @Override
        public int lastIndexOf(Object object) {
            this.refreshIfEmpty();
            return this.getListDelegate().lastIndexOf(object);
        }

        @Override
        public ListIterator<V> listIterator() {
            this.refreshIfEmpty();
            return new WrappedListIterator(this);
        }

        @Override
        public ListIterator<V> listIterator(int n) {
            this.refreshIfEmpty();
            return new WrappedListIterator(this, n);
        }

        @Override
        public List<V> subList(int n, int n2) {
            this.refreshIfEmpty();
            return AbstractMapBasedMultimap.access$300(this.this$0, this.getKey(), this.getListDelegate().subList(n, n2), this.getAncestor() == null ? this : this.getAncestor());
        }

        /*
         * Signature claims super is com.google.common.collect.AbstractMapBasedMultimap$WrappedCollection.WrappedIterator, not com.google.common.collect.AbstractMapBasedMultimap$WrappedCollection$WrappedIterator - discarding signature.
         */
        private class WrappedListIterator
        extends WrappedCollection.WrappedIterator
        implements ListIterator {
            final WrappedList this$1;

            WrappedListIterator(WrappedList wrappedList) {
                this.this$1 = wrappedList;
                super(wrappedList);
            }

            public WrappedListIterator(WrappedList wrappedList, int n) {
                this.this$1 = wrappedList;
                super(wrappedList, wrappedList.getListDelegate().listIterator(n));
            }

            private ListIterator<V> getDelegateListIterator() {
                return (ListIterator)this.getDelegateIterator();
            }

            @Override
            public boolean hasPrevious() {
                return this.getDelegateListIterator().hasPrevious();
            }

            public V previous() {
                return this.getDelegateListIterator().previous();
            }

            @Override
            public int nextIndex() {
                return this.getDelegateListIterator().nextIndex();
            }

            @Override
            public int previousIndex() {
                return this.getDelegateListIterator().previousIndex();
            }

            public void set(V v) {
                this.getDelegateListIterator().set(v);
            }

            public void add(V v) {
                boolean bl = this.this$1.isEmpty();
                this.getDelegateListIterator().add(v);
                AbstractMapBasedMultimap.access$208(this.this$1.this$0);
                if (bl) {
                    this.this$1.addToMap();
                }
            }
        }
    }

    class WrappedNavigableSet
    extends WrappedSortedSet
    implements NavigableSet<V> {
        final AbstractMapBasedMultimap this$0;

        WrappedNavigableSet(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, @Nullable NavigableSet<V> navigableSet, WrappedCollection wrappedCollection) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, k, navigableSet, wrappedCollection);
        }

        NavigableSet<V> getSortedSetDelegate() {
            return (NavigableSet)super.getSortedSetDelegate();
        }

        @Override
        public V lower(V v) {
            return this.getSortedSetDelegate().lower(v);
        }

        @Override
        public V floor(V v) {
            return this.getSortedSetDelegate().floor(v);
        }

        @Override
        public V ceiling(V v) {
            return this.getSortedSetDelegate().ceiling(v);
        }

        @Override
        public V higher(V v) {
            return this.getSortedSetDelegate().higher(v);
        }

        @Override
        public V pollFirst() {
            return Iterators.pollNext(this.iterator());
        }

        @Override
        public V pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }

        private NavigableSet<V> wrap(NavigableSet<V> navigableSet) {
            return new WrappedNavigableSet(this.this$0, this.key, navigableSet, this.getAncestor() == null ? this : this.getAncestor());
        }

        @Override
        public NavigableSet<V> descendingSet() {
            return this.wrap(this.getSortedSetDelegate().descendingSet());
        }

        @Override
        public Iterator<V> descendingIterator() {
            return new WrappedCollection.WrappedIterator(this, this.getSortedSetDelegate().descendingIterator());
        }

        @Override
        public NavigableSet<V> subSet(V v, boolean bl, V v2, boolean bl2) {
            return this.wrap(this.getSortedSetDelegate().subSet(v, bl, v2, bl2));
        }

        @Override
        public NavigableSet<V> headSet(V v, boolean bl) {
            return this.wrap(this.getSortedSetDelegate().headSet(v, bl));
        }

        @Override
        public NavigableSet<V> tailSet(V v, boolean bl) {
            return this.wrap(this.getSortedSetDelegate().tailSet(v, bl));
        }

        SortedSet getSortedSetDelegate() {
            return this.getSortedSetDelegate();
        }
    }

    private class WrappedSortedSet
    extends WrappedCollection
    implements SortedSet<V> {
        final AbstractMapBasedMultimap this$0;

        WrappedSortedSet(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, @Nullable SortedSet<V> sortedSet, WrappedCollection wrappedCollection) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, k, sortedSet, wrappedCollection);
        }

        SortedSet<V> getSortedSetDelegate() {
            return (SortedSet)this.getDelegate();
        }

        @Override
        public Comparator<? super V> comparator() {
            return this.getSortedSetDelegate().comparator();
        }

        @Override
        public V first() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().first();
        }

        @Override
        public V last() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().last();
        }

        @Override
        public SortedSet<V> headSet(V v) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.this$0, this.getKey(), this.getSortedSetDelegate().headSet(v), this.getAncestor() == null ? this : this.getAncestor());
        }

        @Override
        public SortedSet<V> subSet(V v, V v2) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.this$0, this.getKey(), this.getSortedSetDelegate().subSet(v, v2), this.getAncestor() == null ? this : this.getAncestor());
        }

        @Override
        public SortedSet<V> tailSet(V v) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.this$0, this.getKey(), this.getSortedSetDelegate().tailSet(v), this.getAncestor() == null ? this : this.getAncestor());
        }
    }

    private class WrappedSet
    extends WrappedCollection
    implements Set<V> {
        final AbstractMapBasedMultimap this$0;

        WrappedSet(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, Set<V> set) {
            this.this$0 = abstractMapBasedMultimap;
            super(abstractMapBasedMultimap, k, set, null);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return true;
            }
            int n = this.size();
            boolean bl = Sets.removeAllImpl((Set)this.delegate, collection);
            if (bl) {
                int n2 = this.delegate.size();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) + (n2 - n));
                this.removeIfEmpty();
            }
            return bl;
        }
    }

    private class WrappedCollection
    extends AbstractCollection<V> {
        final K key;
        Collection<V> delegate;
        final WrappedCollection ancestor;
        final Collection<V> ancestorDelegate;
        final AbstractMapBasedMultimap this$0;

        WrappedCollection(@Nullable AbstractMapBasedMultimap abstractMapBasedMultimap, K k, @Nullable Collection<V> collection, WrappedCollection wrappedCollection) {
            this.this$0 = abstractMapBasedMultimap;
            this.key = k;
            this.delegate = collection;
            this.ancestor = wrappedCollection;
            this.ancestorDelegate = wrappedCollection == null ? null : wrappedCollection.getDelegate();
        }

        void refreshIfEmpty() {
            Collection collection;
            if (this.ancestor != null) {
                this.ancestor.refreshIfEmpty();
                if (this.ancestor.getDelegate() != this.ancestorDelegate) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.delegate.isEmpty() && (collection = (Collection)AbstractMapBasedMultimap.access$000(this.this$0).get(this.key)) != null) {
                this.delegate = collection;
            }
        }

        void removeIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.removeIfEmpty();
            } else if (this.delegate.isEmpty()) {
                AbstractMapBasedMultimap.access$000(this.this$0).remove(this.key);
            }
        }

        K getKey() {
            return this.key;
        }

        void addToMap() {
            if (this.ancestor != null) {
                this.ancestor.addToMap();
            } else {
                AbstractMapBasedMultimap.access$000(this.this$0).put(this.key, this.delegate);
            }
        }

        @Override
        public int size() {
            this.refreshIfEmpty();
            return this.delegate.size();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            this.refreshIfEmpty();
            return this.delegate.equals(object);
        }

        @Override
        public int hashCode() {
            this.refreshIfEmpty();
            return this.delegate.hashCode();
        }

        @Override
        public String toString() {
            this.refreshIfEmpty();
            return this.delegate.toString();
        }

        Collection<V> getDelegate() {
            return this.delegate;
        }

        @Override
        public Iterator<V> iterator() {
            this.refreshIfEmpty();
            return new WrappedIterator(this);
        }

        @Override
        public Spliterator<V> spliterator() {
            this.refreshIfEmpty();
            return this.delegate.spliterator();
        }

        @Override
        public boolean add(V v) {
            this.refreshIfEmpty();
            boolean bl = this.delegate.isEmpty();
            boolean bl2 = this.delegate.add(v);
            if (bl2) {
                AbstractMapBasedMultimap.access$208(this.this$0);
                if (bl) {
                    this.addToMap();
                }
            }
            return bl2;
        }

        WrappedCollection getAncestor() {
            return this.ancestor;
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return true;
            }
            int n = this.size();
            boolean bl = this.delegate.addAll(collection);
            if (bl) {
                int n2 = this.delegate.size();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) + (n2 - n));
                if (n == 0) {
                    this.addToMap();
                }
            }
            return bl;
        }

        @Override
        public boolean contains(Object object) {
            this.refreshIfEmpty();
            return this.delegate.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            this.refreshIfEmpty();
            return this.delegate.containsAll(collection);
        }

        @Override
        public void clear() {
            int n = this.size();
            if (n == 0) {
                return;
            }
            this.delegate.clear();
            AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) - n);
            this.removeIfEmpty();
        }

        @Override
        public boolean remove(Object object) {
            this.refreshIfEmpty();
            boolean bl = this.delegate.remove(object);
            if (bl) {
                AbstractMapBasedMultimap.access$210(this.this$0);
                this.removeIfEmpty();
            }
            return bl;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return true;
            }
            int n = this.size();
            boolean bl = this.delegate.removeAll(collection);
            if (bl) {
                int n2 = this.delegate.size();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) + (n2 - n));
                this.removeIfEmpty();
            }
            return bl;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            int n = this.size();
            boolean bl = this.delegate.retainAll(collection);
            if (bl) {
                int n2 = this.delegate.size();
                AbstractMapBasedMultimap.access$202(this.this$0, AbstractMapBasedMultimap.access$200(this.this$0) + (n2 - n));
                this.removeIfEmpty();
            }
            return bl;
        }

        class WrappedIterator
        implements Iterator<V> {
            final Iterator<V> delegateIterator;
            final Collection<V> originalDelegate;
            final WrappedCollection this$1;

            WrappedIterator(WrappedCollection wrappedCollection) {
                this.this$1 = wrappedCollection;
                this.originalDelegate = this.this$1.delegate;
                this.delegateIterator = AbstractMapBasedMultimap.access$100(wrappedCollection.delegate);
            }

            WrappedIterator(WrappedCollection wrappedCollection, Iterator<V> iterator2) {
                this.this$1 = wrappedCollection;
                this.originalDelegate = this.this$1.delegate;
                this.delegateIterator = iterator2;
            }

            void validateIterator() {
                this.this$1.refreshIfEmpty();
                if (this.this$1.delegate != this.originalDelegate) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public boolean hasNext() {
                this.validateIterator();
                return this.delegateIterator.hasNext();
            }

            @Override
            public V next() {
                this.validateIterator();
                return this.delegateIterator.next();
            }

            @Override
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$210(this.this$1.this$0);
                this.this$1.removeIfEmpty();
            }

            Iterator<V> getDelegateIterator() {
                this.validateIterator();
                return this.delegateIterator;
            }
        }
    }
}

