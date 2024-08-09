/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharHeapSemiIndirectPriorityQueue;
import it.unimi.dsi.fastutil.chars.CharIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class CharHeapIndirectPriorityQueue
extends CharHeapSemiIndirectPriorityQueue {
    protected final int[] inv;

    public CharHeapIndirectPriorityQueue(char[] cArray, int n, CharComparator charComparator) {
        super(cArray, n, charComparator);
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = charComparator;
        this.inv = new int[cArray.length];
        Arrays.fill(this.inv, -1);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, int n) {
        this(cArray, n, null);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, CharComparator charComparator) {
        this(cArray, cArray.length, charComparator);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray) {
        this(cArray, cArray.length, null);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, int[] nArray, int n, CharComparator charComparator) {
        this(cArray, 0, charComparator);
        this.heap = nArray;
        this.size = n;
        int n2 = n;
        while (n2-- != 0) {
            if (this.inv[nArray[n2]] != -1) {
                throw new IllegalArgumentException("Index " + nArray[n2] + " appears twice in the heap");
            }
            this.inv[nArray[n2]] = n2;
        }
        CharIndirectHeaps.makeHeap(cArray, nArray, this.inv, n, charComparator);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, int[] nArray, CharComparator charComparator) {
        this(cArray, nArray, nArray.length, charComparator);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, int[] nArray, int n) {
        this(cArray, nArray, n, null);
    }

    public CharHeapIndirectPriorityQueue(char[] cArray, int[] nArray) {
        this(cArray, nArray, nArray.length);
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
        CharIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
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
            CharIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public void changed() {
        CharIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
    }

    @Override
    public void changed(int n) {
        int n2 = this.inv[n];
        if (n2 < 0) {
            throw new IllegalArgumentException("Index " + n + " does not belong to the queue");
        }
        int n3 = CharIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
        CharIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
    }

    @Override
    public void allChanged() {
        CharIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
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
            int n3 = CharIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, n2, this.c);
            CharIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, n3, this.c);
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.inv, -1);
    }
}

