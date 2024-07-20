/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractByteList
extends AbstractByteCollection
implements ByteList,
ByteStack {
    protected AbstractByteList() {
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
    public void add(int index, byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(byte k) {
        this.add(this.size(), k);
        return true;
    }

    @Override
    public byte removeByte(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte set(int index, byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Byte> c) {
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

    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    @Deprecated
    public ByteListIterator byteListIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public ByteListIterator byteListIterator(int index) {
        return this.listIterator(index);
    }

    @Override
    public ByteListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public ByteListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ByteListIterator listIterator(final int index) {
        this.ensureIndex(index);
        return new AbstractByteListIterator(){
            int pos;
            int last;
            {
                this.pos = index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractByteList.this.size();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public byte nextByte() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractByteList.this.getByte(this.last);
            }

            @Override
            public byte previousByte() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractByteList.this.getByte(this.pos);
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
            public void add(byte k) {
                AbstractByteList.this.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(byte k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractByteList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractByteList.this.removeByte(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(byte k) {
        return this.indexOf(k) >= 0;
    }

    @Override
    public int indexOf(byte k) {
        ByteListIterator i = this.listIterator();
        while (i.hasNext()) {
            byte e = i.nextByte();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(byte k) {
        ByteListIterator i = this.listIterator(this.size());
        while (i.hasPrevious()) {
            byte e = i.previousByte();
            if (k != e) continue;
            return i.nextIndex();
        }
        return -1;
    }

    @Override
    public void size(int size) {
        int i = this.size();
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

    @Override
    public ByteList subList(int from, int to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ByteSubList(this, from, to);
    }

    @Override
    @Deprecated
    public ByteList byteSubList(int from, int to) {
        return this.subList(from, to);
    }

    @Override
    public void removeElements(int from, int to) {
        this.ensureIndex(to);
        ByteListIterator i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.nextByte();
            i.remove();
        }
    }

    @Override
    public void addElements(int index, byte[] a, int offset, int length) {
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
    public void addElements(int index, byte[] a) {
        this.addElements(index, a, 0, a.length);
    }

    @Override
    public void getElements(int from, byte[] a, int offset, int length) {
        ByteListIterator i = this.listIterator(from);
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
            a[offset++] = i.nextByte();
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
        if (l instanceof ByteList) {
            ByteListIterator i1 = this.listIterator();
            ByteListIterator i2 = ((ByteList)l).listIterator();
            while (s-- != 0) {
                if (i1.nextByte() == i2.nextByte()) continue;
                return false;
            }
            return true;
        }
        ByteListIterator i1 = this.listIterator();
        ListIterator i2 = l.listIterator();
        while (s-- != 0) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(List<? extends Byte> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof ByteList) {
            ByteListIterator i1 = this.listIterator();
            ByteListIterator i2 = ((ByteList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                byte e2;
                byte e1 = i1.nextByte();
                int r = Byte.compare(e1, e2 = i2.nextByte());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        ByteListIterator i1 = this.listIterator();
        ListIterator<? extends Byte> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        ByteListIterator i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
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
        return this.removeByte(this.size() - 1);
    }

    @Override
    public byte topByte() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getByte(this.size() - 1);
    }

    @Override
    public byte peekByte(int i) {
        return this.getByte(this.size() - 1 - i);
    }

    @Override
    public boolean rem(byte k) {
        int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.removeByte(index);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return this.rem((Byte)o);
    }

    @Override
    public boolean addAll(int index, ByteCollection c) {
        return this.addAll(index, (Collection<? extends Byte>)c);
    }

    @Override
    public boolean addAll(int index, ByteList l) {
        return this.addAll(index, (ByteCollection)l);
    }

    @Override
    public boolean addAll(ByteCollection c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public boolean addAll(ByteList l) {
        return this.addAll(this.size(), l);
    }

    @Override
    public void add(int index, Byte ok) {
        this.add(index, (byte)ok);
    }

    @Override
    @Deprecated
    public Byte set(int index, Byte ok) {
        return this.set(index, (byte)ok);
    }

    @Override
    @Deprecated
    public Byte get(int index) {
        return this.getByte(index);
    }

    @Override
    public int indexOf(Object ok) {
        return this.indexOf((Byte)ok);
    }

    @Override
    public int lastIndexOf(Object ok) {
        return this.lastIndexOf((Byte)ok);
    }

    @Override
    @Deprecated
    public Byte remove(int index) {
        return this.removeByte(index);
    }

    @Override
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
        ByteListIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (n-- != 0) {
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
    extends AbstractByteList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList l;
        protected final int from;
        protected int to;
        private static final boolean ASSERTS = false;

        public ByteSubList(ByteList l, int from, int to) {
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
        public void add(int index, byte k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Byte> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public byte getByte(int index) {
            this.ensureRestrictedIndex(index);
            return this.l.getByte(this.from + index);
        }

        @Override
        public byte removeByte(int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeByte(this.from + index);
        }

        @Override
        public byte set(int index, byte k) {
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
        public void getElements(int from, byte[] a, int offset, int length) {
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
        public void addElements(int index, byte[] a, int offset, int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public ByteListIterator listIterator(final int index) {
            this.ensureIndex(index);
            return new AbstractByteListIterator(){
                int pos;
                int last;
                {
                    this.pos = index;
                    this.last = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < ByteSubList.this.size();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(byte k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.add(this.pos++, k);
                    this.last = -1;
                }

                @Override
                public void set(byte k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    ByteSubList.this.removeByte(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                }
            };
        }

        @Override
        public ByteList subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ByteSubList(this, from, to);
        }

        @Override
        public boolean rem(byte k) {
            int index = this.indexOf(k);
            if (index == -1) {
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
        public boolean addAll(int index, ByteCollection c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public boolean addAll(int index, ByteList l) {
            this.ensureIndex(index);
            this.to += l.size();
            return this.l.addAll(this.from + index, l);
        }
    }
}

