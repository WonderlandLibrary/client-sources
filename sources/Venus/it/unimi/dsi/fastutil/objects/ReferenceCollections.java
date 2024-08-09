/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public final class ReferenceCollections {
    private ReferenceCollections() {
    }

    public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> referenceCollection) {
        return new SynchronizedCollection<K>(referenceCollection);
    }

    public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> referenceCollection, Object object) {
        return new SynchronizedCollection<K>(referenceCollection, object);
    }

    public static <K> ReferenceCollection<K> unmodifiable(ReferenceCollection<K> referenceCollection) {
        return new UnmodifiableCollection<K>(referenceCollection);
    }

    public static <K> ReferenceCollection<K> asCollection(ObjectIterable<K> objectIterable) {
        if (objectIterable instanceof ReferenceCollection) {
            return (ReferenceCollection)objectIterable;
        }
        return new IterableCollection<K>(objectIterable);
    }

    public static class IterableCollection<K>
    extends AbstractReferenceCollection<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectIterable<K> iterable;

        protected IterableCollection(ObjectIterable<K> objectIterable) {
            if (objectIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = objectIterable;
        }

        @Override
        public int size() {
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection<K>
    implements ReferenceCollection<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceCollection<K> collection;

        protected UnmodifiableCollection(ReferenceCollection<K> referenceCollection) {
            if (referenceCollection == null) {
                throw new NullPointerException();
            }
            this.collection = referenceCollection;
        }

        @Override
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object) {
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
        public boolean contains(Object object) {
            return this.collection.contains(object);
        }

        @Override
        public ObjectIterator<K> iterator() {
            return ObjectIterators.unmodifiable(this.collection.iterator());
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class SynchronizedCollection<K>
    implements ReferenceCollection<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceCollection<K> collection;
        protected final Object sync;

        protected SynchronizedCollection(ReferenceCollection<K> referenceCollection, Object object) {
            if (referenceCollection == null) {
                throw new NullPointerException();
            }
            this.collection = referenceCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(ReferenceCollection<K> referenceCollection) {
            if (referenceCollection == null) {
                throw new NullPointerException();
            }
            this.collection = referenceCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
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
        public <T> T[] toArray(T[] TArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(TArray);
            }
        }

        @Override
        public ObjectIterator<K> iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends K> collection) {
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class EmptyCollection<K>
    extends AbstractReferenceCollection<K> {
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
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
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
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

