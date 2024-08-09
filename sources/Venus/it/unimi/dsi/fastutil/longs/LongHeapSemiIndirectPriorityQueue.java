/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIndirectPriorityQueue;
import it.unimi.dsi.fastutil.longs.LongSemiIndirectHeaps;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongHeapSemiIndirectPriorityQueue
implements LongIndirectPriorityQueue {
    protected final long[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected LongComparator c;

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int n, LongComparator longComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = lArray;
        this.c = longComparator;
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int n) {
        this(lArray, n, null);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, LongComparator longComparator) {
        this(lArray, lArray.length, longComparator);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray) {
        this(lArray, lArray.length, null);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int[] nArray, int n, LongComparator longComparator) {
        this(lArray, 0, longComparator);
        this.heap = nArray;
        this.size = n;
        LongSemiIndirectHeaps.makeHeap(lArray, nArray, n, longComparator);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int[] nArray, LongComparator longComparator) {
        this(lArray, nArray, nArray.length, longComparator);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int[] nArray, int n) {
        this(lArray, nArray, n, null);
    }

    public LongHeapSemiIndirectPriorityQueue(long[] lArray, int[] nArray) {
        this(lArray, nArray, nArray.length);
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
        LongSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            LongSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        LongSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        LongSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public LongComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? LongSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : LongSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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

