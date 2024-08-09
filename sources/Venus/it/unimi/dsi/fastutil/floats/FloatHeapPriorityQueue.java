/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatHeaps;
import it.unimi.dsi.fastutil.floats.FloatPriorityQueue;
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
public class FloatHeapPriorityQueue
implements FloatPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient float[] heap = FloatArrays.EMPTY_ARRAY;
    protected int size;
    protected FloatComparator c;

    public FloatHeapPriorityQueue(int n, FloatComparator floatComparator) {
        if (n > 0) {
            this.heap = new float[n];
        }
        this.c = floatComparator;
    }

    public FloatHeapPriorityQueue(int n) {
        this(n, null);
    }

    public FloatHeapPriorityQueue(FloatComparator floatComparator) {
        this(0, floatComparator);
    }

    public FloatHeapPriorityQueue() {
        this(0, null);
    }

    public FloatHeapPriorityQueue(float[] fArray, int n, FloatComparator floatComparator) {
        this(floatComparator);
        this.heap = fArray;
        this.size = n;
        FloatHeaps.makeHeap(fArray, n, floatComparator);
    }

    public FloatHeapPriorityQueue(float[] fArray, FloatComparator floatComparator) {
        this(fArray, fArray.length, floatComparator);
    }

    public FloatHeapPriorityQueue(float[] fArray, int n) {
        this(fArray, n, null);
    }

    public FloatHeapPriorityQueue(float[] fArray) {
        this(fArray, fArray.length);
    }

    public FloatHeapPriorityQueue(FloatCollection floatCollection, FloatComparator floatComparator) {
        this(floatCollection.toFloatArray(), floatComparator);
    }

    public FloatHeapPriorityQueue(FloatCollection floatCollection) {
        this(floatCollection, (FloatComparator)null);
    }

    public FloatHeapPriorityQueue(Collection<? extends Float> collection, FloatComparator floatComparator) {
        this(collection.size(), floatComparator);
        Iterator<? extends Float> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next().floatValue();
        }
    }

    public FloatHeapPriorityQueue(Collection<? extends Float> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(float f) {
        if (this.size == this.heap.length) {
            this.heap = FloatArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = f;
        FloatHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public float dequeueFloat() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        float f = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            FloatHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return f;
    }

    @Override
    public float firstFloat() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        FloatHeaps.downHeap(this.heap, this.size, 0, this.c);
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
        this.heap = FloatArrays.trim(this.heap, this.size);
    }

    @Override
    public FloatComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new float[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

