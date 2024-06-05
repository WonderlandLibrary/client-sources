package com.google.common.util.concurrent;

import java.util.*;
import java.util.concurrent.*;

public interface ListeningExecutorService extends ExecutorService
{
     <T> ListenableFuture<T> submit(final Callable<T> p0);
    
    ListenableFuture<?> submit(final Runnable p0);
    
     <T> ListenableFuture<T> submit(final Runnable p0, final T p1);
    
     <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> p0) throws InterruptedException;
    
     <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> p0, final long p1, final TimeUnit p2) throws InterruptedException;
}
