/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import it.unimi.dsi.fastutil.shorts.ShortSets;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class ShortSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ShortSortedSets() {
    }

    public static ShortSortedSet singleton(short s) {
        return new Singleton(s, null);
    }

    public static ShortSortedSet singleton(short s, ShortComparator shortComparator) {
        return new Singleton(s, shortComparator);
    }

    public static ShortSortedSet singleton(Object object) {
        return new Singleton((short)((Short)object), null);
    }

    public static ShortSortedSet singleton(Object object, ShortComparator shortComparator) {
        return new Singleton((short)((Short)object), shortComparator);
    }

    public static ShortSortedSet synchronize(ShortSortedSet shortSortedSet) {
        return new SynchronizedSortedSet(shortSortedSet);
    }

    public static ShortSortedSet synchronize(ShortSortedSet shortSortedSet, Object object) {
        return new SynchronizedSortedSet(shortSortedSet, object);
    }

    public static ShortSortedSet unmodifiable(ShortSortedSet shortSortedSet) {
        return new UnmodifiableSortedSet(shortSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends ShortSets.UnmodifiableSet
    implements ShortSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortSortedSet sortedSet;

        protected UnmodifiableSortedSet(ShortSortedSet shortSortedSet) {
            super(shortSortedSet);
            this.sortedSet = shortSortedSet;
        }

        @Override
        public ShortComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public ShortSortedSet subSet(short s, short s2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(s, s2));
        }

        @Override
        public ShortSortedSet headSet(short s) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(s));
        }

        @Override
        public ShortSortedSet tailSet(short s) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(s));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return ShortIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public ShortBidirectionalIterator iterator(short s) {
            return ShortIterators.unmodifiable(this.sortedSet.iterator(s));
        }

        @Override
        public short firstShort() {
            return this.sortedSet.firstShort();
        }

        @Override
        public short lastShort() {
            return this.sortedSet.lastShort();
        }

        @Override
        @Deprecated
        public Short first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Short last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public ShortSortedSet subSet(Short s, Short s2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(s, s2));
        }

        @Override
        @Deprecated
        public ShortSortedSet headSet(Short s) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(s));
        }

        @Override
        @Deprecated
        public ShortSortedSet tailSet(Short s) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(s));
        }

        @Override
        public ShortIterator iterator() {
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
            return this.tailSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Short)object, (Short)object2);
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
    extends ShortSets.SynchronizedSet
    implements ShortSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortSortedSet sortedSet;

        protected SynchronizedSortedSet(ShortSortedSet shortSortedSet, Object object) {
            super(shortSortedSet, object);
            this.sortedSet = shortSortedSet;
        }

        protected SynchronizedSortedSet(ShortSortedSet shortSortedSet) {
            super(shortSortedSet);
            this.sortedSet = shortSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public ShortSortedSet subSet(short s, short s2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(s, s2), this.sync);
        }

        @Override
        public ShortSortedSet headSet(short s) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(s), this.sync);
        }

        @Override
        public ShortSortedSet tailSet(short s) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(s), this.sync);
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public ShortBidirectionalIterator iterator(short s) {
            return this.sortedSet.iterator(s);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short firstShort() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstShort();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short lastShort() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastShort();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short first() {
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
        public Short last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public ShortSortedSet subSet(Short s, Short s2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(s, s2), this.sync);
        }

        @Override
        @Deprecated
        public ShortSortedSet headSet(Short s) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(s), this.sync);
        }

        @Override
        @Deprecated
        public ShortSortedSet tailSet(Short s) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(s), this.sync);
        }

        @Override
        public ShortIterator iterator() {
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
            return this.tailSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Short)object, (Short)object2);
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
    extends ShortSets.Singleton
    implements ShortSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final ShortComparator comparator;

        protected Singleton(short s, ShortComparator shortComparator) {
            super(s);
            this.comparator = shortComparator;
        }

        private Singleton(short s) {
            this(s, (ShortComparator)null);
        }

        final int compare(short s, short s2) {
            return this.comparator == null ? Short.compare(s, s2) : this.comparator.compare(s, s2);
        }

        @Override
        public ShortBidirectionalIterator iterator(short s) {
            ShortBidirectionalIterator shortBidirectionalIterator = this.iterator();
            if (this.compare(this.element, s) <= 0) {
                shortBidirectionalIterator.nextShort();
            }
            return shortBidirectionalIterator;
        }

        @Override
        public ShortComparator comparator() {
            return this.comparator;
        }

        @Override
        public ShortSortedSet subSet(short s, short s2) {
            if (this.compare(s, this.element) <= 0 && this.compare(this.element, s2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ShortSortedSet headSet(short s) {
            if (this.compare(this.element, s) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ShortSortedSet tailSet(short s) {
            if (this.compare(s, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public short firstShort() {
            return this.element;
        }

        @Override
        public short lastShort() {
            return this.element;
        }

        @Override
        @Deprecated
        public ShortSortedSet subSet(Short s, Short s2) {
            return this.subSet((short)s, (short)s2);
        }

        @Override
        @Deprecated
        public ShortSortedSet headSet(Short s) {
            return this.headSet((short)s);
        }

        @Override
        @Deprecated
        public ShortSortedSet tailSet(Short s) {
            return this.tailSet((short)s);
        }

        @Override
        @Deprecated
        public Short first() {
            return this.element;
        }

        @Override
        @Deprecated
        public Short last() {
            return this.element;
        }

        @Override
        public ShortBidirectionalIterator iterator() {
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
            return this.tailSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Short)object, (Short)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(short s, 1 var2_2) {
            this(s);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends ShortSets.EmptySet
    implements ShortSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public ShortBidirectionalIterator iterator(short s) {
            return ShortIterators.EMPTY_ITERATOR;
        }

        @Override
        public ShortSortedSet subSet(short s, short s2) {
            return EMPTY_SET;
        }

        @Override
        public ShortSortedSet headSet(short s) {
            return EMPTY_SET;
        }

        @Override
        public ShortSortedSet tailSet(short s) {
            return EMPTY_SET;
        }

        @Override
        public short firstShort() {
            throw new NoSuchElementException();
        }

        @Override
        public short lastShort() {
            throw new NoSuchElementException();
        }

        @Override
        public ShortComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public ShortSortedSet subSet(Short s, Short s2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public ShortSortedSet headSet(Short s) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public ShortSortedSet tailSet(Short s) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Short first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Short last() {
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
            return this.tailSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Short)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Short)object, (Short)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

