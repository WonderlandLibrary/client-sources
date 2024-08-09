/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.floats.AbstractFloatList;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatArrayList
extends AbstractFloatList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient float[] a;
    protected int size;
    static final boolean $assertionsDisabled = !FloatArrayList.class.desiredAssertionStatus();

    protected FloatArrayList(float[] fArray, boolean bl) {
        this.a = fArray;
    }

    public FloatArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? FloatArrays.EMPTY_ARRAY : new float[n];
    }

    public FloatArrayList() {
        this.a = FloatArrays.DEFAULT_EMPTY_ARRAY;
    }

    public FloatArrayList(Collection<? extends Float> collection) {
        this(collection.size());
        this.size = FloatIterators.unwrap(FloatIterators.asFloatIterator(collection.iterator()), this.a);
    }

    public FloatArrayList(FloatCollection floatCollection) {
        this(floatCollection.size());
        this.size = FloatIterators.unwrap(floatCollection.iterator(), this.a);
    }

    public FloatArrayList(FloatList floatList) {
        this(floatList.size());
        this.size = floatList.size();
        floatList.getElements(0, this.a, 0, this.size);
    }

    public FloatArrayList(float[] fArray) {
        this(fArray, 0, fArray.length);
    }

    public FloatArrayList(float[] fArray, int n, int n2) {
        this(n2);
        System.arraycopy(fArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public FloatArrayList(Iterator<? extends Float> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next().floatValue());
        }
    }

    public FloatArrayList(FloatIterator floatIterator) {
        this();
        while (floatIterator.hasNext()) {
            this.add(floatIterator.nextFloat());
        }
    }

    public float[] elements() {
        return this.a;
    }

    public static FloatArrayList wrap(float[] fArray, int n) {
        if (n > fArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + fArray.length + ")");
        }
        FloatArrayList floatArrayList = new FloatArrayList(fArray, false);
        floatArrayList.size = n;
        return floatArrayList;
    }

    public static FloatArrayList wrap(float[] fArray) {
        return FloatArrayList.wrap(fArray, fArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == FloatArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = FloatArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != FloatArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = FloatArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, float f) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = f;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(float f) {
        this.grow(this.size + 1);
        this.a[this.size++] = f;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public float getFloat(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(float f) {
        for (int i = 0; i < this.size; ++i) {
            if (Float.floatToIntBits(f) != Float.floatToIntBits(this.a[i])) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(f) != Float.floatToIntBits(this.a[n])) continue;
            return n;
        }
        return 1;
    }

    @Override
    public float removeFloat(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        float f = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return f;
    }

    @Override
    public boolean rem(float f) {
        int n = this.indexOf(f);
        if (n == -1) {
            return true;
        }
        this.removeFloat(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public float set(int n, float f) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        float f2 = this.a[n];
        this.a[n] = f;
        return f2;
    }

    @Override
    public void clear() {
        this.size = 0;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int n) {
        if (n > this.a.length) {
            this.ensureCapacity(n);
        }
        if (n > this.size) {
            java.util.Arrays.fill(this.a, this.size, n, 0.0f);
        }
        this.size = n;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        float[] fArray = new float[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, fArray, 0, this.size);
        this.a = fArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, float[] fArray, int n2, int n3) {
        FloatArrays.ensureOffsetLength(fArray, n2, n3);
        System.arraycopy(this.a, n, fArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, float[] fArray, int n2, int n3) {
        this.ensureIndex(n);
        FloatArrays.ensureOffsetLength(fArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(fArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public float[] toArray(float[] fArray) {
        if (fArray == null || fArray.length < this.size) {
            fArray = new float[this.size];
        }
        System.arraycopy(this.a, 0, fArray, 0, this.size);
        return fArray;
    }

    @Override
    public boolean addAll(int n, FloatCollection floatCollection) {
        this.ensureIndex(n);
        int n2 = floatCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        FloatIterator floatIterator = floatCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = floatIterator.nextFloat();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, FloatList floatList) {
        this.ensureIndex(n);
        int n2 = floatList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        floatList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(FloatCollection floatCollection) {
        int n;
        float[] fArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (floatCollection.contains(fArray[n])) continue;
            fArray[n2++] = fArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        float[] fArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(Float.valueOf(fArray[n]))) continue;
            fArray[n2++] = fArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public FloatListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new FloatListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final FloatArrayList this$0;
            {
                this.this$0 = floatArrayList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public float nextFloat() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public float previousFloat() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.a[this.pos];
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(float f) {
                this.this$0.add(this.pos++, f);
                this.last = -1;
            }

            @Override
            public void set(float f) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, f);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeFloat(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public FloatArrayList clone() {
        FloatArrayList floatArrayList = new FloatArrayList(this.size);
        System.arraycopy(this.a, 0, floatArrayList.a, 0, this.size);
        floatArrayList.size = this.size;
        return floatArrayList;
    }

    public boolean equals(FloatArrayList floatArrayList) {
        if (floatArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != floatArrayList.size()) {
            return true;
        }
        float[] fArray = this.a;
        float[] fArray2 = floatArrayList.a;
        while (n-- != 0) {
            if (fArray[n] == fArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(FloatArrayList floatArrayList) {
        int n;
        int n2 = this.size();
        int n3 = floatArrayList.size();
        float[] fArray = this.a;
        float[] fArray2 = floatArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            float f = fArray[n];
            float f2 = fArray2[n];
            int n4 = Float.compare(f, f2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

