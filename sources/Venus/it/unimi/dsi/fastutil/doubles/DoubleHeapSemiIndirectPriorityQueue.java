/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIndirectPriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleSemiIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleHeapSemiIndirectPriorityQueue
implements DoubleIndirectPriorityQueue {
    protected final double[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected DoubleComparator c;

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int n, DoubleComparator doubleComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = dArray;
        this.c = doubleComparator;
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int n) {
        this(dArray, n, null);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, DoubleComparator doubleComparator) {
        this(dArray, dArray.length, doubleComparator);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray) {
        this(dArray, dArray.length, null);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int[] nArray, int n, DoubleComparator doubleComparator) {
        this(dArray, 0, doubleComparator);
        this.heap = nArray;
        this.size = n;
        DoubleSemiIndirectHeaps.makeHeap(dArray, nArray, n, doubleComparator);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int[] nArray, DoubleComparator doubleComparator) {
        this(dArray, nArray, nArray.length, doubleComparator);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int[] nArray, int n) {
        this(dArray, nArray, n, null);
    }

    public DoubleHeapSemiIndirectPriorityQueue(double[] dArray, int[] nArray) {
        this(dArray, nArray, nArray.length);
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
        DoubleSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
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
        DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        DoubleSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
    public DoubleComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
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

