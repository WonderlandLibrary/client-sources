package com.google.common.util.concurrent;

import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class ForwardingExecutorService extends ForwardingObject implements ExecutorService
{
    protected ForwardingExecutorService() {
    }
    
    @Override
    protected abstract ExecutorService delegate();
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().awaitTermination(timeout, unit);
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.delegate().invokeAll(tasks);
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().invokeAll(tasks, timeout, unit);
    }
    
    @Override
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.delegate().invokeAny(tasks);
    }
    
    @Override
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().invokeAny(tasks, timeout, unit);
    }
    
    @Override
    public boolean isShutdown() {
        return this.delegate().isShutdown();
    }
    
    @Override
    public boolean isTerminated() {
        return this.delegate().isTerminated();
    }
    
    @Override
    public void shutdown() {
        this.delegate().shutdown();
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate().shutdownNow();
    }
    
    @Override
    public void execute(final Runnable command) {
        this.delegate().execute(command);
    }
    
    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return this.delegate().submit(task);
    }
    
    @Override
    public Future<?> submit(final Runnable task) {
        return this.delegate().submit(task);
    }
    
    @Override
    public <T> Future<T> submit(final Runnable task, final T result) {
        return this.delegate().submit(task, result);
    }
}
