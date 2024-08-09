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
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

@GwtIncompatible
public class AtomicDouble
extends Number
implements Serializable {
    private static final long serialVersionUID = 0L;
    private volatile transient long value;
    private static final AtomicLongFieldUpdater<AtomicDouble> updater = AtomicLongFieldUpdater.newUpdater(AtomicDouble.class, "value");

    public AtomicDouble(double d) {
        this.value = Double.doubleToRawLongBits(d);
    }

    public AtomicDouble() {
    }

    public final double get() {
        return Double.longBitsToDouble(this.value);
    }

    public final void set(double d) {
        long l;
        this.value = l = Double.doubleToRawLongBits(d);
    }

    public final void lazySet(double d) {
        this.set(d);
    }

    public final double getAndSet(double d) {
        long l = Double.doubleToRawLongBits(d);
        return Double.longBitsToDouble(updater.getAndSet(this, l));
    }

    public final boolean compareAndSet(double d, double d2) {
        return updater.compareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(double d, double d2) {
        return updater.weakCompareAndSet(this, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(double d) {
        double d2;
        double d3;
        long l;
        long l2;
        while (!updater.compareAndSet(this, l2 = this.value, l = Double.doubleToRawLongBits(d3 = (d2 = Double.longBitsToDouble(l2)) + d))) {
        }
        return d2;
    }

    @CanIgnoreReturnValue
    public final double addAndGet(double d) {
        double d2;
        double d3;
        long l;
        long l2;
        while (!updater.compareAndSet(this, l2 = this.value, l = Double.doubleToRawLongBits(d3 = (d2 = Double.longBitsToDouble(l2)) + d))) {
        }
        return d3;
    }

    public String toString() {
        return Double.toString(this.get());
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    @Override
    public long longValue() {
        return (long)this.get();
    }

    @Override
    public float floatValue() {
        return (float)this.get();
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(this.get());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.set(objectInputStream.readDouble());
    }
}

