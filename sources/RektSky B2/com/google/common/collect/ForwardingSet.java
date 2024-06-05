package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E>
{
    protected ForwardingSet() {
    }
    
    @Override
    protected abstract Set<E> delegate();
    
    @Override
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Override
    protected boolean standardRemoveAll(final Collection<?> collection) {
        return Sets.removeAllImpl(this, Preconditions.checkNotNull(collection));
    }
    
    protected boolean standardEquals(@Nullable final Object object) {
        return Sets.equalsImpl(this, object);
    }
    
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
    }
}
