/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractNavigableMap;
import com.google.common.collect.AbstractRangeSet;
import com.google.common.collect.BoundType;
import com.google.common.collect.Cut;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public class TreeRangeSet<C extends Comparable<?>>
extends AbstractRangeSet<C>
implements Serializable {
    @VisibleForTesting
    final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
    private transient Set<Range<C>> asRanges;
    private transient Set<Range<C>> asDescendingSetOfRanges;
    private transient RangeSet<C> complement;

    public static <C extends Comparable<?>> TreeRangeSet<C> create() {
        return new TreeRangeSet<C>(new TreeMap<Cut<C>, Range<C>>());
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.addAll((RangeSet)rangeSet);
        return treeRangeSet;
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(Iterable<Range<C>> iterable) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.addAll(iterable);
        return treeRangeSet;
    }

    private TreeRangeSet(NavigableMap<Cut<C>, Range<C>> navigableMap) {
        this.rangesByLowerBound = navigableMap;
    }

    @Override
    public Set<Range<C>> asRanges() {
        AsRanges asRanges = this.asRanges;
        return asRanges == null ? (this.asRanges = new AsRanges(this, this.rangesByLowerBound.values())) : asRanges;
    }

    @Override
    public Set<Range<C>> asDescendingSetOfRanges() {
        AsRanges asRanges = this.asDescendingSetOfRanges;
        return asRanges == null ? (this.asDescendingSetOfRanges = new AsRanges(this, this.rangesByLowerBound.descendingMap().values())) : asRanges;
    }

    @Override
    @Nullable
    public Range<C> rangeContaining(C c) {
        Preconditions.checkNotNull(c);
        Map.Entry<Cut<C>, Range<C>> entry = this.rangesByLowerBound.floorEntry(Cut.belowValue(c));
        if (entry != null && entry.getValue().contains(c)) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public boolean intersects(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.ceilingEntry(range.lowerBound);
        if (entry != null && entry.getValue().isConnected(range) && !entry.getValue().intersection(range).isEmpty()) {
            return false;
        }
        Map.Entry entry2 = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        return entry2 != null && entry2.getValue().isConnected(range) && !entry2.getValue().intersection(range).isEmpty();
    }

    @Override
    public boolean encloses(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        return entry != null && entry.getValue().encloses(range);
    }

    @Nullable
    private Range<C> rangeEnclosing(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        return entry != null && entry.getValue().encloses(range) ? entry.getValue() : null;
    }

    @Override
    public Range<C> span() {
        Map.Entry<Cut<C>, Range<C>> entry = this.rangesByLowerBound.firstEntry();
        Map.Entry<Cut<C>, Range<C>> entry2 = this.rangesByLowerBound.lastEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return Range.create(entry.getValue().lowerBound, entry2.getValue().upperBound);
    }

    @Override
    public void add(Range<C> range) {
        Object object;
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return;
        }
        Cut cut = range.lowerBound;
        Cut cut2 = range.upperBound;
        Map.Entry entry = this.rangesByLowerBound.lowerEntry(cut);
        if (entry != null) {
            object = entry.getValue();
            if (((Range)object).upperBound.compareTo(cut) >= 0) {
                if (((Range)object).upperBound.compareTo(cut2) >= 0) {
                    cut2 = ((Range)object).upperBound;
                }
                cut = ((Range)object).lowerBound;
            }
        }
        if ((object = this.rangesByLowerBound.floorEntry(cut2)) != null) {
            Range range2 = (Range)object.getValue();
            if (range2.upperBound.compareTo(cut2) >= 0) {
                cut2 = range2.upperBound;
            }
        }
        this.rangesByLowerBound.subMap(cut, cut2).clear();
        this.replaceRangeWithSameLowerBound(Range.create(cut, cut2));
    }

    @Override
    public void remove(Range<C> range) {
        Object object;
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return;
        }
        Map.Entry entry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        if (entry != null) {
            object = entry.getValue();
            if (((Range)object).upperBound.compareTo(range.lowerBound) >= 0) {
                if (range.hasUpperBound() && ((Range)object).upperBound.compareTo(range.upperBound) >= 0) {
                    this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, ((Range)object).upperBound));
                }
                this.replaceRangeWithSameLowerBound(Range.create(((Range)object).lowerBound, range.lowerBound));
            }
        }
        if ((object = this.rangesByLowerBound.floorEntry(range.upperBound)) != null) {
            Range range2 = (Range)object.getValue();
            if (range.hasUpperBound() && range2.upperBound.compareTo(range.upperBound) >= 0) {
                this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, range2.upperBound));
            }
        }
        this.rangesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }

    private void replaceRangeWithSameLowerBound(Range<C> range) {
        if (range.isEmpty()) {
            this.rangesByLowerBound.remove(range.lowerBound);
        } else {
            this.rangesByLowerBound.put(range.lowerBound, range);
        }
    }

    @Override
    public RangeSet<C> complement() {
        Complement complement = this.complement;
        return complement == null ? (this.complement = new Complement(this)) : complement;
    }

    @Override
    public RangeSet<C> subRangeSet(Range<C> range) {
        return range.equals(Range.all()) ? this : new SubRangeSet(this, range);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public void removeAll(RangeSet rangeSet) {
        super.removeAll(rangeSet);
    }

    @Override
    public void addAll(RangeSet rangeSet) {
        super.addAll(rangeSet);
    }

    @Override
    public boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    TreeRangeSet(NavigableMap navigableMap, 1 var2_2) {
        this(navigableMap);
    }

    static Range access$600(TreeRangeSet treeRangeSet, Range range) {
        return treeRangeSet.rangeEnclosing(range);
    }

    private final class SubRangeSet
    extends TreeRangeSet<C> {
        private final Range<C> restriction;
        final TreeRangeSet this$0;

        SubRangeSet(TreeRangeSet treeRangeSet, Range<C> range) {
            this.this$0 = treeRangeSet;
            super(new SubRangeSetRangesByLowerBound(Range.all(), range, treeRangeSet.rangesByLowerBound, null), null);
            this.restriction = range;
        }

        @Override
        public boolean encloses(Range<C> range) {
            if (!this.restriction.isEmpty() && this.restriction.encloses(range)) {
                Range range2 = TreeRangeSet.access$600(this.this$0, range);
                return range2 != null && !range2.intersection(this.restriction).isEmpty();
            }
            return true;
        }

        @Override
        @Nullable
        public Range<C> rangeContaining(C c) {
            if (!this.restriction.contains(c)) {
                return null;
            }
            Range range = this.this$0.rangeContaining(c);
            return range == null ? null : range.intersection(this.restriction);
        }

        @Override
        public void add(Range<C> range) {
            Preconditions.checkArgument(this.restriction.encloses(range), "Cannot add range %s to subRangeSet(%s)", range, this.restriction);
            super.add(range);
        }

        @Override
        public void remove(Range<C> range) {
            if (range.isConnected(this.restriction)) {
                this.this$0.remove(range.intersection(this.restriction));
            }
        }

        @Override
        public boolean contains(C c) {
            return this.restriction.contains(c) && this.this$0.contains((Comparable)c);
        }

        @Override
        public void clear() {
            this.this$0.remove(this.restriction);
        }

        @Override
        public RangeSet<C> subRangeSet(Range<C> range) {
            if (range.encloses(this.restriction)) {
                return this;
            }
            if (range.isConnected(this.restriction)) {
                return new SubRangeSet(this, this.restriction.intersection(range));
            }
            return ImmutableRangeSet.of();
        }
    }

    private static final class SubRangeSetRangesByLowerBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final Range<Cut<C>> lowerBoundWindow;
        private final Range<C> restriction;
        private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
        private final NavigableMap<Cut<C>, Range<C>> rangesByUpperBound;

        private SubRangeSetRangesByLowerBound(Range<Cut<C>> range, Range<C> range2, NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.lowerBoundWindow = Preconditions.checkNotNull(range);
            this.restriction = Preconditions.checkNotNull(range2);
            this.rangesByLowerBound = Preconditions.checkNotNull(navigableMap);
            this.rangesByUpperBound = new RangesByUpperBound<C>(navigableMap);
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (!range.isConnected(this.lowerBoundWindow)) {
                return ImmutableSortedMap.of();
            }
            return new SubRangeSetRangesByLowerBound<C>(this.lowerBoundWindow.intersection(range), this.restriction, this.rangesByLowerBound);
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.get(object) != null;
        }

        @Override
        @Nullable
        public Range<C> get(@Nullable Object object) {
            if (object instanceof Cut) {
                try {
                    Cut cut = (Cut)object;
                    if (!this.lowerBoundWindow.contains(cut) || cut.compareTo(this.restriction.lowerBound) < 0 || cut.compareTo(this.restriction.upperBound) >= 0) {
                        return null;
                    }
                    if (cut.equals(this.restriction.lowerBound)) {
                        Range<C> range = Maps.valueOrNull(this.rangesByLowerBound.floorEntry(cut));
                        if (range != null && range.upperBound.compareTo(this.restriction.lowerBound) > 0) {
                            return range.intersection(this.restriction);
                        }
                    } else {
                        Range range = (Range)this.rangesByLowerBound.get(cut);
                        if (range != null) {
                            return range.intersection(this.restriction);
                        }
                    }
                } catch (ClassCastException classCastException) {
                    return null;
                }
            }
            return null;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            if (this.lowerBoundWindow.upperBound.isLessThan(this.restriction.lowerBound)) {
                return Iterators.emptyIterator();
            }
            Iterator iterator2 = this.lowerBoundWindow.lowerBound.isLessThan(this.restriction.lowerBound) ? this.rangesByUpperBound.tailMap(this.restriction.lowerBound, false).values().iterator() : this.rangesByLowerBound.tailMap((Cut<C>)this.lowerBoundWindow.lowerBound.endpoint(), this.lowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values().iterator();
            Cut cut = Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, iterator2, cut){
                final Iterator val$completeRangeItr;
                final Cut val$upperBoundOnLowerBounds;
                final SubRangeSetRangesByLowerBound this$0;
                {
                    this.this$0 = subRangeSetRangesByLowerBound;
                    this.val$completeRangeItr = iterator2;
                    this.val$upperBoundOnLowerBounds = cut;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (!this.val$completeRangeItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Range range = (Range)this.val$completeRangeItr.next();
                    if (this.val$upperBoundOnLowerBounds.isLessThan(range.lowerBound)) {
                        return (Map.Entry)this.endOfData();
                    }
                    range = range.intersection(SubRangeSetRangesByLowerBound.access$300(this.this$0));
                    return Maps.immutableEntry(range.lowerBound, range);
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            Cut cut = Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            Iterator iterator2 = this.rangesByLowerBound.headMap(cut.endpoint(), cut.typeAsUpperBound() == BoundType.CLOSED).descendingMap().values().iterator();
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, iterator2){
                final Iterator val$completeRangeItr;
                final SubRangeSetRangesByLowerBound this$0;
                {
                    this.this$0 = subRangeSetRangesByLowerBound;
                    this.val$completeRangeItr = iterator2;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (!this.val$completeRangeItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Range range = (Range)this.val$completeRangeItr.next();
                    if (SubRangeSetRangesByLowerBound.access$300((SubRangeSetRangesByLowerBound)this.this$0).lowerBound.compareTo(range.upperBound) >= 0) {
                        return (Map.Entry)this.endOfData();
                    }
                    range = range.intersection(SubRangeSetRangesByLowerBound.access$300(this.this$0));
                    if (SubRangeSetRangesByLowerBound.access$400(this.this$0).contains(range.lowerBound)) {
                        return Maps.immutableEntry(range.lowerBound, range);
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
        public int size() {
            return Iterators.size(this.entryIterator());
        }

        @Override
        @Nullable
        public Object get(@Nullable Object object) {
            return this.get(object);
        }

        @Override
        public NavigableMap tailMap(Object object, boolean bl) {
            return this.tailMap((Cut)object, bl);
        }

        @Override
        public NavigableMap headMap(Object object, boolean bl) {
            return this.headMap((Cut)object, bl);
        }

        @Override
        public NavigableMap subMap(Object object, boolean bl, Object object2, boolean bl2) {
            return this.subMap((Cut)object, bl, (Cut)object2, bl2);
        }

        static Range access$300(SubRangeSetRangesByLowerBound subRangeSetRangesByLowerBound) {
            return subRangeSetRangesByLowerBound.restriction;
        }

        static Range access$400(SubRangeSetRangesByLowerBound subRangeSetRangesByLowerBound) {
            return subRangeSetRangesByLowerBound.lowerBoundWindow;
        }

        SubRangeSetRangesByLowerBound(Range range, Range range2, NavigableMap navigableMap, 1 var4_4) {
            this(range, range2, navigableMap);
        }
    }

    private final class Complement
    extends TreeRangeSet<C> {
        final TreeRangeSet this$0;

        Complement(TreeRangeSet treeRangeSet) {
            this.this$0 = treeRangeSet;
            super(new ComplementRangesByLowerBound(treeRangeSet.rangesByLowerBound), null);
        }

        @Override
        public void add(Range<C> range) {
            this.this$0.remove(range);
        }

        @Override
        public void remove(Range<C> range) {
            this.this$0.add(range);
        }

        @Override
        public boolean contains(C c) {
            return !this.this$0.contains((Comparable)c);
        }

        @Override
        public RangeSet<C> complement() {
            return this.this$0;
        }
    }

    private static final class ComplementRangesByLowerBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final NavigableMap<Cut<C>, Range<C>> positiveRangesByLowerBound;
        private final NavigableMap<Cut<C>, Range<C>> positiveRangesByUpperBound;
        private final Range<Cut<C>> complementLowerBoundWindow;

        ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this(navigableMap, Range.all());
        }

        private ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.positiveRangesByLowerBound = navigableMap;
            this.positiveRangesByUpperBound = new RangesByUpperBound<C>(navigableMap);
            this.complementLowerBoundWindow = range;
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (!this.complementLowerBoundWindow.isConnected(range)) {
                return ImmutableSortedMap.of();
            }
            range = range.intersection(this.complementLowerBoundWindow);
            return new ComplementRangesByLowerBound<C>(this.positiveRangesByLowerBound, range);
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            Cut cut;
            Collection collection = this.complementLowerBoundWindow.hasLowerBound() ? this.positiveRangesByUpperBound.tailMap(this.complementLowerBoundWindow.lowerEndpoint(), this.complementLowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values() : this.positiveRangesByUpperBound.values();
            PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (this.complementLowerBoundWindow.contains(Cut.belowAll()) && (!peekingIterator.hasNext() || ((Range)peekingIterator.peek()).lowerBound != Cut.belowAll())) {
                cut = Cut.belowAll();
            } else if (peekingIterator.hasNext()) {
                cut = ((Range)peekingIterator.next()).upperBound;
            } else {
                return Iterators.emptyIterator();
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, cut, peekingIterator){
                Cut<C> nextComplementRangeLowerBound;
                final Cut val$firstComplementRangeLowerBound;
                final PeekingIterator val$positiveItr;
                final ComplementRangesByLowerBound this$0;
                {
                    this.this$0 = complementRangesByLowerBound;
                    this.val$firstComplementRangeLowerBound = cut;
                    this.val$positiveItr = peekingIterator;
                    this.nextComplementRangeLowerBound = this.val$firstComplementRangeLowerBound;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    Range range;
                    if (ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)this.this$0).upperBound.isLessThan(this.nextComplementRangeLowerBound) || this.nextComplementRangeLowerBound == Cut.aboveAll()) {
                        return (Map.Entry)this.endOfData();
                    }
                    if (this.val$positiveItr.hasNext()) {
                        Range range2 = (Range)this.val$positiveItr.next();
                        range = Range.create(this.nextComplementRangeLowerBound, range2.lowerBound);
                        this.nextComplementRangeLowerBound = range2.upperBound;
                    } else {
                        range = Range.create(this.nextComplementRangeLowerBound, Cut.aboveAll());
                        this.nextComplementRangeLowerBound = Cut.aboveAll();
                    }
                    return Maps.immutableEntry(range.lowerBound, range);
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            Cut cut;
            boolean bl;
            Cut cut2 = this.complementLowerBoundWindow.hasUpperBound() ? this.complementLowerBoundWindow.upperEndpoint() : Cut.aboveAll();
            PeekingIterator peekingIterator = Iterators.peekingIterator(this.positiveRangesByUpperBound.headMap(cut2, bl = this.complementLowerBoundWindow.hasUpperBound() && this.complementLowerBoundWindow.upperBoundType() == BoundType.CLOSED).descendingMap().values().iterator());
            if (peekingIterator.hasNext()) {
                cut = ((Range)peekingIterator.peek()).upperBound == Cut.aboveAll() ? ((Range)peekingIterator.next()).lowerBound : this.positiveRangesByLowerBound.higherKey(((Range)peekingIterator.peek()).upperBound);
            } else {
                if (!this.complementLowerBoundWindow.contains(Cut.belowAll()) || this.positiveRangesByLowerBound.containsKey(Cut.belowAll())) {
                    return Iterators.emptyIterator();
                }
                cut = this.positiveRangesByLowerBound.higherKey(Cut.belowAll());
            }
            Cut cut3 = MoreObjects.firstNonNull(cut, Cut.aboveAll());
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, cut3, peekingIterator){
                Cut<C> nextComplementRangeUpperBound;
                final Cut val$firstComplementRangeUpperBound;
                final PeekingIterator val$positiveItr;
                final ComplementRangesByLowerBound this$0;
                {
                    this.this$0 = complementRangesByLowerBound;
                    this.val$firstComplementRangeUpperBound = cut;
                    this.val$positiveItr = peekingIterator;
                    this.nextComplementRangeUpperBound = this.val$firstComplementRangeUpperBound;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
                        return (Map.Entry)this.endOfData();
                    }
                    if (this.val$positiveItr.hasNext()) {
                        Range range = (Range)this.val$positiveItr.next();
                        Range range2 = Range.create(range.upperBound, this.nextComplementRangeUpperBound);
                        this.nextComplementRangeUpperBound = range.lowerBound;
                        if (ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)this.this$0).lowerBound.isLessThan(range2.lowerBound)) {
                            return Maps.immutableEntry(range2.lowerBound, range2);
                        }
                    } else if (ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)this.this$0).lowerBound.isLessThan(Cut.belowAll())) {
                        Range range = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
                        this.nextComplementRangeUpperBound = Cut.belowAll();
                        return Maps.immutableEntry(Cut.belowAll(), range);
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
        public int size() {
            return Iterators.size(this.entryIterator());
        }

        @Override
        @Nullable
        public Range<C> get(Object object) {
            if (object instanceof Cut) {
                try {
                    Cut cut = (Cut)object;
                    Map.Entry<Cut<C>, Range<C>> entry = this.tailMap(cut, false).firstEntry();
                    if (entry != null && entry.getKey().equals(cut)) {
                        return entry.getValue();
                    }
                } catch (ClassCastException classCastException) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.get(object) != null;
        }

        @Override
        @Nullable
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        public NavigableMap tailMap(Object object, boolean bl) {
            return this.tailMap((Cut)object, bl);
        }

        @Override
        public NavigableMap headMap(Object object, boolean bl) {
            return this.headMap((Cut)object, bl);
        }

        @Override
        public NavigableMap subMap(Object object, boolean bl, Object object2, boolean bl2) {
            return this.subMap((Cut)object, bl, (Cut)object2, bl2);
        }

        static Range access$100(ComplementRangesByLowerBound complementRangesByLowerBound) {
            return complementRangesByLowerBound.complementLowerBoundWindow;
        }
    }

    @VisibleForTesting
    static final class RangesByUpperBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
        private final Range<Cut<C>> upperBoundWindow;

        RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.rangesByLowerBound = navigableMap;
            this.upperBoundWindow = Range.all();
        }

        private RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.rangesByLowerBound = navigableMap;
            this.upperBoundWindow = range;
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (range.isConnected(this.upperBoundWindow)) {
                return new RangesByUpperBound<C>(this.rangesByLowerBound, range.intersection(this.upperBoundWindow));
            }
            return ImmutableSortedMap.of();
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        public boolean containsKey(@Nullable Object object) {
            return this.get(object) != null;
        }

        @Override
        public Range<C> get(@Nullable Object object) {
            if (object instanceof Cut) {
                try {
                    Cut cut = (Cut)object;
                    if (!this.upperBoundWindow.contains(cut)) {
                        return null;
                    }
                    Map.Entry<Cut, Range<C>> entry = this.rangesByLowerBound.lowerEntry(cut);
                    if (entry != null && entry.getValue().upperBound.equals(cut)) {
                        return entry.getValue();
                    }
                } catch (ClassCastException classCastException) {
                    return null;
                }
            }
            return null;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            Map.Entry<Cut<C>, Range<C>> entry;
            Iterator iterator2 = !this.upperBoundWindow.hasLowerBound() ? this.rangesByLowerBound.values().iterator() : ((entry = this.rangesByLowerBound.lowerEntry(this.upperBoundWindow.lowerEndpoint())) == null ? this.rangesByLowerBound.values().iterator() : (this.upperBoundWindow.lowerBound.isLessThan(entry.getValue().upperBound) ? this.rangesByLowerBound.tailMap(entry.getKey(), true).values().iterator() : this.rangesByLowerBound.tailMap(this.upperBoundWindow.lowerEndpoint(), true).values().iterator()));
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, iterator2){
                final Iterator val$backingItr;
                final RangesByUpperBound this$0;
                {
                    this.this$0 = rangesByUpperBound;
                    this.val$backingItr = iterator2;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (!this.val$backingItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Range range = (Range)this.val$backingItr.next();
                    if (RangesByUpperBound.access$000((RangesByUpperBound)this.this$0).upperBound.isLessThan(range.upperBound)) {
                        return (Map.Entry)this.endOfData();
                    }
                    return Maps.immutableEntry(range.upperBound, range);
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            Collection collection = this.upperBoundWindow.hasUpperBound() ? this.rangesByLowerBound.headMap(this.upperBoundWindow.upperEndpoint(), false).descendingMap().values() : this.rangesByLowerBound.descendingMap().values();
            PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (peekingIterator.hasNext() && this.upperBoundWindow.upperBound.isLessThan(((Range)peekingIterator.peek()).upperBound)) {
                peekingIterator.next();
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(this, peekingIterator){
                final PeekingIterator val$backingItr;
                final RangesByUpperBound this$0;
                {
                    this.this$0 = rangesByUpperBound;
                    this.val$backingItr = peekingIterator;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (!this.val$backingItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Range range = (Range)this.val$backingItr.next();
                    return RangesByUpperBound.access$000((RangesByUpperBound)this.this$0).lowerBound.isLessThan(range.upperBound) ? Maps.immutableEntry(range.upperBound, range) : (Map.Entry)this.endOfData();
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        @Override
        public int size() {
            if (this.upperBoundWindow.equals(Range.all())) {
                return this.rangesByLowerBound.size();
            }
            return Iterators.size(this.entryIterator());
        }

        @Override
        public boolean isEmpty() {
            return this.upperBoundWindow.equals(Range.all()) ? this.rangesByLowerBound.isEmpty() : !this.entryIterator().hasNext();
        }

        @Override
        public Object get(@Nullable Object object) {
            return this.get(object);
        }

        @Override
        public NavigableMap tailMap(Object object, boolean bl) {
            return this.tailMap((Cut)object, bl);
        }

        @Override
        public NavigableMap headMap(Object object, boolean bl) {
            return this.headMap((Cut)object, bl);
        }

        @Override
        public NavigableMap subMap(Object object, boolean bl, Object object2, boolean bl2) {
            return this.subMap((Cut)object, bl, (Cut)object2, bl2);
        }

        static Range access$000(RangesByUpperBound rangesByUpperBound) {
            return rangesByUpperBound.upperBoundWindow;
        }
    }

    final class AsRanges
    extends ForwardingCollection<Range<C>>
    implements Set<Range<C>> {
        final Collection<Range<C>> delegate;
        final TreeRangeSet this$0;

        AsRanges(TreeRangeSet treeRangeSet, Collection<Range<C>> collection) {
            this.this$0 = treeRangeSet;
            this.delegate = collection;
        }

        @Override
        protected Collection<Range<C>> delegate() {
            return this.delegate;
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

