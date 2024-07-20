/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.bytes.AbstractByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterators;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

public class ByteBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private ByteBigLists() {
    }

    public static ByteBigList shuffle(ByteBigList l, Random random) {
        long i = l.size64();
        while (i-- != 0L) {
            long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
            byte t = l.getByte(i);
            l.set(i, l.getByte(p));
            l.set(p, t);
        }
        return l;
    }

    public static ByteBigList singleton(byte element) {
        return new Singleton(element);
    }

    public static ByteBigList singleton(Object element) {
        return new Singleton((Byte)element);
    }

    public static ByteBigList synchronize(ByteBigList l) {
        return new SynchronizedBigList(l);
    }

    public static ByteBigList synchronize(ByteBigList l, Object sync) {
        return new SynchronizedBigList(l, sync);
    }

    public static ByteBigList unmodifiable(ByteBigList l) {
        return new UnmodifiableBigList(l);
    }

    public static ByteBigList asBigList(ByteList list) {
        return new ListBigList(list);
    }

    public static class ListBigList
    extends AbstractByteBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final ByteList list;

        protected ListBigList(ByteList list) {
            this.list = list;
        }

        private int intIndex(long index) {
            if (index >= Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices");
            }
            return (int)index;
        }

        @Override
        public long size64() {
            return this.list.size();
        }

        @Override
        @Deprecated
        public int size() {
            return this.list.size();
        }

        @Override
        public void size(long size) {
            this.list.size(this.intIndex(size));
        }

        @Override
        public ByteBigListIterator iterator() {
            return ByteBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public boolean addAll(long index, Collection<? extends Byte> c) {
            return this.list.addAll(this.intIndex(index), c);
        }

        @Override
        public ByteBigListIterator listIterator(long index) {
            return ByteBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(index)));
        }

        @Override
        public ByteBigList subList(long from, long to) {
            return new ListBigList(this.list.subList(this.intIndex(from), this.intIndex(to)));
        }

        @Override
        public boolean contains(byte key) {
            return this.list.contains(key);
        }

        @Override
        public byte[] toByteArray() {
            return this.list.toByteArray();
        }

        @Override
        public void removeElements(long from, long to) {
            this.list.removeElements(this.intIndex(from), this.intIndex(to));
        }

        @Override
        public byte[] toByteArray(byte[] a) {
            return this.list.toByteArray(a);
        }

        @Override
        public void add(long index, byte key) {
            this.list.add(this.intIndex(index), key);
        }

        @Override
        public boolean addAll(long index, ByteCollection c) {
            return this.list.addAll(this.intIndex(index), c);
        }

        @Override
        public boolean addAll(long index, ByteBigList c) {
            return this.list.addAll(this.intIndex(index), c);
        }

        @Override
        public boolean add(byte key) {
            return this.list.add(key);
        }

        @Override
        public boolean addAll(ByteBigList c) {
            return this.list.addAll(c);
        }

        @Override
        public byte getByte(long index) {
            return this.list.getByte(this.intIndex(index));
        }

        @Override
        public long indexOf(byte k) {
            return this.list.indexOf(k);
        }

        @Override
        public long lastIndexOf(byte k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public byte removeByte(long index) {
            return this.list.removeByte(this.intIndex(index));
        }

        @Override
        public byte set(long index, byte k) {
            return this.list.set(this.intIndex(index), k);
        }

        @Override
        public boolean addAll(ByteCollection c) {
            return this.list.addAll(c);
        }

        @Override
        public boolean containsAll(ByteCollection c) {
            return this.list.containsAll(c);
        }

        @Override
        public boolean removeAll(ByteCollection c) {
            return this.list.removeAll(c);
        }

        @Override
        public boolean retainAll(ByteCollection c) {
            return this.list.retainAll(c);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return this.list.toArray(a);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.list.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Byte> c) {
            return this.list.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return this.list.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return this.list.retainAll(c);
        }

        @Override
        public void clear() {
            this.list.clear();
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }
    }

    public static class UnmodifiableBigList
    extends ByteCollections.UnmodifiableCollection
    implements ByteBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList list;

        protected UnmodifiableBigList(ByteBigList l) {
            super(l);
            this.list = l;
        }

        @Override
        public byte getByte(long i) {
            return this.list.getByte(i);
        }

        @Override
        public byte set(long i, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long i, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(long i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(byte k) {
            return this.list.indexOf(k);
        }

        @Override
        public long lastIndexOf(byte k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(long index, Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long from, byte[][] a, long offset, long length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(long from, long to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long index, byte[][] a, long offset, long length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long index, byte[][] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(long size) {
            this.list.size(size);
        }

        @Override
        public long size64() {
            return this.list.size64();
        }

        @Override
        public ByteBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ByteBigListIterator listIterator(long i) {
            return ByteBigListIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        public ByteBigList subList(long from, long to) {
            return ByteBigLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        public boolean equals(Object o) {
            return this.list.equals(o);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int compareTo(BigList<? extends Byte> o) {
            return this.list.compareTo(o);
        }

        @Override
        public boolean addAll(long index, ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteBigList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long index, ByteBigList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte get(long i) {
            return (Byte)this.list.get(i);
        }

        @Override
        public void add(long i, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte set(long index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte remove(long i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(Object o) {
            return this.list.indexOf(o);
        }

        @Override
        public long lastIndexOf(Object o) {
            return this.list.lastIndexOf(o);
        }
    }

    public static class SynchronizedBigList
    extends ByteCollections.SynchronizedCollection
    implements ByteBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList list;

        protected SynchronizedBigList(ByteBigList l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedBigList(ByteBigList l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(long i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getByte(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte set(long i, byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long i, byte k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(long i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeByte(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long index, Collection<? extends Byte> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long from, byte[][] a, long offset, long length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(from, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(long from, long to) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(from, to);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long index, byte[][] a, long offset, long length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long index, byte[][] a) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(long size) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(size);
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
        public ByteBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator(long i) {
            return this.list.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteBigList subList(long from, long to) {
            Object object = this.sync;
            synchronized (object) {
                return ByteBigLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.equals(o);
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
        public int compareTo(BigList<? extends Byte> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long index, ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long index, ByteBigList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteBigList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte get(long i) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long i, Byte k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte set(long index, Byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(index, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte remove(long i) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.list.remove(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(o);
            }
        }
    }

    public static class Singleton
    extends AbstractByteBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final byte element;

        private Singleton(byte element) {
            this.element = element;
        }

        @Override
        public byte getByte(long i) {
            if (i == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public byte removeByte(long i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(byte k) {
            return k == this.element;
        }

        @Override
        public boolean addAll(Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long i, Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte[] toByteArray() {
            byte[] a = new byte[]{this.element};
            return a;
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.singleton(this.element);
        }

        @Override
        public ByteBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator(long i) {
            if (i > 1L || i < 0L) {
                throw new IndexOutOfBoundsException();
            }
            ByteBigListIterator l = this.listIterator();
            if (i == 1L) {
                l.next();
            }
            return l;
        }

        @Override
        public ByteBigList subList(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            if (from != 0L || to != 1L) {
                return EMPTY_BIG_LIST;
            }
            return this;
        }

        @Override
        @Deprecated
        public int size() {
            return 1;
        }

        @Override
        public long size64() {
            return 1L;
        }

        @Override
        public void size(long size) {
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
        public boolean rem(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long i, ByteCollection c) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyBigList
    extends ByteCollections.EmptyCollection
    implements ByteBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public void add(long index, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(long i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte set(long index, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(byte k) {
            return -1L;
        }

        @Override
        public long lastIndexOf(byte k) {
            return -1L;
        }

        @Override
        public boolean addAll(Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long i, Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte get(long i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean addAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteBigList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long i, ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long i, ByteBigList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte set(long index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(long i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Byte remove(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(Object k) {
            return -1L;
        }

        @Override
        public long lastIndexOf(Object k) {
            return -1L;
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ByteBigListIterator iterator() {
            return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ByteBigListIterator listIterator(long i) {
            if (i == 0L) {
                return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        public ByteBigList subList(long from, long to) {
            if (from == 0L && to == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long from, byte[][] a, long offset, long length) {
            ByteBigArrays.ensureOffsetLength(a, offset, length);
            if (from != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long from, long to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long index, byte[][] a, long offset, long length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long index, byte[][] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(long s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 0L;
        }

        @Override
        public int compareTo(BigList<? extends Byte> o) {
            if (o == this) {
                return 0;
            }
            return o.isEmpty() ? 0 : -1;
        }

        private Object readResolve() {
            return EMPTY_BIG_LIST;
        }

        public Object clone() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BigList && ((BigList)o).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }
    }
}

