/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.status.StatusLogger;

public class DefaultShutdownCallbackRegistry
implements ShutdownCallbackRegistry,
LifeCycle2,
Runnable {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private final AtomicReference<LifeCycle.State> state = new AtomicReference<LifeCycle.State>(LifeCycle.State.INITIALIZED);
    private final ThreadFactory threadFactory;
    private final Collection<Cancellable> hooks = new CopyOnWriteArrayList<Cancellable>();
    private Reference<Thread> shutdownHookRef;

    public DefaultShutdownCallbackRegistry() {
        this(Executors.defaultThreadFactory());
    }

    protected DefaultShutdownCallbackRegistry(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public void run() {
        if (this.state.compareAndSet(LifeCycle.State.STARTED, LifeCycle.State.STOPPING)) {
            for (Runnable runnable : this.hooks) {
                try {
                    runnable.run();
                } catch (Throwable throwable) {
                    try {
                        LOGGER.error(SHUTDOWN_HOOK_MARKER, "Caught exception executing shutdown hook {}", (Object)runnable, (Object)throwable);
                    } catch (Throwable throwable2) {
                        System.err.println("Caught exception " + throwable2.getClass() + " logging exception " + throwable.getClass());
                        throwable.printStackTrace();
                    }
                }
            }
            this.state.set(LifeCycle.State.STOPPED);
        }
    }

    @Override
    public Cancellable addShutdownCallback(Runnable runnable) {
        if (this.isStarted()) {
            RegisteredCancellable registeredCancellable = new RegisteredCancellable(runnable, this.hooks);
            this.hooks.add(registeredCancellable);
            return registeredCancellable;
        }
        throw new IllegalStateException("Cannot add new shutdown hook as this is not started. Current state: " + this.state.get().name());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void start() {
        if (this.state.compareAndSet(LifeCycle.State.INITIALIZED, LifeCycle.State.STARTING)) {
            try {
                this.addShutdownHook(this.threadFactory.newThread(this));
                this.state.set(LifeCycle.State.STARTED);
            } catch (IllegalStateException illegalStateException) {
                this.state.set(LifeCycle.State.STOPPED);
                throw illegalStateException;
            } catch (Exception exception) {
                LOGGER.catching(exception);
                this.state.set(LifeCycle.State.STOPPED);
            }
        }
    }

    private void addShutdownHook(Thread thread2) {
        this.shutdownHookRef = new WeakReference<Thread>(thread2);
        Runtime.getRuntime().addShutdownHook(thread2);
    }

    @Override
    public void stop() {
        this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        if (this.state.compareAndSet(LifeCycle.State.STARTED, LifeCycle.State.STOPPING)) {
            try {
                this.removeShutdownHook();
            } finally {
                this.state.set(LifeCycle.State.STOPPED);
            }
        }
        return false;
    }

    private void removeShutdownHook() {
        Thread thread2 = this.shutdownHookRef.get();
        if (thread2 != null) {
            Runtime.getRuntime().removeShutdownHook(thread2);
            this.shutdownHookRef.enqueue();
        }
    }

    @Override
    public LifeCycle.State getState() {
        return this.state.get();
    }

    @Override
    public boolean isStarted() {
        return this.state.get() == LifeCycle.State.STARTED;
    }

    @Override
    public boolean isStopped() {
        return this.state.get() == LifeCycle.State.STOPPED;
    }

    private static class RegisteredCancellable
    implements Cancellable {
        private final Reference<Runnable> hook;
        private Collection<Cancellable> registered;

        RegisteredCancellable(Runnable runnable, Collection<Cancellable> collection) {
            this.registered = collection;
            this.hook = new SoftReference<Runnable>(runnable);
        }

        @Override
        public void cancel() {
            this.hook.clear();
            this.registered.remove(this);
            this.registered = null;
        }

        @Override
        public void run() {
            Runnable runnable = this.hook.get();
            if (runnable != null) {
                runnable.run();
                this.hook.clear();
            }
        }

        public String toString() {
            return String.valueOf(this.hook.get());
        }
    }
}

