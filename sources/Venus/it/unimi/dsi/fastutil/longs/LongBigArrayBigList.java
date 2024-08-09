/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongBigList;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongBigList;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
public class LongBigArrayBigList
extends AbstractLongBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient long[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !LongBigArrayBigList.class.desiredAssertionStatus();

    protected LongBigArrayBigList(long[][] lArray, boolean bl) {
        this.a = lArray;
    }

    public LongBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? LongBigArrays.EMPTY_BIG_ARRAY : LongBigArrays.newBigArray(l);
    }

    public LongBigArrayBigList() {
        this.a = LongBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public LongBigArrayBigList(LongCollection longCollection) {
        this(longCollection.size());
        LongIterator longIterator = longCollection.iterator();
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public LongBigArrayBigList(LongBigList longBigList) {
        this(longBigList.size64());
        this.size = longBigList.size64();
        longBigList.getElements(0L, this.a, 0L, this.size);
    }

    public LongBigArrayBigList(long[][] lArray) {
        this(lArray, 0L, LongBigArrays.length(lArray));
    }

    public LongBigArrayBigList(long[][] lArray, long l, long l2) {
        this(l2);
        LongBigArrays.copy(lArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public LongBigArrayBigList(Iterator<? extends Long> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((long)iterator2.next());
        }
    }

    public LongBigArrayBigList(LongIterator longIterator) {
        this();
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public long[][] elements() {
        return this.a;
    }

    public static LongBigArrayBigList wrap(long[][] lArray, long l) {
        if (l > LongBigArrays.length(lArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + LongBigArrays.length(lArray) + ")");
        }
        LongBigArrayBigList longBigArrayBigList = new LongBigArrayBigList(lArray, false);
        longBigArrayBigList.size = l;
        return longBigArrayBigList;
    }

    public static LongBigArrayBigList wrap(long[][] lArray) {
        return LongBigArrayBigList.wrap(lArray, LongBigArrays.length(lArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == LongBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = LongBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = LongBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != LongBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = LongBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, long l2) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            LongBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        LongBigArrays.set(this.a, l, l2);
        ++this.size;
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(long l) {
        this.grow(this.size + 1L);
        LongBigArrays.set(this.a, this.size++, l);
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public long getLong(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return LongBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(long l) {
        for (long i = 0L; i < this.size; ++i) {
            if (l != LongBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(long l) {
        long l2 = this.size;
        while (l2-- != 0L) {
            if (l != LongBigArrays.get(this.a, l2)) continue;
            return l2;
        }
        return -1L;
    }

    @Override
    public long removeLong(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        long l2 = LongBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            LongBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return l2;
    }

    @Override
    public boolean rem(long l) {
        long l2 = this.indexOf(l);
        if (l2 == -1L) {
            return true;
        }
        this.removeLong(l2);
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public long set(long l, long l2) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        long l3 = LongBigArrays.get(this.a, l);
        LongBigArrays.set(this.a, l, l2);
        return l3;
    }

    @Override
    public boolean removeAll(LongCollection longCollection) {
        long l;
        long[] lArray = null;
        long[] lArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                lArray = this.a[++n];
            }
            if (!longCollection.contains((long)lArray[n2])) {
                if (n4 == 0x8000000) {
                    lArray2 = this.a[++n3];
                    n4 = 0;
                }
                lArray2[n4++] = lArray[n2];
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
        long[] lArray = null;
        long[] lArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                lArray = this.a[++n];
            }
            if (!collection.contains((long)lArray[n2])) {
                if (n4 == 0x8000000) {
                    lArray2 = this.a[++n3];
                    n4 = 0;
                }
                lArray2[n4++] = lArray[n2];
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
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > LongBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            LongBigArrays.fill(this.a, this.size, l, 0L);
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
        long l2 = LongBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = LongBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > LongBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, long[][] lArray, long l2, long l3) {
        LongBigArrays.copy(this.a, l, lArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        LongBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, long[][] lArray, long l2, long l3) {
        this.ensureIndex(l);
        LongBigArrays.ensureOffsetLength(lArray, l2, l3);
        this.grow(this.size + l3);
        LongBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        LongBigArrays.copy(lArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public LongBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new LongBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final LongBigArrayBigList this$0;
            {
                this.this$0 = longBigArrayBigList;
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
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return LongBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public long previousLong() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return LongBigArrays.get(this.this$0.a, this.pos);
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
            public void add(long l) {
                this.this$0.add(this.pos++, l);
                this.last = -1L;
            }

            @Override
            public void set(long l) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, l);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeLong(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public LongBigArrayBigList clone() {
        LongBigArrayBigList longBigArrayBigList = new LongBigArrayBigList(this.size);
        LongBigArrays.copy(this.a, 0L, longBigArrayBigList.a, 0L, this.size);
        longBigArrayBigList.size = this.size;
        return longBigArrayBigList;
    }

    public boolean equals(LongBigArrayBigList longBigArrayBigList) {
        if (longBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != longBigArrayBigList.size64()) {
            return true;
        }
        long[][] lArray = this.a;
        long[][] lArray2 = longBigArrayBigList.a;
        while (l-- != 0L) {
            if (LongBigArrays.get(lArray, l) == LongBigArrays.get(lArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(LongBigArrayBigList longBigArrayBigList) {
        long l = this.size64();
        long l2 = longBigArrayBigList.size64();
        long[][] lArray = this.a;
        long[][] lArray2 = longBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            long l3;
            long l4 = LongBigArrays.get(lArray, n);
            int n2 = Long.compare(l4, l3 = LongBigArrays.get(lArray2, n));
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
            objectOutputStream.writeLong(LongBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = LongBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            LongBigArrays.set(this.a, n, objectInputStream.readLong());
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

