/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.util.concurrent.AbstractFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class VoidChannelPromise
extends AbstractFuture<Void>
implements ChannelPromise {
    private final Channel channel;
    private final ChannelFutureListener fireExceptionListener;

    public VoidChannelPromise(Channel channel, boolean bl) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        this.fireExceptionListener = bl ? new ChannelFutureListener(this){
            final VoidChannelPromise this$0;
            {
                this.this$0 = voidChannelPromise;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable throwable = channelFuture.cause();
                if (throwable != null) {
                    VoidChannelPromise.access$000(this.this$0, throwable);
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        } : null;
    }

    @Override
    public VoidChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        return this;
    }

    @Override
    public VoidChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        return this;
    }

    @Override
    public VoidChannelPromise await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }

    @Override
    public boolean await(long l, TimeUnit timeUnit) {
        VoidChannelPromise.fail();
        return true;
    }

    @Override
    public boolean await(long l) {
        VoidChannelPromise.fail();
        return true;
    }

    @Override
    public VoidChannelPromise awaitUninterruptibly() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public boolean awaitUninterruptibly(long l, TimeUnit timeUnit) {
        VoidChannelPromise.fail();
        return true;
    }

    @Override
    public boolean awaitUninterruptibly(long l) {
        VoidChannelPromise.fail();
        return true;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean setUncancellable() {
        return false;
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return true;
    }

    @Override
    public Throwable cause() {
        return null;
    }

    @Override
    public VoidChannelPromise sync() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise syncUninterruptibly() {
        VoidChannelPromise.fail();
        return this;
    }

    @Override
    public VoidChannelPromise setFailure(Throwable throwable) {
        this.fireException0(throwable);
        return this;
    }

    @Override
    public VoidChannelPromise setSuccess() {
        return this;
    }

    @Override
    public boolean tryFailure(Throwable throwable) {
        this.fireException0(throwable);
        return true;
    }

    @Override
    public boolean cancel(boolean bl) {
        return true;
    }

    @Override
    public boolean trySuccess() {
        return true;
    }

    private static void fail() {
        throw new IllegalStateException("void future");
    }

    @Override
    public VoidChannelPromise setSuccess(Void void_) {
        return this;
    }

    @Override
    public boolean trySuccess(Void void_) {
        return true;
    }

    @Override
    public Void getNow() {
        return null;
    }

    @Override
    public ChannelPromise unvoid() {
        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise(this.channel);
        if (this.fireExceptionListener != null) {
            defaultChannelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>)this.fireExceptionListener);
        }
        return defaultChannelPromise;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    private void fireException0(Throwable throwable) {
        if (this.fireExceptionListener != null && this.channel.isRegistered()) {
            this.channel.pipeline().fireExceptionCaught(throwable);
        }
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

    public ChannelPromise removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    public ChannelPromise removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    public ChannelPromise addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

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

    static void access$000(VoidChannelPromise voidChannelPromise, Throwable throwable) {
        voidChannelPromise.fireException0(throwable);
    }
}

