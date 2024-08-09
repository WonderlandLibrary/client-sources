/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;

public interface DoubleBigListIterator
extends DoubleBidirectionalIterator,
BigListIterator<Double> {
    @Override
    default public void set(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Double d) {
        this.set((double)d);
    }

    @Override
    @Deprecated
    default public void add(Double d) {
        this.add((double)d);
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextDouble();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousDouble();
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
        this.add((Double)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Double)object);
    }
}

