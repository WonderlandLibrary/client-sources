/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteBigArrayBigList
extends AbstractByteBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient byte[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !ByteBigArrayBigList.class.desiredAssertionStatus();

    protected ByteBigArrayBigList(byte[][] byArray, boolean bl) {
        this.a = byArray;
    }

    public ByteBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? ByteBigArrays.EMPTY_BIG_ARRAY : ByteBigArrays.newBigArray(l);
    }

    public ByteBigArrayBigList() {
        this.a = ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public ByteBigArrayBigList(ByteCollection byteCollection) {
        this(byteCollection.size());
        ByteIterator byteIterator = byteCollection.iterator();
        while (byteIterator.hasNext()) {
            this.add(byteIterator.nextByte());
        }
    }

    public ByteBigArrayBigList(ByteBigList byteBigList) {
        this(byteBigList.size64());
        this.size = byteBigList.size64();
        byteBigList.getElements(0L, this.a, 0L, this.size);
    }

    public ByteBigArrayBigList(byte[][] byArray) {
        this(byArray, 0L, ByteBigArrays.length(byArray));
    }

    public ByteBigArrayBigList(byte[][] byArray, long l, long l2) {
        this(l2);
        ByteBigArrays.copy(byArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public ByteBigArrayBigList(Iterator<? extends Byte> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((byte)iterator2.next());
        }
    }

    public ByteBigArrayBigList(ByteIterator byteIterator) {
        this();
        while (byteIterator.hasNext()) {
            this.add(byteIterator.nextByte());
        }
    }

    public byte[][] elements() {
        return this.a;
    }

    public static ByteBigArrayBigList wrap(byte[][] byArray, long l) {
        if (l > ByteBigArrays.length(byArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + ByteBigArrays.length(byArray) + ")");
        }
        ByteBigArrayBigList byteBigArrayBigList = new ByteBigArrayBigList(byArray, false);
        byteBigArrayBigList.size = l;
        return byteBigArrayBigList;
    }

    public static ByteBigArrayBigList wrap(byte[][] byArray) {
        return ByteBigArrayBigList.wrap(byArray, ByteBigArrays.length(byArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = ByteBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = ByteBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = ByteBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, byte by) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            ByteBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        ByteBigArrays.set(this.a, l, by);
        ++this.size;
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(byte by) {
        this.grow(this.size + 1L);
        ByteBigArrays.set(this.a, this.size++, by);
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public byte getByte(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return ByteBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(byte by) {
        for (long i = 0L; i < this.size; ++i) {
            if (by != ByteBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(byte by) {
        long l = this.size;
        while (l-- != 0L) {
            if (by != ByteBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public byte removeByte(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        byte by = ByteBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            ByteBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return by;
    }

    @Override
    public boolean rem(byte by) {
        long l = this.indexOf(by);
        if (l == -1L) {
            return true;
        }
        this.removeByte(l);
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public byte set(long l, byte by) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        byte by2 = ByteBigArrays.get(this.a, l);
        ByteBigArrays.set(this.a, l, by);
        return by2;
    }

    @Override
    public boolean removeAll(ByteCollection byteCollection) {
        long l;
        byte[] byArray = null;
        byte[] byArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                byArray = this.a[++n];
            }
            if (!byteCollection.contains((byte)byArray[n2])) {
                if (n4 == 0x8000000) {
                    byArray2 = this.a[++n3];
                    n4 = 0;
                }
                byArray2[n4++] = byArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        long l;
        byte[] byArray = null;
        byte[] byArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                byArray = this.a[++n];
            }
            if (!collection.contains((byte)byArray[n2])) {
                if (n4 == 0x8000000) {
                    byArray2 = this.a[++n3];
                    n4 = 0;
                }
                byArray2[n4++] = byArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public void clear() {
        this.size = 0L;
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > ByteBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            ByteBigArrays.fill(this.a, this.size, l, (byte)0);
        }
        this.size = l;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public void trim() {
        this.trim(0L);
    }

    public void trim(long l) {
        long l2 = ByteBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = ByteBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > ByteBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, byte[][] byArray, long l2, long l3) {
        ByteBigArrays.copy(this.a, l, byArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        ByteBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, byte[][] byArray, long l2, long l3) {
        this.ensureIndex(l);
        ByteBigArrays.ensureOffsetLength(byArray, l2, l3);
        this.grow(this.size + l3);
        ByteBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        ByteBigArrays.copy(byArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public ByteBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new ByteBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final ByteBigArrayBigList this$0;
            {
                this.this$0 = byteBigArrayBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
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
                return ByteBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public byte previousByte() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return ByteBigArrays.get(this.this$0.a, this.pos);
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

    public ByteBigArrayBigList clone() {
        ByteBigArrayBigList byteBigArrayBigList = new ByteBigArrayBigList(this.size);
        ByteBigArrays.copy(this.a, 0L, byteBigArrayBigList.a, 0L, this.size);
        byteBigArrayBigList.size = this.size;
        return byteBigArrayBigList;
    }

    public boolean equals(ByteBigArrayBigList byteBigArrayBigList) {
        if (byteBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != byteBigArrayBigList.size64()) {
            return true;
        }
        byte[][] byArray = this.a;
        byte[][] byArray2 = byteBigArrayBigList.a;
        while (l-- != 0L) {
            if (ByteBigArrays.get(byArray, l) == ByteBigArrays.get(byArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ByteBigArrayBigList byteBigArrayBigList) {
        long l = this.size64();
        long l2 = byteBigArrayBigList.size64();
        byte[][] byArray = this.a;
        byte[][] byArray2 = byteBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            byte by;
            byte by2 = ByteBigArrays.get(byArray, n);
            int n2 = Byte.compare(by2, by = ByteBigArrays.get(byArray2, n));
            if (n2 != 0) {
                return n2;
            }
            ++n;
        }
        return (long)n < l2 ? -1 : ((long)n < l ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = 0;
        while ((long)n < this.size) {
            objectOutputStream.writeByte(ByteBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = ByteBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            ByteBigArrays.set(this.a, n, objectInputStream.readByte());
            ++n;
        }
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

