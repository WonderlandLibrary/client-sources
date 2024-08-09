/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.ints.AbstractIntList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
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
public class IntArrayList
extends AbstractIntList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient int[] a;
    protected int size;
    static final boolean $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();

    protected IntArrayList(int[] nArray, boolean bl) {
        this.a = nArray;
    }

    public IntArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? IntArrays.EMPTY_ARRAY : new int[n];
    }

    public IntArrayList() {
        this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }

    public IntArrayList(Collection<? extends Integer> collection) {
        this(collection.size());
        this.size = IntIterators.unwrap(IntIterators.asIntIterator(collection.iterator()), this.a);
    }

    public IntArrayList(IntCollection intCollection) {
        this(intCollection.size());
        this.size = IntIterators.unwrap(intCollection.iterator(), this.a);
    }

    public IntArrayList(IntList intList) {
        this(intList.size());
        this.size = intList.size();
        intList.getElements(0, this.a, 0, this.size);
    }

    public IntArrayList(int[] nArray) {
        this(nArray, 0, nArray.length);
    }

    public IntArrayList(int[] nArray, int n, int n2) {
        this(n2);
        System.arraycopy(nArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public IntArrayList(Iterator<? extends Integer> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((int)iterator2.next());
        }
    }

    public IntArrayList(IntIterator intIterator) {
        this();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public int[] elements() {
        return this.a;
    }

    public static IntArrayList wrap(int[] nArray, int n) {
        if (n > nArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + nArray.length + ")");
        }
        IntArrayList intArrayList = new IntArrayList(nArray, false);
        intArrayList.size = n;
        return intArrayList;
    }

    public static IntArrayList wrap(int[] nArray) {
        return IntArrayList.wrap(nArray, nArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == IntArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = IntArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = IntArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, int n2) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = n2;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(int n) {
        this.grow(this.size + 1);
        this.a[this.size++] = n;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int getInt(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(int n) {
        for (int i = 0; i < this.size; ++i) {
            if (n != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (n != this.a[n2]) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public int removeInt(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n2 = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return n2;
    }

    @Override
    public boolean rem(int n) {
        int n2 = this.indexOf(n);
        if (n2 == -1) {
            return true;
        }
        this.removeInt(n2);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int set(int n, int n2) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n3 = this.a[n];
        this.a[n] = n2;
        return n3;
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
            java.util.Arrays.fill(this.a, this.size, n, 0);
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
        int[] nArray = new int[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, nArray, 0, this.size);
        this.a = nArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, int[] nArray, int n2, int n3) {
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        System.arraycopy(this.a, n, nArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(nArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public int[] toArray(int[] nArray) {
        if (nArray == null || nArray.length < this.size) {
            nArray = new int[this.size];
        }
        System.arraycopy(this.a, 0, nArray, 0, this.size);
        return nArray;
    }

    @Override
    public boolean addAll(int n, IntCollection intCollection) {
        this.ensureIndex(n);
        int n2 = intCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        IntIterator intIterator = intCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = intIterator.nextInt();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, IntList intList) {
        this.ensureIndex(n);
        int n2 = intList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        intList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(IntCollection intCollection) {
        int n;
        int[] nArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (intCollection.contains(nArray[n])) continue;
            nArray[n2++] = nArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        int[] nArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(nArray[n])) continue;
            nArray[n2++] = nArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public IntListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new IntListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final IntArrayList this$0;
            {
                this.this$0 = intArrayList;
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
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public int previousInt() {
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
            public void add(int n) {
                this.this$0.add(this.pos++, n);
                this.last = -1;
            }

            @Override
            public void set(int n) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public IntArrayList clone() {
        IntArrayList intArrayList = new IntArrayList(this.size);
        System.arraycopy(this.a, 0, intArrayList.a, 0, this.size);
        intArrayList.size = this.size;
        return intArrayList;
    }

    public boolean equals(IntArrayList intArrayList) {
        if (intArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != intArrayList.size()) {
            return true;
        }
        int[] nArray = this.a;
        int[] nArray2 = intArrayList.a;
        while (n-- != 0) {
            if (nArray[n] == nArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(IntArrayList intArrayList) {
        int n;
        int n2 = this.size();
        int n3 = intArrayList.size();
        int[] nArray = this.a;
        int[] nArray2 = intArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            int n4 = nArray[n];
            int n5 = nArray2[n];
            int n6 = Integer.compare(n4, n5);
            if (n6 == 0) continue;
            return n6;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readInt();
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

