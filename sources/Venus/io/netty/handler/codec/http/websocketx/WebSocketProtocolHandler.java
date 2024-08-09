/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.List;

abstract class WebSocketProtocolHandler
extends MessageToMessageDecoder<WebSocketFrame> {
    WebSocketProtocolHandler() {
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (webSocketFrame instanceof PingWebSocketFrame) {
            webSocketFrame.content().retain();
            channelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(webSocketFrame.content()));
            return;
        }
        if (webSocketFrame instanceof PongWebSocketFrame) {
            return;
        }
        list.add(webSocketFrame.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.fireExceptionCaught(throwable);
        channelHandlerContext.close();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }
}

