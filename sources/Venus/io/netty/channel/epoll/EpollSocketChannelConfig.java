/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EpollSocketChannelConfig
extends EpollChannelConfig
implements SocketChannelConfig {
    private final EpollSocketChannel channel;
    private volatile boolean allowHalfClosure;

    EpollSocketChannelConfig(EpollSocketChannel epollSocketChannel) {
        super(epollSocketChannel);
        this.channel = epollSocketChannel;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            this.setTcpNoDelay(false);
        }
        this.calculateMaxBytesPerGatheringWrite();
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, EpollChannelOption.TCP_CORK, EpollChannelOption.TCP_NOTSENT_LOWAT, EpollChannelOption.TCP_KEEPCNT, EpollChannelOption.TCP_KEEPIDLE, EpollChannelOption.TCP_KEEPINTVL, EpollChannelOption.TCP_MD5SIG, EpollChannelOption.TCP_QUICKACK, EpollChannelOption.IP_TRANSPARENT, EpollChannelOption.TCP_FASTOPEN_CONNECT);
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
        if (channelOption == EpollChannelOption.TCP_CORK) {
            return (T)Boolean.valueOf(this.isTcpCork());
        }
        if (channelOption == EpollChannelOption.TCP_NOTSENT_LOWAT) {
            return (T)Long.valueOf(this.getTcpNotSentLowAt());
        }
        if (channelOption == EpollChannelOption.TCP_KEEPIDLE) {
            return (T)Integer.valueOf(this.getTcpKeepIdle());
        }
        if (channelOption == EpollChannelOption.TCP_KEEPINTVL) {
            return (T)Integer.valueOf(this.getTcpKeepIntvl());
        }
        if (channelOption == EpollChannelOption.TCP_KEEPCNT) {
            return (T)Integer.valueOf(this.getTcpKeepCnt());
        }
        if (channelOption == EpollChannelOption.TCP_USER_TIMEOUT) {
            return (T)Integer.valueOf(this.getTcpUserTimeout());
        }
        if (channelOption == EpollChannelOption.TCP_QUICKACK) {
            return (T)Boolean.valueOf(this.isTcpQuickAck());
        }
        if (channelOption == EpollChannelOption.IP_TRANSPARENT) {
            return (T)Boolean.valueOf(this.isIpTransparent());
        }
        if (channelOption == EpollChannelOption.TCP_FASTOPEN_CONNECT) {
            return (T)Boolean.valueOf(this.isTcpFastOpenConnect());
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
        } else if (channelOption == EpollChannelOption.TCP_CORK) {
            this.setTcpCork((Boolean)t);
        } else if (channelOption == EpollChannelOption.TCP_NOTSENT_LOWAT) {
            this.setTcpNotSentLowAt((Long)t);
        } else if (channelOption == EpollChannelOption.TCP_KEEPIDLE) {
            this.setTcpKeepIdle((Integer)t);
        } else if (channelOption == EpollChannelOption.TCP_KEEPCNT) {
            this.setTcpKeepCnt((Integer)t);
        } else if (channelOption == EpollChannelOption.TCP_KEEPINTVL) {
            this.setTcpKeepIntvl((Integer)t);
        } else if (channelOption == EpollChannelOption.TCP_USER_TIMEOUT) {
            this.setTcpUserTimeout((Integer)t);
        } else if (channelOption == EpollChannelOption.IP_TRANSPARENT) {
            this.setIpTransparent((Boolean)t);
        } else if (channelOption == EpollChannelOption.TCP_MD5SIG) {
            Map map = (Map)t;
            this.setTcpMd5Sig(map);
        } else if (channelOption == EpollChannelOption.TCP_QUICKACK) {
            this.setTcpQuickAck((Boolean)t);
        } else if (channelOption == EpollChannelOption.TCP_FASTOPEN_CONNECT) {
            this.setTcpFastOpenConnect((Boolean)t);
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

    public boolean isTcpCork() {
        try {
            return this.channel.socket.isTcpCork();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public long getTcpNotSentLowAt() {
        try {
            return this.channel.socket.getTcpNotSentLowAt();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public int getTcpKeepIdle() {
        try {
            return this.channel.socket.getTcpKeepIdle();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public int getTcpKeepIntvl() {
        try {
            return this.channel.socket.getTcpKeepIntvl();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public int getTcpKeepCnt() {
        try {
            return this.channel.socket.getTcpKeepCnt();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public int getTcpUserTimeout() {
        try {
            return this.channel.socket.getTcpUserTimeout();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setKeepAlive(boolean bl) {
        try {
            this.channel.socket.setKeepAlive(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        return this;
    }

    @Override
    public EpollSocketChannelConfig setReceiveBufferSize(int n) {
        try {
            this.channel.socket.setReceiveBufferSize(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setReuseAddress(boolean bl) {
        try {
            this.channel.socket.setReuseAddress(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setSendBufferSize(int n) {
        try {
            this.channel.socket.setSendBufferSize(n);
            this.calculateMaxBytesPerGatheringWrite();
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setSoLinger(int n) {
        try {
            this.channel.socket.setSoLinger(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setTcpNoDelay(boolean bl) {
        try {
            this.channel.socket.setTcpNoDelay(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpCork(boolean bl) {
        try {
            this.channel.socket.setTcpCork(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpNotSentLowAt(long l) {
        try {
            this.channel.socket.setTcpNotSentLowAt(l);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public EpollSocketChannelConfig setTrafficClass(int n) {
        try {
            this.channel.socket.setTrafficClass(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpKeepIdle(int n) {
        try {
            this.channel.socket.setTcpKeepIdle(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpKeepIntvl(int n) {
        try {
            this.channel.socket.setTcpKeepIntvl(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Deprecated
    public EpollSocketChannelConfig setTcpKeepCntl(int n) {
        return this.setTcpKeepCnt(n);
    }

    public EpollSocketChannelConfig setTcpKeepCnt(int n) {
        try {
            this.channel.socket.setTcpKeepCnt(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpUserTimeout(int n) {
        try {
            this.channel.socket.setTcpUserTimeout(n);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public boolean isIpTransparent() {
        try {
            return this.channel.socket.isIpTransparent();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setIpTransparent(boolean bl) {
        try {
            this.channel.socket.setIpTransparent(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpMd5Sig(Map<InetAddress, byte[]> map) {
        try {
            this.channel.setTcpMd5Sig(map);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpQuickAck(boolean bl) {
        try {
            this.channel.socket.setTcpQuickAck(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public boolean isTcpQuickAck() {
        try {
            return this.channel.socket.isTcpQuickAck();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public EpollSocketChannelConfig setTcpFastOpenConnect(boolean bl) {
        try {
            this.channel.socket.setTcpFastOpenConnect(bl);
            return this;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    public boolean isTcpFastOpenConnect() {
        try {
            return this.channel.socket.isTcpFastOpenConnect();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }

    @Override
    public EpollSocketChannelConfig setAllowHalfClosure(boolean bl) {
        this.allowHalfClosure = bl;
        return this;
    }

    @Override
    public EpollSocketChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public EpollSocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    @Deprecated
    public EpollSocketChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    @Deprecated
    public EpollSocketChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    @Override
    public EpollSocketChannelConfig setEpollMode(EpollMode epollMode) {
        super.setEpollMode(epollMode);
        return this;
    }

    private void calculateMaxBytesPerGatheringWrite() {
        int n = this.getSendBufferSize() << 1;
        if (n > 0) {
            this.setMaxBytesPerGatheringWrite(this.getSendBufferSize() << 1);
        }
    }

    @Override
    public EpollChannelConfig setEpollMode(EpollMode epollMode) {
        return this.setEpollMode(epollMode);
    }

    @Override
    public EpollChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public EpollChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    @Deprecated
    public EpollChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    @Deprecated
    public EpollChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public EpollChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public EpollChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public EpollChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public EpollChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public EpollChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public EpollChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
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

