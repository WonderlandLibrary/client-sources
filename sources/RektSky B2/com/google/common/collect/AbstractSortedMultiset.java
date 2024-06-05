package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(emulated = true)
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E>
{
    @GwtTransient
    final Comparator<? super E> comparator;
    private transient SortedMultiset<E> descendingMultiset;
    
    AbstractSortedMultiset() {
        this((Comparator)Ordering.natural());
    }
    
    AbstractSortedMultiset(final Comparator<? super E> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }
    
    @Override
    public NavigableSet<E> elementSet() {
        return (NavigableSet<E>)(NavigableSet)super.elementSet();
    }
    
    @Override
    NavigableSet<E> createElementSet() {
        return new SortedMultisets.NavigableElementSet<E>(this);
    }
    
    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    @Override
    public Multiset.Entry<E> firstEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
        return entryIterator.hasNext() ? entryIterator.next() : null;
    }
    
    @Override
    public Multiset.Entry<E> lastEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
        return entryIterator.hasNext() ? entryIterator.next() : null;
    }
    
    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
        if (entryIterator.hasNext()) {
            Multiset.Entry<E> result = entryIterator.next();
            result = Multisets.immutableEntry(result.getElement(), result.getCount());
            entryIterator.remove();
            return result;
        }
        return null;
    }
    
    @Override
    public Multiset.Entry<E> pollLastEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
        if (entryIterator.hasNext()) {
            Multiset.Entry<E> result = entryIterator.next();
            result = Multisets.immutableEntry(result.getElement(), result.getCount());
            entryIterator.remove();
            return result;
        }
        return null;
    }
    
    @Override
    public SortedMultiset<E> subMultiset(@Nullable final E fromElement, final BoundType fromBoundType, @Nullable final E toElement, final BoundType toBoundType) {
        Preconditions.checkNotNull(fromBoundType);
        Preconditions.checkNotNull(toBoundType);
        return this.tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
    }
    
    abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();
    
    Iterator<E> descendingIterator() {
        return Multisets.iteratorImpl(this.descendingMultiset());
    }
    
    @Override
    public SortedMultiset<E> descendingMultiset() {
        final SortedMultiset<E> result = this.descendingMultiset;
        return (result == null) ? (this.descendingMultiset = this.createDescendingMultiset()) : result;
    }
    
    SortedMultiset<E> createDescendingMultiset() {
        return new DescendingMultiset<E>() {
            @Override
            SortedMultiset<E> forwardMultiset() {
                return (SortedMultiset<E>)AbstractSortedMultiset.this;
            }
            
            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return AbstractSortedMultiset.this.descendingEntryIterator();
            }
            
            @Override
            public Iterator<E> iterator() {
                return AbstractSortedMultiset.this.descendingIterator();
            }
        };
    }
}
