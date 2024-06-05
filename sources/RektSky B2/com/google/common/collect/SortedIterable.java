package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
interface SortedIterable<T> extends Iterable<T>
{
    Comparator<? super T> comparator();
    
    Iterator<T> iterator();
}
