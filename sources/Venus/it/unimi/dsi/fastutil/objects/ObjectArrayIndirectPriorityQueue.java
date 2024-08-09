/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectArrayIndirectPriorityQueue<K>
implements IndirectPriorityQueue<K> {
    protected K[] refArray;
    protected int[] array = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected Comparator<? super K> c;
    protected int firstIndex;
    protected boolean firstIndexValid;

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int n, Comparator<? super K> comparator) {
        if (n > 0) {
            this.array = new int[n];
        }
        this.refArray = KArray;
        this.c = comparator;
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int n) {
        this(KArray, n, null);
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, Comparator<? super K> comparator) {
        this(KArray, KArray.length, comparator);
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray) {
        this(KArray, KArray.length, null);
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int[] nArray, int n, Comparator<? super K> comparator) {
        this(KArray, 0, comparator);
        this.array = nArray;
        this.size = n;
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int[] nArray, Comparator<? super K> comparator) {
        this(KArray, nArray, nArray.length, comparator);
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int[] nArray, int n) {
        this(KArray, nArray, n, null);
    }

    public ObjectArrayIndirectPriorityQueue(K[] KArray, int[] nArray) {
        this(KArray, nArray, nArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        K k = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (((Comparable)this.refArray[this.array[n]]).compareTo(k) >= 0) continue;
                n2 = n;
                k = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.refArray[this.array[n]], k) >= 0) continue;
                n2 = n;
                k = this.refArray[this.array[n2]];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private int findLast() {
        int n = this.size;
        int n2 = --n;
        K k = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (((Comparable)k).compareTo(this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                k = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(k, this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                k = this.refArray[this.array[n2]];
            }
        }
        return n2;
    }

    protected final void ensureNonEmpty() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }

    protected void ensureElement(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.refArray.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
        }
    }

    @Override
    public void enqueue(int n) {
        this.ensureElement(n);
        if (this.size == this.array.length) {
            this.array = IntArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (((Comparable)this.refArray[n]).compareTo(this.refArray[this.array[this.firstIndex]]) < 0) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(this.refArray[n], this.refArray[this.array[this.firstIndex]]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = n;
    }

    @Override
    public int dequeue() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        int n2 = this.array[n];
        if (--this.size != 0) {
            System.arraycopy(this.array, n + 1, this.array, n, this.size - n);
        }
        this.firstIndexValid = false;
        return n2;
    }

    @Override
    public int first() {
        this.ensureNonEmpty();
        return this.array[this.findFirst()];
    }

    @Override
    public int last() {
        this.ensureNonEmpty();
        return this.array[this.findLast()];
    }

    @Override
    public void changed() {
        this.ensureNonEmpty();
        this.firstIndexValid = false;
    }

    @Override
    public void changed(int n) {
        this.ensureElement(n);
        if (n == this.firstIndex) {
            this.firstIndexValid = false;
        }
    }

    @Override
    public void allChanged() {
        this.firstIndexValid = false;
    }

    @Override
    public boolean remove(int n) {
        this.ensureElement(n);
        int[] nArray = this.array;
        int n2 = this.size;
        while (n2-- != 0 && nArray[n2] != n) {
        }
        if (n2 < 0) {
            return true;
        }
        this.firstIndexValid = false;
        if (--this.size != 0) {
            System.arraycopy(nArray, n2 + 1, nArray, n2, this.size - n2);
        }
        return false;
    }

    @Override
    public int front(int[] nArray) {
        K k = this.refArray[this.array[this.findFirst()]];
        int n = this.size;
        int n2 = 0;
        while (n-- != 0) {
            if (!k.equals(this.refArray[this.array[n]])) continue;
            nArray[n2++] = this.array[n];
        }
        return n2;
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
    public Comparator<? super K> comparator() {
        return this.c;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.refArray[this.array[i]]);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

