package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public abstract class ForwardingCheckedFuture<V, X extends Exception> extends ForwardingListenableFuture<V> implements CheckedFuture<V, X>
{
    @Override
    public V checkedGet() throws X, Exception {
        return this.delegate().checkedGet();
    }
    
    @Override
    public V checkedGet(final long timeout, final TimeUnit unit) throws TimeoutException, X, Exception {
        return this.delegate().checkedGet(timeout, unit);
    }
    
    @Override
    protected abstract CheckedFuture<V, X> delegate();
    
    @Beta
    public abstract static class SimpleForwardingCheckedFuture<V, X extends Exception> extends ForwardingCheckedFuture<V, X>
    {
        private final CheckedFuture<V, X> delegate;
        
        protected SimpleForwardingCheckedFuture(final CheckedFuture<V, X> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        @Override
        protected final CheckedFuture<V, X> delegate() {
            return this.delegate;
        }
    }
}
