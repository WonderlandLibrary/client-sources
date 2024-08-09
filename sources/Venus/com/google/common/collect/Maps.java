/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractNavigableMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.BoundType;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingNavigableSet;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ForwardingSortedMap;
import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.ImmutableEnumMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMapDifference;
import com.google.common.collect.Synchronized;
import com.google.common.collect.TransformedIterator;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Maps {
    static final Joiner.MapJoiner STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");

    private Maps() {
    }

    static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
        return EntryFunction.KEY;
    }

    static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE;
    }

    static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> iterator2) {
        return Iterators.transform(iterator2, Maps.keyFunction());
    }

    static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> iterator2) {
        return Iterators.transform(iterator2, Maps.valueFunction());
    }

    @GwtCompatible(serializable=true)
    @Beta
    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
        if (map instanceof ImmutableEnumMap) {
            ImmutableEnumMap immutableEnumMap = (ImmutableEnumMap)map;
            return immutableEnumMap;
        }
        if (map.isEmpty()) {
            return ImmutableMap.of();
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            Preconditions.checkNotNull(entry.getKey());
            Preconditions.checkNotNull(entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(new EnumMap<K, V>(map));
    }

    @Beta
    public static <T, K extends Enum<K>, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableEnumMap(java.util.function.Function<? super T, ? extends K> function, java.util.function.Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(Maps::lambda$toImmutableEnumMap$1, (arg_0, arg_1) -> Maps.lambda$toImmutableEnumMap$2(function, function2, arg_0, arg_1), Accumulator::combine, Accumulator::toImmutableMap, Collector.Characteristics.UNORDERED);
    }

    @Beta
    public static <T, K extends Enum<K>, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableEnumMap(java.util.function.Function<? super T, ? extends K> function, java.util.function.Function<? super T, ? extends V> function2, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collector.of(() -> Maps.lambda$toImmutableEnumMap$3(binaryOperator), (arg_0, arg_1) -> Maps.lambda$toImmutableEnumMap$4(function, function2, arg_0, arg_1), Accumulator::combine, Accumulator::toImmutableMap, new Collector.Characteristics[0]);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int n) {
        return new HashMap(Maps.capacity(n));
    }

    static int capacity(int n) {
        if (n < 3) {
            CollectPreconditions.checkNonnegative(n, "expectedSize");
            return n + 1;
        }
        if (n < 0x40000000) {
            return (int)((float)n / 0.75f + 1.0f);
        }
        return 0;
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int n) {
        return new LinkedHashMap(Maps.capacity(n));
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new MapMaker().makeMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
        return new TreeMap<K, V>(sortedMap);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> comparator) {
        return new TreeMap(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> clazz) {
        return new EnumMap(Preconditions.checkNotNull(clazz));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap();
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
        if (map instanceof SortedMap) {
            SortedMap sortedMap = (SortedMap)map;
            SortedMapDifference<? extends K, ? extends V> sortedMapDifference = Maps.difference(sortedMap, map2);
            return sortedMapDifference;
        }
        return Maps.difference(map, map2, Equivalence.equals());
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
        Preconditions.checkNotNull(equivalence);
        LinkedHashMap<K, V> linkedHashMap = Maps.newLinkedHashMap();
        LinkedHashMap<? extends K, ? extends V> linkedHashMap2 = new LinkedHashMap<K, V>(map2);
        LinkedHashMap<K, V> linkedHashMap3 = Maps.newLinkedHashMap();
        LinkedHashMap<K, V> linkedHashMap4 = Maps.newLinkedHashMap();
        Maps.doDifference(map, map2, equivalence, linkedHashMap, linkedHashMap2, linkedHashMap3, linkedHashMap4);
        return new MapDifferenceImpl<K, V>(linkedHashMap, linkedHashMap2, linkedHashMap3, linkedHashMap4);
    }

    private static <K, V> void doDifference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence, Map<K, V> map3, Map<K, V> map4, Map<K, V> map5, Map<K, MapDifference.ValueDifference<V>> map6) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            if (map2.containsKey(k)) {
                V v2 = map4.remove(k);
                if (equivalence.equivalent(v, v2)) {
                    map5.put(k, v);
                    continue;
                }
                map6.put(k, ValueDifferenceImpl.create(v, v2));
                continue;
            }
            map3.put(k, v);
        }
    }

    private static <K, V> Map<K, V> unmodifiableMap(Map<K, ? extends V> map) {
        if (map instanceof SortedMap) {
            return Collections.unmodifiableSortedMap((SortedMap)map);
        }
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        Comparator<K> comparator = Maps.orNaturalOrder(sortedMap.comparator());
        TreeMap<K, V> treeMap = Maps.newTreeMap(comparator);
        TreeMap<? extends K, ? extends V> treeMap2 = Maps.newTreeMap(comparator);
        treeMap2.putAll(map);
        TreeMap<K, V> treeMap3 = Maps.newTreeMap(comparator);
        TreeMap<K, V> treeMap4 = Maps.newTreeMap(comparator);
        Maps.doDifference(sortedMap, map, Equivalence.equals(), treeMap, treeMap2, treeMap3, treeMap4);
        return new SortedMapDifferenceImpl<K, V>(treeMap, treeMap2, treeMap3, treeMap4);
    }

    static <E> Comparator<? super E> orNaturalOrder(@Nullable Comparator<? super E> comparator) {
        if (comparator != null) {
            return comparator;
        }
        return Ordering.natural();
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new AsMapView<K, V>(set, function);
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
        return new SortedAsMapView<K, V>(sortedSet, function);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
        return new NavigableAsMapView<K, V>(navigableSet, function);
    }

    static <K, V> Iterator<Map.Entry<K, V>> asMapEntryIterator(Set<K> set, Function<? super K, V> function) {
        return new TransformedIterator<K, Map.Entry<K, V>>(set.iterator(), function){
            final Function val$function;
            {
                this.val$function = function;
                super(iterator2);
            }

            @Override
            Map.Entry<K, V> transform(K k) {
                return Maps.immutableEntry(k, this.val$function.apply(k));
            }

            @Override
            Object transform(Object object) {
                return this.transform(object);
            }
        };
    }

    private static <E> Set<E> removeOnlySet(Set<E> set) {
        return new ForwardingSet<E>(set){
            final Set val$set;
            {
                this.val$set = set;
            }

            @Override
            protected Set<E> delegate() {
                return this.val$set;
            }

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    private static <E> SortedSet<E> removeOnlySortedSet(SortedSet<E> sortedSet) {
        return new ForwardingSortedSet<E>(sortedSet){
            final SortedSet val$set;
            {
                this.val$set = sortedSet;
            }

            @Override
            protected SortedSet<E> delegate() {
                return this.val$set;
            }

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public SortedSet<E> headSet(E e) {
                return Maps.access$300(super.headSet(e));
            }

            @Override
            public SortedSet<E> subSet(E e, E e2) {
                return Maps.access$300(super.subSet(e, e2));
            }

            @Override
            public SortedSet<E> tailSet(E e) {
                return Maps.access$300(super.tailSet(e));
            }

            @Override
            protected Set delegate() {
                return this.delegate();
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    @GwtIncompatible
    private static <E> NavigableSet<E> removeOnlyNavigableSet(NavigableSet<E> navigableSet) {
        return new ForwardingNavigableSet<E>(navigableSet){
            final NavigableSet val$set;
            {
                this.val$set = navigableSet;
            }

            @Override
            protected NavigableSet<E> delegate() {
                return this.val$set;
            }

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public SortedSet<E> headSet(E e) {
                return Maps.access$300(super.headSet(e));
            }

            @Override
            public SortedSet<E> subSet(E e, E e2) {
                return Maps.access$300(super.subSet(e, e2));
            }

            @Override
            public SortedSet<E> tailSet(E e) {
                return Maps.access$300(super.tailSet(e));
            }

            @Override
            public NavigableSet<E> headSet(E e, boolean bl) {
                return Maps.access$400(super.headSet(e, bl));
            }

            @Override
            public NavigableSet<E> tailSet(E e, boolean bl) {
                return Maps.access$400(super.tailSet(e, bl));
            }

            @Override
            public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
                return Maps.access$400(super.subSet(e, bl, e2, bl2));
            }

            @Override
            public NavigableSet<E> descendingSet() {
                return Maps.access$400(super.descendingSet());
            }

            @Override
            protected SortedSet delegate() {
                return this.delegate();
            }

            @Override
            protected Set delegate() {
                return this.delegate();
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
        return Maps.toMap(iterable.iterator(), function);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> iterator2, Function<? super K, V> function) {
        Preconditions.checkNotNull(function);
        LinkedHashMap<K, V> linkedHashMap = Maps.newLinkedHashMap();
        while (iterator2.hasNext()) {
            K k = iterator2.next();
            linkedHashMap.put(k, function.apply(k));
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
        return Maps.uniqueIndex(iterable.iterator(), function);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> iterator2, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (iterator2.hasNext()) {
            V v = iterator2.next();
            builder.put(function.apply(v), v);
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(illegalArgumentException.getMessage() + ". To index multiple values under a key, use Multimaps.index.");
        }
    }

    @GwtIncompatible
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            builder.put(string, properties.getProperty(string));
        }
        return builder.build();
    }

    @GwtCompatible(serializable=true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K k, @Nullable V v) {
        return new ImmutableEntry<K, V>(k, v);
    }

    static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
        return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet(set));
    }

    static <K, V> Map.Entry<K, V> unmodifiableEntry(Map.Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>(entry){
            final Map.Entry val$entry;
            {
                this.val$entry = entry;
            }

            @Override
            public K getKey() {
                return this.val$entry.getKey();
            }

            @Override
            public V getValue() {
                return this.val$entry.getValue();
            }
        };
    }

    static <K, V> UnmodifiableIterator<Map.Entry<K, V>> unmodifiableEntryIterator(Iterator<Map.Entry<K, V>> iterator2) {
        return new UnmodifiableIterator<Map.Entry<K, V>>(iterator2){
            final Iterator val$entryIterator;
            {
                this.val$entryIterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$entryIterator.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                return Maps.unmodifiableEntry((Map.Entry)this.val$entryIterator.next());
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Beta
    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
        return new BiMapConverter<A, B>(biMap);
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
        return Synchronized.biMap(biMap, null);
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
        return new UnmodifiableBiMap<K, V>(biMap, null);
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, Function<? super V1, V2> function) {
        return Maps.transformEntries(map, Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, Function<? super V1, V2> function) {
        return Maps.transformEntries(sortedMap, Maps.asEntryTransformer(function));
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, Function<? super V1, V2> function) {
        return Maps.transformEntries(navigableMap, Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMap<K, V1, V2>(map, entryTransformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesSortedMap<K, V1, V2>(sortedMap, entryTransformer);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesNavigableMap<K, V1, V2>(navigableMap, entryTransformer);
    }

    static <K, V1, V2> EntryTransformer<K, V1, V2> asEntryTransformer(Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>(function){
            final Function val$function;
            {
                this.val$function = function;
            }

            @Override
            public V2 transformEntry(K k, V1 V1) {
                return this.val$function.apply(V1);
            }
        };
    }

    static <K, V1, V2> Function<V1, V2> asValueToValueFunction(EntryTransformer<? super K, V1, V2> entryTransformer, K k) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<V1, V2>(entryTransformer, k){
            final EntryTransformer val$transformer;
            final Object val$key;
            {
                this.val$transformer = entryTransformer;
                this.val$key = object;
            }

            @Override
            public V2 apply(@Nullable V1 V1) {
                return this.val$transformer.transformEntry(this.val$key, V1);
            }
        };
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, V2> asEntryToValueFunction(EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, V2>(entryTransformer){
            final EntryTransformer val$transformer;
            {
                this.val$transformer = entryTransformer;
            }

            @Override
            public V2 apply(Map.Entry<K, V1> entry) {
                return this.val$transformer.transformEntry(entry.getKey(), entry.getValue());
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Map.Entry)object);
            }
        };
    }

    static <V2, K, V1> Map.Entry<K, V2> transformEntry(EntryTransformer<? super K, ? super V1, V2> entryTransformer, Map.Entry<K, V1> entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V2>(entry, entryTransformer){
            final Map.Entry val$entry;
            final EntryTransformer val$transformer;
            {
                this.val$entry = entry;
                this.val$transformer = entryTransformer;
            }

            @Override
            public K getKey() {
                return this.val$entry.getKey();
            }

            @Override
            public V2 getValue() {
                return this.val$transformer.transformEntry(this.val$entry.getKey(), this.val$entry.getValue());
            }
        };
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> asEntryToEntryFunction(EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>(entryTransformer){
            final EntryTransformer val$transformer;
            {
                this.val$transformer = entryTransformer;
            }

            @Override
            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                return Maps.transformEntry(this.val$transformer, entry);
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Map.Entry)object);
            }
        };
    }

    static <K> Predicate<Map.Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> predicate) {
        return Predicates.compose(predicate, Maps.keyFunction());
    }

    static <V> Predicate<Map.Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> predicate) {
        return Predicates.compose(predicate, Maps.valueFunction());
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        Predicate<Map.Entry<? super K, ?>> predicate2 = Maps.keyPredicateOnEntries(predicate);
        return map instanceof AbstractFilteredMap ? Maps.filterFiltered((AbstractFilteredMap)map, predicate2) : new FilteredKeyMap<K, V>(Preconditions.checkNotNull(map), predicate, predicate2);
    }

    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
        return Maps.filterEntries(sortedMap, Maps.keyPredicateOnEntries(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
        return Maps.filterEntries(navigableMap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        return Maps.filterEntries(biMap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        return Maps.filterEntries(map, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(sortedMap, Maps.valuePredicateOnEntries(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(navigableMap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(biMap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return map instanceof AbstractFilteredMap ? Maps.filterFiltered((AbstractFilteredMap)map, predicate) : new FilteredEntryMap<K, V>(Preconditions.checkNotNull(map), predicate);
    }

    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return sortedMap instanceof FilteredEntrySortedMap ? Maps.filterFiltered((FilteredEntrySortedMap)sortedMap, predicate) : new FilteredEntrySortedMap<K, V>(Preconditions.checkNotNull(sortedMap), predicate);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return navigableMap instanceof FilteredEntryNavigableMap ? Maps.filterFiltered((FilteredEntryNavigableMap)navigableMap, predicate) : new FilteredEntryNavigableMap<K, V>(Preconditions.checkNotNull(navigableMap), predicate);
    }

    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        return biMap instanceof FilteredEntryBiMap ? Maps.filterFiltered((FilteredEntryBiMap)biMap, predicate) : new FilteredEntryBiMap<K, V>(biMap, predicate);
    }

    private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> abstractFilteredMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntryMap(abstractFilteredMap.unfiltered, Predicates.and(abstractFilteredMap.predicate, predicate));
    }

    private static <K, V> SortedMap<K, V> filterFiltered(FilteredEntrySortedMap<K, V> filteredEntrySortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Predicate<? super Map.Entry<K, V>> predicate2 = Predicates.and(filteredEntrySortedMap.predicate, predicate);
        return new FilteredEntrySortedMap<K, V>(filteredEntrySortedMap.sortedMap(), predicate2);
    }

    @GwtIncompatible
    private static <K, V> NavigableMap<K, V> filterFiltered(FilteredEntryNavigableMap<K, V> filteredEntryNavigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Predicate<? super Map.Entry<K, V>> predicate2 = Predicates.and(FilteredEntryNavigableMap.access$600(filteredEntryNavigableMap), predicate);
        return new FilteredEntryNavigableMap(FilteredEntryNavigableMap.access$700(filteredEntryNavigableMap), predicate2);
    }

    private static <K, V> BiMap<K, V> filterFiltered(FilteredEntryBiMap<K, V> filteredEntryBiMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Predicate<? super Map.Entry<K, V>> predicate2 = Predicates.and(filteredEntryBiMap.predicate, predicate);
        return new FilteredEntryBiMap<K, V>(filteredEntryBiMap.unfiltered(), predicate2);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        if (navigableMap instanceof UnmodifiableNavigableMap) {
            NavigableMap<K, ? extends V> navigableMap2 = navigableMap;
            return navigableMap2;
        }
        return new UnmodifiableNavigableMap<K, V>(navigableMap);
    }

    @Nullable
    private static <K, V> Map.Entry<K, V> unmodifiableOrNull(@Nullable Map.Entry<K, ? extends V> entry) {
        return entry == null ? null : Maps.unmodifiableEntry(entry);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.navigableMap(navigableMap);
    }

    static <V> V safeGet(Map<?, V> map, @Nullable Object object) {
        Preconditions.checkNotNull(map);
        try {
            return map.get(object);
        } catch (ClassCastException classCastException) {
            return null;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    static boolean safeContainsKey(Map<?, ?> map, Object object) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(object);
        } catch (ClassCastException classCastException) {
            return true;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    static <V> V safeRemove(Map<?, V> map, Object object) {
        Preconditions.checkNotNull(map);
        try {
            return map.remove(object);
        } catch (ClassCastException classCastException) {
            return null;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    static boolean containsKeyImpl(Map<?, ?> map, @Nullable Object object) {
        return Iterators.contains(Maps.keyIterator(map.entrySet().iterator()), object);
    }

    static boolean containsValueImpl(Map<?, ?> map, @Nullable Object object) {
        return Iterators.contains(Maps.valueIterator(map.entrySet().iterator()), object);
    }

    static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> collection, Object object) {
        if (!(object instanceof Map.Entry)) {
            return true;
        }
        return collection.contains(Maps.unmodifiableEntry((Map.Entry)object));
    }

    static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> collection, Object object) {
        if (!(object instanceof Map.Entry)) {
            return true;
        }
        return collection.remove(Maps.unmodifiableEntry((Map.Entry)object));
    }

    static boolean equalsImpl(Map<?, ?> map, Object object) {
        if (map == object) {
            return false;
        }
        if (object instanceof Map) {
            Map map2 = (Map)object;
            return map.entrySet().equals(map2.entrySet());
        }
        return true;
    }

    static String toStringImpl(Map<?, ?> map) {
        StringBuilder stringBuilder = Collections2.newStringBuilderForCollection(map.size()).append('{');
        STANDARD_JOINER.appendTo(stringBuilder, map);
        return stringBuilder.append('}').toString();
    }

    static <K, V> void putAllImpl(Map<K, V> map, Map<? extends K, ? extends V> map2) {
        for (Map.Entry<K, V> entry : map2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    @Nullable
    static <K> K keyOrNull(@Nullable Map.Entry<K, ?> entry) {
        return entry == null ? null : (K)entry.getKey();
    }

    @Nullable
    static <V> V valueOrNull(@Nullable Map.Entry<?, V> entry) {
        return entry == null ? null : (V)entry.getValue();
    }

    static <E> ImmutableMap<E, Integer> indexMap(Collection<E> collection) {
        ImmutableMap.Builder<E, Integer> builder = new ImmutableMap.Builder<E, Integer>(collection.size());
        int n = 0;
        for (E e : collection) {
            builder.put(e, n++);
        }
        return builder.build();
    }

    @Beta
    @GwtIncompatible
    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
        if (navigableMap.comparator() != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            return navigableMap.subMap(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED, range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
        }
        if (range.hasLowerBound()) {
            return navigableMap.tailMap(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
        }
        if (range.hasUpperBound()) {
            return navigableMap.headMap(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
        }
        return Preconditions.checkNotNull(navigableMap);
    }

    private static void lambda$toImmutableEnumMap$4(java.util.function.Function function, java.util.function.Function function2, Accumulator accumulator, Object object) {
        Enum enum_ = (Enum)Preconditions.checkNotNull(function.apply(object), "Null key for input %s", object);
        Object r = Preconditions.checkNotNull(function2.apply(object), "Null value for input %s", object);
        accumulator.put(enum_, r);
    }

    private static Accumulator lambda$toImmutableEnumMap$3(BinaryOperator binaryOperator) {
        return new Accumulator(binaryOperator);
    }

    private static void lambda$toImmutableEnumMap$2(java.util.function.Function function, java.util.function.Function function2, Accumulator accumulator, Object object) {
        Enum enum_ = (Enum)Preconditions.checkNotNull(function.apply(object), "Null key for input %s", object);
        Object r = Preconditions.checkNotNull(function2.apply(object), "Null value for input %s", object);
        accumulator.put(enum_, r);
    }

    private static Accumulator lambda$toImmutableEnumMap$1() {
        return new Accumulator(Maps::lambda$null$0);
    }

    private static Object lambda$null$0(Object object, Object object2) {
        throw new IllegalArgumentException("Multiple values for key: " + object + ", " + object2);
    }

    static Map access$100(Map map) {
        return Maps.unmodifiableMap(map);
    }

    static Set access$200(Set set) {
        return Maps.removeOnlySet(set);
    }

    static SortedSet access$300(SortedSet sortedSet) {
        return Maps.removeOnlySortedSet(sortedSet);
    }

    static NavigableSet access$400(NavigableSet navigableSet) {
        return Maps.removeOnlyNavigableSet(navigableSet);
    }

    static Map.Entry access$800(Map.Entry entry) {
        return Maps.unmodifiableOrNull(entry);
    }

    @GwtIncompatible
    static abstract class DescendingMap<K, V>
    extends ForwardingMap<K, V>
    implements NavigableMap<K, V> {
        private transient Comparator<? super K> comparator;
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient NavigableSet<K> navigableKeySet;

        DescendingMap() {
        }

        abstract NavigableMap<K, V> forward();

        @Override
        protected final Map<K, V> delegate() {
            return this.forward();
        }

        @Override
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator = this.comparator;
            if (comparator == null) {
                Comparator comparator2 = this.forward().comparator();
                if (comparator2 == null) {
                    comparator2 = Ordering.natural();
                }
                comparator = this.comparator = DescendingMap.reverse(comparator2);
            }
            return comparator;
        }

        private static <T> Ordering<T> reverse(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override
        public K firstKey() {
            return this.forward().lastKey();
        }

        @Override
        public K lastKey() {
            return this.forward().firstKey();
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K k) {
            return this.forward().higherEntry(k);
        }

        @Override
        public K lowerKey(K k) {
            return this.forward().higherKey(k);
        }

        @Override
        public Map.Entry<K, V> floorEntry(K k) {
            return this.forward().ceilingEntry(k);
        }

        @Override
        public K floorKey(K k) {
            return this.forward().ceilingKey(k);
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K k) {
            return this.forward().floorEntry(k);
        }

        @Override
        public K ceilingKey(K k) {
            return this.forward().floorKey(k);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K k) {
            return this.forward().lowerEntry(k);
        }

        @Override
        public K higherKey(K k) {
            return this.forward().lowerKey(k);
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            return this.forward().lastEntry();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            return this.forward().firstEntry();
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return this.forward().pollLastEntry();
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return this.forward().pollFirstEntry();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return this.forward();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet;
            return set == null ? (this.entrySet = this.createEntrySet()) : set;
        }

        abstract Iterator<Map.Entry<K, V>> entryIterator();

        Set<Map.Entry<K, V>> createEntrySet() {
            class EntrySetImpl
            extends EntrySet<K, V> {
                final DescendingMap this$0;

                EntrySetImpl(DescendingMap descendingMap) {
                    this.this$0 = descendingMap;
                }

                @Override
                Map<K, V> map() {
                    return this.this$0;
                }

                @Override
                public Iterator<Map.Entry<K, V>> iterator() {
                    return this.this$0.entryIterator();
                }
            }
            return new EntrySetImpl(this);
        }

        @Override
        public Set<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet = this.navigableKeySet;
            return navigableSet == null ? (this.navigableKeySet = new NavigableKeySet(this)) : navigableSet;
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.forward().navigableKeySet();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return this.forward().subMap(k2, bl2, k, bl).descendingMap();
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return this.forward().tailMap(k, bl).descendingMap();
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return this.forward().headMap(k, bl).descendingMap();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, true);
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return this.headMap(k, true);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, false);
        }

        @Override
        public Collection<V> values() {
            return new Values(this);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    static abstract class EntrySet<K, V>
    extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        abstract Map<K, V> map();

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                Object k = entry.getKey();
                V v = Maps.safeGet(this.map(), k);
                return Objects.equal(v, entry.getValue()) && (v != null || this.map().containsKey(k));
            }
            return true;
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean remove(Object object) {
            if (this.contains(object)) {
                Map.Entry entry = (Map.Entry)object;
                return this.map().keySet().remove(entry.getKey());
            }
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll(Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unsupportedOperationException) {
                return Sets.removeAllImpl(this, collection.iterator());
            }
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll(Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet hashSet = Sets.newHashSetWithExpectedSize(collection.size());
                for (Object obj : collection) {
                    if (!this.contains(obj)) continue;
                    Map.Entry entry = (Map.Entry)obj;
                    hashSet.add(entry.getKey());
                }
                return this.map().keySet().retainAll(hashSet);
            }
        }
    }

    static class Values<K, V>
    extends AbstractCollection<V> {
        @Weak
        final Map<K, V> map;

        Values(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        final Map<K, V> map() {
            return this.map;
        }

        @Override
        public Iterator<V> iterator() {
            return Maps.valueIterator(this.map().entrySet().iterator());
        }

        @Override
        public void forEach(Consumer<? super V> consumer) {
            Preconditions.checkNotNull(consumer);
            this.map.forEach((arg_0, arg_1) -> Values.lambda$forEach$0(consumer, arg_0, arg_1));
        }

        @Override
        public boolean remove(Object object) {
            try {
                return super.remove(object);
            } catch (UnsupportedOperationException unsupportedOperationException) {
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!Objects.equal(object, entry.getValue())) continue;
                    this.map().remove(entry.getKey());
                    return false;
                }
                return true;
            }
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll(Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet<K> hashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!collection.contains(entry.getValue())) continue;
                    hashSet.add(entry.getKey());
                }
                return this.map().keySet().removeAll(hashSet);
            }
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll(Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet<K> hashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!collection.contains(entry.getValue())) continue;
                    hashSet.add(entry.getKey());
                }
                return this.map().keySet().retainAll(hashSet);
            }
        }

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.map().containsValue(object);
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        private static void lambda$forEach$0(Consumer consumer, Object object, Object object2) {
            consumer.accept(object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtIncompatible
    static class NavigableKeySet<K, V>
    extends SortedKeySet<K, V>
    implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }

        @Override
        NavigableMap<K, V> map() {
            return (NavigableMap)this.map;
        }

        @Override
        public K lower(K k) {
            return this.map().lowerKey(k);
        }

        @Override
        public K floor(K k) {
            return this.map().floorKey(k);
        }

        @Override
        public K ceiling(K k) {
            return this.map().ceilingKey(k);
        }

        @Override
        public K higher(K k) {
            return this.map().higherKey(k);
        }

        @Override
        public K pollFirst() {
            return Maps.keyOrNull(this.map().pollFirstEntry());
        }

        @Override
        public K pollLast() {
            return Maps.keyOrNull(this.map().pollLastEntry());
        }

        @Override
        public NavigableSet<K> descendingSet() {
            return this.map().descendingKeySet();
        }

        @Override
        public Iterator<K> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<K> subSet(K k, boolean bl, K k2, boolean bl2) {
            return this.map().subMap(k, bl, k2, bl2).navigableKeySet();
        }

        @Override
        public NavigableSet<K> headSet(K k, boolean bl) {
            return this.map().headMap(k, bl).navigableKeySet();
        }

        @Override
        public NavigableSet<K> tailSet(K k, boolean bl) {
            return this.map().tailMap(k, bl).navigableKeySet();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return this.subSet(k, true, k2, true);
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return this.headSet(k, true);
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return this.tailSet(k, false);
        }

        @Override
        SortedMap map() {
            return this.map();
        }

        @Override
        Map map() {
            return this.map();
        }
    }

    static class SortedKeySet<K, V>
    extends KeySet<K, V>
    implements SortedSet<K> {
        SortedKeySet(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }

        @Override
        SortedMap<K, V> map() {
            return (SortedMap)super.map();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.map().comparator();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(this.map().subMap(k, k2));
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(this.map().headMap(k));
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(this.map().tailMap(k));
        }

        @Override
        public K first() {
            return this.map().firstKey();
        }

        @Override
        public K last() {
            return this.map().lastKey();
        }

        @Override
        Map map() {
            return this.map();
        }
    }

    static class KeySet<K, V>
    extends Sets.ImprovedAbstractSet<K> {
        @Weak
        final Map<K, V> map;

        KeySet(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        Map<K, V> map() {
            return this.map;
        }

        @Override
        public Iterator<K> iterator() {
            return Maps.keyIterator(this.map().entrySet().iterator());
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            Preconditions.checkNotNull(consumer);
            this.map.forEach((arg_0, arg_1) -> KeySet.lambda$forEach$0(consumer, arg_0, arg_1));
        }

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean contains(Object object) {
            return this.map().containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            if (this.contains(object)) {
                this.map().remove(object);
                return false;
            }
            return true;
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        private static void lambda$forEach$0(Consumer consumer, Object object, Object object2) {
            consumer.accept(object);
        }
    }

    static abstract class IteratorBasedAbstractMap<K, V>
    extends AbstractMap<K, V> {
        IteratorBasedAbstractMap() {
        }

        @Override
        public abstract int size();

        abstract Iterator<Map.Entry<K, V>> entryIterator();

        Spliterator<Map.Entry<K, V>> entrySpliterator() {
            return Spliterators.spliterator(this.entryIterator(), (long)this.size(), 65);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>(this){
                final IteratorBasedAbstractMap this$0;
                {
                    this.this$0 = iteratorBasedAbstractMap;
                }

                @Override
                Map<K, V> map() {
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

                @Override
                public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
                    this.this$0.forEachEntry(consumer);
                }
            };
        }

        void forEachEntry(Consumer<? super Map.Entry<K, V>> consumer) {
            this.entryIterator().forEachRemaining(consumer);
        }

        @Override
        public void clear() {
            Iterators.clear(this.entryIterator());
        }
    }

    @GwtCompatible
    static abstract class ViewCachingAbstractMap<K, V>
    extends AbstractMap<K, V> {
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient Set<K> keySet;
        private transient Collection<V> values;

        ViewCachingAbstractMap() {
        }

        abstract Set<Map.Entry<K, V>> createEntrySet();

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet;
            return set == null ? (this.entrySet = this.createEntrySet()) : set;
        }

        @Override
        public Set<K> keySet() {
            Set<K> set = this.keySet;
            return set == null ? (this.keySet = this.createKeySet()) : set;
        }

        Set<K> createKeySet() {
            return new KeySet(this);
        }

        @Override
        public Collection<V> values() {
            Collection<V> collection = this.values;
            return collection == null ? (this.values = this.createValues()) : collection;
        }

        Collection<V> createValues() {
            return new Values(this);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtIncompatible
    static class UnmodifiableNavigableMap<K, V>
    extends ForwardingSortedMap<K, V>
    implements NavigableMap<K, V>,
    Serializable {
        private final NavigableMap<K, ? extends V> delegate;
        private transient UnmodifiableNavigableMap<K, V> descendingMap;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
            this.delegate = navigableMap;
        }

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap, UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap) {
            this.delegate = navigableMap;
            this.descendingMap = unmodifiableNavigableMap;
        }

        @Override
        protected SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.delegate);
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K k) {
            return Maps.access$800(this.delegate.lowerEntry(k));
        }

        @Override
        public K lowerKey(K k) {
            return this.delegate.lowerKey(k);
        }

        @Override
        public Map.Entry<K, V> floorEntry(K k) {
            return Maps.access$800(this.delegate.floorEntry(k));
        }

        @Override
        public K floorKey(K k) {
            return this.delegate.floorKey(k);
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K k) {
            return Maps.access$800(this.delegate.ceilingEntry(k));
        }

        @Override
        public K ceilingKey(K k) {
            return this.delegate.ceilingKey(k);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K k) {
            return Maps.access$800(this.delegate.higherEntry(k));
        }

        @Override
        public K higherKey(K k) {
            return this.delegate.higherKey(k);
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            return Maps.access$800(this.delegate.firstEntry());
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            return Maps.access$800(this.delegate.lastEntry());
        }

        @Override
        public final Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap = this.descendingMap;
            return unmodifiableNavigableMap == null ? (this.descendingMap = new UnmodifiableNavigableMap<K, V>(this.delegate.descendingMap(), this)) : unmodifiableNavigableMap;
        }

        @Override
        public Set<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, true);
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return this.headMap(k, true);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, false);
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(k, bl));
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(k, bl));
        }

        @Override
        protected Map delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    static final class FilteredEntryBiMap<K, V>
    extends FilteredEntryMap<K, V>
    implements BiMap<K, V> {
        @RetainedWith
        private final BiMap<V, K> inverse;

        private static <K, V> Predicate<Map.Entry<V, K>> inversePredicate(Predicate<? super Map.Entry<K, V>> predicate) {
            return new Predicate<Map.Entry<V, K>>(predicate){
                final Predicate val$forwardPredicate;
                {
                    this.val$forwardPredicate = predicate;
                }

                @Override
                public boolean apply(Map.Entry<V, K> entry) {
                    return this.val$forwardPredicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }

                @Override
                public boolean apply(Object object) {
                    return this.apply((Map.Entry)object);
                }
            };
        }

        FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.inverse = new FilteredEntryBiMap<K, V>(biMap.inverse(), FilteredEntryBiMap.inversePredicate(predicate), this);
        }

        private FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.inverse = biMap2;
        }

        BiMap<K, V> unfiltered() {
            return (BiMap)this.unfiltered;
        }

        @Override
        public V forcePut(@Nullable K k, @Nullable V v) {
            Preconditions.checkArgument(this.apply(k, v));
            return this.unfiltered().forcePut(k, v);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            this.unfiltered().replaceAll((arg_0, arg_1) -> this.lambda$replaceAll$0(biFunction, arg_0, arg_1));
        }

        @Override
        public BiMap<V, K> inverse() {
            return this.inverse;
        }

        @Override
        public Set<V> values() {
            return this.inverse.keySet();
        }

        @Override
        public Collection values() {
            return this.values();
        }

        private Object lambda$replaceAll$0(BiFunction biFunction, Object object, Object object2) {
            return this.predicate.apply(Maps.immutableEntry(object, object2)) ? biFunction.apply(object, object2) : object2;
        }
    }

    @GwtIncompatible
    private static class FilteredEntryNavigableMap<K, V>
    extends AbstractNavigableMap<K, V> {
        private final NavigableMap<K, V> unfiltered;
        private final Predicate<? super Map.Entry<K, V>> entryPredicate;
        private final Map<K, V> filteredDelegate;

        FilteredEntryNavigableMap(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = Preconditions.checkNotNull(navigableMap);
            this.entryPredicate = predicate;
            this.filteredDelegate = new FilteredEntryMap<K, V>(navigableMap, predicate);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.unfiltered.comparator();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this, this){
                final FilteredEntryNavigableMap this$0;
                {
                    this.this$0 = filteredEntryNavigableMap;
                    super(navigableMap);
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.access$700(this.this$0).entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.access$600(this.this$0), Maps.keyPredicateOnEntries(Predicates.in(collection))));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.access$700(this.this$0).entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.access$600(this.this$0), Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection)))));
                }
            };
        }

        @Override
        public Collection<V> values() {
            return new FilteredMapValues<K, V>(this, this.unfiltered, this.entryPredicate);
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
        }

        @Override
        Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
        }

        @Override
        public int size() {
            return this.filteredDelegate.size();
        }

        @Override
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @Override
        @Nullable
        public V get(@Nullable Object object) {
            return this.filteredDelegate.get(object);
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.filteredDelegate.containsKey(object);
        }

        @Override
        public V put(K k, V v) {
            return this.filteredDelegate.put(k, v);
        }

        @Override
        public V remove(@Nullable Object object) {
            return this.filteredDelegate.remove(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            this.filteredDelegate.putAll(map);
        }

        @Override
        public void clear() {
            this.filteredDelegate.clear();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return this.filteredDelegate.entrySet();
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.filterEntries(this.unfiltered.subMap(k, bl, k2, bl2), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.filterEntries(this.unfiltered.headMap(k, bl), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.filterEntries(this.unfiltered.tailMap(k, bl), this.entryPredicate);
        }

        static Predicate access$600(FilteredEntryNavigableMap filteredEntryNavigableMap) {
            return filteredEntryNavigableMap.entryPredicate;
        }

        static NavigableMap access$700(FilteredEntryNavigableMap filteredEntryNavigableMap) {
            return filteredEntryNavigableMap.unfiltered;
        }
    }

    private static class FilteredEntrySortedMap<K, V>
    extends FilteredEntryMap<K, V>
    implements SortedMap<K, V> {
        FilteredEntrySortedMap(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        SortedMap<K, V> sortedMap() {
            return (SortedMap)this.unfiltered;
        }

        @Override
        public SortedSet<K> keySet() {
            return (SortedSet)super.keySet();
        }

        @Override
        SortedSet<K> createKeySet() {
            return new SortedKeySet(this);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        public K firstKey() {
            return (K)this.keySet().iterator().next();
        }

        @Override
        public K lastKey() {
            SortedMap<K, V> sortedMap = this.sortedMap();
            K k;
            while (!this.apply(k = sortedMap.lastKey(), this.unfiltered.get(k))) {
                sortedMap = this.sortedMap().headMap(k);
            }
            return k;
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().headMap(k), this.predicate);
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().subMap(k, k2), this.predicate);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().tailMap(k), this.predicate);
        }

        @Override
        Set createKeySet() {
            return this.createKeySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        class SortedKeySet
        extends FilteredEntryMap.KeySet
        implements SortedSet<K> {
            final FilteredEntrySortedMap this$0;

            SortedKeySet(FilteredEntrySortedMap filteredEntrySortedMap) {
                this.this$0 = filteredEntrySortedMap;
                super(filteredEntrySortedMap);
            }

            @Override
            public Comparator<? super K> comparator() {
                return this.this$0.sortedMap().comparator();
            }

            @Override
            public SortedSet<K> subSet(K k, K k2) {
                return (SortedSet)this.this$0.subMap(k, k2).keySet();
            }

            @Override
            public SortedSet<K> headSet(K k) {
                return (SortedSet)this.this$0.headMap(k).keySet();
            }

            @Override
            public SortedSet<K> tailSet(K k) {
                return (SortedSet)this.this$0.tailMap(k).keySet();
            }

            @Override
            public K first() {
                return this.this$0.firstKey();
            }

            @Override
            public K last() {
                return this.this$0.lastKey();
            }
        }
    }

    static class FilteredEntryMap<K, V>
    extends AbstractFilteredMap<K, V> {
        final Set<Map.Entry<K, V>> filteredEntrySet;

        FilteredEntryMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map, predicate);
            this.filteredEntrySet = Sets.filter(map.entrySet(), this.predicate);
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet(this, null);
        }

        @Override
        Set<K> createKeySet() {
            return new KeySet(this);
        }

        class KeySet
        extends com.google.common.collect.Maps$KeySet<K, V> {
            final FilteredEntryMap this$0;

            KeySet(FilteredEntryMap filteredEntryMap) {
                this.this$0 = filteredEntryMap;
                super(filteredEntryMap);
            }

            @Override
            public boolean remove(Object object) {
                if (this.this$0.containsKey(object)) {
                    this.this$0.unfiltered.remove(object);
                    return false;
                }
                return true;
            }

            @Override
            private boolean removeIf(Predicate<? super K> predicate) {
                return Iterables.removeIf(this.this$0.unfiltered.entrySet(), Predicates.and(this.this$0.predicate, Maps.keyPredicateOnEntries(predicate)));
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return this.removeIf(Predicates.in(collection));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return this.removeIf(Predicates.not(Predicates.in(collection)));
            }

            @Override
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }

            @Override
            public <T> T[] toArray(T[] TArray) {
                return Lists.newArrayList(this.iterator()).toArray(TArray);
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class EntrySet
        extends ForwardingSet<Map.Entry<K, V>> {
            final FilteredEntryMap this$0;

            private EntrySet(FilteredEntryMap filteredEntryMap) {
                this.this$0 = filteredEntryMap;
            }

            @Override
            protected Set<Map.Entry<K, V>> delegate() {
                return this.this$0.filteredEntrySet;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(this, this.this$0.filteredEntrySet.iterator()){
                    final EntrySet this$1;
                    {
                        this.this$1 = entrySet;
                        super(iterator2);
                    }

                    @Override
                    Map.Entry<K, V> transform(Map.Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>(this, entry){
                            final Map.Entry val$entry;
                            final 1 this$2;
                            {
                                this.this$2 = var1_1;
                                this.val$entry = entry;
                            }

                            @Override
                            protected Map.Entry<K, V> delegate() {
                                return this.val$entry;
                            }

                            @Override
                            public V setValue(V v) {
                                Preconditions.checkArgument(this.this$2.this$1.this$0.apply(this.getKey(), v));
                                return super.setValue(v);
                            }

                            @Override
                            protected Object delegate() {
                                return this.delegate();
                            }
                        };
                    }

                    @Override
                    Object transform(Object object) {
                        return this.transform((Map.Entry)object);
                    }
                };
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }

            EntrySet(FilteredEntryMap filteredEntryMap, com.google.common.collect.Maps$1 var2_2) {
                this(filteredEntryMap);
            }
        }
    }

    private static class FilteredKeyMap<K, V>
    extends AbstractFilteredMap<K, V> {
        final Predicate<? super K> keyPredicate;

        FilteredKeyMap(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.keyPredicate = predicate;
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), this.predicate);
        }

        @Override
        Set<K> createKeySet() {
            return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
        }

        @Override
        public boolean containsKey(Object object) {
            return this.unfiltered.containsKey(object) && this.keyPredicate.apply(object);
        }
    }

    private static final class FilteredMapValues<K, V>
    extends Values<K, V> {
        final Map<K, V> unfiltered;
        final Predicate<? super Map.Entry<K, V>> predicate;

        FilteredMapValues(Map<K, V> map, Map<K, V> map2, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map);
            this.unfiltered = map2;
            this.predicate = predicate;
        }

        @Override
        public boolean remove(Object object) {
            return Iterables.removeFirstMatching(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(Predicates.equalTo(object)))) != null;
        }

        @Override
        private boolean removeIf(Predicate<? super V> predicate) {
            return Iterables.removeIf(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(predicate)));
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.removeIf(Predicates.in(collection));
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.removeIf(Predicates.not(Predicates.in(collection)));
        }

        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return Lists.newArrayList(this.iterator()).toArray(TArray);
        }
    }

    private static abstract class AbstractFilteredMap<K, V>
    extends ViewCachingAbstractMap<K, V> {
        final Map<K, V> unfiltered;
        final Predicate<? super Map.Entry<K, V>> predicate;

        AbstractFilteredMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = map;
            this.predicate = predicate;
        }

        boolean apply(@Nullable Object object, @Nullable V v) {
            Object object2 = object;
            return this.predicate.apply(Maps.immutableEntry(object2, v));
        }

        @Override
        public V put(K k, V v) {
            Preconditions.checkArgument(this.apply(k, v));
            return this.unfiltered.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }

        @Override
        public boolean containsKey(Object object) {
            return this.unfiltered.containsKey(object) && this.apply(object, this.unfiltered.get(object));
        }

        @Override
        public V get(Object object) {
            V v = this.unfiltered.get(object);
            return v != null && this.apply(object, v) ? (V)v : null;
        }

        @Override
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }

        @Override
        public V remove(Object object) {
            return this.containsKey(object) ? (V)this.unfiltered.remove(object) : null;
        }

        @Override
        Collection<V> createValues() {
            return new FilteredMapValues<K, V>(this, this.unfiltered, this.predicate);
        }
    }

    @GwtIncompatible
    private static class TransformedEntriesNavigableMap<K, V1, V2>
    extends TransformedEntriesSortedMap<K, V1, V2>
    implements NavigableMap<K, V2> {
        TransformedEntriesNavigableMap(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(navigableMap, entryTransformer);
        }

        @Override
        public Map.Entry<K, V2> ceilingEntry(K k) {
            return this.transformEntry(this.fromMap().ceilingEntry(k));
        }

        @Override
        public K ceilingKey(K k) {
            return this.fromMap().ceilingKey(k);
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.fromMap().descendingKeySet();
        }

        @Override
        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries(this.fromMap().descendingMap(), this.transformer);
        }

        @Override
        public Map.Entry<K, V2> firstEntry() {
            return this.transformEntry(this.fromMap().firstEntry());
        }

        @Override
        public Map.Entry<K, V2> floorEntry(K k) {
            return this.transformEntry(this.fromMap().floorEntry(k));
        }

        @Override
        public K floorKey(K k) {
            return this.fromMap().floorKey(k);
        }

        @Override
        public NavigableMap<K, V2> headMap(K k) {
            return this.headMap(k, true);
        }

        @Override
        public NavigableMap<K, V2> headMap(K k, boolean bl) {
            return Maps.transformEntries(this.fromMap().headMap(k, bl), this.transformer);
        }

        @Override
        public Map.Entry<K, V2> higherEntry(K k) {
            return this.transformEntry(this.fromMap().higherEntry(k));
        }

        @Override
        public K higherKey(K k) {
            return this.fromMap().higherKey(k);
        }

        @Override
        public Map.Entry<K, V2> lastEntry() {
            return this.transformEntry(this.fromMap().lastEntry());
        }

        @Override
        public Map.Entry<K, V2> lowerEntry(K k) {
            return this.transformEntry(this.fromMap().lowerEntry(k));
        }

        @Override
        public K lowerKey(K k) {
            return this.fromMap().lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return this.fromMap().navigableKeySet();
        }

        @Override
        public Map.Entry<K, V2> pollFirstEntry() {
            return this.transformEntry(this.fromMap().pollFirstEntry());
        }

        @Override
        public Map.Entry<K, V2> pollLastEntry() {
            return this.transformEntry(this.fromMap().pollLastEntry());
        }

        @Override
        public NavigableMap<K, V2> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.transformEntries(this.fromMap().subMap(k, bl, k2, bl2), this.transformer);
        }

        @Override
        public NavigableMap<K, V2> subMap(K k, K k2) {
            return this.subMap(k, true, k2, true);
        }

        @Override
        public NavigableMap<K, V2> tailMap(K k) {
            return this.tailMap(k, false);
        }

        @Override
        public NavigableMap<K, V2> tailMap(K k, boolean bl) {
            return Maps.transformEntries(this.fromMap().tailMap(k, bl), this.transformer);
        }

        @Nullable
        private Map.Entry<K, V2> transformEntry(@Nullable Map.Entry<K, V1> entry) {
            return entry == null ? null : Maps.transformEntry(this.transformer, entry);
        }

        @Override
        protected NavigableMap<K, V1> fromMap() {
            return (NavigableMap)super.fromMap();
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

        @Override
        protected SortedMap fromMap() {
            return this.fromMap();
        }
    }

    static class TransformedEntriesSortedMap<K, V1, V2>
    extends TransformedEntriesMap<K, V1, V2>
    implements SortedMap<K, V2> {
        protected SortedMap<K, V1> fromMap() {
            return (SortedMap)this.fromMap;
        }

        TransformedEntriesSortedMap(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(sortedMap, entryTransformer);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.fromMap().comparator();
        }

        @Override
        public K firstKey() {
            return this.fromMap().firstKey();
        }

        @Override
        public SortedMap<K, V2> headMap(K k) {
            return Maps.transformEntries(this.fromMap().headMap(k), this.transformer);
        }

        @Override
        public K lastKey() {
            return this.fromMap().lastKey();
        }

        @Override
        public SortedMap<K, V2> subMap(K k, K k2) {
            return Maps.transformEntries(this.fromMap().subMap(k, k2), this.transformer);
        }

        @Override
        public SortedMap<K, V2> tailMap(K k) {
            return Maps.transformEntries(this.fromMap().tailMap(k), this.transformer);
        }
    }

    static class TransformedEntriesMap<K, V1, V2>
    extends IteratorBasedAbstractMap<K, V2> {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;

        TransformedEntriesMap(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.fromMap = Preconditions.checkNotNull(map);
            this.transformer = Preconditions.checkNotNull(entryTransformer);
        }

        @Override
        public int size() {
            return this.fromMap.size();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.fromMap.containsKey(object);
        }

        @Override
        @Nullable
        public V2 get(@Nullable Object object) {
            return this.getOrDefault(object, (V2)null);
        }

        @Override
        @Nullable
        public V2 getOrDefault(@Nullable Object object, @Nullable V2 V2) {
            V1 V1 = this.fromMap.get(object);
            return V1 != null || this.fromMap.containsKey(object) ? this.transformer.transformEntry(object, V1) : V2;
        }

        @Override
        public V2 remove(Object object) {
            return this.fromMap.containsKey(object) ? (V2)this.transformer.transformEntry((K)object, (V1)this.fromMap.remove(object)) : null;
        }

        @Override
        public void clear() {
            this.fromMap.clear();
        }

        @Override
        public Set<K> keySet() {
            return this.fromMap.keySet();
        }

        @Override
        Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        @Override
        Spliterator<Map.Entry<K, V2>> entrySpliterator() {
            return CollectSpliterators.map(this.fromMap.entrySet().spliterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V2> biConsumer) {
            Preconditions.checkNotNull(biConsumer);
            this.fromMap.forEach((arg_0, arg_1) -> this.lambda$forEach$0(biConsumer, arg_0, arg_1));
        }

        @Override
        public Collection<V2> values() {
            return new Values(this);
        }

        private void lambda$forEach$0(BiConsumer biConsumer, Object object, Object object2) {
            biConsumer.accept(object, this.transformer.transformEntry(object, object2));
        }
    }

    @FunctionalInterface
    public static interface EntryTransformer<K, V1, V2> {
        public V2 transformEntry(@Nullable K var1, @Nullable V1 var2);
    }

    private static class UnmodifiableBiMap<K, V>
    extends ForwardingMap<K, V>
    implements BiMap<K, V>,
    Serializable {
        final Map<K, V> unmodifiableMap;
        final BiMap<? extends K, ? extends V> delegate;
        @RetainedWith
        BiMap<V, K> inverse;
        transient Set<V> values;
        private static final long serialVersionUID = 0L;

        UnmodifiableBiMap(BiMap<? extends K, ? extends V> biMap, @Nullable BiMap<V, K> biMap2) {
            this.unmodifiableMap = Collections.unmodifiableMap(biMap);
            this.delegate = biMap;
            this.inverse = biMap2;
        }

        @Override
        protected Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        @Override
        public V forcePut(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BiMap<V, K> inverse() {
            BiMap<K, V> biMap = this.inverse;
            return biMap == null ? (this.inverse = new UnmodifiableBiMap<V, K>(this.delegate.inverse(), this)) : biMap;
        }

        @Override
        public Set<V> values() {
            Set<V> set = this.values;
            return set == null ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : set;
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    private static final class BiMapConverter<A, B>
    extends Converter<A, B>
    implements Serializable {
        private final BiMap<A, B> bimap;
        private static final long serialVersionUID = 0L;

        BiMapConverter(BiMap<A, B> biMap) {
            this.bimap = Preconditions.checkNotNull(biMap);
        }

        @Override
        protected B doForward(A a) {
            return BiMapConverter.convert(this.bimap, a);
        }

        @Override
        protected A doBackward(B b) {
            return BiMapConverter.convert(this.bimap.inverse(), b);
        }

        private static <X, Y> Y convert(BiMap<X, Y> biMap, X x) {
            Object v = biMap.get(x);
            Preconditions.checkArgument(v != null, "No non-null mapping present for input: %s", x);
            return (Y)v;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof BiMapConverter) {
                BiMapConverter biMapConverter = (BiMapConverter)object;
                return this.bimap.equals(biMapConverter.bimap);
            }
            return true;
        }

        public int hashCode() {
            return this.bimap.hashCode();
        }

        public String toString() {
            return "Maps.asConverter(" + this.bimap + ")";
        }
    }

    static class UnmodifiableEntrySet<K, V>
    extends UnmodifiableEntries<K, V>
    implements Set<Map.Entry<K, V>> {
        UnmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
            super(set);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    static class UnmodifiableEntries<K, V>
    extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> entries;

        UnmodifiableEntries(Collection<Map.Entry<K, V>> collection) {
            this.entries = collection;
        }

        @Override
        protected Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return Maps.unmodifiableEntryIterator(this.entries.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.standardToArray(TArray);
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    @GwtIncompatible
    private static final class NavigableAsMapView<K, V>
    extends AbstractNavigableMap<K, V> {
        private final NavigableSet<K> set;
        private final Function<? super K, V> function;

        NavigableAsMapView(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.set = Preconditions.checkNotNull(navigableSet);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.asMap(this.set.subSet(k, bl, k2, bl2), this.function);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.asMap(this.set.headSet(k, bl), this.function);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.asMap(this.set.tailSet(k, bl), this.function);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.set.comparator();
        }

        @Override
        @Nullable
        public V get(@Nullable Object object) {
            return this.getOrDefault(object, null);
        }

        @Override
        @Nullable
        public V getOrDefault(@Nullable Object object, @Nullable V v) {
            if (Collections2.safeContains(this.set, object)) {
                Object object2 = object;
                return this.function.apply((K)object2);
            }
            return v;
        }

        @Override
        public void clear() {
            this.set.clear();
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return Maps.asMapEntryIterator(this.set, this.function);
        }

        @Override
        Spliterator<Map.Entry<K, V>> entrySpliterator() {
            return CollectSpliterators.map(this.set.spliterator(), this::lambda$entrySpliterator$0);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            this.set.forEach(arg_0 -> this.lambda$forEach$1(biConsumer, arg_0));
        }

        @Override
        Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return this.descendingMap().entrySet().iterator();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Maps.access$400(this.set);
        }

        @Override
        public int size() {
            return this.set.size();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap(this.set.descendingSet(), this.function);
        }

        private void lambda$forEach$1(BiConsumer biConsumer, Object object) {
            biConsumer.accept(object, this.function.apply((K)object));
        }

        private Map.Entry lambda$entrySpliterator$0(Object object) {
            return Maps.immutableEntry(object, this.function.apply((K)object));
        }
    }

    private static class SortedAsMapView<K, V>
    extends AsMapView<K, V>
    implements SortedMap<K, V> {
        SortedAsMapView(SortedSet<K> sortedSet, Function<? super K, V> function) {
            super(sortedSet, function);
        }

        @Override
        SortedSet<K> backingSet() {
            return (SortedSet)super.backingSet();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.backingSet().comparator();
        }

        @Override
        public Set<K> keySet() {
            return Maps.access$300((SortedSet)this.backingSet());
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return Maps.asMap(this.backingSet().subSet(k, k2), this.function);
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return Maps.asMap(this.backingSet().headSet(k), this.function);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return Maps.asMap(this.backingSet().tailSet(k), this.function);
        }

        @Override
        public K firstKey() {
            return (K)this.backingSet().first();
        }

        @Override
        public K lastKey() {
            return (K)this.backingSet().last();
        }

        @Override
        Set backingSet() {
            return this.backingSet();
        }
    }

    private static class AsMapView<K, V>
    extends ViewCachingAbstractMap<K, V> {
        private final Set<K> set;
        final Function<? super K, V> function;

        Set<K> backingSet() {
            return this.set;
        }

        AsMapView(Set<K> set, Function<? super K, V> function) {
            this.set = Preconditions.checkNotNull(set);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public Set<K> createKeySet() {
            return Maps.access$200(this.backingSet());
        }

        @Override
        Collection<V> createValues() {
            return Collections2.transform(this.set, this.function);
        }

        @Override
        public int size() {
            return this.backingSet().size();
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.backingSet().contains(object);
        }

        @Override
        public V get(@Nullable Object object) {
            return this.getOrDefault(object, null);
        }

        @Override
        public V getOrDefault(@Nullable Object object, @Nullable V v) {
            if (Collections2.safeContains(this.backingSet(), object)) {
                Object object2 = object;
                return this.function.apply((K)object2);
            }
            return v;
        }

        @Override
        public V remove(@Nullable Object object) {
            if (this.backingSet().remove(object)) {
                Object object2 = object;
                return this.function.apply((K)object2);
            }
            return null;
        }

        @Override
        public void clear() {
            this.backingSet().clear();
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            class EntrySetImpl
            extends EntrySet<K, V> {
                final AsMapView this$0;

                EntrySetImpl(AsMapView asMapView) {
                    this.this$0 = asMapView;
                }

                @Override
                Map<K, V> map() {
                    return this.this$0;
                }

                @Override
                public Iterator<Map.Entry<K, V>> iterator() {
                    return Maps.asMapEntryIterator(this.this$0.backingSet(), this.this$0.function);
                }
            }
            return new EntrySetImpl(this);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            Preconditions.checkNotNull(biConsumer);
            this.backingSet().forEach(arg_0 -> this.lambda$forEach$0(biConsumer, arg_0));
        }

        private void lambda$forEach$0(BiConsumer biConsumer, Object object) {
            biConsumer.accept(object, this.function.apply((K)object));
        }
    }

    static class SortedMapDifferenceImpl<K, V>
    extends MapDifferenceImpl<K, V>
    implements SortedMapDifference<K, V> {
        SortedMapDifferenceImpl(SortedMap<K, V> sortedMap, SortedMap<K, V> sortedMap2, SortedMap<K, V> sortedMap3, SortedMap<K, MapDifference.ValueDifference<V>> sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }

        @Override
        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap)super.entriesDiffering();
        }

        @Override
        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap)super.entriesInCommon();
        }

        @Override
        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap)super.entriesOnlyOnLeft();
        }

        @Override
        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap)super.entriesOnlyOnRight();
        }

        @Override
        public Map entriesDiffering() {
            return this.entriesDiffering();
        }

        @Override
        public Map entriesInCommon() {
            return this.entriesInCommon();
        }

        @Override
        public Map entriesOnlyOnRight() {
            return this.entriesOnlyOnRight();
        }

        @Override
        public Map entriesOnlyOnLeft() {
            return this.entriesOnlyOnLeft();
        }
    }

    static class ValueDifferenceImpl<V>
    implements MapDifference.ValueDifference<V> {
        private final V left;
        private final V right;

        static <V> MapDifference.ValueDifference<V> create(@Nullable V v, @Nullable V v2) {
            return new ValueDifferenceImpl<V>(v, v2);
        }

        private ValueDifferenceImpl(@Nullable V v, @Nullable V v2) {
            this.left = v;
            this.right = v2;
        }

        @Override
        public V leftValue() {
            return this.left;
        }

        @Override
        public V rightValue() {
            return this.right;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof MapDifference.ValueDifference) {
                MapDifference.ValueDifference valueDifference = (MapDifference.ValueDifference)object;
                return Objects.equal(this.left, valueDifference.leftValue()) && Objects.equal(this.right, valueDifference.rightValue());
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }

        public String toString() {
            return "(" + this.left + ", " + this.right + ")";
        }
    }

    static class MapDifferenceImpl<K, V>
    implements MapDifference<K, V> {
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;
        final Map<K, V> onBoth;
        final Map<K, MapDifference.ValueDifference<V>> differences;

        MapDifferenceImpl(Map<K, V> map, Map<K, V> map2, Map<K, V> map3, Map<K, MapDifference.ValueDifference<V>> map4) {
            this.onlyOnLeft = Maps.access$100(map);
            this.onlyOnRight = Maps.access$100(map2);
            this.onBoth = Maps.access$100(map3);
            this.differences = Maps.access$100(map4);
        }

        @Override
        public boolean areEqual() {
            return this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty();
        }

        @Override
        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }

        @Override
        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }

        @Override
        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }

        @Override
        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof MapDifference) {
                MapDifference mapDifference = (MapDifference)object;
                return this.entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) && this.entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) && this.entriesInCommon().equals(mapDifference.entriesInCommon()) && this.entriesDiffering().equals(mapDifference.entriesDiffering());
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
        }

        public String toString() {
            if (this.areEqual()) {
                return "equal";
            }
            StringBuilder stringBuilder = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                stringBuilder.append(": only on left=").append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                stringBuilder.append(": only on right=").append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                stringBuilder.append(": value differences=").append(this.differences);
            }
            return stringBuilder.toString();
        }
    }

    private static class Accumulator<K extends Enum<K>, V> {
        private final BinaryOperator<V> mergeFunction;
        private EnumMap<K, V> map = null;

        Accumulator(BinaryOperator<V> binaryOperator) {
            this.mergeFunction = binaryOperator;
        }

        void put(K k, V v) {
            if (this.map == null) {
                this.map = new EnumMap(((Enum)k).getDeclaringClass());
            }
            this.map.merge(k, v, this.mergeFunction);
        }

        Accumulator<K, V> combine(Accumulator<K, V> accumulator) {
            if (this.map == null) {
                return accumulator;
            }
            if (accumulator.map == null) {
                return this;
            }
            accumulator.map.forEach(this::put);
            return this;
        }

        ImmutableMap<K, V> toImmutableMap() {
            return this.map == null ? ImmutableMap.of() : ImmutableEnumMap.asImmutable(this.map);
        }
    }

    private static enum EntryFunction implements Function<Map.Entry<?, ?>, Object>
    {
        KEY{

            @Override
            @Nullable
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }

            @Override
            @Nullable
            public Object apply(Object object) {
                return this.apply((Map.Entry)object);
            }
        }
        ,
        VALUE{

            @Override
            @Nullable
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }

            @Override
            @Nullable
            public Object apply(Object object) {
                return this.apply((Map.Entry)object);
            }
        };


        private EntryFunction() {
        }

        EntryFunction(1 var3_3) {
            this();
        }
    }
}

