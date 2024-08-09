/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.Cut;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class TreeRangeMap<K extends Comparable, V>
implements RangeMap<K, V> {
    private final NavigableMap<Cut<K>, RangeMapEntry<K, V>> entriesByLowerBound = Maps.newTreeMap();
    private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap(){

        @Nullable
        public Object get(Comparable comparable) {
            return null;
        }

        @Nullable
        public Map.Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public void put(Range range, Object object) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        @Override
        public void clear() {
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<K, V>();
    }

    private TreeRangeMap() {
    }

    @Override
    @Nullable
    public V get(K k) {
        Map.Entry<Range<K>, V> entry = this.getEntry(k);
        return entry == null ? null : (V)entry.getValue();
    }

    @Override
    @Nullable
    public Map.Entry<Range<K>, V> getEntry(K k) {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry = this.entriesByLowerBound.floorEntry(Cut.belowValue(k));
        if (entry != null && entry.getValue().contains(k)) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public void put(Range<K> range, V v) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(v);
            this.remove(range);
            this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry<K, V>(range, v));
        }
    }

    @Override
    public void putAll(RangeMap<K, V> rangeMap) {
        for (Map.Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.entriesByLowerBound.clear();
    }

    @Override
    public Range<K> span() {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry = this.entriesByLowerBound.firstEntry();
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry2 = this.entriesByLowerBound.lastEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return Range.create(((Range)entry.getValue().getKey()).lowerBound, ((Range)entry2.getValue().getKey()).upperBound);
    }

    private void putRangeMapEntry(Cut<K> cut, Cut<K> cut2, V v) {
        this.entriesByLowerBound.put(cut, new RangeMapEntry<K, V>(cut, cut2, v));
    }

    @Override
    public void remove(Range<K> range) {
        RangeMapEntry rangeMapEntry;
        Map.Entry<Range<Object>, Object> entry;
        if (range.isEmpty()) {
            return;
        }
        Map.Entry entry2 = this.entriesByLowerBound.lowerEntry(range.lowerBound);
        if (entry2 != null && ((RangeMapEntry)(entry = entry2.getValue())).getUpperBound().compareTo(range.lowerBound) > 0) {
            if (((RangeMapEntry)entry).getUpperBound().compareTo(range.upperBound) > 0) {
                this.putRangeMapEntry(range.upperBound, ((RangeMapEntry)entry).getUpperBound(), entry2.getValue().getValue());
            }
            this.putRangeMapEntry(((RangeMapEntry)entry).getLowerBound(), range.lowerBound, entry2.getValue().getValue());
        }
        if ((entry = this.entriesByLowerBound.lowerEntry(range.upperBound)) != null && (rangeMapEntry = (RangeMapEntry)entry.getValue()).getUpperBound().compareTo(range.upperBound) > 0) {
            this.putRangeMapEntry(range.upperBound, rangeMapEntry.getUpperBound(), ((RangeMapEntry)entry.getValue()).getValue());
        }
        this.entriesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }

    @Override
    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this, this.entriesByLowerBound.values());
    }

    @Override
    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this, this.entriesByLowerBound.descendingMap().values());
    }

    @Override
    public RangeMap<K, V> subRangeMap(Range<K> range) {
        if (range.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(this, range);
    }

    private RangeMap<K, V> emptySubRangeMap() {
        return EMPTY_SUB_RANGE_MAP;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof RangeMap) {
            RangeMap rangeMap = (RangeMap)object;
            return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.asMapOfRanges().hashCode();
    }

    @Override
    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }

    static NavigableMap access$000(TreeRangeMap treeRangeMap) {
        return treeRangeMap.entriesByLowerBound;
    }

    static RangeMap access$100(TreeRangeMap treeRangeMap) {
        return treeRangeMap.emptySubRangeMap();
    }

    private class SubRangeMap
    implements RangeMap<K, V> {
        private final Range<K> subRange;
        final TreeRangeMap this$0;

        SubRangeMap(TreeRangeMap treeRangeMap, Range<K> range) {
            this.this$0 = treeRangeMap;
            this.subRange = range;
        }

        @Override
        @Nullable
        public V get(K k) {
            return this.subRange.contains(k) ? (Object)this.this$0.get(k) : null;
        }

        @Override
        @Nullable
        public Map.Entry<Range<K>, V> getEntry(K k) {
            Map.Entry entry;
            if (this.subRange.contains(k) && (entry = this.this$0.getEntry(k)) != null) {
                return Maps.immutableEntry(entry.getKey().intersection(this.subRange), entry.getValue());
            }
            return null;
        }

        @Override
        public Range<K> span() {
            Cut cut;
            Map.Entry entry = TreeRangeMap.access$000(this.this$0).floorEntry(this.subRange.lowerBound);
            if (entry != null && ((RangeMapEntry)entry.getValue()).getUpperBound().compareTo(this.subRange.lowerBound) > 0) {
                cut = this.subRange.lowerBound;
            } else {
                cut = TreeRangeMap.access$000(this.this$0).ceilingKey(this.subRange.lowerBound);
                if (cut == null || cut.compareTo(this.subRange.upperBound) >= 0) {
                    throw new NoSuchElementException();
                }
            }
            Map.Entry entry2 = TreeRangeMap.access$000(this.this$0).lowerEntry(this.subRange.upperBound);
            if (entry2 == null) {
                throw new NoSuchElementException();
            }
            Cut<Object> cut2 = ((RangeMapEntry)entry2.getValue()).getUpperBound().compareTo(this.subRange.upperBound) >= 0 ? this.subRange.upperBound : ((RangeMapEntry)entry2.getValue()).getUpperBound();
            return Range.create(cut, cut2);
        }

        @Override
        public void put(Range<K> range, V v) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
            this.this$0.put(range, v);
        }

        @Override
        public void putAll(RangeMap<K, V> rangeMap) {
            if (rangeMap.asMapOfRanges().isEmpty()) {
                return;
            }
            Range range = rangeMap.span();
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", range, this.subRange);
            this.this$0.putAll(rangeMap);
        }

        @Override
        public void clear() {
            this.this$0.remove(this.subRange);
        }

        @Override
        public void remove(Range<K> range) {
            if (range.isConnected(this.subRange)) {
                this.this$0.remove(range.intersection(this.subRange));
            }
        }

        @Override
        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (!range.isConnected(this.subRange)) {
                return TreeRangeMap.access$100(this.this$0);
            }
            return this.this$0.subRangeMap(range.intersection(this.subRange));
        }

        @Override
        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap(this);
        }

        @Override
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new SubRangeMapAsMap(this){
                final SubRangeMap this$1;
                {
                    this.this$1 = subRangeMap;
                    super(subRangeMap);
                }

                @Override
                Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                    if (SubRangeMap.access$200(this.this$1).isEmpty()) {
                        return Iterators.emptyIterator();
                    }
                    Iterator iterator2 = TreeRangeMap.access$000(this.this$1.this$0).headMap(SubRangeMap.access$200((SubRangeMap)this.this$1).upperBound, false).descendingMap().values().iterator();
                    return new AbstractIterator<Map.Entry<Range<K>, V>>(this, iterator2){
                        final Iterator val$backingItr;
                        final 1 this$2;
                        {
                            this.this$2 = var1_1;
                            this.val$backingItr = iterator2;
                        }

                        @Override
                        protected Map.Entry<Range<K>, V> computeNext() {
                            if (this.val$backingItr.hasNext()) {
                                RangeMapEntry rangeMapEntry = (RangeMapEntry)this.val$backingItr.next();
                                if (rangeMapEntry.getUpperBound().compareTo(SubRangeMap.access$200((SubRangeMap)this.this$2.this$1).lowerBound) <= 0) {
                                    return (Map.Entry)this.endOfData();
                                }
                                return Maps.immutableEntry(((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.access$200(this.this$2.this$1)), rangeMapEntry.getValue());
                            }
                            return (Map.Entry)this.endOfData();
                        }

                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }
            };
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof RangeMap) {
                RangeMap rangeMap = (RangeMap)object;
                return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.asMapOfRanges().hashCode();
        }

        @Override
        public String toString() {
            return this.asMapOfRanges().toString();
        }

        static Range access$200(SubRangeMap subRangeMap) {
            return subRangeMap.subRange;
        }

        class SubRangeMapAsMap
        extends AbstractMap<Range<K>, V> {
            final SubRangeMap this$1;

            SubRangeMapAsMap(SubRangeMap subRangeMap) {
                this.this$1 = subRangeMap;
            }

            @Override
            public boolean containsKey(Object object) {
                return this.get(object) != null;
            }

            @Override
            public V get(Object object) {
                try {
                    if (object instanceof Range) {
                        Range range = (Range)object;
                        if (!SubRangeMap.access$200(this.this$1).encloses(range) || range.isEmpty()) {
                            return null;
                        }
                        RangeMapEntry rangeMapEntry = null;
                        if (range.lowerBound.compareTo(SubRangeMap.access$200((SubRangeMap)this.this$1).lowerBound) == 0) {
                            Map.Entry entry = TreeRangeMap.access$000(this.this$1.this$0).floorEntry(range.lowerBound);
                            if (entry != null) {
                                rangeMapEntry = (RangeMapEntry)entry.getValue();
                            }
                        } else {
                            rangeMapEntry = (RangeMapEntry)TreeRangeMap.access$000(this.this$1.this$0).get(range.lowerBound);
                        }
                        if (rangeMapEntry != null && ((Range)rangeMapEntry.getKey()).isConnected(SubRangeMap.access$200(this.this$1)) && ((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.access$200(this.this$1)).equals(range)) {
                            return rangeMapEntry.getValue();
                        }
                    }
                } catch (ClassCastException classCastException) {
                    return null;
                }
                return null;
            }

            @Override
            public V remove(Object object) {
                Object v = this.get(object);
                if (v != null) {
                    Range range = (Range)object;
                    this.this$1.this$0.remove(range);
                    return v;
                }
                return null;
            }

            @Override
            public void clear() {
                this.this$1.clear();
            }

            private boolean removeEntryIf(Predicate<? super Map.Entry<Range<K>, V>> predicate) {
                ArrayList arrayList = Lists.newArrayList();
                for (Map.Entry entry : this.entrySet()) {
                    if (!predicate.apply(entry)) continue;
                    arrayList.add(entry.getKey());
                }
                for (Range range : arrayList) {
                    this.this$1.this$0.remove(range);
                }
                return !arrayList.isEmpty();
            }

            @Override
            public Set<Range<K>> keySet() {
                return new Maps.KeySet<Range<K>, V>(this, this){
                    final SubRangeMapAsMap this$2;
                    {
                        this.this$2 = subRangeMapAsMap;
                        super(map);
                    }

                    @Override
                    public boolean remove(@Nullable Object object) {
                        return this.this$2.remove(object) != null;
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.access$300(this.this$2, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.keyFunction()));
                    }
                };
            }

            @Override
            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.EntrySet<Range<K>, V>(this){
                    final SubRangeMapAsMap this$2;
                    {
                        this.this$2 = subRangeMapAsMap;
                    }

                    @Override
                    Map<Range<K>, V> map() {
                        return this.this$2;
                    }

                    @Override
                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return this.this$2.entryIterator();
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.access$300(this.this$2, Predicates.not(Predicates.in(collection)));
                    }

                    @Override
                    public int size() {
                        return Iterators.size(this.iterator());
                    }

                    @Override
                    public boolean isEmpty() {
                        return !this.iterator().hasNext();
                    }
                };
            }

            Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                if (SubRangeMap.access$200(this.this$1).isEmpty()) {
                    return Iterators.emptyIterator();
                }
                Cut cut = MoreObjects.firstNonNull(TreeRangeMap.access$000(this.this$1.this$0).floorKey(SubRangeMap.access$200((SubRangeMap)this.this$1).lowerBound), SubRangeMap.access$200((SubRangeMap)this.this$1).lowerBound);
                Iterator iterator2 = TreeRangeMap.access$000(this.this$1.this$0).tailMap(cut, true).values().iterator();
                return new AbstractIterator<Map.Entry<Range<K>, V>>(this, iterator2){
                    final Iterator val$backingItr;
                    final SubRangeMapAsMap this$2;
                    {
                        this.this$2 = subRangeMapAsMap;
                        this.val$backingItr = iterator2;
                    }

                    @Override
                    protected Map.Entry<Range<K>, V> computeNext() {
                        while (this.val$backingItr.hasNext()) {
                            RangeMapEntry rangeMapEntry = (RangeMapEntry)this.val$backingItr.next();
                            if (rangeMapEntry.getLowerBound().compareTo(SubRangeMap.access$200((SubRangeMap)this.this$2.this$1).upperBound) >= 0) {
                                return (Map.Entry)this.endOfData();
                            }
                            if (rangeMapEntry.getUpperBound().compareTo(SubRangeMap.access$200((SubRangeMap)this.this$2.this$1).lowerBound) <= 0) continue;
                            return Maps.immutableEntry(((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.access$200(this.this$2.this$1)), rangeMapEntry.getValue());
                        }
                        return (Map.Entry)this.endOfData();
                    }

                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }

            @Override
            public Collection<V> values() {
                return new Maps.Values<Range<K>, V>(this, this){
                    final SubRangeMapAsMap this$2;
                    {
                        this.this$2 = subRangeMapAsMap;
                        super(map);
                    }

                    @Override
                    public boolean removeAll(Collection<?> collection) {
                        return SubRangeMapAsMap.access$300(this.this$2, Predicates.compose(Predicates.in(collection), Maps.valueFunction()));
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.access$300(this.this$2, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.valueFunction()));
                    }
                };
            }

            static boolean access$300(SubRangeMapAsMap subRangeMapAsMap, Predicate predicate) {
                return subRangeMapAsMap.removeEntryIf(predicate);
            }
        }
    }

    private final class AsMapOfRanges
    extends Maps.IteratorBasedAbstractMap<Range<K>, V> {
        final Iterable<Map.Entry<Range<K>, V>> entryIterable;
        final TreeRangeMap this$0;

        AsMapOfRanges(TreeRangeMap treeRangeMap, Iterable<RangeMapEntry<K, V>> iterable) {
            this.this$0 = treeRangeMap;
            this.entryIterable = iterable;
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.get(object) != null;
        }

        @Override
        public V get(@Nullable Object object) {
            if (object instanceof Range) {
                Range range = (Range)object;
                RangeMapEntry rangeMapEntry = (RangeMapEntry)TreeRangeMap.access$000(this.this$0).get(range.lowerBound);
                if (rangeMapEntry != null && ((Range)rangeMapEntry.getKey()).equals(range)) {
                    return rangeMapEntry.getValue();
                }
            }
            return null;
        }

        @Override
        public int size() {
            return TreeRangeMap.access$000(this.this$0).size();
        }

        @Override
        Iterator<Map.Entry<Range<K>, V>> entryIterator() {
            return this.entryIterable.iterator();
        }
    }

    private static final class RangeMapEntry<K extends Comparable, V>
    extends AbstractMapEntry<Range<K>, V> {
        private final Range<K> range;
        private final V value;

        RangeMapEntry(Cut<K> cut, Cut<K> cut2, V v) {
            this(Range.create(cut, cut2), v);
        }

        RangeMapEntry(Range<K> range, V v) {
            this.range = range;
            this.value = v;
        }

        @Override
        public Range<K> getKey() {
            return this.range;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        public boolean contains(K k) {
            return this.range.contains(k);
        }

        Cut<K> getLowerBound() {
            return this.range.lowerBound;
        }

        Cut<K> getUpperBound() {
            return this.range.upperBound;
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }
    }
}

