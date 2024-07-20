/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LongLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private LongLists() {
    }

    public static LongList shuffle(LongList l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            long t = l.getLong(i);
            l.set(i, l.getLong(p));
            l.set(p, t);
        }
        return l;
    }

    public static LongList singleton(long element) {
        return new Singleton(element);
    }

    public static LongList singleton(Object element) {
        return new Singleton((Long)element);
    }

    public static LongList synchronize(LongList l) {
        return new SynchronizedList(l);
    }

    public static LongList synchronize(LongList l, Object sync) {
        return new SynchronizedList(l, sync);
    }

    public static LongList unmodifiable(LongList l) {
        return new UnmodifiableList(l);
    }

    public static class UnmodifiableList
    extends LongCollections.UnmodifiableCollection
    implements LongList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongList list;

        protected UnmodifiableList(LongList l) {
            super(l);
            this.list = l;
        }

        @Override
        public long getLong(int i) {
            return this.list.getLong(i);
        }

        @Override
        public long set(int i, long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(long k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(long k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, long[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, long[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, long[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public LongListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public LongListIterator listIterator() {
            return LongIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public LongListIterator listIterator(int i) {
            return LongIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public LongList subList(int from, int to) {
            return LongLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        @Deprecated
        public LongList longSubList(int from, int to) {
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
        public int compareTo(List<? extends Long> o) {
            return this.list.compareTo(o);
        }

        @Override
        public boolean addAll(int index, LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, LongList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long get(int i) {
            return (Long)this.list.get(i);
        }

        @Override
        public void add(int i, Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long set(int index, Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long remove(int i) {
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
    extends LongCollections.SynchronizedCollection
    implements LongList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongList list;

        protected SynchronizedList(LongList l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(LongList l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long getLong(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getLong(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long set(int i, long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, long k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long removeLong(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeLong(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends Long> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, long[] a, int offset, int length) {
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
        public void addElements(int index, long[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, long[] a) {
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
        public LongListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public LongListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public LongListIterator listIterator(int i) {
            return this.list.listIterator(i);
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator(int i) {
            return this.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongList subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return LongLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        @Override
        @Deprecated
        public LongList longSubList(int from, int to) {
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
        public int compareTo(List<? extends Long> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, LongCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, LongList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(LongList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, Long k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long set(int index, Long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(index, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.list.remove(i);
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
    extends AbstractLongList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final long element;

        private Singleton(long element) {
            this.element = element;
        }

        @Override
        public long getLong(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public long removeLong(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(long k) {
            return k == this.element;
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Long> c) {
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
        public long[] toLongArray() {
            long[] a = new long[]{this.element};
            return a;
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
        public LongListIterator listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            LongListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public LongList subList(int from, int to) {
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
        public boolean rem(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, LongCollection c) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyList
    extends LongCollections.EmptyCollection
    implements LongList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public void add(int index, long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long removeLong(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long set(int index, long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(long k) {
            return -1;
        }

        @Override
        public int lastIndexOf(long k) {
            return -1;
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean addAll(LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(LongList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, LongCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, LongList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long set(int index, Long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getLong(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Long remove(int k) {
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
        public LongIterator longIterator() {
            return LongIterators.EMPTY_ITERATOR;
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
        public LongListIterator listIterator(int i) {
            if (i == 0) {
                return LongIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public LongListIterator longListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public LongList subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public LongList longSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public void getElements(int from, long[] a, int offset, int length) {
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
        public void addElements(int index, long[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, long[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Long> o) {
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

