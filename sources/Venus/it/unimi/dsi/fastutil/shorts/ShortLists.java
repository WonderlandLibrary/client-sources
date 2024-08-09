/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortList;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class ShortLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ShortLists() {
    }

    public static ShortList shuffle(ShortList shortList, Random random2) {
        int n = shortList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            short s = shortList.getShort(n);
            shortList.set(n, shortList.getShort(n2));
            shortList.set(n2, s);
        }
        return shortList;
    }

    public static ShortList singleton(short s) {
        return new Singleton(s);
    }

    public static ShortList singleton(Object object) {
        return new Singleton((Short)object);
    }

    public static ShortList synchronize(ShortList shortList) {
        return shortList instanceof RandomAccess ? new SynchronizedRandomAccessList(shortList) : new SynchronizedList(shortList);
    }

    public static ShortList synchronize(ShortList shortList, Object object) {
        return shortList instanceof RandomAccess ? new SynchronizedRandomAccessList(shortList, object) : new SynchronizedList(shortList, object);
    }

    public static ShortList unmodifiable(ShortList shortList) {
        return shortList instanceof RandomAccess ? new UnmodifiableRandomAccessList(shortList) : new UnmodifiableList(shortList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(ShortList shortList) {
            super(shortList);
        }

        @Override
        public ShortList subList(int n, int n2) {
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
    extends ShortCollections.UnmodifiableCollection
    implements ShortList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortList list;

        protected UnmodifiableList(ShortList shortList) {
            super(shortList);
            this.list = shortList;
        }

        @Override
        public short getShort(int n) {
            return this.list.getShort(n);
        }

        @Override
        public short set(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(short s) {
            return this.list.indexOf(s);
        }

        @Override
        public int lastIndexOf(short s) {
            return this.list.lastIndexOf(s);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, short[] sArray, int n2, int n3) {
            this.list.getElements(n, sArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, short[] sArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, short[] sArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public ShortListIterator listIterator() {
            return ShortIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ShortListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ShortListIterator listIterator(int n) {
            return ShortIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public ShortList subList(int n, int n2) {
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
        public int compareTo(List<? extends Short> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short set(int n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short remove(int n) {
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
        public ShortIterator iterator() {
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
            this.add(n, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Short)object);
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

        protected SynchronizedRandomAccessList(ShortList shortList, Object object) {
            super(shortList, object);
        }

        protected SynchronizedRandomAccessList(ShortList shortList) {
            super(shortList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortList subList(int n, int n2) {
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
    extends ShortCollections.SynchronizedCollection
    implements ShortList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortList list;

        protected SynchronizedList(ShortList shortList, Object object) {
            super(shortList, object);
            this.list = shortList;
        }

        protected SynchronizedList(ShortList shortList) {
            super(shortList);
            this.list = shortList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short getShort(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getShort(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short set(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short removeShort(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeShort(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Short> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, short[] sArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, sArray, n2, n3);
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
        public void addElements(int n, short[] sArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, sArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, short[] sArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, sArray);
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
        public ShortListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ShortListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ShortListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortList subList(int n, int n2) {
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
        public int compareTo(List<? extends Short> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, ShortCollection shortCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, shortCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, ShortList shortList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, shortList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ShortList shortList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(shortList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short get(int n) {
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
        public void add(int n, Short s) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short set(int n, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short remove(int n) {
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
        public ShortIterator iterator() {
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
            this.add(n, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Short)object);
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
    extends AbstractShortList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final short element;

        protected Singleton(short s) {
            this.element = s;
        }

        @Override
        public short getShort(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(int n) {
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
        public ShortListIterator listIterator() {
            return ShortIterators.singleton(this.element);
        }

        @Override
        public ShortListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ShortListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            ShortListIterator shortListIterator = this.listIterator();
            if (n == 1) {
                shortListIterator.nextShort();
            }
            return shortListIterator;
        }

        @Override
        public ShortList subList(int n, int n2) {
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
        public boolean addAll(int n, Collection<? extends Short> collection) {
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
        public boolean addAll(ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ShortCollection shortCollection) {
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
        public ShortIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends ShortCollections.EmptyCollection
    implements ShortList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public short getShort(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short set(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(short s) {
            return 1;
        }

        @Override
        public int lastIndexOf(short s) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ShortList shortList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short set(int n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short remove(int n) {
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
        public ShortListIterator listIterator() {
            return ShortIterators.EMPTY_ITERATOR;
        }

        @Override
        public ShortListIterator iterator() {
            return ShortIterators.EMPTY_ITERATOR;
        }

        @Override
        public ShortListIterator listIterator(int n) {
            if (n == 0) {
                return ShortIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public ShortList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, short[] sArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= sArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, short[] sArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, short[] sArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Short> list) {
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
            this.add(n, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Short)object);
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

