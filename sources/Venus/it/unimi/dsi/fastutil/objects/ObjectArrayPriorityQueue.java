/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectArrayPriorityQueue<K>
implements PriorityQueue<K>,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient K[] array = ObjectArrays.EMPTY_ARRAY;
    protected int size;
    protected Comparator<? super K> c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public ObjectArrayPriorityQueue(int n, Comparator<? super K> comparator) {
        if (n > 0) {
            this.array = new Object[n];
        }
        this.c = comparator;
    }

    public ObjectArrayPriorityQueue(int n) {
        this(n, null);
    }

    public ObjectArrayPriorityQueue(Comparator<? super K> comparator) {
        this(0, comparator);
    }

    public ObjectArrayPriorityQueue() {
        this(0, null);
    }

    public ObjectArrayPriorityQueue(K[] KArray, int n, Comparator<? super K> comparator) {
        this(comparator);
        this.array = KArray;
        this.size = n;
    }

    public ObjectArrayPriorityQueue(K[] KArray, Comparator<? super K> comparator) {
        this(KArray, KArray.length, comparator);
    }

    public ObjectArrayPriorityQueue(K[] KArray, int n) {
        this(KArray, n, null);
    }

    public ObjectArrayPriorityQueue(K[] KArray) {
        this(KArray, KArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        K k = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (((Comparable)this.array[n]).compareTo(k) >= 0) continue;
                n2 = n;
                k = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], k) >= 0) continue;
                n2 = n;
                k = this.array[n2];
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
    public void enqueue(K k) {
        if (this.size == this.array.length) {
            this.array = ObjectArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (((Comparable)k).compareTo(this.array[this.firstIndex]) < 0) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(k, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = k;
    }

    @Override
    public K dequeue() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        K k = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.array[this.size] = null;
        this.firstIndexValid = false;
        return k;
    }

    @Override
    public K first() {
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
        Arrays.fill(this.array, 0, this.size, null);
        this.size = 0;
        this.firstIndexValid = false;
    }

    public void trim() {
        this.array = ObjectArrays.trim(this.array, this.size);
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new Object[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readObject();
        }
    }
}

