/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.barchart.udt.nio.ChannelUDT
 */
package io.netty.channel.udt;

import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtServerChannelConfig;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class DefaultUdtServerChannelConfig
extends DefaultUdtChannelConfig
implements UdtServerChannelConfig {
    private volatile int backlog = 64;

    public DefaultUdtServerChannelConfig(UdtChannel udtChannel, ChannelUDT channelUDT, boolean bl) throws IOException {
        super(udtChannel, channelUDT, bl);
        if (bl) {
            this.apply(channelUDT);
        }
    }

    @Override
    protected void apply(ChannelUDT channelUDT) throws IOException {
    }

    @Override
    public int getBacklog() {
        return this.backlog;
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return (T)Integer.valueOf(this.getBacklog());
        }
        return super.getOption(channelOption);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BACKLOG);
    }

    @Override
    public UdtServerChannelConfig setBacklog(int n) {
        this.backlog = n;
        return this;
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption != ChannelOption.SO_BACKLOG) {
            return super.setOption(channelOption, t);
        }
        this.setBacklog((Integer)t);
        return false;
    }

    @Override
    public UdtServerChannelConfig setProtocolReceiveBufferSize(int n) {
        super.setProtocolReceiveBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setProtocolSendBufferSize(int n) {
        super.setProtocolSendBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setReceiveBufferSize(int n) {
        super.setReceiveBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setReuseAddress(boolean bl) {
        super.setReuseAddress(bl);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSendBufferSize(int n) {
        super.setSendBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSoLinger(int n) {
        super.setSoLinger(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSystemReceiveBufferSize(int n) {
        super.setSystemReceiveBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSystemSendBufferSize(int n) {
        super.setSystemSendBufferSize(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public UdtServerChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    @Override
    public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }

    @Override
    public UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return this.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    public UdtChannelConfig setWriteBufferHighWaterMark(int n) {
        return this.setWriteBufferHighWaterMark(n);
    }

    @Override
    public UdtChannelConfig setWriteBufferLowWaterMark(int n) {
        return this.setWriteBufferLowWaterMark(n);
    }

    @Override
    public UdtChannelConfig setAutoClose(boolean bl) {
        return this.setAutoClose(bl);
    }

    @Override
    public UdtChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    @Override
    public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        return this.setAllocator(byteBufAllocator);
    }

    @Override
    public UdtChannelConfig setWriteSpinCount(int n) {
        return this.setWriteSpinCount(n);
    }

    @Override
    @Deprecated
    public UdtChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public UdtChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }

    @Override
    public UdtChannelConfig setSystemReceiveBufferSize(int n) {
        return this.setSystemReceiveBufferSize(n);
    }

    @Override
    public UdtChannelConfig setProtocolSendBufferSize(int n) {
        return this.setProtocolSendBufferSize(n);
    }

    @Override
    public UdtChannelConfig setSystemSendBufferSize(int n) {
        return this.setSystemSendBufferSize(n);
    }

    @Override
    public UdtChannelConfig setSoLinger(int n) {
        return this.setSoLinger(n);
    }

    @Override
    public UdtChannelConfig setSendBufferSize(int n) {
        return this.setSendBufferSize(n);
    }

    @Override
    public UdtChannelConfig setReuseAddress(boolean bl) {
        return this.setReuseAddress(bl);
    }

    @Override
    public UdtChannelConfig setReceiveBufferSize(int n) {
        return this.setReceiveBufferSize(n);
    }

    @Override
    public UdtChannelConfig setProtocolReceiveBufferSize(int n) {
        return this.setProtocolReceiveBufferSize(n);
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
    @Deprecated
    public ChannelConfig setMaxMessagesPerRead(int n) {
        return this.setMaxMessagesPerRead(n);
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int n) {
        return this.setConnectTimeoutMillis(n);
    }
}

