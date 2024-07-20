/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.AbstractObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceBigList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractReferenceBigList<K>
extends AbstractReferenceCollection<K>
implements ReferenceBigList<K>,
Stack<K> {
    protected AbstractReferenceBigList() {
    }

    protected void ensureIndex(long index) {
        if (index < 0L) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size64() + ")");
        }
    }

    protected void ensureRestrictedIndex(long index) {
        if (index < 0L) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size64() + ")");
        }
    }

    @Override
    public void add(long index, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(K k) {
        this.add(this.size64(), k);
        return true;
    }

    @Override
    public K remove(long i) {
        throw new UnsupportedOperationException();
    }

    public K remove(int i) {
        return this.remove((long)i);
    }

    @Override
    public K set(long index, K k) {
        throw new UnsupportedOperationException();
    }

    public K set(int index, K k) {
        return this.set((long)index, k);
    }

    @Override
    public boolean addAll(long index, Collection<? extends K> c) {
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

    public boolean addAll(int index, Collection<? extends K> c) {
        return this.addAll((long)index, c);
    }

    @Override
    public boolean addAll(Collection<? extends K> c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public ObjectBigListIterator<K> iterator() {
        return this.listIterator();
    }

    @Override
    public ObjectBigListIterator<K> listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public ObjectBigListIterator<K> listIterator(final long index) {
        this.ensureIndex(index);
        return new AbstractObjectBigListIterator<K>(){
            long pos;
            long last;
            {
                this.pos = index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractReferenceBigList.this.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractReferenceBigList.this.get(this.last);
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractReferenceBigList.this.get(this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(K k) {
                AbstractReferenceBigList.this.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(K k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractReferenceBigList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractReferenceBigList.this.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public ObjectBigListIterator<K> listIterator(int index) {
        return this.listIterator((long)index);
    }

    @Override
    public boolean contains(Object k) {
        return this.indexOf(k) >= 0L;
    }

    @Override
    public long indexOf(Object k) {
        BigListIterator i = this.listIterator();
        while (i.hasNext()) {
            Object e = i.next();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(Object k) {
        BigListIterator i = this.listIterator(this.size64());
        while (i.hasPrevious()) {
            Object e = i.previous();
            if (k != e) continue;
            return i.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long size) {
        long i = this.size64();
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

    public void size(int size) {
        this.size((long)size);
    }

    @Override
    public ReferenceBigList<K> subList(long from, long to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ReferenceSubList(this, from, to);
    }

    @Override
    public void removeElements(long from, long to) {
        this.ensureIndex(to);
        BigListIterator i = this.listIterator(from);
        long n = to - from;
        if (n < 0L) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0L) {
            i.next();
            i.remove();
        }
    }

    @Override
    public void addElements(long index, K[][] a, long offset, long length) {
        this.ensureIndex(index);
        ObjectBigArrays.ensureOffsetLength(a, offset, length);
        while (length-- != 0L) {
            this.add(index++, ObjectBigArrays.get(a, offset++));
        }
    }

    @Override
    public void addElements(long index, K[][] a) {
        this.addElements(index, a, 0L, ObjectBigArrays.length(a));
    }

    @Override
    public void getElements(long from, Object[][] a, long offset, long length) {
        BigListIterator i = this.listIterator(from);
        ObjectBigArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
        }
        while (length-- != 0L) {
            ObjectBigArrays.set(a, offset++, i.next());
        }
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BigList)) {
            return false;
        }
        BigList l = (BigList)o;
        long s = this.size64();
        if (s != l.size64()) {
            return false;
        }
        BigListIterator i1 = this.listIterator();
        BigListIterator i2 = l.listIterator();
        while (s-- != 0L) {
            if (i1.next() == i2.next()) continue;
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        ObjectIterator i = this.iterator();
        int h = 1;
        long s = this.size64();
        while (s-- != 0L) {
            Object k = i.next();
            h = 31 * h + System.identityHashCode(k);
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
        return this.remove(this.size64() - 1L);
    }

    @Override
    public K top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.get(this.size64() - 1L);
    }

    @Override
    public K peek(int i) {
        return this.get(this.size64() - 1L - (long)i);
    }

    public K get(int index) {
        return this.get((long)index);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.iterator();
        long n = this.size64();
        boolean first = true;
        s.append("[");
        while (n-- != 0L) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object k = i.next();
            if (this == k) {
                s.append("(this big list)");
                continue;
            }
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class ReferenceSubList<K>
    extends AbstractReferenceBigList<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceBigList<K> l;
        protected final long from;
        protected long to;
        private static final boolean ASSERTS = false;

        public ReferenceSubList(ReferenceBigList<K> l, long from, long to) {
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
        public void add(long index, K k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(long index, Collection<? extends K> c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public K get(long index) {
            this.ensureRestrictedIndex(index);
            return this.l.get(this.from + index);
        }

        @Override
        public K remove(long index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.remove(this.from + index);
        }

        @Override
        public K set(long index, K k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }

        @Override
        public void clear() {
            this.removeElements(0L, this.size64());
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long from, Object[][] a, long offset, long length) {
            this.ensureIndex(from);
            if (from + length > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }

        @Override
        public void removeElements(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
        }

        @Override
        public void addElements(long index, K[][] a, long offset, long length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public ObjectBigListIterator<K> listIterator(final long index) {
            this.ensureIndex(index);
            return new AbstractObjectBigListIterator<K>(){
                long pos;
                long last;
                {
                    this.pos = index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < ReferenceSubList.this.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return ReferenceSubList.this.l.get(ReferenceSubList.this.from + this.last);
                }

                @Override
                public K previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return ReferenceSubList.this.l.get(ReferenceSubList.this.from + this.pos);
                }

                @Override
                public long nextIndex() {
                    return this.pos;
                }

                @Override
                public long previousIndex() {
                    return this.pos - 1L;
                }

                @Override
                public void add(K k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ReferenceSubList.this.add(this.pos++, k);
                    this.last = -1L;
                }

                @Override
                public void set(K k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ReferenceSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ReferenceSubList.this.remove(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                }
            };
        }

        @Override
        public ReferenceBigList<K> subList(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ReferenceSubList<K>(this, from, to);
        }

        @Override
        public boolean remove(Object o) {
            long index = this.indexOf(o);
            if (index == -1L) {
                return false;
            }
            this.remove(index);
            return true;
        }
    }
}

