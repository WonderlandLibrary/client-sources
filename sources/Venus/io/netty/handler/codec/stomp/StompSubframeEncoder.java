/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiHeadersEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeadersSubframe;
import io.netty.handler.codec.stomp.StompSubframe;
import io.netty.util.CharsetUtil;
import java.util.List;
import java.util.Map;

public class StompSubframeEncoder
extends MessageToMessageEncoder<StompSubframe> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, StompSubframe stompSubframe, List<Object> list) throws Exception {
        if (stompSubframe instanceof StompFrame) {
            StompFrame stompFrame = (StompFrame)stompSubframe;
            ByteBuf byteBuf = StompSubframeEncoder.encodeFrame(stompFrame, channelHandlerContext);
            list.add(byteBuf);
            ByteBuf byteBuf2 = StompSubframeEncoder.encodeContent(stompFrame, channelHandlerContext);
            list.add(byteBuf2);
        } else if (stompSubframe instanceof StompHeadersSubframe) {
            StompHeadersSubframe stompHeadersSubframe = (StompHeadersSubframe)stompSubframe;
            ByteBuf byteBuf = StompSubframeEncoder.encodeFrame(stompHeadersSubframe, channelHandlerContext);
            list.add(byteBuf);
        } else if (stompSubframe instanceof StompContentSubframe) {
            StompContentSubframe stompContentSubframe = (StompContentSubframe)stompSubframe;
            ByteBuf byteBuf = StompSubframeEncoder.encodeContent(stompContentSubframe, channelHandlerContext);
            list.add(byteBuf);
        }
    }

    private static ByteBuf encodeContent(StompContentSubframe stompContentSubframe, ChannelHandlerContext channelHandlerContext) {
        if (stompContentSubframe instanceof LastStompContentSubframe) {
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(stompContentSubframe.content().readableBytes() + 1);
            byteBuf.writeBytes(stompContentSubframe.content());
            byteBuf.writeByte(0);
            return byteBuf;
        }
        return stompContentSubframe.content().retain();
    }

    private static ByteBuf encodeFrame(StompHeadersSubframe stompHeadersSubframe, ChannelHandlerContext channelHandlerContext) {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        byteBuf.writeCharSequence(stompHeadersSubframe.command().toString(), CharsetUtil.US_ASCII);
        byteBuf.writeByte(10);
        AsciiHeadersEncoder asciiHeadersEncoder = new AsciiHeadersEncoder(byteBuf, AsciiHeadersEncoder.SeparatorType.COLON, AsciiHeadersEncoder.NewlineType.LF);
        for (Map.Entry<CharSequence, CharSequence> entry : stompHeadersSubframe.headers()) {
            asciiHeadersEncoder.encode(entry);
        }
        byteBuf.writeByte(10);
        return byteBuf;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (StompSubframe)object, (List<Object>)list);
    }
}

