/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import javax.annotation.Nullable;

@GwtIncompatible
abstract class AbstractRangeSet<C extends Comparable>
implements RangeSet<C> {
    AbstractRangeSet() {
    }

    @Override
    public boolean contains(C c) {
        return this.rangeContaining(c) != null;
    }

    @Override
    public abstract Range<C> rangeContaining(C var1);

    @Override
    public boolean isEmpty() {
        return this.asRanges().isEmpty();
    }

    @Override
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.remove(Range.all());
    }

    @Override
    public boolean enclosesAll(RangeSet<C> rangeSet) {
        return this.enclosesAll(rangeSet.asRanges());
    }

    @Override
    public void addAll(RangeSet<C> rangeSet) {
        this.addAll(rangeSet.asRanges());
    }

    @Override
    public void removeAll(RangeSet<C> rangeSet) {
        this.removeAll(rangeSet.asRanges());
    }

    @Override
    public boolean intersects(Range<C> range) {
        return !this.subRangeSet(range).isEmpty();
    }

    @Override
    public abstract boolean encloses(Range<C> var1);

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof RangeSet) {
            RangeSet rangeSet = (RangeSet)object;
            return this.asRanges().equals(rangeSet.asRanges());
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return this.asRanges().hashCode();
    }

    @Override
    public final String toString() {
        return this.asRanges().toString();
    }
}

