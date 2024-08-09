/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.barchart.udt.OptionUDT
 *  com.barchart.udt.SocketUDT
 *  com.barchart.udt.nio.ChannelUDT
 */
package io.netty.channel.udt;

import com.barchart.udt.OptionUDT;
import com.barchart.udt.SocketUDT;
import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtChannelOption;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class DefaultUdtChannelConfig
extends DefaultChannelConfig
implements UdtChannelConfig {
    private static final int K = 1024;
    private static final int M = 0x100000;
    private volatile int protocolReceiveBufferSize = 0xA00000;
    private volatile int protocolSendBufferSize = 0xA00000;
    private volatile int systemReceiveBufferSize = 0x100000;
    private volatile int systemSendBufferSize = 0x100000;
    private volatile int allocatorReceiveBufferSize = 131072;
    private volatile int allocatorSendBufferSize = 131072;
    private volatile int soLinger;
    private volatile boolean reuseAddress = true;

    public DefaultUdtChannelConfig(UdtChannel udtChannel, ChannelUDT channelUDT, boolean bl) throws IOException {
        super(udtChannel);
        if (bl) {
            this.apply(channelUDT);
        }
    }

    protected void apply(ChannelUDT channelUDT) throws IOException {
        SocketUDT socketUDT = channelUDT.socketUDT();
        socketUDT.setReuseAddress(this.isReuseAddress());
        socketUDT.setSendBufferSize(this.getSendBufferSize());
        if (this.getSoLinger() <= 0) {
            socketUDT.setSoLinger(false, 1);
        } else {
            socketUDT.setSoLinger(true, this.getSoLinger());
        }
        socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, (Object)this.getProtocolReceiveBufferSize());
        socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, (Object)this.getProtocolSendBufferSize());
        socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, (Object)this.getSystemReceiveBufferSize());
        socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, (Object)this.getSystemSendBufferSize());
    }

    @Override
    public int getProtocolReceiveBufferSize() {
        return this.protocolReceiveBufferSize;
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            return (T)Integer.valueOf(this.getProtocolReceiveBufferSize());
        }
        if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            return (T)Integer.valueOf(this.getProtocolSendBufferSize());
        }
        if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            return (T)Integer.valueOf(this.getSystemReceiveBufferSize());
        }
        if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            return (T)Integer.valueOf(this.getSystemSendBufferSize());
        }
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T)Integer.valueOf(this.getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return (T)Integer.valueOf(this.getSendBufferSize());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return (T)Boolean.valueOf(this.isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return (T)Integer.valueOf(this.getSoLinger());
        }
        return super.getOption(channelOption);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER);
    }

    @Override
    public int getReceiveBufferSize() {
        return this.allocatorReceiveBufferSize;
    }

    @Override
    public int getSendBufferSize() {
        return this.allocatorSendBufferSize;
    }

    @Override
    public int getSoLinger() {
        return this.soLinger;
    }

    @Override
    public boolean isReuseAddress() {
        return this.reuseAddress;
    }

    @Override
    public UdtChannelConfig setProtocolReceiveBufferSize(int n) {
        this.protocolReceiveBufferSize = n;
        return this;
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            this.setProtocolReceiveBufferSize((Integer)t);
        } else if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            this.setProtocolSendBufferSize((Integer)t);
        } else if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            this.setSystemReceiveBufferSize((Integer)t);
        } else if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            this.setSystemSendBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((Integer)t);
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((Boolean)t);
        } else if (channelOption == ChannelOption.SO_LINGER) {
            this.setSoLinger((Integer)t);
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
    }

    @Override
    public UdtChannelConfig setReceiveBufferSize(int n) {
        this.allocatorReceiveBufferSize = n;
        return this;
    }

    @Override
    public UdtChannelConfig setReuseAddress(boolean bl) {
        this.reuseAddress = bl;
        return this;
    }

    @Override
    public UdtChannelConfig setSendBufferSize(int n) {
        this.allocatorSendBufferSize = n;
        return this;
    }

    @Override
    public UdtChannelConfig setSoLinger(int n) {
        this.soLinger = n;
        return this;
    }

    @Override
    public int getSystemReceiveBufferSize() {
        return this.systemReceiveBufferSize;
    }

    @Override
    public UdtChannelConfig setSystemSendBufferSize(int n) {
        this.systemReceiveBufferSize = n;
        return this;
    }

    @Override
    public int getProtocolSendBufferSize() {
        return this.protocolSendBufferSize;
    }

    @Override
    public UdtChannelConfig setProtocolSendBufferSize(int n) {
        this.protocolSendBufferSize = n;
        return this;
    }

    @Override
    public UdtChannelConfig setSystemReceiveBufferSize(int n) {
        this.systemSendBufferSize = n;
        return this;
    }

    @Override
    public int getSystemSendBufferSize() {
        return this.systemSendBufferSize;
    }

    @Override
    public UdtChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public UdtChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public UdtChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public UdtChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public UdtChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    public UdtChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public UdtChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
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

