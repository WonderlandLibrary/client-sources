/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractFuture;
import io.netty.util.concurrent.BlockingOperationException;
import io.netty.util.concurrent.DefaultFutureListeners;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GenericProgressiveFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultPromise<V>
extends AbstractFuture<V>
implements Promise<V> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
    private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
    private static final Object SUCCESS = new Object();
    private static final Object UNCANCELLABLE = new Object();
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)"));
    private volatile Object result;
    private final EventExecutor executor;
    private Object listeners;
    private short waiters;
    private boolean notifyingListeners;

    public DefaultPromise(EventExecutor eventExecutor) {
        this.executor = ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    protected DefaultPromise() {
        this.executor = null;
    }

    @Override
    public Promise<V> setSuccess(V v) {
        if (this.setSuccess0(v)) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    @Override
    public boolean trySuccess(V v) {
        if (this.setSuccess0(v)) {
            this.notifyListeners();
            return false;
        }
        return true;
    }

    @Override
    public Promise<V> setFailure(Throwable throwable) {
        if (this.setFailure0(throwable)) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, throwable);
    }

    @Override
    public boolean tryFailure(Throwable throwable) {
        if (this.setFailure0(throwable)) {
            this.notifyListeners();
            return false;
        }
        return true;
    }

    @Override
    public boolean setUncancellable() {
        if (RESULT_UPDATER.compareAndSet(this, null, UNCANCELLABLE)) {
            return false;
        }
        Object object = this.result;
        return !DefaultPromise.isDone0(object) || !DefaultPromise.isCancelled0(object);
    }

    @Override
    public boolean isSuccess() {
        Object object = this.result;
        return object != null && object != UNCANCELLABLE && !(object instanceof CauseHolder);
    }

    @Override
    public boolean isCancellable() {
        return this.result == null;
    }

    @Override
    public Throwable cause() {
        Object object = this.result;
        return object instanceof CauseHolder ? ((CauseHolder)object).cause : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            this.addListener0(genericFutureListener);
        }
        if (this.isDone()) {
            this.notifyListeners();
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>> ... genericFutureListenerArray) {
        ObjectUtil.checkNotNull(genericFutureListenerArray, "listeners");
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArray) {
                if (genericFutureListener == null) break;
                this.addListener0(genericFutureListener);
            }
        }
        if (this.isDone()) {
            this.notifyListeners();
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            this.removeListener0(genericFutureListener);
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>> ... genericFutureListenerArray) {
        ObjectUtil.checkNotNull(genericFutureListenerArray, "listeners");
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArray) {
                if (genericFutureListener == null) break;
                this.removeListener0(genericFutureListener);
            }
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> await() throws InterruptedException {
        if (this.isDone()) {
            return this;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException(this.toString());
        }
        this.checkDeadLock();
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            while (!this.isDone()) {
                this.incWaiters();
                try {
                    this.wait();
                } finally {
                    this.decWaiters();
                }
            }
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Promise<V> awaitUninterruptibly() {
        if (this.isDone()) {
            return this;
        }
        this.checkDeadLock();
        boolean bl = false;
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            while (!this.isDone()) {
                this.incWaiters();
                try {
                    this.wait();
                } catch (InterruptedException interruptedException) {
                    bl = true;
                } finally {
                    this.decWaiters();
                }
            }
        }
        if (bl) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override
    public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.await0(timeUnit.toNanos(l), true);
    }

    @Override
    public boolean await(long l) throws InterruptedException {
        return this.await0(TimeUnit.MILLISECONDS.toNanos(l), true);
    }

    @Override
    public boolean awaitUninterruptibly(long l, TimeUnit timeUnit) {
        try {
            return this.await0(timeUnit.toNanos(l), false);
        } catch (InterruptedException interruptedException) {
            throw new InternalError();
        }
    }

    @Override
    public boolean awaitUninterruptibly(long l) {
        try {
            return this.await0(TimeUnit.MILLISECONDS.toNanos(l), false);
        } catch (InterruptedException interruptedException) {
            throw new InternalError();
        }
    }

    @Override
    public V getNow() {
        Object object = this.result;
        if (object instanceof CauseHolder || object == SUCCESS) {
            return null;
        }
        return (V)object;
    }

    @Override
    public boolean cancel(boolean bl) {
        if (RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
            this.checkNotifyWaiters();
            this.notifyListeners();
            return false;
        }
        return true;
    }

    @Override
    public boolean isCancelled() {
        return DefaultPromise.isCancelled0(this.result);
    }

    @Override
    public boolean isDone() {
        return DefaultPromise.isDone0(this.result);
    }

    @Override
    public Promise<V> sync() throws InterruptedException {
        this.await();
        this.rethrowIfFailed();
        return this;
    }

    @Override
    public Promise<V> syncUninterruptibly() {
        this.awaitUninterruptibly();
        this.rethrowIfFailed();
        return this;
    }

    public String toString() {
        return this.toStringBuilder().toString();
    }

    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('@').append(Integer.toHexString(this.hashCode()));
        Object object = this.result;
        if (object == SUCCESS) {
            stringBuilder.append("(success)");
        } else if (object == UNCANCELLABLE) {
            stringBuilder.append("(uncancellable)");
        } else if (object instanceof CauseHolder) {
            stringBuilder.append("(failure: ").append(((CauseHolder)object).cause).append(')');
        } else if (object != null) {
            stringBuilder.append("(success: ").append(object).append(')');
        } else {
            stringBuilder.append("(incomplete)");
        }
        return stringBuilder;
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    protected void checkDeadLock() {
        EventExecutor eventExecutor = this.executor();
        if (eventExecutor != null && eventExecutor.inEventLoop()) {
            throw new BlockingOperationException(this.toString());
        }
    }

    protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> genericFutureListener) {
        ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
        ObjectUtil.checkNotNull(future, "future");
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        DefaultPromise.notifyListenerWithStackOverFlowProtection(eventExecutor, future, genericFutureListener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notifyListeners() {
        InternalThreadLocalMap internalThreadLocalMap;
        int n;
        EventExecutor eventExecutor = this.executor();
        if (eventExecutor.inEventLoop() && (n = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) < MAX_LISTENER_STACK_DEPTH) {
            internalThreadLocalMap.setFutureListenerStackDepth(n + 1);
            try {
                this.notifyListenersNow();
            } finally {
                internalThreadLocalMap.setFutureListenerStackDepth(n);
            }
            return;
        }
        DefaultPromise.safeExecute(eventExecutor, new Runnable(this){
            final DefaultPromise this$0;
            {
                this.this$0 = defaultPromise;
            }

            @Override
            public void run() {
                DefaultPromise.access$000(this.this$0);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void notifyListenerWithStackOverFlowProtection(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> genericFutureListener) {
        InternalThreadLocalMap internalThreadLocalMap;
        int n;
        if (eventExecutor.inEventLoop() && (n = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) < MAX_LISTENER_STACK_DEPTH) {
            internalThreadLocalMap.setFutureListenerStackDepth(n + 1);
            try {
                DefaultPromise.notifyListener0(future, genericFutureListener);
            } finally {
                internalThreadLocalMap.setFutureListenerStackDepth(n);
            }
            return;
        }
        DefaultPromise.safeExecute(eventExecutor, new Runnable(future, genericFutureListener){
            final Future val$future;
            final GenericFutureListener val$listener;
            {
                this.val$future = future;
                this.val$listener = genericFutureListener;
            }

            @Override
            public void run() {
                DefaultPromise.access$100(this.val$future, this.val$listener);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notifyListenersNow() {
        Object object;
        DefaultPromise defaultPromise = this;
        synchronized (defaultPromise) {
            if (this.notifyingListeners || this.listeners == null) {
                return;
            }
            this.notifyingListeners = true;
            object = this.listeners;
            this.listeners = null;
        }
        while (true) {
            if (object instanceof DefaultFutureListeners) {
                this.notifyListeners0((DefaultFutureListeners)object);
            } else {
                DefaultPromise.notifyListener0(this, (GenericFutureListener)object);
            }
            defaultPromise = this;
            synchronized (defaultPromise) {
                if (this.listeners == null) {
                    this.notifyingListeners = false;
                    return;
                }
                object = this.listeners;
                this.listeners = null;
            }
        }
    }

    private void notifyListeners0(DefaultFutureListeners defaultFutureListeners) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArray = defaultFutureListeners.listeners();
        int n = defaultFutureListeners.size();
        for (int i = 0; i < n; ++i) {
            DefaultPromise.notifyListener0(this, genericFutureListenerArray[i]);
        }
    }

    private static void notifyListener0(Future future, GenericFutureListener genericFutureListener) {
        try {
            genericFutureListener.operationComplete(future);
        } catch (Throwable throwable) {
            logger.warn("An exception was thrown by " + genericFutureListener.getClass().getName() + ".operationComplete()", throwable);
        }
    }

    private void addListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        if (this.listeners == null) {
            this.listeners = genericFutureListener;
        } else if (this.listeners instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners)this.listeners).add(genericFutureListener);
        } else {
            this.listeners = new DefaultFutureListeners((GenericFutureListener)this.listeners, genericFutureListener);
        }
    }

    private void removeListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        if (this.listeners instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners)this.listeners).remove(genericFutureListener);
        } else if (this.listeners == genericFutureListener) {
            this.listeners = null;
        }
    }

    private boolean setSuccess0(V v) {
        return this.setValue0(v == null ? SUCCESS : v);
    }

    private boolean setFailure0(Throwable throwable) {
        return this.setValue0(new CauseHolder(ObjectUtil.checkNotNull(throwable, "cause")));
    }

    private boolean setValue0(Object object) {
        if (RESULT_UPDATER.compareAndSet(this, null, object) || RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, object)) {
            this.checkNotifyWaiters();
            return false;
        }
        return true;
    }

    private synchronized void checkNotifyWaiters() {
        if (this.waiters > 0) {
            this.notifyAll();
        }
    }

    private void incWaiters() {
        if (this.waiters == Short.MAX_VALUE) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        this.waiters = (short)(this.waiters + 1);
    }

    private void decWaiters() {
        this.waiters = (short)(this.waiters - 1);
    }

    private void rethrowIfFailed() {
        Throwable throwable = this.cause();
        if (throwable == null) {
            return;
        }
        PlatformDependent.throwException(throwable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean await0(long l, boolean bl) throws InterruptedException {
        if (this.isDone()) {
            return false;
        }
        if (l <= 0L) {
            return this.isDone();
        }
        if (bl && Thread.interrupted()) {
            throw new InterruptedException(this.toString());
        }
        this.checkDeadLock();
        long l2 = System.nanoTime();
        long l3 = l;
        boolean bl2 = false;
        try {
            do {
                DefaultPromise defaultPromise = this;
                synchronized (defaultPromise) {
                    block20: {
                        if (!this.isDone()) break block20;
                        boolean bl3 = true;
                        return bl3;
                    }
                    this.incWaiters();
                    try {
                        this.wait(l3 / 1000000L, (int)(l3 % 1000000L));
                    } catch (InterruptedException interruptedException) {
                        if (bl) {
                            throw interruptedException;
                        }
                        bl2 = true;
                    } finally {
                        this.decWaiters();
                    }
                }
                if (!this.isDone()) continue;
                boolean bl4 = true;
                return bl4;
            } while ((l3 = l - (System.nanoTime() - l2)) > 0L);
            boolean bl5 = this.isDone();
            return bl5;
        } finally {
            if (bl2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void notifyProgressiveListeners(long l, long l2) {
        Object object = this.progressiveListeners();
        if (object == null) {
            return;
        }
        ProgressiveFuture progressiveFuture = (ProgressiveFuture)((Object)this);
        EventExecutor eventExecutor = this.executor();
        if (eventExecutor.inEventLoop()) {
            if (object instanceof GenericProgressiveFutureListener[]) {
                DefaultPromise.notifyProgressiveListeners0(progressiveFuture, (GenericProgressiveFutureListener[])object, l, l2);
            } else {
                DefaultPromise.notifyProgressiveListener0(progressiveFuture, (GenericProgressiveFutureListener)object, l, l2);
            }
        } else if (object instanceof GenericProgressiveFutureListener[]) {
            GenericProgressiveFutureListener[] genericProgressiveFutureListenerArray = (GenericProgressiveFutureListener[])object;
            DefaultPromise.safeExecute(eventExecutor, new Runnable(this, progressiveFuture, genericProgressiveFutureListenerArray, l, l2){
                final ProgressiveFuture val$self;
                final GenericProgressiveFutureListener[] val$array;
                final long val$progress;
                final long val$total;
                final DefaultPromise this$0;
                {
                    this.this$0 = defaultPromise;
                    this.val$self = progressiveFuture;
                    this.val$array = genericProgressiveFutureListenerArray;
                    this.val$progress = l;
                    this.val$total = l2;
                }

                @Override
                public void run() {
                    DefaultPromise.access$200(this.val$self, this.val$array, this.val$progress, this.val$total);
                }
            });
        } else {
            GenericProgressiveFutureListener genericProgressiveFutureListener = (GenericProgressiveFutureListener)object;
            DefaultPromise.safeExecute(eventExecutor, new Runnable(this, progressiveFuture, genericProgressiveFutureListener, l, l2){
                final ProgressiveFuture val$self;
                final GenericProgressiveFutureListener val$l;
                final long val$progress;
                final long val$total;
                final DefaultPromise this$0;
                {
                    this.this$0 = defaultPromise;
                    this.val$self = progressiveFuture;
                    this.val$l = genericProgressiveFutureListener;
                    this.val$progress = l;
                    this.val$total = l2;
                }

                @Override
                public void run() {
                    DefaultPromise.access$300(this.val$self, this.val$l, this.val$progress, this.val$total);
                }
            });
        }
    }

    /*
     * WARNING - void declaration
     */
    private synchronized Object progressiveListeners() {
        Object object = this.listeners;
        if (object == null) {
            return null;
        }
        if (object instanceof DefaultFutureListeners) {
            void var7_12;
            DefaultFutureListeners defaultFutureListeners = (DefaultFutureListeners)object;
            int n = defaultFutureListeners.progressiveSize();
            switch (n) {
                case 0: {
                    return null;
                }
                case 1: {
                    for (GenericFutureListener<Future<?>> genericFutureListener : defaultFutureListeners.listeners()) {
                        if (!(genericFutureListener instanceof GenericProgressiveFutureListener)) continue;
                        return genericFutureListener;
                    }
                    return null;
                }
            }
            GenericFutureListener<? extends Future<?>>[] genericFutureListenerArray = defaultFutureListeners.listeners();
            GenericProgressiveFutureListener[] genericProgressiveFutureListenerArray = new GenericProgressiveFutureListener[n];
            int n2 = 0;
            boolean n3 = false;
            while (var7_12 < n) {
                GenericFutureListener<Future<?>> genericFutureListener = genericFutureListenerArray[n2];
                if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                    genericProgressiveFutureListenerArray[++var7_12] = (GenericProgressiveFutureListener)genericFutureListener;
                }
                ++n2;
            }
            return genericProgressiveFutureListenerArray;
        }
        if (object instanceof GenericProgressiveFutureListener) {
            return object;
        }
        return null;
    }

    private static void notifyProgressiveListeners0(ProgressiveFuture<?> progressiveFuture, GenericProgressiveFutureListener<?>[] genericProgressiveFutureListenerArray, long l, long l2) {
        for (GenericProgressiveFutureListener<?> genericProgressiveFutureListener : genericProgressiveFutureListenerArray) {
            if (genericProgressiveFutureListener == null) break;
            DefaultPromise.notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, l, l2);
        }
    }

    private static void notifyProgressiveListener0(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener genericProgressiveFutureListener, long l, long l2) {
        try {
            genericProgressiveFutureListener.operationProgressed(progressiveFuture, l, l2);
        } catch (Throwable throwable) {
            logger.warn("An exception was thrown by " + genericProgressiveFutureListener.getClass().getName() + ".operationProgressed()", throwable);
        }
    }

    private static boolean isCancelled0(Object object) {
        return object instanceof CauseHolder && ((CauseHolder)object).cause instanceof CancellationException;
    }

    private static boolean isDone0(Object object) {
        return object != null && object != UNCANCELLABLE;
    }

    private static void safeExecute(EventExecutor eventExecutor, Runnable runnable) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable throwable) {
            rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", throwable);
        }
    }

    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Future removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public Future removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public Future addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public Future addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    static void access$000(DefaultPromise defaultPromise) {
        defaultPromise.notifyListenersNow();
    }

    static void access$100(Future future, GenericFutureListener genericFutureListener) {
        DefaultPromise.notifyListener0(future, genericFutureListener);
    }

    static void access$200(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener[] genericProgressiveFutureListenerArray, long l, long l2) {
        DefaultPromise.notifyProgressiveListeners0(progressiveFuture, genericProgressiveFutureListenerArray, l, l2);
    }

    static void access$300(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener genericProgressiveFutureListener, long l, long l2) {
        DefaultPromise.notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, l, l2);
    }

    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable throwable) {
            this.cause = throwable;
        }
    }
}

