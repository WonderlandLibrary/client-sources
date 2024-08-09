/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.kqueue.AcceptFilter;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueChannelOption;
import io.netty.channel.kqueue.KQueueServerChannelConfig;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.unix.UnixChannelOption;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KQueueServerSocketChannelConfig
extends KQueueServerChannelConfig
implements ServerSocketChannelConfig {
    KQueueServerSocketChannelConfig(KQueueServerSocketChannel kQueueServerSocketChannel) {
        super(kQueueServerSocketChannel);
        this.setReuseAddress(false);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), UnixChannelOption.SO_REUSEPORT, KQueueChannelOption.SO_ACCEPTFILTER);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == UnixChannelOption.SO_REUSEPORT) {
            return (T)Boolean.valueOf(this.isReusePort());
        }
        if (channelOption == KQueueChannelOption.SO_ACCEPTFILTER) {
            return (T)this.getAcceptFilter();
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption == UnixChannelOption.SO_REUSEPORT) {
            this.setReusePort((Boolean)t);
        } else if (channelOption == KQueueChannelOption.SO_ACCEPTFILTER) {
            this.setAcceptFilter((AcceptFilter)t);
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
    }

    public KQueueServerSocketChannelConfig setReusePort(boolean bl) {
        try {
            this.channel.socket.setReusePort(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public boolean isReusePort() {
        try {
            return this.channel.socket.isReusePort();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public KQueueServerSocketChannelConfig setAcceptFilter(AcceptFilter acceptFilter) {
        try {
            this.channel.socket.setAcceptFilter(acceptFilter);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public AcceptFilter getAcceptFilter() {
        try {
            return this.channel.socket.getAcceptFilter();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueServerSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean bl) {
        super.setRcvAllocTransportProvidesGuess(bl);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setReuseAddress(boolean bl) {
        super.setReuseAddress(bl);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setReceiveBufferSize(int n) {
        super.setReceiveBufferSize(n);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setBacklog(int n) {
        super.setBacklog(n);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueServerSocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    @Deprecated
    public KQueueServerSocketChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueServerSocketChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public KQueueServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    @Override
    public KQueueServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public KQueueServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    @Deprecated
    public KQueueServerChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    @Deprecated
    public KQueueServerChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public KQueueServerChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public KQueueServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public KQueueServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public KQueueServerChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public KQueueServerChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public KQueueServerChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public KQueueServerChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }

    @Override
    public KQueueServerChannelConfig setRcvAllocTransportProvidesGuess(boolean bl) {
        return this.setRcvAllocTransportProvidesGuess(bl);
    }

    @Override
    public KQueueServerChannelConfig setBacklog(int n) {
        return this.setBacklog(n);
    }

    @Override
    public KQueueServerChannelConfig setReceiveBufferSize(int n) {
        return this.setReceiveBufferSize(n);
    }

    @Override
    public KQueueServerChannelConfig setReuseAddress(boolean bl) {
        return this.setReuseAddress(bl);
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    @Deprecated
    public ServerSocketChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    @Deprecated
    public ServerSocketChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public ServerSocketChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public ServerSocketChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public ServerSocketChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public ServerSocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }

    @Override
    public ServerSocketChannelConfig setReceiveBufferSize(int n) {
        return this.setReceiveBufferSize(n);
    }

    @Override
    public ServerSocketChannelConfig setReuseAddress(boolean bl) {
        return this.setReuseAddress(bl);
    }

    @Override
    public ServerSocketChannelConfig setBacklog(int n) {
        return this.setBacklog(n);
    }

    @Override
    public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
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

    @Override
    public KQueueChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public KQueueChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public KQueueChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public KQueueChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public KQueueChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public KQueueChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public KQueueChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public KQueueChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public KQueueChannelConfig setRcvAllocTransportProvidesGuess(boolean bl) {
        return this.setRcvAllocTransportProvidesGuess(bl);
    }
}

