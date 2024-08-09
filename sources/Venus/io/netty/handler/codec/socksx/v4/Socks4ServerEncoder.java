/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.socksx.v4.Socks4CommandResponse;
import io.netty.util.NetUtil;

@ChannelHandler.Sharable
public final class Socks4ServerEncoder
extends MessageToByteEncoder<Socks4CommandResponse> {
    public static final Socks4ServerEncoder INSTANCE = new Socks4ServerEncoder();
    private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0};

    private Socks4ServerEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Socks4CommandResponse socks4CommandResponse, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(0);
        byteBuf.writeByte(socks4CommandResponse.status().byteValue());
        byteBuf.writeShort(socks4CommandResponse.dstPort());
        byteBuf.writeBytes(socks4CommandResponse.dstAddr() == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(socks4CommandResponse.dstAddr()));
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Socks4CommandResponse)object, byteBuf);
    }
}

