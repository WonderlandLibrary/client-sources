/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class BooleanLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private BooleanLists() {
    }

    public static BooleanList shuffle(BooleanList booleanList, Random random2) {
        int n = booleanList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            boolean bl = booleanList.getBoolean(n);
            booleanList.set(n, booleanList.getBoolean(n2));
            booleanList.set(n2, bl);
        }
        return booleanList;
    }

    public static BooleanList singleton(boolean bl) {
        return new Singleton(bl);
    }

    public static BooleanList singleton(Object object) {
        return new Singleton((Boolean)object);
    }

    public static BooleanList synchronize(BooleanList booleanList) {
        return booleanList instanceof RandomAccess ? new SynchronizedRandomAccessList(booleanList) : new SynchronizedList(booleanList);
    }

    public static BooleanList synchronize(BooleanList booleanList, Object object) {
        return booleanList instanceof RandomAccess ? new SynchronizedRandomAccessList(booleanList, object) : new SynchronizedList(booleanList, object);
    }

    public static BooleanList unmodifiable(BooleanList booleanList) {
        return booleanList instanceof RandomAccess ? new UnmodifiableRandomAccessList(booleanList) : new UnmodifiableList(booleanList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(BooleanList booleanList) {
            super(booleanList);
        }

        @Override
        public BooleanList subList(int n, int n2) {
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
    extends BooleanCollections.UnmodifiableCollection
    implements BooleanList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanList list;

        protected UnmodifiableList(BooleanList booleanList) {
            super(booleanList);
            this.list = booleanList;
        }

        @Override
        public boolean getBoolean(int n) {
            return this.list.getBoolean(n);
        }

        @Override
        public boolean set(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(boolean bl) {
            return this.list.indexOf(bl);
        }

        @Override
        public int lastIndexOf(boolean bl) {
            return this.list.lastIndexOf(bl);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Boolean> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, boolean[] blArray, int n2, int n3) {
            this.list.getElements(n, blArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, boolean[] blArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, boolean[] blArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public BooleanListIterator listIterator() {
            return BooleanIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public BooleanListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public BooleanListIterator listIterator(int n) {
            return BooleanIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public BooleanList subList(int n, int n2) {
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
        public int compareTo(List<? extends Boolean> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean set(int n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean remove(int n) {
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
        public BooleanIterator iterator() {
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
            this.add(n, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Boolean)object);
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

        protected SynchronizedRandomAccessList(BooleanList booleanList, Object object) {
            super(booleanList, object);
        }

        protected SynchronizedRandomAccessList(BooleanList booleanList) {
            super(booleanList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BooleanList subList(int n, int n2) {
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
    extends BooleanCollections.SynchronizedCollection
    implements BooleanList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanList list;

        protected SynchronizedList(BooleanList booleanList, Object object) {
            super(booleanList, object);
            this.list = booleanList;
        }

        protected SynchronizedList(BooleanList booleanList) {
            super(booleanList);
            this.list = booleanList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean getBoolean(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getBoolean(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean set(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeBoolean(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeBoolean(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Boolean> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, boolean[] blArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, blArray, n2, n3);
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
        public void addElements(int n, boolean[] blArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, blArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, boolean[] blArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, blArray);
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
        public BooleanListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public BooleanListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public BooleanListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BooleanList subList(int n, int n2) {
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
        public int compareTo(List<? extends Boolean> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, BooleanCollection booleanCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, booleanCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, BooleanList booleanList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, booleanList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(BooleanList booleanList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(booleanList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean get(int n) {
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
        public void add(int n, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean set(int n, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean remove(int n) {
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
        public BooleanIterator iterator() {
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
            this.add(n, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Boolean)object);
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
    extends AbstractBooleanList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final boolean element;

        protected Singleton(boolean bl) {
            this.element = bl;
        }

        @Override
        public boolean getBoolean(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(int n) {
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
        public BooleanListIterator listIterator() {
            return BooleanIterators.singleton(this.element);
        }

        @Override
        public BooleanListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public BooleanListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            BooleanListIterator booleanListIterator = this.listIterator();
            if (n == 1) {
                booleanListIterator.nextBoolean();
            }
            return booleanListIterator;
        }

        @Override
        public BooleanList subList(int n, int n2) {
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
        public boolean addAll(int n, Collection<? extends Boolean> collection) {
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
        public boolean addAll(BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, BooleanCollection booleanCollection) {
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
        public BooleanIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends BooleanCollections.EmptyCollection
    implements BooleanList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public boolean getBoolean(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean set(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(boolean bl) {
            return 1;
        }

        @Override
        public int lastIndexOf(boolean bl) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Boolean> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, BooleanList booleanList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean set(int n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean remove(int n) {
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
        public BooleanListIterator listIterator() {
            return BooleanIterators.EMPTY_ITERATOR;
        }

        @Override
        public BooleanListIterator iterator() {
            return BooleanIterators.EMPTY_ITERATOR;
        }

        @Override
        public BooleanListIterator listIterator(int n) {
            if (n == 0) {
                return BooleanIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public BooleanList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, boolean[] blArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= blArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, boolean[] blArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, boolean[] blArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Boolean> list) {
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
            this.add(n, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Boolean)object);
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

