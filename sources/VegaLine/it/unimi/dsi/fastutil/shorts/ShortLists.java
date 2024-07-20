/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortList;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ShortLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ShortLists() {
    }

    public static ShortList shuffle(ShortList l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            short t = l.getShort(i);
            l.set(i, l.getShort(p));
            l.set(p, t);
        }
        return l;
    }

    public static ShortList singleton(short element) {
        return new Singleton(element);
    }

    public static ShortList singleton(Object element) {
        return new Singleton((Short)element);
    }

    public static ShortList synchronize(ShortList l) {
        return new SynchronizedList(l);
    }

    public static ShortList synchronize(ShortList l, Object sync) {
        return new SynchronizedList(l, sync);
    }

    public static ShortList unmodifiable(ShortList l) {
        return new UnmodifiableList(l);
    }

    public static class UnmodifiableList
    extends ShortCollections.UnmodifiableCollection
    implements ShortList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortList list;

        protected UnmodifiableList(ShortList l) {
            super(l);
            this.list = l;
        }

        @Override
        public short getShort(int i) {
            return this.list.getShort(i);
        }

        @Override
        public short set(int i, short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(short k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(short k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Short> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, short[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, short[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, short[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public ShortListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public ShortListIterator listIterator() {
            return ShortIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ShortListIterator listIterator(int i) {
            return ShortIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ShortList subList(int from, int to) {
            return ShortLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        @Deprecated
        public ShortList shortSubList(int from, int to) {
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
        public int compareTo(List<? extends Short> o) {
            return this.list.compareTo(o);
        }

        @Override
        public boolean addAll(int index, ShortCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, ShortList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short get(int i) {
            return (Short)this.list.get(i);
        }

        @Override
        public void add(int i, Short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short set(int index, Short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short remove(int i) {
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
    extends ShortCollections.SynchronizedCollection
    implements ShortList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortList list;

        protected SynchronizedList(ShortList l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(ShortList l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short getShort(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getShort(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short set(int i, short k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, short k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short removeShort(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeShort(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(short k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(short k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends Short> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, short[] a, int offset, int length) {
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
        public void addElements(int index, short[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, short[] a) {
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
        public ShortListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public ShortListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ShortListIterator listIterator(int i) {
            return this.list.listIterator(i);
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator(int i) {
            return this.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortList subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return ShortLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        @Override
        @Deprecated
        public ShortList shortSubList(int from, int to) {
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
        public int compareTo(List<? extends Short> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, ShortCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, ShortList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(ShortList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, Short k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short set(int index, Short k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(index, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.list.remove(i);
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
    extends AbstractShortList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final short element;

        private Singleton(short element) {
            this.element = element;
        }

        @Override
        public short getShort(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public short removeShort(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(short k) {
            return k == this.element;
        }

        @Override
        public boolean addAll(Collection<? extends Short> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Short> c) {
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
        public short[] toShortArray() {
            short[] a = new short[]{this.element};
            return a;
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
        public ShortListIterator listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ShortListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public ShortList subList(int from, int to) {
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
        public boolean rem(short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, ShortCollection c) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyList
    extends ShortCollections.EmptyCollection
    implements ShortList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public void add(int index, short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short removeShort(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short set(int index, short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(short k) {
            return -1;
        }

        @Override
        public int lastIndexOf(short k) {
            return -1;
        }

        @Override
        public boolean addAll(Collection<? extends Short> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Short> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean addAll(ShortCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(ShortList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, ShortCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, ShortList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, Short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short set(int index, Short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short getShort(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Short remove(int k) {
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
        public ShortIterator shortIterator() {
            return ShortIterators.EMPTY_ITERATOR;
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
        public ShortListIterator listIterator(int i) {
            if (i == 0) {
                return ShortIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ShortListIterator shortListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ShortList subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public ShortList shortSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public void getElements(int from, short[] a, int offset, int length) {
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
        public void addElements(int index, short[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, short[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Short> o) {
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

