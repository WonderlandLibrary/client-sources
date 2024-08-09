/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.PriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectArrayFIFOQueue<K>
implements PriorityQueue<K>,
Serializable {
    private static final long serialVersionUID = 0L;
    public static final int INITIAL_CAPACITY = 4;
    protected transient K[] array;
    protected transient int length;
    protected transient int start;
    protected transient int end;

    public ObjectArrayFIFOQueue(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.array = new Object[Math.max(1, n)];
        this.length = this.array.length;
    }

    public ObjectArrayFIFOQueue() {
        this(4);
    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public K dequeue() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        K k = this.array[this.start];
        this.array[this.start] = null;
        if (++this.start == this.length) {
            this.start = 0;
        }
        this.reduce();
        return k;
    }

    public K dequeueLast() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        if (this.end == 0) {
            this.end = this.length;
        }
        K k = this.array[--this.end];
        this.array[this.end] = null;
        this.reduce();
        return k;
    }

    private final void resize(int n, int n2) {
        Object[] objectArray = new Object[n2];
        if (this.start >= this.end) {
            if (n != 0) {
                System.arraycopy(this.array, this.start, objectArray, 0, this.length - this.start);
                System.arraycopy(this.array, 0, objectArray, this.length - this.start, this.end);
            }
        } else {
            System.arraycopy(this.array, this.start, objectArray, 0, this.end - this.start);
        }
        this.start = 0;
        this.end = n;
        this.array = objectArray;
        this.length = n2;
    }

    private final void expand() {
        this.resize(this.length, (int)Math.min(0x7FFFFFF7L, 2L * (long)this.length));
    }

    private final void reduce() {
        int n = this.size();
        if (this.length > 4 && n <= this.length / 4) {
            this.resize(n, this.length / 2);
        }
    }

    @Override
    public void enqueue(K k) {
        this.array[this.end++] = k;
        if (this.end == this.length) {
            this.end = 0;
        }
        if (this.end == this.start) {
            this.expand();
        }
    }

    public void enqueueFirst(K k) {
        if (this.start == 0) {
            this.start = this.length;
        }
        this.array[--this.start] = k;
        if (this.end == this.start) {
            this.expand();
        }
    }

    @Override
    public K first() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[this.start];
    }

    @Override
    public K last() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[(this.end == 0 ? this.length : this.end) - 1];
    }

    @Override
    public void clear() {
        if (this.start <= this.end) {
            Arrays.fill(this.array, this.start, this.end, null);
        } else {
            Arrays.fill(this.array, this.start, this.length, null);
            Arrays.fill(this.array, 0, this.end, null);
        }
        this.end = 0;
        this.start = 0;
    }

    public void trim() {
        int n = this.size();
        Object[] objectArray = new Object[n + 1];
        if (this.start <= this.end) {
            System.arraycopy(this.array, this.start, objectArray, 0, this.end - this.start);
        } else {
            System.arraycopy(this.array, this.start, objectArray, 0, this.length - this.start);
            System.arraycopy(this.array, 0, objectArray, this.length - this.start, this.end);
        }
        this.start = 0;
        this.end = n;
        this.length = this.end + 1;
        this.array = objectArray;
    }

    @Override
    public int size() {
        int n = this.end - this.start;
        return n >= 0 ? n : this.length + n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.size();
        objectOutputStream.writeInt(n);
        int n2 = this.start;
        while (n-- != 0) {
            objectOutputStream.writeObject(this.array[n2++]);
            if (n2 != this.length) continue;
            n2 = 0;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.end = objectInputStream.readInt();
        this.length = HashCommon.nextPowerOfTwo(this.end + 1);
        this.array = new Object[this.length];
        for (int i = 0; i < this.end; ++i) {
            this.array[i] = objectInputStream.readObject();
        }
    }
}

