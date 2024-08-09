/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.IntCollections$SynchronizedCollection
 *  com.viaversion.viaversion.libs.fastutil.ints.IntCollections$UnmodifiableCollection
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterable;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class IntCollections {
    private IntCollections() {
    }

    public static IntCollection synchronize(IntCollection intCollection) {
        return new SynchronizedCollection(intCollection);
    }

    public static IntCollection synchronize(IntCollection intCollection, Object object) {
        return new SynchronizedCollection(intCollection, object);
    }

    public static IntCollection unmodifiable(IntCollection intCollection) {
        return new UnmodifiableCollection(intCollection);
    }

    public static IntCollection asCollection(IntIterable intIterable) {
        if (intIterable instanceof IntCollection) {
            return (IntCollection)intIterable;
        }
        return new IterableCollection(intIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractIntCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntIterable iterable;

        protected IterableCollection(IntIterable intIterable) {
            this.iterable = Objects.requireNonNull(intIterable);
        }

        @Override
        public int size() {
            long l = this.iterable.spliterator().getExactSizeIfKnown();
            if (l >= 0L) {
                return (int)Math.min(Integer.MAX_VALUE, l);
            }
            int n = 0;
            IntIterator intIterator = this.iterator();
            while (intIterator.hasNext()) {
                intIterator.nextInt();
                ++n;
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public IntIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        public IntSpliterator spliterator() {
            return this.iterable.spliterator();
        }

        @Override
        public IntIterator intIterator() {
            return this.iterable.intIterator();
        }

        @Override
        public IntSpliterator intSpliterator() {
            return this.iterable.intSpliterator();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    static class SizeDecreasingSupplier<C extends IntCollection>
    implements Supplier<C> {
        static final int RECOMMENDED_MIN_SIZE = 8;
        final AtomicInteger suppliedCount = new AtomicInteger(0);
        final int expectedFinalSize;
        final IntFunction<C> builder;

        SizeDecreasingSupplier(int n, IntFunction<C> intFunction) {
            this.expectedFinalSize = n;
            this.builder = intFunction;
        }

        @Override
        public C get() {
            int n = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
            if (n < 0) {
                n = 8;
            }
            return (C)((IntCollection)this.builder.apply(n));
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class EmptyCollection
    extends AbstractIntCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(int n) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            if (TArray.length > 0) {
                TArray[0] = null;
            }
            return TArray;
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void clear() {
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Collection)) {
                return true;
            }
            return ((Collection)object).isEmpty();
        }

        @Override
        @Deprecated
        public void forEach(Consumer<? super Integer> consumer) {
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override
        public boolean addAll(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean removeIf(Predicate<? super Integer> predicate) {
            Objects.requireNonNull(predicate);
            return true;
        }

        @Override
        public int[] toIntArray() {
            return IntArrays.EMPTY_ARRAY;
        }

        @Override
        @Deprecated
        public int[] toIntArray(int[] nArray) {
            return nArray;
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
        }

        @Override
        public boolean containsAll(IntCollection intCollection) {
            return intCollection.isEmpty();
        }

        @Override
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeIf(IntPredicate intPredicate) {
            Objects.requireNonNull(intPredicate);
            return true;
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

