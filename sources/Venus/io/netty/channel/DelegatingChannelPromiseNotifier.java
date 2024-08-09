/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.VoidChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DelegatingChannelPromiseNotifier
implements ChannelPromise,
ChannelFutureListener {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DelegatingChannelPromiseNotifier.class);
    private final ChannelPromise delegate;
    private final boolean logNotifyFailure;

    public DelegatingChannelPromiseNotifier(ChannelPromise channelPromise) {
        this(channelPromise, !(channelPromise instanceof VoidChannelPromise));
    }

    public DelegatingChannelPromiseNotifier(ChannelPromise channelPromise, boolean bl) {
        this.delegate = ObjectUtil.checkNotNull(channelPromise, "delegate");
        this.logNotifyFailure = bl;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        InternalLogger internalLogger;
        InternalLogger internalLogger2 = internalLogger = this.logNotifyFailure ? logger : null;
        if (channelFuture.isSuccess()) {
            Void void_ = (Void)channelFuture.get();
            PromiseNotificationUtil.trySuccess(this.delegate, void_, internalLogger);
        } else if (channelFuture.isCancelled()) {
            PromiseNotificationUtil.tryCancel(this.delegate, internalLogger);
        } else {
            Throwable throwable = channelFuture.cause();
            PromiseNotificationUtil.tryFailure(this.delegate, throwable, internalLogger);
        }
    }

    @Override
    public Channel channel() {
        return this.delegate.channel();
    }

    @Override
    public ChannelPromise setSuccess(Void void_) {
        this.delegate.setSuccess(void_);
        return this;
    }

    @Override
    public ChannelPromise setSuccess() {
        this.delegate.setSuccess();
        return this;
    }

    @Override
    public boolean trySuccess() {
        return this.delegate.trySuccess();
    }

    @Override
    public boolean trySuccess(Void void_) {
        return this.delegate.trySuccess(void_);
    }

    @Override
    public ChannelPromise setFailure(Throwable throwable) {
        this.delegate.setFailure(throwable);
        return this;
    }

    @Override
    public ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        this.delegate.addListener(genericFutureListener);
        return this;
    }

    @Override
    public ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        this.delegate.addListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        this.delegate.removeListener(genericFutureListener);
        return this;
    }

    @Override
    public ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        this.delegate.removeListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public boolean tryFailure(Throwable throwable) {
        return this.delegate.tryFailure(throwable);
    }

    @Override
    public boolean setUncancellable() {
        return this.delegate.setUncancellable();
    }

    @Override
    public ChannelPromise await() throws InterruptedException {
        this.delegate.await();
        return this;
    }

    @Override
    public ChannelPromise awaitUninterruptibly() {
        this.delegate.awaitUninterruptibly();
        return this;
    }

    @Override
    public boolean isVoid() {
        return this.delegate.isVoid();
    }

    @Override
    public ChannelPromise unvoid() {
        return this.isVoid() ? new DelegatingChannelPromiseNotifier(this.delegate.unvoid()) : this;
    }

    @Override
    public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.await(l, timeUnit);
    }

    @Override
    public boolean await(long l) throws InterruptedException {
        return this.delegate.await(l);
    }

    @Override
    public boolean awaitUninterruptibly(long l, TimeUnit timeUnit) {
        return this.delegate.awaitUninterruptibly(l, timeUnit);
    }

    @Override
    public boolean awaitUninterruptibly(long l) {
        return this.delegate.awaitUninterruptibly(l);
    }

    @Override
    public Void getNow() {
        return (Void)this.delegate.getNow();
    }

    @Override
    public boolean cancel(boolean bl) {
        return this.delegate.cancel(bl);
    }

    @Override
    public boolean isCancelled() {
        return this.delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.delegate.isDone();
    }

    @Override
    public Void get() throws InterruptedException, ExecutionException {
        return (Void)this.delegate.get();
    }

    @Override
    public Void get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (Void)this.delegate.get(l, timeUnit);
    }

    @Override
    public ChannelPromise sync() throws InterruptedException {
        this.delegate.sync();
        return this;
    }

    @Override
    public ChannelPromise syncUninterruptibly() {
        this.delegate.syncUninterruptibly();
        return this;
    }

    @Override
    public boolean isSuccess() {
        return this.delegate.isSuccess();
    }

    @Override
    public boolean isCancellable() {
        return this.delegate.isCancellable();
    }

    @Override
    public Throwable cause() {
        return this.delegate.cause();
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

    public ChannelFuture removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    public ChannelFuture removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    public ChannelFuture addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    public ChannelFuture addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public Object getNow() {
        return this.getNow();
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
    public Object get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.get(l, timeUnit);
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return this.get();
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
    public boolean trySuccess(Object object) {
        return this.trySuccess((Void)object);
    }

    @Override
    public Promise setSuccess(Object object) {
        return this.setSuccess((Void)object);
    }

    @Override
    public void operationComplete(Future future) throws Exception {
        this.operationComplete((ChannelFuture)future);
    }
}

