/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.BytePriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteArrayPriorityQueue
implements BytePriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient byte[] array = ByteArrays.EMPTY_ARRAY;
    protected int size;
    protected ByteComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public ByteArrayPriorityQueue(int n, ByteComparator byteComparator) {
        if (n > 0) {
            this.array = new byte[n];
        }
        this.c = byteComparator;
    }

    public ByteArrayPriorityQueue(int n) {
        this(n, null);
    }

    public ByteArrayPriorityQueue(ByteComparator byteComparator) {
        this(0, byteComparator);
    }

    public ByteArrayPriorityQueue() {
        this(0, null);
    }

    public ByteArrayPriorityQueue(byte[] byArray, int n, ByteComparator byteComparator) {
        this(byteComparator);
        this.array = byArray;
        this.size = n;
    }

    public ByteArrayPriorityQueue(byte[] byArray, ByteComparator byteComparator) {
        this(byArray, byArray.length, byteComparator);
    }

    public ByteArrayPriorityQueue(byte[] byArray, int n) {
        this(byArray, n, null);
    }

    public ByteArrayPriorityQueue(byte[] byArray) {
        this(byArray, byArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        byte by = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.array[n] >= by) continue;
                n2 = n;
                by = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], by) >= 0) continue;
                n2 = n;
                by = this.array[n2];
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
    public void enqueue(byte by) {
        if (this.size == this.array.length) {
            this.array = ByteArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (by < this.array[this.firstIndex]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(by, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = by;
    }

    @Override
    public byte dequeueByte() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        byte by = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return by;
    }

    @Override
    public byte firstByte() {
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
        this.array = ByteArrays.trim(this.array, this.size);
    }

    @Override
    public ByteComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new byte[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readByte();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

