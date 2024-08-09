/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleArraySet
extends AbstractDoubleSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] a;
    private int size;

    public DoubleArraySet(double[] dArray) {
        this.a = dArray;
        this.size = dArray.length;
    }

    public DoubleArraySet() {
        this.a = DoubleArrays.EMPTY_ARRAY;
    }

    public DoubleArraySet(int n) {
        this.a = new double[n];
    }

    public DoubleArraySet(DoubleCollection doubleCollection) {
        this(doubleCollection.size());
        this.addAll(doubleCollection);
    }

    public DoubleArraySet(Collection<? extends Double> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public DoubleArraySet(double[] dArray, int n) {
        this.a = dArray;
        this.size = n;
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + dArray.length + ")");
        }
    }

    private int findKey(double d) {
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(this.a[n]) != Double.doubleToLongBits(d)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public DoubleIterator iterator() {
        return new DoubleIterator(this){
            int next;
            final DoubleArraySet this$0;
            {
                this.this$0 = doubleArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < DoubleArraySet.access$000(this.this$0);
            }

            @Override
            public double nextDouble() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return DoubleArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = DoubleArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(DoubleArraySet.access$100(this.this$0), this.next + 1, DoubleArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(double d) {
        return this.findKey(d) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        return false;
    }

    @Override
    public boolean add(double d) {
        int n = this.findKey(d);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.a[n2];
            }
            this.a = dArray;
        }
        this.a[this.size++] = d;
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public DoubleArraySet clone() {
        DoubleArraySet doubleArraySet;
        try {
            doubleArraySet = (DoubleArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        doubleArraySet.a = (double[])this.a.clone();
        return doubleArraySet;
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
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(DoubleArraySet doubleArraySet) {
        return doubleArraySet.size;
    }

    static double[] access$100(DoubleArraySet doubleArraySet) {
        return doubleArraySet.a;
    }

    static int access$010(DoubleArraySet doubleArraySet) {
        return doubleArraySet.size--;
    }
}

