/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByteBigList
extends AbstractByteCollection
implements ByteBigList,
ByteStack {
    protected AbstractByteBigList() {
    }

    protected void ensureIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l > this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than list size (" + this.size64() + ")");
        }
    }

    protected void ensureRestrictedIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l >= this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size64() + ")");
        }
    }

    @Override
    public void add(long l, byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(byte by) {
        this.add(this.size64(), by);
        return false;
    }

    @Override
    public byte removeByte(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte set(long l, byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Byte> collection) {
        this.ensureIndex(l);
        Iterator<? extends Byte> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Byte> collection) {
        return this.addAll(this.size64(), collection);
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
    public ByteBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new ByteBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractByteBigList this$0;
            {
                this.this$0 = abstractByteBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size64();
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
                return this.this$0.getByte(this.last);
            }

            @Override
            public byte previousByte() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getByte(this.pos);
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
            public void add(byte by) {
                this.this$0.add(this.pos++, by);
                this.last = -1L;
            }

            @Override
            public void set(byte by) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, by);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeByte(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(byte by) {
        return this.indexOf(by) >= 0L;
    }

    @Override
    public long indexOf(byte by) {
        ByteBigListIterator byteBigListIterator = this.listIterator();
        while (byteBigListIterator.hasNext()) {
            byte by2 = byteBigListIterator.nextByte();
            if (by != by2) continue;
            return byteBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(byte by) {
        ByteBigListIterator byteBigListIterator = this.listIterator(this.size64());
        while (byteBigListIterator.hasPrevious()) {
            byte by2 = byteBigListIterator.previousByte();
            if (by != by2) continue;
            return byteBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add((byte)1);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public ByteBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new ByteSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        ByteBigListIterator byteBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            byteBigListIterator.nextByte();
            byteBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, byte[][] byArray, long l2, long l3) {
        this.ensureIndex(l);
        ByteBigArrays.ensureOffsetLength(byArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, ByteBigArrays.get(byArray, l2++));
        }
    }

    @Override
    public void addElements(long l, byte[][] byArray) {
        this.addElements(l, byArray, 0L, ByteBigArrays.length(byArray));
    }

    @Override
    public void getElements(long l, byte[][] byArray, long l2, long l3) {
        ByteBigListIterator byteBigListIterator = this.listIterator(l);
        ByteBigArrays.ensureOffsetLength(byArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            ByteBigArrays.set(byArray, l2++, byteBigListIterator.nextByte());
        }
    }

    @Override
    public void clear() {
        this.removeElements(0L, this.size64());
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        ByteBigListIterator byteBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            byte by = byteBigListIterator.nextByte();
            n = 31 * n + by;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof BigList)) {
            return true;
        }
        BigList bigList = (BigList)object;
        long l = this.size64();
        if (l != bigList.size64()) {
            return true;
        }
        if (bigList instanceof ByteBigList) {
            ByteBigListIterator byteBigListIterator = this.listIterator();
            ByteBigListIterator byteBigListIterator2 = ((ByteBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (byteBigListIterator.nextByte() == byteBigListIterator2.nextByte()) continue;
                return true;
            }
            return false;
        }
        ByteBigListIterator byteBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(byteBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Byte> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof ByteBigList) {
            ByteBigListIterator byteBigListIterator = this.listIterator();
            ByteBigListIterator byteBigListIterator2 = ((ByteBigList)bigList).listIterator();
            while (byteBigListIterator.hasNext() && byteBigListIterator2.hasNext()) {
                byte by;
                byte by2 = byteBigListIterator.nextByte();
                int n = Byte.compare(by2, by = byteBigListIterator2.nextByte());
                if (n == 0) continue;
                return n;
            }
            return byteBigListIterator2.hasNext() ? -1 : (byteBigListIterator.hasNext() ? 1 : 0);
        }
        ByteBigListIterator byteBigListIterator = this.listIterator();
        BigListIterator<? extends Byte> bigListIterator = bigList.listIterator();
        while (byteBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)byteBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (byteBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(byte by) {
        this.add(by);
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
    public byte peekByte(int n) {
        return this.getByte(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(byte by) {
        long l = this.indexOf(by);
        if (l == -1L) {
            return true;
        }
        this.removeByte(l);
        return false;
    }

    @Override
    public boolean addAll(long l, ByteCollection byteCollection) {
        return this.addAll(l, (Collection<? extends Byte>)byteCollection);
    }

    @Override
    public boolean addAll(long l, ByteBigList byteBigList) {
        return this.addAll(l, (ByteCollection)byteBigList);
    }

    @Override
    public boolean addAll(ByteCollection byteCollection) {
        return this.addAll(this.size64(), byteCollection);
    }

    @Override
    public boolean addAll(ByteBigList byteBigList) {
        return this.addAll(this.size64(), byteBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Byte by) {
        this.add(l, (byte)by);
    }

    @Override
    @Deprecated
    public Byte set(long l, Byte by) {
        return this.set(l, (byte)by);
    }

    @Override
    @Deprecated
    public Byte get(long l) {
        return this.getByte(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Byte)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Byte)object);
    }

    @Override
    @Deprecated
    public Byte remove(long l) {
        return this.removeByte(l);
    }

    @Override
    @Deprecated
    public void push(Byte by) {
        this.push((byte)by);
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
    public Byte peek(int n) {
        return this.peekByte(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ByteBigListIterator byteBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            byte by = byteBigListIterator.nextByte();
            stringBuilder.append(String.valueOf(by));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ByteIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public void add(long l, Object object) {
        this.add(l, (Byte)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Byte)object);
    }

    @Override
    @Deprecated
    public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    public Object get(long l) {
        return this.get(l);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((BigList)object);
    }

    @Override
    @Deprecated
    public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    public void push(Object object) {
        this.push((Byte)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ByteSubList
    extends AbstractByteBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractByteBigList.class.desiredAssertionStatus();

        public ByteSubList(ByteBigList byteBigList, long l, long l2) {
            this.l = byteBigList;
            this.from = l;
            this.to = l2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(byte by) {
            this.l.add(this.to, by);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, byte by) {
            this.ensureIndex(l);
            this.l.add(this.from + l, by);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Byte> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public byte getByte(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getByte(this.from + l);
        }

        @Override
        public byte removeByte(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeByte(this.from + l);
        }

        @Override
        public byte set(long l, byte by) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, by);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, byte[][] byArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, byArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            this.l.removeElements(this.from + l, this.from + l2);
            this.to -= l2 - l;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(long l, byte[][] byArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, byArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ByteBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new ByteBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractByteBigList.class.desiredAssertionStatus();
                final long val$index;
                final ByteSubList this$0;
                {
                    this.this$0 = byteSubList;
                    this.val$index = l;
                    this.pos = this.val$index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size64();
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
                    return this.this$0.l.getByte(this.this$0.from + this.last);
                }

                @Override
                public byte previousByte() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getByte(this.this$0.from + this.pos);
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
                public void add(byte by) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, by);
                    this.last = -1L;
                    if (!$assertionsDisabled && !ByteSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(byte by) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, by);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeByte(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !ByteSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ByteBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new ByteSubList(this, l, l2);
        }

        @Override
        public boolean rem(byte by) {
            long l = this.indexOf(by);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeByte(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, ByteCollection byteCollection) {
            this.ensureIndex(l);
            return super.addAll(l, byteCollection);
        }

        @Override
        public boolean addAll(long l, ByteBigList byteBigList) {
            this.ensureIndex(l);
            return super.addAll(l, byteBigList);
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            super.add(l, (Byte)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Byte)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return super.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return super.get(l);
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public ByteIterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((BigList)object);
        }

        @Override
        @Deprecated
        public Object peek(int n) {
            return super.peek(n);
        }

        @Override
        @Deprecated
        public Object top() {
            return super.top();
        }

        @Override
        @Deprecated
        public Object pop() {
            return super.pop();
        }

        @Override
        @Deprecated
        public void push(Object object) {
            super.push((Byte)object);
        }

        static boolean access$000(ByteSubList byteSubList) {
            return byteSubList.assertRange();
        }
    }
}

