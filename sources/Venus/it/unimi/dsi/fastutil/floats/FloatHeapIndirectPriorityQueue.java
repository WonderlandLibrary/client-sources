/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.floats.FloatIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class FloatHeapIndirectPriorityQueue
extends FloatHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public FloatHeapIndirectPriorityQueue(float[] fArray, int n, FloatComparator floatComparator) {
        super(fArray, n, floatComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = floatComparator;
        this.inv = new int[fArray.length];
        Arrays.fill(this.inv, -1);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, int n) {
        this(fArray, n, null);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, FloatComparator floatComparator) {
        this(fArray, fArray.length, floatComparator);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray) {
        this(fArray, fArray.length, null);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, int[] nArray, int n, FloatComparator floatComparator) {
        this(fArray, 0, floatComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        FloatIndirectHeaps.makeHeap(fArray, nArray, this.inv, n, floatComparator);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, int[] nArray, FloatComparator floatComparator) {
        this(fArray, nArray, nArray.length, floatComparator);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, int[] nArray, int n) {
        this(fArray, nArray, n, null);
    }

    public FloatHeapIndirectPriorityQueue(float[] fArray, int[] nArray) {
        this(fArray, nArray, nArray.length);
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
        FloatIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            FloatIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        FloatIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = FloatIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        FloatIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        FloatIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = FloatIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            FloatIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

