package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;

@Beta
public final class FakeTimeLimiter implements TimeLimiter
{
    @Override
    public <T> T newProxy(final T target, final Class<T> interfaceType, final long timeoutDuration, final TimeUnit timeoutUnit) {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(timeoutUnit);
        return target;
    }
    
    @Override
    public <T> T callWithTimeout(final Callable<T> callable, final long timeoutDuration, final TimeUnit timeoutUnit, final boolean amInterruptible) throws Exception {
        Preconditions.checkNotNull(timeoutUnit);
        return callable.call();
    }
}
