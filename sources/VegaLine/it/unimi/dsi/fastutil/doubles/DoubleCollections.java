/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;

public class DoubleCollections {
    private DoubleCollections() {
    }

    public static DoubleCollection synchronize(DoubleCollection c) {
        return new SynchronizedCollection(c);
    }

    public static DoubleCollection synchronize(DoubleCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static DoubleCollection unmodifiable(DoubleCollection c) {
        return new UnmodifiableCollection(c);
    }

    public static DoubleCollection asCollection(DoubleIterable iterable) {
        if (iterable instanceof DoubleCollection) {
            return (DoubleCollection)iterable;
        }
        return new IterableCollection(iterable);
    }

    public static class IterableCollection
    extends AbstractDoubleCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleIterable iterable;

        protected IterableCollection(DoubleIterable iterable) {
            if (iterable == null) {
                throw new NullPointerException();
            }
            this.iterable = iterable;
        }

        @Override
        public int size() {
            int c = 0;
            DoubleIterator iterator = this.iterator();
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
        public DoubleIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        @Deprecated
        public DoubleIterator doubleIterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection
    implements DoubleCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleCollection collection;

        protected UnmodifiableCollection(DoubleCollection c) {
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
        public boolean contains(double o) {
            return this.collection.contains(o);
        }

        @Override
        public DoubleIterator iterator() {
            return DoubleIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        @Deprecated
        public DoubleIterator doubleIterator() {
            return this.iterator();
        }

        @Override
        public boolean add(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Double> c) {
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
        public double[] toDoubleArray() {
            return this.collection.toDoubleArray();
        }

        @Override
        public double[] toDoubleArray(double[] a) {
            return this.collection.toDoubleArray(a);
        }

        @Override
        public double[] toArray(double[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public boolean rem(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(DoubleCollection c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return this.collection.contains(k);
        }
    }

    public static class SynchronizedCollection
    implements DoubleCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(DoubleCollection c, Object sync) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = sync;
        }

        protected SynchronizedCollection(DoubleCollection c) {
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
        public boolean contains(double o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(o);
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double[] toDoubleArray(double[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toDoubleArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double[] toArray(double[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toDoubleArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(DoubleCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(DoubleCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(DoubleCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(DoubleCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Double k) {
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
        public DoubleIterator iterator() {
            return this.collection.iterator();
        }

        @Override
        @Deprecated
        public DoubleIterator doubleIterator() {
            return this.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(double k) {
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
        public boolean addAll(Collection<? extends Double> c) {
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
    extends AbstractDoubleCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean add(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(double k) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public double[] toDoubleArray(double[] a) {
            return a;
        }

        @Override
        public double[] toDoubleArray() {
            return DoubleArrays.EMPTY_ARRAY;
        }

        @Override
        public boolean rem(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(DoubleCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(DoubleCollection c) {
            return c.isEmpty();
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return DoubleIterators.EMPTY_ITERATOR;
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

