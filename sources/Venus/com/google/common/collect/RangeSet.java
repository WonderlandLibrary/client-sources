/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Range;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public interface RangeSet<C extends Comparable> {
    public boolean contains(C var1);

    public Range<C> rangeContaining(C var1);

    public boolean intersects(Range<C> var1);

    public boolean encloses(Range<C> var1);

    public boolean enclosesAll(RangeSet<C> var1);

    default public boolean enclosesAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            if (this.encloses(range)) continue;
            return true;
        }
        return false;
    }

    public boolean isEmpty();

    public Range<C> span();

    public Set<Range<C>> asRanges();

    public Set<Range<C>> asDescendingSetOfRanges();

    public RangeSet<C> complement();

    public RangeSet<C> subRangeSet(Range<C> var1);

    public void add(Range<C> var1);

    public void remove(Range<C> var1);

    public void clear();

    public void addAll(RangeSet<C> var1);

    default public void addAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            this.add(range);
        }
    }

    public void removeAll(RangeSet<C> var1);

    default public void removeAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            this.remove(range);
        }
    }

    public boolean equals(@Nullable Object var1);

    public int hashCode();

    public String toString();
}

