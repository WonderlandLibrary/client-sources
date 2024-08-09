/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;

public interface IntBigListIterator
extends IntBidirectionalIterator,
BigListIterator<Integer> {
    @Override
    default public void set(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Integer n) {
        this.set((int)n);
    }

    @Override
    @Deprecated
    default public void add(Integer n) {
        this.add((int)n);
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextInt();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousInt();
        }
        return l - l2 - 1L;
    }

    @Override
    default public int skip(int n) {
        return SafeMath.safeLongToInt(this.skip((long)n));
    }

    @Override
    @Deprecated
    default public void add(Object object) {
        this.add((Integer)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Integer)object);
    }
}

