/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongBigList;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongBigList;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;
import it.unimi.dsi.fastutil.longs.LongBigListIterators;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class LongBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private LongBigLists() {
    }

    public static LongBigList shuffle(LongBigList longBigList, Random random2) {
        long l = longBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            long l3 = longBigList.getLong(l);
            longBigList.set(l, longBigList.getLong(l2));
            longBigList.set(l2, l3);
        }
        return longBigList;
    }

    public static LongBigList singleton(long l) {
        return new Singleton(l);
    }

    public static LongBigList singleton(Object object) {
        return new Singleton((Long)object);
    }

    public static LongBigList synchronize(LongBigList longBigList) {
        return new SynchronizedBigList(longBigList);
    }

    public static LongBigList synchronize(LongBigList longBigList, Object object) {
        return new SynchronizedBigList(longBigList, object);
    }

    public static LongBigList unmodifiable(LongBigList longBigList) {
        return new UnmodifiableBigList(longBigList);
    }

    public static LongBigList asBigList(LongList longList) {
        return new ListBigList(longList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractLongBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final LongList list;

        protected ListBigList(LongList longList) {
            this.list = longList;
        }

        private int intIndex(long l) {
            if (l >= Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices");
            }
            return (int)l;
        }

        @Override
        public long size64() {
            return this.list.size();
        }

        @Override
        public void size(long l) {
            this.list.size(this.intIndex(l));
        }

        @Override
        public LongBigListIterator iterator() {
            return LongBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public LongBigListIterator listIterator() {
            return LongBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            return LongBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public LongBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(long l) {
            return this.list.contains(l);
        }

        @Override
        public long[] toLongArray() {
            return this.list.toLongArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public long[] toLongArray(long[] lArray) {
            return this.list.toArray(lArray);
        }

        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            return this.list.addAll(this.intIndex(l), longCollection);
        }

        @Override
        public boolean addAll(LongCollection longCollection) {
            return this.list.addAll(longCollection);
        }

        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            return this.list.addAll(this.intIndex(l), longBigList);
        }

        @Override
        public boolean addAll(LongBigList longBigList) {
            return this.list.addAll(longBigList);
        }

        @Override
        public boolean containsAll(LongCollection longCollection) {
            return this.list.containsAll(longCollection);
        }

        @Override
        public boolean removeAll(LongCollection longCollection) {
            return this.list.removeAll(longCollection);
        }

        @Override
        public boolean retainAll(LongCollection longCollection) {
            return this.list.retainAll(longCollection);
        }

        @Override
        public void add(long l, long l2) {
            this.list.add(this.intIndex(l), l2);
        }

        @Override
        public boolean add(long l) {
            return this.list.add(l);
        }

        @Override
        public long getLong(long l) {
            return this.list.getLong(this.intIndex(l));
        }

        @Override
        public long indexOf(long l) {
            return this.list.indexOf(l);
        }

        @Override
        public long lastIndexOf(long l) {
            return this.list.lastIndexOf(l);
        }

        @Override
        public long removeLong(long l) {
            return this.list.removeLong(this.intIndex(l));
        }

        @Override
        public long set(long l, long l2) {
            return this.list.set(this.intIndex(l), l2);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.list.toArray(TArray);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.list.containsAll(collection);
        }

        @Override
        public boolean addAll(Collection<? extends Long> collection) {
            return this.list.addAll(collection);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.list.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.list.retainAll(collection);
        }

        @Override
        public void clear() {
            this.list.clear();
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public LongIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends LongCollections.UnmodifiableCollection
    implements LongBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongBigList list;

        protected UnmodifiableBigList(LongBigList longBigList) {
            super(longBigList);
            this.list = longBigList;
        }

        @Override
        public long getLong(long l) {
            return this.list.getLong(l);
        }

        @Override
        public long set(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(long l) {
            return this.list.indexOf(l);
        }

        @Override
        public long lastIndexOf(long l) {
            return this.list.lastIndexOf(l);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, long[][] lArray, long l2, long l3) {
            this.list.getElements(l, lArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, long[][] lArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, long[][] lArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void size(long l) {
            this.list.size(l);
        }

        @Override
        public long size64() {
            return this.list.size64();
        }

        @Override
        public LongBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public LongBigListIterator listIterator() {
            return LongBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            return LongBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public LongBigList subList(long l, long l2) {
            return LongBigLists.unmodifiable(this.list.subList(l, l2));
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.list.equals(object);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int compareTo(BigList<? extends Long> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long set(long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
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
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedBigList
    extends LongCollections.SynchronizedCollection
    implements LongBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongBigList list;

        protected SynchronizedBigList(LongBigList longBigList, Object object) {
            super(longBigList, object);
            this.list = longBigList;
        }

        protected SynchronizedBigList(LongBigList longBigList) {
            super(longBigList);
            this.list = longBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long getLong(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getLong(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long set(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long removeLong(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeLong(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, long[][] lArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, lArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, long[][] lArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, lArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, long[][] lArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, lArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void size(long l) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long size64() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.size64();
            }
        }

        @Override
        public LongBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public LongBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return LongBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
                return this.list.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compareTo(BigList<? extends Long> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, longBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(LongBigList longBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(longBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long set(long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long indexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.indexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
            }
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
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractLongBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final long element;

        protected Singleton(long l) {
            this.element = l;
        }

        @Override
        public long getLong(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(long l) {
            return l == this.element;
        }

        @Override
        public long[] toLongArray() {
            long[] lArray = new long[]{this.element};
            return lArray;
        }

        @Override
        public LongBigListIterator listIterator() {
            return LongBigListIterators.singleton(this.element);
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            LongBigListIterator longBigListIterator = this.listIterator();
            if (l == 1L) {
                longBigListIterator.nextLong();
            }
            return longBigListIterator;
        }

        @Override
        public LongBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            if (l != 0L || l2 != 1L) {
                return EMPTY_BIG_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Long> collection) {
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
        public boolean addAll(LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 1L;
        }

        public Object clone() {
            return this;
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyBigList
    extends LongCollections.EmptyCollection
    implements LongBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public long getLong(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long set(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(long l) {
            return -1L;
        }

        @Override
        public long lastIndexOf(long l) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Long set(long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return -1L;
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
            return -1L;
        }

        @Override
        public LongBigListIterator listIterator() {
            return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public LongBigListIterator iterator() {
            return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            if (l == 0L) {
                return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public LongBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, long[][] lArray, long l2, long l3) {
            LongBigArrays.ensureOffsetLength(lArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, long[][] lArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, long[][] lArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 0L;
        }

        @Override
        public int compareTo(BigList<? extends Long> bigList) {
            if (bigList == this) {
                return 1;
            }
            return bigList.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof BigList && ((BigList)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public LongIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Long)object);
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }
}

