/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateEncoder;
import java.util.List;

class PerMessageDeflateEncoder
extends DeflateEncoder {
    private boolean compressing;

    public PerMessageDeflateEncoder(int n, int n2, boolean bl) {
        super(n, n2, bl);
    }

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return (object instanceof TextWebSocketFrame || object instanceof BinaryWebSocketFrame) && (((WebSocketFrame)object).rsv() & 4) == 0 || object instanceof ContinuationWebSocketFrame && this.compressing;
    }

    @Override
    protected int rsv(WebSocketFrame webSocketFrame) {
        return webSocketFrame instanceof TextWebSocketFrame || webSocketFrame instanceof BinaryWebSocketFrame ? webSocketFrame.rsv() | 4 : webSocketFrame.rsv();
    }

    @Override
    protected boolean removeFrameTail(WebSocketFrame webSocketFrame) {
        return webSocketFrame.isFinalFragment();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        super.encode(channelHandlerContext, webSocketFrame, list);
        if (webSocketFrame.isFinalFragment()) {
            this.compressing = false;
        } else if (webSocketFrame instanceof TextWebSocketFrame || webSocketFrame instanceof BinaryWebSocketFrame) {
            this.compressing = true;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }
}

