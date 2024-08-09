/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;

@GwtIncompatible
public class AtomicDoubleArray
implements Serializable {
    private static final long serialVersionUID = 0L;
    private transient AtomicLongArray longs;

    public AtomicDoubleArray(int n) {
        this.longs = new AtomicLongArray(n);
    }

    public AtomicDoubleArray(double[] dArray) {
        int n = dArray.length;
        long[] lArray = new long[n];
        for (int i = 0; i < n; ++i) {
            lArray[i] = Double.doubleToRawLongBits(dArray[i]);
        }
        this.longs = new AtomicLongArray(lArray);
    }

    public final int length() {
        return this.longs.length();
    }

    public final double get(int n) {
        return Double.longBitsToDouble(this.longs.get(n));
    }

    public final void set(int n, double d) {
        long l = Double.doubleToRawLongBits(d);
        this.longs.set(n, l);
    }

    public final void lazySet(int n, double d) {
        this.set(n, d);
    }

    public final double getAndSet(int n, double d) {
        long l = Double.doubleToRawLongBits(d);
        return Double.longBitsToDouble(this.longs.getAndSet(n, l));
    }

    public final boolean compareAndSet(int n, double d, double d2) {
        return this.longs.compareAndSet(n, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(int n, double d, double d2) {
        return this.longs.weakCompareAndSet(n, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(int n, double d) {
        double d2;
        double d3;
        long l;
        long l2;
        while (!this.longs.compareAndSet(n, l2 = this.longs.get(n), l = Double.doubleToRawLongBits(d3 = (d2 = Double.longBitsToDouble(l2)) + d))) {
        }
        return d2;
    }

    @CanIgnoreReturnValue
    public double addAndGet(int n, double d) {
        double d2;
        double d3;
        long l;
        long l2;
        while (!this.longs.compareAndSet(n, l2 = this.longs.get(n), l = Double.doubleToRawLongBits(d3 = (d2 = Double.longBitsToDouble(l2)) + d))) {
        }
        return d3;
    }

    public String toString() {
        int n = this.length() - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder(19 * (n + 1));
        stringBuilder.append('[');
        int n2 = 0;
        while (true) {
            stringBuilder.append(Double.longBitsToDouble(this.longs.get(n2)));
            if (n2 == n) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(',').append(' ');
            ++n2;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.length();
        objectOutputStream.writeInt(n);
        for (int i = 0; i < n; ++i) {
            objectOutputStream.writeDouble(this.get(i));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = objectInputStream.readInt();
        this.longs = new AtomicLongArray(n);
        for (int i = 0; i < n; ++i) {
            this.set(i, objectInputStream.readDouble());
        }
    }
}

