/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FixedChannelPool
extends SimpleChannelPool {
    private static final IllegalStateException FULL_EXCEPTION;
    private static final TimeoutException TIMEOUT_EXCEPTION;
    static final IllegalStateException POOL_CLOSED_ON_RELEASE_EXCEPTION;
    static final IllegalStateException POOL_CLOSED_ON_ACQUIRE_EXCEPTION;
    private final EventExecutor executor;
    private final long acquireTimeoutNanos;
    private final Runnable timeoutTask;
    private final Queue<AcquireTask> pendingAcquireQueue = new ArrayDeque<AcquireTask>();
    private final int maxConnections;
    private final int maxPendingAcquires;
    private int acquiredChannelCount;
    private int pendingAcquireCount;
    private boolean closed;
    static final boolean $assertionsDisabled;

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int n) {
        this(bootstrap, channelPoolHandler, n, Integer.MAX_VALUE);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int n, int n2) {
        this(bootstrap, channelPoolHandler, ChannelHealthChecker.ACTIVE, null, -1L, n, n2);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long l, int n, int n2) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, acquireTimeoutAction, l, n, n2, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long l, int n, int n2, boolean bl) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, acquireTimeoutAction, l, n, n2, bl, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long l, int n, int n2, boolean bl, boolean bl2) {
        super(bootstrap, channelPoolHandler, channelHealthChecker, bl, bl2);
        if (n < 1) {
            throw new IllegalArgumentException("maxConnections: " + n + " (expected: >= 1)");
        }
        if (n2 < 1) {
            throw new IllegalArgumentException("maxPendingAcquires: " + n2 + " (expected: >= 1)");
        }
        if (acquireTimeoutAction == null && l == -1L) {
            this.timeoutTask = null;
            this.acquireTimeoutNanos = -1L;
        } else {
            if (acquireTimeoutAction == null && l != -1L) {
                throw new NullPointerException("action");
            }
            if (acquireTimeoutAction != null && l < 0L) {
                throw new IllegalArgumentException("acquireTimeoutMillis: " + l + " (expected: >= 0)");
            }
            this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(l);
            switch (6.$SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction[acquireTimeoutAction.ordinal()]) {
                case 1: {
                    this.timeoutTask = new TimeoutTask(this){
                        final FixedChannelPool this$0;
                        {
                            this.this$0 = fixedChannelPool;
                            super(fixedChannelPool, null);
                        }

                        @Override
                        public void onTimeout(AcquireTask acquireTask) {
                            acquireTask.promise.setFailure(FixedChannelPool.access$100());
                        }
                    };
                    break;
                }
                case 2: {
                    this.timeoutTask = new TimeoutTask(this){
                        final FixedChannelPool this$0;
                        {
                            this.this$0 = fixedChannelPool;
                            super(fixedChannelPool, null);
                        }

                        @Override
                        public void onTimeout(AcquireTask acquireTask) {
                            acquireTask.acquired();
                            FixedChannelPool.access$201(this.this$0, acquireTask.promise);
                        }
                    };
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }
        this.executor = bootstrap.config().group().next();
        this.maxConnections = n;
        this.maxPendingAcquires = n2;
    }

    @Override
    public Future<Channel> acquire(Promise<Channel> promise) {
        try {
            if (this.executor.inEventLoop()) {
                this.acquire0(promise);
            } else {
                this.executor.execute(new Runnable(this, promise){
                    final Promise val$promise;
                    final FixedChannelPool this$0;
                    {
                        this.this$0 = fixedChannelPool;
                        this.val$promise = promise;
                    }

                    @Override
                    public void run() {
                        FixedChannelPool.access$300(this.this$0, this.val$promise);
                    }
                });
            }
        } catch (Throwable throwable) {
            promise.setFailure(throwable);
        }
        return promise;
    }

    private void acquire0(Promise<Channel> promise) {
        if (!$assertionsDisabled && !this.executor.inEventLoop()) {
            throw new AssertionError();
        }
        if (this.closed) {
            promise.setFailure(POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
            return;
        }
        if (this.acquiredChannelCount < this.maxConnections) {
            if (!$assertionsDisabled && this.acquiredChannelCount < 0) {
                throw new AssertionError();
            }
            Promise<Channel> promise2 = this.executor.newPromise();
            AcquireListener acquireListener = new AcquireListener(this, promise);
            acquireListener.acquired();
            promise2.addListener(acquireListener);
            super.acquire(promise2);
        } else {
            if (this.pendingAcquireCount >= this.maxPendingAcquires) {
                promise.setFailure(FULL_EXCEPTION);
            } else {
                AcquireTask acquireTask = new AcquireTask(this, promise);
                if (this.pendingAcquireQueue.offer(acquireTask)) {
                    ++this.pendingAcquireCount;
                    if (this.timeoutTask != null) {
                        acquireTask.timeoutFuture = this.executor.schedule(this.timeoutTask, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
                    }
                } else {
                    promise.setFailure(FULL_EXCEPTION);
                }
            }
            if (!$assertionsDisabled && this.pendingAcquireCount <= 0) {
                throw new AssertionError();
            }
        }
    }

    @Override
    public Future<Void> release(Channel channel, Promise<Void> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        Promise<Void> promise2 = this.executor.newPromise();
        super.release(channel, promise2.addListener(new FutureListener<Void>(this, channel, promise){
            static final boolean $assertionsDisabled = !FixedChannelPool.class.desiredAssertionStatus();
            final Channel val$channel;
            final Promise val$promise;
            final FixedChannelPool this$0;
            {
                this.this$0 = fixedChannelPool;
                this.val$channel = channel;
                this.val$promise = promise;
            }

            @Override
            public void operationComplete(Future<Void> future) throws Exception {
                if (!$assertionsDisabled && !FixedChannelPool.access$400(this.this$0).inEventLoop()) {
                    throw new AssertionError();
                }
                if (FixedChannelPool.access$500(this.this$0)) {
                    this.val$channel.close();
                    this.val$promise.setFailure(POOL_CLOSED_ON_RELEASE_EXCEPTION);
                    return;
                }
                if (future.isSuccess()) {
                    FixedChannelPool.access$600(this.this$0);
                    this.val$promise.setSuccess(null);
                } else {
                    Throwable throwable = future.cause();
                    if (!(throwable instanceof IllegalArgumentException)) {
                        FixedChannelPool.access$600(this.this$0);
                    }
                    this.val$promise.setFailure(future.cause());
                }
            }
        }));
        return promise;
    }

    private void decrementAndRunTaskQueue() {
        --this.acquiredChannelCount;
        if (!$assertionsDisabled && this.acquiredChannelCount < 0) {
            throw new AssertionError();
        }
        this.runTaskQueue();
    }

    private void runTaskQueue() {
        AcquireTask acquireTask;
        while (this.acquiredChannelCount < this.maxConnections && (acquireTask = this.pendingAcquireQueue.poll()) != null) {
            ScheduledFuture<?> scheduledFuture = acquireTask.timeoutFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            --this.pendingAcquireCount;
            acquireTask.acquired();
            super.acquire(acquireTask.promise);
        }
        if (!$assertionsDisabled && this.pendingAcquireCount < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.acquiredChannelCount < 0) {
            throw new AssertionError();
        }
    }

    @Override
    public void close() {
        this.executor.execute(new Runnable(this){
            final FixedChannelPool this$0;
            {
                this.this$0 = fixedChannelPool;
            }

            @Override
            public void run() {
                if (!FixedChannelPool.access$500(this.this$0)) {
                    AcquireTask acquireTask;
                    FixedChannelPool.access$502(this.this$0, true);
                    while ((acquireTask = (AcquireTask)FixedChannelPool.access$800(this.this$0).poll()) != null) {
                        ScheduledFuture<?> scheduledFuture = acquireTask.timeoutFuture;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(false);
                        }
                        acquireTask.promise.setFailure(new ClosedChannelException());
                    }
                    FixedChannelPool.access$1102(this.this$0, 0);
                    FixedChannelPool.access$902(this.this$0, 0);
                    FixedChannelPool.access$1201(this.this$0);
                }
            }
        });
    }

    static TimeoutException access$100() {
        return TIMEOUT_EXCEPTION;
    }

    static Future access$201(FixedChannelPool fixedChannelPool, Promise promise) {
        return super.acquire(promise);
    }

    static void access$300(FixedChannelPool fixedChannelPool, Promise promise) {
        fixedChannelPool.acquire0(promise);
    }

    static EventExecutor access$400(FixedChannelPool fixedChannelPool) {
        return fixedChannelPool.executor;
    }

    static boolean access$500(FixedChannelPool fixedChannelPool) {
        return fixedChannelPool.closed;
    }

    static void access$600(FixedChannelPool fixedChannelPool) {
        fixedChannelPool.decrementAndRunTaskQueue();
    }

    static long access$700(FixedChannelPool fixedChannelPool) {
        return fixedChannelPool.acquireTimeoutNanos;
    }

    static Queue access$800(FixedChannelPool fixedChannelPool) {
        return fixedChannelPool.pendingAcquireQueue;
    }

    static int access$906(FixedChannelPool fixedChannelPool) {
        return --fixedChannelPool.pendingAcquireCount;
    }

    static void access$1000(FixedChannelPool fixedChannelPool) {
        fixedChannelPool.runTaskQueue();
    }

    static int access$1108(FixedChannelPool fixedChannelPool) {
        return fixedChannelPool.acquiredChannelCount++;
    }

    static boolean access$502(FixedChannelPool fixedChannelPool, boolean bl) {
        fixedChannelPool.closed = bl;
        return fixedChannelPool.closed;
    }

    static int access$1102(FixedChannelPool fixedChannelPool, int n) {
        fixedChannelPool.acquiredChannelCount = n;
        return fixedChannelPool.acquiredChannelCount;
    }

    static int access$902(FixedChannelPool fixedChannelPool, int n) {
        fixedChannelPool.pendingAcquireCount = n;
        return fixedChannelPool.pendingAcquireCount;
    }

    static void access$1201(FixedChannelPool fixedChannelPool) {
        super.close();
    }

    static {
        $assertionsDisabled = !FixedChannelPool.class.desiredAssertionStatus();
        FULL_EXCEPTION = ThrowableUtil.unknownStackTrace(new IllegalStateException("Too many outstanding acquire operations"), FixedChannelPool.class, "acquire0(...)");
        TIMEOUT_EXCEPTION = ThrowableUtil.unknownStackTrace(new TimeoutException("Acquire operation took longer then configured maximum time"), FixedChannelPool.class, "<init>(...)");
        POOL_CLOSED_ON_RELEASE_EXCEPTION = ThrowableUtil.unknownStackTrace(new IllegalStateException("FixedChannelPool was closed"), FixedChannelPool.class, "release(...)");
        POOL_CLOSED_ON_ACQUIRE_EXCEPTION = ThrowableUtil.unknownStackTrace(new IllegalStateException("FixedChannelPool was closed"), FixedChannelPool.class, "acquire0(...)");
    }

    private class AcquireListener
    implements FutureListener<Channel> {
        private final Promise<Channel> originalPromise;
        protected boolean acquired;
        static final boolean $assertionsDisabled = !FixedChannelPool.class.desiredAssertionStatus();
        final FixedChannelPool this$0;

        AcquireListener(FixedChannelPool fixedChannelPool, Promise<Channel> promise) {
            this.this$0 = fixedChannelPool;
            this.originalPromise = promise;
        }

        @Override
        public void operationComplete(Future<Channel> future) throws Exception {
            if (!$assertionsDisabled && !FixedChannelPool.access$400(this.this$0).inEventLoop()) {
                throw new AssertionError();
            }
            if (FixedChannelPool.access$500(this.this$0)) {
                if (future.isSuccess()) {
                    future.getNow().close();
                }
                this.originalPromise.setFailure(POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
                return;
            }
            if (future.isSuccess()) {
                this.originalPromise.setSuccess(future.getNow());
            } else {
                if (this.acquired) {
                    FixedChannelPool.access$600(this.this$0);
                } else {
                    FixedChannelPool.access$1000(this.this$0);
                }
                this.originalPromise.setFailure(future.cause());
            }
        }

        public void acquired() {
            if (this.acquired) {
                return;
            }
            FixedChannelPool.access$1108(this.this$0);
            this.acquired = true;
        }
    }

    private abstract class TimeoutTask
    implements Runnable {
        static final boolean $assertionsDisabled = !FixedChannelPool.class.desiredAssertionStatus();
        final FixedChannelPool this$0;

        private TimeoutTask(FixedChannelPool fixedChannelPool) {
            this.this$0 = fixedChannelPool;
        }

        @Override
        public final void run() {
            AcquireTask acquireTask;
            if (!$assertionsDisabled && !FixedChannelPool.access$400(this.this$0).inEventLoop()) {
                throw new AssertionError();
            }
            long l = System.nanoTime();
            while ((acquireTask = (AcquireTask)FixedChannelPool.access$800(this.this$0).peek()) != null && l - acquireTask.expireNanoTime >= 0L) {
                FixedChannelPool.access$800(this.this$0).remove();
                FixedChannelPool.access$906(this.this$0);
                this.onTimeout(acquireTask);
            }
        }

        public abstract void onTimeout(AcquireTask var1);

        TimeoutTask(FixedChannelPool fixedChannelPool, 1 var2_2) {
            this(fixedChannelPool);
        }
    }

    private final class AcquireTask
    extends AcquireListener {
        final Promise<Channel> promise;
        final long expireNanoTime;
        ScheduledFuture<?> timeoutFuture;
        final FixedChannelPool this$0;

        public AcquireTask(FixedChannelPool fixedChannelPool, Promise<Channel> promise) {
            this.this$0 = fixedChannelPool;
            super(fixedChannelPool, promise);
            this.expireNanoTime = System.nanoTime() + FixedChannelPool.access$700(this.this$0);
            this.promise = FixedChannelPool.access$400(fixedChannelPool).newPromise().addListener(this);
        }
    }

    public static enum AcquireTimeoutAction {
        NEW,
        FAIL;

    }
}

