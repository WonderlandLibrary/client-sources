/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.DoublePredicate;

public final class DoubleCollections {
    private DoubleCollections() {
    }

    public static DoubleCollection synchronize(DoubleCollection doubleCollection) {
        return new SynchronizedCollection(doubleCollection);
    }

    public static DoubleCollection synchronize(DoubleCollection doubleCollection, Object object) {
        return new SynchronizedCollection(doubleCollection, object);
    }

    public static DoubleCollection unmodifiable(DoubleCollection doubleCollection) {
        return new UnmodifiableCollection(doubleCollection);
    }

    public static DoubleCollection asCollection(DoubleIterable doubleIterable) {
        if (doubleIterable instanceof DoubleCollection) {
            return (DoubleCollection)doubleIterable;
        }
        return new IterableCollection(doubleIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractDoubleCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleIterable iterable;

        protected IterableCollection(DoubleIterable doubleIterable) {
            if (doubleIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = doubleIterable;
        }

        @Override
        public int size() {
            int n = 0;
            DoubleIterator doubleIterator = this.iterator();
            while (doubleIterator.hasNext()) {
                doubleIterator.nextDouble();
                ++n;
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public DoubleIterator iterator() {
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
    implements DoubleCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleCollection collection;

        protected UnmodifiableCollection(DoubleCollection doubleCollection) {
            if (doubleCollection == null) {
                throw new NullPointerException();
            }
            this.collection = doubleCollection;
        }

        @Override
        public boolean add(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(double d) {
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
        public boolean contains(double d) {
            return this.collection.contains(d);
        }

        @Override
        public DoubleIterator iterator() {
            return DoubleIterators.unmodifiable(this.collection.iterator());
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
        public boolean addAll(Collection<? extends Double> collection) {
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
        public boolean add(Double d) {
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
        public double[] toDoubleArray() {
            return this.collection.toDoubleArray();
        }

        @Override
        @Deprecated
        public double[] toDoubleArray(double[] dArray) {
            return this.toArray(dArray);
        }

        @Override
        public double[] toArray(double[] dArray) {
            return this.collection.toArray(dArray);
        }

        @Override
        public boolean containsAll(DoubleCollection doubleCollection) {
            return this.collection.containsAll(doubleCollection);
        }

        @Override
        public boolean addAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(DoubleCollection doubleCollection) {
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
            return this.add((Double)object);
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
    implements DoubleCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(DoubleCollection doubleCollection, Object object) {
            if (doubleCollection == null) {
                throw new NullPointerException();
            }
            this.collection = doubleCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(DoubleCollection doubleCollection) {
            if (doubleCollection == null) {
                throw new NullPointerException();
            }
            this.collection = doubleCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(d);
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
        public double[] toDoubleArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toDoubleArray();
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
        public double[] toDoubleArray(double[] dArray) {
            return this.toArray(dArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double[] toArray(double[] dArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(dArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeIf(DoublePredicate doublePredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeIf(doublePredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(d);
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
        public DoubleIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Double> collection) {
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
            return this.add((Double)object);
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
    extends AbstractDoubleCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(double d) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return DoubleIterators.EMPTY_ITERATOR;
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
        public boolean addAll(Collection<? extends Double> collection) {
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
        public boolean addAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public DoubleIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

