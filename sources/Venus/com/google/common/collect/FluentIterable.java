/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Streams;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Stream;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public abstract class FluentIterable<E>
implements Iterable<E> {
    private final Optional<Iterable<E>> iterableDelegate;

    protected FluentIterable() {
        this.iterableDelegate = Optional.absent();
    }

    FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        this.iterableDelegate = Optional.fromNullable(this != iterable ? iterable : null);
    }

    private Iterable<E> getDelegate() {
        return this.iterableDelegate.or(this);
    }

    public static <E> FluentIterable<E> from(Iterable<E> iterable) {
        return iterable instanceof FluentIterable ? (FluentIterable<E>)iterable : new FluentIterable<E>(iterable, iterable){
            final Iterable val$iterable;
            {
                this.val$iterable = iterable2;
                super(iterable);
            }

            @Override
            public Iterator<E> iterator() {
                return this.val$iterable.iterator();
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> from(E[] EArray) {
        return FluentIterable.from(Arrays.asList(EArray));
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> fluentIterable) {
        return Preconditions.checkNotNull(fluentIterable);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return FluentIterable.concat(ImmutableList.of(iterable, iterable2));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return FluentIterable.concat(ImmutableList.of(iterable, iterable2, iterable3));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return FluentIterable.concat(ImmutableList.of(iterable, iterable2, iterable3, iterable4));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> ... iterableArray) {
        return FluentIterable.concat(ImmutableList.copyOf(iterableArray));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(iterable){
            final Iterable val$inputs;
            {
                this.val$inputs = iterable;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.concat(Iterables.transform(this.val$inputs, Iterables.toIterator()).iterator());
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> of() {
        return FluentIterable.from(ImmutableList.of());
    }

    @Deprecated
    @Beta
    public static <E> FluentIterable<E> of(E[] EArray) {
        return FluentIterable.from(Lists.newArrayList(EArray));
    }

    @Beta
    public static <E> FluentIterable<E> of(@Nullable E e, E ... EArray) {
        return FluentIterable.from(Lists.asList(e, EArray));
    }

    public String toString() {
        return Iterables.toString(this.getDelegate());
    }

    public final int size() {
        return Iterables.size(this.getDelegate());
    }

    public final boolean contains(@Nullable Object object) {
        return Iterables.contains(this.getDelegate(), object);
    }

    public final FluentIterable<E> cycle() {
        return FluentIterable.from(Iterables.cycle(this.getDelegate()));
    }

    @Beta
    public final FluentIterable<E> append(Iterable<? extends E> iterable) {
        return FluentIterable.from(FluentIterable.concat(this.getDelegate(), iterable));
    }

    @Beta
    public final FluentIterable<E> append(E ... EArray) {
        return FluentIterable.from(FluentIterable.concat(this.getDelegate(), Arrays.asList(EArray)));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return FluentIterable.from(Iterables.filter(this.getDelegate(), predicate));
    }

    @GwtIncompatible
    public final <T> FluentIterable<T> filter(Class<T> clazz) {
        return FluentIterable.from(Iterables.filter(this.getDelegate(), clazz));
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(this.getDelegate(), predicate);
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(this.getDelegate(), predicate);
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(this.getDelegate(), predicate);
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return FluentIterable.from(Iterables.transform(this.getDelegate(), function));
    }

    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return FluentIterable.from(FluentIterable.concat(this.transform(function)));
    }

    public final Optional<E> first() {
        Iterator<E> iterator2 = this.getDelegate().iterator();
        return iterator2.hasNext() ? Optional.of(iterator2.next()) : Optional.absent();
    }

    public final Optional<E> last() {
        E e;
        Iterable<E> iterable = this.getDelegate();
        if (iterable instanceof List) {
            List list = (List)iterable;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.of(list.get(list.size() - 1));
        }
        Iterator<E> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return Optional.absent();
        }
        if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)iterable;
            return Optional.of(sortedSet.last());
        }
        do {
            e = iterator2.next();
        } while (iterator2.hasNext());
        return Optional.of(e);
    }

    public final FluentIterable<E> skip(int n) {
        return FluentIterable.from(Iterables.skip(this.getDelegate(), n));
    }

    public final FluentIterable<E> limit(int n) {
        return FluentIterable.from(Iterables.limit(this.getDelegate(), n));
    }

    public final boolean isEmpty() {
        return !this.getDelegate().iterator().hasNext();
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(this.getDelegate());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(this.getDelegate());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(this.getDelegate());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, this.getDelegate());
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(this.getDelegate());
    }

    public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> function) {
        return Maps.toMap(this.getDelegate(), function);
    }

    public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> function) {
        return Multimaps.index(this.getDelegate(), function);
    }

    public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> function) {
        return Maps.uniqueIndex(this.getDelegate(), function);
    }

    @GwtIncompatible
    public final E[] toArray(Class<E> clazz) {
        return Iterables.toArray(this.getDelegate(), clazz);
    }

    @CanIgnoreReturnValue
    public final <C extends Collection<? super E>> C copyInto(C c) {
        Preconditions.checkNotNull(c);
        Iterable<E> iterable = this.getDelegate();
        if (iterable instanceof Collection) {
            c.addAll(Collections2.cast(iterable));
        } else {
            for (E e : iterable) {
                c.add(e);
            }
        }
        return c;
    }

    @Beta
    public final String join(Joiner joiner) {
        return joiner.join(this);
    }

    public final E get(int n) {
        return Iterables.get(this.getDelegate(), n);
    }

    public final Stream<E> stream() {
        return Streams.stream(this.getDelegate());
    }

    private static class FromIterableFunction<E>
    implements Function<Iterable<E>, FluentIterable<E>> {
        private FromIterableFunction() {
        }

        @Override
        public FluentIterable<E> apply(Iterable<E> iterable) {
            return FluentIterable.from(iterable);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Iterable)object);
        }
    }
}

