/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;

public class ByteCollections {
    private ByteCollections() {
    }

    public static ByteCollection synchronize(ByteCollection c) {
        return new SynchronizedCollection(c);
    }

    public static ByteCollection synchronize(ByteCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static ByteCollection unmodifiable(ByteCollection c) {
        return new UnmodifiableCollection(c);
    }

    public static ByteCollection asCollection(ByteIterable iterable) {
        if (iterable instanceof ByteCollection) {
            return (ByteCollection)iterable;
        }
        return new IterableCollection(iterable);
    }

    public static class IterableCollection
    extends AbstractByteCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteIterable iterable;

        protected IterableCollection(ByteIterable iterable) {
            if (iterable == null) {
                throw new NullPointerException();
            }
            this.iterable = iterable;
        }

        @Override
        public int size() {
            int c = 0;
            ByteIterator iterator = this.iterator();
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
        public ByteIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        @Deprecated
        public ByteIterator byteIterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection
    implements ByteCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteCollection collection;

        protected UnmodifiableCollection(ByteCollection c) {
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
        public boolean contains(byte o) {
            return this.collection.contains(o);
        }

        @Override
        public ByteIterator iterator() {
            return ByteIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        @Deprecated
        public ByteIterator byteIterator() {
            return this.iterator();
        }

        @Override
        public boolean add(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Byte> c) {
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
        public byte[] toByteArray() {
            return this.collection.toByteArray();
        }

        @Override
        public byte[] toByteArray(byte[] a) {
            return this.collection.toByteArray(a);
        }

        @Override
        public byte[] toArray(byte[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public boolean rem(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(ByteCollection c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return this.collection.contains(k);
        }
    }

    public static class SynchronizedCollection
    implements ByteCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(ByteCollection c, Object sync) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = sync;
        }

        protected SynchronizedCollection(ByteCollection c) {
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
        public boolean contains(byte o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte[] toByteArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toByteArray();
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
        public byte[] toByteArray(byte[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toByteArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte[] toArray(byte[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toByteArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Byte k) {
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
        public ByteIterator iterator() {
            return this.collection.iterator();
        }

        @Override
        @Deprecated
        public ByteIterator byteIterator() {
            return this.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(byte k) {
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
        public boolean addAll(Collection<? extends Byte> c) {
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
    extends AbstractByteCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean add(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(byte k) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public byte[] toByteArray(byte[] a) {
            return a;
        }

        @Override
        public byte[] toByteArray() {
            return ByteArrays.EMPTY_ARRAY;
        }

        @Override
        public boolean rem(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(ByteCollection c) {
            return c.isEmpty();
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return ByteIterators.EMPTY_ITERATOR;
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

