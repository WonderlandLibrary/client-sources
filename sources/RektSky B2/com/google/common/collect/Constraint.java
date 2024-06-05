package com.google.common.collect;

import com.google.common.annotations.*;

@GwtCompatible
interface Constraint<E>
{
    E checkElement(final E p0);
    
    String toString();
}
