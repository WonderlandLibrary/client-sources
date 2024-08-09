/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.bytes.ByteIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ByteHeapIndirectPriorityQueue
extends ByteHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int n, ByteComparator byteComparator) {
        super(byArray, n, byteComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = byteComparator;
        this.inv = new int[byArray.length];
        Arrays.fill(this.inv, -1);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int n) {
        this(byArray, n, null);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, ByteComparator byteComparator) {
        this(byArray, byArray.length, byteComparator);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray) {
        this(byArray, byArray.length, null);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int[] nArray, int n, ByteComparator byteComparator) {
        this(byArray, 0, byteComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        ByteIndirectHeaps.makeHeap(byArray, nArray, this.inv, n, byteComparator);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int[] nArray, ByteComparator byteComparator) {
        this(byArray, nArray, nArray.length, byteComparator);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int[] nArray, int n) {
        this(byArray, nArray, n, null);
    }

    public ByteHeapIndirectPriorityQueue(byte[] byArray, int[] nArray) {
        this(byArray, nArray, nArray.length);
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
        ByteIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            ByteIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        ByteIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = ByteIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        ByteIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        ByteIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = ByteIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            ByteIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

