/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectArrayIterator<E>
implements Iterator<E> {
    final E[] array;
    final int startIndex;
    final int endIndex;
    int index = 0;

    @SafeVarargs
    public ObjectArrayIterator(E ... EArray) {
        this(EArray, 0, EArray.length);
    }

    public ObjectArrayIterator(E[] EArray, int n) {
        this(EArray, n, EArray.length);
    }

    public ObjectArrayIterator(E[] EArray, int n, int n2) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index must not be less than zero");
        }
        if (n2 > EArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index must not be greater than the array length");
        }
        if (n > EArray.length) {
            throw new ArrayIndexOutOfBoundsException("Start index must not be greater than the array length");
        }
        if (n2 < n) {
            throw new IllegalArgumentException("End index must not be less than start index");
        }
        this.array = EArray;
        this.startIndex = n;
        this.endIndex = n2;
        this.index = n;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.endIndex;
    }

    @Override
    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.array[this.index++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported for an ObjectArrayIterator");
    }

    public E[] getArray() {
        return this.array;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void reset() {
        this.index = this.startIndex;
    }
}

