/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;

public class FloatCollections {
    private FloatCollections() {
    }

    public static FloatCollection synchronize(FloatCollection c) {
        return new SynchronizedCollection(c);
    }

    public static FloatCollection synchronize(FloatCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static FloatCollection unmodifiable(FloatCollection c) {
        return new UnmodifiableCollection(c);
    }

    public static FloatCollection asCollection(FloatIterable iterable) {
        if (iterable instanceof FloatCollection) {
            return (FloatCollection)iterable;
        }
        return new IterableCollection(iterable);
    }

    public static class IterableCollection
    extends AbstractFloatCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatIterable iterable;

        protected IterableCollection(FloatIterable iterable) {
            if (iterable == null) {
                throw new NullPointerException();
            }
            this.iterable = iterable;
        }

        @Override
        public int size() {
            int c = 0;
            FloatIterator iterator = this.iterator();
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
        public FloatIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        @Deprecated
        public FloatIterator floatIterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection
    implements FloatCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatCollection collection;

        protected UnmodifiableCollection(FloatCollection c) {
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
        public boolean contains(float o) {
            return this.collection.contains(o);
        }

        @Override
        public FloatIterator iterator() {
            return FloatIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        @Deprecated
        public FloatIterator floatIterator() {
            return this.iterator();
        }

        @Override
        public boolean add(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Float> c) {
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
        public float[] toFloatArray() {
            return this.collection.toFloatArray();
        }

        @Override
        public float[] toFloatArray(float[] a) {
            return this.collection.toFloatArray(a);
        }

        @Override
        public float[] toArray(float[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public boolean rem(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(FloatCollection c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return this.collection.contains(k);
        }
    }

    public static class SynchronizedCollection
    implements FloatCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(FloatCollection c, Object sync) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = sync;
        }

        protected SynchronizedCollection(FloatCollection c) {
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
        public boolean contains(float o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float[] toFloatArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toFloatArray();
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
        public float[] toFloatArray(float[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toFloatArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float[] toArray(float[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toFloatArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(FloatCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(FloatCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(FloatCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(FloatCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Float k) {
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
        public FloatIterator iterator() {
            return this.collection.iterator();
        }

        @Override
        @Deprecated
        public FloatIterator floatIterator() {
            return this.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(float k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(float k) {
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
        public boolean addAll(Collection<? extends Float> c) {
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
    extends AbstractFloatCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean add(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(float k) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public float[] toFloatArray(float[] a) {
            return a;
        }

        @Override
        public float[] toFloatArray() {
            return FloatArrays.EMPTY_ARRAY;
        }

        @Override
        public boolean rem(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(FloatCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(FloatCollection c) {
            return c.isEmpty();
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return FloatIterators.EMPTY_ITERATOR;
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

