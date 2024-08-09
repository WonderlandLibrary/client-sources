/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultChannelProgressivePromise
extends DefaultProgressivePromise<Void>
implements ChannelProgressivePromise,
ChannelFlushPromiseNotifier.FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public DefaultChannelProgressivePromise(Channel channel) {
        this.channel = channel;
    }

    public DefaultChannelProgressivePromise(Channel channel, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.channel = channel;
    }

    @Override
    protected EventExecutor executor() {
        EventExecutor eventExecutor = super.executor();
        if (eventExecutor == null) {
            return this.channel().eventLoop();
        }
        return eventExecutor;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public ChannelProgressivePromise setSuccess() {
        return this.setSuccess(null);
    }

    @Override
    public ChannelProgressivePromise setSuccess(Void void_) {
        super.setSuccess(void_);
        return this;
    }

    @Override
    public boolean trySuccess() {
        return this.trySuccess(null);
    }

    @Override
    public ChannelProgressivePromise setFailure(Throwable throwable) {
        super.setFailure(throwable);
        return this;
    }

    @Override
    public ChannelProgressivePromise setProgress(long l, long l2) {
        super.setProgress(l, l2);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        super.addListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        super.removeListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public ChannelProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public ChannelProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public ChannelProgressivePromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public long flushCheckpoint() {
        return this.checkpoint;
    }

    @Override
    public void flushCheckpoint(long l) {
        this.checkpoint = l;
    }

    @Override
    public ChannelProgressivePromise promise() {
        return this;
    }

    @Override
    protected void checkDeadLock() {
        if (this.channel().isRegistered()) {
            super.checkDeadLock();
        }
    }

    @Override
    public ChannelProgressivePromise unvoid() {
        return this;
    }

    @Override
    public boolean isVoid() {
        return true;
    }

    @Override
    public ProgressivePromise setFailure(Throwable throwable) {
        return this.setFailure(throwable);
    }

    @Override
    public ProgressivePromise setSuccess(Object object) {
        return this.setSuccess((Void)object);
    }

    @Override
    public ProgressivePromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ProgressivePromise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ProgressivePromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ProgressivePromise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ProgressivePromise removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ProgressivePromise removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ProgressivePromise addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ProgressivePromise addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public ProgressivePromise setProgress(long l, long l2) {
        return this.setProgress(l, l2);
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
        return this.setSuccess((Void)object);
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

    @Override
    public ChannelProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelProgressiveFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelProgressiveFuture removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelProgressiveFuture removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ChannelProgressiveFuture addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelProgressiveFuture addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelFuture removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelFuture removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ChannelFuture addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelFuture addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public ChannelPromise unvoid() {
        return this.unvoid();
    }

    @Override
    public ChannelPromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelPromise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelPromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelPromise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelPromise removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelPromise removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ChannelPromise addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelPromise addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public ChannelPromise setFailure(Throwable throwable) {
        return this.setFailure(throwable);
    }

    @Override
    public ChannelPromise setSuccess() {
        return this.setSuccess();
    }

    @Override
    public ChannelPromise setSuccess(Void void_) {
        return this.setSuccess(void_);
    }

    @Override
    public ChannelPromise promise() {
        return this.promise();
    }
}

