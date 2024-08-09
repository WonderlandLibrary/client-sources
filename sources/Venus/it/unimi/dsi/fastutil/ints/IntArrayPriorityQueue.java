/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
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
public class IntArrayPriorityQueue
implements IntPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient int[] array = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected IntComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public IntArrayPriorityQueue(int n, IntComparator intComparator) {
        if (n > 0) {
            this.array = new int[n];
        }
        this.c = intComparator;
    }

    public IntArrayPriorityQueue(int n) {
        this(n, null);
    }

    public IntArrayPriorityQueue(IntComparator intComparator) {
        this(0, intComparator);
    }

    public IntArrayPriorityQueue() {
        this(0, null);
    }

    public IntArrayPriorityQueue(int[] nArray, int n, IntComparator intComparator) {
        this(intComparator);
        this.array = nArray;
        this.size = n;
    }

    public IntArrayPriorityQueue(int[] nArray, IntComparator intComparator) {
        this(nArray, nArray.length, intComparator);
    }

    public IntArrayPriorityQueue(int[] nArray, int n) {
        this(nArray, n, null);
    }

    public IntArrayPriorityQueue(int[] nArray) {
        this(nArray, nArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        int n3 = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.array[n] >= n3) continue;
                n2 = n;
                n3 = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], n3) >= 0) continue;
                n2 = n;
                n3 = this.array[n2];
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
    public void enqueue(int n) {
        if (this.size == this.array.length) {
            this.array = IntArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (n < this.array[this.firstIndex]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(n, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = n;
    }

    @Override
    public int dequeueInt() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        int n2 = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return n2;
    }

    @Override
    public int firstInt() {
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
        this.array = IntArrays.trim(this.array, this.size);
    }

    @Override
    public IntComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new int[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readInt();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

