/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

@GwtCompatible
public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.on(", ").useForNull("null");

    private Collections2() {
    }

    public static <E> Collection<E> filter(Collection<E> collection, com.google.common.base.Predicate<? super E> predicate) {
        if (collection instanceof FilteredCollection) {
            return ((FilteredCollection)collection).createCombined(predicate);
        }
        return new FilteredCollection<E>(Preconditions.checkNotNull(collection), Preconditions.checkNotNull(predicate));
    }

    static boolean safeContains(Collection<?> collection, @Nullable Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(object);
        } catch (ClassCastException classCastException) {
            return true;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    static boolean safeRemove(Collection<?> collection, @Nullable Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(object);
        } catch (ClassCastException classCastException) {
            return true;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection<F, T>(collection, function);
    }

    static boolean containsAllImpl(Collection<?> collection, Collection<?> collection2) {
        return Iterables.all(collection2, Predicates.in(collection));
    }

    static String toStringImpl(Collection<?> collection) {
        StringBuilder stringBuilder = Collections2.newStringBuilderForCollection(collection.size()).append('[');
        STANDARD_JOINER.appendTo(stringBuilder, Iterables.transform(collection, new Function<Object, Object>(collection){
            final Collection val$collection;
            {
                this.val$collection = collection;
            }

            @Override
            public Object apply(Object object) {
                return object == this.val$collection ? "(this Collection)" : object;
            }
        }));
        return stringBuilder.append(']').toString();
    }

    static StringBuilder newStringBuilderForCollection(int n) {
        CollectPreconditions.checkNonnegative(n, "size");
        return new StringBuilder((int)Math.min((long)n * 8L, 0x40000000L));
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection)iterable;
    }

    @Beta
    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return Collections2.orderedPermutations(iterable, Ordering.natural());
    }

    @Beta
    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection<E>(iterable, comparator);
    }

    @Beta
    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection<E>(ImmutableList.copyOf(collection));
    }

    private static boolean isPermutation(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return true;
        }
        HashMultiset<?> hashMultiset = HashMultiset.create(list);
        HashMultiset<?> hashMultiset2 = HashMultiset.create(list2);
        return hashMultiset.equals(hashMultiset2);
    }

    private static boolean isPositiveInt(long l) {
        return l >= 0L && l <= Integer.MAX_VALUE;
    }

    static boolean access$000(long l) {
        return Collections2.isPositiveInt(l);
    }

    static boolean access$100(List list, List list2) {
        return Collections2.isPermutation(list, list2);
    }

    private static class PermutationIterator<E>
    extends AbstractIterator<List<E>> {
        final List<E> list;
        final int[] c;
        final int[] o;
        int j;

        PermutationIterator(List<E> list) {
            this.list = new ArrayList<E>(list);
            int n = list.size();
            this.c = new int[n];
            this.o = new int[n];
            Arrays.fill(this.c, 0);
            Arrays.fill(this.o, 1);
            this.j = Integer.MAX_VALUE;
        }

        @Override
        protected List<E> computeNext() {
            if (this.j <= 0) {
                return (List)this.endOfData();
            }
            ImmutableList<E> immutableList = ImmutableList.copyOf(this.list);
            this.calculateNextPermutation();
            return immutableList;
        }

        void calculateNextPermutation() {
            block4: {
                int n;
                this.j = this.list.size() - 1;
                int n2 = 0;
                if (this.j == -1) {
                    return;
                }
                while (true) {
                    if ((n = this.c[this.j] + this.o[this.j]) < 0) {
                        this.switchDirection();
                        continue;
                    }
                    if (n != this.j + 1) break;
                    if (this.j != 0) {
                        ++n2;
                        this.switchDirection();
                        continue;
                    }
                    break block4;
                    break;
                }
                Collections.swap(this.list, this.j - this.c[this.j] + n2, this.j - n + n2);
                this.c[this.j] = n;
            }
        }

        void switchDirection() {
            this.o[this.j] = -this.o[this.j];
            --this.j;
        }

        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }

    private static final class PermutationCollection<E>
    extends AbstractCollection<List<E>> {
        final ImmutableList<E> inputList;

        PermutationCollection(ImmutableList<E> immutableList) {
            this.inputList = immutableList;
        }

        @Override
        public int size() {
            return IntMath.factorial(this.inputList.size());
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<List<E>> iterator() {
            return new PermutationIterator<E>(this.inputList);
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object instanceof List) {
                List list = (List)object;
                return Collections2.access$100(this.inputList, list);
            }
            return true;
        }

        @Override
        public String toString() {
            return "permutations(" + this.inputList + ")";
        }
    }

    private static final class OrderedPermutationIterator<E>
    extends AbstractIterator<List<E>> {
        List<E> nextPermutation;
        final Comparator<? super E> comparator;

        OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator) {
            this.nextPermutation = Lists.newArrayList(list);
            this.comparator = comparator;
        }

        @Override
        protected List<E> computeNext() {
            if (this.nextPermutation == null) {
                return (List)this.endOfData();
            }
            ImmutableList<E> immutableList = ImmutableList.copyOf(this.nextPermutation);
            this.calculateNextPermutation();
            return immutableList;
        }

        void calculateNextPermutation() {
            int n = this.findNextJ();
            if (n == -1) {
                this.nextPermutation = null;
                return;
            }
            int n2 = this.findNextL(n);
            Collections.swap(this.nextPermutation, n, n2);
            int n3 = this.nextPermutation.size();
            Collections.reverse(this.nextPermutation.subList(n + 1, n3));
        }

        int findNextJ() {
            for (int i = this.nextPermutation.size() - 2; i >= 0; --i) {
                if (this.comparator.compare(this.nextPermutation.get(i), this.nextPermutation.get(i + 1)) >= 0) continue;
                return i;
            }
            return 1;
        }

        int findNextL(int n) {
            E e = this.nextPermutation.get(n);
            for (int i = this.nextPermutation.size() - 1; i > n; --i) {
                if (this.comparator.compare(e, this.nextPermutation.get(i)) >= 0) continue;
                return i;
            }
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }

    private static final class OrderedPermutationCollection<E>
    extends AbstractCollection<List<E>> {
        final ImmutableList<E> inputList;
        final Comparator<? super E> comparator;
        final int size;

        OrderedPermutationCollection(Iterable<E> iterable, Comparator<? super E> comparator) {
            this.inputList = Ordering.from(comparator).immutableSortedCopy(iterable);
            this.comparator = comparator;
            this.size = OrderedPermutationCollection.calculateSize(this.inputList, comparator);
        }

        private static <E> int calculateSize(List<E> list, Comparator<? super E> comparator) {
            long l = 1L;
            int n = 1;
            int n2 = 1;
            while (n < list.size()) {
                int n3 = comparator.compare(list.get(n - 1), list.get(n));
                if (n3 < 0) {
                    l *= LongMath.binomial(n, n2);
                    n2 = 0;
                    if (!Collections2.access$000(l)) {
                        return 0;
                    }
                }
                ++n;
                ++n2;
            }
            if (!Collections2.access$000(l *= LongMath.binomial(n, n2))) {
                return 0;
            }
            return (int)l;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator<E>(this.inputList, this.comparator);
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object instanceof List) {
                List list = (List)object;
                return Collections2.access$100(this.inputList, list);
            }
            return true;
        }

        @Override
        public String toString() {
            return "orderedPermutationCollection(" + this.inputList + ")";
        }
    }

    static class TransformedCollection<F, T>
    extends AbstractCollection<T> {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;

        TransformedCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.fromCollection = Preconditions.checkNotNull(collection);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromCollection.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }

        @Override
        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }

        @Override
        public Spliterator<T> spliterator() {
            return CollectSpliterators.map(this.fromCollection.spliterator(), this.function);
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            Preconditions.checkNotNull(consumer);
            this.fromCollection.forEach(arg_0 -> this.lambda$forEach$0(consumer, arg_0));
        }

        @Override
        public boolean removeIf(Predicate<? super T> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.fromCollection.removeIf(arg_0 -> this.lambda$removeIf$1(predicate, arg_0));
        }

        @Override
        public int size() {
            return this.fromCollection.size();
        }

        private boolean lambda$removeIf$1(Predicate predicate, Object object) {
            return predicate.test(this.function.apply(object));
        }

        private void lambda$forEach$0(Consumer consumer, Object object) {
            consumer.accept(this.function.apply(object));
        }
    }

    static class FilteredCollection<E>
    extends AbstractCollection<E> {
        final Collection<E> unfiltered;
        final com.google.common.base.Predicate<? super E> predicate;

        FilteredCollection(Collection<E> collection, com.google.common.base.Predicate<? super E> predicate) {
            this.unfiltered = collection;
            this.predicate = predicate;
        }

        FilteredCollection<E> createCombined(com.google.common.base.Predicate<? super E> predicate) {
            return new FilteredCollection<E>(this.unfiltered, Predicates.and(this.predicate, predicate));
        }

        @Override
        public boolean add(E e) {
            Preconditions.checkArgument(this.predicate.apply(e));
            return this.unfiltered.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            for (E e : collection) {
                Preconditions.checkArgument(this.predicate.apply(e));
            }
            return this.unfiltered.addAll(collection);
        }

        @Override
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (Collections2.safeContains(this.unfiltered, object)) {
                Object object2 = object;
                return this.predicate.apply(object2);
            }
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return Collections2.containsAllImpl(this, collection);
        }

        @Override
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered, this.predicate);
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public Spliterator<E> spliterator() {
            return CollectSpliterators.filter(this.unfiltered.spliterator(), this.predicate);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            Preconditions.checkNotNull(consumer);
            this.unfiltered.forEach(arg_0 -> this.lambda$forEach$0(consumer, arg_0));
        }

        @Override
        public boolean remove(Object object) {
            return this.contains(object) && this.unfiltered.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.removeIf(collection::contains);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.removeIf(arg_0 -> FilteredCollection.lambda$retainAll$1(collection, arg_0));
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.unfiltered.removeIf(arg_0 -> this.lambda$removeIf$2(predicate, arg_0));
        }

        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }

        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return Lists.newArrayList(this.iterator()).toArray(TArray);
        }

        private boolean lambda$removeIf$2(Predicate predicate, Object object) {
            return this.predicate.apply(object) && predicate.test(object);
        }

        private static boolean lambda$retainAll$1(Collection collection, Object object) {
            return !collection.contains(object);
        }

        private void lambda$forEach$0(Consumer consumer, Object object) {
            if (this.predicate.test(object)) {
                consumer.accept(object);
            }
        }
    }
}

