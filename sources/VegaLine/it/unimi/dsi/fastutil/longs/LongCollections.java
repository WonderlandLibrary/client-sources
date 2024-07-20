/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;

public class LongCollections {
    private LongCollections() {
    }

    public static LongCollection synchronize(LongCollection c) {
        return new SynchronizedCollection(c);
    }

    public static LongCollection synchronize(LongCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static LongCollection unmodifiable(LongCollection c) {
        return new UnmodifiableCollection(c);
    }

    public static LongCollection asCollection(LongIterable iterable) {
        if (iterable instanceof LongCollection) {
            return (LongCollection)iterable;
        }
        return new IterableCollection(iterable);
    }

    public static class IterableCollection
    extends AbstractLongCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongIterable iterable;

        protected IterableCollection(LongIterable iterable) {
            if (iterable == null) {
                throw new NullPointerException();
            }
            this.iterable = iterable;
        }

        @Override
        public int size() {
            int c = 0;
            LongIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                ++c;
            }
            return c;
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
        @Deprecated
        public LongIterator longIterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection
    implements LongCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongCollection collection;

        protected UnmodifiableCollection(LongCollection c) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
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
        public boolean contains(long o) {
            return this.collection.contains(o);
        }

        @Override
        public LongIterator iterator() {
            return LongIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        @Deprecated
        public LongIterator longIterator() {
            return this.iterator();
        }

        @Override
        public boolean add(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.collection.toString();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override
        public long[] toLongArray() {
            return this.collection.toLongArray();
        }

        @Override
        public long[] toLongArray(long[] a) {
            return this.collection.toLongArray(a);
        }

        @Override
        public long[] toArray(long[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public boolean rem(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(LongCollection c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return this.collection.contains(k);
        }
    }

    public static class SynchronizedCollection
    implements LongCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(LongCollection c, Object sync) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = sync;
        }

        protected SynchronizedCollection(LongCollection c) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = this;
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
        public boolean contains(long o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(o);
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long[] toLongArray(long[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toLongArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long[] toArray(long[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toLongArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(LongCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(LongCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(LongCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(LongCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(a);
            }
        }

        @Override
        public LongIterator iterator() {
            return this.collection.iterator();
        }

        @Override
        @Deprecated
        public LongIterator longIterator() {
            return this.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object ok) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.remove(ok);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Long> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
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
    }

    public static abstract class EmptyCollection
    extends AbstractLongCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean add(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(long k) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public long[] toLongArray(long[] a) {
            return a;
        }

        @Override
        public long[] toLongArray() {
            return LongArrays.EMPTY_ARRAY;
        }

        @Override
        public boolean rem(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(LongCollection c) {
            return c.isEmpty();
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return LongIterators.EMPTY_ITERATOR;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Collection)) {
                return false;
            }
            return ((Collection)o).isEmpty();
        }
    }
}

