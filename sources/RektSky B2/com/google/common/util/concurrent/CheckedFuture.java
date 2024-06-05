package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface CheckedFuture<V, X extends Exception> extends ListenableFuture<V>
{
    V checkedGet() throws X, Exception;
    
    V checkedGet(final long p0, final TimeUnit p1) throws TimeoutException, X, Exception;
}
