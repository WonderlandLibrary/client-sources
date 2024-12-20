/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractObjectList<K>
extends AbstractObjectCollection<K>
implements ObjectList<K>,
Stack<K> {
    protected AbstractObjectList() {
    }

    protected void ensureIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void ensureRestrictedIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    @Override
    public void add(int index, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(K k) {
        this.add(this.size(), k);
        return true;
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
    public boolean addAll(int index, Collection<? extends K> c) {
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        Iterator<K> i = c.iterator();
        while (n-- != 0) {
            this.add(index++, i.next());
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends K> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    @Deprecated
    public ObjectListIterator<K> objectListIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public ObjectListIterator<K> objectListIterator(int index) {
        return this.listIterator(index);
    }

    @Override
    public ObjectListIterator<K> iterator() {
        return this.listIterator();
    }

    @Override
    public ObjectListIterator<K> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ObjectListIterator<K> listIterator(final int index) {
        this.ensureIndex(index);
        return new AbstractObjectListIterator<K>(){
            int pos;
            int last;
            {
                this.pos = index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractObjectList.this.size();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractObjectList.this.get(this.last);
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractObjectList.this.get(this.pos);
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(K k) {
                AbstractObjectList.this.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(K k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractObjectList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractObjectList.this.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(Object k) {
        return this.indexOf(k) >= 0;
    }

    @Override
    public int indexOf(Object k) {
        ListIterator i = this.listIterator();
        while (i.hasNext()) {
            Object e = i.next();
            if (!(k == null ? e == null : k.equals(e))) continue;
            return i.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object k) {
        ListIterator i = this.listIterator(this.size());
        while (i.hasPrevious()) {
            Object e = i.previous();
            if (!(k == null ? e == null : k.equals(e))) continue;
            return i.nextIndex();
        }
        return -1;
    }

    @Override
    public void size(int size) {
        int i = this.size();
        if (size > i) {
            while (i++ < size) {
                this.add((K)null);
            }
        } else {
            while (i-- != size) {
                this.remove(i);
            }
        }
    }

    @Override
    public ObjectList<K> subList(int from, int to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ObjectSubList(this, from, to);
    }

    @Override
    @Deprecated
    public ObjectList<K> objectSubList(int from, int to) {
        return this.subList(from, to);
    }

    @Override
    public void removeElements(int from, int to) {
        this.ensureIndex(to);
        ListIterator i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.next();
            i.remove();
        }
    }

    @Override
    public void addElements(int index, K[] a, int offset, int length) {
        this.ensureIndex(index);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        while (length-- != 0) {
            this.add(index++, a[offset++]);
        }
    }

    @Override
    public void addElements(int index, K[] a) {
        this.addElements(index, a, 0, a.length);
    }

    @Override
    public void getElements(int from, Object[] a, int offset, int length) {
        ListIterator i = this.listIterator(from);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
        }
        while (length-- != 0) {
            a[offset++] = i.next();
        }
    }

    private boolean valEquals(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        List l = (List)o;
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        ListIterator i1 = this.listIterator();
        ListIterator i2 = l.listIterator();
        while (s-- != 0) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(List<? extends K> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof ObjectList) {
            ListIterator i1 = this.listIterator();
            ObjectListIterator i2 = ((ObjectList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                Object e2;
                Object e1 = i1.next();
                int r = ((Comparable)e1).compareTo(e2 = i2.next());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        ListIterator i1 = this.listIterator();
        ListIterator<K> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        ObjectIterator i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
            Object k = i.next();
            h = 31 * h + (k == null ? 0 : k.hashCode());
        }
        return h;
    }

    @Override
    public void push(K o) {
        this.add(o);
    }

    @Override
    public K pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(this.size() - 1);
    }

    @Override
    public K top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (K)this.get(this.size() - 1);
    }

    @Override
    public K peek(int i) {
        return (K)this.get(this.size() - 1 - i);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object k = i.next();
            if (this == k) {
                s.append("(this list)");
                continue;
            }
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class ObjectSubList<K>
    extends AbstractObjectList<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList<K> l;
        protected final int from;
        protected int to;
        private static final boolean ASSERTS = false;

        public ObjectSubList(ObjectList<K> l, int from, int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private void assertRange() {
        }

        @Override
        public boolean add(K k) {
            this.l.add(this.to, k);
            ++this.to;
            return true;
        }

        @Override
        public void add(int index, K k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(int index, Collection<? extends K> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public K get(int index) {
            this.ensureRestrictedIndex(index);
            return (K)this.l.get(this.from + index);
        }

        @Override
        public K remove(int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return (K)this.l.remove(this.from + index);
        }

        @Override
        public K set(int index, K k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }

        @Override
        public void clear() {
            this.removeElements(0, this.size());
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
            this.ensureIndex(from);
            if (from + length > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
        }

        @Override
        public void addElements(int index, K[] a, int offset, int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public ObjectListIterator<K> listIterator(final int index) {
            this.ensureIndex(index);
            return new AbstractObjectListIterator<K>(){
                int pos;
                int last;
                {
                    this.pos = index;
                    this.last = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < ObjectSubList.this.size();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0;
                }

                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return ObjectSubList.this.l.get(ObjectSubList.this.from + this.last);
                }

                @Override
                public K previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return ObjectSubList.this.l.get(ObjectSubList.this.from + this.pos);
                }

                @Override
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.add(this.pos++, k);
                    this.last = -1;
                }

                @Override
                public void set(K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ObjectSubList.this.remove(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                }
            };
        }

        @Override
        public ObjectList<K> subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ObjectSubList<K>(this, from, to);
        }

        @Override
        public boolean remove(Object o) {
            int index = this.indexOf(o);
            if (index == -1) {
                return false;
            }
            this.remove(index);
            return true;
        }
    }
}

