/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupException;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class VoidChannelGroupFuture
implements ChannelGroupFuture {
    private static final Iterator<ChannelFuture> EMPTY = Collections.emptyList().iterator();
    private final ChannelGroup group;

    VoidChannelGroupFuture(ChannelGroup channelGroup) {
        this.group = channelGroup;
    }

    @Override
    public ChannelGroup group() {
        return this.group;
    }

    @Override
    public ChannelFuture find(Channel channel) {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public ChannelGroupException cause() {
        return null;
    }

    @Override
    public boolean isPartialSuccess() {
        return true;
    }

    @Override
    public boolean isPartialFailure() {
        return true;
    }

    @Override
    public ChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture await() {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture awaitUninterruptibly() {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture syncUninterruptibly() {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public ChannelGroupFuture sync() {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public Iterator<ChannelFuture> iterator() {
        return EMPTY;
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public boolean await(long l, TimeUnit timeUnit) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public boolean await(long l) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public boolean awaitUninterruptibly(long l, TimeUnit timeUnit) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public boolean awaitUninterruptibly(long l) {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public Void getNow() {
        return null;
    }

    @Override
    public boolean cancel(boolean bl) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return true;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public Void get() {
        throw VoidChannelGroupFuture.reject();
    }

    @Override
    public Void get(long l, TimeUnit timeUnit) {
        throw VoidChannelGroupFuture.reject();
    }

    private static RuntimeException reject() {
        return new IllegalStateException("void future");
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
    public Throwable cause() {
        return this.cause();
    }

    @Override
    public Object get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.get(l, timeUnit);
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return this.get();
    }
}

