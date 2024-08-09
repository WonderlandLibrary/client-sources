/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SctpMessageCompletionHandler
extends MessageToMessageDecoder<SctpMessage> {
    private final Map<Integer, ByteBuf> fragments = new HashMap<Integer, ByteBuf>();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, SctpMessage sctpMessage, List<Object> list) throws Exception {
        ByteBuf byteBuf = sctpMessage.content();
        int n = sctpMessage.protocolIdentifier();
        int n2 = sctpMessage.streamIdentifier();
        boolean bl = sctpMessage.isComplete();
        boolean bl2 = sctpMessage.isUnordered();
        ByteBuf byteBuf2 = this.fragments.remove(n2);
        if (byteBuf2 == null) {
            byteBuf2 = Unpooled.EMPTY_BUFFER;
        }
        if (bl && !byteBuf2.isReadable()) {
            list.add(sctpMessage);
        } else if (!bl && byteBuf2.isReadable()) {
            this.fragments.put(n2, Unpooled.wrappedBuffer(byteBuf2, byteBuf));
        } else if (bl && byteBuf2.isReadable()) {
            SctpMessage sctpMessage2 = new SctpMessage(n, n2, bl2, Unpooled.wrappedBuffer(byteBuf2, byteBuf));
            list.add(sctpMessage2);
        } else {
            this.fragments.put(n2, byteBuf);
        }
        byteBuf.retain();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        for (ByteBuf byteBuf : this.fragments.values()) {
            byteBuf.release();
        }
        this.fragments.clear();
        super.handlerRemoved(channelHandlerContext);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (SctpMessage)object, (List<Object>)list);
    }
}

