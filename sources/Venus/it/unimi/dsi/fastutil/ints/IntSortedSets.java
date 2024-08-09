/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class IntSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private IntSortedSets() {
    }

    public static IntSortedSet singleton(int n) {
        return new Singleton(n, null);
    }

    public static IntSortedSet singleton(int n, IntComparator intComparator) {
        return new Singleton(n, intComparator);
    }

    public static IntSortedSet singleton(Object object) {
        return new Singleton((int)((Integer)object), null);
    }

    public static IntSortedSet singleton(Object object, IntComparator intComparator) {
        return new Singleton((int)((Integer)object), intComparator);
    }

    public static IntSortedSet synchronize(IntSortedSet intSortedSet) {
        return new SynchronizedSortedSet(intSortedSet);
    }

    public static IntSortedSet synchronize(IntSortedSet intSortedSet, Object object) {
        return new SynchronizedSortedSet(intSortedSet, object);
    }

    public static IntSortedSet unmodifiable(IntSortedSet intSortedSet) {
        return new UnmodifiableSortedSet(intSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends IntSets.UnmodifiableSet
    implements IntSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntSortedSet sortedSet;

        protected UnmodifiableSortedSet(IntSortedSet intSortedSet) {
            super(intSortedSet);
            this.sortedSet = intSortedSet;
        }

        @Override
        public IntComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(n, n2));
        }

        @Override
        public IntSortedSet headSet(int n) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(n));
        }

        @Override
        public IntSortedSet tailSet(int n) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(n));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return IntIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return IntIterators.unmodifiable(this.sortedSet.iterator(n));
        }

        @Override
        public int firstInt() {
            return this.sortedSet.firstInt();
        }

        @Override
        public int lastInt() {
            return this.sortedSet.lastInt();
        }

        @Override
        @Deprecated
        public Integer first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Integer last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public IntSortedSet subSet(Integer n, Integer n2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(n, n2));
        }

        @Override
        @Deprecated
        public IntSortedSet headSet(Integer n) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(n));
        }

        @Override
        @Deprecated
        public IntSortedSet tailSet(Integer n) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(n));
        }

        @Override
        public IntIterator iterator() {
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
            return this.tailSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Integer)object, (Integer)object2);
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
    extends IntSets.SynchronizedSet
    implements IntSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntSortedSet sortedSet;

        protected SynchronizedSortedSet(IntSortedSet intSortedSet, Object object) {
            super(intSortedSet, object);
            this.sortedSet = intSortedSet;
        }

        protected SynchronizedSortedSet(IntSortedSet intSortedSet) {
            super(intSortedSet);
            this.sortedSet = intSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IntComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(n, n2), this.sync);
        }

        @Override
        public IntSortedSet headSet(int n) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(n), this.sync);
        }

        @Override
        public IntSortedSet tailSet(int n) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(n), this.sync);
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return this.sortedSet.iterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int firstInt() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstInt();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastInt() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastInt();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer first() {
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
        public Integer last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public IntSortedSet subSet(Integer n, Integer n2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(n, n2), this.sync);
        }

        @Override
        @Deprecated
        public IntSortedSet headSet(Integer n) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(n), this.sync);
        }

        @Override
        @Deprecated
        public IntSortedSet tailSet(Integer n) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(n), this.sync);
        }

        @Override
        public IntIterator iterator() {
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
            return this.tailSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Integer)object, (Integer)object2);
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
    extends IntSets.Singleton
    implements IntSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final IntComparator comparator;

        protected Singleton(int n, IntComparator intComparator) {
            super(n);
            this.comparator = intComparator;
        }

        private Singleton(int n) {
            this(n, (IntComparator)null);
        }

        final int compare(int n, int n2) {
            return this.comparator == null ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            IntBidirectionalIterator intBidirectionalIterator = this.iterator();
            if (this.compare(this.element, n) <= 0) {
                intBidirectionalIterator.nextInt();
            }
            return intBidirectionalIterator;
        }

        @Override
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            if (this.compare(n, this.element) <= 0 && this.compare(this.element, n2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public IntSortedSet headSet(int n) {
            if (this.compare(this.element, n) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public IntSortedSet tailSet(int n) {
            if (this.compare(n, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public int firstInt() {
            return this.element;
        }

        @Override
        public int lastInt() {
            return this.element;
        }

        @Override
        @Deprecated
        public IntSortedSet subSet(Integer n, Integer n2) {
            return this.subSet((int)n, (int)n2);
        }

        @Override
        @Deprecated
        public IntSortedSet headSet(Integer n) {
            return this.headSet((int)n);
        }

        @Override
        @Deprecated
        public IntSortedSet tailSet(Integer n) {
            return this.tailSet((int)n);
        }

        @Override
        @Deprecated
        public Integer first() {
            return this.element;
        }

        @Override
        @Deprecated
        public Integer last() {
            return this.element;
        }

        @Override
        public IntBidirectionalIterator iterator() {
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
            return this.tailSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Integer)object, (Integer)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(int n, 1 var2_2) {
            this(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends IntSets.EmptySet
    implements IntSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            return EMPTY_SET;
        }

        @Override
        public IntSortedSet headSet(int n) {
            return EMPTY_SET;
        }

        @Override
        public IntSortedSet tailSet(int n) {
            return EMPTY_SET;
        }

        @Override
        public int firstInt() {
            throw new NoSuchElementException();
        }

        @Override
        public int lastInt() {
            throw new NoSuchElementException();
        }

        @Override
        public IntComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public IntSortedSet subSet(Integer n, Integer n2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public IntSortedSet headSet(Integer n) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public IntSortedSet tailSet(Integer n) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Integer first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Integer last() {
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
            return this.tailSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Integer)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Integer)object, (Integer)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

