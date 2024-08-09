/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class DoubleSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private DoubleSortedSets() {
    }

    public static DoubleSortedSet singleton(double d) {
        return new Singleton(d, null);
    }

    public static DoubleSortedSet singleton(double d, DoubleComparator doubleComparator) {
        return new Singleton(d, doubleComparator);
    }

    public static DoubleSortedSet singleton(Object object) {
        return new Singleton((double)((Double)object), null);
    }

    public static DoubleSortedSet singleton(Object object, DoubleComparator doubleComparator) {
        return new Singleton((double)((Double)object), doubleComparator);
    }

    public static DoubleSortedSet synchronize(DoubleSortedSet doubleSortedSet) {
        return new SynchronizedSortedSet(doubleSortedSet);
    }

    public static DoubleSortedSet synchronize(DoubleSortedSet doubleSortedSet, Object object) {
        return new SynchronizedSortedSet(doubleSortedSet, object);
    }

    public static DoubleSortedSet unmodifiable(DoubleSortedSet doubleSortedSet) {
        return new UnmodifiableSortedSet(doubleSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends DoubleSets.UnmodifiableSet
    implements DoubleSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleSortedSet sortedSet;

        protected UnmodifiableSortedSet(DoubleSortedSet doubleSortedSet) {
            super(doubleSortedSet);
            this.sortedSet = doubleSortedSet;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(d, d2));
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(d));
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(d));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return DoubleIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            return DoubleIterators.unmodifiable(this.sortedSet.iterator(d));
        }

        @Override
        public double firstDouble() {
            return this.sortedSet.firstDouble();
        }

        @Override
        public double lastDouble() {
            return this.sortedSet.lastDouble();
        }

        @Override
        @Deprecated
        public Double first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Double last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public DoubleSortedSet subSet(Double d, Double d2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(d, d2));
        }

        @Override
        @Deprecated
        public DoubleSortedSet headSet(Double d) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(d));
        }

        @Override
        @Deprecated
        public DoubleSortedSet tailSet(Double d) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(d));
        }

        @Override
        public DoubleIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double)object, (Double)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedSet
    extends DoubleSets.SynchronizedSet
    implements DoubleSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleSortedSet sortedSet;

        protected SynchronizedSortedSet(DoubleSortedSet doubleSortedSet, Object object) {
            super(doubleSortedSet, object);
            this.sortedSet = doubleSortedSet;
        }

        protected SynchronizedSortedSet(DoubleSortedSet doubleSortedSet) {
            super(doubleSortedSet);
            this.sortedSet = doubleSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(d, d2), this.sync);
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(d), this.sync);
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(d), this.sync);
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            return this.sortedSet.iterator(d);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double firstDouble() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstDouble();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double lastDouble() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastDouble();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double first() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public DoubleSortedSet subSet(Double d, Double d2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public DoubleSortedSet headSet(Double d) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(d), this.sync);
        }

        @Override
        @Deprecated
        public DoubleSortedSet tailSet(Double d) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(d), this.sync);
        }

        @Override
        public DoubleIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double)object, (Double)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends DoubleSets.Singleton
    implements DoubleSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final DoubleComparator comparator;

        protected Singleton(double d, DoubleComparator doubleComparator) {
            super(d);
            this.comparator = doubleComparator;
        }

        private Singleton(double d) {
            this(d, (DoubleComparator)null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            DoubleBidirectionalIterator doubleBidirectionalIterator = this.iterator();
            if (this.compare(this.element, d) <= 0) {
                doubleBidirectionalIterator.nextDouble();
            }
            return doubleBidirectionalIterator;
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            if (this.compare(d, this.element) <= 0 && this.compare(this.element, d2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            if (this.compare(this.element, d) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            if (this.compare(d, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public double firstDouble() {
            return this.element;
        }

        @Override
        public double lastDouble() {
            return this.element;
        }

        @Override
        @Deprecated
        public DoubleSortedSet subSet(Double d, Double d2) {
            return this.subSet((double)d, (double)d2);
        }

        @Override
        @Deprecated
        public DoubleSortedSet headSet(Double d) {
            return this.headSet((double)d);
        }

        @Override
        @Deprecated
        public DoubleSortedSet tailSet(Double d) {
            return this.tailSet((double)d);
        }

        @Override
        @Deprecated
        public Double first() {
            return this.element;
        }

        @Override
        @Deprecated
        public Double last() {
            return this.element;
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return super.iterator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double)object, (Double)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(double d, 1 var3_2) {
            this(d);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends DoubleSets.EmptySet
    implements DoubleSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            return DoubleIterators.EMPTY_ITERATOR;
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            return EMPTY_SET;
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            return EMPTY_SET;
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            return EMPTY_SET;
        }

        @Override
        public double firstDouble() {
            throw new NoSuchElementException();
        }

        @Override
        public double lastDouble() {
            throw new NoSuchElementException();
        }

        @Override
        public DoubleComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public DoubleSortedSet subSet(Double d, Double d2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public DoubleSortedSet headSet(Double d) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public DoubleSortedSet tailSet(Double d) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Double first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Double last() {
            throw new NoSuchElementException();
        }

        @Override
        public Object clone() {
            return EMPTY_SET;
        }

        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Double)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double)object, (Double)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

