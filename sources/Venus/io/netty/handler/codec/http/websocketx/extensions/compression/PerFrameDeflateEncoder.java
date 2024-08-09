/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateEncoder;

class PerFrameDeflateEncoder
extends DeflateEncoder {
    public PerFrameDeflateEncoder(int n, int n2, boolean bl) {
        super(n, n2, bl);
    }

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return (object instanceof TextWebSocketFrame || object instanceof BinaryWebSocketFrame || object instanceof ContinuationWebSocketFrame) && ((WebSocketFrame)object).content().readableBytes() > 0 && (((WebSocketFrame)object).rsv() & 4) == 0;
    }

    @Override
    protected int rsv(WebSocketFrame webSocketFrame) {
        return webSocketFrame.rsv() | 4;
    }

    @Override
    protected boolean removeFrameTail(WebSocketFrame webSocketFrame) {
        return false;
    }
}

