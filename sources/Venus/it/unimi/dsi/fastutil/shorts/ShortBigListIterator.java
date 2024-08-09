/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;

public interface ShortBigListIterator
extends ShortBidirectionalIterator,
BigListIterator<Short> {
    @Override
    default public void set(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Short s) {
        this.set((short)s);
    }

    @Override
    @Deprecated
    default public void add(Short s) {
        this.add((short)s);
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextShort();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousShort();
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
        this.add((Short)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Short)object);
    }
}

