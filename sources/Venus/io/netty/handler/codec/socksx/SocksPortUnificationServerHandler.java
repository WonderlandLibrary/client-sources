/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v4.Socks4ServerDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ServerEncoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

public class SocksPortUnificationServerHandler
extends ByteToMessageDecoder {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SocksPortUnificationServerHandler.class);
    private final Socks5ServerEncoder socks5encoder;

    public SocksPortUnificationServerHandler() {
        this(Socks5ServerEncoder.DEFAULT);
    }

    public SocksPortUnificationServerHandler(Socks5ServerEncoder socks5ServerEncoder) {
        if (socks5ServerEncoder == null) {
            throw new NullPointerException("socks5encoder");
        }
        this.socks5encoder = socks5ServerEncoder;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n = byteBuf.readerIndex();
        if (byteBuf.writerIndex() == n) {
            return;
        }
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        byte by = byteBuf.getByte(n);
        SocksVersion socksVersion = SocksVersion.valueOf(by);
        switch (1.$SwitchMap$io$netty$handler$codec$socksx$SocksVersion[socksVersion.ordinal()]) {
            case 1: {
                SocksPortUnificationServerHandler.logKnownVersion(channelHandlerContext, socksVersion);
                channelPipeline.addAfter(channelHandlerContext.name(), null, Socks4ServerEncoder.INSTANCE);
                channelPipeline.addAfter(channelHandlerContext.name(), null, new Socks4ServerDecoder());
                break;
            }
            case 2: {
                SocksPortUnificationServerHandler.logKnownVersion(channelHandlerContext, socksVersion);
                channelPipeline.addAfter(channelHandlerContext.name(), null, this.socks5encoder);
                channelPipeline.addAfter(channelHandlerContext.name(), null, new Socks5InitialRequestDecoder());
                break;
            }
            default: {
                SocksPortUnificationServerHandler.logUnknownVersion(channelHandlerContext, by);
                byteBuf.skipBytes(byteBuf.readableBytes());
                channelHandlerContext.close();
                return;
            }
        }
        channelPipeline.remove(this);
    }

    private static void logKnownVersion(ChannelHandlerContext channelHandlerContext, SocksVersion socksVersion) {
        logger.debug("{} Protocol version: {}({})", (Object)channelHandlerContext.channel(), (Object)socksVersion);
    }

    private static void logUnknownVersion(ChannelHandlerContext channelHandlerContext, byte by) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} Unknown protocol version: {}", (Object)channelHandlerContext.channel(), (Object)(by & 0xFF));
        }
    }
}

