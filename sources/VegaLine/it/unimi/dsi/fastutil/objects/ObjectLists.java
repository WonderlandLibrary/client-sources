/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class ObjectLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ObjectLists() {
    }

    public static <K> ObjectList<K> shuffle(ObjectList<K> l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            Object t = l.get(i);
            l.set(i, l.get(p));
            l.set(p, t);
        }
        return l;
    }

    public static <K> ObjectList<K> emptyList() {
        return EMPTY_LIST;
    }

    public static <K> ObjectList<K> singleton(K element) {
        return new Singleton(element);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> l) {
        return new SynchronizedList<K>(l);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> l, Object sync) {
        return new SynchronizedList<K>(l, sync);
    }

    public static <K> ObjectList<K> unmodifiable(ObjectList<K> l) {
        return new UnmodifiableList<K>(l);
    }

    public static class UnmodifiableList<K>
    extends ObjectCollections.UnmodifiableCollection<K>
    implements ObjectList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList<K> list;

        protected UnmodifiableList(ObjectList<K> l) {
            super(l);
            this.list = l;
        }

        @Override
        public K get(int i) {
            return (K)this.list.get(i);
        }

        @Override
        public K set(int i, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(Object k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            return ObjectIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ObjectList<K> subList(int from, int to) {
            return ObjectLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        @Deprecated
        public ObjectList<K> objectSubList(int from, int to) {
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
        public int compareTo(List<? extends K> o) {
            return this.list.compareTo(o);
        }
    }

    public static class SynchronizedList<K>
    extends ObjectCollections.SynchronizedCollection<K>
    implements ObjectList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList<K> list;

        protected SynchronizedList(ObjectList<K> l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(ObjectList<K> l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (K)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K set(int i, K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, K k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (K)this.list.remove(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends K> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
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
        public void addElements(int index, K[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, K[] a) {
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
        public ObjectListIterator<K> iterator() {
            return this.list.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            return this.list.listIterator(i);
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator(int i) {
            return this.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectList<K> subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return ObjectLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        @Override
        @Deprecated
        public ObjectList<K> objectSubList(int from, int to) {
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
        public int compareTo(List<? extends K> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }
    }

    public static class Singleton<K>
    extends AbstractObjectList<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final K element;

        private Singleton(K element) {
            this.element = element;
        }

        @Override
        public K get(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return k == null ? this.element == null : k.equals(this.element);
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends K> c) {
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
        public Object[] toArray() {
            Object[] a = new Object[]{this.element};
            return a;
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public ObjectList<K> subList(int from, int to) {
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
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyList<K>
    extends ObjectCollections.EmptyCollection<K>
    implements ObjectList<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public void add(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K set(int index, K k) {
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
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public ObjectIterator<K> objectIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            if (i == 0) {
                return ObjectIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public ObjectListIterator<K> objectListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public ObjectList<K> subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public ObjectList<K> objectSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
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
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends K> o) {
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

