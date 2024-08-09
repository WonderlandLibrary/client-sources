/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class ByteSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ByteSortedSets() {
    }

    public static ByteSortedSet singleton(byte by) {
        return new Singleton(by, null);
    }

    public static ByteSortedSet singleton(byte by, ByteComparator byteComparator) {
        return new Singleton(by, byteComparator);
    }

    public static ByteSortedSet singleton(Object object) {
        return new Singleton((byte)((Byte)object), null);
    }

    public static ByteSortedSet singleton(Object object, ByteComparator byteComparator) {
        return new Singleton((byte)((Byte)object), byteComparator);
    }

    public static ByteSortedSet synchronize(ByteSortedSet byteSortedSet) {
        return new SynchronizedSortedSet(byteSortedSet);
    }

    public static ByteSortedSet synchronize(ByteSortedSet byteSortedSet, Object object) {
        return new SynchronizedSortedSet(byteSortedSet, object);
    }

    public static ByteSortedSet unmodifiable(ByteSortedSet byteSortedSet) {
        return new UnmodifiableSortedSet(byteSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends ByteSets.UnmodifiableSet
    implements ByteSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteSortedSet sortedSet;

        protected UnmodifiableSortedSet(ByteSortedSet byteSortedSet) {
            super(byteSortedSet);
            this.sortedSet = byteSortedSet;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(by, by2));
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(by));
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(by));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return ByteIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return ByteIterators.unmodifiable(this.sortedSet.iterator(by));
        }

        @Override
        public byte firstByte() {
            return this.sortedSet.firstByte();
        }

        @Override
        public byte lastByte() {
            return this.sortedSet.lastByte();
        }

        @Override
        @Deprecated
        public Byte first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Byte last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public ByteSortedSet subSet(Byte by, Byte by2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(by, by2));
        }

        @Override
        @Deprecated
        public ByteSortedSet headSet(Byte by) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(by));
        }

        @Override
        @Deprecated
        public ByteSortedSet tailSet(Byte by) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(by));
        }

        @Override
        public ByteIterator iterator() {
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
            return this.tailSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte)object, (Byte)object2);
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
    extends ByteSets.SynchronizedSet
    implements ByteSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteSortedSet sortedSet;

        protected SynchronizedSortedSet(ByteSortedSet byteSortedSet, Object object) {
            super(byteSortedSet, object);
            this.sortedSet = byteSortedSet;
        }

        protected SynchronizedSortedSet(ByteSortedSet byteSortedSet) {
            super(byteSortedSet);
            this.sortedSet = byteSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(by, by2), this.sync);
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(by), this.sync);
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(by), this.sync);
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return this.sortedSet.iterator(by);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte firstByte() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstByte();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte lastByte() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastByte();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte first() {
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
        public Byte last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public ByteSortedSet subSet(Byte by, Byte by2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(by, by2), this.sync);
        }

        @Override
        @Deprecated
        public ByteSortedSet headSet(Byte by) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(by), this.sync);
        }

        @Override
        @Deprecated
        public ByteSortedSet tailSet(Byte by) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(by), this.sync);
        }

        @Override
        public ByteIterator iterator() {
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
            return this.tailSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte)object, (Byte)object2);
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
    extends ByteSets.Singleton
    implements ByteSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final ByteComparator comparator;

        protected Singleton(byte by, ByteComparator byteComparator) {
            super(by);
            this.comparator = byteComparator;
        }

        private Singleton(byte by) {
            this(by, (ByteComparator)null);
        }

        final int compare(byte by, byte by2) {
            return this.comparator == null ? Byte.compare(by, by2) : this.comparator.compare(by, by2);
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            ByteBidirectionalIterator byteBidirectionalIterator = this.iterator();
            if (this.compare(this.element, by) <= 0) {
                byteBidirectionalIterator.nextByte();
            }
            return byteBidirectionalIterator;
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            if (this.compare(by, this.element) <= 0 && this.compare(this.element, by2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            if (this.compare(this.element, by) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            if (this.compare(by, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public byte firstByte() {
            return this.element;
        }

        @Override
        public byte lastByte() {
            return this.element;
        }

        @Override
        @Deprecated
        public ByteSortedSet subSet(Byte by, Byte by2) {
            return this.subSet((byte)by, (byte)by2);
        }

        @Override
        @Deprecated
        public ByteSortedSet headSet(Byte by) {
            return this.headSet((byte)by);
        }

        @Override
        @Deprecated
        public ByteSortedSet tailSet(Byte by) {
            return this.tailSet((byte)by);
        }

        @Override
        @Deprecated
        public Byte first() {
            return this.element;
        }

        @Override
        @Deprecated
        public Byte last() {
            return this.element;
        }

        @Override
        public ByteBidirectionalIterator iterator() {
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
            return this.tailSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(byte by, 1 var2_2) {
            this(by);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends ByteSets.EmptySet
    implements ByteSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return ByteIterators.EMPTY_ITERATOR;
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            return EMPTY_SET;
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            return EMPTY_SET;
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            return EMPTY_SET;
        }

        @Override
        public byte firstByte() {
            throw new NoSuchElementException();
        }

        @Override
        public byte lastByte() {
            throw new NoSuchElementException();
        }

        @Override
        public ByteComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public ByteSortedSet subSet(Byte by, Byte by2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public ByteSortedSet headSet(Byte by) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public ByteSortedSet tailSet(Byte by) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Byte first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Byte last() {
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
            return this.tailSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Byte)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

