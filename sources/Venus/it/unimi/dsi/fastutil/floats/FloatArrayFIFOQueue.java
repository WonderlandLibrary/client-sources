/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatArrayFIFOQueue
implements FloatPriorityQueue,
Serializable {
    private static final long serialVersionUID = 0L;
    public static final int INITIAL_CAPACITY = 4;
    protected transient float[] array;
    protected transient int length;
    protected transient int start;
    protected transient int end;

    public FloatArrayFIFOQueue(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.array = new float[Math.max(1, n)];
        this.length = this.array.length;
    }

    public FloatArrayFIFOQueue() {
        this(4);
    }

    @Override
    public FloatComparator comparator() {
        return null;
    }

    @Override
    public float dequeueFloat() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        float f = this.array[this.start];
        if (++this.start == this.length) {
            this.start = 0;
        }
        this.reduce();
        return f;
    }

    public float dequeueLastFloat() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        if (this.end == 0) {
            this.end = this.length;
        }
        float f = this.array[--this.end];
        this.reduce();
        return f;
    }

    private final void resize(int n, int n2) {
        float[] fArray = new float[n2];
        if (this.start >= this.end) {
            if (n != 0) {
                System.arraycopy(this.array, this.start, fArray, 0, this.length - this.start);
                System.arraycopy(this.array, 0, fArray, this.length - this.start, this.end);
            }
        } else {
            System.arraycopy(this.array, this.start, fArray, 0, this.end - this.start);
        }
        this.start = 0;
        this.end = n;
        this.array = fArray;
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
    public void enqueue(float f) {
        this.array[this.end++] = f;
        if (this.end == this.length) {
            this.end = 0;
        }
        if (this.end == this.start) {
            this.expand();
        }
    }

    public void enqueueFirst(float f) {
        if (this.start == 0) {
            this.start = this.length;
        }
        this.array[--this.start] = f;
        if (this.end == this.start) {
            this.expand();
        }
    }

    @Override
    public float firstFloat() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[this.start];
    }

    @Override
    public float lastFloat() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[(this.end == 0 ? this.length : this.end) - 1];
    }

    @Override
    public void clear() {
        this.end = 0;
        this.start = 0;
    }

    public void trim() {
        int n = this.size();
        float[] fArray = new float[n + 1];
        if (this.start <= this.end) {
            System.arraycopy(this.array, this.start, fArray, 0, this.end - this.start);
        } else {
            System.arraycopy(this.array, this.start, fArray, 0, this.length - this.start);
            System.arraycopy(this.array, 0, fArray, this.length - this.start, this.end);
        }
        this.start = 0;
        this.end = n;
        this.length = this.end + 1;
        this.array = fArray;
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
            objectOutputStream.writeFloat(this.array[n2++]);
            if (n2 != this.length) continue;
            n2 = 0;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.end = objectInputStream.readInt();
        this.length = HashCommon.nextPowerOfTwo(this.end + 1);
        this.array = new float[this.length];
        for (int i = 0; i < this.end; ++i) {
            this.array[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

