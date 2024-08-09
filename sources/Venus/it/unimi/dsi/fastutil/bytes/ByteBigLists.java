/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterators;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class ByteBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private ByteBigLists() {
    }

    public static ByteBigList shuffle(ByteBigList byteBigList, Random random2) {
        long l = byteBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            byte by = byteBigList.getByte(l);
            byteBigList.set(l, byteBigList.getByte(l2));
            byteBigList.set(l2, by);
        }
        return byteBigList;
    }

    public static ByteBigList singleton(byte by) {
        return new Singleton(by);
    }

    public static ByteBigList singleton(Object object) {
        return new Singleton((Byte)object);
    }

    public static ByteBigList synchronize(ByteBigList byteBigList) {
        return new SynchronizedBigList(byteBigList);
    }

    public static ByteBigList synchronize(ByteBigList byteBigList, Object object) {
        return new SynchronizedBigList(byteBigList, object);
    }

    public static ByteBigList unmodifiable(ByteBigList byteBigList) {
        return new UnmodifiableBigList(byteBigList);
    }

    public static ByteBigList asBigList(ByteList byteList) {
        return new ListBigList(byteList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractByteBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final ByteList list;

        protected ListBigList(ByteList byteList) {
            this.list = byteList;
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
        public ByteBigListIterator iterator() {
            return ByteBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            return ByteBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Byte> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public ByteBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(byte by) {
            return this.list.contains(by);
        }

        @Override
        public byte[] toByteArray() {
            return this.list.toByteArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public byte[] toByteArray(byte[] byArray) {
            return this.list.toArray(byArray);
        }

        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
            return this.list.addAll(this.intIndex(l), byteCollection);
        }

        @Override
        public boolean addAll(ByteCollection byteCollection) {
            return this.list.addAll(byteCollection);
        }

        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            return this.list.addAll(this.intIndex(l), byteBigList);
        }

        @Override
        public boolean addAll(ByteBigList byteBigList) {
            return this.list.addAll(byteBigList);
        }

        @Override
        public boolean containsAll(ByteCollection byteCollection) {
            return this.list.containsAll(byteCollection);
        }

        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            return this.list.removeAll(byteCollection);
        }

        @Override
        public boolean retainAll(ByteCollection byteCollection) {
            return this.list.retainAll(byteCollection);
        }

        @Override
        public void add(long l, byte by) {
            this.list.add(this.intIndex(l), by);
        }

        @Override
        public boolean add(byte by) {
            return this.list.add(by);
        }

        @Override
        public byte getByte(long l) {
            return this.list.getByte(this.intIndex(l));
        }

        @Override
        public long indexOf(byte by) {
            return this.list.indexOf(by);
        }

        @Override
        public long lastIndexOf(byte by) {
            return this.list.lastIndexOf(by);
        }

        @Override
        public byte removeByte(long l) {
            return this.list.removeByte(this.intIndex(l));
        }

        @Override
        public byte set(long l, byte by) {
            return this.list.set(this.intIndex(l), by);
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
        public boolean addAll(Collection<? extends Byte> collection) {
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
        public ByteIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends ByteCollections.UnmodifiableCollection
    implements ByteBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList list;

        protected UnmodifiableBigList(ByteBigList byteBigList) {
            super(byteBigList);
            this.list = byteBigList;
        }

        @Override
        public byte getByte(long l) {
            return this.list.getByte(l);
        }

        @Override
        public byte set(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(byte by) {
            return this.list.indexOf(by);
        }

        @Override
        public long lastIndexOf(byte by) {
            return this.list.lastIndexOf(by);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, byte[][] byArray, long l2, long l3) {
            this.list.getElements(l, byArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, byte[][] byArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, byte[][] byArray) {
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
        public ByteBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            return ByteBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public ByteBigList subList(long l, long l2) {
            return ByteBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Byte> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte set(long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte remove(long l) {
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
        public ByteIterator iterator() {
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
            this.add(l, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Byte)object);
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
    extends ByteCollections.SynchronizedCollection
    implements ByteBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList list;

        protected SynchronizedBigList(ByteBigList byteBigList, Object object) {
            super(byteBigList, object);
            this.list = byteBigList;
        }

        protected SynchronizedBigList(ByteBigList byteBigList) {
            super(byteBigList);
            this.list = byteBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getByte(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte set(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeByte(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Byte> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, byte[][] byArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, byArray, l2, l3);
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
        public void addElements(long l, byte[][] byArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, byArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, byte[][] byArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, byArray);
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
        public ByteBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return ByteBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Byte> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, byteCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, byteBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ByteBigList byteBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(byteBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte get(long l) {
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
        public Byte set(long l, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte remove(long l) {
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
        public ByteIterator iterator() {
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
            this.add(l, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Byte)object);
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
    extends AbstractByteBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final byte element;

        protected Singleton(byte by) {
            this.element = by;
        }

        @Override
        public byte getByte(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(long l) {
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
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.singleton(this.element);
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            ByteBigListIterator byteBigListIterator = this.listIterator();
            if (l == 1L) {
                byteBigListIterator.nextByte();
            }
            return byteBigListIterator;
        }

        @Override
        public ByteBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Byte> collection) {
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
        public boolean addAll(ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
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
    extends ByteCollections.EmptyCollection
    implements ByteBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public byte getByte(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte removeByte(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte set(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(byte by) {
            return -1L;
        }

        @Override
        public long lastIndexOf(byte by) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Byte set(long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte remove(long l) {
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
        public ByteBigListIterator listIterator() {
            return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ByteBigListIterator iterator() {
            return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            if (l == 0L) {
                return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public ByteBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, byte[][] byArray, long l2, long l3) {
            ByteBigArrays.ensureOffsetLength(byArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, byte[][] byArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, byte[][] byArray) {
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
        public int compareTo(BigList<? extends Byte> bigList) {
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
            this.add(l, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Byte)object);
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

