/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.DoublePredicate;

public final class FloatCollections {
    private FloatCollections() {
    }

    public static FloatCollection synchronize(FloatCollection floatCollection) {
        return new SynchronizedCollection(floatCollection);
    }

    public static FloatCollection synchronize(FloatCollection floatCollection, Object object) {
        return new SynchronizedCollection(floatCollection, object);
    }

    public static FloatCollection unmodifiable(FloatCollection floatCollection) {
        return new UnmodifiableCollection(floatCollection);
    }

    public static FloatCollection asCollection(FloatIterable floatIterable) {
        if (floatIterable instanceof FloatCollection) {
            return (FloatCollection)floatIterable;
        }
        return new IterableCollection(floatIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractFloatCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatIterable iterable;

        protected IterableCollection(FloatIterable floatIterable) {
            if (floatIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = floatIterable;
        }

        @Override
        public int size() {
            int n = 0;
            FloatIterator floatIterator = this.iterator();
            while (floatIterator.hasNext()) {
                floatIterator.nextFloat();
                ++n;
            }
            return n;
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableCollection
    implements FloatCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatCollection collection;

        protected UnmodifiableCollection(FloatCollection floatCollection) {
            if (floatCollection == null) {
                throw new NullPointerException();
            }
            this.collection = floatCollection;
        }

        @Override
        public boolean add(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(float f) {
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
        public boolean contains(float f) {
            return this.collection.contains(f);
        }

        @Override
        public FloatIterator iterator() {
            return FloatIterators.unmodifiable(this.collection.iterator());
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
        public boolean addAll(Collection<? extends Float> collection) {
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
        public boolean add(Float f) {
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
        public float[] toFloatArray() {
            return this.collection.toFloatArray();
        }

        @Override
        @Deprecated
        public float[] toFloatArray(float[] fArray) {
            return this.toArray(fArray);
        }

        @Override
        public float[] toArray(float[] fArray) {
            return this.collection.toArray(fArray);
        }

        @Override
        public boolean containsAll(FloatCollection floatCollection) {
            return this.collection.containsAll(floatCollection);
        }

        @Override
        public boolean addAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(FloatCollection floatCollection) {
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
            return this.add((Float)object);
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
    implements FloatCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(FloatCollection floatCollection, Object object) {
            if (floatCollection == null) {
                throw new NullPointerException();
            }
            this.collection = floatCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(FloatCollection floatCollection) {
            if (floatCollection == null) {
                throw new NullPointerException();
            }
            this.collection = floatCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(f);
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

        @Override
        @Deprecated
        public float[] toFloatArray(float[] fArray) {
            return this.toArray(fArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float[] toArray(float[] fArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(fArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(floatCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(floatCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(floatCollection);
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
        public boolean retainAll(FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(floatCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(f);
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
        public FloatIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Float> collection) {
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
            return this.add((Float)object);
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
    extends AbstractFloatCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(float f) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return FloatIterators.EMPTY_ITERATOR;
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
        public boolean addAll(Collection<? extends Float> collection) {
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
        public boolean addAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FloatIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

