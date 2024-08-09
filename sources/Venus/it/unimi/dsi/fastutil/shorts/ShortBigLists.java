/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterators;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class ShortBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private ShortBigLists() {
    }

    public static ShortBigList shuffle(ShortBigList shortBigList, Random random2) {
        long l = shortBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            short s = shortBigList.getShort(l);
            shortBigList.set(l, shortBigList.getShort(l2));
            shortBigList.set(l2, s);
        }
        return shortBigList;
    }

    public static ShortBigList singleton(short s) {
        return new Singleton(s);
    }

    public static ShortBigList singleton(Object object) {
        return new Singleton((Short)object);
    }

    public static ShortBigList synchronize(ShortBigList shortBigList) {
        return new SynchronizedBigList(shortBigList);
    }

    public static ShortBigList synchronize(ShortBigList shortBigList, Object object) {
        return new SynchronizedBigList(shortBigList, object);
    }

    public static ShortBigList unmodifiable(ShortBigList shortBigList) {
        return new UnmodifiableBigList(shortBigList);
    }

    public static ShortBigList asBigList(ShortList shortList) {
        return new ListBigList(shortList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractShortBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final ShortList list;

        protected ListBigList(ShortList shortList) {
            this.list = shortList;
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
        public ShortBigListIterator iterator() {
            return ShortBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public ShortBigListIterator listIterator() {
            return ShortBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            return ShortBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Short> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public ShortBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(short s) {
            return this.list.contains(s);
        }

        @Override
        public short[] toShortArray() {
            return this.list.toShortArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public short[] toShortArray(short[] sArray) {
            return this.list.toArray(sArray);
        }

        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            return this.list.addAll(this.intIndex(l), shortCollection);
        }

        @Override
        public boolean addAll(ShortCollection shortCollection) {
            return this.list.addAll(shortCollection);
        }

        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            return this.list.addAll(this.intIndex(l), shortBigList);
        }

        @Override
        public boolean addAll(ShortBigList shortBigList) {
            return this.list.addAll(shortBigList);
        }

        @Override
        public boolean containsAll(ShortCollection shortCollection) {
            return this.list.containsAll(shortCollection);
        }

        @Override
        public boolean removeAll(ShortCollection shortCollection) {
            return this.list.removeAll(shortCollection);
        }

        @Override
        public boolean retainAll(ShortCollection shortCollection) {
            return this.list.retainAll(shortCollection);
        }

        @Override
        public void add(long l, short s) {
            this.list.add(this.intIndex(l), s);
        }

        @Override
        public boolean add(short s) {
            return this.list.add(s);
        }

        @Override
        public short getShort(long l) {
            return this.list.getShort(this.intIndex(l));
        }

        @Override
        public long indexOf(short s) {
            return this.list.indexOf(s);
        }

        @Override
        public long lastIndexOf(short s) {
            return this.list.lastIndexOf(s);
        }

        @Override
        public short removeShort(long l) {
            return this.list.removeShort(this.intIndex(l));
        }

        @Override
        public short set(long l, short s) {
            return this.list.set(this.intIndex(l), s);
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
        public boolean addAll(Collection<? extends Short> collection) {
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
        public ShortIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends ShortCollections.UnmodifiableCollection
    implements ShortBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortBigList list;

        protected UnmodifiableBigList(ShortBigList shortBigList) {
            super(shortBigList);
            this.list = shortBigList;
        }

        @Override
        public short getShort(long l) {
            return this.list.getShort(l);
        }

        @Override
        public short set(long l, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(short s) {
            return this.list.indexOf(s);
        }

        @Override
        public long lastIndexOf(short s) {
            return this.list.lastIndexOf(s);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, short[][] sArray, long l2, long l3) {
            this.list.getElements(l, sArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, short[][] sArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, short[][] sArray) {
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
        public ShortBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ShortBigListIterator listIterator() {
            return ShortBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            return ShortBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public ShortBigList subList(long l, long l2) {
            return ShortBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Short> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short set(long l, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short remove(long l) {
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
        public ShortIterator iterator() {
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
            this.add(l, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Short)object);
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
    extends ShortCollections.SynchronizedCollection
    implements ShortBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortBigList list;

        protected SynchronizedBigList(ShortBigList shortBigList, Object object) {
            super(shortBigList, object);
            this.list = shortBigList;
        }

        protected SynchronizedBigList(ShortBigList shortBigList) {
            super(shortBigList);
            this.list = shortBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short getShort(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getShort(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short set(long l, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, short s) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short removeShort(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeShort(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Short> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, short[][] sArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, sArray, l2, l3);
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
        public void addElements(long l, short[][] sArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, sArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, short[][] sArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, sArray);
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
        public ShortBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public ShortBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return ShortBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Short> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, shortCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, shortBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ShortBigList shortBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(shortBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Short s) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short get(long l) {
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
        public Short set(long l, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short remove(long l) {
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
        public ShortIterator iterator() {
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
            this.add(l, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Short)object);
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
    extends AbstractShortBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final short element;

        protected Singleton(short s) {
            this.element = s;
        }

        @Override
        public short getShort(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(short s) {
            return s == this.element;
        }

        @Override
        public short[] toShortArray() {
            short[] sArray = new short[]{this.element};
            return sArray;
        }

        @Override
        public ShortBigListIterator listIterator() {
            return ShortBigListIterators.singleton(this.element);
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            ShortBigListIterator shortBigListIterator = this.listIterator();
            if (l == 1L) {
                shortBigListIterator.nextShort();
            }
            return shortBigListIterator;
        }

        @Override
        public ShortBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Short> collection) {
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
        public boolean addAll(ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ShortCollection shortCollection) {
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
    extends ShortCollections.EmptyCollection
    implements ShortBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public short getShort(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short set(long l, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(short s) {
            return -1L;
        }

        @Override
        public long lastIndexOf(short s) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Short set(long l, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short remove(long l) {
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
        public ShortBigListIterator listIterator() {
            return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ShortBigListIterator iterator() {
            return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            if (l == 0L) {
                return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public ShortBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, short[][] sArray, long l2, long l3) {
            ShortBigArrays.ensureOffsetLength(sArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, short[][] sArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, short[][] sArray) {
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
        public int compareTo(BigList<? extends Short> bigList) {
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
        public ShortBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public ShortIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Short)object);
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
            this.add(l, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Short)object);
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

