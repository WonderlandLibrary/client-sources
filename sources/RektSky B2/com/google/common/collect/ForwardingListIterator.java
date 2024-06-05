package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingListIterator<E> extends ForwardingIterator<E> implements ListIterator<E>
{
    protected ForwardingListIterator() {
    }
    
    @Override
    protected abstract ListIterator<E> delegate();
    
    @Override
    public void add(final E element) {
        this.delegate().add(element);
    }
    
    @Override
    public boolean hasPrevious() {
        return this.delegate().hasPrevious();
    }
    
    @Override
    public int nextIndex() {
        return this.delegate().nextIndex();
    }
    
    @Override
    public E previous() {
        return this.delegate().previous();
    }
    
    @Override
    public int previousIndex() {
        return this.delegate().previousIndex();
    }
    
    @Override
    public void set(final E element) {
        this.delegate().set(element);
    }
}
