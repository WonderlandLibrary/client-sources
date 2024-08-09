/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collector;

@GwtCompatible
final class CollectCollectors {
    CollectCollectors() {
    }

    static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(ImmutableBiMap.Builder::new, (arg_0, arg_1) -> CollectCollectors.lambda$toImmutableBiMap$0(function, function2, arg_0, arg_1), ImmutableBiMap.Builder::combine, ImmutableBiMap.Builder::build, new Collector.Characteristics[0]);
    }

    static <E> Collector<E, ?, ImmutableList<E>> toImmutableList() {
        return Collector.of(ImmutableList::builder, ImmutableList.Builder::add, ImmutableList.Builder::combine, ImmutableList.Builder::build, new Collector.Characteristics[0]);
    }

    static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(ImmutableMap.Builder::new, (arg_0, arg_1) -> CollectCollectors.lambda$toImmutableMap$1(function, function2, arg_0, arg_1), ImmutableMap.Builder::combine, ImmutableMap.Builder::build, new Collector.Characteristics[0]);
    }

    static <E> Collector<E, ?, ImmutableSet<E>> toImmutableSet() {
        return Collector.of(ImmutableSet::builder, ImmutableSet.Builder::add, ImmutableSet.Builder::combine, ImmutableSet.Builder::build, new Collector.Characteristics[0]);
    }

    static <T, K, V> Collector<T, ?, ImmutableSortedMap<K, V>> toImmutableSortedMap(Comparator<? super K> comparator, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(() -> CollectCollectors.lambda$toImmutableSortedMap$2(comparator), (arg_0, arg_1) -> CollectCollectors.lambda$toImmutableSortedMap$3(function, function2, arg_0, arg_1), ImmutableSortedMap.Builder::combine, ImmutableSortedMap.Builder::build, Collector.Characteristics.UNORDERED);
    }

    static <E> Collector<E, ?, ImmutableSortedSet<E>> toImmutableSortedSet(Comparator<? super E> comparator) {
        Preconditions.checkNotNull(comparator);
        return Collector.of(() -> CollectCollectors.lambda$toImmutableSortedSet$4(comparator), ImmutableSortedSet.Builder::add, ImmutableSortedSet.Builder::combine, ImmutableSortedSet.Builder::build, new Collector.Characteristics[0]);
    }

    private static ImmutableSortedSet.Builder lambda$toImmutableSortedSet$4(Comparator comparator) {
        return new ImmutableSortedSet.Builder(comparator);
    }

    private static void lambda$toImmutableSortedMap$3(Function function, Function function2, ImmutableSortedMap.Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object));
    }

    private static ImmutableSortedMap.Builder lambda$toImmutableSortedMap$2(Comparator comparator) {
        return new ImmutableSortedMap.Builder(comparator);
    }

    private static void lambda$toImmutableMap$1(Function function, Function function2, ImmutableMap.Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object));
    }

    private static void lambda$toImmutableBiMap$0(Function function, Function function2, ImmutableBiMap.Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object));
    }
}

