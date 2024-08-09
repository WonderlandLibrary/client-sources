/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterators;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class BooleanBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private BooleanBigLists() {
    }

    public static BooleanBigList shuffle(BooleanBigList booleanBigList, Random random2) {
        long l = booleanBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            boolean bl = booleanBigList.getBoolean(l);
            booleanBigList.set(l, booleanBigList.getBoolean(l2));
            booleanBigList.set(l2, bl);
        }
        return booleanBigList;
    }

    public static BooleanBigList singleton(boolean bl) {
        return new Singleton(bl);
    }

    public static BooleanBigList singleton(Object object) {
        return new Singleton((Boolean)object);
    }

    public static BooleanBigList synchronize(BooleanBigList booleanBigList) {
        return new SynchronizedBigList(booleanBigList);
    }

    public static BooleanBigList synchronize(BooleanBigList booleanBigList, Object object) {
        return new SynchronizedBigList(booleanBigList, object);
    }

    public static BooleanBigList unmodifiable(BooleanBigList booleanBigList) {
        return new UnmodifiableBigList(booleanBigList);
    }

    public static BooleanBigList asBigList(BooleanList booleanList) {
        return new ListBigList(booleanList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractBooleanBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final BooleanList list;

        protected ListBigList(BooleanList booleanList) {
            this.list = booleanList;
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
        public BooleanBigListIterator iterator() {
            return BooleanBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public BooleanBigListIterator listIterator() {
            return BooleanBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            return BooleanBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public BooleanBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(boolean bl) {
            return this.list.contains(bl);
        }

        @Override
        public boolean[] toBooleanArray() {
            return this.list.toBooleanArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public boolean[] toBooleanArray(boolean[] blArray) {
            return this.list.toArray(blArray);
        }

        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            return this.list.addAll(this.intIndex(l), booleanCollection);
        }

        @Override
        public boolean addAll(BooleanCollection booleanCollection) {
            return this.list.addAll(booleanCollection);
        }

        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            return this.list.addAll(this.intIndex(l), booleanBigList);
        }

        @Override
        public boolean addAll(BooleanBigList booleanBigList) {
            return this.list.addAll(booleanBigList);
        }

        @Override
        public boolean containsAll(BooleanCollection booleanCollection) {
            return this.list.containsAll(booleanCollection);
        }

        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            return this.list.removeAll(booleanCollection);
        }

        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
            return this.list.retainAll(booleanCollection);
        }

        @Override
        public void add(long l, boolean bl) {
            this.list.add(this.intIndex(l), bl);
        }

        @Override
        public boolean add(boolean bl) {
            return this.list.add(bl);
        }

        @Override
        public boolean getBoolean(long l) {
            return this.list.getBoolean(this.intIndex(l));
        }

        @Override
        public long indexOf(boolean bl) {
            return this.list.indexOf(bl);
        }

        @Override
        public long lastIndexOf(boolean bl) {
            return this.list.lastIndexOf(bl);
        }

        @Override
        public boolean removeBoolean(long l) {
            return this.list.removeBoolean(this.intIndex(l));
        }

        @Override
        public boolean set(long l, boolean bl) {
            return this.list.set(this.intIndex(l), bl);
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
        public boolean addAll(Collection<? extends Boolean> collection) {
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
        public BooleanIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends BooleanCollections.UnmodifiableCollection
    implements BooleanBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanBigList list;

        protected UnmodifiableBigList(BooleanBigList booleanBigList) {
            super(booleanBigList);
            this.list = booleanBigList;
        }

        @Override
        public boolean getBoolean(long l) {
            return this.list.getBoolean(l);
        }

        @Override
        public boolean set(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(boolean bl) {
            return this.list.indexOf(bl);
        }

        @Override
        public long lastIndexOf(boolean bl) {
            return this.list.lastIndexOf(bl);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, boolean[][] blArray, long l2, long l3) {
            this.list.getElements(l, blArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, boolean[][] blArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, boolean[][] blArray) {
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
        public BooleanBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public BooleanBigListIterator listIterator() {
            return BooleanBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            return BooleanBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public BooleanBigList subList(long l, long l2) {
            return BooleanBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Boolean> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean set(long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean remove(long l) {
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
        public BooleanIterator iterator() {
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
            this.add(l, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Boolean)object);
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
    extends BooleanCollections.SynchronizedCollection
    implements BooleanBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanBigList list;

        protected SynchronizedBigList(BooleanBigList booleanBigList, Object object) {
            super(booleanBigList, object);
            this.list = booleanBigList;
        }

        protected SynchronizedBigList(BooleanBigList booleanBigList) {
            super(booleanBigList);
            this.list = booleanBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean getBoolean(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getBoolean(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean set(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeBoolean(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeBoolean(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, boolean[][] blArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, blArray, l2, l3);
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
        public void addElements(long l, boolean[][] blArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, blArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, boolean[][] blArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, blArray);
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
        public BooleanBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public BooleanBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BooleanBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return BooleanBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Boolean> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, booleanBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(BooleanBigList booleanBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(booleanBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean get(long l) {
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
        public Boolean set(long l, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean remove(long l) {
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
        public BooleanIterator iterator() {
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
            this.add(l, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Boolean)object);
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
    extends AbstractBooleanBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final boolean element;

        protected Singleton(boolean bl) {
            this.element = bl;
        }

        @Override
        public boolean getBoolean(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(boolean bl) {
            return bl == this.element;
        }

        @Override
        public boolean[] toBooleanArray() {
            boolean[] blArray = new boolean[]{this.element};
            return blArray;
        }

        @Override
        public BooleanBigListIterator listIterator() {
            return BooleanBigListIterators.singleton(this.element);
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            BooleanBigListIterator booleanBigListIterator = this.listIterator();
            if (l == 1L) {
                booleanBigListIterator.nextBoolean();
            }
            return booleanBigListIterator;
        }

        @Override
        public BooleanBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Boolean> collection) {
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
        public boolean addAll(BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
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
    extends BooleanCollections.EmptyCollection
    implements BooleanBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public boolean getBoolean(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean set(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(boolean bl) {
            return -1L;
        }

        @Override
        public long lastIndexOf(boolean bl) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Boolean set(long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean remove(long l) {
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
        public BooleanBigListIterator listIterator() {
            return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public BooleanBigListIterator iterator() {
            return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            if (l == 0L) {
                return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public BooleanBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, boolean[][] blArray, long l2, long l3) {
            BooleanBigArrays.ensureOffsetLength(blArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, boolean[][] blArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, boolean[][] blArray) {
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
        public int compareTo(BigList<? extends Boolean> bigList) {
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
        public BooleanBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public BooleanIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Boolean)object);
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
            this.add(l, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Boolean)object);
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

