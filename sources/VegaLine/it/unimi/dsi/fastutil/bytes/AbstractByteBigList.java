/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractByteBigList
extends AbstractByteCollection
implements ByteBigList,
ByteStack {
    protected AbstractByteBigList() {
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
    public void add(long index, byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(byte k) {
        this.add(this.size64(), k);
        return true;
    }

    @Override
    public byte removeByte(long i) {
        throw new UnsupportedOperationException();
    }

    public byte removeByte(int i) {
        return this.removeByte((long)i);
    }

    @Override
    public byte set(long index, byte k) {
        throw new UnsupportedOperationException();
    }

    public byte set(int index, byte k) {
        return this.set((long)index, k);
    }

    @Override
    public boolean addAll(long index, Collection<? extends Byte> c) {
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        Iterator<? extends Byte> i = c.iterator();
        while (n-- != 0) {
            this.add(index++, i.next());
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends Byte> c) {
        return this.addAll((long)index, c);
    }

    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public ByteBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public ByteBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public ByteBigListIterator listIterator(final long index) {
        this.ensureIndex(index);
        return new AbstractByteBigListIterator(){
            long pos;
            long last;
            {
                this.pos = index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractByteBigList.this.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public byte nextByte() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractByteBigList.this.getByte(this.last);
            }

            @Override
            public byte previousByte() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractByteBigList.this.getByte(this.pos);
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
            public void add(byte k) {
                AbstractByteBigList.this.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(byte k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractByteBigList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                AbstractByteBigList.this.removeByte(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public ByteBigListIterator listIterator(int index) {
        return this.listIterator((long)index);
    }

    @Override
    public boolean contains(byte k) {
        return this.indexOf(k) >= 0L;
    }

    @Override
    public long indexOf(byte k) {
        ByteBigListIterator i = this.listIterator();
        while (i.hasNext()) {
            byte e = i.nextByte();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(byte k) {
        ByteBigListIterator i = this.listIterator(this.size64());
        while (i.hasPrevious()) {
            byte e = i.previousByte();
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
                this.add((byte)0);
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
    public ByteBigList subList(long from, long to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ByteSubList(this, from, to);
    }

    @Override
    public void removeElements(long from, long to) {
        this.ensureIndex(to);
        ByteBigListIterator i = this.listIterator(from);
        long n = to - from;
        if (n < 0L) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0L) {
            i.nextByte();
            i.remove();
        }
    }

    @Override
    public void addElements(long index, byte[][] a, long offset, long length) {
        this.ensureIndex(index);
        ByteBigArrays.ensureOffsetLength(a, offset, length);
        while (length-- != 0L) {
            this.add(index++, ByteBigArrays.get(a, offset++));
        }
    }

    @Override
    public void addElements(long index, byte[][] a) {
        this.addElements(index, a, 0L, ByteBigArrays.length(a));
    }

    @Override
    public void getElements(long from, byte[][] a, long offset, long length) {
        ByteBigListIterator i = this.listIterator(from);
        ByteBigArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
        }
        while (length-- != 0L) {
            ByteBigArrays.set(a, offset++, i.nextByte());
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
        if (l instanceof ByteBigList) {
            ByteBigListIterator i1 = this.listIterator();
            ByteBigListIterator i2 = ((ByteBigList)l).listIterator();
            while (s-- != 0L) {
                if (i1.nextByte() == i2.nextByte()) continue;
                return false;
            }
            return true;
        }
        ByteBigListIterator i1 = this.listIterator();
        BigListIterator i2 = l.listIterator();
        while (s-- != 0L) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(BigList<? extends Byte> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof ByteBigList) {
            ByteBigListIterator i1 = this.listIterator();
            ByteBigListIterator i2 = ((ByteBigList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                byte e2;
                byte e1 = i1.nextByte();
                int r = Byte.compare(e1, e2 = i2.nextByte());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        ByteBigListIterator i1 = this.listIterator();
        BigListIterator<? extends Byte> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        ByteBigListIterator i = this.iterator();
        int h = 1;
        long s = this.size64();
        while (s-- != 0L) {
            byte k = i.nextByte();
            h = 31 * h + k;
        }
        return h;
    }

    @Override
    public void push(byte o) {
        this.add(o);
    }

    @Override
    public byte popByte() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeByte(this.size64() - 1L);
    }

    @Override
    public byte topByte() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getByte(this.size64() - 1L);
    }

    @Override
    public byte peekByte(int i) {
        return this.getByte(this.size64() - 1L - (long)i);
    }

    public byte getByte(int index) {
        return this.getByte((long)index);
    }

    @Override
    public boolean rem(byte k) {
        long index = this.indexOf(k);
        if (index == -1L) {
            return false;
        }
        this.removeByte(index);
        return true;
    }

    @Override
    public boolean addAll(long index, ByteCollection c) {
        return this.addAll(index, (Collection<? extends Byte>)c);
    }

    @Override
    public boolean addAll(long index, ByteBigList l) {
        return this.addAll(index, (ByteCollection)l);
    }

    @Override
    public boolean addAll(ByteCollection c) {
        return this.addAll(this.size64(), c);
    }

    @Override
    public boolean addAll(ByteBigList l) {
        return this.addAll(this.size64(), l);
    }

    @Override
    @Deprecated
    public void add(long index, Byte ok) {
        this.add(index, (byte)ok);
    }

    @Override
    @Deprecated
    public Byte set(long index, Byte ok) {
        return this.set(index, (byte)ok);
    }

    @Override
    @Deprecated
    public Byte get(long index) {
        return this.getByte(index);
    }

    @Override
    @Deprecated
    public long indexOf(Object ok) {
        return this.indexOf((Byte)ok);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object ok) {
        return this.lastIndexOf((Byte)ok);
    }

    @Deprecated
    public Byte remove(int index) {
        return this.removeByte(index);
    }

    @Override
    @Deprecated
    public Byte remove(long index) {
        return this.removeByte(index);
    }

    @Override
    @Deprecated
    public void push(Byte o) {
        this.push((byte)o);
    }

    @Override
    @Deprecated
    public Byte pop() {
        return this.popByte();
    }

    @Override
    @Deprecated
    public Byte top() {
        return this.topByte();
    }

    @Override
    @Deprecated
    public Byte peek(int i) {
        return this.peekByte(i);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ByteBigListIterator i = this.iterator();
        long n = this.size64();
        boolean first = true;
        s.append("[");
        while (n-- != 0L) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            byte k = i.nextByte();
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class ByteSubList
    extends AbstractByteBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList l;
        protected final long from;
        protected long to;
        private static final boolean ASSERTS = false;

        public ByteSubList(ByteBigList l, long from, long to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private void assertRange() {
        }

        @Override
        public boolean add(byte k) {
            this.l.add(this.to, k);
            ++this.to;
            return true;
        }

        @Override
        public void add(long index, byte k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(long index, Collection<? extends Byte> c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public byte getByte(long index) {
            this.ensureRestrictedIndex(index);
            return this.l.getByte(this.from + index);
        }

        @Override
        public byte removeByte(long index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeByte(this.from + index);
        }

        @Override
        public byte set(long index, byte k) {
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
        public void getElements(long from, byte[][] a, long offset, long length) {
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
        public void addElements(long index, byte[][] a, long offset, long length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public ByteBigListIterator listIterator(final long index) {
            this.ensureIndex(index);
            return new AbstractByteBigListIterator(){
                long pos;
                long last;
                {
                    this.pos = index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < ByteSubList.this.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public byte nextByte() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return ByteSubList.this.l.getByte(ByteSubList.this.from + this.last);
                }

                @Override
                public byte previousByte() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return ByteSubList.this.l.getByte(ByteSubList.this.from + this.pos);
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
                public void add(byte k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.add(this.pos++, k);
                    this.last = -1L;
                }

                @Override
                public void set(byte k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.removeByte(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                }
            };
        }

        @Override
        public ByteBigList subList(long from, long to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ByteSubList(this, from, to);
        }

        @Override
        public boolean rem(byte k) {
            long index = this.indexOf(k);
            if (index == -1L) {
                return false;
            }
            --this.to;
            this.l.removeByte(this.from + index);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return this.rem((Byte)o);
        }

        @Override
        public boolean addAll(long index, ByteCollection c) {
            this.ensureIndex(index);
            this.to += (long)c.size();
            return this.l.addAll(this.from + index, c);
        }

        public boolean addAll(long index, ByteList l) {
            this.ensureIndex(index);
            this.to += (long)l.size();
            return this.l.addAll(this.from + index, l);
        }
    }
}

