/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.group;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class CombinedIterator<E>
implements Iterator<E> {
    private final Iterator<E> i1;
    private final Iterator<E> i2;
    private Iterator<E> currentIterator;

    CombinedIterator(Iterator<E> iterator2, Iterator<E> iterator3) {
        if (iterator2 == null) {
            throw new NullPointerException("i1");
        }
        if (iterator3 == null) {
            throw new NullPointerException("i2");
        }
        this.i1 = iterator2;
        this.i2 = iterator3;
        this.currentIterator = iterator2;
    }

    @Override
    public boolean hasNext() {
        while (true) {
            if (this.currentIterator.hasNext()) {
                return false;
            }
            if (this.currentIterator != this.i1) break;
            this.currentIterator = this.i2;
        }
        return true;
    }

    @Override
    public E next() {
        while (true) {
            try {
                return this.currentIterator.next();
            } catch (NoSuchElementException noSuchElementException) {
                if (this.currentIterator == this.i1) {
                    this.currentIterator = this.i2;
                    continue;
                }
                throw noSuchElementException;
            }
            break;
        }
    }

    @Override
    public void remove() {
        this.currentIterator.remove();
    }
}

