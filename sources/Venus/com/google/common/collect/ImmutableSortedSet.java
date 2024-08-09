/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.DescendingImmutableSortedSet;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSetFauxverideShim;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableSortedSet<E>
extends ImmutableSortedSetFauxverideShim<E>
implements NavigableSet<E>,
SortedIterable<E> {
    static final int SPLITERATOR_CHARACTERISTICS = 1301;
    final transient Comparator<? super E> comparator;
    @LazyInit
    @GwtIncompatible
    transient ImmutableSortedSet<E> descendingSet;

    @Beta
    public static <E> Collector<E, ?, ImmutableSortedSet<E>> toImmutableSortedSet(Comparator<? super E> comparator) {
        return CollectCollectors.toImmutableSortedSet(comparator);
    }

    static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
        }
        return new RegularImmutableSortedSet<E>(ImmutableList.of(), comparator);
    }

    public static <E> ImmutableSortedSet<E> of() {
        return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e) {
        return new RegularImmutableSortedSet<E>(ImmutableList.of(e), Ordering.natural());
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2) {
        return ImmutableSortedSet.construct(Ordering.natural(), 2, e, e2);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3) {
        return ImmutableSortedSet.construct(Ordering.natural(), 3, e, e2, e3);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSortedSet.construct(Ordering.natural(), 4, e, e2, e3, e4);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSortedSet.construct(Ordering.natural(), 5, e, e2, e3, e4, e5);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... EArray) {
        Comparable[] comparableArray = new Comparable[6 + EArray.length];
        comparableArray[0] = e;
        comparableArray[1] = e2;
        comparableArray[2] = e3;
        comparableArray[3] = e4;
        comparableArray[4] = e5;
        comparableArray[5] = e6;
        System.arraycopy(EArray, 0, comparableArray, 6, EArray.length);
        return ImmutableSortedSet.construct(Ordering.natural(), comparableArray.length, comparableArray);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] EArray) {
        return ImmutableSortedSet.construct(Ordering.natural(), EArray.length, (Object[])EArray.clone());
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> iterable) {
        Ordering ordering = Ordering.natural();
        return ImmutableSortedSet.copyOf(ordering, iterable);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> collection) {
        Ordering ordering = Ordering.natural();
        return ImmutableSortedSet.copyOf(ordering, collection);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> iterator2) {
        Ordering ordering = Ordering.natural();
        return ImmutableSortedSet.copyOf(ordering, iterator2);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> iterator2) {
        return ((Builder)new Builder<E>(comparator).addAll((Iterator)iterator2)).build();
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        Object object;
        Preconditions.checkNotNull(comparator);
        boolean bl = SortedIterables.hasSameComparator(comparator, iterable);
        if (bl && iterable instanceof ImmutableSortedSet && !((ImmutableCollection)(object = (ImmutableSortedSet)iterable)).isPartialView()) {
            return object;
        }
        object = Iterables.toArray(iterable);
        return ImmutableSortedSet.construct(comparator, ((Object[])object).length, object);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> collection) {
        return ImmutableSortedSet.copyOf(comparator, collection);
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
        Comparator<E> comparator = SortedIterables.comparator(sortedSet);
        ImmutableList<E> immutableList = ImmutableList.copyOf(sortedSet);
        if (immutableList.isEmpty()) {
            return ImmutableSortedSet.emptySet(comparator);
        }
        return new RegularImmutableSortedSet<E>(immutableList, comparator);
    }

    static <E> ImmutableSortedSet<E> construct(Comparator<? super E> comparator, int n, E ... EArray) {
        if (n == 0) {
            return ImmutableSortedSet.emptySet(comparator);
        }
        ObjectArrays.checkElementsNotNull((Object[])EArray, n);
        Arrays.sort(EArray, 0, n, comparator);
        int n2 = 1;
        for (int i = 1; i < n; ++i) {
            E e = EArray[i];
            E e2 = EArray[n2 - 1];
            if (comparator.compare(e, e2) == 0) continue;
            EArray[n2++] = e;
        }
        Arrays.fill(EArray, n2, n, null);
        return new RegularImmutableSortedSet<E>(ImmutableList.asImmutableList(EArray, n2), comparator);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    int unsafeCompare(Object object, Object object2) {
        return ImmutableSortedSet.unsafeCompare(this.comparator, object, object2);
    }

    static int unsafeCompare(Comparator<?> comparator, Object object, Object object2) {
        Comparator<?> comparator2 = comparator;
        return comparator2.compare(object, object2);
    }

    ImmutableSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public ImmutableSortedSet<E> headSet(E e) {
        return this.headSet((Object)e, true);
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet<E> headSet(E e, boolean bl) {
        return this.headSetImpl(Preconditions.checkNotNull(e), bl);
    }

    @Override
    public ImmutableSortedSet<E> subSet(E e, E e2) {
        return this.subSet((Object)e, true, (Object)e2, true);
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        Preconditions.checkNotNull(e);
        Preconditions.checkNotNull(e2);
        Preconditions.checkArgument(this.comparator.compare(e, e2) <= 0);
        return this.subSetImpl(e, bl, e2, bl2);
    }

    @Override
    public ImmutableSortedSet<E> tailSet(E e) {
        return this.tailSet((Object)e, false);
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet<E> tailSet(E e, boolean bl) {
        return this.tailSetImpl(Preconditions.checkNotNull(e), bl);
    }

    abstract ImmutableSortedSet<E> headSetImpl(E var1, boolean var2);

    abstract ImmutableSortedSet<E> subSetImpl(E var1, boolean var2, E var3, boolean var4);

    abstract ImmutableSortedSet<E> tailSetImpl(E var1, boolean var2);

    @Override
    @GwtIncompatible
    public E lower(E e) {
        return Iterators.getNext(((ImmutableSortedSet)this.headSet((Object)e, true)).descendingIterator(), null);
    }

    @Override
    @GwtIncompatible
    public E floor(E e) {
        return Iterators.getNext(((ImmutableSortedSet)this.headSet((Object)e, false)).descendingIterator(), null);
    }

    @Override
    @GwtIncompatible
    public E ceiling(E e) {
        return Iterables.getFirst(this.tailSet((Object)e, false), null);
    }

    @Override
    @GwtIncompatible
    public E higher(E e) {
        return Iterables.getFirst(this.tailSet((Object)e, true), null);
    }

    @Override
    public E first() {
        return this.iterator().next();
    }

    @Override
    public E last() {
        return this.descendingIterator().next();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    @GwtIncompatible
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    @GwtIncompatible
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.descendingSet;
        if (immutableSortedSet == null) {
            immutableSortedSet = this.descendingSet = this.createDescendingSet();
            immutableSortedSet.descendingSet = this;
        }
        return immutableSortedSet;
    }

    @GwtIncompatible
    ImmutableSortedSet<E> createDescendingSet() {
        return new DescendingImmutableSortedSet(this);
    }

    @Override
    public Spliterator<E> spliterator() {
        return new Spliterators.AbstractSpliterator<E>(this, this.size(), 1365){
            final UnmodifiableIterator<E> iterator;
            final ImmutableSortedSet this$0;
            {
                this.this$0 = immutableSortedSet;
                super(l, n);
                this.iterator = this.this$0.iterator();
            }

            @Override
            public boolean tryAdvance(Consumer<? super E> consumer) {
                if (this.iterator.hasNext()) {
                    consumer.accept(this.iterator.next());
                    return false;
                }
                return true;
            }

            @Override
            public Comparator<? super E> getComparator() {
                return this.this$0.comparator;
            }
        };
    }

    @Override
    @GwtIncompatible
    public abstract UnmodifiableIterator<E> descendingIterator();

    abstract int indexOf(@Nullable Object var1);

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override
    Object writeReplace() {
        return new SerializedForm<E>(this.comparator, this.toArray());
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public SortedSet tailSet(Object object) {
        return this.tailSet(object);
    }

    @Override
    public SortedSet headSet(Object object) {
        return this.headSet(object);
    }

    @Override
    public SortedSet subSet(Object object, Object object2) {
        return this.subSet(object, object2);
    }

    @Override
    @GwtIncompatible
    public NavigableSet tailSet(Object object, boolean bl) {
        return this.tailSet(object, bl);
    }

    @Override
    @GwtIncompatible
    public NavigableSet headSet(Object object, boolean bl) {
        return this.headSet(object, bl);
    }

    @Override
    @GwtIncompatible
    public NavigableSet subSet(Object object, boolean bl, Object object2, boolean bl2) {
        return this.subSet(object, bl, object2, bl2);
    }

    @Override
    @GwtIncompatible
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }

    @Override
    @GwtIncompatible
    public NavigableSet descendingSet() {
        return this.descendingSet();
    }

    private static class SerializedForm<E>
    implements Serializable {
        final Comparator<? super E> comparator;
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        public SerializedForm(Comparator<? super E> comparator, Object[] objectArray) {
            this.comparator = comparator;
            this.elements = objectArray;
        }

        Object readResolve() {
            return ((Builder)new Builder<E>(this.comparator).add(this.elements)).build();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Builder<E>
    extends ImmutableSet.Builder<E> {
        private final Comparator<? super E> comparator;

        public Builder(Comparator<? super E> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add((Object)e);
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
        @CanIgnoreReturnValue
        Builder<E> combine(ImmutableCollection.ArrayBasedBuilder<E> arrayBasedBuilder) {
            super.combine((ImmutableCollection.ArrayBasedBuilder)arrayBasedBuilder);
            return this;
        }

        @Override
        public ImmutableSortedSet<E> build() {
            Object[] objectArray = this.contents;
            ImmutableSortedSet<Object> immutableSortedSet = ImmutableSortedSet.construct(this.comparator, this.size, objectArray);
            this.size = immutableSortedSet.size();
            return immutableSortedSet;
        }

        @Override
        public ImmutableSet build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        ImmutableSet.Builder combine(ImmutableCollection.ArrayBasedBuilder arrayBasedBuilder) {
            return this.combine(arrayBasedBuilder);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableSet.Builder addAll(Iterator iterator2) {
            return this.addAll(iterator2);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableSet.Builder addAll(Iterable iterable) {
            return this.addAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableSet.Builder add(Object[] objectArray) {
            return this.add(objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableSet.Builder add(Object object) {
            return this.add(object);
        }

        @Override
        @CanIgnoreReturnValue
        ImmutableCollection.ArrayBasedBuilder combine(ImmutableCollection.ArrayBasedBuilder arrayBasedBuilder) {
            return this.combine(arrayBasedBuilder);
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
        public ImmutableCollection.ArrayBasedBuilder add(Object object) {
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
        public ImmutableCollection.Builder add(Object object) {
            return this.add(object);
        }
    }
}

