/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIndirectPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatArrayIndirectPriorityQueue
implements FloatIndirectPriorityQueue {
    protected float[] refArray;
    protected int[] array = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected FloatComparator c;
    protected int firstIndex;
    protected boolean firstIndexValid;

    public FloatArrayIndirectPriorityQueue(float[] fArray, int n, FloatComparator floatComparator) {
        if (n > 0) {
            this.array = new int[n];
        }
        this.refArray = fArray;
        this.c = floatComparator;
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, int n) {
        this(fArray, n, null);
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, FloatComparator floatComparator) {
        this(fArray, fArray.length, floatComparator);
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray) {
        this(fArray, fArray.length, null);
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, int[] nArray, int n, FloatComparator floatComparator) {
        this(fArray, 0, floatComparator);
        this.array = nArray;
        this.size = n;
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, int[] nArray, FloatComparator floatComparator) {
        this(fArray, nArray, nArray.length, floatComparator);
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, int[] nArray, int n) {
        this(fArray, nArray, n, null);
    }

    public FloatArrayIndirectPriorityQueue(float[] fArray, int[] nArray) {
        this(fArray, nArray, nArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        float f = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (Float.compare(this.refArray[this.array[n]], f) >= 0) continue;
                n2 = n;
                f = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.refArray[this.array[n]], f) >= 0) continue;
                n2 = n;
                f = this.refArray[this.array[n2]];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private int findLast() {
        int n = this.size;
        int n2 = --n;
        float f = this.refArray[this.array[n2]];
        if (this.c == null) {
            while (n-- != 0) {
                if (Float.compare(f, this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                f = this.refArray[this.array[n2]];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(f, this.refArray[this.array[n]]) >= 0) continue;
                n2 = n;
                f = this.refArray[this.array[n2]];
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
                if (Float.compare(this.refArray[n], this.refArray[this.array[this.firstIndex]]) < 0) {
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
        float f = this.refArray[this.array[this.findFirst()]];
        int n = this.size;
        int n2 = 0;
        while (n-- != 0) {
            if (Float.floatToIntBits(f) != Float.floatToIntBits(this.refArray[this.array[n]])) continue;
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
    public FloatComparator comparator() {
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

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

