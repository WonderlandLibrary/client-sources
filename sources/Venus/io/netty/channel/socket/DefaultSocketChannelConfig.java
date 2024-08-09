/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSocketChannelConfig
extends DefaultChannelConfig
implements SocketChannelConfig {
    protected final Socket javaSocket;
    private volatile boolean allowHalfClosure;

    public DefaultSocketChannelConfig(SocketChannel socketChannel, Socket socket) {
        super(socketChannel);
        if (socket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = socket;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            try {
                this.setTcpNoDelay(false);
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE);
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
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
    }

    @Override
    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public int getSoLinger() {
        try {
            return this.javaSocket.getSoLinger();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public boolean isKeepAlive() {
        try {
            return this.javaSocket.getKeepAlive();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public boolean isTcpNoDelay() {
        try {
            return this.javaSocket.getTcpNoDelay();
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
    }

    @Override
    public SocketChannelConfig setKeepAlive(boolean bl) {
        try {
            this.javaSocket.setKeepAlive(bl);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        this.javaSocket.setPerformancePreferences(n, n2, n3);
        return this;
    }

    @Override
    public SocketChannelConfig setReceiveBufferSize(int n) {
        try {
            this.javaSocket.setReceiveBufferSize(n);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setReuseAddress(boolean bl) {
        try {
            this.javaSocket.setReuseAddress(bl);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setSendBufferSize(int n) {
        try {
            this.javaSocket.setSendBufferSize(n);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setSoLinger(int n) {
        try {
            if (n < 0) {
                this.javaSocket.setSoLinger(false, 1);
            } else {
                this.javaSocket.setSoLinger(true, n);
            }
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setTcpNoDelay(boolean bl) {
        try {
            this.javaSocket.setTcpNoDelay(bl);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public SocketChannelConfig setTrafficClass(int n) {
        try {
            this.javaSocket.setTrafficClass(n);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }

    @Override
    public SocketChannelConfig setAllowHalfClosure(boolean bl) {
        this.allowHalfClosure = bl;
        return this;
    }

    @Override
    public SocketChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public SocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public SocketChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public SocketChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public SocketChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    public SocketChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public SocketChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
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
    public ChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
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
}

