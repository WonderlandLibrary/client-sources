/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultProgressivePromise<V>
extends DefaultPromise<V>
implements ProgressivePromise<V> {
    public DefaultProgressivePromise(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    protected DefaultProgressivePromise() {
    }

    @Override
    public ProgressivePromise<V> setProgress(long l, long l2) {
        if (l2 < 0L) {
            l2 = -1L;
            if (l < 0L) {
                throw new IllegalArgumentException("progress: " + l + " (expected: >= 0)");
            }
        } else if (l < 0L || l > l2) {
            throw new IllegalArgumentException("progress: " + l + " (expected: 0 <= progress <= total (" + l2 + "))");
        }
        if (this.isDone()) {
            throw new IllegalStateException("complete already");
        }
        this.notifyProgressiveListeners(l, l2);
        return this;
    }

    @Override
    public boolean tryProgress(long l, long l2) {
        if (l2 < 0L) {
            l2 = -1L;
            if (l < 0L || this.isDone()) {
                return true;
            }
        } else if (l < 0L || l > l2 || this.isDone()) {
            return true;
        }
        this.notifyProgressiveListeners(l, l2);
        return false;
    }

    @Override
    public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.addListener((GenericFutureListener)genericFutureListener);
        return this;
    }

    @Override
    public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>> ... genericFutureListenerArray) {
        super.addListeners((GenericFutureListener[])genericFutureListenerArray);
        return this;
    }

    @Override
    public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.removeListener((GenericFutureListener)genericFutureListener);
        return this;
    }

    @Override
    public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>> ... genericFutureListenerArray) {
        super.removeListeners((GenericFutureListener[])genericFutureListenerArray);
        return this;
    }

    @Override
    public ProgressivePromise<V> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ProgressivePromise<V> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public ProgressivePromise<V> await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public ProgressivePromise<V> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public ProgressivePromise<V> setSuccess(V v) {
        super.setSuccess(v);
        return this;
    }

    @Override
    public ProgressivePromise<V> setFailure(Throwable throwable) {
        super.setFailure(throwable);
        return this;
    }

    @Override
    public Promise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Promise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Promise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Promise removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public Promise removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public Promise addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public Promise addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public Promise setFailure(Throwable throwable) {
        return this.setFailure(throwable);
    }

    @Override
    public Promise setSuccess(Object object) {
        return this.setSuccess(object);
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

    @Override
    public ProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ProgressiveFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ProgressiveFuture removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ProgressiveFuture removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ProgressiveFuture addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ProgressiveFuture addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
}

