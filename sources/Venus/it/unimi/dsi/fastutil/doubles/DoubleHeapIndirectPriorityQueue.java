/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DoubleHeapIndirectPriorityQueue
extends DoubleHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int n, DoubleComparator doubleComparator) {
        super(dArray, n, doubleComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = doubleComparator;
        this.inv = new int[dArray.length];
        Arrays.fill(this.inv, -1);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int n) {
        this(dArray, n, null);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, DoubleComparator doubleComparator) {
        this(dArray, dArray.length, doubleComparator);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray) {
        this(dArray, dArray.length, null);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int[] nArray, int n, DoubleComparator doubleComparator) {
        this(dArray, 0, doubleComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        DoubleIndirectHeaps.makeHeap(dArray, nArray, this.inv, n, doubleComparator);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int[] nArray, DoubleComparator doubleComparator) {
        this(dArray, nArray, nArray.length, doubleComparator);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int[] nArray, int n) {
        this(dArray, nArray, n, null);
    }

    public DoubleHeapIndirectPriorityQueue(double[] dArray, int[] nArray) {
        this(dArray, nArray, nArray.length);
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
        DoubleIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            DoubleIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        DoubleIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = DoubleIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        DoubleIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        DoubleIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = DoubleIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            DoubleIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

