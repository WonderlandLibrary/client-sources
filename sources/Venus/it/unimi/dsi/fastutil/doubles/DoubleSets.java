/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class DoubleSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private DoubleSets() {
    }

    public static DoubleSet singleton(double d) {
        return new Singleton(d);
    }

    public static DoubleSet singleton(Double d) {
        return new Singleton(d);
    }

    public static DoubleSet synchronize(DoubleSet doubleSet) {
        return new SynchronizedSet(doubleSet);
    }

    public static DoubleSet synchronize(DoubleSet doubleSet, Object object) {
        return new SynchronizedSet(doubleSet, object);
    }

    public static DoubleSet unmodifiable(DoubleSet doubleSet) {
        return new UnmodifiableSet(doubleSet);
    }

    public static class UnmodifiableSet
    extends DoubleCollections.UnmodifiableCollection
    implements DoubleSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(DoubleSet doubleSet) {
            super(doubleSet);
        }

        @Override
        public boolean remove(double d) {
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
        public boolean rem(double d) {
            return super.rem(d);
        }
    }

    public static class SynchronizedSet
    extends DoubleCollections.SynchronizedCollection
    implements DoubleSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(DoubleSet doubleSet, Object object) {
            super(doubleSet, object);
        }

        protected SynchronizedSet(DoubleSet doubleSet) {
            super(doubleSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(d);
            }
        }

        @Override
        @Deprecated
        public boolean rem(double d) {
            return super.rem(d);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractDoubleSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double element;

        protected Singleton(double d) {
            this.element = d;
        }

        @Override
        public boolean contains(double d) {
            return Double.doubleToLongBits(d) == Double.doubleToLongBits(this.element);
        }

        @Override
        public boolean remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public DoubleListIterator iterator() {
            return DoubleIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
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

        public Object clone() {
            return this;
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

    public static class EmptySet
    extends DoubleCollections.EmptyCollection
    implements DoubleSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(double d) {
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
        public boolean rem(double d) {
            return super.rem(d);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

