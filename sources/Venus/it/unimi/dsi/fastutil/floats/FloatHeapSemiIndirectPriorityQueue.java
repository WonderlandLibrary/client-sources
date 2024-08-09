/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIndirectPriorityQueue;
import it.unimi.dsi.fastutil.floats.FloatSemiIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatHeapSemiIndirectPriorityQueue
implements FloatIndirectPriorityQueue {
    protected final float[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected FloatComparator c;

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int n, FloatComparator floatComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = fArray;
        this.c = floatComparator;
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int n) {
        this(fArray, n, null);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, FloatComparator floatComparator) {
        this(fArray, fArray.length, floatComparator);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray) {
        this(fArray, fArray.length, null);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int[] nArray, int n, FloatComparator floatComparator) {
        this(fArray, 0, floatComparator);
        this.heap = nArray;
        this.size = n;
        FloatSemiIndirectHeaps.makeHeap(fArray, nArray, n, floatComparator);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int[] nArray, FloatComparator floatComparator) {
        this(fArray, nArray, nArray.length, floatComparator);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int[] nArray, int n) {
        this(fArray, nArray, n, null);
    }

    public FloatHeapSemiIndirectPriorityQueue(float[] fArray, int[] nArray) {
        this(fArray, nArray, nArray.length);
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
        FloatSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            FloatSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        FloatSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        FloatSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public FloatComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? FloatSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : FloatSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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

