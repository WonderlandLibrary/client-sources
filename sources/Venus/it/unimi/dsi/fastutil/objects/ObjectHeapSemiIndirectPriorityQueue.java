/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.objects.ObjectSemiIndirectHeaps;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectHeapSemiIndirectPriorityQueue<K>
implements IndirectPriorityQueue<K> {
    protected final K[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected Comparator<? super K> c;

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int n, Comparator<? super K> comparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = KArray;
        this.c = comparator;
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int n) {
        this(KArray, n, null);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, Comparator<? super K> comparator) {
        this(KArray, KArray.length, comparator);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray) {
        this(KArray, KArray.length, null);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int[] nArray, int n, Comparator<? super K> comparator) {
        this(KArray, 0, comparator);
        this.heap = nArray;
        this.size = n;
        ObjectSemiIndirectHeaps.makeHeap(KArray, nArray, n, comparator);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int[] nArray, Comparator<? super K> comparator) {
        this(KArray, nArray, nArray.length, comparator);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int[] nArray, int n) {
        this(KArray, nArray, n, null);
    }

    public ObjectHeapSemiIndirectPriorityQueue(K[] KArray, int[] nArray) {
        this(KArray, nArray, nArray.length);
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
        ObjectSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        ObjectSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public Comparator<? super K> comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? ObjectSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : ObjectSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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
}

