/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArrayFIFOQueue
implements IntPriorityQueue,
Serializable {
    private static final long serialVersionUID = 0L;
    public static final int INITIAL_CAPACITY = 4;
    protected transient int[] array;
    protected transient int length;
    protected transient int start;
    protected transient int end;

    public IntArrayFIFOQueue(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.array = new int[Math.max(1, n)];
        this.length = this.array.length;
    }

    public IntArrayFIFOQueue() {
        this(4);
    }

    @Override
    public IntComparator comparator() {
        return null;
    }

    @Override
    public int dequeueInt() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        int n = this.array[this.start];
        if (++this.start == this.length) {
            this.start = 0;
        }
        this.reduce();
        return n;
    }

    public int dequeueLastInt() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        if (this.end == 0) {
            this.end = this.length;
        }
        int n = this.array[--this.end];
        this.reduce();
        return n;
    }

    private final void resize(int n, int n2) {
        int[] nArray = new int[n2];
        if (this.start >= this.end) {
            if (n != 0) {
                System.arraycopy(this.array, this.start, nArray, 0, this.length - this.start);
                System.arraycopy(this.array, 0, nArray, this.length - this.start, this.end);
            }
        } else {
            System.arraycopy(this.array, this.start, nArray, 0, this.end - this.start);
        }
        this.start = 0;
        this.end = n;
        this.array = nArray;
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
    public void enqueue(int n) {
        this.array[this.end++] = n;
        if (this.end == this.length) {
            this.end = 0;
        }
        if (this.end == this.start) {
            this.expand();
        }
    }

    public void enqueueFirst(int n) {
        if (this.start == 0) {
            this.start = this.length;
        }
        this.array[--this.start] = n;
        if (this.end == this.start) {
            this.expand();
        }
    }

    @Override
    public int firstInt() {
        if (this.start == this.end) {
            throw new NoSuchElementException();
        }
        return this.array[this.start];
    }

    @Override
    public int lastInt() {
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
        int[] nArray = new int[n + 1];
        if (this.start <= this.end) {
            System.arraycopy(this.array, this.start, nArray, 0, this.end - this.start);
        } else {
            System.arraycopy(this.array, this.start, nArray, 0, this.length - this.start);
            System.arraycopy(this.array, 0, nArray, this.length - this.start, this.end);
        }
        this.start = 0;
        this.end = n;
        this.length = this.end + 1;
        this.array = nArray;
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
            objectOutputStream.writeInt(this.array[n2++]);
            if (n2 != this.length) continue;
            n2 = 0;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.end = objectInputStream.readInt();
        this.length = HashCommon.nextPowerOfTwo(this.end + 1);
        this.array = new int[this.length];
        for (int i = 0; i < this.end; ++i) {
            this.array[i] = objectInputStream.readInt();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

