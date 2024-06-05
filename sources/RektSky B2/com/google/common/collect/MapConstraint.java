package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
@Beta
public interface MapConstraint<K, V>
{
    void checkKeyValue(@Nullable final K p0, @Nullable final V p1);
    
    String toString();
}
