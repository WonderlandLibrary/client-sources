/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.sctp.SctpChannelOption;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSctpChannelConfig
extends DefaultChannelConfig
implements SctpChannelConfig {
    private final com.sun.nio.sctp.SctpChannel javaChannel;

    public DefaultSctpChannelConfig(SctpChannel sctpChannel, com.sun.nio.sctp.SctpChannel sctpChannel2) {
        super(sctpChannel);
        if (sctpChannel2 == null) {
            throw new NullPointerException("javaChannel");
        }
        this.javaChannel = sctpChannel2;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            try {
                this.setSctpNoDelay(false);
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_NODELAY, SctpChannelOption.SCTP_INIT_MAXSTREAMS);
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T)Integer.valueOf(this.getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return (T)Integer.valueOf(this.getSendBufferSize());
        }
        if (channelOption == SctpChannelOption.SCTP_NODELAY) {
            return (T)Boolean.valueOf(this.isSctpNoDelay());
        }
        if (channelOption == SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
            return (T)this.getInitMaxStreams();
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
        } else if (channelOption == SctpChannelOption.SCTP_NODELAY) {
            this.setSctpNoDelay((Boolean)t);
        } else if (channelOption == SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
            this.setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)t);
        } else {
            return super.setOption(channelOption, t);
        }
        return false;
    }

    @Override
    public boolean isSctpNoDelay() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_NODELAY);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public SctpChannelConfig setSctpNoDelay(boolean bl) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, bl);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    @Override
    public int getSendBufferSize() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public SctpChannelConfig setSendBufferSize(int n) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, n);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    @Override
    public int getReceiveBufferSize() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public SctpChannelConfig setReceiveBufferSize(int n) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, n);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    @Override
    public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public SctpChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
        return this;
    }

    @Override
    public SctpChannelConfig setConnectTimeoutMillis(int n) {
        super.setConnectTimeoutMillis(n);
        return this;
    }

    @Override
    @Deprecated
    public SctpChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public SctpChannelConfig setWriteSpinCount(int n) {
        super.setWriteSpinCount(n);
        return this;
    }

    @Override
    public SctpChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override
    public SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override
    public SctpChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    public SctpChannelConfig setAutoClose(boolean bl) {
        super.setAutoClose(bl);
        return this;
    }

    @Override
    public SctpChannelConfig setWriteBufferHighWaterMark(int n) {
        super.setWriteBufferHighWaterMark(n);
        return this;
    }

    @Override
    public SctpChannelConfig setWriteBufferLowWaterMark(int n) {
        super.setWriteBufferLowWaterMark(n);
        return this;
    }

    @Override
    public SctpChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override
    public SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
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

