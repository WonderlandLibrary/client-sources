/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.LongAddable;
import com.google.common.cache.Striped64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@GwtCompatible(emulated=true)
final class LongAdder
extends Striped64
implements Serializable,
LongAddable {
    private static final long serialVersionUID = 7249069246863182397L;

    @Override
    final long fn(long l, long l2) {
        return l + l2;
    }

    @Override
    public void add(long l) {
        long l2;
        Striped64.Cell[] cellArray = this.cells;
        if (this.cells != null || !this.casBase(l2 = this.base, l2 + l)) {
            long l3;
            Striped64.Cell cell;
            int n;
            boolean bl = true;
            int[] nArray = (int[])threadHashCode.get();
            if (nArray == null || cellArray == null || (n = cellArray.length) < 1 || (cell = cellArray[n - 1 & nArray[0]]) == null || !(bl = cell.cas(l3 = cell.value, l3 + l))) {
                this.retryUpdate(l, nArray, bl);
            }
        }
    }

    @Override
    public void increment() {
        this.add(1L);
    }

    public void decrement() {
        this.add(-1L);
    }

    @Override
    public long sum() {
        long l = this.base;
        Striped64.Cell[] cellArray = this.cells;
        if (cellArray != null) {
            for (Striped64.Cell cell : cellArray) {
                if (cell == null) continue;
                l += cell.value;
            }
        }
        return l;
    }

    public void reset() {
        this.internalReset(0L);
    }

    public long sumThenReset() {
        long l = this.base;
        Striped64.Cell[] cellArray = this.cells;
        this.base = 0L;
        if (cellArray != null) {
            for (Striped64.Cell cell : cellArray) {
                if (cell == null) continue;
                l += cell.value;
                cell.value = 0L;
            }
        }
        return l;
    }

    public String toString() {
        return Long.toString(this.sum());
    }

    @Override
    public long longValue() {
        return this.sum();
    }

    @Override
    public int intValue() {
        return (int)this.sum();
    }

    @Override
    public float floatValue() {
        return this.sum();
    }

    @Override
    public double doubleValue() {
        return this.sum();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(this.sum());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.busy = 0;
        this.cells = null;
        this.base = objectInputStream.readLong();
    }
}

