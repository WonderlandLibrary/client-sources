package com.google.common.collect;

import com.google.common.annotations.*;

@GwtCompatible(serializable = true)
class EmptyImmutableListMultimap extends ImmutableListMultimap<Object, Object>
{
    static final EmptyImmutableListMultimap INSTANCE;
    private static final long serialVersionUID = 0L;
    
    private EmptyImmutableListMultimap() {
        super(ImmutableMap.of(), 0);
    }
    
    private Object readResolve() {
        return EmptyImmutableListMultimap.INSTANCE;
    }
    
    static {
        INSTANCE = new EmptyImmutableListMultimap();
    }
}
