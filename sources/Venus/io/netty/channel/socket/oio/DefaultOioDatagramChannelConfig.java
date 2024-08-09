/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.PreferHeapByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.channel.socket.oio.OioDatagramChannelConfig;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class DefaultOioDatagramChannelConfig
extends DefaultDatagramChannelConfig
implements OioDatagramChannelConfig {
    DefaultOioDatagramChannelConfig(DatagramChannel datagramChannel, DatagramSocket datagramSocket) {
        super(datagramChannel, datagramSocket);
        this.setAllocator(new PreferHeapByteBufAllocator(this.getAllocator()));
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_TIMEOUT) {
            return (T)Integer.valueOf(this.getSoTimeout());
        }
        return super.getOption(channelOption);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption != ChannelOption.SO_TIMEOUT) {
            return super.setOption(channelOption, t);
        }
        this.setSoTimeout((Integer)t);
        return false;
    }

    @Override
    public OioDatagramChannelConfig setSoTimeout(int n) {
        try {
            this.javaSocket().setSoTimeout(n);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    @Override
    public int getSoTimeout() {
        try {
            return this.javaSocket().getSoTimeout();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public OioDatagramChannelConfig setBroadcast(boolean bl) {
        super.setBroadcast(bl);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setInterface(InetAddress inetAddress) {
        super.setInterface(inetAddress);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setLoopbackModeDisabled(boolean bl) {
        super.setLoopbackModeDisabled(bl);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        super.setNetworkInterface(networkInterface);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setReuseAddress(boolean bl) {
        super.setReuseAddress(bl);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setReceiveBufferSize(int n) {
        super.setReceiveBufferSize(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setSendBufferSize(int n) {
        super.setSendBufferSize(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setTimeToLive(int n) {
        super.setTimeToLive(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setTrafficClass(int n) {
        super.setTrafficClass(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public OioDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    @Override
    public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    public DatagramChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    public DatagramChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public DatagramChannelConfig setAutoClose(boolean bl) {
        return this.setAutoClose(bl);
    }

    @Override
    public DatagramChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public DatagramChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public DatagramChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public DatagramChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    public DatagramChannelConfig setTrafficClass(int n) {
        return this.setTrafficClass(n);
    }

    @Override
    public DatagramChannelConfig setTimeToLive(int n) {
        return this.setTimeToLive(n);
    }

    @Override
    public DatagramChannelConfig setSendBufferSize(int n) {
        return this.setSendBufferSize(n);
    }

    @Override
    public DatagramChannelConfig setReceiveBufferSize(int n) {
        return this.setReceiveBufferSize(n);
    }

    @Override
    public DatagramChannelConfig setReuseAddress(boolean bl) {
        return this.setReuseAddress(bl);
    }

    @Override
    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        return this.setNetworkInterface(networkInterface);
    }

    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(boolean bl) {
        return this.setLoopbackModeDisabled(bl);
    }

    @Override
    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        return this.setInterface(inetAddress);
    }

    @Override
    public DatagramChannelConfig setBroadcast(boolean bl) {
        return this.setBroadcast(bl);
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
    public ChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }
}

