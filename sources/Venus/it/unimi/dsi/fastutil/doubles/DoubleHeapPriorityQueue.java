/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleHeaps;
import it.unimi.dsi.fastutil.doubles.DoublePriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleHeapPriorityQueue
implements DoublePriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient double[] heap = DoubleArrays.EMPTY_ARRAY;
    protected int size;
    protected DoubleComparator c;

    public DoubleHeapPriorityQueue(int n, DoubleComparator doubleComparator) {
        if (n > 0) {
            this.heap = new double[n];
        }
        this.c = doubleComparator;
    }

    public DoubleHeapPriorityQueue(int n) {
        this(n, null);
    }

    public DoubleHeapPriorityQueue(DoubleComparator doubleComparator) {
        this(0, doubleComparator);
    }

    public DoubleHeapPriorityQueue() {
        this(0, null);
    }

    public DoubleHeapPriorityQueue(double[] dArray, int n, DoubleComparator doubleComparator) {
        this(doubleComparator);
        this.heap = dArray;
        this.size = n;
        DoubleHeaps.makeHeap(dArray, n, doubleComparator);
    }

    public DoubleHeapPriorityQueue(double[] dArray, DoubleComparator doubleComparator) {
        this(dArray, dArray.length, doubleComparator);
    }

    public DoubleHeapPriorityQueue(double[] dArray, int n) {
        this(dArray, n, null);
    }

    public DoubleHeapPriorityQueue(double[] dArray) {
        this(dArray, dArray.length);
    }

    public DoubleHeapPriorityQueue(DoubleCollection doubleCollection, DoubleComparator doubleComparator) {
        this(doubleCollection.toDoubleArray(), doubleComparator);
    }

    public DoubleHeapPriorityQueue(DoubleCollection doubleCollection) {
        this(doubleCollection, (DoubleComparator)null);
    }

    public DoubleHeapPriorityQueue(Collection<? extends Double> collection, DoubleComparator doubleComparator) {
        this(collection.size(), doubleComparator);
        Iterator<? extends Double> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next();
        }
    }

    public DoubleHeapPriorityQueue(Collection<? extends Double> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(double d) {
        if (this.size == this.heap.length) {
            this.heap = DoubleArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = d;
        DoubleHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public double dequeueDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        double d = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            DoubleHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return d;
    }

    @Override
    public double firstDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        DoubleHeaps.downHeap(this.heap, this.size, 0, this.c);
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
        this.heap = DoubleArrays.trim(this.heap, this.size);
    }

    @Override
    public DoubleComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new double[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readDouble();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

