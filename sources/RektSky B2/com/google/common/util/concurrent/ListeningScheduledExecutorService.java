package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface ListeningScheduledExecutorService extends ScheduledExecutorService, ListeningExecutorService
{
    ListenableScheduledFuture<?> schedule(final Runnable p0, final long p1, final TimeUnit p2);
    
     <V> ListenableScheduledFuture<V> schedule(final Callable<V> p0, final long p1, final TimeUnit p2);
    
    ListenableScheduledFuture<?> scheduleAtFixedRate(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
    
    ListenableScheduledFuture<?> scheduleWithFixedDelay(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
}
