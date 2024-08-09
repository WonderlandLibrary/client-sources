/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;

public interface BooleanBigListIterator
extends BooleanBidirectionalIterator,
BigListIterator<Boolean> {
    @Override
    default public void set(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Boolean bl) {
        this.set((boolean)bl);
    }

    @Override
    @Deprecated
    default public void add(Boolean bl) {
        this.add((boolean)bl);
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextBoolean();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousBoolean();
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
        this.add((Boolean)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Boolean)object);
    }
}

