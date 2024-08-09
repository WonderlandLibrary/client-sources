/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIndirectPriorityQueue;
import it.unimi.dsi.fastutil.bytes.ByteSemiIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteHeapSemiIndirectPriorityQueue
implements ByteIndirectPriorityQueue {
    protected final byte[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected ByteComparator c;

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int n, ByteComparator byteComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = byArray;
        this.c = byteComparator;
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int n) {
        this(byArray, n, null);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, ByteComparator byteComparator) {
        this(byArray, byArray.length, byteComparator);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray) {
        this(byArray, byArray.length, null);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int[] nArray, int n, ByteComparator byteComparator) {
        this(byArray, 0, byteComparator);
        this.heap = nArray;
        this.size = n;
        ByteSemiIndirectHeaps.makeHeap(byArray, nArray, n, byteComparator);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int[] nArray, ByteComparator byteComparator) {
        this(byArray, nArray, nArray.length, byteComparator);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int[] nArray, int n) {
        this(byArray, nArray, n, null);
    }

    public ByteHeapSemiIndirectPriorityQueue(byte[] byArray, int[] nArray) {
        this(byArray, nArray, nArray.length);
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
        ByteSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            ByteSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        ByteSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        ByteSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public ByteComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? ByteSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : ByteSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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

