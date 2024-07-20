/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.UncheckedBooleanSupplier;
import io.netty.util.internal.ObjectUtil;

class EpollRecvByteAllocatorHandle
implements RecvByteBufAllocator.ExtendedHandle {
    private final RecvByteBufAllocator.ExtendedHandle delegate;
    private boolean isEdgeTriggered;
    private boolean receivedRdHup;
    private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier(){

        @Override
        public boolean get() {
            return EpollRecvByteAllocatorHandle.this.maybeMoreDataToRead();
        }
    };

    EpollRecvByteAllocatorHandle(RecvByteBufAllocator.ExtendedHandle handle) {
        this.delegate = ObjectUtil.checkNotNull(handle, "handle");
    }

    final void receivedRdHup() {
        this.receivedRdHup = true;
    }

    final boolean isReceivedRdHup() {
        return this.receivedRdHup;
    }

    boolean maybeMoreDataToRead() {
        return this.isEdgeTriggered && this.lastBytesRead() > 0 || !this.isEdgeTriggered && this.lastBytesRead() == this.attemptedBytesRead() || this.receivedRdHup;
    }

    final void edgeTriggered(boolean edgeTriggered) {
        this.isEdgeTriggered = edgeTriggered;
    }

    final boolean isEdgeTriggered() {
        return this.isEdgeTriggered;
    }

    @Override
    public final ByteBuf allocate(ByteBufAllocator alloc) {
        return this.delegate.allocate(alloc);
    }

    @Override
    public final int guess() {
        return this.delegate.guess();
    }

    @Override
    public final void reset(ChannelConfig config) {
        this.delegate.reset(config);
    }

    @Override
    public final void incMessagesRead(int numMessages) {
        this.delegate.incMessagesRead(numMessages);
    }

    @Override
    public final void lastBytesRead(int bytes) {
        this.delegate.lastBytesRead(bytes);
    }

    @Override
    public final int lastBytesRead() {
        return this.delegate.lastBytesRead();
    }

    @Override
    public final int attemptedBytesRead() {
        return this.delegate.attemptedBytesRead();
    }

    @Override
    public final void attemptedBytesRead(int bytes) {
        this.delegate.attemptedBytesRead(bytes);
    }

    @Override
    public final void readComplete() {
        this.delegate.readComplete();
    }

    @Override
    public final boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
        return this.delegate.continueReading(maybeMoreDataSupplier);
    }

    @Override
    public final boolean continueReading() {
        return this.delegate.continueReading(this.defaultMaybeMoreDataSupplier);
    }
}

