/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArrayPriorityQueue
implements LongPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient long[] array = LongArrays.EMPTY_ARRAY;
    protected int size;
    protected LongComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public LongArrayPriorityQueue(int n, LongComparator longComparator) {
        if (n > 0) {
            this.array = new long[n];
        }
        this.c = longComparator;
    }

    public LongArrayPriorityQueue(int n) {
        this(n, null);
    }

    public LongArrayPriorityQueue(LongComparator longComparator) {
        this(0, longComparator);
    }

    public LongArrayPriorityQueue() {
        this(0, null);
    }

    public LongArrayPriorityQueue(long[] lArray, int n, LongComparator longComparator) {
        this(longComparator);
        this.array = lArray;
        this.size = n;
    }

    public LongArrayPriorityQueue(long[] lArray, LongComparator longComparator) {
        this(lArray, lArray.length, longComparator);
    }

    public LongArrayPriorityQueue(long[] lArray, int n) {
        this(lArray, n, null);
    }

    public LongArrayPriorityQueue(long[] lArray) {
        this(lArray, lArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        long l = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.array[n] >= l) continue;
                n2 = n;
                l = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], l) >= 0) continue;
                n2 = n;
                l = this.array[n2];
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
    public void enqueue(long l) {
        if (this.size == this.array.length) {
            this.array = LongArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (l < this.array[this.firstIndex]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(l, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = l;
    }

    @Override
    public long dequeueLong() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        long l = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return l;
    }

    @Override
    public long firstLong() {
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
        this.array = LongArrays.trim(this.array, this.size);
    }

    @Override
    public LongComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new long[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readLong();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

