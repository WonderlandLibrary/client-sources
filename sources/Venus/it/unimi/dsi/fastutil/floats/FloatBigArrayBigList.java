/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatBigList;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatBigList;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatBigArrayBigList
extends AbstractFloatBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient float[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !FloatBigArrayBigList.class.desiredAssertionStatus();

    protected FloatBigArrayBigList(float[][] fArray, boolean bl) {
        this.a = fArray;
    }

    public FloatBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? FloatBigArrays.EMPTY_BIG_ARRAY : FloatBigArrays.newBigArray(l);
    }

    public FloatBigArrayBigList() {
        this.a = FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public FloatBigArrayBigList(FloatCollection floatCollection) {
        this(floatCollection.size());
        FloatIterator floatIterator = floatCollection.iterator();
        while (floatIterator.hasNext()) {
            this.add(floatIterator.nextFloat());
        }
    }

    public FloatBigArrayBigList(FloatBigList floatBigList) {
        this(floatBigList.size64());
        this.size = floatBigList.size64();
        floatBigList.getElements(0L, this.a, 0L, this.size);
    }

    public FloatBigArrayBigList(float[][] fArray) {
        this(fArray, 0L, FloatBigArrays.length(fArray));
    }

    public FloatBigArrayBigList(float[][] fArray, long l, long l2) {
        this(l2);
        FloatBigArrays.copy(fArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public FloatBigArrayBigList(Iterator<? extends Float> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next().floatValue());
        }
    }

    public FloatBigArrayBigList(FloatIterator floatIterator) {
        this();
        while (floatIterator.hasNext()) {
            this.add(floatIterator.nextFloat());
        }
    }

    public float[][] elements() {
        return this.a;
    }

    public static FloatBigArrayBigList wrap(float[][] fArray, long l) {
        if (l > FloatBigArrays.length(fArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + FloatBigArrays.length(fArray) + ")");
        }
        FloatBigArrayBigList floatBigArrayBigList = new FloatBigArrayBigList(fArray, false);
        floatBigArrayBigList.size = l;
        return floatBigArrayBigList;
    }

    public static FloatBigArrayBigList wrap(float[][] fArray) {
        return FloatBigArrayBigList.wrap(fArray, FloatBigArrays.length(fArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = FloatBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = FloatBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = FloatBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, float f) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            FloatBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        FloatBigArrays.set(this.a, l, f);
        ++this.size;
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(float f) {
        this.grow(this.size + 1L);
        FloatBigArrays.set(this.a, this.size++, f);
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public float getFloat(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return FloatBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(float f) {
        for (long i = 0L; i < this.size; ++i) {
            if (Float.floatToIntBits(f) != Float.floatToIntBits(FloatBigArrays.get(this.a, i))) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(float f) {
        long l = this.size;
        while (l-- != 0L) {
            if (Float.floatToIntBits(f) != Float.floatToIntBits(FloatBigArrays.get(this.a, l))) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public float removeFloat(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        float f = FloatBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            FloatBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return f;
    }

    @Override
    public boolean rem(float f) {
        long l = this.indexOf(f);
        if (l == -1L) {
            return true;
        }
        this.removeFloat(l);
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public float set(long l, float f) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        float f2 = FloatBigArrays.get(this.a, l);
        FloatBigArrays.set(this.a, l, f);
        return f2;
    }

    @Override
    public boolean removeAll(FloatCollection floatCollection) {
        long l;
        float[] fArray = null;
        float[] fArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                fArray = this.a[++n];
            }
            if (!floatCollection.contains((float)fArray[n2])) {
                if (n4 == 0x8000000) {
                    fArray2 = this.a[++n3];
                    n4 = 0;
                }
                fArray2[n4++] = fArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        long l;
        float[] fArray = null;
        float[] fArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                fArray = this.a[++n];
            }
            if (!collection.contains(Float.valueOf((float)fArray[n2]))) {
                if (n4 == 0x8000000) {
                    fArray2 = this.a[++n3];
                    n4 = 0;
                }
                fArray2[n4++] = fArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public void clear() {
        this.size = 0L;
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > FloatBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            FloatBigArrays.fill(this.a, this.size, l, 0.0f);
        }
        this.size = l;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public void trim() {
        this.trim(0L);
    }

    public void trim(long l) {
        long l2 = FloatBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = FloatBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > FloatBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, float[][] fArray, long l2, long l3) {
        FloatBigArrays.copy(this.a, l, fArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        FloatBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, float[][] fArray, long l2, long l3) {
        this.ensureIndex(l);
        FloatBigArrays.ensureOffsetLength(fArray, l2, l3);
        this.grow(this.size + l3);
        FloatBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        FloatBigArrays.copy(fArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public FloatBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new FloatBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final FloatBigArrayBigList this$0;
            {
                this.this$0 = floatBigArrayBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public float nextFloat() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return FloatBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public float previousFloat() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return FloatBigArrays.get(this.this$0.a, this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(float f) {
                this.this$0.add(this.pos++, f);
                this.last = -1L;
            }

            @Override
            public void set(float f) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, f);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeFloat(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public FloatBigArrayBigList clone() {
        FloatBigArrayBigList floatBigArrayBigList = new FloatBigArrayBigList(this.size);
        FloatBigArrays.copy(this.a, 0L, floatBigArrayBigList.a, 0L, this.size);
        floatBigArrayBigList.size = this.size;
        return floatBigArrayBigList;
    }

    public boolean equals(FloatBigArrayBigList floatBigArrayBigList) {
        if (floatBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != floatBigArrayBigList.size64()) {
            return true;
        }
        float[][] fArray = this.a;
        float[][] fArray2 = floatBigArrayBigList.a;
        while (l-- != 0L) {
            if (FloatBigArrays.get(fArray, l) == FloatBigArrays.get(fArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(FloatBigArrayBigList floatBigArrayBigList) {
        long l = this.size64();
        long l2 = floatBigArrayBigList.size64();
        float[][] fArray = this.a;
        float[][] fArray2 = floatBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            float f;
            float f2 = FloatBigArrays.get(fArray, n);
            int n2 = Float.compare(f2, f = FloatBigArrays.get(fArray2, n));
            if (n2 != 0) {
                return n2;
            }
            ++n;
        }
        return (long)n < l2 ? -1 : ((long)n < l ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = 0;
        while ((long)n < this.size) {
            objectOutputStream.writeFloat(FloatBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = FloatBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            FloatBigArrays.set(this.a, n, objectInputStream.readFloat());
            ++n;
        }
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

