/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.objects.ObjectHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectIndirectHeaps;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectHeapIndirectPriorityQueue<K>
extends ObjectHeapSemiIndirectPriorityQueue<K> {
    protected final int[] inv;

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int n, Comparator<? super K> comparator) {
        super(KArray, n, comparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = comparator;
        this.inv = new int[KArray.length];
        Arrays.fill(this.inv, -1);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int n) {
        this(KArray, n, null);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, Comparator<? super K> comparator) {
        this(KArray, KArray.length, comparator);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray) {
        this(KArray, KArray.length, null);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int[] nArray, int n, Comparator<? super K> comparator) {
        this(KArray, 0, comparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        ObjectIndirectHeaps.makeHeap(KArray, nArray, this.inv, n, comparator);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int[] nArray, Comparator<? super K> comparator) {
        this(KArray, nArray, nArray.length, comparator);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int[] nArray, int n) {
        this(KArray, nArray, n, null);
    }

    public ObjectHeapIndirectPriorityQueue(K[] KArray, int[] nArray) {
        this(KArray, nArray, nArray.length);
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
        ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        ObjectIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

