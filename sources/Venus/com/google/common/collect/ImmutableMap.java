/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableEnumMap;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableMapKeySet;
import com.google.common.collect.ImmutableMapValues;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableMap<K, V>
implements Map<K, V>,
Serializable {
    static final Map.Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
    @LazyInit
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    @LazyInit
    private transient ImmutableSet<K> keySet;
    @LazyInit
    private transient ImmutableCollection<V> values;
    @LazyInit
    private transient ImmutableSetMultimap<K, V> multimapView;

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return CollectCollectors.toImmutableMap(function, function2);
    }

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collectors.collectingAndThen(Collectors.toMap(function, function2, binaryOperator, LinkedHashMap::new), ImmutableMap::copyOf);
    }

    public static <K, V> ImmutableMap<K, V> of() {
        return ImmutableBiMap.of();
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v) {
        return ImmutableBiMap.of(k, v);
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2) {
        return RegularImmutableMap.fromEntries(ImmutableMap.entryOf(k, v), ImmutableMap.entryOf(k2, v2));
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return RegularImmutableMap.fromEntries(ImmutableMap.entryOf(k, v), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3));
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return RegularImmutableMap.fromEntries(ImmutableMap.entryOf(k, v), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3), ImmutableMap.entryOf(k4, v4));
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return RegularImmutableMap.fromEntries(ImmutableMap.entryOf(k, v), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3), ImmutableMap.entryOf(k4, v4), ImmutableMap.entryOf(k5, v5));
    }

    static <K, V> ImmutableMapEntry<K, V> entryOf(K k, V v) {
        return new ImmutableMapEntry<K, V>(k, v);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    static void checkNoConflict(boolean bl, String string, Map.Entry<?, ?> entry, Map.Entry<?, ?> entry2) {
        if (!bl) {
            throw new IllegalArgumentException("Multiple entries with same " + string + ": " + entry + " and " + entry2);
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap)) {
            ImmutableMap immutableMap = (ImmutableMap)map;
            if (!immutableMap.isPartialView()) {
                return immutableMap;
            }
        } else if (map instanceof EnumMap) {
            ImmutableMap<K, V> immutableMap = ImmutableMap.copyOfEnumMap((EnumMap)map);
            return immutableMap;
        }
        return ImmutableMap.copyOf(map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Map.Entry<?, ?>[] entryArray = Iterables.toArray(iterable, EMPTY_ENTRY_ARRAY);
        switch (entryArray.length) {
            case 0: {
                return ImmutableMap.of();
            }
            case 1: {
                Map.Entry<?, ?> entry = entryArray[0];
                return ImmutableMap.of(entry.getKey(), entry.getValue());
            }
        }
        return RegularImmutableMap.fromEntries(entryArray);
    }

    private static <K extends Enum<K>, V> ImmutableMap<K, V> copyOfEnumMap(EnumMap<K, ? extends V> enumMap) {
        EnumMap<K, V> enumMap2 = new EnumMap<K, V>(enumMap);
        for (Map.Entry<K, V> entry : enumMap2.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(enumMap2);
    }

    ImmutableMap() {
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final V putIfAbsent(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final boolean replace(K k, V v, V v2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V replace(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final boolean remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.get(object) != null;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return ((ImmutableCollection)this.values()).contains(object);
    }

    @Override
    public abstract V get(@Nullable Object var1);

    @Override
    public final V getOrDefault(@Nullable Object object, @Nullable V v) {
        V v2 = this.get(object);
        return v2 != null ? v2 : v;
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.entrySet;
        return immutableSet == null ? (this.entrySet = this.createEntrySet()) : immutableSet;
    }

    abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();

    @Override
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.keySet;
        return immutableSet == null ? (this.keySet = this.createKeySet()) : immutableSet;
    }

    ImmutableSet<K> createKeySet() {
        return this.isEmpty() ? ImmutableSet.of() : new ImmutableMapKeySet(this);
    }

    UnmodifiableIterator<K> keyIterator() {
        Iterator iterator2 = ((ImmutableSet)this.entrySet()).iterator();
        return new UnmodifiableIterator<K>(this, (UnmodifiableIterator)iterator2){
            final UnmodifiableIterator val$entryIterator;
            final ImmutableMap this$0;
            {
                this.this$0 = immutableMap;
                this.val$entryIterator = unmodifiableIterator;
            }

            @Override
            public boolean hasNext() {
                return this.val$entryIterator.hasNext();
            }

            @Override
            public K next() {
                return ((Map.Entry)this.val$entryIterator.next()).getKey();
            }
        };
    }

    Spliterator<K> keySpliterator() {
        return CollectSpliterators.map(((ImmutableCollection)((Object)this.entrySet())).spliterator(), Map.Entry::getKey);
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.values;
        return immutableCollection == null ? (this.values = this.createValues()) : immutableCollection;
    }

    ImmutableCollection<V> createValues() {
        return new ImmutableMapValues(this);
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (this.isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.multimapView;
        return immutableSetMultimap == null ? (this.multimapView = new ImmutableSetMultimap(new MapViewOfValuesAsSingletonSets(this, null), this.size(), null)) : immutableSetMultimap;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    abstract boolean isPartialView();

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    boolean isHashCodeFast() {
        return true;
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    Object writeReplace() {
        return new SerializedForm(this);
    }

    @Override
    public Set entrySet() {
        return this.entrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    static class SerializedForm
    implements Serializable {
        private final Object[] keys;
        private final Object[] values;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableMap<?, ?> immutableMap) {
            this.keys = new Object[immutableMap.size()];
            this.values = new Object[immutableMap.size()];
            int n = 0;
            for (Map.Entry entry : immutableMap.entrySet()) {
                this.keys[n] = entry.getKey();
                this.values[n] = entry.getValue();
                ++n;
            }
        }

        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>(this.keys.length);
            return this.createMap(builder);
        }

        Object createMap(Builder<Object, Object> builder) {
            for (int i = 0; i < this.keys.length; ++i) {
                builder.put(this.keys[i], this.values[i]);
            }
            return builder.build();
        }
    }

    private final class MapViewOfValuesAsSingletonSets
    extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        final ImmutableMap this$0;

        private MapViewOfValuesAsSingletonSets(ImmutableMap immutableMap) {
            this.this$0 = immutableMap;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public ImmutableSet<K> keySet() {
            return this.this$0.keySet();
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public ImmutableSet<V> get(@Nullable Object object) {
            Object v = this.this$0.get(object);
            return v == null ? null : ImmutableSet.of(v);
        }

        @Override
        boolean isPartialView() {
            return this.this$0.isPartialView();
        }

        @Override
        public int hashCode() {
            return this.this$0.hashCode();
        }

        @Override
        boolean isHashCodeFast() {
            return this.this$0.isHashCodeFast();
        }

        @Override
        UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> entryIterator() {
            Iterator iterator2 = ((ImmutableSet)this.this$0.entrySet()).iterator();
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>(this, iterator2){
                final Iterator val$backingIterator;
                final MapViewOfValuesAsSingletonSets this$1;
                {
                    this.this$1 = mapViewOfValuesAsSingletonSets;
                    this.val$backingIterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$backingIterator.hasNext();
                }

                @Override
                public Map.Entry<K, ImmutableSet<V>> next() {
                    Map.Entry entry = (Map.Entry)this.val$backingIterator.next();
                    return new AbstractMapEntry<K, ImmutableSet<V>>(this, entry){
                        final Map.Entry val$backingEntry;
                        final 1 this$2;
                        {
                            this.this$2 = var1_1;
                            this.val$backingEntry = entry;
                        }

                        @Override
                        public K getKey() {
                            return this.val$backingEntry.getKey();
                        }

                        @Override
                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.of(this.val$backingEntry.getValue());
                        }

                        @Override
                        public Object getValue() {
                            return this.getValue();
                        }
                    };
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public Object get(@Nullable Object object) {
            return this.get(object);
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        MapViewOfValuesAsSingletonSets(ImmutableMap immutableMap, com.google.common.collect.ImmutableMap$1 var2_2) {
            this(immutableMap);
        }
    }

    static abstract class IteratorBasedImmutableMap<K, V>
    extends ImmutableMap<K, V> {
        IteratorBasedImmutableMap() {
        }

        abstract UnmodifiableIterator<Map.Entry<K, V>> entryIterator();

        Spliterator<Map.Entry<K, V>> entrySpliterator() {
            return Spliterators.spliterator(this.entryIterator(), (long)this.size(), 1297);
        }

        @Override
        ImmutableSet<Map.Entry<K, V>> createEntrySet() {
            class EntrySetImpl
            extends ImmutableMapEntrySet<K, V> {
                final IteratorBasedImmutableMap this$0;

                EntrySetImpl(IteratorBasedImmutableMap iteratorBasedImmutableMap) {
                    this.this$0 = iteratorBasedImmutableMap;
                }

                @Override
                ImmutableMap<K, V> map() {
                    return this.this$0;
                }

                @Override
                public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                    return this.this$0.entryIterator();
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            }
            return new EntrySetImpl(this);
        }

        @Override
        public Set entrySet() {
            return super.entrySet();
        }

        @Override
        public Collection values() {
            return super.values();
        }

        @Override
        public Set keySet() {
            return super.keySet();
        }
    }

    public static class Builder<K, V> {
        Comparator<? super V> valueComparator;
        ImmutableMapEntry<K, V>[] entries;
        int size;
        boolean entriesUsed;

        public Builder() {
            this(4);
        }

        Builder(int n) {
            this.entries = new ImmutableMapEntry[n];
            this.size = 0;
            this.entriesUsed = false;
        }

        private void ensureCapacity(int n) {
            if (n > this.entries.length) {
                this.entries = Arrays.copyOf(this.entries, ImmutableCollection.Builder.expandedCapacity(this.entries.length, n));
                this.entriesUsed = false;
            }
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            this.ensureCapacity(this.size + 1);
            ImmutableMapEntry<K, V> immutableMapEntry = ImmutableMap.entryOf(k, v);
            this.entries[this.size++] = immutableMapEntry;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return this.put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return this.putAll(map.entrySet());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            if (iterable instanceof Collection) {
                this.ensureCapacity(this.size + ((Collection)iterable).size());
            }
            for (Map.Entry<K, V> entry : iterable) {
                this.put(entry);
            }
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            Preconditions.checkState(this.valueComparator == null, "valueComparator was already set");
            this.valueComparator = Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        @CanIgnoreReturnValue
        Builder<K, V> combine(Builder<K, V> builder) {
            Preconditions.checkNotNull(builder);
            this.ensureCapacity(this.size + builder.size);
            System.arraycopy(builder.entries, 0, this.entries, this.size, builder.size);
            this.size += builder.size;
            return this;
        }

        public ImmutableMap<K, V> build() {
            switch (this.size) {
                case 0: {
                    return ImmutableMap.of();
                }
                case 1: {
                    return ImmutableMap.of(this.entries[0].getKey(), this.entries[0].getValue());
                }
            }
            if (this.valueComparator != null) {
                if (this.entriesUsed) {
                    this.entries = Arrays.copyOf(this.entries, this.size);
                }
                Arrays.sort(this.entries, 0, this.size, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
            }
            this.entriesUsed = this.size == this.entries.length;
            return RegularImmutableMap.fromEntryArray(this.size, this.entries);
        }
    }
}

