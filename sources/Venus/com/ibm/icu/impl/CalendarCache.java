/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public class CalendarCache {
    private static final int[] primes = new int[]{61, 127, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139};
    private int pIndex = 0;
    private int size = 0;
    private int arraySize = primes[this.pIndex];
    private int threshold = this.arraySize * 3 / 4;
    private long[] keys = new long[this.arraySize];
    private long[] values = new long[this.arraySize];
    public static long EMPTY = Long.MIN_VALUE;

    public CalendarCache() {
        this.makeArrays(this.arraySize);
    }

    private void makeArrays(int n) {
        this.keys = new long[n];
        this.values = new long[n];
        for (int i = 0; i < n; ++i) {
            this.values[i] = EMPTY;
        }
        this.arraySize = n;
        this.threshold = (int)((double)this.arraySize * 0.75);
        this.size = 0;
    }

    public synchronized long get(long l) {
        return this.values[this.findIndex(l)];
    }

    public synchronized void put(long l, long l2) {
        if (this.size >= this.threshold) {
            this.rehash();
        }
        int n = this.findIndex(l);
        this.keys[n] = l;
        this.values[n] = l2;
        ++this.size;
    }

    private final int findIndex(long l) {
        int n = this.hash(l);
        int n2 = 0;
        while (this.values[n] != EMPTY && this.keys[n] != l) {
            if (n2 == 0) {
                n2 = this.hash2(l);
            }
            n = (n + n2) % this.arraySize;
        }
        return n;
    }

    private void rehash() {
        int n = this.arraySize;
        long[] lArray = this.keys;
        long[] lArray2 = this.values;
        this.arraySize = this.pIndex < primes.length - 1 ? primes[++this.pIndex] : this.arraySize * 2 + 1;
        this.size = 0;
        this.makeArrays(this.arraySize);
        for (int i = 0; i < n; ++i) {
            if (lArray2[i] == EMPTY) continue;
            this.put(lArray[i], lArray2[i]);
        }
    }

    private final int hash(long l) {
        int n = (int)((l * 15821L + 1L) % (long)this.arraySize);
        if (n < 0) {
            n += this.arraySize;
        }
        return n;
    }

    private final int hash2(long l) {
        return this.arraySize - 2 - (int)(l % (long)(this.arraySize - 2));
    }
}

