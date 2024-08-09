/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIndirectPriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortSemiIndirectHeaps;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ShortHeapSemiIndirectPriorityQueue
implements ShortIndirectPriorityQueue {
    protected final short[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected ShortComparator c;

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int n, ShortComparator shortComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = sArray;
        this.c = shortComparator;
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int n) {
        this(sArray, n, null);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, ShortComparator shortComparator) {
        this(sArray, sArray.length, shortComparator);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray) {
        this(sArray, sArray.length, null);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int[] nArray, int n, ShortComparator shortComparator) {
        this(sArray, 0, shortComparator);
        this.heap = nArray;
        this.size = n;
        ShortSemiIndirectHeaps.makeHeap(sArray, nArray, n, shortComparator);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int[] nArray, ShortComparator shortComparator) {
        this(sArray, nArray, nArray.length, shortComparator);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int[] nArray, int n) {
        this(sArray, nArray, n, null);
    }

    public ShortHeapSemiIndirectPriorityQueue(short[] sArray, int[] nArray) {
        this(sArray, nArray, nArray.length);
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
        ShortSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            ShortSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        ShortSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        ShortSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public ShortComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? ShortSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : ShortSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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

