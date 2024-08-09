/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntBigList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntBigList;
import it.unimi.dsi.fastutil.ints.IntBigListIterator;
import it.unimi.dsi.fastutil.ints.IntBigListIterators;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class IntBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private IntBigLists() {
    }

    public static IntBigList shuffle(IntBigList intBigList, Random random2) {
        long l = intBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            int n = intBigList.getInt(l);
            intBigList.set(l, intBigList.getInt(l2));
            intBigList.set(l2, n);
        }
        return intBigList;
    }

    public static IntBigList singleton(int n) {
        return new Singleton(n);
    }

    public static IntBigList singleton(Object object) {
        return new Singleton((Integer)object);
    }

    public static IntBigList synchronize(IntBigList intBigList) {
        return new SynchronizedBigList(intBigList);
    }

    public static IntBigList synchronize(IntBigList intBigList, Object object) {
        return new SynchronizedBigList(intBigList, object);
    }

    public static IntBigList unmodifiable(IntBigList intBigList) {
        return new UnmodifiableBigList(intBigList);
    }

    public static IntBigList asBigList(IntList intList) {
        return new ListBigList(intList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractIntBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final IntList list;

        protected ListBigList(IntList intList) {
            this.list = intList;
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
        public IntBigListIterator iterator() {
            return IntBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public IntBigListIterator listIterator() {
            return IntBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            return IntBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public IntBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(int n) {
            return this.list.contains(n);
        }

        @Override
        public int[] toIntArray() {
            return this.list.toIntArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public int[] toIntArray(int[] nArray) {
            return this.list.toArray(nArray);
        }

        @Override
        public boolean addAll(long l, IntCollection intCollection) {
            return this.list.addAll(this.intIndex(l), intCollection);
        }

        @Override
        public boolean addAll(IntCollection intCollection) {
            return this.list.addAll(intCollection);
        }

        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            return this.list.addAll(this.intIndex(l), intBigList);
        }

        @Override
        public boolean addAll(IntBigList intBigList) {
            return this.list.addAll(intBigList);
        }

        @Override
        public boolean containsAll(IntCollection intCollection) {
            return this.list.containsAll(intCollection);
        }

        @Override
        public boolean removeAll(IntCollection intCollection) {
            return this.list.removeAll(intCollection);
        }

        @Override
        public boolean retainAll(IntCollection intCollection) {
            return this.list.retainAll(intCollection);
        }

        @Override
        public void add(long l, int n) {
            this.list.add(this.intIndex(l), n);
        }

        @Override
        public boolean add(int n) {
            return this.list.add(n);
        }

        @Override
        public int getInt(long l) {
            return this.list.getInt(this.intIndex(l));
        }

        @Override
        public long indexOf(int n) {
            return this.list.indexOf(n);
        }

        @Override
        public long lastIndexOf(int n) {
            return this.list.lastIndexOf(n);
        }

        @Override
        public int removeInt(long l) {
            return this.list.removeInt(this.intIndex(l));
        }

        @Override
        public int set(long l, int n) {
            return this.list.set(this.intIndex(l), n);
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
        public boolean addAll(Collection<? extends Integer> collection) {
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
        public IntIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends IntCollections.UnmodifiableCollection
    implements IntBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntBigList list;

        protected UnmodifiableBigList(IntBigList intBigList) {
            super(intBigList);
            this.list = intBigList;
        }

        @Override
        public int getInt(long l) {
            return this.list.getInt(l);
        }

        @Override
        public int set(long l, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int removeInt(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(int n) {
            return this.list.indexOf(n);
        }

        @Override
        public long lastIndexOf(int n) {
            return this.list.lastIndexOf(n);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, int[][] nArray, long l2, long l3) {
            this.list.getElements(l, nArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, int[][] nArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, int[][] nArray) {
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
        public IntBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public IntBigListIterator listIterator() {
            return IntBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            return IntBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public IntBigList subList(long l, long l2) {
            return IntBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Integer> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer set(long l, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer remove(long l) {
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
        public IntIterator iterator() {
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
            this.add(l, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Integer)object);
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
    extends IntCollections.SynchronizedCollection
    implements IntBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntBigList list;

        protected SynchronizedBigList(IntBigList intBigList, Object object) {
            super(intBigList, object);
            this.list = intBigList;
        }

        protected SynchronizedBigList(IntBigList intBigList) {
            super(intBigList);
            this.list = intBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int getInt(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getInt(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int set(long l, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, int n) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int removeInt(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeInt(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, int[][] nArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, nArray, l2, l3);
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
        public void addElements(long l, int[][] nArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, nArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, int[][] nArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, nArray);
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
        public IntBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public IntBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IntBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return IntBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Integer> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, IntCollection intCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, intCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, intBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(IntBigList intBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(intBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer get(long l) {
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
        public Integer set(long l, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer remove(long l) {
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
        public IntIterator iterator() {
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
            this.add(l, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Integer)object);
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
    extends AbstractIntBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final int element;

        protected Singleton(int n) {
            this.element = n;
        }

        @Override
        public int getInt(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int removeInt(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(int n) {
            return n == this.element;
        }

        @Override
        public int[] toIntArray() {
            int[] nArray = new int[]{this.element};
            return nArray;
        }

        @Override
        public IntBigListIterator listIterator() {
            return IntBigListIterators.singleton(this.element);
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            IntBigListIterator intBigListIterator = this.listIterator();
            if (l == 1L) {
                intBigListIterator.nextInt();
            }
            return intBigListIterator;
        }

        @Override
        public IntBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
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
        public boolean addAll(IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, IntCollection intCollection) {
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
    extends IntCollections.EmptyCollection
    implements IntBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public int getInt(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int removeInt(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int set(long l, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(int n) {
            return -1L;
        }

        @Override
        public long lastIndexOf(int n) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Integer set(long l, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer remove(long l) {
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
        public IntBigListIterator listIterator() {
            return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public IntBigListIterator iterator() {
            return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            if (l == 0L) {
                return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public IntBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, int[][] nArray, long l2, long l3) {
            IntBigArrays.ensureOffsetLength(nArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, int[][] nArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, int[][] nArray) {
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
        public int compareTo(BigList<? extends Integer> bigList) {
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
        public IntBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Integer)object);
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
            this.add(l, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Integer)object);
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

