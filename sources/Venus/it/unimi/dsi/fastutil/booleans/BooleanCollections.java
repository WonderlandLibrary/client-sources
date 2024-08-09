/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public final class BooleanCollections {
    private BooleanCollections() {
    }

    public static BooleanCollection synchronize(BooleanCollection booleanCollection) {
        return new SynchronizedCollection(booleanCollection);
    }

    public static BooleanCollection synchronize(BooleanCollection booleanCollection, Object object) {
        return new SynchronizedCollection(booleanCollection, object);
    }

    public static BooleanCollection unmodifiable(BooleanCollection booleanCollection) {
        return new UnmodifiableCollection(booleanCollection);
    }

    public static BooleanCollection asCollection(BooleanIterable booleanIterable) {
        if (booleanIterable instanceof BooleanCollection) {
            return (BooleanCollection)booleanIterable;
        }
        return new IterableCollection(booleanIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractBooleanCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanIterable iterable;

        protected IterableCollection(BooleanIterable booleanIterable) {
            if (booleanIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = booleanIterable;
        }

        @Override
        public int size() {
            int n = 0;
            BooleanIterator booleanIterator = this.iterator();
            while (booleanIterator.hasNext()) {
                booleanIterator.nextBoolean();
                ++n;
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public BooleanIterator iterator() {
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
    implements BooleanCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanCollection collection;

        protected UnmodifiableCollection(BooleanCollection booleanCollection) {
            if (booleanCollection == null) {
                throw new NullPointerException();
            }
            this.collection = booleanCollection;
        }

        @Override
        public boolean add(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(boolean bl) {
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
        public boolean contains(boolean bl) {
            return this.collection.contains(bl);
        }

        @Override
        public BooleanIterator iterator() {
            return BooleanIterators.unmodifiable(this.collection.iterator());
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
        public boolean addAll(Collection<? extends Boolean> collection) {
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
        public boolean add(Boolean bl) {
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
        public boolean[] toBooleanArray() {
            return this.collection.toBooleanArray();
        }

        @Override
        @Deprecated
        public boolean[] toBooleanArray(boolean[] blArray) {
            return this.toArray(blArray);
        }

        @Override
        public boolean[] toArray(boolean[] blArray) {
            return this.collection.toArray(blArray);
        }

        @Override
        public boolean containsAll(BooleanCollection booleanCollection) {
            return this.collection.containsAll(booleanCollection);
        }

        @Override
        public boolean addAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
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
            return this.add((Boolean)object);
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
    implements BooleanCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(BooleanCollection booleanCollection, Object object) {
            if (booleanCollection == null) {
                throw new NullPointerException();
            }
            this.collection = booleanCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(BooleanCollection booleanCollection) {
            if (booleanCollection == null) {
                throw new NullPointerException();
            }
            this.collection = booleanCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(bl);
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
        public boolean[] toBooleanArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toBooleanArray();
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
        public boolean[] toBooleanArray(boolean[] blArray) {
            return this.toArray(blArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean[] toArray(boolean[] blArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(blArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(bl);
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
        public BooleanIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Boolean> collection) {
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
            return this.add((Boolean)object);
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
    extends AbstractBooleanCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(boolean bl) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public BooleanBidirectionalIterator iterator() {
            return BooleanIterators.EMPTY_ITERATOR;
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
        public boolean addAll(Collection<? extends Boolean> collection) {
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
        public boolean addAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BooleanIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

