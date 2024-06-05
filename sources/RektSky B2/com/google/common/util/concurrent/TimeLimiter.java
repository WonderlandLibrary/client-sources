package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface TimeLimiter
{
     <T> T newProxy(final T p0, final Class<T> p1, final long p2, final TimeUnit p3);
    
     <T> T callWithTimeout(final Callable<T> p0, final long p1, final TimeUnit p2, final boolean p3) throws Exception;
}
