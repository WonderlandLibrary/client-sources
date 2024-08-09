/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortIndirectHeaps;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ShortHeapIndirectPriorityQueue
extends ShortHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public ShortHeapIndirectPriorityQueue(short[] sArray, int n, ShortComparator shortComparator) {
        super(sArray, n, shortComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = shortComparator;
        this.inv = new int[sArray.length];
        Arrays.fill(this.inv, -1);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, int n) {
        this(sArray, n, null);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, ShortComparator shortComparator) {
        this(sArray, sArray.length, shortComparator);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray) {
        this(sArray, sArray.length, null);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, int[] nArray, int n, ShortComparator shortComparator) {
        this(sArray, 0, shortComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        ShortIndirectHeaps.makeHeap(sArray, nArray, this.inv, n, shortComparator);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, int[] nArray, ShortComparator shortComparator) {
        this(sArray, nArray, nArray.length, shortComparator);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, int[] nArray, int n) {
        this(sArray, nArray, n, null);
    }

    public ShortHeapIndirectPriorityQueue(short[] sArray, int[] nArray) {
        this(sArray, nArray, nArray.length);
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
        ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        ShortIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

