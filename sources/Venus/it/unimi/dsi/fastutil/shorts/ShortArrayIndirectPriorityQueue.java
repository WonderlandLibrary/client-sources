/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIndirectPriorityQueue;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ShortArrayIndirectPriorityQueue
implements ShortIndirectPriorityQueue {
    protected short[] refArray;
    protected int[] array = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected ShortComparator c;
    protected int firstIndex;
    protected boolean firstIndexValid;

    public ShortArrayIndirectPriorityQueue(short[] sArray, int n, ShortComparator shortComparator) {
        if (n > 0) {
            this.array = new int[n];
        }
        this.refArray = sArray;
        this.c = shortComparator;
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, int n) {
        this(sArray, n, null);
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, ShortComparator shortComparator) {
        this(sArray, sArray.length, shortComparator);
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray) {
        this(sArray, sArray.length, null);
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, int[] nArray, int n, ShortComparator shortComparator) {
        this(sArray, 0, shortComparator);
        this.array = nArray;
        this.size = n;
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, int[] nArray, ShortComparator shortComparator) {
        this(sArray, nArray, nArray.length, shortComparator);
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, int[] nArray, int n) {
        this(sArray, nArray, n, null);
    }

    public ShortArrayIndirectPriorityQueue(short[] sArray, int[] nArray) {
        this(sArray, nArray, nArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        short s = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.refArray[this.array[n]] >= s) continue;
                n2 = n;
                s = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.refArray[this.array[n]], s) >= 0) continue;
                n2 = n;
                s = this.refArray[this.array[n2]];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private int findLast() {
        int n = this.size;
        int n2 = --n;
        short s = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (s >= this.refArray[this.array[n]]) continue;
                n2 = n;
                s = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(s, this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                s = this.refArray[this.array[n2]];
            }
        }
        return n2;
    }

    protected final void ensureNonEmpty() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }

    protected void ensureElement(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.refArray.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
        }
    }

    @Override
    public void enqueue(int n) {
        this.ensureElement(n);
        if (this.size == this.array.length) {
            this.array = IntArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (this.refArray[n] < this.refArray[this.array[this.firstIndex]]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(this.refArray[n], this.refArray[this.array[this.firstIndex]]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = n;
    }

    @Override
    public int dequeue() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        int n2 = this.array[n];
        if (--this.size != 0) {
            System.arraycopy(this.array, n + 1, this.array, n, this.size - n);
        }
        this.firstIndexValid = false;
        return n2;
    }

    @Override
    public int first() {
        this.ensureNonEmpty();
        return this.array[this.findFirst()];
    }

    @Override
    public int last() {
        this.ensureNonEmpty();
        return this.array[this.findLast()];
    }

    @Override
    public void changed() {
        this.ensureNonEmpty();
        this.firstIndexValid = false;
    }

    @Override
    public void changed(int n) {
        this.ensureElement(n);
        if (n == this.firstIndex) {
            this.firstIndexValid = false;
        }
    }

    @Override
    public void allChanged() {
        this.firstIndexValid = false;
    }

    @Override
    public boolean remove(int n) {
        this.ensureElement(n);
        int[] nArray = this.array;
        int n2 = this.size;
        while (n2-- != 0 && nArray[n2] != n) {
        }
        if (n2 < 0) {
            return true;
        }
        this.firstIndexValid = false;
        if (--this.size != 0) {
            System.arraycopy(nArray, n2 + 1, nArray, n2, this.size - n2);
        }
        return false;
    }

    @Override
    public int front(int[] nArray) {
        short s = this.refArray[this.array[this.findFirst()]];
        int n = this.size;
        int n2 = 0;
        while (n-- != 0) {
            if (s != this.refArray[this.array[n]]) continue;
            nArray[n2++] = this.array[n];
        }
        return n2;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.firstIndexValid = false;
    }

    public void trim() {
        this.array = IntArrays.trim(this.array, this.size);
    }

    @Override
    public ShortComparator comparator() {
        return this.c;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.refArray[this.array[i]]);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

