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
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.NetUtil;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultServerSocketChannelConfig
extends DefaultChannelConfig
implements ServerSocketChannelConfig {
    protected final ServerSocket javaSocket;
    private volatile int backlog = NetUtil.SOMAXCONN;

    public DefaultServerSocketChannelConfig(ServerSocketChannel serverSocketChannel, ServerSocket serverSocket) {
        super(serverSocketChannel);
        if (serverSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = serverSocket;
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T)Integer.valueOf(this.getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return (T)Boolean.valueOf(this.isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return (T)Integer.valueOf(this.getBacklog());
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((Boolean)t);
        } else if (channelOption == ChannelOption.SO_BACKLOG) {
            this.setBacklog((Integer)t);
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
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
    public ServerSocketChannelConfig setReuseAddress(boolean bl) {
        try {
            this.javaSocket.setReuseAddress(bl);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
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
    public ServerSocketChannelConfig setReceiveBufferSize(int n) {
        try {
            this.javaSocket.setReceiveBufferSize(n);
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public ServerSocketChannelConfig setPerformancePreferences(int n, int n2, int n3) {
        this.javaSocket.setPerformancePreferences(n, n2, n3);
        return this;
    }

    @Override
    public int getBacklog() {
        return this.backlog;
    }

    @Override
    public ServerSocketChannelConfig setBacklog(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("backlog: " + n);
        }
        this.backlog = n;
        return this;
    }

    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public ServerSocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
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

