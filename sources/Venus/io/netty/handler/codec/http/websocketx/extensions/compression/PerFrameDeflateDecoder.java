/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateDecoder;

class PerFrameDeflateDecoder
extends DeflateDecoder {
    public PerFrameDeflateDecoder(boolean bl) {
        super(bl);
    }

    @Override
    public boolean acceptInboundMessage(Object object) throws Exception {
        return (object instanceof TextWebSocketFrame || object instanceof BinaryWebSocketFrame || object instanceof ContinuationWebSocketFrame) && (((WebSocketFrame)object).rsv() & 4) > 0;
    }

    @Override
    protected int newRsv(WebSocketFrame webSocketFrame) {
        return webSocketFrame.rsv() ^ 4;
    }

    @Override
    protected boolean appendFrameTail(WebSocketFrame webSocketFrame) {
        return false;
    }
}

