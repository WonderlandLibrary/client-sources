/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
public class DoubleBigArrayBigList
extends AbstractDoubleBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient double[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !DoubleBigArrayBigList.class.desiredAssertionStatus();

    protected DoubleBigArrayBigList(double[][] dArray, boolean bl) {
        this.a = dArray;
    }

    public DoubleBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? DoubleBigArrays.EMPTY_BIG_ARRAY : DoubleBigArrays.newBigArray(l);
    }

    public DoubleBigArrayBigList() {
        this.a = DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public DoubleBigArrayBigList(DoubleCollection doubleCollection) {
        this(doubleCollection.size());
        DoubleIterator doubleIterator = doubleCollection.iterator();
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public DoubleBigArrayBigList(DoubleBigList doubleBigList) {
        this(doubleBigList.size64());
        this.size = doubleBigList.size64();
        doubleBigList.getElements(0L, this.a, 0L, this.size);
    }

    public DoubleBigArrayBigList(double[][] dArray) {
        this(dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public DoubleBigArrayBigList(double[][] dArray, long l, long l2) {
        this(l2);
        DoubleBigArrays.copy(dArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public DoubleBigArrayBigList(Iterator<? extends Double> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((double)iterator2.next());
        }
    }

    public DoubleBigArrayBigList(DoubleIterator doubleIterator) {
        this();
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public double[][] elements() {
        return this.a;
    }

    public static DoubleBigArrayBigList wrap(double[][] dArray, long l) {
        if (l > DoubleBigArrays.length(dArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + DoubleBigArrays.length(dArray) + ")");
        }
        DoubleBigArrayBigList doubleBigArrayBigList = new DoubleBigArrayBigList(dArray, false);
        doubleBigArrayBigList.size = l;
        return doubleBigArrayBigList;
    }

    public static DoubleBigArrayBigList wrap(double[][] dArray) {
        return DoubleBigArrayBigList.wrap(dArray, DoubleBigArrays.length(dArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = DoubleBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = DoubleBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = DoubleBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, double d) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            DoubleBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        DoubleBigArrays.set(this.a, l, d);
        ++this.size;
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(double d) {
        this.grow(this.size + 1L);
        DoubleBigArrays.set(this.a, this.size++, d);
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public double getDouble(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return DoubleBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(double d) {
        for (long i = 0L; i < this.size; ++i) {
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(DoubleBigArrays.get(this.a, i))) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(double d) {
        long l = this.size;
        while (l-- != 0L) {
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(DoubleBigArrays.get(this.a, l))) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public double removeDouble(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        double d = DoubleBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            DoubleBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return d;
    }

    @Override
    public boolean rem(double d) {
        long l = this.indexOf(d);
        if (l == -1L) {
            return true;
        }
        this.removeDouble(l);
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public double set(long l, double d) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        double d2 = DoubleBigArrays.get(this.a, l);
        DoubleBigArrays.set(this.a, l, d);
        return d2;
    }

    @Override
    public boolean removeAll(DoubleCollection doubleCollection) {
        long l;
        double[] dArray = null;
        double[] dArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                dArray = this.a[++n];
            }
            if (!doubleCollection.contains((double)dArray[n2])) {
                if (n4 == 0x8000000) {
                    dArray2 = this.a[++n3];
                    n4 = 0;
                }
                dArray2[n4++] = dArray[n2];
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
        double[] dArray = null;
        double[] dArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                dArray = this.a[++n];
            }
            if (!collection.contains((double)dArray[n2])) {
                if (n4 == 0x8000000) {
                    dArray2 = this.a[++n3];
                    n4 = 0;
                }
                dArray2[n4++] = dArray[n2];
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
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > DoubleBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            DoubleBigArrays.fill(this.a, this.size, l, 0.0);
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
        long l2 = DoubleBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = DoubleBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > DoubleBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, double[][] dArray, long l2, long l3) {
        DoubleBigArrays.copy(this.a, l, dArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        DoubleBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, double[][] dArray, long l2, long l3) {
        this.ensureIndex(l);
        DoubleBigArrays.ensureOffsetLength(dArray, l2, l3);
        this.grow(this.size + l3);
        DoubleBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        DoubleBigArrays.copy(dArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public DoubleBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new DoubleBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final DoubleBigArrayBigList this$0;
            {
                this.this$0 = doubleBigArrayBigList;
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
            public double nextDouble() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return DoubleBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public double previousDouble() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return DoubleBigArrays.get(this.this$0.a, this.pos);
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
            public void add(double d) {
                this.this$0.add(this.pos++, d);
                this.last = -1L;
            }

            @Override
            public void set(double d) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, d);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeDouble(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public DoubleBigArrayBigList clone() {
        DoubleBigArrayBigList doubleBigArrayBigList = new DoubleBigArrayBigList(this.size);
        DoubleBigArrays.copy(this.a, 0L, doubleBigArrayBigList.a, 0L, this.size);
        doubleBigArrayBigList.size = this.size;
        return doubleBigArrayBigList;
    }

    public boolean equals(DoubleBigArrayBigList doubleBigArrayBigList) {
        if (doubleBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != doubleBigArrayBigList.size64()) {
            return true;
        }
        double[][] dArray = this.a;
        double[][] dArray2 = doubleBigArrayBigList.a;
        while (l-- != 0L) {
            if (DoubleBigArrays.get(dArray, l) == DoubleBigArrays.get(dArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(DoubleBigArrayBigList doubleBigArrayBigList) {
        long l = this.size64();
        long l2 = doubleBigArrayBigList.size64();
        double[][] dArray = this.a;
        double[][] dArray2 = doubleBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            double d;
            double d2 = DoubleBigArrays.get(dArray, n);
            int n2 = Double.compare(d2, d = DoubleBigArrays.get(dArray2, n));
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
            objectOutputStream.writeDouble(DoubleBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = DoubleBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            DoubleBigArrays.set(this.a, n, objectInputStream.readDouble());
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

