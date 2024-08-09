/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteList;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class ByteLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ByteLists() {
    }

    public static ByteList shuffle(ByteList byteList, Random random2) {
        int n = byteList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            byte by = byteList.getByte(n);
            byteList.set(n, byteList.getByte(n2));
            byteList.set(n2, by);
        }
        return byteList;
    }

    public static ByteList singleton(byte by) {
        return new Singleton(by);
    }

    public static ByteList singleton(Object object) {
        return new Singleton((Byte)object);
    }

    public static ByteList synchronize(ByteList byteList) {
        return byteList instanceof RandomAccess ? new SynchronizedRandomAccessList(byteList) : new SynchronizedList(byteList);
    }

    public static ByteList synchronize(ByteList byteList, Object object) {
        return byteList instanceof RandomAccess ? new SynchronizedRandomAccessList(byteList, object) : new SynchronizedList(byteList, object);
    }

    public static ByteList unmodifiable(ByteList byteList) {
        return byteList instanceof RandomAccess ? new UnmodifiableRandomAccessList(byteList) : new UnmodifiableList(byteList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(ByteList byteList) {
            super(byteList);
        }

        @Override
        public ByteList subList(int n, int n2) {
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
    extends ByteCollections.UnmodifiableCollection
    implements ByteList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList list;

        protected UnmodifiableList(ByteList byteList) {
            super(byteList);
            this.list = byteList;
        }

        @Override
        public byte getByte(int n) {
            return this.list.getByte(n);
        }

        @Override
        public byte set(int n, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(byte by) {
            return this.list.indexOf(by);
        }

        @Override
        public int lastIndexOf(byte by) {
            return this.list.lastIndexOf(by);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, byte[] byArray, int n2, int n3) {
            this.list.getElements(n, byArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, byte[] byArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, byte[] byArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public ByteListIterator listIterator() {
            return ByteIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ByteListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteListIterator listIterator(int n) {
            return ByteIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public ByteList subList(int n, int n2) {
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
        public int compareTo(List<? extends Byte> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte set(int n, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte remove(int n) {
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
        public ByteIterator iterator() {
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
            this.add(n, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Byte)object);
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

        protected SynchronizedRandomAccessList(ByteList byteList, Object object) {
            super(byteList, object);
        }

        protected SynchronizedRandomAccessList(ByteList byteList) {
            super(byteList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteList subList(int n, int n2) {
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
    extends ByteCollections.SynchronizedCollection
    implements ByteList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList list;

        protected SynchronizedList(ByteList byteList, Object object) {
            super(byteList, object);
            this.list = byteList;
        }

        protected SynchronizedList(ByteList byteList) {
            super(byteList);
            this.list = byteList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getByte(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte set(int n, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, byte by) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeByte(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Byte> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, byte[] byArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, byArray, n2, n3);
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
        public void addElements(int n, byte[] byArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, byArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, byte[] byArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, byArray);
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
        public ByteListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteList subList(int n, int n2) {
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
        public int compareTo(List<? extends Byte> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, ByteList byteList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, byteList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteList byteList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(byteList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte get(int n) {
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
        public void add(int n, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte set(int n, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte remove(int n) {
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
        public ByteIterator iterator() {
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
            this.add(n, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Byte)object);
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
    extends AbstractByteList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final byte element;

        protected Singleton(byte by) {
            this.element = by;
        }

        @Override
        public byte getByte(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(byte by) {
            return by == this.element;
        }

        @Override
        public byte[] toByteArray() {
            byte[] byArray = new byte[]{this.element};
            return byArray;
        }

        @Override
        public ByteListIterator listIterator() {
            return ByteIterators.singleton(this.element);
        }

        @Override
        public ByteListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            ByteListIterator byteListIterator = this.listIterator();
            if (n == 1) {
                byteListIterator.nextByte();
            }
            return byteListIterator;
        }

        @Override
        public ByteList subList(int n, int n2) {
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
        public boolean addAll(int n, Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Byte> collection) {
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
        public boolean addAll(ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection byteCollection) {
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
        public ByteIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends ByteCollections.EmptyCollection
    implements ByteList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public byte getByte(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte set(int n, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(byte by) {
            return 1;
        }

        @Override
        public int lastIndexOf(byte by) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, ByteList byteList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte set(int n, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte remove(int n) {
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
        public ByteListIterator listIterator() {
            return ByteIterators.EMPTY_ITERATOR;
        }

        @Override
        public ByteListIterator iterator() {
            return ByteIterators.EMPTY_ITERATOR;
        }

        @Override
        public ByteListIterator listIterator(int n) {
            if (n == 0) {
                return ByteIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public ByteList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, byte[] byArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= byArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, byte[] byArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, byte[] byArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Byte> list) {
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
        public ByteBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Byte)object);
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
            this.add(n, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Byte)object);
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

