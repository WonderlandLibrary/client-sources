/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketFrameAggregator
extends MessageAggregator<WebSocketFrame, WebSocketFrame, ContinuationWebSocketFrame, WebSocketFrame> {
    public WebSocketFrameAggregator(int n) {
        super(n);
    }

    @Override
    protected boolean isStartMessage(WebSocketFrame webSocketFrame) throws Exception {
        return webSocketFrame instanceof TextWebSocketFrame || webSocketFrame instanceof BinaryWebSocketFrame;
    }

    @Override
    protected boolean isContentMessage(WebSocketFrame webSocketFrame) throws Exception {
        return webSocketFrame instanceof ContinuationWebSocketFrame;
    }

    @Override
    protected boolean isLastContentMessage(ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
        return this.isContentMessage(continuationWebSocketFrame) && continuationWebSocketFrame.isFinalFragment();
    }

    @Override
    protected boolean isAggregated(WebSocketFrame webSocketFrame) throws Exception {
        if (webSocketFrame.isFinalFragment()) {
            return !this.isContentMessage(webSocketFrame);
        }
        return !this.isStartMessage(webSocketFrame) && !this.isContentMessage(webSocketFrame);
    }

    @Override
    protected boolean isContentLengthInvalid(WebSocketFrame webSocketFrame, int n) {
        return true;
    }

    @Override
    protected Object newContinueResponse(WebSocketFrame webSocketFrame, int n, ChannelPipeline channelPipeline) {
        return null;
    }

    @Override
    protected boolean closeAfterContinueResponse(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean ignoreContentAfterContinueResponse(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected WebSocketFrame beginAggregation(WebSocketFrame webSocketFrame, ByteBuf byteBuf) throws Exception {
        if (webSocketFrame instanceof TextWebSocketFrame) {
            return new TextWebSocketFrame(true, webSocketFrame.rsv(), byteBuf);
        }
        if (webSocketFrame instanceof BinaryWebSocketFrame) {
            return new BinaryWebSocketFrame(true, webSocketFrame.rsv(), byteBuf);
        }
        throw new Error();
    }

    @Override
    protected ByteBufHolder beginAggregation(Object object, ByteBuf byteBuf) throws Exception {
        return this.beginAggregation((WebSocketFrame)object, byteBuf);
    }

    @Override
    protected Object newContinueResponse(Object object, int n, ChannelPipeline channelPipeline) throws Exception {
        return this.newContinueResponse((WebSocketFrame)object, n, channelPipeline);
    }

    @Override
    protected boolean isContentLengthInvalid(Object object, int n) throws Exception {
        return this.isContentLengthInvalid((WebSocketFrame)object, n);
    }

    @Override
    protected boolean isAggregated(Object object) throws Exception {
        return this.isAggregated((WebSocketFrame)object);
    }

    @Override
    protected boolean isLastContentMessage(ByteBufHolder byteBufHolder) throws Exception {
        return this.isLastContentMessage((ContinuationWebSocketFrame)byteBufHolder);
    }

    @Override
    protected boolean isContentMessage(Object object) throws Exception {
        return this.isContentMessage((WebSocketFrame)object);
    }

    @Override
    protected boolean isStartMessage(Object object) throws Exception {
        return this.isStartMessage((WebSocketFrame)object);
    }
}

