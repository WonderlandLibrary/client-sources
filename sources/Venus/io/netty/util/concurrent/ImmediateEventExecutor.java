/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FailedFuture;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public final class ImmediateEventExecutor
extends AbstractEventExecutor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new FastThreadLocal<Queue<Runnable>>(){

        @Override
        protected Queue<Runnable> initialValue() throws Exception {
            return new ArrayDeque<Runnable>();
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private static final FastThreadLocal<Boolean> RUNNING = new FastThreadLocal<Boolean>(){

        @Override
        protected Boolean initialValue() throws Exception {
            return false;
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());

    private ImmediateEventExecutor() {
    }

    @Override
    public boolean inEventLoop() {
        return false;
    }

    @Override
    public boolean inEventLoop(Thread thread2) {
        return false;
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    @Deprecated
    public void shutdown() {
    }

    @Override
    public boolean isShuttingDown() {
        return true;
    }

    @Override
    public boolean isShutdown() {
        return true;
    }

    @Override
    public boolean isTerminated() {
        return true;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (!RUNNING.get().booleanValue()) {
            RUNNING.set(true);
            try {
                runnable.run();
            } catch (Throwable throwable) {
                logger.info("Throwable caught while executing Runnable {}", (Object)runnable, (Object)throwable);
            } finally {
                Runnable runnable2;
                Queue<Runnable> queue = DELAYED_RUNNABLES.get();
                while ((runnable2 = queue.poll()) != null) {
                    try {
                        runnable2.run();
                    } catch (Throwable throwable) {
                        logger.info("Throwable caught while executing Runnable {}", (Object)runnable2, (Object)throwable);
                    }
                }
                RUNNING.set(false);
            }
        } else {
            DELAYED_RUNNABLES.get().add(runnable);
        }
    }

    @Override
    public <V> Promise<V> newPromise() {
        return new ImmediatePromise(this);
    }

    @Override
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }

    static class ImmediateProgressivePromise<V>
    extends DefaultProgressivePromise<V> {
        ImmediateProgressivePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }

        @Override
        protected void checkDeadLock() {
        }
    }

    static class ImmediatePromise<V>
    extends DefaultPromise<V> {
        ImmediatePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }

        @Override
        protected void checkDeadLock() {
        }
    }
}

