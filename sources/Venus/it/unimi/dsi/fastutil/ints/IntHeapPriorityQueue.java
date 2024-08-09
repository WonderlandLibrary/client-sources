/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntHeaps;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
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
public class IntHeapPriorityQueue
implements IntPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected IntComparator c;

    public IntHeapPriorityQueue(int n, IntComparator intComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.c = intComparator;
    }

    public IntHeapPriorityQueue(int n) {
        this(n, null);
    }

    public IntHeapPriorityQueue(IntComparator intComparator) {
        this(0, intComparator);
    }

    public IntHeapPriorityQueue() {
        this(0, null);
    }

    public IntHeapPriorityQueue(int[] nArray, int n, IntComparator intComparator) {
        this(intComparator);
        this.heap = nArray;
        this.size = n;
        IntHeaps.makeHeap(nArray, n, intComparator);
    }

    public IntHeapPriorityQueue(int[] nArray, IntComparator intComparator) {
        this(nArray, nArray.length, intComparator);
    }

    public IntHeapPriorityQueue(int[] nArray, int n) {
        this(nArray, n, null);
    }

    public IntHeapPriorityQueue(int[] nArray) {
        this(nArray, nArray.length);
    }

    public IntHeapPriorityQueue(IntCollection intCollection, IntComparator intComparator) {
        this(intCollection.toIntArray(), intComparator);
    }

    public IntHeapPriorityQueue(IntCollection intCollection) {
        this(intCollection, (IntComparator)null);
    }

    public IntHeapPriorityQueue(Collection<? extends Integer> collection, IntComparator intComparator) {
        this(collection.size(), intComparator);
        Iterator<? extends Integer> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next();
        }
    }

    public IntHeapPriorityQueue(Collection<? extends Integer> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(int n) {
        if (this.size == this.heap.length) {
            this.heap = IntArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = n;
        IntHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeueInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            IntHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public int firstInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        IntHeaps.downHeap(this.heap, this.size, 0, this.c);
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
    public IntComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new int[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readInt();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

