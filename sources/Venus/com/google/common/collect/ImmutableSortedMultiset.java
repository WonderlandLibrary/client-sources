/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.DescendingImmutableSortedMultiset;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMultisetFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedMultiset;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
public abstract class ImmutableSortedMultiset<E>
extends ImmutableSortedMultisetFauxverideShim<E>
implements SortedMultiset<E> {
    @LazyInit
    transient ImmutableSortedMultiset<E> descendingMultiset;

    @Beta
    public static <E> Collector<E, ?, ImmutableSortedMultiset<E>> toImmutableSortedMultiset(Comparator<? super E> comparator) {
        return ImmutableSortedMultiset.toImmutableSortedMultiset(comparator, Function.identity(), ImmutableSortedMultiset::lambda$toImmutableSortedMultiset$0);
    }

    private static <T, E> Collector<T, ?, ImmutableSortedMultiset<E>> toImmutableSortedMultiset(Comparator<? super E> comparator, Function<? super T, ? extends E> function, ToIntFunction<? super T> toIntFunction) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(toIntFunction);
        return Collector.of(() -> ImmutableSortedMultiset.lambda$toImmutableSortedMultiset$1(comparator), (arg_0, arg_1) -> ImmutableSortedMultiset.lambda$toImmutableSortedMultiset$2(function, toIntFunction, arg_0, arg_1), ImmutableSortedMultiset::lambda$toImmutableSortedMultiset$3, arg_0 -> ImmutableSortedMultiset.lambda$toImmutableSortedMultiset$4(comparator, arg_0), new Collector.Characteristics[0]);
    }

    public static <E> ImmutableSortedMultiset<E> of() {
        return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e) {
        RegularImmutableSortedSet regularImmutableSortedSet = (RegularImmutableSortedSet)ImmutableSortedSet.of(e);
        long[] lArray = new long[]{0L, 1L};
        return new RegularImmutableSortedMultiset(regularImmutableSortedSet, lArray, 0, 1);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3, e4));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(e, e2, e3, e4, e5));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... EArray) {
        int n = EArray.length + 6;
        ArrayList arrayList = Lists.newArrayListWithCapacity(n);
        Collections.addAll(arrayList, e, e2, e3, e4, e5, e6);
        Collections.addAll(arrayList, EArray);
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), arrayList);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] EArray) {
        return ImmutableSortedMultiset.copyOf(Ordering.natural(), Arrays.asList(EArray));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> iterable) {
        Ordering ordering = Ordering.natural();
        return ImmutableSortedMultiset.copyOf(ordering, iterable);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> iterator2) {
        Ordering ordering = Ordering.natural();
        return ImmutableSortedMultiset.copyOf(ordering, iterator2);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> iterator2) {
        Preconditions.checkNotNull(comparator);
        return ((Builder)new Builder<E>(comparator).addAll((Iterator)iterator2)).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        AbstractCollection abstractCollection;
        if (iterable instanceof ImmutableSortedMultiset && comparator.equals(((ImmutableSortedMultiset)(abstractCollection = (ImmutableSortedMultiset)iterable)).comparator())) {
            if (((ImmutableCollection)abstractCollection).isPartialView()) {
                return ImmutableSortedMultiset.copyOfSortedEntries(comparator, ((ImmutableSet)((ImmutableMultiset)abstractCollection).entrySet()).asList());
            }
            return abstractCollection;
        }
        iterable = Lists.newArrayList(iterable);
        abstractCollection = TreeMultiset.create(Preconditions.checkNotNull(comparator));
        Iterables.addAll(abstractCollection, iterable);
        return ImmutableSortedMultiset.copyOfSortedEntries(comparator, ((TreeMultiset)abstractCollection).entrySet());
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return ImmutableSortedMultiset.copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> collection) {
        if (collection.isEmpty()) {
            return ImmutableSortedMultiset.emptyMultiset(comparator);
        }
        ImmutableList.Builder builder = new ImmutableList.Builder(collection.size());
        long[] lArray = new long[collection.size() + 1];
        int n = 0;
        for (Multiset.Entry<E> entry : collection) {
            builder.add((Object)entry.getElement());
            lArray[n + 1] = lArray[n] + (long)entry.getCount();
            ++n;
        }
        return new RegularImmutableSortedMultiset<E>(new RegularImmutableSortedSet<E>(builder.build(), comparator), lArray, 0, collection.size());
    }

    static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
        }
        return new RegularImmutableSortedMultiset<E>(comparator);
    }

    ImmutableSortedMultiset() {
    }

    @Override
    public final Comparator<? super E> comparator() {
        return ((ImmutableSortedSet)this.elementSet()).comparator();
    }

    @Override
    public abstract ImmutableSortedSet<E> elementSet();

    @Override
    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> immutableSortedMultiset = this.descendingMultiset;
        if (immutableSortedMultiset == null) {
            this.descendingMultiset = this.isEmpty() ? ImmutableSortedMultiset.emptyMultiset(Ordering.from(this.comparator()).reverse()) : new DescendingImmutableSortedMultiset(this);
            return this.descendingMultiset;
        }
        return immutableSortedMultiset;
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract ImmutableSortedMultiset<E> headMultiset(E var1, BoundType var2);

    @Override
    public ImmutableSortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        Preconditions.checkArgument(this.comparator().compare(e, e2) <= 0, "Expected lowerBound <= upperBound but %s > %s", e, e2);
        return ((ImmutableSortedMultiset)this.tailMultiset((Object)e, boundType)).headMultiset((Object)e2, boundType2);
    }

    @Override
    public abstract ImmutableSortedMultiset<E> tailMultiset(E var1, BoundType var2);

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    @Override
    public ImmutableSet elementSet() {
        return this.elementSet();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    @Override
    public SortedMultiset tailMultiset(Object object, BoundType boundType) {
        return this.tailMultiset(object, boundType);
    }

    @Override
    public SortedMultiset subMultiset(Object object, BoundType boundType, Object object2, BoundType boundType2) {
        return this.subMultiset(object, boundType, object2, boundType2);
    }

    @Override
    public SortedMultiset headMultiset(Object object, BoundType boundType) {
        return this.headMultiset(object, boundType);
    }

    @Override
    public SortedMultiset descendingMultiset() {
        return this.descendingMultiset();
    }

    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }

    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }

    private static ImmutableSortedMultiset lambda$toImmutableSortedMultiset$4(Comparator comparator, Multiset multiset) {
        return ImmutableSortedMultiset.copyOfSortedEntries(comparator, multiset.entrySet());
    }

    private static Multiset lambda$toImmutableSortedMultiset$3(Multiset multiset, Multiset multiset2) {
        multiset.addAll(multiset2);
        return multiset;
    }

    private static void lambda$toImmutableSortedMultiset$2(Function function, ToIntFunction toIntFunction, Multiset multiset, Object object) {
        multiset.add(function.apply(object), toIntFunction.applyAsInt(object));
    }

    private static Multiset lambda$toImmutableSortedMultiset$1(Comparator comparator) {
        return TreeMultiset.create(comparator);
    }

    private static int lambda$toImmutableSortedMultiset$0(Object object) {
        return 0;
    }

    private static final class SerializedForm<E>
    implements Serializable {
        final Comparator<? super E> comparator;
        final E[] elements;
        final int[] counts;

        SerializedForm(SortedMultiset<E> sortedMultiset) {
            this.comparator = sortedMultiset.comparator();
            int n = sortedMultiset.entrySet().size();
            this.elements = new Object[n];
            this.counts = new int[n];
            int n2 = 0;
            for (Multiset.Entry<E> entry : sortedMultiset.entrySet()) {
                this.elements[n2] = entry.getElement();
                this.counts[n2] = entry.getCount();
                ++n2;
            }
        }

        Object readResolve() {
            int n = this.elements.length;
            Builder<E> builder = new Builder<E>(this.comparator);
            for (int i = 0; i < n; ++i) {
                builder.addCopies((Object)this.elements[i], this.counts[i]);
            }
            return builder.build();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<E>
    extends ImmutableMultiset.Builder<E> {
        public Builder(Comparator<? super E> comparator) {
            super(TreeMultiset.create(Preconditions.checkNotNull(comparator)));
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add((Object)e);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e, int n) {
            super.addCopies(e, n);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> setCount(E e, int n) {
            super.setCount(e, n);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E ... EArray) {
            super.add((Object[])EArray);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable)iterable);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll((Iterator)iterator2);
            return this;
        }

        @Override
        public ImmutableSortedMultiset<E> build() {
            return ImmutableSortedMultiset.copyOfSorted((SortedMultiset)this.contents);
        }

        @Override
        public ImmutableMultiset build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder addAll(Iterator iterator2) {
            return this.addAll(iterator2);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder addAll(Iterable iterable) {
            return this.addAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder add(Object[] objectArray) {
            return this.add(objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder setCount(Object object, int n) {
            return this.setCount(object, n);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder addCopies(Object object, int n) {
            return this.addCopies(object, n);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultiset.Builder add(Object object) {
            return this.add(object);
        }

        @Override
        public ImmutableCollection build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterator iterator2) {
            return this.addAll(iterator2);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterable iterable) {
            return this.addAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object[] objectArray) {
            return this.add(objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object object) {
            return this.add(object);
        }
    }
}

