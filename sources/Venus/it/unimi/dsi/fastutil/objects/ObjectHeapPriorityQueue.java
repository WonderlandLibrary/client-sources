/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectHeaps;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectHeapPriorityQueue<K>
implements PriorityQueue<K>,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient K[] heap = ObjectArrays.EMPTY_ARRAY;
    protected int size;
    protected Comparator<? super K> c;

    public ObjectHeapPriorityQueue(int n, Comparator<? super K> comparator) {
        if (n > 0) {
            this.heap = new Object[n];
        }
        this.c = comparator;
    }

    public ObjectHeapPriorityQueue(int n) {
        this(n, null);
    }

    public ObjectHeapPriorityQueue(Comparator<? super K> comparator) {
        this(0, comparator);
    }

    public ObjectHeapPriorityQueue() {
        this(0, null);
    }

    public ObjectHeapPriorityQueue(K[] KArray, int n, Comparator<? super K> comparator) {
        this(comparator);
        this.heap = KArray;
        this.size = n;
        ObjectHeaps.makeHeap(KArray, n, comparator);
    }

    public ObjectHeapPriorityQueue(K[] KArray, Comparator<? super K> comparator) {
        this(KArray, KArray.length, comparator);
    }

    public ObjectHeapPriorityQueue(K[] KArray, int n) {
        this(KArray, n, null);
    }

    public ObjectHeapPriorityQueue(K[] KArray) {
        this(KArray, KArray.length);
    }

    public ObjectHeapPriorityQueue(Collection<? extends K> collection, Comparator<? super K> comparator) {
        this(collection.toArray(), comparator);
    }

    public ObjectHeapPriorityQueue(Collection<? extends K> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(K k) {
        if (this.size == this.heap.length) {
            this.heap = ObjectArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = k;
        ObjectHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public K dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        K k = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        this.heap[this.size] = null;
        if (this.size != 0) {
            ObjectHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return k;
    }

    @Override
    public K first() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        ObjectHeaps.downHeap(this.heap, this.size, 0, this.c);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        Arrays.fill(this.heap, 0, this.size, null);
        this.size = 0;
    }

    public void trim() {
        this.heap = ObjectArrays.trim(this.heap, this.size);
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new Object[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readObject();
        }
    }
}

