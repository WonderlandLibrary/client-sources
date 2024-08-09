/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.KQueueChannelOption;
import io.netty.channel.unix.Limits;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KQueueChannelConfig
extends DefaultChannelConfig {
    final AbstractKQueueChannel channel;
    private volatile boolean transportProvidesGuess;
    private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;

    KQueueChannelConfig(AbstractKQueueChannel abstractKQueueChannel) {
        super(abstractKQueueChannel);
        this.channel = abstractKQueueChannel;
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS) {
            return (T)Boolean.valueOf(this.getRcvAllocTransportProvidesGuess());
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption != KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS) {
            return super.setOption(channelOption, t);
        }
        this.setRcvAllocTransportProvidesGuess((Boolean)t);
        return false;
    }

    public KQueueChannelConfig setRcvAllocTransportProvidesGuess(boolean bl) {
        this.transportProvidesGuess = bl;
        return this;
    }

    public boolean getRcvAllocTransportProvidesGuess() {
        return this.transportProvidesGuess;
    }

    @Override
    public KQueueChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public KQueueChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public KQueueChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public KQueueChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        if (!(recvByteBufAllocator.newHandle() instanceof RecvByteBufAllocator.ExtendedHandle)) {
            throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + RecvByteBufAllocator.ExtendedHandle.class);
        }
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public KQueueChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public KQueueChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public KQueueChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    @Override
    protected final void autoReadCleared() {
        this.channel.clearReadFilter();
    }

    final void setMaxBytesPerGatheringWrite(long l) {
        this.maxBytesPerGatheringWrite = Math.min(Limits.SSIZE_MAX, l);
    }

    final long getMaxBytesPerGatheringWrite() {
        return this.maxBytesPerGatheringWrite;
    }

    @Override
    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    @Deprecated
    public ChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    @Deprecated
    public ChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public ChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public ChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public ChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }
}

