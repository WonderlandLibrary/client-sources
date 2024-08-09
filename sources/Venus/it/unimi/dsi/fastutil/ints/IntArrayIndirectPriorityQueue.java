/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIndirectPriorityQueue;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArrayIndirectPriorityQueue
implements IntIndirectPriorityQueue {
    protected int[] refArray;
    protected int[] array = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected IntComparator c;
    protected int firstIndex;
    protected boolean firstIndexValid;

    public IntArrayIndirectPriorityQueue(int[] nArray, int n, IntComparator intComparator) {
        if (n > 0) {
            this.array = new int[n];
        }
        this.refArray = nArray;
        this.c = intComparator;
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, int n) {
        this(nArray, n, null);
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, IntComparator intComparator) {
        this(nArray, nArray.length, intComparator);
    }

    public IntArrayIndirectPriorityQueue(int[] nArray) {
        this(nArray, nArray.length, null);
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, int[] nArray2, int n, IntComparator intComparator) {
        this(nArray, 0, intComparator);
        this.array = nArray2;
        this.size = n;
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, int[] nArray2, IntComparator intComparator) {
        this(nArray, nArray2, nArray2.length, intComparator);
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, int[] nArray2, int n) {
        this(nArray, nArray2, n, null);
    }

    public IntArrayIndirectPriorityQueue(int[] nArray, int[] nArray2) {
        this(nArray, nArray2, nArray2.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        int n3 = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.refArray[this.array[n]] >= n3) continue;
                n2 = n;
                n3 = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.refArray[this.array[n]], n3) >= 0) continue;
                n2 = n;
                n3 = this.refArray[this.array[n2]];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private int findLast() {
        int n = this.size;
        int n2 = --n;
        int n3 = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (n3 >= this.refArray[this.array[n]]) continue;
                n2 = n;
                n3 = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(n3, this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                n3 = this.refArray[this.array[n2]];
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
        int n = this.refArray[this.array[this.findFirst()]];
        int n2 = this.size;
        int n3 = 0;
        while (n2-- != 0) {
            if (n != this.refArray[this.array[n2]]) continue;
            nArray[n3++] = this.array[n2];
        }
        return n3;
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
    public IntComparator comparator() {
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

