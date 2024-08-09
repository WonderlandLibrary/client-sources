/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
public class BooleanBigArrayBigList
extends AbstractBooleanBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient boolean[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !BooleanBigArrayBigList.class.desiredAssertionStatus();

    protected BooleanBigArrayBigList(boolean[][] blArray, boolean bl) {
        this.a = blArray;
    }

    public BooleanBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? BooleanBigArrays.EMPTY_BIG_ARRAY : BooleanBigArrays.newBigArray(l);
    }

    public BooleanBigArrayBigList() {
        this.a = BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public BooleanBigArrayBigList(BooleanCollection booleanCollection) {
        this(booleanCollection.size());
        BooleanIterator booleanIterator = booleanCollection.iterator();
        while (booleanIterator.hasNext()) {
            this.add(booleanIterator.nextBoolean());
        }
    }

    public BooleanBigArrayBigList(BooleanBigList booleanBigList) {
        this(booleanBigList.size64());
        this.size = booleanBigList.size64();
        booleanBigList.getElements(0L, this.a, 0L, this.size);
    }

    public BooleanBigArrayBigList(boolean[][] blArray) {
        this(blArray, 0L, BooleanBigArrays.length(blArray));
    }

    public BooleanBigArrayBigList(boolean[][] blArray, long l, long l2) {
        this(l2);
        BooleanBigArrays.copy(blArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public BooleanBigArrayBigList(Iterator<? extends Boolean> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((boolean)iterator2.next());
        }
    }

    public BooleanBigArrayBigList(BooleanIterator booleanIterator) {
        this();
        while (booleanIterator.hasNext()) {
            this.add(booleanIterator.nextBoolean());
        }
    }

    public boolean[][] elements() {
        return this.a;
    }

    public static BooleanBigArrayBigList wrap(boolean[][] blArray, long l) {
        if (l > BooleanBigArrays.length(blArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + BooleanBigArrays.length(blArray) + ")");
        }
        BooleanBigArrayBigList booleanBigArrayBigList = new BooleanBigArrayBigList(blArray, false);
        booleanBigArrayBigList.size = l;
        return booleanBigArrayBigList;
    }

    public static BooleanBigArrayBigList wrap(boolean[][] blArray) {
        return BooleanBigArrayBigList.wrap(blArray, BooleanBigArrays.length(blArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = BooleanBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = BooleanBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = BooleanBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, boolean bl) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            BooleanBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        BooleanBigArrays.set(this.a, l, bl);
        ++this.size;
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(boolean bl) {
        this.grow(this.size + 1L);
        BooleanBigArrays.set(this.a, this.size++, bl);
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean getBoolean(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return BooleanBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(boolean bl) {
        for (long i = 0L; i < this.size; ++i) {
            if (bl != BooleanBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(boolean bl) {
        long l = this.size;
        while (l-- != 0L) {
            if (bl != BooleanBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public boolean removeBoolean(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        boolean bl = BooleanBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            BooleanBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return bl;
    }

    @Override
    public boolean rem(boolean bl) {
        long l = this.indexOf(bl);
        if (l == -1L) {
            return true;
        }
        this.removeBoolean(l);
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean set(long l, boolean bl) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        boolean bl2 = BooleanBigArrays.get(this.a, l);
        BooleanBigArrays.set(this.a, l, bl);
        return bl2;
    }

    @Override
    public boolean removeAll(BooleanCollection booleanCollection) {
        long l;
        boolean[] blArray = null;
        boolean[] blArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                blArray = this.a[++n];
            }
            if (!booleanCollection.contains((boolean)blArray[n2])) {
                if (n4 == 0x8000000) {
                    blArray2 = this.a[++n3];
                    n4 = 0;
                }
                blArray2[n4++] = blArray[n2];
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
        boolean[] blArray = null;
        boolean[] blArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                blArray = this.a[++n];
            }
            if (!collection.contains((boolean)blArray[n2])) {
                if (n4 == 0x8000000) {
                    blArray2 = this.a[++n3];
                    n4 = 0;
                }
                blArray2[n4++] = blArray[n2];
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
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > BooleanBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            BooleanBigArrays.fill(this.a, this.size, l, false);
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
        long l2 = BooleanBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = BooleanBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > BooleanBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, boolean[][] blArray, long l2, long l3) {
        BooleanBigArrays.copy(this.a, l, blArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        BooleanBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, boolean[][] blArray, long l2, long l3) {
        this.ensureIndex(l);
        BooleanBigArrays.ensureOffsetLength(blArray, l2, l3);
        this.grow(this.size + l3);
        BooleanBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        BooleanBigArrays.copy(blArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public BooleanBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new BooleanBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final BooleanBigArrayBigList this$0;
            {
                this.this$0 = booleanBigArrayBigList;
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
            public boolean nextBoolean() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return BooleanBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public boolean previousBoolean() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return BooleanBigArrays.get(this.this$0.a, this.pos);
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
            public void add(boolean bl) {
                this.this$0.add(this.pos++, bl);
                this.last = -1L;
            }

            @Override
            public void set(boolean bl) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, bl);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeBoolean(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public BooleanBigArrayBigList clone() {
        BooleanBigArrayBigList booleanBigArrayBigList = new BooleanBigArrayBigList(this.size);
        BooleanBigArrays.copy(this.a, 0L, booleanBigArrayBigList.a, 0L, this.size);
        booleanBigArrayBigList.size = this.size;
        return booleanBigArrayBigList;
    }

    public boolean equals(BooleanBigArrayBigList booleanBigArrayBigList) {
        if (booleanBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != booleanBigArrayBigList.size64()) {
            return true;
        }
        boolean[][] blArray = this.a;
        boolean[][] blArray2 = booleanBigArrayBigList.a;
        while (l-- != 0L) {
            if (BooleanBigArrays.get(blArray, l) == BooleanBigArrays.get(blArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BooleanBigArrayBigList booleanBigArrayBigList) {
        long l = this.size64();
        long l2 = booleanBigArrayBigList.size64();
        boolean[][] blArray = this.a;
        boolean[][] blArray2 = booleanBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            boolean bl;
            boolean bl2 = BooleanBigArrays.get(blArray, n);
            int n2 = Boolean.compare(bl2, bl = BooleanBigArrays.get(blArray2, n));
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
            objectOutputStream.writeBoolean(BooleanBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = BooleanBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            BooleanBigArrays.set(this.a, n, objectInputStream.readBoolean());
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

