/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongBigListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongBigList;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractLongBigList
extends AbstractLongCollection
implements LongBigList,
LongStack {
    protected AbstractLongBigList() {
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
    public void add(long index, long k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(long k) {
        this.add(this.size64(), k);
        return true;
    }

    @Override
    public long removeLong(long i) {
        throw new UnsupportedOperationException();
    }

    public long removeLong(int i) {
        return this.removeLong((long)i);
    }

    @Override
    public long set(long index, long k) {
        throw new UnsupportedOperationException();
    }

    public long set(int index, long k) {
        return this.set((long)index, k);
    }

    @Override
    public boolean addAll(long index, Collection<? extends Long> c) {
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        Iterator<? extends Long> i = c.iterator();
        while (n-- != 0) {
            this.add(index++, i.next());
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends Long> c) {
        return this.addAll((long)index, c);
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public LongBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public LongBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public LongBigListIterator listIterator(final long index) {
        this.ensureIndex(index);
        return new AbstractLongBigListIterator(){
            long pos;
            long last;
            {
                this.pos = index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractLongBigList.this.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractLongBigList.this.getLong(this.last);
            }

            @Override
            public long previousLong() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractLongBigList.this.getLong(this.pos);
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
            public void add(long k) {
                AbstractLongBigList.this.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(long k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractLongBigList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractLongBigList.this.removeLong(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public LongBigListIterator listIterator(int index) {
        return this.listIterator((long)index);
    }

    @Override
    public boolean contains(long k) {
        return this.indexOf(k) >= 0L;
    }

    @Override
    public long indexOf(long k) {
        LongBigListIterator i = this.listIterator();
        while (i.hasNext()) {
            long e = i.nextLong();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(long k) {
        LongBigListIterator i = this.listIterator(this.size64());
        while (i.hasPrevious()) {
            long e = i.previousLong();
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
                this.add(0L);
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
    public LongBigList subList(long from, long to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new LongSubList(this, from, to);
    }

    @Override
    public void removeElements(long from, long to) {
        this.ensureIndex(to);
        LongBigListIterator i = this.listIterator(from);
        long n = to - from;
        if (n < 0L) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0L) {
            i.nextLong();
            i.remove();
        }
    }

    @Override
    public void addElements(long index, long[][] a, long offset, long length) {
        this.ensureIndex(index);
        LongBigArrays.ensureOffsetLength(a, offset, length);
        while (length-- != 0L) {
            this.add(index++, LongBigArrays.get(a, offset++));
        }
    }

    @Override
    public void addElements(long index, long[][] a) {
        this.addElements(index, a, 0L, LongBigArrays.length(a));
    }

    @Override
    public void getElements(long from, long[][] a, long offset, long length) {
        LongBigListIterator i = this.listIterator(from);
        LongBigArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
        }
        while (length-- != 0L) {
            LongBigArrays.set(a, offset++, i.nextLong());
        }
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    private boolean valEquals(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
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
        if (l instanceof LongBigList) {
            LongBigListIterator i1 = this.listIterator();
            LongBigListIterator i2 = ((LongBigList)l).listIterator();
            while (s-- != 0L) {
                if (i1.nextLong() == i2.nextLong()) continue;
                return false;
            }
            return true;
        }
        LongBigListIterator i1 = this.listIterator();
        BigListIterator i2 = l.listIterator();
        while (s-- != 0L) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(BigList<? extends Long> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof LongBigList) {
            LongBigListIterator i1 = this.listIterator();
            LongBigListIterator i2 = ((LongBigList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                long e2;
                long e1 = i1.nextLong();
                int r = Long.compare(e1, e2 = i2.nextLong());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        LongBigListIterator i1 = this.listIterator();
        BigListIterator<? extends Long> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        LongBigListIterator i = this.iterator();
        int h = 1;
        long s = this.size64();
        while (s-- != 0L) {
            long k = i.nextLong();
            h = 31 * h + HashCommon.long2int(k);
        }
        return h;
    }

    @Override
    public void push(long o) {
        this.add(o);
    }

    @Override
    public long popLong() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeLong(this.size64() - 1L);
    }

    @Override
    public long topLong() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getLong(this.size64() - 1L);
    }

    @Override
    public long peekLong(int i) {
        return this.getLong(this.size64() - 1L - (long)i);
    }

    public long getLong(int index) {
        return this.getLong((long)index);
    }

    @Override
    public boolean rem(long k) {
        long index = this.indexOf(k);
        if (index == -1L) {
            return false;
        }
        this.removeLong(index);
        return true;
    }

    @Override
    public boolean addAll(long index, LongCollection c) {
        return this.addAll(index, (Collection<? extends Long>)c);
    }

    @Override
    public boolean addAll(long index, LongBigList l) {
        return this.addAll(index, (LongCollection)l);
    }

    @Override
    public boolean addAll(LongCollection c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public boolean addAll(LongBigList l) {
        return this.addAll(this.size64(), l);
    }

    @Override
    @Deprecated
    public void add(long index, Long ok) {
        this.add(index, (long)ok);
    }

    @Override
    @Deprecated
    public Long set(long index, Long ok) {
        return this.set(index, (long)ok);
    }

    @Override
    @Deprecated
    public Long get(long index) {
        return this.getLong(index);
    }

    @Override
    @Deprecated
    public long indexOf(Object ok) {
        return this.indexOf((Long)ok);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object ok) {
        return this.lastIndexOf((Long)ok);
    }

    @Deprecated
    public Long remove(int index) {
        return this.removeLong(index);
    }

    @Override
    @Deprecated
    public Long remove(long index) {
        return this.removeLong(index);
    }

    @Override
    @Deprecated
    public void push(Long o) {
        this.push((long)o);
    }

    @Override
    @Deprecated
    public Long pop() {
        return this.popLong();
    }

    @Override
    @Deprecated
    public Long top() {
        return this.topLong();
    }

    @Override
    @Deprecated
    public Long peek(int i) {
        return this.peekLong(i);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        LongBigListIterator i = this.iterator();
        long n = this.size64();
        boolean first = true;
        s.append("[");
        while (n-- != 0L) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            long k = i.nextLong();
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class LongSubList
    extends AbstractLongBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongBigList l;
        protected final long from;
        protected long to;
        private static final boolean ASSERTS = false;

        public LongSubList(LongBigList l, long from, long to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private void assertRange() {
        }

        @Override
        public boolean add(long k) {
            this.l.add(this.to, k);
            ++this.to;
            return true;
        }

        @Override
        public void add(long index, long k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(long index, Collection<? extends Long> c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public long getLong(long index) {
            this.ensureRestrictedIndex(index);
            return this.l.getLong(this.from + index);
        }

        @Override
        public long removeLong(long index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeLong(this.from + index);
        }

        @Override
        public long set(long index, long k) {
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
        public void getElements(long from, long[][] a, long offset, long length) {
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
        public void addElements(long index, long[][] a, long offset, long length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public LongBigListIterator listIterator(final long index) {
            this.ensureIndex(index);
            return new AbstractLongBigListIterator(){
                long pos;
                long last;
                {
                    this.pos = index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < LongSubList.this.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public long nextLong() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return LongSubList.this.l.getLong(LongSubList.this.from + this.last);
                }

                @Override
                public long previousLong() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return LongSubList.this.l.getLong(LongSubList.this.from + this.pos);
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
                public void add(long k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    LongSubList.this.add(this.pos++, k);
                    this.last = -1L;
                }

                @Override
                public void set(long k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    LongSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    LongSubList.this.removeLong(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                }
            };
        }

        @Override
        public LongBigList subList(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new LongSubList(this, from, to);
        }

        @Override
        public boolean rem(long k) {
            long index = this.indexOf(k);
            if (index == -1L) {
                return false;
            }
            --this.to;
            this.l.removeLong(this.from + index);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return this.rem((Long)o);
        }

        @Override
        public boolean addAll(long index, LongCollection c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        public boolean addAll(long index, LongList l) {
            this.ensureIndex(index);
            this.to += (long)l.size();
            return this.l.addAll(this.from + index, l);
        }
    }
}

