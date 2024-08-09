/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.longs.AbstractLongList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArrayList
extends AbstractLongList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient long[] a;
    protected int size;
    static final boolean $assertionsDisabled = !LongArrayList.class.desiredAssertionStatus();

    protected LongArrayList(long[] lArray, boolean bl) {
        this.a = lArray;
    }

    public LongArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? LongArrays.EMPTY_ARRAY : new long[n];
    }

    public LongArrayList() {
        this.a = LongArrays.DEFAULT_EMPTY_ARRAY;
    }

    public LongArrayList(Collection<? extends Long> collection) {
        this(collection.size());
        this.size = LongIterators.unwrap(LongIterators.asLongIterator(collection.iterator()), this.a);
    }

    public LongArrayList(LongCollection longCollection) {
        this(longCollection.size());
        this.size = LongIterators.unwrap(longCollection.iterator(), this.a);
    }

    public LongArrayList(LongList longList) {
        this(longList.size());
        this.size = longList.size();
        longList.getElements(0, this.a, 0, this.size);
    }

    public LongArrayList(long[] lArray) {
        this(lArray, 0, lArray.length);
    }

    public LongArrayList(long[] lArray, int n, int n2) {
        this(n2);
        System.arraycopy(lArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public LongArrayList(Iterator<? extends Long> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((long)iterator2.next());
        }
    }

    public LongArrayList(LongIterator longIterator) {
        this();
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public long[] elements() {
        return this.a;
    }

    public static LongArrayList wrap(long[] lArray, int n) {
        if (n > lArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + lArray.length + ")");
        }
        LongArrayList longArrayList = new LongArrayList(lArray, false);
        longArrayList.size = n;
        return longArrayList;
    }

    public static LongArrayList wrap(long[] lArray) {
        return LongArrayList.wrap(lArray, lArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == LongArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = LongArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != LongArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = LongArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, long l) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = l;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(long l) {
        this.grow(this.size + 1);
        this.a[this.size++] = l;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public long getLong(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(long l) {
        for (int i = 0; i < this.size; ++i) {
            if (l != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (l != this.a[n]) continue;
            return n;
        }
        return 1;
    }

    @Override
    public long removeLong(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        long l = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return l;
    }

    @Override
    public boolean rem(long l) {
        int n = this.indexOf(l);
        if (n == -1) {
            return true;
        }
        this.removeLong(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public long set(int n, long l) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        long l2 = this.a[n];
        this.a[n] = l;
        return l2;
    }

    @Override
    public void clear() {
        this.size = 0;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int n) {
        if (n > this.a.length) {
            this.ensureCapacity(n);
        }
        if (n > this.size) {
            java.util.Arrays.fill(this.a, this.size, n, 0L);
        }
        this.size = n;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        long[] lArray = new long[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, lArray, 0, this.size);
        this.a = lArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, long[] lArray, int n2, int n3) {
        LongArrays.ensureOffsetLength(lArray, n2, n3);
        System.arraycopy(this.a, n, lArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, long[] lArray, int n2, int n3) {
        this.ensureIndex(n);
        LongArrays.ensureOffsetLength(lArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(lArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public long[] toArray(long[] lArray) {
        if (lArray == null || lArray.length < this.size) {
            lArray = new long[this.size];
        }
        System.arraycopy(this.a, 0, lArray, 0, this.size);
        return lArray;
    }

    @Override
    public boolean addAll(int n, LongCollection longCollection) {
        this.ensureIndex(n);
        int n2 = longCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        LongIterator longIterator = longCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = longIterator.nextLong();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, LongList longList) {
        this.ensureIndex(n);
        int n2 = longList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        longList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(LongCollection longCollection) {
        int n;
        long[] lArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (longCollection.contains(lArray[n])) continue;
            lArray[n2++] = lArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        long[] lArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(lArray[n])) continue;
            lArray[n2++] = lArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public LongListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new LongListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final LongArrayList this$0;
            {
                this.this$0 = longArrayList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public long previousLong() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.a[this.pos];
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
            public void add(long l) {
                this.this$0.add(this.pos++, l);
                this.last = -1;
            }

            @Override
            public void set(long l) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, l);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeLong(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public LongArrayList clone() {
        LongArrayList longArrayList = new LongArrayList(this.size);
        System.arraycopy(this.a, 0, longArrayList.a, 0, this.size);
        longArrayList.size = this.size;
        return longArrayList;
    }

    public boolean equals(LongArrayList longArrayList) {
        if (longArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != longArrayList.size()) {
            return true;
        }
        long[] lArray = this.a;
        long[] lArray2 = longArrayList.a;
        while (n-- != 0) {
            if (lArray[n] == lArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(LongArrayList longArrayList) {
        int n;
        int n2 = this.size();
        int n3 = longArrayList.size();
        long[] lArray = this.a;
        long[] lArray2 = longArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            long l = lArray[n];
            long l2 = lArray2[n];
            int n4 = Long.compare(l, l2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readLong();
        }
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

