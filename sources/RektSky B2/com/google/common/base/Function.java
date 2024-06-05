package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public interface Function<F, T>
{
    @Nullable
    T apply(@Nullable final F p0);
    
    boolean equals(@Nullable final Object p0);
}
