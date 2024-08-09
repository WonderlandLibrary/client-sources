/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.IntPredicate;

public final class ByteCollections {
    private ByteCollections() {
    }

    public static ByteCollection synchronize(ByteCollection byteCollection) {
        return new SynchronizedCollection(byteCollection);
    }

    public static ByteCollection synchronize(ByteCollection byteCollection, Object object) {
        return new SynchronizedCollection(byteCollection, object);
    }

    public static ByteCollection unmodifiable(ByteCollection byteCollection) {
        return new UnmodifiableCollection(byteCollection);
    }

    public static ByteCollection asCollection(ByteIterable byteIterable) {
        if (byteIterable instanceof ByteCollection) {
            return (ByteCollection)byteIterable;
        }
        return new IterableCollection(byteIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractByteCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteIterable iterable;

        protected IterableCollection(ByteIterable byteIterable) {
            if (byteIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = byteIterable;
        }

        @Override
        public int size() {
            int n = 0;
            ByteIterator byteIterator = this.iterator();
            while (byteIterator.hasNext()) {
                byteIterator.nextByte();
                ++n;
            }
            return n;
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableCollection
    implements ByteCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteCollection collection;

        protected UnmodifiableCollection(ByteCollection byteCollection) {
            if (byteCollection == null) {
                throw new NullPointerException();
            }
            this.collection = byteCollection;
        }

        @Override
        public boolean add(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(byte by) {
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
        public boolean contains(byte by) {
            return this.collection.contains(by);
        }

        @Override
        public ByteIterator iterator() {
            return ByteIterators.unmodifiable(this.collection.iterator());
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
        public boolean addAll(Collection<? extends Byte> collection) {
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
        public boolean add(Byte by) {
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
        public byte[] toByteArray() {
            return this.collection.toByteArray();
        }

        @Override
        @Deprecated
        public byte[] toByteArray(byte[] byArray) {
            return this.toArray(byArray);
        }

        @Override
        public byte[] toArray(byte[] byArray) {
            return this.collection.toArray(byArray);
        }

        @Override
        public boolean containsAll(ByteCollection byteCollection) {
            return this.collection.containsAll(byteCollection);
        }

        @Override
        public boolean addAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection byteCollection) {
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
            return this.add((Byte)object);
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
    implements ByteCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(ByteCollection byteCollection, Object object) {
            if (byteCollection == null) {
                throw new NullPointerException();
            }
            this.collection = byteCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(ByteCollection byteCollection) {
            if (byteCollection == null) {
                throw new NullPointerException();
            }
            this.collection = byteCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(by);
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

        @Override
        @Deprecated
        public byte[] toByteArray(byte[] byArray) {
            return this.toArray(byArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte[] toArray(byte[] byArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(byArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeIf(IntPredicate intPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeIf(intPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(by);
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
        public ByteIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Byte> collection) {
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
            return this.add((Byte)object);
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
    extends AbstractByteCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(byte by) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return ByteIterators.EMPTY_ITERATOR;
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
        public boolean addAll(Collection<? extends Byte> collection) {
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
        public boolean addAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

