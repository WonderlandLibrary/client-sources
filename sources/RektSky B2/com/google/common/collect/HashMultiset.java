package com.google.common.collect;

import com.google.common.annotations.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public final class HashMultiset<E> extends AbstractMapBasedMultiset<E>
{
    @GwtIncompatible("Not needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static <E> HashMultiset<E> create() {
        return new HashMultiset<E>();
    }
    
    public static <E> HashMultiset<E> create(final int distinctElements) {
        return new HashMultiset<E>(distinctElements);
    }
    
    public static <E> HashMultiset<E> create(final Iterable<? extends E> elements) {
        final HashMultiset<E> multiset = create(Multisets.inferDistinctElements(elements));
        Iterables.addAll(multiset, elements);
        return multiset;
    }
    
    private HashMultiset() {
        super(new HashMap());
    }
    
    private HashMultiset(final int distinctElements) {
        super((Map<Object, Count>)Maps.newHashMapWithExpectedSize(distinctElements));
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMultiset((Multiset<Object>)this, stream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        final int distinctElements = Serialization.readCount(stream);
        this.setBackingMap((Map<E, Count>)Maps.newHashMapWithExpectedSize(distinctElements));
        Serialization.populateMultiset((Multiset<Object>)this, stream, distinctElements);
    }
}
