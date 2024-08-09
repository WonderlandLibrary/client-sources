/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.Limits;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EpollChannelConfig
extends DefaultChannelConfig {
    final AbstractEpollChannel channel;
    private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;

    EpollChannelConfig(AbstractEpollChannel abstractEpollChannel) {
        super(abstractEpollChannel);
        this.channel = abstractEpollChannel;
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), EpollChannelOption.EPOLL_MODE);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == EpollChannelOption.EPOLL_MODE) {
            return (T)((Object)this.getEpollMode());
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption != EpollChannelOption.EPOLL_MODE) {
            return super.setOption(channelOption, t);
        }
        this.setEpollMode((EpollMode)((Object)t));
        return false;
    }

    @Override
    public EpollChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public EpollChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public EpollChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public EpollChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public EpollChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        if (!(recvByteBufAllocator.newHandle() instanceof RecvByteBufAllocator.ExtendedHandle)) {
            throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + RecvByteBufAllocator.ExtendedHandle.class);
        }
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public EpollChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    @Deprecated
    public EpollChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    @Deprecated
    public EpollChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public EpollChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public EpollChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    public EpollMode getEpollMode() {
        return this.channel.isFlagSet(Native.EPOLLET) ? EpollMode.EDGE_TRIGGERED : EpollMode.LEVEL_TRIGGERED;
    }

    public EpollChannelConfig setEpollMode(EpollMode epollMode) {
        if (epollMode == null) {
            throw new NullPointerException("mode");
        }
        try {
            switch (1.$SwitchMap$io$netty$channel$epoll$EpollMode[epollMode.ordinal()]) {
                case 1: {
                    this.checkChannelNotRegistered();
                    this.channel.setFlag(Native.EPOLLET);
                    break;
                }
                case 2: {
                    this.checkChannelNotRegistered();
                    this.channel.clearFlag(Native.EPOLLET);
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    private void checkChannelNotRegistered() {
        if (this.channel.isRegistered()) {
            throw new IllegalStateException("EpollMode can only be changed before channel is registered");
        }
    }

    @Override
    protected final void autoReadCleared() {
        this.channel.clearEpollIn();
    }

    final void setMaxBytesPerGatheringWrite(long l) {
        this.maxBytesPerGatheringWrite = l;
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

