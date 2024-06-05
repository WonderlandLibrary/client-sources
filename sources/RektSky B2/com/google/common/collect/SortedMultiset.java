package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E>
{
    Comparator<? super E> comparator();
    
    Multiset.Entry<E> firstEntry();
    
    Multiset.Entry<E> lastEntry();
    
    Multiset.Entry<E> pollFirstEntry();
    
    Multiset.Entry<E> pollLastEntry();
    
    NavigableSet<E> elementSet();
    
    Set<Multiset.Entry<E>> entrySet();
    
    Iterator<E> iterator();
    
    SortedMultiset<E> descendingMultiset();
    
    SortedMultiset<E> headMultiset(final E p0, final BoundType p1);
    
    SortedMultiset<E> subMultiset(final E p0, final BoundType p1, final E p2, final BoundType p3);
    
    SortedMultiset<E> tailMultiset(final E p0, final BoundType p1);
}
