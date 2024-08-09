/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets$SynchronizedSortedSet
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets$UnmodifiableSortedSet
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.Spliterator;

public final class IntSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private IntSortedSets() {
    }

    public static IntSortedSet singleton(int n) {
        return new Singleton(n);
    }

    public static IntSortedSet singleton(int n, IntComparator intComparator) {
        return new Singleton(n, intComparator);
    }

    public static IntSortedSet singleton(Object object) {
        return new Singleton((Integer)object);
    }

    public static IntSortedSet singleton(Object object, IntComparator intComparator) {
        return new Singleton((Integer)object, intComparator);
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

        Singleton(int n) {
            this(n, null);
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
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element, this.comparator);
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
        public Spliterator spliterator() {
            return this.spliterator();
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

