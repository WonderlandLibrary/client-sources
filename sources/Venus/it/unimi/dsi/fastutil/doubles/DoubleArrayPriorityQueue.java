/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleArrays;
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
public class DoubleArrayPriorityQueue
implements DoublePriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient double[] array = DoubleArrays.EMPTY_ARRAY;
    protected int size;
    protected DoubleComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public DoubleArrayPriorityQueue(int n, DoubleComparator doubleComparator) {
        if (n > 0) {
            this.array = new double[n];
        }
        this.c = doubleComparator;
    }

    public DoubleArrayPriorityQueue(int n) {
        this(n, null);
    }

    public DoubleArrayPriorityQueue(DoubleComparator doubleComparator) {
        this(0, doubleComparator);
    }

    public DoubleArrayPriorityQueue() {
        this(0, null);
    }

    public DoubleArrayPriorityQueue(double[] dArray, int n, DoubleComparator doubleComparator) {
        this(doubleComparator);
        this.array = dArray;
        this.size = n;
    }

    public DoubleArrayPriorityQueue(double[] dArray, DoubleComparator doubleComparator) {
        this(dArray, dArray.length, doubleComparator);
    }

    public DoubleArrayPriorityQueue(double[] dArray, int n) {
        this(dArray, n, null);
    }

    public DoubleArrayPriorityQueue(double[] dArray) {
        this(dArray, dArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        double d = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (Double.compare(this.array[n], d) >= 0) continue;
                n2 = n;
                d = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], d) >= 0) continue;
                n2 = n;
                d = this.array[n2];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void enqueue(double d) {
        if (this.size == this.array.length) {
            this.array = DoubleArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (Double.compare(d, this.array[this.firstIndex]) < 0) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(d, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = d;
    }

    @Override
    public double dequeueDouble() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        double d = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return d;
    }

    @Override
    public double firstDouble() {
        this.ensureNonEmpty();
        return this.array[this.findFirst()];
    }

    @Override
    public void changed() {
        this.ensureNonEmpty();
        this.firstIndexValid = false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.firstIndexValid = false;
    }

    public void trim() {
        this.array = DoubleArrays.trim(this.array, this.size);
    }

    @Override
    public DoubleComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new double[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readDouble();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

