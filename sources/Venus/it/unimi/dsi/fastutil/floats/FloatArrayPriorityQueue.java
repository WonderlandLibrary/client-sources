/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatArrays;
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
public class FloatArrayPriorityQueue
implements FloatPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient float[] array = FloatArrays.EMPTY_ARRAY;
    protected int size;
    protected FloatComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public FloatArrayPriorityQueue(int n, FloatComparator floatComparator) {
        if (n > 0) {
            this.array = new float[n];
        }
        this.c = floatComparator;
    }

    public FloatArrayPriorityQueue(int n) {
        this(n, null);
    }

    public FloatArrayPriorityQueue(FloatComparator floatComparator) {
        this(0, floatComparator);
    }

    public FloatArrayPriorityQueue() {
        this(0, null);
    }

    public FloatArrayPriorityQueue(float[] fArray, int n, FloatComparator floatComparator) {
        this(floatComparator);
        this.array = fArray;
        this.size = n;
    }

    public FloatArrayPriorityQueue(float[] fArray, FloatComparator floatComparator) {
        this(fArray, fArray.length, floatComparator);
    }

    public FloatArrayPriorityQueue(float[] fArray, int n) {
        this(fArray, n, null);
    }

    public FloatArrayPriorityQueue(float[] fArray) {
        this(fArray, fArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        float f = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (Float.compare(this.array[n], f) >= 0) continue;
                n2 = n;
                f = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], f) >= 0) continue;
                n2 = n;
                f = this.array[n2];
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
    public void enqueue(float f) {
        if (this.size == this.array.length) {
            this.array = FloatArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (Float.compare(f, this.array[this.firstIndex]) < 0) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(f, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = f;
    }

    @Override
    public float dequeueFloat() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        float f = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return f;
    }

    @Override
    public float firstFloat() {
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
        this.array = FloatArrays.trim(this.array, this.size);
    }

    @Override
    public FloatComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new float[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

