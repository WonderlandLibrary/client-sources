/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ShortArrayPriorityQueue
implements ShortPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient short[] array = ShortArrays.EMPTY_ARRAY;
    protected int size;
    protected ShortComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public ShortArrayPriorityQueue(int n, ShortComparator shortComparator) {
        if (n > 0) {
            this.array = new short[n];
        }
        this.c = shortComparator;
    }

    public ShortArrayPriorityQueue(int n) {
        this(n, null);
    }

    public ShortArrayPriorityQueue(ShortComparator shortComparator) {
        this(0, shortComparator);
    }

    public ShortArrayPriorityQueue() {
        this(0, null);
    }

    public ShortArrayPriorityQueue(short[] sArray, int n, ShortComparator shortComparator) {
        this(shortComparator);
        this.array = sArray;
        this.size = n;
    }

    public ShortArrayPriorityQueue(short[] sArray, ShortComparator shortComparator) {
        this(sArray, sArray.length, shortComparator);
    }

    public ShortArrayPriorityQueue(short[] sArray, int n) {
        this(sArray, n, null);
    }

    public ShortArrayPriorityQueue(short[] sArray) {
        this(sArray, sArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        short s = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.array[n] >= s) continue;
                n2 = n;
                s = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], s) >= 0) continue;
                n2 = n;
                s = this.array[n2];
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
    public void enqueue(short s) {
        if (this.size == this.array.length) {
            this.array = ShortArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (s < this.array[this.firstIndex]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(s, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = s;
    }

    @Override
    public short dequeueShort() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        short s = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return s;
    }

    @Override
    public short firstShort() {
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
        this.array = ShortArrays.trim(this.array, this.size);
    }

    @Override
    public ShortComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new short[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readShort();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

