/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.Callables;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.TrustedListenableFutureTask;
import com.google.common.util.concurrent.WrappingExecutorService;
import com.google.common.util.concurrent.WrappingScheduledExecutorService;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;

@GwtCompatible(emulated=true)
public final class MoreExecutors {
    private MoreExecutors() {
    }

    @Beta
    @GwtIncompatible
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long l, TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(threadPoolExecutor, l, timeUnit);
    }

    @Beta
    @GwtIncompatible
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long l, TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor, l, timeUnit);
    }

    @Beta
    @GwtIncompatible
    public static void addDelayedShutdownHook(ExecutorService executorService, long l, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(executorService, l, timeUnit);
    }

    @Beta
    @GwtIncompatible
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        return new Application().getExitingExecutorService(threadPoolExecutor);
    }

    @Beta
    @GwtIncompatible
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor);
    }

    @GwtIncompatible
    private static void useDaemonThreadFactory(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(false).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }

    @GwtIncompatible
    public static ListeningExecutorService newDirectExecutorService() {
        return new DirectExecutorService(null);
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    @GwtIncompatible
    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        return executorService instanceof ListeningExecutorService ? (ListeningExecutorService)executorService : (executorService instanceof ScheduledExecutorService ? new ScheduledListeningDecorator((ScheduledExecutorService)executorService) : new ListeningDecorator(executorService));
    }

    @GwtIncompatible
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService scheduledExecutorService) {
        return scheduledExecutorService instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService)scheduledExecutorService : new ScheduledListeningDecorator(scheduledExecutorService);
    }

    @GwtIncompatible
    static <T> T invokeAnyImpl(ListeningExecutorService listeningExecutorService, Collection<? extends Callable<T>> collection, boolean bl, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutionException executionException;
        ArrayList<ListenableFuture<T>> arrayList;
        block15: {
            Preconditions.checkNotNull(listeningExecutorService);
            Preconditions.checkNotNull(timeUnit);
            int n = collection.size();
            Preconditions.checkArgument(n > 0);
            arrayList = Lists.newArrayListWithCapacity(n);
            LinkedBlockingQueue<Future<T>> linkedBlockingQueue = Queues.newLinkedBlockingQueue();
            long l2 = timeUnit.toNanos(l);
            executionException = null;
            long l3 = bl ? System.nanoTime() : 0L;
            Iterator<Callable<T>> iterator2 = collection.iterator();
            arrayList.add(MoreExecutors.submitAndAddQueueListener(listeningExecutorService, iterator2.next(), linkedBlockingQueue));
            --n;
            int n2 = 1;
            while (true) {
                Object v;
                Future future;
                if ((future = (Future)linkedBlockingQueue.poll()) == null) {
                    if (n > 0) {
                        --n;
                        arrayList.add(MoreExecutors.submitAndAddQueueListener(listeningExecutorService, iterator2.next(), linkedBlockingQueue));
                        ++n2;
                    } else {
                        if (n2 == 0) break;
                        if (bl) {
                            future = (Future)linkedBlockingQueue.poll(l2, TimeUnit.NANOSECONDS);
                            if (future == null) {
                                throw new TimeoutException();
                            }
                            long l4 = System.nanoTime();
                            l2 -= l4 - l3;
                            l3 = l4;
                        } else {
                            future = (Future)linkedBlockingQueue.take();
                        }
                    }
                }
                if (future == null) continue;
                --n2;
                try {
                    v = future.get();
                } catch (ExecutionException executionException2) {
                    executionException = executionException2;
                    continue;
                } catch (RuntimeException runtimeException) {
                    executionException = new ExecutionException(runtimeException);
                    continue;
                }
                return (T)v;
                break;
            }
            if (executionException != null) break block15;
            executionException = new ExecutionException(null);
        }
        throw executionException;
        finally {
            for (Future future : arrayList) {
                future.cancel(true);
            }
        }
    }

    @GwtIncompatible
    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService listeningExecutorService, Callable<T> callable, BlockingQueue<Future<T>> blockingQueue) {
        Future future = listeningExecutorService.submit((Callable)callable);
        future.addListener(new Runnable(blockingQueue, (ListenableFuture)future){
            final BlockingQueue val$queue;
            final ListenableFuture val$future;
            {
                this.val$queue = blockingQueue;
                this.val$future = listenableFuture;
            }

            @Override
            public void run() {
                this.val$queue.add(this.val$future);
            }
        }, MoreExecutors.directExecutor());
        return future;
    }

    @Beta
    @GwtIncompatible
    public static ThreadFactory platformThreadFactory() {
        if (!MoreExecutors.isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory)Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", illegalAccessException);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", classNotFoundException);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", noSuchMethodException);
        } catch (InvocationTargetException invocationTargetException) {
            throw Throwables.propagate(invocationTargetException.getCause());
        }
    }

    @GwtIncompatible
    private static boolean isAppEngine() {
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return true;
        }
        try {
            return Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null;
        } catch (ClassNotFoundException classNotFoundException) {
            return true;
        } catch (InvocationTargetException invocationTargetException) {
            return true;
        } catch (IllegalAccessException illegalAccessException) {
            return true;
        } catch (NoSuchMethodException noSuchMethodException) {
            return true;
        }
    }

    @GwtIncompatible
    static Thread newThread(String string, Runnable runnable) {
        Preconditions.checkNotNull(string);
        Preconditions.checkNotNull(runnable);
        Thread thread2 = MoreExecutors.platformThreadFactory().newThread(runnable);
        try {
            thread2.setName(string);
        } catch (SecurityException securityException) {
            // empty catch block
        }
        return thread2;
    }

    @GwtIncompatible
    static Executor renamingDecorator(Executor executor, Supplier<String> supplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(supplier);
        if (MoreExecutors.isAppEngine()) {
            return executor;
        }
        return new Executor(executor, supplier){
            final Executor val$executor;
            final Supplier val$nameSupplier;
            {
                this.val$executor = executor;
                this.val$nameSupplier = supplier;
            }

            @Override
            public void execute(Runnable runnable) {
                this.val$executor.execute(Callables.threadRenaming(runnable, (Supplier<String>)this.val$nameSupplier));
            }
        };
    }

    @GwtIncompatible
    static ExecutorService renamingDecorator(ExecutorService executorService, Supplier<String> supplier) {
        Preconditions.checkNotNull(executorService);
        Preconditions.checkNotNull(supplier);
        if (MoreExecutors.isAppEngine()) {
            return executorService;
        }
        return new WrappingExecutorService(executorService, supplier){
            final Supplier val$nameSupplier;
            {
                this.val$nameSupplier = supplier;
                super(executorService);
            }

            @Override
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, (Supplier<String>)this.val$nameSupplier);
            }

            @Override
            protected Runnable wrapTask(Runnable runnable) {
                return Callables.threadRenaming(runnable, (Supplier<String>)this.val$nameSupplier);
            }
        };
    }

    @GwtIncompatible
    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService scheduledExecutorService, Supplier<String> supplier) {
        Preconditions.checkNotNull(scheduledExecutorService);
        Preconditions.checkNotNull(supplier);
        if (MoreExecutors.isAppEngine()) {
            return scheduledExecutorService;
        }
        return new WrappingScheduledExecutorService(scheduledExecutorService, supplier){
            final Supplier val$nameSupplier;
            {
                this.val$nameSupplier = supplier;
                super(scheduledExecutorService);
            }

            @Override
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, (Supplier<String>)this.val$nameSupplier);
            }

            @Override
            protected Runnable wrapTask(Runnable runnable) {
                return Callables.threadRenaming(runnable, (Supplier<String>)this.val$nameSupplier);
            }
        };
    }

    @Beta
    @CanIgnoreReturnValue
    @GwtIncompatible
    public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long l, TimeUnit timeUnit) {
        long l2 = timeUnit.toNanos(l) / 2L;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(l2, TimeUnit.NANOSECONDS)) {
                executorService.shutdownNow();
                executorService.awaitTermination(l2, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
        return executorService.isTerminated();
    }

    static Executor rejectionPropagatingExecutor(Executor executor, AbstractFuture<?> abstractFuture) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(abstractFuture);
        if (executor == MoreExecutors.directExecutor()) {
            return executor;
        }
        return new Executor(executor, abstractFuture){
            volatile boolean thrownFromDelegate;
            final Executor val$delegate;
            final AbstractFuture val$future;
            {
                this.val$delegate = executor;
                this.val$future = abstractFuture;
                this.thrownFromDelegate = true;
            }

            @Override
            public void execute(Runnable runnable) {
                block2: {
                    try {
                        this.val$delegate.execute(new Runnable(this, runnable){
                            final Runnable val$command;
                            final 5 this$0;
                            {
                                this.this$0 = var1_1;
                                this.val$command = runnable;
                            }

                            @Override
                            public void run() {
                                this.this$0.thrownFromDelegate = false;
                                this.val$command.run();
                            }
                        });
                    } catch (RejectedExecutionException rejectedExecutionException) {
                        if (!this.thrownFromDelegate) break block2;
                        this.val$future.setException(rejectedExecutionException);
                    }
                }
            }
        };
    }

    static void access$000(ThreadPoolExecutor threadPoolExecutor) {
        MoreExecutors.useDaemonThreadFactory(threadPoolExecutor);
    }

    @GwtIncompatible
    private static final class ScheduledListeningDecorator
    extends ListeningDecorator
    implements ListeningScheduledExecutorService {
        final ScheduledExecutorService delegate;

        ScheduledListeningDecorator(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.delegate = Preconditions.checkNotNull(scheduledExecutorService);
        }

        @Override
        public ListenableScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            TrustedListenableFutureTask<Object> trustedListenableFutureTask = TrustedListenableFutureTask.create(runnable, null);
            ScheduledFuture<?> scheduledFuture = this.delegate.schedule(trustedListenableFutureTask, l, timeUnit);
            return new ListenableScheduledTask<Object>(trustedListenableFutureTask, scheduledFuture);
        }

        @Override
        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
            TrustedListenableFutureTask<V> trustedListenableFutureTask = TrustedListenableFutureTask.create(callable);
            ScheduledFuture<?> scheduledFuture = this.delegate.schedule(trustedListenableFutureTask, l, timeUnit);
            return new ListenableScheduledTask<V>(trustedListenableFutureTask, scheduledFuture);
        }

        @Override
        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            ScheduledFuture<?> scheduledFuture = this.delegate.scheduleAtFixedRate(neverSuccessfulListenableFutureTask, l, l2, timeUnit);
            return new ListenableScheduledTask<Void>(neverSuccessfulListenableFutureTask, scheduledFuture);
        }

        @Override
        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            ScheduledFuture<?> scheduledFuture = this.delegate.scheduleWithFixedDelay(neverSuccessfulListenableFutureTask, l, l2, timeUnit);
            return new ListenableScheduledTask<Void>(neverSuccessfulListenableFutureTask, scheduledFuture);
        }

        @Override
        public ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            return this.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
        }

        @Override
        public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            return this.scheduleAtFixedRate(runnable, l, l2, timeUnit);
        }

        @Override
        public ScheduledFuture schedule(Callable callable, long l, TimeUnit timeUnit) {
            return this.schedule(callable, l, timeUnit);
        }

        @Override
        public ScheduledFuture schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            return this.schedule(runnable, l, timeUnit);
        }

        @GwtIncompatible
        private static final class NeverSuccessfulListenableFutureTask
        extends AbstractFuture<Void>
        implements Runnable {
            private final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable runnable) {
                this.delegate = Preconditions.checkNotNull(runnable);
            }

            @Override
            public void run() {
                try {
                    this.delegate.run();
                } catch (Throwable throwable) {
                    this.setException(throwable);
                    throw Throwables.propagate(throwable);
                }
            }
        }

        private static final class ListenableScheduledTask<V>
        extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V>
        implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> scheduledDelegate;

            public ListenableScheduledTask(ListenableFuture<V> listenableFuture, ScheduledFuture<?> scheduledFuture) {
                super(listenableFuture);
                this.scheduledDelegate = scheduledFuture;
            }

            @Override
            public boolean cancel(boolean bl) {
                boolean bl2 = super.cancel(bl);
                if (bl2) {
                    this.scheduledDelegate.cancel(bl);
                }
                return bl2;
            }

            @Override
            public long getDelay(TimeUnit timeUnit) {
                return this.scheduledDelegate.getDelay(timeUnit);
            }

            @Override
            public int compareTo(Delayed delayed) {
                return this.scheduledDelegate.compareTo(delayed);
            }

            @Override
            public int compareTo(Object object) {
                return this.compareTo((Delayed)object);
            }
        }
    }

    @GwtIncompatible
    private static class ListeningDecorator
    extends AbstractListeningExecutorService {
        private final ExecutorService delegate;

        ListeningDecorator(ExecutorService executorService) {
            this.delegate = Preconditions.checkNotNull(executorService);
        }

        @Override
        public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.delegate.awaitTermination(l, timeUnit);
        }

        @Override
        public final boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        @Override
        public final boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        @Override
        public final void shutdown() {
            this.delegate.shutdown();
        }

        @Override
        public final List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }

        @Override
        public final void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }
    }

    private static enum DirectExecutor implements Executor
    {
        INSTANCE;


        @Override
        public void execute(Runnable runnable) {
            runnable.run();
        }

        public String toString() {
            return "MoreExecutors.directExecutor()";
        }
    }

    @GwtIncompatible
    private static final class DirectExecutorService
    extends AbstractListeningExecutorService {
        private final Object lock = new Object();
        @GuardedBy(value="lock")
        private int runningTasks = 0;
        @GuardedBy(value="lock")
        private boolean shutdown = false;

        private DirectExecutorService() {
        }

        @Override
        public void execute(Runnable runnable) {
            this.startTask();
            try {
                runnable.run();
            } finally {
                this.endTask();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isShutdown() {
            Object object = this.lock;
            synchronized (object) {
                return this.shutdown;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void shutdown() {
            Object object = this.lock;
            synchronized (object) {
                this.shutdown = true;
                if (this.runningTasks == 0) {
                    this.lock.notifyAll();
                }
            }
        }

        @Override
        public List<Runnable> shutdownNow() {
            this.shutdown();
            return Collections.emptyList();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isTerminated() {
            Object object = this.lock;
            synchronized (object) {
                return this.shutdown && this.runningTasks == 0;
            }
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            long l2 = timeUnit.toNanos(l);
            Object object = this.lock;
            synchronized (object) {
                while (true) {
                    if (this.shutdown && this.runningTasks == 0) {
                        return true;
                    }
                    if (l2 <= 0L) {
                        return false;
                    }
                    long l3 = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.lock, l2);
                    l2 -= System.nanoTime() - l3;
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void startTask() {
            Object object = this.lock;
            synchronized (object) {
                if (this.shutdown) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                ++this.runningTasks;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void endTask() {
            Object object = this.lock;
            synchronized (object) {
                int n = --this.runningTasks;
                if (n == 0) {
                    this.lock.notifyAll();
                }
            }
        }

        DirectExecutorService(1 var1_1) {
            this();
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static class Application {
        Application() {
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long l, TimeUnit timeUnit) {
            MoreExecutors.access$000(threadPoolExecutor);
            ExecutorService executorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            this.addDelayedShutdownHook(executorService, l, timeUnit);
            return executorService;
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long l, TimeUnit timeUnit) {
            MoreExecutors.access$000(scheduledThreadPoolExecutor);
            ScheduledExecutorService scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            this.addDelayedShutdownHook(scheduledExecutorService, l, timeUnit);
            return scheduledExecutorService;
        }

        final void addDelayedShutdownHook(ExecutorService executorService, long l, TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            this.addShutdownHook(MoreExecutors.newThread("DelayedShutdownHook-for-" + executorService, new Runnable(this, executorService, l, timeUnit){
                final ExecutorService val$service;
                final long val$terminationTimeout;
                final TimeUnit val$timeUnit;
                final Application this$0;
                {
                    this.this$0 = application;
                    this.val$service = executorService;
                    this.val$terminationTimeout = l;
                    this.val$timeUnit = timeUnit;
                }

                @Override
                public void run() {
                    try {
                        this.val$service.shutdown();
                        this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit);
                    } catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }));
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
            return this.getExitingExecutorService(threadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return this.getExitingScheduledExecutorService(scheduledThreadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        @VisibleForTesting
        void addShutdownHook(Thread thread2) {
            Runtime.getRuntime().addShutdownHook(thread2);
        }
    }
}

