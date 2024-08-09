/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortBidirectionalIterator
extends ShortIterator,
ObjectBidirectionalIterator<Short> {
    public short previousShort();

    @Override
    @Deprecated
    default public Short previous() {
        return this.previousShort();
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousShort();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return ShortIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

