/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.BoundType;
import com.google.common.collect.Collections2;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.EmptyContiguousSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class RegularContiguousSet<C extends Comparable>
extends ContiguousSet<C> {
    private final Range<C> range;
    private static final long serialVersionUID = 0L;

    RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.range = range;
    }

    private ContiguousSet<C> intersectionInCurrentDomain(Range<C> range) {
        return this.range.isConnected(range) ? ContiguousSet.create(this.range.intersection(range), this.domain) : new EmptyContiguousSet(this.domain);
    }

    @Override
    ContiguousSet<C> headSetImpl(C c, boolean bl) {
        return this.intersectionInCurrentDomain(Range.upTo(c, BoundType.forBoolean(bl)));
    }

    @Override
    ContiguousSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
        if (c.compareTo(c2) == 0 && !bl && !bl2) {
            return new EmptyContiguousSet(this.domain);
        }
        return this.intersectionInCurrentDomain(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
    }

    @Override
    ContiguousSet<C> tailSetImpl(C c, boolean bl) {
        return this.intersectionInCurrentDomain(Range.downTo(c, BoundType.forBoolean(bl)));
    }

    @Override
    @GwtIncompatible
    int indexOf(Object object) {
        return this.contains(object) ? (int)this.domain.distance(this.first(), (Comparable)object) : -1;
    }

    @Override
    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(this, (Comparable)this.first()){
            final C last;
            final RegularContiguousSet this$0;
            {
                this.this$0 = regularContiguousSet;
                super(comparable);
                this.last = this.this$0.last();
            }

            @Override
            protected C computeNext(C c) {
                return RegularContiguousSet.access$000(c, this.last) ? null : (Object)this.this$0.domain.next(c);
            }

            @Override
            protected Object computeNext(Object object) {
                return this.computeNext((C)((Comparable)object));
            }
        };
    }

    @Override
    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(this, (Comparable)this.last()){
            final C first;
            final RegularContiguousSet this$0;
            {
                this.this$0 = regularContiguousSet;
                super(comparable);
                this.first = this.this$0.first();
            }

            @Override
            protected C computeNext(C c) {
                return RegularContiguousSet.access$000(c, this.first) ? null : (Object)this.this$0.domain.previous(c);
            }

            @Override
            protected Object computeNext(Object object) {
                return this.computeNext((C)((Comparable)object));
            }
        };
    }

    private static boolean equalsOrThrow(Comparable<?> comparable, @Nullable Comparable<?> comparable2) {
        return comparable2 != null && Range.compareOrThrow(comparable, comparable2) == 0;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public C first() {
        return this.range.lowerBound.leastValueAbove(this.domain);
    }

    @Override
    public C last() {
        return this.range.upperBound.greatestValueBelow(this.domain);
    }

    @Override
    public int size() {
        long l = this.domain.distance(this.first(), this.last());
        return l >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l + 1;
    }

    @Override
    public boolean contains(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        try {
            return this.range.contains((Comparable)object);
        } catch (ClassCastException classCastException) {
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Comparable comparable;
        Preconditions.checkNotNull(contiguousSet);
        Preconditions.checkArgument(this.domain.equals(contiguousSet.domain));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable2 = (Comparable)Ordering.natural().max(this.first(), contiguousSet.first());
        return comparable2.compareTo(comparable = (Comparable)Ordering.natural().min(this.last(), contiguousSet.last())) <= 0 ? ContiguousSet.create(Range.closed(comparable2, comparable), this.domain) : new EmptyContiguousSet(this.domain);
    }

    @Override
    public Range<C> range() {
        return this.range(BoundType.CLOSED, BoundType.CLOSED);
    }

    @Override
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.create(this.range.lowerBound.withLowerBoundType(boundType, this.domain), this.range.upperBound.withUpperBoundType(boundType2, this.domain));
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof RegularContiguousSet) {
            RegularContiguousSet regularContiguousSet = (RegularContiguousSet)object;
            if (this.domain.equals(regularContiguousSet.domain)) {
                return this.first().equals(regularContiguousSet.first()) && this.last().equals(regularContiguousSet.last());
            }
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @Override
    @GwtIncompatible
    Object writeReplace() {
        return new SerializedForm(this.range, this.domain, null);
    }

    @Override
    public Object last() {
        return this.last();
    }

    @Override
    public Object first() {
        return this.first();
    }

    @Override
    ImmutableSortedSet tailSetImpl(Object object, boolean bl) {
        return this.tailSetImpl((C)((Comparable)object), bl);
    }

    @Override
    ImmutableSortedSet subSetImpl(Object object, boolean bl, Object object2, boolean bl2) {
        return this.subSetImpl((C)((Comparable)object), bl, (C)((Comparable)object2), bl2);
    }

    @Override
    ImmutableSortedSet headSetImpl(Object object, boolean bl) {
        return this.headSetImpl((C)((Comparable)object), bl);
    }

    @Override
    @GwtIncompatible
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    static boolean access$000(Comparable comparable, Comparable comparable2) {
        return RegularContiguousSet.equalsOrThrow(comparable, comparable2);
    }

    @GwtIncompatible
    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        final Range<C> range;
        final DiscreteDomain<C> domain;

        private SerializedForm(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.range = range;
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new RegularContiguousSet<C>(this.range, this.domain);
        }

        SerializedForm(Range range, DiscreteDomain discreteDomain, 1 var3_3) {
            this(range, discreteDomain);
        }
    }
}

