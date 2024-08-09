/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
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
public class DoubleArrayList
extends AbstractDoubleList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient double[] a;
    protected int size;
    static final boolean $assertionsDisabled = !DoubleArrayList.class.desiredAssertionStatus();

    protected DoubleArrayList(double[] dArray, boolean bl) {
        this.a = dArray;
    }

    public DoubleArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? DoubleArrays.EMPTY_ARRAY : new double[n];
    }

    public DoubleArrayList() {
        this.a = DoubleArrays.DEFAULT_EMPTY_ARRAY;
    }

    public DoubleArrayList(Collection<? extends Double> collection) {
        this(collection.size());
        this.size = DoubleIterators.unwrap(DoubleIterators.asDoubleIterator(collection.iterator()), this.a);
    }

    public DoubleArrayList(DoubleCollection doubleCollection) {
        this(doubleCollection.size());
        this.size = DoubleIterators.unwrap(doubleCollection.iterator(), this.a);
    }

    public DoubleArrayList(DoubleList doubleList) {
        this(doubleList.size());
        this.size = doubleList.size();
        doubleList.getElements(0, this.a, 0, this.size);
    }

    public DoubleArrayList(double[] dArray) {
        this(dArray, 0, dArray.length);
    }

    public DoubleArrayList(double[] dArray, int n, int n2) {
        this(n2);
        System.arraycopy(dArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public DoubleArrayList(Iterator<? extends Double> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((double)iterator2.next());
        }
    }

    public DoubleArrayList(DoubleIterator doubleIterator) {
        this();
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public double[] elements() {
        return this.a;
    }

    public static DoubleArrayList wrap(double[] dArray, int n) {
        if (n > dArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + dArray.length + ")");
        }
        DoubleArrayList doubleArrayList = new DoubleArrayList(dArray, false);
        doubleArrayList.size = n;
        return doubleArrayList;
    }

    public static DoubleArrayList wrap(double[] dArray) {
        return DoubleArrayList.wrap(dArray, dArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == DoubleArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = DoubleArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != DoubleArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = DoubleArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, double d) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = d;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(double d) {
        this.grow(this.size + 1);
        this.a[this.size++] = d;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public double getDouble(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(double d) {
        for (int i = 0; i < this.size; ++i) {
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(this.a[i])) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(double d) {
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(this.a[n])) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double removeDouble(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        double d = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return d;
    }

    @Override
    public boolean rem(double d) {
        int n = this.indexOf(d);
        if (n == -1) {
            return true;
        }
        this.removeDouble(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public double set(int n, double d) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        double d2 = this.a[n];
        this.a[n] = d;
        return d2;
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
            java.util.Arrays.fill(this.a, this.size, n, 0.0);
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
        double[] dArray = new double[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, dArray, 0, this.size);
        this.a = dArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, double[] dArray, int n2, int n3) {
        DoubleArrays.ensureOffsetLength(dArray, n2, n3);
        System.arraycopy(this.a, n, dArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, double[] dArray, int n2, int n3) {
        this.ensureIndex(n);
        DoubleArrays.ensureOffsetLength(dArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(dArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public double[] toArray(double[] dArray) {
        if (dArray == null || dArray.length < this.size) {
            dArray = new double[this.size];
        }
        System.arraycopy(this.a, 0, dArray, 0, this.size);
        return dArray;
    }

    @Override
    public boolean addAll(int n, DoubleCollection doubleCollection) {
        this.ensureIndex(n);
        int n2 = doubleCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        DoubleIterator doubleIterator = doubleCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = doubleIterator.nextDouble();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, DoubleList doubleList) {
        this.ensureIndex(n);
        int n2 = doubleList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        doubleList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(DoubleCollection doubleCollection) {
        int n;
        double[] dArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (doubleCollection.contains(dArray[n])) continue;
            dArray[n2++] = dArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        double[] dArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(dArray[n])) continue;
            dArray[n2++] = dArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public DoubleListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new DoubleListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final DoubleArrayList this$0;
            {
                this.this$0 = doubleArrayList;
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
            public double nextDouble() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public double previousDouble() {
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
            public void add(double d) {
                this.this$0.add(this.pos++, d);
                this.last = -1;
            }

            @Override
            public void set(double d) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, d);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeDouble(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public DoubleArrayList clone() {
        DoubleArrayList doubleArrayList = new DoubleArrayList(this.size);
        System.arraycopy(this.a, 0, doubleArrayList.a, 0, this.size);
        doubleArrayList.size = this.size;
        return doubleArrayList;
    }

    public boolean equals(DoubleArrayList doubleArrayList) {
        if (doubleArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != doubleArrayList.size()) {
            return true;
        }
        double[] dArray = this.a;
        double[] dArray2 = doubleArrayList.a;
        while (n-- != 0) {
            if (dArray[n] == dArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(DoubleArrayList doubleArrayList) {
        int n;
        int n2 = this.size();
        int n3 = doubleArrayList.size();
        double[] dArray = this.a;
        double[] dArray2 = doubleArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            double d = dArray[n];
            double d2 = dArray2[n];
            int n4 = Double.compare(d, d2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readDouble();
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

