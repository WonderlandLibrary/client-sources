package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public interface PeekingIterator<E> extends Iterator<E>
{
    E peek();
    
    E next();
    
    void remove();
}
