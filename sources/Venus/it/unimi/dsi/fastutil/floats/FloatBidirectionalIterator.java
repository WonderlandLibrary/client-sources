/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatBidirectionalIterator
extends FloatIterator,
ObjectBidirectionalIterator<Float> {
    public float previousFloat();

    @Override
    @Deprecated
    default public Float previous() {
        return Float.valueOf(this.previousFloat());
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousFloat();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return FloatIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

