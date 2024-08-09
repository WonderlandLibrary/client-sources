/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongList;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class LongLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private LongLists() {
    }

    public static LongList shuffle(LongList longList, Random random2) {
        int n = longList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            long l = longList.getLong(n);
            longList.set(n, longList.getLong(n2));
            longList.set(n2, l);
        }
        return longList;
    }

    public static LongList singleton(long l) {
        return new Singleton(l);
    }

    public static LongList singleton(Object object) {
        return new Singleton((Long)object);
    }

    public static LongList synchronize(LongList longList) {
        return longList instanceof RandomAccess ? new SynchronizedRandomAccessList(longList) : new SynchronizedList(longList);
    }

    public static LongList synchronize(LongList longList, Object object) {
        return longList instanceof RandomAccess ? new SynchronizedRandomAccessList(longList, object) : new SynchronizedList(longList, object);
    }

    public static LongList unmodifiable(LongList longList) {
        return longList instanceof RandomAccess ? new UnmodifiableRandomAccessList(longList) : new UnmodifiableList(longList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(LongList longList) {
            super(longList);
        }

        @Override
        public LongList subList(int n, int n2) {
            return new UnmodifiableRandomAccessList(this.list.subList(n, n2));
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableList
    extends LongCollections.UnmodifiableCollection
    implements LongList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongList list;

        protected UnmodifiableList(LongList longList) {
            super(longList);
            this.list = longList;
        }

        @Override
        public long getLong(int n) {
            return this.list.getLong(n);
        }

        @Override
        public long set(int n, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(long l) {
            return this.list.indexOf(l);
        }

        @Override
        public int lastIndexOf(long l) {
            return this.list.lastIndexOf(l);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, long[] lArray, int n2, int n3) {
            this.list.getElements(n, lArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, long[] lArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, long[] lArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public LongListIterator listIterator() {
            return LongIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public LongListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public LongListIterator listIterator(int n) {
            return LongIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public LongList subList(int n, int n2) {
            return new UnmodifiableList(this.list.subList(n, n2));
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.collection.equals(object);
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        public int compareTo(List<? extends Long> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long set(int n, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
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
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedRandomAccessList
    extends SynchronizedList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected SynchronizedRandomAccessList(LongList longList, Object object) {
            super(longList, object);
        }

        protected SynchronizedRandomAccessList(LongList longList) {
            super(longList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedRandomAccessList(this.list.subList(n, n2), this.sync);
            }
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedList
    extends LongCollections.SynchronizedCollection
    implements LongList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongList list;

        protected SynchronizedList(LongList longList, Object object) {
            super(longList, object);
            this.list = longList;
        }

        protected SynchronizedList(LongList longList) {
            super(longList);
            this.list = longList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long getLong(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getLong(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long set(int n, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, long l) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long removeLong(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeLong(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Long> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, long[] lArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, lArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, long[] lArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, lArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, long[] lArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, lArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(n);
            }
        }

        @Override
        public LongListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public LongListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public LongListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedList(this.list.subList(n, n2), this.sync);
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
                return this.collection.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compareTo(List<? extends Long> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, LongCollection longCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, longCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, LongList longList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, longList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(LongList longList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(longList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long get(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(int n, Long l) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long set(int n, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int indexOf(Object object) {
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
        public int lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
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
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractLongList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final long element;

        protected Singleton(long l) {
            this.element = l;
        }

        @Override
        public long getLong(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(int n) {
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
        public LongListIterator listIterator() {
            return LongIterators.singleton(this.element);
        }

        @Override
        public LongListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public LongListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            LongListIterator longListIterator = this.listIterator();
            if (n == 1) {
                longListIterator.nextLong();
            }
            return longListIterator;
        }

        @Override
        public LongList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return EMPTY_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Long> collection) {
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
        public boolean addAll(LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, LongCollection longCollection) {
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
        public int size() {
            return 0;
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
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
    public static class EmptyList
    extends LongCollections.EmptyCollection
    implements LongList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public long getLong(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long set(int n, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(long l) {
            return 1;
        }

        @Override
        public int lastIndexOf(long l) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, LongList longList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long set(int n, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return 1;
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return 1;
        }

        @Override
        public LongListIterator listIterator() {
            return LongIterators.EMPTY_ITERATOR;
        }

        @Override
        public LongListIterator iterator() {
            return LongIterators.EMPTY_ITERATOR;
        }

        @Override
        public LongListIterator listIterator(int n) {
            if (n == 0) {
                return LongIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public LongList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, long[] lArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= lArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, long[] lArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, long[] lArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Long> list) {
            if (list == this) {
                return 1;
            }
            return list.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof List && ((List)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_LIST;
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
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Long)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }
}

