/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.FilteredMultimapValues;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
class FilteredEntryMultimap<K, V>
extends AbstractMultimap<K, V>
implements FilteredMultimap<K, V> {
    final Multimap<K, V> unfiltered;
    final Predicate<? super Map.Entry<K, V>> predicate;

    FilteredEntryMultimap(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        this.unfiltered = Preconditions.checkNotNull(multimap);
        this.predicate = Preconditions.checkNotNull(predicate);
    }

    @Override
    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    @Override
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.predicate;
    }

    @Override
    public int size() {
        return this.entries().size();
    }

    private boolean satisfies(K k, V v) {
        return this.predicate.apply(Maps.immutableEntry(k, v));
    }

    static <E> Collection<E> filterCollection(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set)collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.asMap().get(object) != null;
    }

    @Override
    public Collection<V> removeAll(@Nullable Object object) {
        return MoreObjects.firstNonNull(this.asMap().remove(object), this.unmodifiableEmptyCollection());
    }

    Collection<V> unmodifiableEmptyCollection() {
        return this.unfiltered instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList();
    }

    @Override
    public void clear() {
        this.entries().clear();
    }

    @Override
    public Collection<V> get(K k) {
        return FilteredEntryMultimap.filterCollection(this.unfiltered.get(k), new ValuePredicate(this, k));
    }

    @Override
    Collection<Map.Entry<K, V>> createEntries() {
        return FilteredEntryMultimap.filterCollection(this.unfiltered.entries(), this.predicate);
    }

    @Override
    Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return new AsMap(this);
    }

    @Override
    public Set<K> keySet() {
        return this.asMap().keySet();
    }

    boolean removeEntriesIf(Predicate<? super Map.Entry<K, Collection<V>>> predicate) {
        Iterator<Map.Entry<K, Collection<V>>> iterator2 = this.unfiltered.asMap().entrySet().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Map.Entry<K, Collection<V>> entry = iterator2.next();
            K k = entry.getKey();
            Collection<V> collection = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(this, k));
            if (collection.isEmpty() || !predicate.apply(Maps.immutableEntry(k, collection))) continue;
            if (collection.size() == entry.getValue().size()) {
                iterator2.remove();
            } else {
                collection.clear();
            }
            bl = true;
        }
        return bl;
    }

    @Override
    Multiset<K> createKeys() {
        return new Keys(this);
    }

    static boolean access$000(FilteredEntryMultimap filteredEntryMultimap, Object object, Object object2) {
        return filteredEntryMultimap.satisfies(object, object2);
    }

    class Keys
    extends Multimaps.Keys<K, V> {
        final FilteredEntryMultimap this$0;

        Keys(FilteredEntryMultimap filteredEntryMultimap) {
            this.this$0 = filteredEntryMultimap;
            super(filteredEntryMultimap);
        }

        @Override
        public int remove(@Nullable Object object, int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(object);
            }
            Collection collection = this.this$0.unfiltered.asMap().get(object);
            if (collection == null) {
                return 1;
            }
            Object object2 = object;
            int n2 = 0;
            Iterator iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                Object v = iterator2.next();
                if (!FilteredEntryMultimap.access$000(this.this$0, object2, v) || ++n2 > n) continue;
                iterator2.remove();
            }
            return n2;
        }

        @Override
        public Set<Multiset.Entry<K>> entrySet() {
            return new Multisets.EntrySet<K>(this){
                final Keys this$1;
                {
                    this.this$1 = keys2;
                }

                @Override
                Multiset<K> multiset() {
                    return this.this$1;
                }

                @Override
                public Iterator<Multiset.Entry<K>> iterator() {
                    return this.this$1.entryIterator();
                }

                @Override
                public int size() {
                    return this.this$1.this$0.keySet().size();
                }

                private boolean removeEntriesIf(Predicate<? super Multiset.Entry<K>> predicate) {
                    return this.this$1.this$0.removeEntriesIf(new Predicate<Map.Entry<K, Collection<V>>>(this, predicate){
                        final Predicate val$predicate;
                        final 1 this$2;
                        {
                            this.this$2 = var1_1;
                            this.val$predicate = predicate;
                        }

                        @Override
                        public boolean apply(Map.Entry<K, Collection<V>> entry) {
                            return this.val$predicate.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }

                        @Override
                        public boolean apply(Object object) {
                            return this.apply((Map.Entry)object);
                        }
                    });
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return this.removeEntriesIf(Predicates.in(collection));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return this.removeEntriesIf(Predicates.not(Predicates.in(collection)));
                }
            };
        }
    }

    class AsMap
    extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        final FilteredEntryMultimap this$0;

        AsMap(FilteredEntryMultimap filteredEntryMultimap) {
            this.this$0 = filteredEntryMultimap;
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.get(object) != null;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Collection<V> get(@Nullable Object object) {
            Collection collection = this.this$0.unfiltered.asMap().get(object);
            if (collection == null) {
                return null;
            }
            Object object2 = object;
            return (collection = FilteredEntryMultimap.filterCollection(collection, new ValuePredicate(this.this$0, object2))).isEmpty() ? null : collection;
        }

        @Override
        public Collection<V> remove(@Nullable Object object) {
            Collection collection = this.this$0.unfiltered.asMap().get(object);
            if (collection == null) {
                return null;
            }
            Object object2 = object;
            ArrayList arrayList = Lists.newArrayList();
            Iterator iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                Object v = iterator2.next();
                if (!FilteredEntryMultimap.access$000(this.this$0, object2, v)) continue;
                iterator2.remove();
                arrayList.add(v);
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            if (this.this$0.unfiltered instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(arrayList));
            }
            return Collections.unmodifiableList(arrayList);
        }

        @Override
        Set<K> createKeySet() {
            class KeySetImpl
            extends Maps.KeySet<K, Collection<V>> {
                final AsMap this$1;

                KeySetImpl(AsMap asMap) {
                    this.this$1 = asMap;
                    super(asMap);
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in(collection)));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
                }

                @Override
                public boolean remove(@Nullable Object object) {
                    return this.this$1.remove(object) != null;
                }
            }
            return new KeySetImpl(this);
        }

        @Override
        Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            class EntrySetImpl
            extends Maps.EntrySet<K, Collection<V>> {
                final AsMap this$1;

                EntrySetImpl(AsMap asMap) {
                    this.this$1 = asMap;
                }

                @Override
                Map<K, Collection<V>> map() {
                    return this.this$1;
                }

                @Override
                public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Map.Entry<K, Collection<V>>>(this){
                        final Iterator<Map.Entry<K, Collection<V>>> backingIterator;
                        final EntrySetImpl this$2;
                        {
                            this.this$2 = entrySetImpl;
                            this.backingIterator = this.this$2.this$1.this$0.unfiltered.asMap().entrySet().iterator();
                        }

                        @Override
                        protected Map.Entry<K, Collection<V>> computeNext() {
                            while (this.backingIterator.hasNext()) {
                                Map.Entry entry = this.backingIterator.next();
                                Object k = entry.getKey();
                                Collection collection = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(this.this$2.this$1.this$0, k));
                                if (collection.isEmpty()) continue;
                                return Maps.immutableEntry(k, collection);
                            }
                            return (Map.Entry)this.endOfData();
                        }

                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Predicates.in(collection));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Predicates.not(Predicates.in(collection)));
                }

                @Override
                public int size() {
                    return Iterators.size(this.iterator());
                }
            }
            return new EntrySetImpl(this);
        }

        @Override
        Collection<Collection<V>> createValues() {
            class ValuesImpl
            extends Maps.Values<K, Collection<V>> {
                final AsMap this$1;

                ValuesImpl(AsMap asMap) {
                    this.this$1 = asMap;
                    super(asMap);
                }

                @Override
                public boolean remove(@Nullable Object object) {
                    if (object instanceof Collection) {
                        Collection collection = (Collection)object;
                        Iterator iterator2 = this.this$1.this$0.unfiltered.asMap().entrySet().iterator();
                        while (iterator2.hasNext()) {
                            Map.Entry entry = iterator2.next();
                            Object k = entry.getKey();
                            Collection collection2 = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(this.this$1.this$0, k));
                            if (collection2.isEmpty() || !collection.equals(collection2)) continue;
                            if (collection2.size() == entry.getValue().size()) {
                                iterator2.remove();
                            } else {
                                collection2.clear();
                            }
                            return false;
                        }
                    }
                    return true;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
                }
            }
            return new ValuesImpl(this);
        }

        @Override
        public Object remove(@Nullable Object object) {
            return this.remove(object);
        }

        @Override
        public Object get(@Nullable Object object) {
            return this.get(object);
        }
    }

    final class ValuePredicate
    implements Predicate<V> {
        private final K key;
        final FilteredEntryMultimap this$0;

        ValuePredicate(FilteredEntryMultimap filteredEntryMultimap, K k) {
            this.this$0 = filteredEntryMultimap;
            this.key = k;
        }

        @Override
        public boolean apply(@Nullable V v) {
            return FilteredEntryMultimap.access$000(this.this$0, this.key, v);
        }
    }
}

