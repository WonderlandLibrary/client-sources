/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMapFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(serializable=true, emulated=true)
public final class ImmutableSortedMap<K, V>
extends ImmutableSortedMapFauxverideShim<K, V>
implements NavigableMap<K, V> {
    private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
    private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.of());
    private final transient RegularImmutableSortedSet<K> keySet;
    private final transient ImmutableList<V> valueList;
    private transient ImmutableSortedMap<K, V> descendingMap;
    private static final long serialVersionUID = 0L;

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableSortedMap<K, V>> toImmutableSortedMap(Comparator<? super K> comparator, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return CollectCollectors.toImmutableSortedMap(comparator, function, function2);
    }

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableSortedMap<K, V>> toImmutableSortedMap(Comparator<? super K> comparator, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collectors.collectingAndThen(Collectors.toMap(function, function2, binaryOperator, () -> ImmutableSortedMap.lambda$toImmutableSortedMap$0(comparator)), ImmutableSortedMap::copyOfSorted);
    }

    static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return ImmutableSortedMap.of();
        }
        return new ImmutableSortedMap(ImmutableSortedSet.emptySet(comparator), ImmutableList.of());
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return NATURAL_EMPTY_MAP;
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v) {
        return ImmutableSortedMap.of(Ordering.natural(), k, v);
    }

    private static <K, V> ImmutableSortedMap<K, V> of(Comparator<? super K> comparator, K k, V v) {
        return new ImmutableSortedMap<K, V>(new RegularImmutableSortedSet<K>(ImmutableList.of(k), Preconditions.checkNotNull(comparator)), ImmutableList.of(v));
    }

    private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> ofEntries(ImmutableMapEntry<K, V> ... immutableMapEntryArray) {
        return ImmutableSortedMap.fromEntries(Ordering.natural(), false, immutableMapEntryArray, immutableMapEntryArray.length);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3), ImmutableSortedMap.entryOf(k4, v4));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3), ImmutableSortedMap.entryOf(k4, v4), ImmutableSortedMap.entryOf(k5, v5));
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        Ordering ordering = (Ordering)NATURAL_ORDER;
        return ImmutableSortedMap.copyOfInternal(map, ordering);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return ImmutableSortedMap.copyOfInternal(map, Preconditions.checkNotNull(comparator));
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Ordering ordering = (Ordering)NATURAL_ORDER;
        return ImmutableSortedMap.copyOf(iterable, ordering);
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable, Comparator<? super K> comparator) {
        return ImmutableSortedMap.fromEntries(Preconditions.checkNotNull(comparator), false, iterable);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> sortedMap) {
        ImmutableSortedMap immutableSortedMap;
        Comparator<Object> comparator = sortedMap.comparator();
        if (comparator == null) {
            comparator = NATURAL_ORDER;
        }
        if (sortedMap instanceof ImmutableSortedMap && !(immutableSortedMap = (ImmutableSortedMap)sortedMap).isPartialView()) {
            return immutableSortedMap;
        }
        return ImmutableSortedMap.fromEntries(comparator, true, sortedMap.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        SortedMap sortedMap;
        boolean bl = false;
        if (map instanceof SortedMap) {
            sortedMap = (SortedMap)map;
            Comparator comparator2 = sortedMap.comparator();
            boolean bl2 = comparator2 == null ? comparator == NATURAL_ORDER : (bl = comparator.equals(comparator2));
        }
        if (bl && map instanceof ImmutableSortedMap && !((ImmutableSortedMap)(sortedMap = (ImmutableSortedMap)map)).isPartialView()) {
            return sortedMap;
        }
        return ImmutableSortedMap.fromEntries(comparator, bl, map.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> comparator, boolean bl, Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Map.Entry[] entryArray = Iterables.toArray(iterable, EMPTY_ENTRY_ARRAY);
        return ImmutableSortedMap.fromEntries(comparator, bl, entryArray, entryArray.length);
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> comparator, boolean bl, Map.Entry<K, V>[] entryArray, int n) {
        switch (n) {
            case 0: {
                return ImmutableSortedMap.emptyMap(comparator);
            }
            case 1: {
                return ImmutableSortedMap.of(comparator, entryArray[0].getKey(), entryArray[0].getValue());
            }
        }
        Object[] objectArray = new Object[n];
        Object[] objectArray2 = new Object[n];
        if (bl) {
            for (int i = 0; i < n; ++i) {
                K k = entryArray[i].getKey();
                V v = entryArray[i].getValue();
                CollectPreconditions.checkEntryNotNull(k, v);
                objectArray[i] = k;
                objectArray2[i] = v;
            }
        } else {
            Arrays.sort(entryArray, 0, n, Ordering.from(comparator).onKeys());
            K k = entryArray[0].getKey();
            objectArray[0] = k;
            objectArray2[0] = entryArray[0].getValue();
            for (int i = 1; i < n; ++i) {
                K k2 = entryArray[i].getKey();
                V v = entryArray[i].getValue();
                CollectPreconditions.checkEntryNotNull(k2, v);
                objectArray[i] = k2;
                objectArray2[i] = v;
                ImmutableSortedMap.checkNoConflict(comparator.compare(k, k2) != 0, "key", entryArray[i - 1], entryArray[i]);
                k = k2;
            }
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet<K>(new RegularImmutableList(objectArray), comparator), new RegularImmutableList(objectArray2));
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList) {
        this(regularImmutableSortedSet, immutableList, null);
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.keySet = regularImmutableSortedSet;
        this.valueList = immutableList;
        this.descendingMap = immutableSortedMap;
    }

    @Override
    public int size() {
        return this.valueList.size();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        ImmutableList immutableList = this.keySet.asList();
        for (int i = 0; i < this.size(); ++i) {
            biConsumer.accept(immutableList.get(i), this.valueList.get(i));
        }
    }

    @Override
    public V get(@Nullable Object object) {
        int n = this.keySet.indexOf(object);
        return n == -1 ? null : (V)this.valueList.get(n);
    }

    @Override
    boolean isPartialView() {
        return this.keySet.isPartialView() || this.valueList.isPartialView();
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        class EntrySet
        extends ImmutableMapEntrySet<K, V> {
            final ImmutableSortedMap this$0;

            EntrySet(ImmutableSortedMap immutableSortedMap) {
                this.this$0 = immutableSortedMap;
            }

            @Override
            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return this.asList().iterator();
            }

            @Override
            public Spliterator<Map.Entry<K, V>> spliterator() {
                return this.asList().spliterator();
            }

            @Override
            public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
                this.asList().forEach(consumer);
            }

            @Override
            ImmutableList<Map.Entry<K, V>> createAsList() {
                return new ImmutableAsList<Map.Entry<K, V>>(this){
                    final EntrySet this$1;
                    {
                        this.this$1 = entrySet;
                    }

                    @Override
                    public Map.Entry<K, V> get(int n) {
                        return Maps.immutableEntry(ImmutableSortedMap.access$200(this.this$1.this$0).asList().get(n), ImmutableSortedMap.access$300(this.this$1.this$0).get(n));
                    }

                    @Override
                    public Spliterator<Map.Entry<K, V>> spliterator() {
                        return CollectSpliterators.indexed(this.size(), 1297, this::get);
                    }

                    @Override
                    ImmutableCollection<Map.Entry<K, V>> delegateCollection() {
                        return this.this$1;
                    }

                    @Override
                    public Object get(int n) {
                        return this.get(n);
                    }
                };
            }

            @Override
            ImmutableMap<K, V> map() {
                return this.this$0;
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        }
        return this.isEmpty() ? ImmutableSet.of() : new EntrySet(this);
    }

    @Override
    public ImmutableSortedSet<K> keySet() {
        return this.keySet;
    }

    @Override
    public ImmutableCollection<V> values() {
        return this.valueList;
    }

    @Override
    public Comparator<? super K> comparator() {
        return ((ImmutableSortedSet)this.keySet()).comparator();
    }

    @Override
    public K firstKey() {
        return (K)((ImmutableSortedSet)this.keySet()).first();
    }

    @Override
    public K lastKey() {
        return (K)((ImmutableSortedSet)this.keySet()).last();
    }

    private ImmutableSortedMap<K, V> getSubMap(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n == n2) {
            return ImmutableSortedMap.emptyMap(this.comparator());
        }
        return new ImmutableSortedMap<K, V>(this.keySet.getSubSet(n, n2), this.valueList.subList(n, n2));
    }

    @Override
    public ImmutableSortedMap<K, V> headMap(K k) {
        return this.headMap((Object)k, true);
    }

    @Override
    public ImmutableSortedMap<K, V> headMap(K k, boolean bl) {
        return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(k), bl));
    }

    @Override
    public ImmutableSortedMap<K, V> subMap(K k, K k2) {
        return this.subMap((Object)k, true, (Object)k2, true);
    }

    @Override
    public ImmutableSortedMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(k2);
        Preconditions.checkArgument(this.comparator().compare(k, k2) <= 0, "expected fromKey <= toKey but %s > %s", k, k2);
        return ((ImmutableSortedMap)this.headMap((Object)k2, bl2)).tailMap((Object)k, bl);
    }

    @Override
    public ImmutableSortedMap<K, V> tailMap(K k) {
        return this.tailMap((Object)k, false);
    }

    @Override
    public ImmutableSortedMap<K, V> tailMap(K k, boolean bl) {
        return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(k), bl), this.size());
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return ((ImmutableSortedMap)this.headMap((Object)k, true)).lastEntry();
    }

    @Override
    public K lowerKey(K k) {
        return Maps.keyOrNull(this.lowerEntry(k));
    }

    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return ((ImmutableSortedMap)this.headMap((Object)k, false)).lastEntry();
    }

    @Override
    public K floorKey(K k) {
        return Maps.keyOrNull(this.floorEntry(k));
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return ((ImmutableSortedMap)this.tailMap((Object)k, false)).firstEntry();
    }

    @Override
    public K ceilingKey(K k) {
        return Maps.keyOrNull(this.ceilingEntry(k));
    }

    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return ((ImmutableSortedMap)this.tailMap((Object)k, true)).firstEntry();
    }

    @Override
    public K higherKey(K k) {
        return Maps.keyOrNull(this.higherEntry(k));
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        return this.isEmpty() ? null : (Map.Entry)((ImmutableSet)this.entrySet()).asList().get(0);
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        return this.isEmpty() ? null : (Map.Entry)((ImmutableSet)this.entrySet()).asList().get(this.size() - 1);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<Object, V> immutableSortedMap = this.descendingMap;
        if (immutableSortedMap == null) {
            if (this.isEmpty()) {
                immutableSortedMap = ImmutableSortedMap.emptyMap(Ordering.from(this.comparator()).reverse());
                return immutableSortedMap;
            }
            immutableSortedMap = new ImmutableSortedMap<K, V>((RegularImmutableSortedSet)this.keySet.descendingSet(), this.valueList.reverse(), this);
            return immutableSortedMap;
        }
        return immutableSortedMap;
    }

    @Override
    public ImmutableSortedSet<K> navigableKeySet() {
        return this.keySet;
    }

    @Override
    public ImmutableSortedSet<K> descendingKeySet() {
        return this.keySet.descendingSet();
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    @Override
    public ImmutableSet keySet() {
        return this.keySet();
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

    @Override
    public SortedMap tailMap(Object object) {
        return this.tailMap(object);
    }

    @Override
    public SortedMap headMap(Object object) {
        return this.headMap(object);
    }

    @Override
    public SortedMap subMap(Object object, Object object2) {
        return this.subMap(object, object2);
    }

    @Override
    public NavigableMap tailMap(Object object, boolean bl) {
        return this.tailMap(object, bl);
    }

    @Override
    public NavigableMap headMap(Object object, boolean bl) {
        return this.headMap(object, bl);
    }

    @Override
    public NavigableMap subMap(Object object, boolean bl, Object object2, boolean bl2) {
        return this.subMap(object, bl, object2, bl2);
    }

    @Override
    public NavigableSet descendingKeySet() {
        return this.descendingKeySet();
    }

    @Override
    public NavigableSet navigableKeySet() {
        return this.navigableKeySet();
    }

    @Override
    public NavigableMap descendingMap() {
        return this.descendingMap();
    }

    private static TreeMap lambda$toImmutableSortedMap$0(Comparator comparator) {
        return new TreeMap(comparator);
    }

    static ImmutableSortedMap access$000(Comparator comparator, Object object, Object object2) {
        return ImmutableSortedMap.of(comparator, object, object2);
    }

    static ImmutableSortedMap access$100(Comparator comparator, boolean bl, Map.Entry[] entryArray, int n) {
        return ImmutableSortedMap.fromEntries(comparator, bl, entryArray, n);
    }

    static RegularImmutableSortedSet access$200(ImmutableSortedMap immutableSortedMap) {
        return immutableSortedMap.keySet;
    }

    static ImmutableList access$300(ImmutableSortedMap immutableSortedMap) {
        return immutableSortedMap.valueList;
    }

    private static class SerializedForm
    extends ImmutableMap.SerializedForm {
        private final Comparator<Object> comparator;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableSortedMap<?, ?> immutableSortedMap) {
            super(immutableSortedMap);
            this.comparator = immutableSortedMap.comparator();
        }

        @Override
        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>(this.comparator);
            return this.createMap(builder);
        }
    }

    public static class Builder<K, V>
    extends ImmutableMap.Builder<K, V> {
        private final Comparator<? super K> comparator;

        public Builder(Comparator<? super K> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            super.put(k, v);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        @Override
        Builder<K, V> combine(ImmutableMap.Builder<K, V> builder) {
            super.combine(builder);
            return this;
        }

        @Override
        public ImmutableSortedMap<K, V> build() {
            switch (this.size) {
                case 0: {
                    return ImmutableSortedMap.emptyMap(this.comparator);
                }
                case 1: {
                    return ImmutableSortedMap.access$000(this.comparator, this.entries[0].getKey(), this.entries[0].getValue());
                }
            }
            return ImmutableSortedMap.access$100(this.comparator, false, this.entries, this.size);
        }

        @Override
        public ImmutableMap build() {
            return this.build();
        }

        @Override
        ImmutableMap.Builder combine(ImmutableMap.Builder builder) {
            return this.combine(builder);
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        @Beta
        public ImmutableMap.Builder orderEntriesByValue(Comparator comparator) {
            return this.orderEntriesByValue(comparator);
        }

        @Override
        @CanIgnoreReturnValue
        @Beta
        public ImmutableMap.Builder putAll(Iterable iterable) {
            return this.putAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMap.Builder putAll(Map map) {
            return this.putAll(map);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMap.Builder put(Map.Entry entry) {
            return this.put(entry);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMap.Builder put(Object object, Object object2) {
            return this.put(object, object2);
        }
    }
}

