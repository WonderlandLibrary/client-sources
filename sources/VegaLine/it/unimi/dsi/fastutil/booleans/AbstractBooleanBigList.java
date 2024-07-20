/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractBooleanBigList
extends AbstractBooleanCollection
implements BooleanBigList,
BooleanStack {
    protected AbstractBooleanBigList() {
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
    public void add(long index, boolean k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(boolean k) {
        this.add(this.size64(), k);
        return true;
    }

    @Override
    public boolean removeBoolean(long i) {
        throw new UnsupportedOperationException();
    }

    public boolean removeBoolean(int i) {
        return this.removeBoolean((long)i);
    }

    @Override
    public boolean set(long index, boolean k) {
        throw new UnsupportedOperationException();
    }

    public boolean set(int index, boolean k) {
        return this.set((long)index, k);
    }

    @Override
    public boolean addAll(long index, Collection<? extends Boolean> c) {
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        Iterator<? extends Boolean> i = c.iterator();
        while (n-- != 0) {
            this.add(index++, i.next());
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends Boolean> c) {
        return this.addAll((long)index, c);
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public BooleanBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public BooleanBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public BooleanBigListIterator listIterator(final long index) {
        this.ensureIndex(index);
        return new AbstractBooleanBigListIterator(){
            long pos;
            long last;
            {
                this.pos = index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractBooleanBigList.this.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public boolean nextBoolean() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractBooleanBigList.this.getBoolean(this.last);
            }

            @Override
            public boolean previousBoolean() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractBooleanBigList.this.getBoolean(this.pos);
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
            public void add(boolean k) {
                AbstractBooleanBigList.this.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(boolean k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractBooleanBigList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractBooleanBigList.this.removeBoolean(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public BooleanBigListIterator listIterator(int index) {
        return this.listIterator((long)index);
    }

    @Override
    public boolean contains(boolean k) {
        return this.indexOf(k) >= 0L;
    }

    @Override
    public long indexOf(boolean k) {
        BooleanBigListIterator i = this.listIterator();
        while (i.hasNext()) {
            boolean e = i.nextBoolean();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(boolean k) {
        BooleanBigListIterator i = this.listIterator(this.size64());
        while (i.hasPrevious()) {
            boolean e = i.previousBoolean();
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
                this.add(false);
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
    public BooleanBigList subList(long from, long to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new BooleanSubList(this, from, to);
    }

    @Override
    public void removeElements(long from, long to) {
        this.ensureIndex(to);
        BooleanBigListIterator i = this.listIterator(from);
        long n = to - from;
        if (n < 0L) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0L) {
            i.nextBoolean();
            i.remove();
        }
    }

    @Override
    public void addElements(long index, boolean[][] a, long offset, long length) {
        this.ensureIndex(index);
        BooleanBigArrays.ensureOffsetLength(a, offset, length);
        while (length-- != 0L) {
            this.add(index++, BooleanBigArrays.get(a, offset++));
        }
    }

    @Override
    public void addElements(long index, boolean[][] a) {
        this.addElements(index, a, 0L, BooleanBigArrays.length(a));
    }

    @Override
    public void getElements(long from, boolean[][] a, long offset, long length) {
        BooleanBigListIterator i = this.listIterator(from);
        BooleanBigArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
        }
        while (length-- != 0L) {
            BooleanBigArrays.set(a, offset++, i.nextBoolean());
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
        if (l instanceof BooleanBigList) {
            BooleanBigListIterator i1 = this.listIterator();
            BooleanBigListIterator i2 = ((BooleanBigList)l).listIterator();
            while (s-- != 0L) {
                if (i1.nextBoolean() == i2.nextBoolean()) continue;
                return false;
            }
            return true;
        }
        BooleanBigListIterator i1 = this.listIterator();
        BigListIterator i2 = l.listIterator();
        while (s-- != 0L) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(BigList<? extends Boolean> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof BooleanBigList) {
            BooleanBigListIterator i1 = this.listIterator();
            BooleanBigListIterator i2 = ((BooleanBigList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                boolean e2;
                boolean e1 = i1.nextBoolean();
                int r = Boolean.compare(e1, e2 = i2.nextBoolean());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        BooleanBigListIterator i1 = this.listIterator();
        BigListIterator<? extends Boolean> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        BooleanBigListIterator i = this.iterator();
        int h = 1;
        long s = this.size64();
        while (s-- != 0L) {
            boolean k = i.nextBoolean();
            h = 31 * h + (k ? 1231 : 1237);
        }
        return h;
    }

    @Override
    public void push(boolean o) {
        this.add(o);
    }

    @Override
    public boolean popBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeBoolean(this.size64() - 1L);
    }

    @Override
    public boolean topBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getBoolean(this.size64() - 1L);
    }

    @Override
    public boolean peekBoolean(int i) {
        return this.getBoolean(this.size64() - 1L - (long)i);
    }

    public boolean getBoolean(int index) {
        return this.getBoolean((long)index);
    }

    @Override
    public boolean rem(boolean k) {
        long index = this.indexOf(k);
        if (index == -1L) {
            return false;
        }
        this.removeBoolean(index);
        return true;
    }

    @Override
    public boolean addAll(long index, BooleanCollection c) {
        return this.addAll(index, (Collection<? extends Boolean>)c);
    }

    @Override
    public boolean addAll(long index, BooleanBigList l) {
        return this.addAll(index, (BooleanCollection)l);
    }

    @Override
    public boolean addAll(BooleanCollection c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public boolean addAll(BooleanBigList l) {
        return this.addAll(this.size64(), l);
    }

    @Override
    @Deprecated
    public void add(long index, Boolean ok) {
        this.add(index, (boolean)ok);
    }

    @Override
    @Deprecated
    public Boolean set(long index, Boolean ok) {
        return this.set(index, (boolean)ok);
    }

    @Override
    @Deprecated
    public Boolean get(long index) {
        return this.getBoolean(index);
    }

    @Override
    @Deprecated
    public long indexOf(Object ok) {
        return this.indexOf((Boolean)ok);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object ok) {
        return this.lastIndexOf((Boolean)ok);
    }

    @Deprecated
    public Boolean remove(int index) {
        return this.removeBoolean(index);
    }

    @Override
    @Deprecated
    public Boolean remove(long index) {
        return this.removeBoolean(index);
    }

    @Override
    @Deprecated
    public void push(Boolean o) {
        this.push((boolean)o);
    }

    @Override
    @Deprecated
    public Boolean pop() {
        return this.popBoolean();
    }

    @Override
    @Deprecated
    public Boolean top() {
        return this.topBoolean();
    }

    @Override
    @Deprecated
    public Boolean peek(int i) {
        return this.peekBoolean(i);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        BooleanBigListIterator i = this.iterator();
        long n = this.size64();
        boolean first = true;
        s.append("[");
        while (n-- != 0L) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            boolean k = i.nextBoolean();
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class BooleanSubList
    extends AbstractBooleanBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanBigList l;
        protected final long from;
        protected long to;
        private static final boolean ASSERTS = false;

        public BooleanSubList(BooleanBigList l, long from, long to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private void assertRange() {
        }

        @Override
        public boolean add(boolean k) {
            this.l.add(this.to, k);
            ++this.to;
            return true;
        }

        @Override
        public void add(long index, boolean k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(long index, Collection<? extends Boolean> c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public boolean getBoolean(long index) {
            this.ensureRestrictedIndex(index);
            return this.l.getBoolean(this.from + index);
        }

        @Override
        public boolean removeBoolean(long index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeBoolean(this.from + index);
        }

        @Override
        public boolean set(long index, boolean k) {
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
        public void getElements(long from, boolean[][] a, long offset, long length) {
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
        public void addElements(long index, boolean[][] a, long offset, long length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public BooleanBigListIterator listIterator(final long index) {
            this.ensureIndex(index);
            return new AbstractBooleanBigListIterator(){
                long pos;
                long last;
                {
                    this.pos = index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < BooleanSubList.this.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public boolean nextBoolean() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + this.last);
                }

                @Override
                public boolean previousBoolean() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + this.pos);
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
                public void add(boolean k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    BooleanSubList.this.add(this.pos++, k);
                    this.last = -1L;
                }

                @Override
                public void set(boolean k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    BooleanSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    BooleanSubList.this.removeBoolean(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                }
            };
        }

        @Override
        public BooleanBigList subList(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new BooleanSubList(this, from, to);
        }

        @Override
        public boolean rem(boolean k) {
            long index = this.indexOf(k);
            if (index == -1L) {
                return false;
            }
            --this.to;
            this.l.removeBoolean(this.from + index);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return this.rem((Boolean)o);
        }

        @Override
        public boolean addAll(long index, BooleanCollection c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        public boolean addAll(long index, BooleanList l) {
            this.ensureIndex(index);
            this.to += (long)l.size();
            return this.l.addAll(this.from + index, l);
        }
    }
}

