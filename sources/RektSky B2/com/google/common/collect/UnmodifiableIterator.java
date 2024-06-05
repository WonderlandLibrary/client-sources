package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class UnmodifiableIterator<E> implements Iterator<E>
{
    protected UnmodifiableIterator() {
    }
    
    @Deprecated
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
