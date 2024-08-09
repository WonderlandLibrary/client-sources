/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Collections2;
import com.google.common.collect.ConsumingQueueIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Streams;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Iterables {
    private Iterables() {
    }

    public static <T> Iterable<T> unmodifiableIterable(Iterable<? extends T> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof UnmodifiableIterable || iterable instanceof ImmutableCollection) {
            Iterable<? extends T> iterable2 = iterable;
            return iterable2;
        }
        return new UnmodifiableIterable(iterable, null);
    }

    @Deprecated
    public static <E> Iterable<E> unmodifiableIterable(ImmutableCollection<E> immutableCollection) {
        return Preconditions.checkNotNull(immutableCollection);
    }

    public static int size(Iterable<?> iterable) {
        return iterable instanceof Collection ? ((Collection)iterable).size() : Iterators.size(iterable.iterator());
    }

    public static boolean contains(Iterable<?> iterable, @Nullable Object object) {
        if (iterable instanceof Collection) {
            Collection collection = (Collection)iterable;
            return Collections2.safeContains(collection, object);
        }
        return Iterators.contains(iterable.iterator(), object);
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterable<?> iterable, Collection<?> collection) {
        return iterable instanceof Collection ? ((Collection)iterable).removeAll(Preconditions.checkNotNull(collection)) : Iterators.removeAll(iterable.iterator(), collection);
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterable<?> iterable, Collection<?> collection) {
        return iterable instanceof Collection ? ((Collection)iterable).retainAll(Preconditions.checkNotNull(collection)) : Iterators.retainAll(iterable.iterator(), collection);
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterable<T> iterable, Predicate<? super T> predicate) {
        if (iterable instanceof Collection) {
            return ((Collection)iterable).removeIf(predicate);
        }
        return Iterators.removeIf(iterable.iterator(), predicate);
    }

    @Nullable
    static <T> T removeFirstMatching(Iterable<T> iterable, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        Iterator<T> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (!predicate.apply(t)) continue;
            iterator2.remove();
            return t;
        }
        return null;
    }

    public static boolean elementsEqual(Iterable<?> iterable, Iterable<?> iterable2) {
        if (iterable instanceof Collection && iterable2 instanceof Collection) {
            Collection collection = (Collection)iterable;
            Collection collection2 = (Collection)iterable2;
            if (collection.size() != collection2.size()) {
                return true;
            }
        }
        return Iterators.elementsEqual(iterable.iterator(), iterable2.iterator());
    }

    public static String toString(Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator());
    }

    public static <T> T getOnlyElement(Iterable<T> iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }

    @Nullable
    public static <T> T getOnlyElement(Iterable<? extends T> iterable, @Nullable T t) {
        return Iterators.getOnlyElement(iterable.iterator(), t);
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> clazz) {
        return Iterables.toArray(iterable, ObjectArrays.newArray(clazz, 0));
    }

    static <T> T[] toArray(Iterable<? extends T> iterable, T[] TArray) {
        Collection<T> collection = Iterables.castOrCopyToCollection(iterable);
        return collection.toArray(TArray);
    }

    static Object[] toArray(Iterable<?> iterable) {
        return Iterables.castOrCopyToCollection(iterable).toArray();
    }

    private static <E> Collection<E> castOrCopyToCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ? (ArrayList<E>)iterable : Lists.newArrayList(iterable.iterator());
    }

    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> collection, Iterable<? extends T> iterable) {
        if (iterable instanceof Collection) {
            Collection<? extends T> collection2 = Collections2.cast(iterable);
            return collection.addAll(collection2);
        }
        return Iterators.addAll(collection, Preconditions.checkNotNull(iterable).iterator());
    }

    public static int frequency(Iterable<?> iterable, @Nullable Object object) {
        if (iterable instanceof Multiset) {
            return ((Multiset)iterable).count(object);
        }
        if (iterable instanceof Set) {
            return ((Set)iterable).contains(object) ? 1 : 0;
        }
        return Iterators.frequency(iterable.iterator(), object);
    }

    public static <T> Iterable<T> cycle(Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(iterable){
            final Iterable val$iterable;
            {
                this.val$iterable = iterable;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.cycle(this.val$iterable);
            }

            @Override
            public Spliterator<T> spliterator() {
                return Stream.generate(() -> 1.lambda$spliterator$0(this.val$iterable)).flatMap(Streams::stream).spliterator();
            }

            @Override
            public String toString() {
                return this.val$iterable.toString() + " (cycled)";
            }

            private static Iterable lambda$spliterator$0(Iterable iterable) {
                return iterable;
            }
        };
    }

    public static <T> Iterable<T> cycle(T ... TArray) {
        return Iterables.cycle(Lists.newArrayList(TArray));
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return FluentIterable.concat(iterable, iterable2);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return FluentIterable.concat(iterable, iterable2, iterable3);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return FluentIterable.concat(iterable, iterable2, iterable3, iterable4);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> ... iterableArray) {
        return Iterables.concat(ImmutableList.copyOf(iterableArray));
    }

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterable) {
        return FluentIterable.concat(iterable);
    }

    public static <T> Iterable<List<T>> partition(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n > 0);
        return new FluentIterable<List<T>>(iterable, n){
            final Iterable val$iterable;
            final int val$size;
            {
                this.val$iterable = iterable;
                this.val$size = n;
            }

            @Override
            public Iterator<List<T>> iterator() {
                return Iterators.partition(this.val$iterable.iterator(), this.val$size);
            }
        };
    }

    public static <T> Iterable<List<T>> paddedPartition(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n > 0);
        return new FluentIterable<List<T>>(iterable, n){
            final Iterable val$iterable;
            final int val$size;
            {
                this.val$iterable = iterable;
                this.val$size = n;
            }

            @Override
            public Iterator<List<T>> iterator() {
                return Iterators.paddedPartition(this.val$iterable.iterator(), this.val$size);
            }
        };
    }

    public static <T> Iterable<T> filter(Iterable<T> iterable, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(predicate);
        return new FluentIterable<T>(iterable, predicate){
            final Iterable val$unfiltered;
            final Predicate val$retainIfTrue;
            {
                this.val$unfiltered = iterable;
                this.val$retainIfTrue = predicate;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.filter(this.val$unfiltered.iterator(), this.val$retainIfTrue);
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                Preconditions.checkNotNull(consumer);
                this.val$unfiltered.forEach(arg_0 -> 4.lambda$forEach$0(this.val$retainIfTrue, consumer, arg_0));
            }

            @Override
            public Spliterator<T> spliterator() {
                return CollectSpliterators.filter(this.val$unfiltered.spliterator(), this.val$retainIfTrue);
            }

            private static void lambda$forEach$0(Predicate predicate, Consumer consumer, Object object) {
                if (predicate.test(object)) {
                    consumer.accept(object);
                }
            }
        };
    }

    @GwtIncompatible
    public static <T> Iterable<T> filter(Iterable<?> iterable, Class<T> clazz) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(clazz);
        return new FluentIterable<T>(iterable, clazz){
            final Iterable val$unfiltered;
            final Class val$desiredType;
            {
                this.val$unfiltered = iterable;
                this.val$desiredType = clazz;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.filter(this.val$unfiltered.iterator(), this.val$desiredType);
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                Preconditions.checkNotNull(consumer);
                this.val$unfiltered.forEach(arg_0 -> 5.lambda$forEach$0(this.val$desiredType, consumer, arg_0));
            }

            @Override
            public Spliterator<T> spliterator() {
                return CollectSpliterators.filter(this.val$unfiltered.spliterator(), this.val$desiredType::isInstance);
            }

            private static void lambda$forEach$0(Class clazz, Consumer consumer, Object object) {
                if (clazz.isInstance(object)) {
                    consumer.accept(clazz.cast(object));
                }
            }
        };
    }

    public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }

    public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.all(iterable.iterator(), predicate);
    }

    public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.find(iterable.iterator(), predicate);
    }

    @Nullable
    public static <T> T find(Iterable<? extends T> iterable, Predicate<? super T> predicate, @Nullable T t) {
        return Iterators.find(iterable.iterator(), predicate, t);
    }

    public static <T> Optional<T> tryFind(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.tryFind(iterable.iterator(), predicate);
    }

    public static <T> int indexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.indexOf(iterable.iterator(), predicate);
    }

    public static <F, T> Iterable<T> transform(Iterable<F> iterable, Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(function);
        return new FluentIterable<T>(iterable, function){
            final Iterable val$fromIterable;
            final Function val$function;
            {
                this.val$fromIterable = iterable;
                this.val$function = function;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.transform(this.val$fromIterable.iterator(), this.val$function);
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                Preconditions.checkNotNull(consumer);
                this.val$fromIterable.forEach(arg_0 -> 6.lambda$forEach$0(consumer, this.val$function, arg_0));
            }

            @Override
            public Spliterator<T> spliterator() {
                return CollectSpliterators.map(this.val$fromIterable.spliterator(), this.val$function);
            }

            private static void lambda$forEach$0(Consumer consumer, Function function, Object object) {
                consumer.accept(function.apply(object));
            }
        };
    }

    public static <T> T get(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        return (T)(iterable instanceof List ? ((List)iterable).get(n) : Iterators.get(iterable.iterator(), n));
    }

    @Nullable
    public static <T> T get(Iterable<? extends T> iterable, int n, @Nullable T t) {
        Preconditions.checkNotNull(iterable);
        Iterators.checkNonnegative(n);
        if (iterable instanceof List) {
            List<T> list = Lists.cast(iterable);
            return n < list.size() ? list.get(n) : t;
        }
        Iterator<? extends T> iterator2 = iterable.iterator();
        Iterators.advance(iterator2, n);
        return Iterators.getNext(iterator2, t);
    }

    @Nullable
    public static <T> T getFirst(Iterable<? extends T> iterable, @Nullable T t) {
        return Iterators.getNext(iterable.iterator(), t);
    }

    public static <T> T getLast(Iterable<T> iterable) {
        if (iterable instanceof List) {
            List list = (List)iterable;
            if (list.isEmpty()) {
                throw new NoSuchElementException();
            }
            return Iterables.getLastInNonemptyList(list);
        }
        return Iterators.getLast(iterable.iterator());
    }

    @Nullable
    public static <T> T getLast(Iterable<? extends T> iterable, @Nullable T t) {
        if (iterable instanceof Collection) {
            Collection<T> collection = Collections2.cast(iterable);
            if (collection.isEmpty()) {
                return t;
            }
            if (iterable instanceof List) {
                return Iterables.getLastInNonemptyList(Lists.cast(iterable));
            }
        }
        return Iterators.getLast(iterable.iterator(), t);
    }

    private static <T> T getLastInNonemptyList(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> Iterable<T> skip(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n >= 0, "number to skip cannot be negative");
        if (iterable instanceof List) {
            List list = (List)iterable;
            return new FluentIterable<T>(list, n){
                final List val$list;
                final int val$numberToSkip;
                {
                    this.val$list = list;
                    this.val$numberToSkip = n;
                }

                @Override
                public Iterator<T> iterator() {
                    int n = Math.min(this.val$list.size(), this.val$numberToSkip);
                    return this.val$list.subList(n, this.val$list.size()).iterator();
                }
            };
        }
        return new FluentIterable<T>(iterable, n){
            final Iterable val$iterable;
            final int val$numberToSkip;
            {
                this.val$iterable = iterable;
                this.val$numberToSkip = n;
            }

            @Override
            public Iterator<T> iterator() {
                Iterator iterator2 = this.val$iterable.iterator();
                Iterators.advance(iterator2, this.val$numberToSkip);
                return new Iterator<T>(this, iterator2){
                    boolean atStart;
                    final Iterator val$iterator;
                    final 8 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$iterator = iterator2;
                        this.atStart = true;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        Object e = this.val$iterator.next();
                        this.atStart = false;
                        return e;
                    }

                    @Override
                    public void remove() {
                        CollectPreconditions.checkRemove(!this.atStart);
                        this.val$iterator.remove();
                    }
                };
            }

            @Override
            public Spliterator<T> spliterator() {
                return Streams.stream(this.val$iterable).skip(this.val$numberToSkip).spliterator();
            }
        };
    }

    public static <T> Iterable<T> limit(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n >= 0, "limit is negative");
        return new FluentIterable<T>(iterable, n){
            final Iterable val$iterable;
            final int val$limitSize;
            {
                this.val$iterable = iterable;
                this.val$limitSize = n;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.limit(this.val$iterable.iterator(), this.val$limitSize);
            }

            @Override
            public Spliterator<T> spliterator() {
                return Streams.stream(this.val$iterable).limit(this.val$limitSize).spliterator();
            }
        };
    }

    public static <T> Iterable<T> consumingIterable(Iterable<T> iterable) {
        if (iterable instanceof Queue) {
            return new FluentIterable<T>(iterable){
                final Iterable val$iterable;
                {
                    this.val$iterable = iterable;
                }

                @Override
                public Iterator<T> iterator() {
                    return new ConsumingQueueIterator((Queue)this.val$iterable);
                }

                @Override
                public String toString() {
                    return "Iterables.consumingIterable(...)";
                }
            };
        }
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(iterable){
            final Iterable val$iterable;
            {
                this.val$iterable = iterable;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.consumingIterator(this.val$iterable.iterator());
            }

            @Override
            public String toString() {
                return "Iterables.consumingIterable(...)";
            }
        };
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection)iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    @Beta
    public static <T> Iterable<T> mergeSorted(Iterable<? extends Iterable<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterables");
        Preconditions.checkNotNull(comparator, "comparator");
        FluentIterable fluentIterable = new FluentIterable<T>(iterable, comparator){
            final Iterable val$iterables;
            final Comparator val$comparator;
            {
                this.val$iterables = iterable;
                this.val$comparator = comparator;
            }

            @Override
            public Iterator<T> iterator() {
                return Iterators.mergeSorted(Iterables.transform(this.val$iterables, Iterables.toIterator()), this.val$comparator);
            }
        };
        return new UnmodifiableIterable(fluentIterable, null);
    }

    static <T> Function<Iterable<? extends T>, Iterator<? extends T>> toIterator() {
        return new Function<Iterable<? extends T>, Iterator<? extends T>>(){

            @Override
            public Iterator<? extends T> apply(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Iterable)object);
            }
        };
    }

    private static final class UnmodifiableIterable<T>
    extends FluentIterable<T> {
        private final Iterable<? extends T> iterable;

        private UnmodifiableIterable(Iterable<? extends T> iterable) {
            this.iterable = iterable;
        }

        @Override
        public Iterator<T> iterator() {
            return Iterators.unmodifiableIterator(this.iterable.iterator());
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            this.iterable.forEach(consumer);
        }

        @Override
        public Spliterator<T> spliterator() {
            return this.iterable.spliterator();
        }

        @Override
        public String toString() {
            return this.iterable.toString();
        }

        UnmodifiableIterable(Iterable iterable, 1 var2_2) {
            this((Iterable<T>)iterable);
        }
    }
}

