/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortHeaps;
import it.unimi.dsi.fastutil.shorts.ShortPriorityQueue;
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
public class ShortHeapPriorityQueue
implements ShortPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient short[] heap = ShortArrays.EMPTY_ARRAY;
    protected int size;
    protected ShortComparator c;

    public ShortHeapPriorityQueue(int n, ShortComparator shortComparator) {
        if (n > 0) {
            this.heap = new short[n];
        }
        this.c = shortComparator;
    }

    public ShortHeapPriorityQueue(int n) {
        this(n, null);
    }

    public ShortHeapPriorityQueue(ShortComparator shortComparator) {
        this(0, shortComparator);
    }

    public ShortHeapPriorityQueue() {
        this(0, null);
    }

    public ShortHeapPriorityQueue(short[] sArray, int n, ShortComparator shortComparator) {
        this(shortComparator);
        this.heap = sArray;
        this.size = n;
        ShortHeaps.makeHeap(sArray, n, shortComparator);
    }

    public ShortHeapPriorityQueue(short[] sArray, ShortComparator shortComparator) {
        this(sArray, sArray.length, shortComparator);
    }

    public ShortHeapPriorityQueue(short[] sArray, int n) {
        this(sArray, n, null);
    }

    public ShortHeapPriorityQueue(short[] sArray) {
        this(sArray, sArray.length);
    }

    public ShortHeapPriorityQueue(ShortCollection shortCollection, ShortComparator shortComparator) {
        this(shortCollection.toShortArray(), shortComparator);
    }

    public ShortHeapPriorityQueue(ShortCollection shortCollection) {
        this(shortCollection, (ShortComparator)null);
    }

    public ShortHeapPriorityQueue(Collection<? extends Short> collection, ShortComparator shortComparator) {
        this(collection.size(), shortComparator);
        Iterator<? extends Short> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next();
        }
    }

    public ShortHeapPriorityQueue(Collection<? extends Short> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(short s) {
        if (this.size == this.heap.length) {
            this.heap = ShortArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = s;
        ShortHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public short dequeueShort() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        short s = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            ShortHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return s;
    }

    @Override
    public short firstShort() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        ShortHeaps.downHeap(this.heap, this.size, 0, this.c);
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
        this.heap = ShortArrays.trim(this.heap, this.size);
    }

    @Override
    public ShortComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new short[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readShort();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

