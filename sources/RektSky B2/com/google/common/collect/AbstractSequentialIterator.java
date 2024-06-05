package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T>
{
    private T nextOrNull;
    
    protected AbstractSequentialIterator(@Nullable final T firstOrNull) {
        this.nextOrNull = firstOrNull;
    }
    
    protected abstract T computeNext(final T p0);
    
    @Override
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }
    
    @Override
    public final T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return this.nextOrNull;
        }
        finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
        }
    }
}
