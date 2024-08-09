/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Cut;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedLists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public class ImmutableRangeMap<K extends Comparable<?>, V>
implements RangeMap<K, V>,
Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
    private final transient ImmutableList<Range<K>> ranges;
    private final transient ImmutableList<V> values;
    private static final long serialVersionUID = 0L;

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return EMPTY;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V v) {
        return new ImmutableRangeMap<K, V>(ImmutableList.of(range), ImmutableList.of(v));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap)rangeMap;
        }
        Map<Range<K>, V> map = rangeMap.asMapOfRanges();
        ImmutableList.Builder builder = new ImmutableList.Builder(map.size());
        ImmutableList.Builder builder2 = new ImmutableList.Builder(map.size());
        for (Map.Entry<Range<K>, V> entry : map.entrySet()) {
            builder.add(entry.getKey());
            builder2.add(entry.getValue());
        }
        return new ImmutableRangeMap<K, V>(builder.build(), builder2.build());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder();
    }

    ImmutableRangeMap(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.ranges = immutableList;
        this.values = immutableList2;
    }

    @Override
    @Nullable
    public V get(K k) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(k), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (n == -1) {
            return null;
        }
        Range range = (Range)this.ranges.get(n);
        return range.contains(k) ? (V)this.values.get(n) : null;
    }

    @Override
    @Nullable
    public Map.Entry<Range<K>, V> getEntry(K k) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(k), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (n == -1) {
            return null;
        }
        Range range = (Range)this.ranges.get(n);
        return range.contains(k) ? Maps.immutableEntry(range, this.values.get(n)) : null;
    }

    @Override
    public Range<K> span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        Range range = (Range)this.ranges.get(0);
        Range range2 = (Range)this.ranges.get(this.ranges.size() - 1);
        return Range.create(range.lowerBound, range2.upperBound);
    }

    @Override
    @Deprecated
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        RegularImmutableSortedSet regularImmutableSortedSet = new RegularImmutableSortedSet(this.ranges, Range.RANGE_LEX_ORDERING);
        return new ImmutableSortedMap(regularImmutableSortedSet, this.values);
    }

    @Override
    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        RegularImmutableSortedSet<Range<K>> regularImmutableSortedSet = new RegularImmutableSortedSet<Range<K>>(this.ranges.reverse(), Range.RANGE_LEX_ORDERING.reverse());
        return new ImmutableSortedMap<Range<K>, V>(regularImmutableSortedSet, this.values.reverse());
    }

    @Override
    public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
        int n;
        if (Preconditions.checkNotNull(range).isEmpty()) {
            return ImmutableRangeMap.of();
        }
        if (this.ranges.isEmpty() || range.encloses(this.span())) {
            return this;
        }
        int n2 = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (n2 >= (n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER))) {
            return ImmutableRangeMap.of();
        }
        int n3 = n2;
        int n4 = n - n2;
        ImmutableList immutableList = new ImmutableList<Range<K>>(this, n4, n3, range){
            final int val$len;
            final int val$off;
            final Range val$range;
            final ImmutableRangeMap this$0;
            {
                this.this$0 = immutableRangeMap;
                this.val$len = n;
                this.val$off = n2;
                this.val$range = range;
            }

            @Override
            public int size() {
                return this.val$len;
            }

            @Override
            public Range<K> get(int n) {
                Preconditions.checkElementIndex(n, this.val$len);
                if (n == 0 || n == this.val$len - 1) {
                    return ((Range)ImmutableRangeMap.access$000(this.this$0).get(n + this.val$off)).intersection(this.val$range);
                }
                return (Range)ImmutableRangeMap.access$000(this.this$0).get(n + this.val$off);
            }

            @Override
            boolean isPartialView() {
                return false;
            }

            @Override
            public Object get(int n) {
                return this.get(n);
            }
        };
        ImmutableRangeMap immutableRangeMap = this;
        return new ImmutableRangeMap<K, V>(this, immutableList, (ImmutableList)this.values.subList(n2, n), range, immutableRangeMap){
            final Range val$range;
            final ImmutableRangeMap val$outer;
            final ImmutableRangeMap this$0;
            {
                this.this$0 = immutableRangeMap;
                this.val$range = range;
                this.val$outer = immutableRangeMap2;
                super(immutableList, immutableList2);
            }

            @Override
            public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
                if (this.val$range.isConnected(range)) {
                    return this.val$outer.subRangeMap(range.intersection(this.val$range));
                }
                return ImmutableRangeMap.of();
            }

            @Override
            public RangeMap subRangeMap(Range range) {
                return this.subRangeMap(range);
            }

            @Override
            public Map asDescendingMapOfRanges() {
                return super.asDescendingMapOfRanges();
            }

            @Override
            public Map asMapOfRanges() {
                return super.asMapOfRanges();
            }
        };
    }

    @Override
    public int hashCode() {
        return ((ImmutableMap)this.asMapOfRanges()).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof RangeMap) {
            RangeMap rangeMap = (RangeMap)object;
            return ((ImmutableMap)this.asMapOfRanges()).equals(rangeMap.asMapOfRanges());
        }
        return true;
    }

    @Override
    public String toString() {
        return ((ImmutableMap)this.asMapOfRanges()).toString();
    }

    Object writeReplace() {
        return new SerializedForm(this.asMapOfRanges());
    }

    @Override
    public RangeMap subRangeMap(Range range) {
        return this.subRangeMap(range);
    }

    @Override
    public Map asDescendingMapOfRanges() {
        return this.asDescendingMapOfRanges();
    }

    @Override
    public Map asMapOfRanges() {
        return this.asMapOfRanges();
    }

    static ImmutableList access$000(ImmutableRangeMap immutableRangeMap) {
        return immutableRangeMap.ranges;
    }

    private static class SerializedForm<K extends Comparable<?>, V>
    implements Serializable {
        private final ImmutableMap<Range<K>, V> mapOfRanges;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableMap<Range<K>, V> immutableMap) {
            this.mapOfRanges = immutableMap;
        }

        Object readResolve() {
            if (this.mapOfRanges.isEmpty()) {
                return ImmutableRangeMap.of();
            }
            return this.createRangeMap();
        }

        Object createRangeMap() {
            Builder builder = new Builder();
            for (Map.Entry entry : this.mapOfRanges.entrySet()) {
                builder.put((Range)entry.getKey(), entry.getValue());
            }
            return builder.build();
        }
    }

    public static final class Builder<K extends Comparable<?>, V> {
        private final List<Map.Entry<Range<K>, V>> entries = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(v);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
            this.entries.add(Maps.immutableEntry(range, v));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Map.Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableRangeMap<K, V> build() {
            Collections.sort(this.entries, Range.RANGE_LEX_ORDERING.onKeys());
            ImmutableList.Builder builder = new ImmutableList.Builder(this.entries.size());
            ImmutableList.Builder builder2 = new ImmutableList.Builder(this.entries.size());
            for (int i = 0; i < this.entries.size(); ++i) {
                Range<K> range;
                Range<K> range2 = this.entries.get(i).getKey();
                if (i > 0 && range2.isConnected(range = this.entries.get(i - 1).getKey()) && !range2.intersection(range).isEmpty()) {
                    throw new IllegalArgumentException("Overlapping ranges: range " + range + " overlaps with entry " + range2);
                }
                builder.add(range2);
                builder2.add(this.entries.get(i).getValue());
            }
            return new ImmutableRangeMap(builder.build(), builder2.build());
        }
    }
}

