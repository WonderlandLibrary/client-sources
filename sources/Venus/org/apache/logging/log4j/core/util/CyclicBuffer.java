/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.lang.reflect.Array;

public final class CyclicBuffer<T> {
    private final T[] ring;
    private int first = 0;
    private int last = 0;
    private int numElems = 0;
    private final Class<T> clazz;

    public CyclicBuffer(Class<T> clazz, int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException("The maxSize argument (" + n + ") is not a positive integer.");
        }
        this.ring = this.makeArray(clazz, n);
        this.clazz = clazz;
    }

    private T[] makeArray(Class<T> clazz, int n) {
        return (Object[])Array.newInstance(clazz, n);
    }

    public synchronized void add(T t) {
        this.ring[this.last] = t;
        if (++this.last == this.ring.length) {
            this.last = 0;
        }
        if (this.numElems < this.ring.length) {
            ++this.numElems;
        } else if (++this.first == this.ring.length) {
            this.first = 0;
        }
    }

    public synchronized T[] removeAll() {
        T[] TArray = this.makeArray(this.clazz, this.numElems);
        int n = 0;
        while (this.numElems > 0) {
            --this.numElems;
            TArray[n++] = this.ring[this.first];
            this.ring[this.first] = null;
            if (++this.first != this.ring.length) continue;
            this.first = 0;
        }
        return TArray;
    }

    public boolean isEmpty() {
        return 0 == this.numElems;
    }
}

