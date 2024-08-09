/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class LongSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private LongSortedSets() {
    }

    public static LongSortedSet singleton(long l) {
        return new Singleton(l, null);
    }

    public static LongSortedSet singleton(long l, LongComparator longComparator) {
        return new Singleton(l, longComparator);
    }

    public static LongSortedSet singleton(Object object) {
        return new Singleton((long)((Long)object), null);
    }

    public static LongSortedSet singleton(Object object, LongComparator longComparator) {
        return new Singleton((long)((Long)object), longComparator);
    }

    public static LongSortedSet synchronize(LongSortedSet longSortedSet) {
        return new SynchronizedSortedSet(longSortedSet);
    }

    public static LongSortedSet synchronize(LongSortedSet longSortedSet, Object object) {
        return new SynchronizedSortedSet(longSortedSet, object);
    }

    public static LongSortedSet unmodifiable(LongSortedSet longSortedSet) {
        return new UnmodifiableSortedSet(longSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends LongSets.UnmodifiableSet
    implements LongSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongSortedSet sortedSet;

        protected UnmodifiableSortedSet(LongSortedSet longSortedSet) {
            super(longSortedSet);
            this.sortedSet = longSortedSet;
        }

        @Override
        public LongComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(l, l2));
        }

        @Override
        public LongSortedSet headSet(long l) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(l));
        }

        @Override
        public LongSortedSet tailSet(long l) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(l));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return LongIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            return LongIterators.unmodifiable(this.sortedSet.iterator(l));
        }

        @Override
        public long firstLong() {
            return this.sortedSet.firstLong();
        }

        @Override
        public long lastLong() {
            return this.sortedSet.lastLong();
        }

        @Override
        @Deprecated
        public Long first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Long last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public LongSortedSet subSet(Long l, Long l2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(l, l2));
        }

        @Override
        @Deprecated
        public LongSortedSet headSet(Long l) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(l));
        }

        @Override
        @Deprecated
        public LongSortedSet tailSet(Long l) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(l));
        }

        @Override
        public LongIterator iterator() {
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
            return this.tailSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long)object, (Long)object2);
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
    extends LongSets.SynchronizedSet
    implements LongSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongSortedSet sortedSet;

        protected SynchronizedSortedSet(LongSortedSet longSortedSet, Object object) {
            super(longSortedSet, object);
            this.sortedSet = longSortedSet;
        }

        protected SynchronizedSortedSet(LongSortedSet longSortedSet) {
            super(longSortedSet);
            this.sortedSet = longSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(l, l2), this.sync);
        }

        @Override
        public LongSortedSet headSet(long l) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(l), this.sync);
        }

        @Override
        public LongSortedSet tailSet(long l) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(l), this.sync);
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            return this.sortedSet.iterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long firstLong() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstLong();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastLong() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastLong();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long first() {
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
        public Long last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public LongSortedSet subSet(Long l, Long l2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(l, l2), this.sync);
        }

        @Override
        @Deprecated
        public LongSortedSet headSet(Long l) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(l), this.sync);
        }

        @Override
        @Deprecated
        public LongSortedSet tailSet(Long l) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(l), this.sync);
        }

        @Override
        public LongIterator iterator() {
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
            return this.tailSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long)object, (Long)object2);
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
    extends LongSets.Singleton
    implements LongSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final LongComparator comparator;

        protected Singleton(long l, LongComparator longComparator) {
            super(l);
            this.comparator = longComparator;
        }

        private Singleton(long l) {
            this(l, (LongComparator)null);
        }

        final int compare(long l, long l2) {
            return this.comparator == null ? Long.compare(l, l2) : this.comparator.compare(l, l2);
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            LongBidirectionalIterator longBidirectionalIterator = this.iterator();
            if (this.compare(this.element, l) <= 0) {
                longBidirectionalIterator.nextLong();
            }
            return longBidirectionalIterator;
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            if (this.compare(l, this.element) <= 0 && this.compare(this.element, l2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public LongSortedSet headSet(long l) {
            if (this.compare(this.element, l) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public LongSortedSet tailSet(long l) {
            if (this.compare(l, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public long firstLong() {
            return this.element;
        }

        @Override
        public long lastLong() {
            return this.element;
        }

        @Override
        @Deprecated
        public LongSortedSet subSet(Long l, Long l2) {
            return this.subSet((long)l, (long)l2);
        }

        @Override
        @Deprecated
        public LongSortedSet headSet(Long l) {
            return this.headSet((long)l);
        }

        @Override
        @Deprecated
        public LongSortedSet tailSet(Long l) {
            return this.tailSet((long)l);
        }

        @Override
        @Deprecated
        public Long first() {
            return this.element;
        }

        @Override
        @Deprecated
        public Long last() {
            return this.element;
        }

        @Override
        public LongBidirectionalIterator iterator() {
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
            return this.tailSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long)object, (Long)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(long l, 1 var3_2) {
            this(l);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends LongSets.EmptySet
    implements LongSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            return LongIterators.EMPTY_ITERATOR;
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            return EMPTY_SET;
        }

        @Override
        public LongSortedSet headSet(long l) {
            return EMPTY_SET;
        }

        @Override
        public LongSortedSet tailSet(long l) {
            return EMPTY_SET;
        }

        @Override
        public long firstLong() {
            throw new NoSuchElementException();
        }

        @Override
        public long lastLong() {
            throw new NoSuchElementException();
        }

        @Override
        public LongComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public LongSortedSet subSet(Long l, Long l2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public LongSortedSet headSet(Long l) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public LongSortedSet tailSet(Long l) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Long first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Long last() {
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
            return this.tailSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Long)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long)object, (Long)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

