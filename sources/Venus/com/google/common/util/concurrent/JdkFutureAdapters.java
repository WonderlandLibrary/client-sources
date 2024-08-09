/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.ForwardingFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@Beta
@GwtIncompatible
public final class JdkFutureAdapters {
    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture)future;
        }
        return new ListenableFutureAdapter<V>(future);
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture)future;
        }
        return new ListenableFutureAdapter<V>(future, executor);
    }

    private JdkFutureAdapters() {
    }

    private static class ListenableFutureAdapter<V>
    extends ForwardingFuture<V>
    implements ListenableFuture<V> {
        private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(false).setNameFormat("ListenableFutureAdapter-thread-%d").build();
        private static final Executor defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory);
        private final Executor adapterExecutor;
        private final ExecutionList executionList = new ExecutionList();
        private final AtomicBoolean hasListeners = new AtomicBoolean(false);
        private final Future<V> delegate;

        ListenableFutureAdapter(Future<V> future) {
            this(future, defaultAdapterExecutor);
        }

        ListenableFutureAdapter(Future<V> future, Executor executor) {
            this.delegate = Preconditions.checkNotNull(future);
            this.adapterExecutor = Preconditions.checkNotNull(executor);
        }

        @Override
        protected Future<V> delegate() {
            return this.delegate;
        }

        @Override
        public void addListener(Runnable runnable, Executor executor) {
            this.executionList.add(runnable, executor);
            if (this.hasListeners.compareAndSet(false, false)) {
                if (this.delegate.isDone()) {
                    this.executionList.execute();
                    return;
                }
                this.adapterExecutor.execute(new Runnable(this){
                    final ListenableFutureAdapter this$0;
                    {
                        this.this$0 = listenableFutureAdapter;
                    }

                    @Override
                    public void run() {
                        try {
                            Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.access$000(this.this$0));
                        } catch (Throwable throwable) {
                            // empty catch block
                        }
                        ListenableFutureAdapter.access$100(this.this$0).execute();
                    }
                });
            }
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        static Future access$000(ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.delegate;
        }

        static ExecutionList access$100(ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.executionList;
        }
    }
}

