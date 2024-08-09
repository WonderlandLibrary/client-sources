/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ConsumingQueueIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MultitransformedIterator;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.TransformedIterator;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Iterators {
    static final UnmodifiableListIterator<Object> EMPTY_LIST_ITERATOR = new UnmodifiableListIterator<Object>(){

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public Object previous() {
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return 1;
        }

        @Override
        public int previousIndex() {
            return 1;
        }
    };
    private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator<Object>(){

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(false);
        }
    };

    private Iterators() {
    }

    static <T> UnmodifiableIterator<T> emptyIterator() {
        return Iterators.emptyListIterator();
    }

    static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return EMPTY_LIST_ITERATOR;
    }

    static <T> Iterator<T> emptyModifiableIterator() {
        return EMPTY_MODIFIABLE_ITERATOR;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(Iterator<? extends T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        if (iterator2 instanceof UnmodifiableIterator) {
            UnmodifiableIterator unmodifiableIterator = (UnmodifiableIterator)iterator2;
            return unmodifiableIterator;
        }
        return new UnmodifiableIterator<T>(iterator2){
            final Iterator val$iterator;
            {
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public T next() {
                return this.val$iterator.next();
            }
        };
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static int size(Iterator<?> iterator2) {
        long l = 0L;
        while (iterator2.hasNext()) {
            iterator2.next();
            ++l;
        }
        return Ints.saturatedCast(l);
    }

    public static boolean contains(Iterator<?> iterator2, @Nullable Object object) {
        return Iterators.any(iterator2, Predicates.equalTo(object));
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterator<?> iterator2, Collection<?> collection) {
        return Iterators.removeIf(iterator2, Predicates.in(collection));
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean bl = false;
        while (iterator2.hasNext()) {
            if (!predicate.apply(iterator2.next())) continue;
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterator<?> iterator2, Collection<?> collection) {
        return Iterators.removeIf(iterator2, Predicates.not(Predicates.in(collection)));
    }

    public static boolean elementsEqual(Iterator<?> iterator2, Iterator<?> iterator3) {
        while (iterator2.hasNext()) {
            Object obj;
            if (!iterator3.hasNext()) {
                return true;
            }
            Object obj2 = iterator2.next();
            if (Objects.equal(obj2, obj = iterator3.next())) continue;
            return true;
        }
        return !iterator3.hasNext();
    }

    public static String toString(Iterator<?> iterator2) {
        return Collections2.STANDARD_JOINER.appendTo(new StringBuilder().append('['), iterator2).append(']').toString();
    }

    @CanIgnoreReturnValue
    public static <T> T getOnlyElement(Iterator<T> iterator2) {
        T t = iterator2.next();
        if (!iterator2.hasNext()) {
            return t;
        }
        StringBuilder stringBuilder = new StringBuilder().append("expected one element but was: <").append(t);
        for (int i = 0; i < 4 && iterator2.hasNext(); ++i) {
            stringBuilder.append(", ").append(iterator2.next());
        }
        if (iterator2.hasNext()) {
            stringBuilder.append(", ...");
        }
        stringBuilder.append('>');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Nullable
    @CanIgnoreReturnValue
    public static <T> T getOnlyElement(Iterator<? extends T> iterator2, @Nullable T t) {
        return iterator2.hasNext() ? Iterators.getOnlyElement(iterator2) : t;
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterator<? extends T> iterator2, Class<T> clazz) {
        ArrayList<? extends T> arrayList = Lists.newArrayList(iterator2);
        return Iterables.toArray(arrayList, clazz);
    }

    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> iterator2) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(iterator2);
        boolean bl = false;
        while (iterator2.hasNext()) {
            bl |= collection.add(iterator2.next());
        }
        return bl;
    }

    public static int frequency(Iterator<?> iterator2, @Nullable Object object) {
        return Iterators.size(Iterators.filter(iterator2, Predicates.equalTo(object)));
    }

    public static <T> Iterator<T> cycle(Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>(iterable){
            Iterator<T> iterator;
            final Iterable val$iterable;
            {
                this.val$iterable = iterable;
                this.iterator = Iterators.emptyModifiableIterator();
            }

            @Override
            public boolean hasNext() {
                return this.iterator.hasNext() || this.val$iterable.iterator().hasNext();
            }

            @Override
            public T next() {
                if (!this.iterator.hasNext()) {
                    this.iterator = this.val$iterable.iterator();
                    if (!this.iterator.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.iterator.next();
            }

            @Override
            public void remove() {
                this.iterator.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T ... TArray) {
        return Iterators.cycle(Lists.newArrayList(TArray));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        return Iterators.concat(new ConsumingQueueIterator<Iterator>(iterator2, iterator3));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3, Iterator<? extends T> iterator4) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        Preconditions.checkNotNull(iterator4);
        return Iterators.concat(new ConsumingQueueIterator<Iterator>(iterator2, iterator3, iterator4));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3, Iterator<? extends T> iterator4, Iterator<? extends T> iterator5) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        Preconditions.checkNotNull(iterator4);
        Preconditions.checkNotNull(iterator5);
        return Iterators.concat(new ConsumingQueueIterator<Iterator>(iterator2, iterator3, iterator4, iterator5));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> ... iteratorArray) {
        for (Iterator<? extends T> iterator2 : Preconditions.checkNotNull(iteratorArray)) {
            Preconditions.checkNotNull(iterator2);
        }
        return Iterators.concat(new ConsumingQueueIterator<Iterator<? extends T>>(iteratorArray));
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> iterator2) {
        return new ConcatenatedIterator(iterator2);
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator2, int n) {
        return Iterators.partitionImpl(iterator2, n, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator2, int n) {
        return Iterators.partitionImpl(iterator2, n, true);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(Iterator<T> iterator2, int n, boolean bl) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkArgument(n > 0);
        return new UnmodifiableIterator<List<T>>(iterator2, n, bl){
            final Iterator val$iterator;
            final int val$size;
            final boolean val$pad;
            {
                this.val$iterator = iterator2;
                this.val$size = n;
                this.val$pad = bl;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public List<T> next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Object[] objectArray = new Object[this.val$size];
                for (n = 0; n < this.val$size && this.val$iterator.hasNext(); ++n) {
                    objectArray[n] = this.val$iterator.next();
                }
                for (int i = n; i < this.val$size; ++i) {
                    objectArray[i] = null;
                }
                List<Object> list = Collections.unmodifiableList(Arrays.asList(objectArray));
                return this.val$pad || n == this.val$size ? list : list.subList(0, n);
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>(iterator2, predicate){
            final Iterator val$unfiltered;
            final Predicate val$retainIfTrue;
            {
                this.val$unfiltered = iterator2;
                this.val$retainIfTrue = predicate;
            }

            @Override
            protected T computeNext() {
                while (this.val$unfiltered.hasNext()) {
                    Object e = this.val$unfiltered.next();
                    if (!this.val$retainIfTrue.apply(e)) continue;
                    return e;
                }
                return this.endOfData();
            }
        };
    }

    @GwtIncompatible
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> iterator2, Class<T> clazz) {
        return Iterators.filter(iterator2, Predicates.instanceOf(clazz));
    }

    public static <T> boolean any(Iterator<T> iterator2, Predicate<? super T> predicate) {
        return Iterators.indexOf(iterator2, predicate) != -1;
    }

    public static <T> boolean all(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (predicate.apply(t)) continue;
            return true;
        }
        return false;
    }

    public static <T> T find(Iterator<T> iterator2, Predicate<? super T> predicate) {
        return (T)Iterators.filter(iterator2, predicate).next();
    }

    @Nullable
    public static <T> T find(Iterator<? extends T> iterator2, Predicate<? super T> predicate, @Nullable T t) {
        return Iterators.getNext(Iterators.filter(iterator2, predicate), t);
    }

    public static <T> Optional<T> tryFind(Iterator<T> iterator2, Predicate<? super T> predicate) {
        UnmodifiableIterator<T> unmodifiableIterator = Iterators.filter(iterator2, predicate);
        return unmodifiableIterator.hasNext() ? Optional.of(unmodifiableIterator.next()) : Optional.absent();
    }

    public static <T> int indexOf(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int n = 0;
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (predicate.apply(t)) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static <F, T> Iterator<T> transform(Iterator<F> iterator2, Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(iterator2, function){
            final Function val$function;
            {
                this.val$function = function;
                super(iterator2);
            }

            @Override
            T transform(F f) {
                return this.val$function.apply(f);
            }
        };
    }

    public static <T> T get(Iterator<T> iterator2, int n) {
        Iterators.checkNonnegative(n);
        int n2 = Iterators.advance(iterator2, n);
        if (!iterator2.hasNext()) {
            throw new IndexOutOfBoundsException("position (" + n + ") must be less than the number of elements that remained (" + n2 + ")");
        }
        return iterator2.next();
    }

    static void checkNonnegative(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("position (" + n + ") must not be negative");
        }
    }

    @Nullable
    public static <T> T get(Iterator<? extends T> iterator2, int n, @Nullable T t) {
        Iterators.checkNonnegative(n);
        Iterators.advance(iterator2, n);
        return Iterators.getNext(iterator2, t);
    }

    @Nullable
    public static <T> T getNext(Iterator<? extends T> iterator2, @Nullable T t) {
        return iterator2.hasNext() ? iterator2.next() : t;
    }

    public static <T> T getLast(Iterator<T> iterator2) {
        T t;
        do {
            t = iterator2.next();
        } while (iterator2.hasNext());
        return t;
    }

    @Nullable
    public static <T> T getLast(Iterator<? extends T> iterator2, @Nullable T t) {
        return iterator2.hasNext() ? Iterators.getLast(iterator2) : t;
    }

    @CanIgnoreReturnValue
    public static int advance(Iterator<?> iterator2, int n) {
        int n2;
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkArgument(n >= 0, "numberToAdvance must be nonnegative");
        for (n2 = 0; n2 < n && iterator2.hasNext(); ++n2) {
            iterator2.next();
        }
        return n2;
    }

    public static <T> Iterator<T> limit(Iterator<T> iterator2, int n) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkArgument(n >= 0, "limit is negative");
        return new Iterator<T>(n, iterator2){
            private int count;
            final int val$limitSize;
            final Iterator val$iterator;
            {
                this.val$limitSize = n;
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.count < this.val$limitSize && this.val$iterator.hasNext();
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.count;
                return this.val$iterator.next();
            }

            @Override
            public void remove() {
                this.val$iterator.remove();
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(Iterator<T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        return new UnmodifiableIterator<T>(iterator2){
            final Iterator val$iterator;
            {
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public T next() {
                Object e = this.val$iterator.next();
                this.val$iterator.remove();
                return e;
            }

            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }

    @Nullable
    static <T> T pollNext(Iterator<T> iterator2) {
        if (iterator2.hasNext()) {
            T t = iterator2.next();
            iterator2.remove();
            return t;
        }
        return null;
    }

    static void clear(Iterator<?> iterator2) {
        Preconditions.checkNotNull(iterator2);
        while (iterator2.hasNext()) {
            iterator2.next();
            iterator2.remove();
        }
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T ... TArray) {
        return Iterators.forArray(TArray, 0, TArray.length, 0);
    }

    static <T> UnmodifiableListIterator<T> forArray(T[] TArray, int n, int n2, int n3) {
        Preconditions.checkArgument(n2 >= 0);
        int n4 = n + n2;
        Preconditions.checkPositionIndexes(n, n4, TArray.length);
        Preconditions.checkPositionIndex(n3, n2);
        if (n2 == 0) {
            return Iterators.emptyListIterator();
        }
        return new AbstractIndexedListIterator<T>(n2, n3, TArray, n){
            final Object[] val$array;
            final int val$offset;
            {
                this.val$array = objectArray;
                this.val$offset = n3;
                super(n, n2);
            }

            @Override
            protected T get(int n) {
                return this.val$array[this.val$offset + n];
            }
        };
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable T t) {
        return new UnmodifiableIterator<T>(t){
            boolean done;
            final Object val$value;
            {
                this.val$value = object;
            }

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public T next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return this.val$value;
            }
        };
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>(enumeration){
            final Enumeration val$enumeration;
            {
                this.val$enumeration = enumeration;
            }

            @Override
            public boolean hasNext() {
                return this.val$enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return this.val$enumeration.nextElement();
            }
        };
    }

    public static <T> Enumeration<T> asEnumeration(Iterator<T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        return new Enumeration<T>(iterator2){
            final Iterator val$iterator;
            {
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasMoreElements() {
                return this.val$iterator.hasNext();
            }

            @Override
            public T nextElement() {
                return this.val$iterator.next();
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator2) {
        if (iterator2 instanceof PeekingImpl) {
            PeekingImpl peekingImpl = (PeekingImpl)iterator2;
            return peekingImpl;
        }
        return new PeekingImpl<T>(iterator2);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return Preconditions.checkNotNull(peekingIterator);
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator<T>(iterable, comparator);
    }

    static <T> ListIterator<T> cast(Iterator<T> iterator2) {
        return (ListIterator)iterator2;
    }

    private static class ConcatenatedIterator<T>
    extends MultitransformedIterator<Iterator<? extends T>, T> {
        public ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> iterator2) {
            super(ConcatenatedIterator.getComponentIterators(iterator2));
        }

        @Override
        Iterator<? extends T> transform(Iterator<? extends T> iterator2) {
            return iterator2;
        }

        private static <T> Iterator<Iterator<? extends T>> getComponentIterators(Iterator<? extends Iterator<? extends T>> iterator2) {
            return new MultitransformedIterator<Iterator<? extends T>, Iterator<? extends T>>(iterator2){

                @Override
                Iterator<? extends Iterator<? extends T>> transform(Iterator<? extends T> iterator2) {
                    if (iterator2 instanceof ConcatenatedIterator) {
                        ConcatenatedIterator concatenatedIterator = (ConcatenatedIterator)iterator2;
                        return ConcatenatedIterator.access$000(concatenatedIterator.backingIterator);
                    }
                    return Iterators.singletonIterator(iterator2);
                }

                @Override
                Iterator transform(Object object) {
                    return this.transform((Iterator)object);
                }
            };
        }

        @Override
        Iterator transform(Object object) {
            return this.transform((Iterator)object);
        }

        static Iterator access$000(Iterator iterator2) {
            return ConcatenatedIterator.getComponentIterators(iterator2);
        }
    }

    private static class MergingIterator<T>
    extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> queue;

        public MergingIterator(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
            Comparator comparator2 = new Comparator<PeekingIterator<T>>(this, comparator){
                final Comparator val$itemComparator;
                final MergingIterator this$0;
                {
                    this.this$0 = mergingIterator;
                    this.val$itemComparator = comparator;
                }

                @Override
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return this.val$itemComparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }

                @Override
                public int compare(Object object, Object object2) {
                    return this.compare((PeekingIterator)object, (PeekingIterator)object2);
                }
            };
            this.queue = new PriorityQueue<PeekingIterator<T>>(2, comparator2);
            for (Iterator<T> iterator2 : iterable) {
                if (!iterator2.hasNext()) continue;
                this.queue.add(Iterators.peekingIterator(iterator2));
            }
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public T next() {
            PeekingIterator<T> peekingIterator = this.queue.remove();
            T t = peekingIterator.next();
            if (peekingIterator.hasNext()) {
                this.queue.add(peekingIterator);
            }
            return t;
        }
    }

    private static class PeekingImpl<E>
    implements PeekingIterator<E> {
        private final Iterator<? extends E> iterator;
        private boolean hasPeeked;
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> iterator2) {
            this.iterator = Preconditions.checkNotNull(iterator2);
        }

        @Override
        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }

        @Override
        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E e = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return e;
        }

        @Override
        public void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }

        @Override
        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }
}

