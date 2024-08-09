/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AllEqualOrdering;
import com.google.common.collect.ByFunctionOrdering;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ComparatorOrdering;
import com.google.common.collect.CompoundOrdering;
import com.google.common.collect.ExplicitOrdering;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LexicographicalOrdering;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.NullsFirstOrdering;
import com.google.common.collect.NullsLastOrdering;
import com.google.common.collect.Platform;
import com.google.common.collect.ReverseOrdering;
import com.google.common.collect.TopKSelector;
import com.google.common.collect.UsingToStringOrdering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class Ordering<T>
implements Comparator<T> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    @GwtCompatible(serializable=true)
    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering<T>)comparator : new ComparatorOrdering<T>(comparator);
    }

    @Deprecated
    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> from(Ordering<T> ordering) {
        return Preconditions.checkNotNull(ordering);
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> explicit(List<T> list) {
        return new ExplicitOrdering<T>(list);
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> explicit(T t, T ... TArray) {
        return Ordering.explicit(Lists.asList(t, TArray));
    }

    @GwtCompatible(serializable=true)
    public static Ordering<Object> allEqual() {
        return AllEqualOrdering.INSTANCE;
    }

    @GwtCompatible(serializable=true)
    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }

    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }

    protected Ordering() {
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new ByFunctionOrdering<F, T>(function, this);
    }

    <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
        return this.onResultOf(Maps.keyFunction());
    }

    @GwtCompatible(serializable=true)
    public <U extends T> Ordering<U> compound(Comparator<? super U> comparator) {
        return new CompoundOrdering<U>(this, Preconditions.checkNotNull(comparator));
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> iterable) {
        return new CompoundOrdering(iterable);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new LexicographicalOrdering(this);
    }

    @Override
    @CanIgnoreReturnValue
    public abstract int compare(@Nullable T var1, @Nullable T var2);

    @CanIgnoreReturnValue
    public <E extends T> E min(Iterator<E> iterator2) {
        E e = iterator2.next();
        while (iterator2.hasNext()) {
            e = this.min(e, iterator2.next());
        }
        return e;
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(Iterable<E> iterable) {
        return this.min(iterable.iterator());
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(@Nullable E e, @Nullable E e2) {
        return this.compare(e, e2) <= 0 ? e : e2;
    }

    @CanIgnoreReturnValue
    public <E extends T> E min(@Nullable E e, @Nullable E e2, @Nullable E e3, E ... EArray) {
        E e4 = this.min(this.min(e, e2), e3);
        for (E e5 : EArray) {
            e4 = this.min(e4, e5);
        }
        return e4;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(Iterator<E> iterator2) {
        E e = iterator2.next();
        while (iterator2.hasNext()) {
            e = this.max(e, iterator2.next());
        }
        return e;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(Iterable<E> iterable) {
        return this.max(iterable.iterator());
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(@Nullable E e, @Nullable E e2) {
        return this.compare(e, e2) >= 0 ? e : e2;
    }

    @CanIgnoreReturnValue
    public <E extends T> E max(@Nullable E e, @Nullable E e2, @Nullable E e3, E ... EArray) {
        E e4 = this.max(this.max(e, e2), e3);
        for (E e5 : EArray) {
            e4 = this.max(e4, e5);
        }
        return e4;
    }

    public <E extends T> List<E> leastOf(Iterable<E> iterable, int n) {
        Collection collection;
        if (iterable instanceof Collection && (long)(collection = (Collection)iterable).size() <= 2L * (long)n) {
            Object[] objectArray = collection.toArray();
            Arrays.sort(objectArray, this);
            if (objectArray.length > n) {
                objectArray = Arrays.copyOf(objectArray, n);
            }
            return Collections.unmodifiableList(Arrays.asList(objectArray));
        }
        return this.leastOf(iterable.iterator(), n);
    }

    public <E extends T> List<E> leastOf(Iterator<E> iterator2, int n) {
        Preconditions.checkNotNull(iterator2);
        CollectPreconditions.checkNonnegative(n, "k");
        if (n == 0 || !iterator2.hasNext()) {
            return ImmutableList.of();
        }
        if (n >= 0x3FFFFFFF) {
            ArrayList<E> arrayList = Lists.newArrayList(iterator2);
            Collections.sort(arrayList, this);
            if (arrayList.size() > n) {
                arrayList.subList(n, arrayList.size()).clear();
            }
            arrayList.trimToSize();
            return Collections.unmodifiableList(arrayList);
        }
        TopKSelector<E> topKSelector = TopKSelector.least(n, this);
        topKSelector.offerAll(iterator2);
        return topKSelector.topK();
    }

    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int n) {
        return this.reverse().leastOf(iterable, n);
    }

    public <E extends T> List<E> greatestOf(Iterator<E> iterator2, int n) {
        return this.reverse().leastOf(iterator2, n);
    }

    @CanIgnoreReturnValue
    public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
        Object[] objectArray = Iterables.toArray(iterable);
        Arrays.sort(objectArray, this);
        return Lists.newArrayList(Arrays.asList(objectArray));
    }

    @CanIgnoreReturnValue
    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.sortedCopyOf(this, iterable);
    }

    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<T> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            T t = iterator2.next();
            while (iterator2.hasNext()) {
                T t2 = iterator2.next();
                if (this.compare(t, t2) > 0) {
                    return true;
                }
                t = t2;
            }
        }
        return false;
    }

    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<T> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            T t = iterator2.next();
            while (iterator2.hasNext()) {
                T t2 = iterator2.next();
                if (this.compare(t, t2) >= 0) {
                    return true;
                }
                t = t2;
            }
        }
        return false;
    }

    @Deprecated
    public int binarySearch(List<? extends T> list, @Nullable T t) {
        return Collections.binarySearch(list, t, this);
    }

    @VisibleForTesting
    static class IncomparableValueException
    extends ClassCastException {
        final Object value;
        private static final long serialVersionUID = 0L;

        IncomparableValueException(Object object) {
            super("Cannot compare value: " + object);
            this.value = object;
        }
    }

    @VisibleForTesting
    static class ArbitraryOrdering
    extends Ordering<Object> {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final ConcurrentMap<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeMap();

        ArbitraryOrdering() {
        }

        private Integer getUid(Object object) {
            Integer n;
            Integer n2 = (Integer)this.uids.get(object);
            if (n2 == null && (n = this.uids.putIfAbsent(object, n2 = Integer.valueOf(this.counter.getAndIncrement()))) != null) {
                n2 = n;
            }
            return n2;
        }

        @Override
        public int compare(Object object, Object object2) {
            int n;
            if (object == object2) {
                return 1;
            }
            if (object == null) {
                return 1;
            }
            if (object2 == null) {
                return 0;
            }
            int n2 = this.identityHashCode(object);
            if (n2 != (n = this.identityHashCode(object2))) {
                return n2 < n ? -1 : 1;
            }
            int n3 = this.getUid(object).compareTo(this.getUid(object2));
            if (n3 == 0) {
                throw new AssertionError();
            }
            return n3;
        }

        public String toString() {
            return "Ordering.arbitrary()";
        }

        int identityHashCode(Object object) {
            return System.identityHashCode(object);
        }
    }

    private static class ArbitraryOrderingHolder {
        static final Ordering<Object> ARBITRARY_ORDERING = new ArbitraryOrdering();

        private ArbitraryOrderingHolder() {
        }
    }
}

