package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E>
{
    protected ForwardingQueue() {
    }
    
    @Override
    protected abstract Queue<E> delegate();
    
    @Override
    public boolean offer(final E o) {
        return this.delegate().offer(o);
    }
    
    @Override
    public E poll() {
        return this.delegate().poll();
    }
    
    @Override
    public E remove() {
        return this.delegate().remove();
    }
    
    @Override
    public E peek() {
        return this.delegate().peek();
    }
    
    @Override
    public E element() {
        return this.delegate().element();
    }
    
    protected boolean standardOffer(final E e) {
        try {
            return this.add(e);
        }
        catch (IllegalStateException caught) {
            return false;
        }
    }
    
    protected E standardPeek() {
        try {
            return this.element();
        }
        catch (NoSuchElementException caught) {
            return null;
        }
    }
    
    protected E standardPoll() {
        try {
            return this.remove();
        }
        catch (NoSuchElementException caught) {
            return null;
        }
    }
}
