/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections$SynchronizedCollection
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections$UnmodifiableCollection
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ObjectCollections {
    private ObjectCollections() {
    }

    public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> objectCollection) {
        return new SynchronizedCollection(objectCollection);
    }

    public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> objectCollection, Object object) {
        return new SynchronizedCollection(objectCollection, object);
    }

    public static <K> ObjectCollection<K> unmodifiable(ObjectCollection<? extends K> objectCollection) {
        return new UnmodifiableCollection(objectCollection);
    }

    public static <K> ObjectCollection<K> asCollection(ObjectIterable<K> objectIterable) {
        if (objectIterable instanceof ObjectCollection) {
            return (ObjectCollection)objectIterable;
        }
        return new IterableCollection<K>(objectIterable);
    }

    public static class IterableCollection<K>
    extends AbstractObjectCollection<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectIterable<K> iterable;

        protected IterableCollection(ObjectIterable<K> objectIterable) {
            this.iterable = Objects.requireNonNull(objectIterable);
        }

        @Override
        public int size() {
            long l = this.iterable.spliterator().getExactSizeIfKnown();
            if (l >= 0L) {
                return (int)Math.min(Integer.MAX_VALUE, l);
            }
            int n = 0;
            Iterator iterator2 = this.iterator();
            while (iterator2.hasNext()) {
                iterator2.next();
                ++n;
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public ObjectIterator<K> iterator() {
            return this.iterable.iterator();
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return this.iterable.spliterator();
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

    static class SizeDecreasingSupplier<K, C extends ObjectCollection<K>>
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
            return (C)((ObjectCollection)this.builder.apply(n));
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class EmptyCollection<K>
    extends AbstractObjectCollection<K> {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(Object object) {
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
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
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
        public void forEach(Consumer<? super K> consumer) {
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override
        public boolean addAll(Collection<? extends K> collection) {
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
        public boolean removeIf(Predicate<? super K> predicate) {
            Objects.requireNonNull(predicate);
            return true;
        }

        @Override
        public ObjectIterator iterator() {
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

