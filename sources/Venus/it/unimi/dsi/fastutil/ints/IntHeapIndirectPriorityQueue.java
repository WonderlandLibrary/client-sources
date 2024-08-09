/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntIndirectHeaps;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class IntHeapIndirectPriorityQueue
extends IntHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public IntHeapIndirectPriorityQueue(int[] nArray, int n, IntComparator intComparator) {
        super(nArray, n, intComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = intComparator;
        this.inv = new int[nArray.length];
        Arrays.fill(this.inv, -1);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, int n) {
        this(nArray, n, null);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, IntComparator intComparator) {
        this(nArray, nArray.length, intComparator);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray) {
        this(nArray, nArray.length, null);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, int[] nArray2, int n, IntComparator intComparator) {
        this(nArray, 0, intComparator);
        this.heap = nArray2;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray2[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray2[n2] + " appears twice in the heap");
            }
            this.inv[nArray2[n2]] = n2;
        }
        IntIndirectHeaps.makeHeap(nArray, nArray2, this.inv, n, intComparator);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, int[] nArray2, IntComparator intComparator) {
        this(nArray, nArray2, nArray2.length, intComparator);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, int[] nArray2, int n) {
        this(nArray, nArray2, n, null);
    }

    public IntHeapIndirectPriorityQueue(int[] nArray, int[] nArray2) {
        this(nArray, nArray2, nArray2.length);
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
        IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        IntIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

