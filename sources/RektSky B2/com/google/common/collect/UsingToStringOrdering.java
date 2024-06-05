package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;

@GwtCompatible(serializable = true)
final class UsingToStringOrdering extends Ordering<Object> implements Serializable
{
    static final UsingToStringOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    @Override
    public int compare(final Object left, final Object right) {
        return left.toString().compareTo(right.toString());
    }
    
    private Object readResolve() {
        return UsingToStringOrdering.INSTANCE;
    }
    
    @Override
    public String toString() {
        return "Ordering.usingToString()";
    }
    
    private UsingToStringOrdering() {
    }
    
    static {
        INSTANCE = new UsingToStringOrdering();
    }
}
