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
    private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier(this){
        final EpollRecvByteAllocatorHandle this$0;
        {
            this.this$0 = epollRecvByteAllocatorHandle;
        }

        @Override
        public boolean get() {
            return this.this$0.maybeMoreDataToRead();
        }
    };
    private boolean isEdgeTriggered;
    private boolean receivedRdHup;

    EpollRecvByteAllocatorHandle(RecvByteBufAllocator.ExtendedHandle extendedHandle) {
        this.delegate = ObjectUtil.checkNotNull(extendedHandle, "handle");
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

    final void edgeTriggered(boolean bl) {
        this.isEdgeTriggered = bl;
    }

    final boolean isEdgeTriggered() {
        return this.isEdgeTriggered;
    }

    @Override
    public final ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
        return this.delegate.allocate(byteBufAllocator);
    }

    @Override
    public final int guess() {
        return this.delegate.guess();
    }

    @Override
    public final void reset(ChannelConfig channelConfig) {
        this.delegate.reset(channelConfig);
    }

    @Override
    public final void incMessagesRead(int n) {
        this.delegate.incMessagesRead(n);
    }

    @Override
    public final void lastBytesRead(int n) {
        this.delegate.lastBytesRead(n);
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
    public final void attemptedBytesRead(int n) {
        this.delegate.attemptedBytesRead(n);
    }

    @Override
    public final void readComplete() {
        this.delegate.readComplete();
    }

    @Override
    public final boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
        return this.delegate.continueReading(uncheckedBooleanSupplier);
    }

    @Override
    public final boolean continueReading() {
        return this.delegate.continueReading(this.defaultMaybeMoreDataSupplier);
    }
}

