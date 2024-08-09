/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntBidirectionalIterator
extends IntIterator,
ObjectBidirectionalIterator<Integer> {
    public int previousInt();

    @Override
    @Deprecated
    default public Integer previous() {
        return this.previousInt();
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousInt();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return IntIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

