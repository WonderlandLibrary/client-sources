/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class SctpOutboundByteStreamHandler
extends MessageToMessageEncoder<ByteBuf> {
    private final int streamIdentifier;
    private final int protocolIdentifier;
    private final boolean unordered;

    public SctpOutboundByteStreamHandler(int n, int n2) {
        this(n, n2, false);
    }

    public SctpOutboundByteStreamHandler(int n, int n2, boolean bl) {
        this.streamIdentifier = n;
        this.protocolIdentifier = n2;
        this.unordered = bl;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(new SctpMessage(this.protocolIdentifier, this.streamIdentifier, this.unordered, byteBuf.retain()));
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

