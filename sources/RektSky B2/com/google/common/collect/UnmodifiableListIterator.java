package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E>
{
    protected UnmodifiableListIterator() {
    }
    
    @Deprecated
    @Override
    public final void add(final E e) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void set(final E e) {
        throw new UnsupportedOperationException();
    }
}
