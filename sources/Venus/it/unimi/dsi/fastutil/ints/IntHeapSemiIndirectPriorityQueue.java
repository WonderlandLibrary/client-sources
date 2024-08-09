/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntSemiIndirectHeaps;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntHeapSemiIndirectPriorityQueue
implements IntIndirectPriorityQueue {
    protected final int[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected IntComparator c;

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int n, IntComparator intComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = nArray;
        this.c = intComparator;
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int n) {
        this(nArray, n, null);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, IntComparator intComparator) {
        this(nArray, nArray.length, intComparator);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray) {
        this(nArray, nArray.length, null);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int[] nArray2, int n, IntComparator intComparator) {
        this(nArray, 0, intComparator);
        this.heap = nArray2;
        this.size = n;
        IntSemiIndirectHeaps.makeHeap(nArray, nArray2, n, intComparator);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int[] nArray2, IntComparator intComparator) {
        this(nArray, nArray2, nArray2.length, intComparator);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int[] nArray2, int n) {
        this(nArray, nArray2, n, null);
    }

    public IntHeapSemiIndirectPriorityQueue(int[] nArray, int[] nArray2) {
        this(nArray, nArray2, nArray2.length);
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
        if (this.size == this.heap.length) {
            this.heap = IntArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = n;
        IntSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            IntSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public int first() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        IntSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        IntSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    public void trim() {
        this.heap = IntArrays.trim(this.heap, this.size);
    }

    @Override
    public IntComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? IntSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : IntSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.refArray[this.heap[i]]);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

