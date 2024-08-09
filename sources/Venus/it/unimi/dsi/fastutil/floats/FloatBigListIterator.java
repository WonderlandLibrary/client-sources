/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;

public interface FloatBigListIterator
extends FloatBidirectionalIterator,
BigListIterator<Float> {
    @Override
    default public void set(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Float f) {
        this.set(f.floatValue());
    }

    @Override
    @Deprecated
    default public void add(Float f) {
        this.add(f.floatValue());
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextFloat();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousFloat();
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
        this.add((Float)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Float)object);
    }
}

