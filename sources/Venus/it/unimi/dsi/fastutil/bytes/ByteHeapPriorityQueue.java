/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteHeaps;
import it.unimi.dsi.fastutil.bytes.BytePriorityQueue;
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
public class ByteHeapPriorityQueue
implements BytePriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient byte[] heap = ByteArrays.EMPTY_ARRAY;
    protected int size;
    protected ByteComparator c;

    public ByteHeapPriorityQueue(int n, ByteComparator byteComparator) {
        if (n > 0) {
            this.heap = new byte[n];
        }
        this.c = byteComparator;
    }

    public ByteHeapPriorityQueue(int n) {
        this(n, null);
    }

    public ByteHeapPriorityQueue(ByteComparator byteComparator) {
        this(0, byteComparator);
    }

    public ByteHeapPriorityQueue() {
        this(0, null);
    }

    public ByteHeapPriorityQueue(byte[] byArray, int n, ByteComparator byteComparator) {
        this(byteComparator);
        this.heap = byArray;
        this.size = n;
        ByteHeaps.makeHeap(byArray, n, byteComparator);
    }

    public ByteHeapPriorityQueue(byte[] byArray, ByteComparator byteComparator) {
        this(byArray, byArray.length, byteComparator);
    }

    public ByteHeapPriorityQueue(byte[] byArray, int n) {
        this(byArray, n, null);
    }

    public ByteHeapPriorityQueue(byte[] byArray) {
        this(byArray, byArray.length);
    }

    public ByteHeapPriorityQueue(ByteCollection byteCollection, ByteComparator byteComparator) {
        this(byteCollection.toByteArray(), byteComparator);
    }

    public ByteHeapPriorityQueue(ByteCollection byteCollection) {
        this(byteCollection, (ByteComparator)null);
    }

    public ByteHeapPriorityQueue(Collection<? extends Byte> collection, ByteComparator byteComparator) {
        this(collection.size(), byteComparator);
        Iterator<? extends Byte> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next();
        }
    }

    public ByteHeapPriorityQueue(Collection<? extends Byte> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(byte by) {
        if (this.size == this.heap.length) {
            this.heap = ByteArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = by;
        ByteHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public byte dequeueByte() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        byte by = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            ByteHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return by;
    }

    @Override
    public byte firstByte() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        ByteHeaps.downHeap(this.heap, this.size, 0, this.c);
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
        this.heap = ByteArrays.trim(this.heap, this.size);
    }

    @Override
    public ByteComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new byte[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readByte();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

