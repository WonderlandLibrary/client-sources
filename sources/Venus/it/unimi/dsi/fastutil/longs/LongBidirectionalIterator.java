/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongBidirectionalIterator
extends LongIterator,
ObjectBidirectionalIterator<Long> {
    public long previousLong();

    @Override
    @Deprecated
    default public Long previous() {
        return this.previousLong();
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousLong();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return LongIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

