/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongHeaps;
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
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
public class LongHeapPriorityQueue
implements LongPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient long[] heap = LongArrays.EMPTY_ARRAY;
    protected int size;
    protected LongComparator c;

    public LongHeapPriorityQueue(int n, LongComparator longComparator) {
        if (n > 0) {
            this.heap = new long[n];
        }
        this.c = longComparator;
    }

    public LongHeapPriorityQueue(int n) {
        this(n, null);
    }

    public LongHeapPriorityQueue(LongComparator longComparator) {
        this(0, longComparator);
    }

    public LongHeapPriorityQueue() {
        this(0, null);
    }

    public LongHeapPriorityQueue(long[] lArray, int n, LongComparator longComparator) {
        this(longComparator);
        this.heap = lArray;
        this.size = n;
        LongHeaps.makeHeap(lArray, n, longComparator);
    }

    public LongHeapPriorityQueue(long[] lArray, LongComparator longComparator) {
        this(lArray, lArray.length, longComparator);
    }

    public LongHeapPriorityQueue(long[] lArray, int n) {
        this(lArray, n, null);
    }

    public LongHeapPriorityQueue(long[] lArray) {
        this(lArray, lArray.length);
    }

    public LongHeapPriorityQueue(LongCollection longCollection, LongComparator longComparator) {
        this(longCollection.toLongArray(), longComparator);
    }

    public LongHeapPriorityQueue(LongCollection longCollection) {
        this(longCollection, (LongComparator)null);
    }

    public LongHeapPriorityQueue(Collection<? extends Long> collection, LongComparator longComparator) {
        this(collection.size(), longComparator);
        Iterator<? extends Long> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next();
        }
    }

    public LongHeapPriorityQueue(Collection<? extends Long> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(long l) {
        if (this.size == this.heap.length) {
            this.heap = LongArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = l;
        LongHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public long dequeueLong() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        long l = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            LongHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return l;
    }

    @Override
    public long firstLong() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        LongHeaps.downHeap(this.heap, this.size, 0, this.c);
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
        this.heap = LongArrays.trim(this.heap, this.size);
    }

    @Override
    public LongComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new long[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readLong();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

