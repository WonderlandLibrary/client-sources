/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntBigList;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntBigList;
import it.unimi.dsi.fastutil.ints.IntBigListIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public class IntBigArrayBigList
extends AbstractIntBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient int[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !IntBigArrayBigList.class.desiredAssertionStatus();

    protected IntBigArrayBigList(int[][] nArray, boolean bl) {
        this.a = nArray;
    }

    public IntBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? IntBigArrays.EMPTY_BIG_ARRAY : IntBigArrays.newBigArray(l);
    }

    public IntBigArrayBigList() {
        this.a = IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public IntBigArrayBigList(IntCollection intCollection) {
        this(intCollection.size());
        IntIterator intIterator = intCollection.iterator();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public IntBigArrayBigList(IntBigList intBigList) {
        this(intBigList.size64());
        this.size = intBigList.size64();
        intBigList.getElements(0L, this.a, 0L, this.size);
    }

    public IntBigArrayBigList(int[][] nArray) {
        this(nArray, 0L, IntBigArrays.length(nArray));
    }

    public IntBigArrayBigList(int[][] nArray, long l, long l2) {
        this(l2);
        IntBigArrays.copy(nArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public IntBigArrayBigList(Iterator<? extends Integer> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((int)iterator2.next());
        }
    }

    public IntBigArrayBigList(IntIterator intIterator) {
        this();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public int[][] elements() {
        return this.a;
    }

    public static IntBigArrayBigList wrap(int[][] nArray, long l) {
        if (l > IntBigArrays.length(nArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + IntBigArrays.length(nArray) + ")");
        }
        IntBigArrayBigList intBigArrayBigList = new IntBigArrayBigList(nArray, false);
        intBigArrayBigList.size = l;
        return intBigArrayBigList;
    }

    public static IntBigArrayBigList wrap(int[][] nArray) {
        return IntBigArrayBigList.wrap(nArray, IntBigArrays.length(nArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = IntBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = IntBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = IntBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, int n) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            IntBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        IntBigArrays.set(this.a, l, n);
        ++this.size;
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(int n) {
        this.grow(this.size + 1L);
        IntBigArrays.set(this.a, this.size++, n);
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int getInt(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return IntBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(int n) {
        for (long i = 0L; i < this.size; ++i) {
            if (n != IntBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(int n) {
        long l = this.size;
        while (l-- != 0L) {
            if (n != IntBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public int removeInt(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n = IntBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            IntBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return n;
    }

    @Override
    public boolean rem(int n) {
        long l = this.indexOf(n);
        if (l == -1L) {
            return true;
        }
        this.removeInt(l);
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int set(long l, int n) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n2 = IntBigArrays.get(this.a, l);
        IntBigArrays.set(this.a, l, n);
        return n2;
    }

    @Override
    public boolean removeAll(IntCollection intCollection) {
        long l;
        int[] nArray = null;
        int[] nArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                nArray = this.a[++n];
            }
            if (!intCollection.contains((int)nArray[n2])) {
                if (n4 == 0x8000000) {
                    nArray2 = this.a[++n3];
                    n4 = 0;
                }
                nArray2[n4++] = nArray[n2];
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
        int[] nArray = null;
        int[] nArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                nArray = this.a[++n];
            }
            if (!collection.contains((int)nArray[n2])) {
                if (n4 == 0x8000000) {
                    nArray2 = this.a[++n3];
                    n4 = 0;
                }
                nArray2[n4++] = nArray[n2];
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
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > IntBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            IntBigArrays.fill(this.a, this.size, l, 0);
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
        long l2 = IntBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = IntBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > IntBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, int[][] nArray, long l2, long l3) {
        IntBigArrays.copy(this.a, l, nArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        IntBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, int[][] nArray, long l2, long l3) {
        this.ensureIndex(l);
        IntBigArrays.ensureOffsetLength(nArray, l2, l3);
        this.grow(this.size + l3);
        IntBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        IntBigArrays.copy(nArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public IntBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new IntBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final IntBigArrayBigList this$0;
            {
                this.this$0 = intBigArrayBigList;
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
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return IntBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return IntBigArrays.get(this.this$0.a, this.pos);
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
            public void add(int n) {
                this.this$0.add(this.pos++, n);
                this.last = -1L;
            }

            @Override
            public void set(int n) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public IntBigArrayBigList clone() {
        IntBigArrayBigList intBigArrayBigList = new IntBigArrayBigList(this.size);
        IntBigArrays.copy(this.a, 0L, intBigArrayBigList.a, 0L, this.size);
        intBigArrayBigList.size = this.size;
        return intBigArrayBigList;
    }

    public boolean equals(IntBigArrayBigList intBigArrayBigList) {
        if (intBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != intBigArrayBigList.size64()) {
            return true;
        }
        int[][] nArray = this.a;
        int[][] nArray2 = intBigArrayBigList.a;
        while (l-- != 0L) {
            if (IntBigArrays.get(nArray, l) == IntBigArrays.get(nArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(IntBigArrayBigList intBigArrayBigList) {
        long l = this.size64();
        long l2 = intBigArrayBigList.size64();
        int[][] nArray = this.a;
        int[][] nArray2 = intBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            int n2;
            int n3 = IntBigArrays.get(nArray, n);
            int n4 = Integer.compare(n3, n2 = IntBigArrays.get(nArray2, n));
            if (n4 != 0) {
                return n4;
            }
            ++n;
        }
        return (long)n < l2 ? -1 : ((long)n < l ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = 0;
        while ((long)n < this.size) {
            objectOutputStream.writeInt(IntBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = IntBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            IntBigArrays.set(this.a, n, objectInputStream.readInt());
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

