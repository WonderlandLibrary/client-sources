package com.google.common.util.concurrent;

import javax.annotation.*;
import java.util.concurrent.*;

public class ListenableFutureTask<V> extends FutureTask<V> implements ListenableFuture<V>
{
    private final ExecutionList executionList;
    
    public static <V> ListenableFutureTask<V> create(final Callable<V> callable) {
        return new ListenableFutureTask<V>(callable);
    }
    
    public static <V> ListenableFutureTask<V> create(final Runnable runnable, @Nullable final V result) {
        return new ListenableFutureTask<V>(runnable, result);
    }
    
    ListenableFutureTask(final Callable<V> callable) {
        super(callable);
        this.executionList = new ExecutionList();
    }
    
    ListenableFutureTask(final Runnable runnable, @Nullable final V result) {
        super(runnable, result);
        this.executionList = new ExecutionList();
    }
    
    @Override
    public void addListener(final Runnable listener, final Executor exec) {
        this.executionList.add(listener, exec);
    }
    
    @Override
    protected void done() {
        this.executionList.execute();
    }
}
