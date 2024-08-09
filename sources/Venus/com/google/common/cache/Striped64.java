/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import sun.misc.Unsafe;

@GwtIncompatible
abstract class Striped64
extends Number {
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal();
    static final Random rng = new Random();
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    volatile transient Cell[] cells;
    volatile transient long base;
    volatile transient int busy;
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;

    Striped64() {
    }

    final boolean casBase(long l, long l2) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, l, l2);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 0);
    }

    abstract long fn(long var1, long var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final void retryUpdate(long l, int[] nArray, boolean bl) {
        int n;
        int n2;
        if (nArray == null) {
            nArray = new int[1];
            threadHashCode.set(nArray);
            n2 = rng.nextInt();
            nArray[0] = n2 == 0 ? 1 : n2;
            n = nArray[0];
        } else {
            n = nArray[0];
        }
        n2 = 0;
        while (true) {
            long l2;
            int n3;
            Cell[] cellArray = this.cells;
            if (this.cells != null && (n3 = cellArray.length) > 0) {
                Cell[] cellArray2;
                Cell cell = cellArray[n3 - 1 & n];
                if (cell == null) {
                    if (this.busy == 0) {
                        cellArray2 = new Cell(l);
                        if (this.busy == 0 && this.casBusy()) {
                            boolean bl2 = false;
                            try {
                                int n4;
                                int n5;
                                Cell[] cellArray3 = this.cells;
                                if (this.cells != null && (n5 = cellArray3.length) > 0 && cellArray3[n4 = n5 - 1 & n] == null) {
                                    cellArray3[n4] = cellArray2;
                                    bl2 = true;
                                }
                            } finally {
                                this.busy = 0;
                            }
                            if (!bl2) continue;
                            return;
                        }
                    }
                    n2 = 0;
                } else if (!bl) {
                    bl = true;
                } else {
                    l2 = cell.value;
                    if (cell.cas(l2, this.fn(l2, l))) return;
                    if (n3 >= NCPU || this.cells != cellArray) {
                        n2 = 0;
                    } else if (n2 == 0) {
                        n2 = 1;
                    } else if (this.busy == 0 && this.casBusy()) {
                        try {
                            if (this.cells == cellArray) {
                                cellArray2 = new Cell[n3 << 1];
                                for (int i = 0; i < n3; ++i) {
                                    cellArray2[i] = cellArray[i];
                                }
                                this.cells = cellArray2;
                            }
                        } finally {
                            this.busy = 0;
                        }
                        n2 = 0;
                        continue;
                    }
                }
                n ^= n << 13;
                n ^= n >>> 17;
                n ^= n << 5;
                nArray[0] = n;
                continue;
            }
            if (this.busy == 0 && this.cells == cellArray && this.casBusy()) {
                boolean bl3 = false;
                try {
                    if (this.cells == cellArray) {
                        Cell[] cellArray4 = new Cell[2];
                        cellArray4[n & 1] = new Cell(l);
                        this.cells = cellArray4;
                        bl3 = true;
                    }
                } finally {
                    this.busy = 0;
                }
                if (!bl3) continue;
                return;
            }
            l2 = this.base;
            if (this.casBase(l2, this.fn(l2, l))) return;
        }
    }

    final void internalReset(long l) {
        Cell[] cellArray = this.cells;
        this.base = l;
        if (cellArray != null) {
            for (Cell cell : cellArray) {
                if (cell == null) continue;
                cell.value = l;
            }
        }
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch (SecurityException securityException) {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>(){

                    @Override
                    public Unsafe run() throws Exception {
                        Class<Unsafe> clazz = Unsafe.class;
                        for (Field field : clazz.getDeclaredFields()) {
                            field.setAccessible(false);
                            Object object = field.get(null);
                            if (!clazz.isInstance(object)) continue;
                            return (Unsafe)clazz.cast(object);
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }

                    @Override
                    public Object run() throws Exception {
                        return this.run();
                    }
                });
            } catch (PrivilegedActionException privilegedActionException) {
                throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
            }
        }
    }

    static Unsafe access$000() {
        return Striped64.getUnsafe();
    }

    static {
        try {
            UNSAFE = Striped64.getUnsafe();
            Class<Striped64> clazz = Striped64.class;
            baseOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("busy"));
        } catch (Exception exception) {
            throw new Error(exception);
        }
    }

    static final class Cell {
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long value;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        private static final Unsafe UNSAFE;
        private static final long valueOffset;

        Cell(long l) {
            this.value = l;
        }

        final boolean cas(long l, long l2) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, l, l2);
        }

        static {
            try {
                UNSAFE = Striped64.access$000();
                Class<Cell> clazz = Cell.class;
                valueOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("value"));
            } catch (Exception exception) {
                throw new Error(exception);
            }
        }
    }
}

