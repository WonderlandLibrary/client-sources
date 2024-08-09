/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class IntSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private IntSets() {
    }

    public static IntSet singleton(int n) {
        return new Singleton(n);
    }

    public static IntSet singleton(Integer n) {
        return new Singleton(n);
    }

    public static IntSet synchronize(IntSet intSet) {
        return new SynchronizedSet(intSet);
    }

    public static IntSet synchronize(IntSet intSet, Object object) {
        return new SynchronizedSet(intSet, object);
    }

    public static IntSet unmodifiable(IntSet intSet) {
        return new UnmodifiableSet(intSet);
    }

    public static class UnmodifiableSet
    extends IntCollections.UnmodifiableCollection
    implements IntSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(IntSet intSet) {
            super(intSet);
        }

        @Override
        public boolean remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.collection.equals(object);
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        @Deprecated
        public boolean rem(int n) {
            return super.rem(n);
        }
    }

    public static class SynchronizedSet
    extends IntCollections.SynchronizedCollection
    implements IntSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(IntSet intSet, Object object) {
            super(intSet, object);
        }

        protected SynchronizedSet(IntSet intSet) {
            super(intSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(n);
            }
        }

        @Override
        @Deprecated
        public boolean rem(int n) {
            return super.rem(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractIntSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int element;

        protected Singleton(int n) {
            this.element = n;
        }

        @Override
        public boolean contains(int n) {
            return n == this.element;
        }

        @Override
        public boolean remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntListIterator iterator() {
            return IntIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Integer> collection) {
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
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends IntCollections.EmptyCollection
    implements IntSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(int n) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof Set && ((Set)object).isEmpty();
        }

        @Override
        @Deprecated
        public boolean rem(int n) {
            return super.rem(n);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

