/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoublePriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleArrayFIFOQueue
implements DoublePriorityQueue,
Serializable {
    private static final long serialVersionUID = 0L;
    public static final int INITIAL_CAPACITY = 4;
    protected transient double[] array;
    protected transient int length;
    protected transient int start;
    protected transient int end;

    public DoubleArrayFIFOQueue(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.array = new double[Math.max(1, n)];
        this.length = this.array.length;
    }

    public DoubleArrayFIFOQueue() {
        this(4);
    }

    @Override
    public DoubleComparator comparator() {
        return null;
    }

    @Override
    public double dequeueDouble() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        double d = this.array[this.start];
        if (++this.start == this.length) {
            this.start = 0;
        }
        this.reduce();
        return d;
    }

    public double dequeueLastDouble() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        if (this.end == 0) {
            this.end = this.length;
        }
        double d = this.array[--this.end];
        this.reduce();
        return d;
    }

    private final void resize(int n, int n2) {
        double[] dArray = new double[n2];
        if (this.start >= this.end) {
            if (n != 0) {
                System.arraycopy(this.array, this.start, dArray, 0, this.length - this.start);
                System.arraycopy(this.array, 0, dArray, this.length - this.start, this.end);
            }
        } else {
            System.arraycopy(this.array, this.start, dArray, 0, this.end - this.start);
        }
        this.start = 0;
        this.end = n;
        this.array = dArray;
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
    public void enqueue(double d) {
        this.array[this.end++] = d;
        if (this.end == this.length) {
            this.end = 0;
        }
        if (this.end == this.start) {
            this.expand();
        }
    }

    public void enqueueFirst(double d) {
        if (this.start == 0) {
            this.start = this.length;
        }
        this.array[--this.start] = d;
        if (this.end == this.start) {
            this.expand();
        }
    }

    @Override
    public double firstDouble() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[this.start];
    }

    @Override
    public double lastDouble() {
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
        double[] dArray = new double[n + 1];
        if (this.start <= this.end) {
            System.arraycopy(this.array, this.start, dArray, 0, this.end - this.start);
        } else {
            System.arraycopy(this.array, this.start, dArray, 0, this.length - this.start);
            System.arraycopy(this.array, 0, dArray, this.length - this.start, this.end);
        }
        this.start = 0;
        this.end = n;
        this.length = this.end + 1;
        this.array = dArray;
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
            objectOutputStream.writeDouble(this.array[n2++]);
            if (n2 != this.length) continue;
            n2 = 0;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.end = objectInputStream.readInt();
        this.length = HashCommon.nextPowerOfTwo(this.end + 1);
        this.array = new double[this.length];
        for (int i = 0; i < this.end; ++i) {
            this.array[i] = objectInputStream.readDouble();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

