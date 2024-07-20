/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteList;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ByteLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ByteLists() {
    }

    public static ByteList shuffle(ByteList l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            byte t = l.getByte(i);
            l.set(i, l.getByte(p));
            l.set(p, t);
        }
        return l;
    }

    public static ByteList singleton(byte element) {
        return new Singleton(element);
    }

    public static ByteList singleton(Object element) {
        return new Singleton((Byte)element);
    }

    public static ByteList synchronize(ByteList l) {
        return new SynchronizedList(l);
    }

    public static ByteList synchronize(ByteList l, Object sync) {
        return new SynchronizedList(l, sync);
    }

    public static ByteList unmodifiable(ByteList l) {
        return new UnmodifiableList(l);
    }

    public static class UnmodifiableList
    extends ByteCollections.UnmodifiableCollection
    implements ByteList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList list;

        protected UnmodifiableList(ByteList l) {
            super(l);
            this.list = l;
        }

        @Override
        public byte getByte(int i) {
            return this.list.getByte(i);
        }

        @Override
        public byte set(int i, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(byte k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(byte k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, byte[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, byte[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, byte[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public ByteListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteListIterator listIterator() {
            return ByteIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ByteListIterator listIterator(int i) {
            return ByteIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ByteList subList(int from, int to) {
            return ByteLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        @Deprecated
        public ByteList byteSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public boolean equals(Object o) {
            return this.collection.equals(o);
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        public int compareTo(List<? extends Byte> o) {
            return this.list.compareTo(o);
        }

        @Override
        public boolean addAll(int index, ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, ByteList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte get(int i) {
            return (Byte)this.list.get(i);
        }

        @Override
        public void add(int i, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte set(int index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object o) {
            return this.list.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return this.list.lastIndexOf(o);
        }
    }

    public static class SynchronizedList
    extends ByteCollections.SynchronizedCollection
    implements ByteList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList list;

        protected SynchronizedList(ByteList l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(ByteList l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getByte(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte set(int i, byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, byte k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeByte(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends Byte> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, byte[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(from, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(from, to);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, byte[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, byte[] a) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(int size) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(size);
            }
        }

        @Override
        public ByteListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteListIterator listIterator(int i) {
            return this.list.listIterator(i);
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator(int i) {
            return this.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteList subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return ByteLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        @Override
        @Deprecated
        public ByteList byteSubList(int from, int to) {
            return this.subList(from, to);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.equals(o);
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
        public int compareTo(List<? extends Byte> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, ByteCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, ByteList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, Byte k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte set(int index, Byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(index, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.list.remove(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(o);
            }
        }
    }

    public static class Singleton
    extends AbstractByteList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final byte element;

        private Singleton(byte element) {
            this.element = element;
        }

        @Override
        public byte getByte(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public byte removeByte(int i) {
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
        public boolean addAll(int i, Collection<? extends Byte> c) {
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
        public ByteListIterator listIterator() {
            return ByteIterators.singleton(this.element);
        }

        @Override
        public ByteListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteListIterator listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ByteListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public ByteList subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            if (from != 0 || to != 1) {
                return EMPTY_LIST;
            }
            return this;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void size(int size) {
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
        public boolean addAll(int i, ByteCollection c) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyList
    extends ByteCollections.EmptyCollection
    implements ByteList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public void add(int index, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte set(int index, byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(byte k) {
            return -1;
        }

        @Override
        public int lastIndexOf(byte k) {
            return -1;
        }

        @Override
        public boolean addAll(Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Byte> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean addAll(ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, ByteCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, ByteList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte set(int index, Byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Byte remove(int k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object k) {
            return -1;
        }

        @Override
        public int lastIndexOf(Object k) {
            return -1;
        }

        @Override
        @Deprecated
        public ByteIterator byteIterator() {
            return ByteIterators.EMPTY_ITERATOR;
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
        public ByteListIterator listIterator(int i) {
            if (i == 0) {
                return ByteIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ByteListIterator byteListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ByteList subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public ByteList byteSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public void getElements(int from, byte[] a, int offset, int length) {
            if (from == 0 && length == 0 && offset >= 0 && offset <= a.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, byte[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, byte[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Byte> o) {
            if (o == this) {
                return 0;
            }
            return o.isEmpty() ? 0 : -1;
        }

        private Object readResolve() {
            return EMPTY_LIST;
        }

        public Object clone() {
            return EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof List && ((List)o).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }
    }
}

