/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.LongPredicate;

public final class LongCollections {
    private LongCollections() {
    }

    public static LongCollection synchronize(LongCollection longCollection) {
        return new SynchronizedCollection(longCollection);
    }

    public static LongCollection synchronize(LongCollection longCollection, Object object) {
        return new SynchronizedCollection(longCollection, object);
    }

    public static LongCollection unmodifiable(LongCollection longCollection) {
        return new UnmodifiableCollection(longCollection);
    }

    public static LongCollection asCollection(LongIterable longIterable) {
        if (longIterable instanceof LongCollection) {
            return (LongCollection)longIterable;
        }
        return new IterableCollection(longIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractLongCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongIterable iterable;

        protected IterableCollection(LongIterable longIterable) {
            if (longIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = longIterable;
        }

        @Override
        public int size() {
            int n = 0;
            LongIterator longIterator = this.iterator();
            while (longIterator.hasNext()) {
                longIterator.nextLong();
                ++n;
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public LongIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableCollection
    implements LongCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongCollection collection;

        protected UnmodifiableCollection(LongCollection longCollection) {
            if (longCollection == null) {
                throw new NullPointerException();
            }
            this.collection = longCollection;
        }

        @Override
        public boolean add(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.collection.size();
        }

        @Override
        public boolean isEmpty() {
            return this.collection.isEmpty();
        }

        @Override
        public boolean contains(long l) {
            return this.collection.contains(l);
        }

        @Override
        public LongIterator iterator() {
            return LongIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.collection.toArray(TArray);
        }

        @Override
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override
        public boolean addAll(Collection<? extends Long> collection) {
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
        public boolean add(Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean contains(Object object) {
            return this.collection.contains(object);
        }

        @Override
        @Deprecated
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long[] toLongArray() {
            return this.collection.toLongArray();
        }

        @Override
        @Deprecated
        public long[] toLongArray(long[] lArray) {
            return this.toArray(lArray);
        }

        @Override
        public long[] toArray(long[] lArray) {
            return this.collection.toArray(lArray);
        }

        @Override
        public boolean containsAll(LongCollection longCollection) {
            return this.collection.containsAll(longCollection);
        }

        @Override
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.collection.toString();
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.collection.equals(object);
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Long)object);
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedCollection
    implements LongCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(LongCollection longCollection, Object object) {
            if (longCollection == null) {
                throw new NullPointerException();
            }
            this.collection = longCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(LongCollection longCollection) {
            if (longCollection == null) {
                throw new NullPointerException();
            }
            this.collection = longCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long[] toLongArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toLongArray();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object[] toArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray();
            }
        }

        @Override
        @Deprecated
        public long[] toLongArray(long[] lArray) {
            return this.toArray(lArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long[] toArray(long[] lArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(lArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeIf(LongPredicate longPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeIf(longPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean contains(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.contains(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.remove(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] TArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(TArray);
            }
        }

        @Override
        public LongIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Long> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.collection.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Long)object);
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class EmptyCollection
    extends AbstractLongCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(long l) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return LongIterators.EMPTY_ITERATOR;
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
        public boolean addAll(Collection<? extends Long> collection) {
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
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

