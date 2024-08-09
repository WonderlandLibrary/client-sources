/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Callables {
    private Callables() {
    }

    public static <T> Callable<T> returning(@Nullable T t) {
        return new Callable<T>(t){
            final Object val$value;
            {
                this.val$value = object;
            }

            @Override
            public T call() {
                return this.val$value;
            }
        };
    }

    @Beta
    @GwtIncompatible
    public static <T> AsyncCallable<T> asAsyncCallable(Callable<T> callable, ListeningExecutorService listeningExecutorService) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(listeningExecutorService);
        return new AsyncCallable<T>(listeningExecutorService, callable){
            final ListeningExecutorService val$listeningExecutorService;
            final Callable val$callable;
            {
                this.val$listeningExecutorService = listeningExecutorService;
                this.val$callable = callable;
            }

            @Override
            public ListenableFuture<T> call() throws Exception {
                return this.val$listeningExecutorService.submit(this.val$callable);
            }
        };
    }

    @GwtIncompatible
    static <T> Callable<T> threadRenaming(Callable<T> callable, Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callable);
        return new Callable<T>(supplier, callable){
            final Supplier val$nameSupplier;
            final Callable val$callable;
            {
                this.val$nameSupplier = supplier;
                this.val$callable = callable;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public T call() throws Exception {
                Thread thread2 = Thread.currentThread();
                String string = thread2.getName();
                boolean bl = Callables.access$000((String)this.val$nameSupplier.get(), thread2);
                try {
                    Object v = this.val$callable.call();
                    return v;
                } finally {
                    if (bl) {
                        boolean bl2 = Callables.access$000(string, thread2);
                    }
                }
            }
        };
    }

    @GwtIncompatible
    static Runnable threadRenaming(Runnable runnable, Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable);
        return new Runnable(supplier, runnable){
            final Supplier val$nameSupplier;
            final Runnable val$task;
            {
                this.val$nameSupplier = supplier;
                this.val$task = runnable;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                Thread thread2 = Thread.currentThread();
                String string = thread2.getName();
                boolean bl = Callables.access$000((String)this.val$nameSupplier.get(), thread2);
                try {
                    this.val$task.run();
                } finally {
                    if (bl) {
                        boolean bl2 = Callables.access$000(string, thread2);
                    }
                }
            }
        };
    }

    @GwtIncompatible
    private static boolean trySetName(String string, Thread thread2) {
        try {
            thread2.setName(string);
            return true;
        } catch (SecurityException securityException) {
            return true;
        }
    }

    static boolean access$000(String string, Thread thread2) {
        return Callables.trySetName(string, thread2);
    }
}

