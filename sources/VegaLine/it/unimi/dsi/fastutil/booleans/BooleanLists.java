/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BooleanLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private BooleanLists() {
    }

    public static BooleanList shuffle(BooleanList l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            boolean t = l.getBoolean(i);
            l.set(i, l.getBoolean(p));
            l.set(p, t);
        }
        return l;
    }

    public static BooleanList singleton(boolean element) {
        return new Singleton(element);
    }

    public static BooleanList singleton(Object element) {
        return new Singleton((Boolean)element);
    }

    public static BooleanList synchronize(BooleanList l) {
        return new SynchronizedList(l);
    }

    public static BooleanList synchronize(BooleanList l, Object sync) {
        return new SynchronizedList(l, sync);
    }

    public static BooleanList unmodifiable(BooleanList l) {
        return new UnmodifiableList(l);
    }

    public static class UnmodifiableList
    extends BooleanCollections.UnmodifiableCollection
    implements BooleanList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanList list;

        protected UnmodifiableList(BooleanList l) {
            super(l);
            this.list = l;
        }

        @Override
        public boolean getBoolean(int i) {
            return this.list.getBoolean(i);
        }

        @Override
        public boolean set(int i, boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(boolean k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(boolean k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Boolean> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, boolean[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, boolean[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, boolean[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public BooleanListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public BooleanListIterator listIterator() {
            return BooleanIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public BooleanListIterator listIterator(int i) {
            return BooleanIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public BooleanList subList(int from, int to) {
            return BooleanLists.unmodifiable(this.list.subList(from, to));
        }

        @Override
        @Deprecated
        public BooleanList booleanSubList(int from, int to) {
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
        public int compareTo(List<? extends Boolean> o) {
            return this.list.compareTo(o);
        }

        @Override
        public boolean addAll(int index, BooleanCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, BooleanList l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean get(int i) {
            return (Boolean)this.list.get(i);
        }

        @Override
        public void add(int i, Boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean set(int index, Boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean remove(int i) {
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
    extends BooleanCollections.SynchronizedCollection
    implements BooleanList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanList list;

        protected SynchronizedList(BooleanList l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(BooleanList l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean getBoolean(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getBoolean(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean set(int i, boolean k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, boolean k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeBoolean(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeBoolean(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(boolean k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(boolean k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends Boolean> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, boolean[] a, int offset, int length) {
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
        public void addElements(int index, boolean[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, boolean[] a) {
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
        public BooleanListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public BooleanListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public BooleanListIterator listIterator(int i) {
            return this.list.listIterator(i);
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator(int i) {
            return this.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BooleanList subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return BooleanLists.synchronize(this.list.subList(from, to), this.sync);
            }
        }

        @Override
        @Deprecated
        public BooleanList booleanSubList(int from, int to) {
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
        public int compareTo(List<? extends Boolean> o) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, BooleanCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, BooleanList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(BooleanList l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Boolean)this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, Boolean k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean set(int index, Boolean k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(index, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return (Boolean)this.list.remove(i);
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
    extends AbstractBooleanList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final boolean element;

        private Singleton(boolean element) {
            this.element = element;
        }

        @Override
        public boolean getBoolean(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean removeBoolean(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(boolean k) {
            return k == this.element;
        }

        @Override
        public boolean addAll(Collection<? extends Boolean> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Boolean> c) {
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
        public boolean[] toBooleanArray() {
            boolean[] a = new boolean[]{this.element};
            return a;
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
        public BooleanListIterator listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            BooleanListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public BooleanList subList(int from, int to) {
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
        public boolean rem(boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, BooleanCollection c) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyList
    extends BooleanCollections.EmptyCollection
    implements BooleanList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public void add(int index, boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeBoolean(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean set(int index, boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(boolean k) {
            return -1;
        }

        @Override
        public int lastIndexOf(boolean k) {
            return -1;
        }

        @Override
        public boolean addAll(Collection<? extends Boolean> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends Boolean> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean addAll(BooleanCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(BooleanList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, BooleanCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, BooleanList c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, Boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean set(int index, Boolean k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Boolean remove(int k) {
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
        public BooleanIterator booleanIterator() {
            return BooleanIterators.EMPTY_ITERATOR;
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
        public BooleanListIterator listIterator(int i) {
            if (i == 0) {
                return BooleanIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public BooleanListIterator booleanListIterator(int i) {
            return this.listIterator(i);
        }

        @Override
        public BooleanList subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public BooleanList booleanSubList(int from, int to) {
            return this.subList(from, to);
        }

        @Override
        public void getElements(int from, boolean[] a, int offset, int length) {
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
        public void addElements(int index, boolean[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, boolean[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Boolean> o) {
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

