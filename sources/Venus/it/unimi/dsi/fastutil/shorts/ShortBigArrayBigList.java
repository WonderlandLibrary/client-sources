/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
public class ShortBigArrayBigList
extends AbstractShortBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient short[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !ShortBigArrayBigList.class.desiredAssertionStatus();

    protected ShortBigArrayBigList(short[][] sArray, boolean bl) {
        this.a = sArray;
    }

    public ShortBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? ShortBigArrays.EMPTY_BIG_ARRAY : ShortBigArrays.newBigArray(l);
    }

    public ShortBigArrayBigList() {
        this.a = ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public ShortBigArrayBigList(ShortCollection shortCollection) {
        this(shortCollection.size());
        ShortIterator shortIterator = shortCollection.iterator();
        while (shortIterator.hasNext()) {
            this.add(shortIterator.nextShort());
        }
    }

    public ShortBigArrayBigList(ShortBigList shortBigList) {
        this(shortBigList.size64());
        this.size = shortBigList.size64();
        shortBigList.getElements(0L, this.a, 0L, this.size);
    }

    public ShortBigArrayBigList(short[][] sArray) {
        this(sArray, 0L, ShortBigArrays.length(sArray));
    }

    public ShortBigArrayBigList(short[][] sArray, long l, long l2) {
        this(l2);
        ShortBigArrays.copy(sArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public ShortBigArrayBigList(Iterator<? extends Short> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((short)iterator2.next());
        }
    }

    public ShortBigArrayBigList(ShortIterator shortIterator) {
        this();
        while (shortIterator.hasNext()) {
            this.add(shortIterator.nextShort());
        }
    }

    public short[][] elements() {
        return this.a;
    }

    public static ShortBigArrayBigList wrap(short[][] sArray, long l) {
        if (l > ShortBigArrays.length(sArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + ShortBigArrays.length(sArray) + ")");
        }
        ShortBigArrayBigList shortBigArrayBigList = new ShortBigArrayBigList(sArray, false);
        shortBigArrayBigList.size = l;
        return shortBigArrayBigList;
    }

    public static ShortBigArrayBigList wrap(short[][] sArray) {
        return ShortBigArrayBigList.wrap(sArray, ShortBigArrays.length(sArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = ShortBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = ShortBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = ShortBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, short s) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            ShortBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        ShortBigArrays.set(this.a, l, s);
        ++this.size;
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(short s) {
        this.grow(this.size + 1L);
        ShortBigArrays.set(this.a, this.size++, s);
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public short getShort(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return ShortBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(short s) {
        for (long i = 0L; i < this.size; ++i) {
            if (s != ShortBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(short s) {
        long l = this.size;
        while (l-- != 0L) {
            if (s != ShortBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public short removeShort(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        short s = ShortBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            ShortBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return s;
    }

    @Override
    public boolean rem(short s) {
        long l = this.indexOf(s);
        if (l == -1L) {
            return true;
        }
        this.removeShort(l);
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public short set(long l, short s) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        short s2 = ShortBigArrays.get(this.a, l);
        ShortBigArrays.set(this.a, l, s);
        return s2;
    }

    @Override
    public boolean removeAll(ShortCollection shortCollection) {
        long l;
        short[] sArray = null;
        short[] sArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                sArray = this.a[++n];
            }
            if (!shortCollection.contains((short)sArray[n2])) {
                if (n4 == 0x8000000) {
                    sArray2 = this.a[++n3];
                    n4 = 0;
                }
                sArray2[n4++] = sArray[n2];
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
        short[] sArray = null;
        short[] sArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                sArray = this.a[++n];
            }
            if (!collection.contains((short)sArray[n2])) {
                if (n4 == 0x8000000) {
                    sArray2 = this.a[++n3];
                    n4 = 0;
                }
                sArray2[n4++] = sArray[n2];
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
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > ShortBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            ShortBigArrays.fill(this.a, this.size, l, (short)0);
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
        long l2 = ShortBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = ShortBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > ShortBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, short[][] sArray, long l2, long l3) {
        ShortBigArrays.copy(this.a, l, sArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        ShortBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, short[][] sArray, long l2, long l3) {
        this.ensureIndex(l);
        ShortBigArrays.ensureOffsetLength(sArray, l2, l3);
        this.grow(this.size + l3);
        ShortBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        ShortBigArrays.copy(sArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public ShortBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new ShortBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final ShortBigArrayBigList this$0;
            {
                this.this$0 = shortBigArrayBigList;
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
            public short nextShort() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return ShortBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public short previousShort() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return ShortBigArrays.get(this.this$0.a, this.pos);
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
            public void add(short s) {
                this.this$0.add(this.pos++, s);
                this.last = -1L;
            }

            @Override
            public void set(short s) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, s);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeShort(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public ShortBigArrayBigList clone() {
        ShortBigArrayBigList shortBigArrayBigList = new ShortBigArrayBigList(this.size);
        ShortBigArrays.copy(this.a, 0L, shortBigArrayBigList.a, 0L, this.size);
        shortBigArrayBigList.size = this.size;
        return shortBigArrayBigList;
    }

    public boolean equals(ShortBigArrayBigList shortBigArrayBigList) {
        if (shortBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != shortBigArrayBigList.size64()) {
            return true;
        }
        short[][] sArray = this.a;
        short[][] sArray2 = shortBigArrayBigList.a;
        while (l-- != 0L) {
            if (ShortBigArrays.get(sArray, l) == ShortBigArrays.get(sArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ShortBigArrayBigList shortBigArrayBigList) {
        long l = this.size64();
        long l2 = shortBigArrayBigList.size64();
        short[][] sArray = this.a;
        short[][] sArray2 = shortBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            short s;
            short s2 = ShortBigArrays.get(sArray, n);
            int n2 = Short.compare(s2, s = ShortBigArrays.get(sArray2, n));
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
            objectOutputStream.writeShort(ShortBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = ShortBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            ShortBigArrays.set(this.a, n, objectInputStream.readShort());
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

