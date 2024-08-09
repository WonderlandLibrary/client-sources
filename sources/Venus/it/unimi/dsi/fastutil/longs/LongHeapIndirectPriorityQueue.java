/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.longs.LongIndirectHeaps;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class LongHeapIndirectPriorityQueue
extends LongHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public LongHeapIndirectPriorityQueue(long[] lArray, int n, LongComparator longComparator) {
        super(lArray, n, longComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = longComparator;
        this.inv = new int[lArray.length];
        Arrays.fill(this.inv, -1);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, int n) {
        this(lArray, n, null);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, LongComparator longComparator) {
        this(lArray, lArray.length, longComparator);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray) {
        this(lArray, lArray.length, null);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, int[] nArray, int n, LongComparator longComparator) {
        this(lArray, 0, longComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        LongIndirectHeaps.makeHeap(lArray, nArray, this.inv, n, longComparator);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, int[] nArray, LongComparator longComparator) {
        this(lArray, nArray, nArray.length, longComparator);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, int[] nArray, int n) {
        this(lArray, nArray, n, null);
    }

    public LongHeapIndirectPriorityQueue(long[] lArray, int[] nArray) {
        this(lArray, nArray, nArray.length);
    }

    @Override
    public void enqueue(int n) {
        if (this.inv[n] >= 0) {
            throw new IllegalArgumentException("Index " + n + " belongs to the queue");
        }
        if (this.size == this.heap.length) {
            this.heap = IntArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size] = n;
        this.inv[this.heap[this.size]] = this.size++;
        LongIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
    }

    @Override
    public boolean contains(int n) {
        return this.inv[n] >= 0;
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        if (--this.size != 0) {
            this.heap[0] = this.heap[this.size];
            this.inv[this.heap[0]] = 0;
        }
        this.inv[n] = -1;
        if (this.size != 0) {
            LongIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        LongIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = LongIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        LongIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        LongIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
    }

    @Override
    public boolean remove(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            return true;
        }
        this.inv[n] = -1;
        if (n2 < --this.size) {
            this.heap[n2] = this.heap[this.size];
            this.inv[this.heap[n2]] = n2;
            int n3 = LongIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            LongIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

