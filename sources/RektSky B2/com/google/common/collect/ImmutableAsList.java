package com.google.common.collect;

import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
abstract class ImmutableAsList<E> extends ImmutableList<E>
{
    abstract ImmutableCollection<E> delegateCollection();
    
    @Override
    public boolean contains(final Object target) {
        return this.delegateCollection().contains(target);
    }
    
    @Override
    public int size() {
        return this.delegateCollection().size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegateCollection().isEmpty();
    }
    
    @Override
    boolean isPartialView() {
        return this.delegateCollection().isPartialView();
    }
    
    @GwtIncompatible("serialization")
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new SerializedForm(this.delegateCollection());
    }
    
    @GwtIncompatible("serialization")
    static class SerializedForm implements Serializable
    {
        final ImmutableCollection<?> collection;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableCollection<?> collection) {
            this.collection = collection;
        }
        
        Object readResolve() {
            return this.collection.asList();
        }
    }
}
