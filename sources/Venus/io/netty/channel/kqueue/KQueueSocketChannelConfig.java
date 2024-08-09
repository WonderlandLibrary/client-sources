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
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueChannelOption;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class KQueueSocketChannelConfig
extends KQueueChannelConfig
implements SocketChannelConfig {
    private final KQueueSocketChannel channel;
    private volatile boolean allowHalfClosure;

    KQueueSocketChannelConfig(KQueueSocketChannel kQueueSocketChannel) {
        super(kQueueSocketChannel);
        this.channel = kQueueSocketChannel;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            this.setTcpNoDelay(false);
        }
        this.calculateMaxBytesPerGatheringWrite();
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, KQueueChannelOption.SO_SNDLOWAT, KQueueChannelOption.TCP_NOPUSH);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T)Integer.valueOf(this.getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return (T)Integer.valueOf(this.getSendBufferSize());
        }
        if (channelOption == ChannelOption.TCP_NODELAY) {
            return (T)Boolean.valueOf(this.isTcpNoDelay());
        }
        if (channelOption == ChannelOption.SO_KEEPALIVE) {
            return (T)Boolean.valueOf(this.isKeepAlive());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return (T)Boolean.valueOf(this.isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return (T)Integer.valueOf(this.getSoLinger());
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return (T)Integer.valueOf(this.getTrafficClass());
        }
        if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            return (T)Boolean.valueOf(this.isAllowHalfClosure());
        }
        if (channelOption == KQueueChannelOption.SO_SNDLOWAT) {
            return (T)Integer.valueOf(this.getSndLowAt());
        }
        if (channelOption == KQueueChannelOption.TCP_NOPUSH) {
            return (T)Boolean.valueOf(this.isTcpNoPush());
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.TCP_NODELAY) {
            this.setTcpNoDelay((Boolean)t);
        } else if (channelOption == ChannelOption.SO_KEEPALIVE) {
            this.setKeepAlive((Boolean)t);
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((Boolean)t);
        } else if (channelOption == ChannelOption.SO_LINGER) {
            this.setSoLinger((Integer)t);
        } else if (channelOption == ChannelOption.IP_TOS) {
            this.setTrafficClass((Integer)t);
        } else if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            this.setAllowHalfClosure((Boolean)t);
        } else if (channelOption == KQueueChannelOption.SO_SNDLOWAT) {
            this.setSndLowAt((Integer)t);
        } else if (channelOption == KQueueChannelOption.TCP_NOPUSH) {
            this.setTcpNoPush((Boolean)t);
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
    }

    @Override
    public int getReceiveBufferSize() {
        try {
            return this.channel.socket.getReceiveBufferSize();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public int getSendBufferSize() {
        try {
            return this.channel.socket.getSendBufferSize();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public int getSoLinger() {
        try {
            return this.channel.socket.getSoLinger();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public int getTrafficClass() {
        try {
            return this.channel.socket.getTrafficClass();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public boolean isKeepAlive() {
        try {
            return this.channel.socket.isKeepAlive();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public boolean isReuseAddress() {
        try {
            return this.channel.socket.isReuseAddress();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public boolean isTcpNoDelay() {
        try {
            return this.channel.socket.isTcpNoDelay();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public int getSndLowAt() {
        try {
            return this.channel.socket.getSndLowAt();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public void setSndLowAt(int n) {
        try {
            this.channel.socket.setSndLowAt(n);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public boolean isTcpNoPush() {
        try {
            return this.channel.socket.isTcpNoPush();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public void setTcpNoPush(boolean bl) {
        try {
            this.channel.socket.setTcpNoPush(bl);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setKeepAlive(boolean bl) {
        try {
            this.channel.socket.setKeepAlive(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setReceiveBufferSize(int n) {
        try {
            this.channel.socket.setReceiveBufferSize(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setReuseAddress(boolean bl) {
        try {
            this.channel.socket.setReuseAddress(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setSendBufferSize(int n) {
        try {
            this.channel.socket.setSendBufferSize(n);
            this.calculateMaxBytesPerGatheringWrite();
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setSoLinger(int n) {
        try {
            this.channel.socket.setSoLinger(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setTcpNoDelay(boolean bl) {
        try {
            this.channel.socket.setTcpNoDelay(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public KQueueSocketChannelConfig setTrafficClass(int n) {
        try {
            this.channel.socket.setTrafficClass(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }

    @Override
    public KQueueSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean bl) {
        super.setRcvAllocTransportProvidesGuess(bl);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setAllowHalfClosure(boolean bl) {
        this.allowHalfClosure = bl;
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueSocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    @Deprecated
    public KQueueSocketChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    @Deprecated
    public KQueueSocketChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public KQueueSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    private void calculateMaxBytesPerGatheringWrite() {
        int n = this.getSendBufferSize() << 1;
        if (n > 0) {
            this.setMaxBytesPerGatheringWrite(this.getSendBufferSize() << 1);
        }
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
    public ChannelConfig setAutoClose(boolean bl) {
        return this.setAutoClose(bl);
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
    public SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public SocketChannelConfig setAutoClose(boolean bl) {
        return this.setAutoClose(bl);
    }

    @Override
    public SocketChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public SocketChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public SocketChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public SocketChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public SocketChannelConfig setAllowHalfClosure(boolean bl) {
        return this.setAllowHalfClosure(bl);
    }

    @Override
    public SocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }

    @Override
    public SocketChannelConfig setReuseAddress(boolean bl) {
        return this.setReuseAddress(bl);
    }

    @Override
    public SocketChannelConfig setTrafficClass(int n) {
        return this.setTrafficClass(n);
    }

    @Override
    public SocketChannelConfig setKeepAlive(boolean bl) {
        return this.setKeepAlive(bl);
    }

    @Override
    public SocketChannelConfig setReceiveBufferSize(int n) {
        return this.setReceiveBufferSize(n);
    }

    @Override
    public SocketChannelConfig setSendBufferSize(int n) {
        return this.setSendBufferSize(n);
    }

    @Override
    public SocketChannelConfig setSoLinger(int n) {
        return this.setSoLinger(n);
    }

    @Override
    public SocketChannelConfig setTcpNoDelay(boolean bl) {
        return this.setTcpNoDelay(bl);
    }
}

