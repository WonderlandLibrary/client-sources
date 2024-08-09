/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Beta
@GwtIncompatible
public abstract class ForwardingCheckedFuture<V, X extends Exception>
extends ForwardingListenableFuture<V>
implements CheckedFuture<V, X> {
    @Override
    @CanIgnoreReturnValue
    public V checkedGet() throws X {
        return this.delegate().checkedGet();
    }

    @Override
    @CanIgnoreReturnValue
    public V checkedGet(long l, TimeUnit timeUnit) throws TimeoutException, X {
        return this.delegate().checkedGet(l, timeUnit);
    }

    @Override
    protected abstract CheckedFuture<V, X> delegate();

    @Override
    protected ListenableFuture delegate() {
        return this.delegate();
    }

    @Override
    protected Future delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Beta
    public static abstract class SimpleForwardingCheckedFuture<V, X extends Exception>
    extends ForwardingCheckedFuture<V, X> {
        private final CheckedFuture<V, X> delegate;

        protected SimpleForwardingCheckedFuture(CheckedFuture<V, X> checkedFuture) {
            this.delegate = Preconditions.checkNotNull(checkedFuture);
        }

        @Override
        protected final CheckedFuture<V, X> delegate() {
            return this.delegate;
        }

        @Override
        protected ListenableFuture delegate() {
            return this.delegate();
        }

        @Override
        protected Future delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

