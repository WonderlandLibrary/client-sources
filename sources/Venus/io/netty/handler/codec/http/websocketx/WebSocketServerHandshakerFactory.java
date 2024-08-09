/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker00;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker07;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker08;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker13;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

public class WebSocketServerHandshakerFactory {
    private final String webSocketURL;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadLength;
    private final boolean allowMaskMismatch;

    public WebSocketServerHandshakerFactory(String string, String string2, boolean bl) {
        this(string, string2, bl, 65536);
    }

    public WebSocketServerHandshakerFactory(String string, String string2, boolean bl, int n) {
        this(string, string2, bl, n, false);
    }

    public WebSocketServerHandshakerFactory(String string, String string2, boolean bl, int n, boolean bl2) {
        this.webSocketURL = string;
        this.subprotocols = string2;
        this.allowExtensions = bl;
        this.maxFramePayloadLength = n;
        this.allowMaskMismatch = bl2;
    }

    public WebSocketServerHandshaker newHandshaker(HttpRequest httpRequest) {
        String string = httpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_VERSION);
        if (string != null) {
            if (string.equals(WebSocketVersion.V13.toHttpHeaderValue())) {
                return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
            }
            if (string.equals(WebSocketVersion.V08.toHttpHeaderValue())) {
                return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
            }
            if (string.equals(WebSocketVersion.V07.toHttpHeaderValue())) {
                return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
            }
            return null;
        }
        return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
    }

    @Deprecated
    public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
        WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel) {
        return WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel, channel.newPromise());
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel, ChannelPromise channelPromise) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
        defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION, (Object)WebSocketVersion.V13.toHttpHeaderValue());
        HttpUtil.setContentLength(defaultFullHttpResponse, 0L);
        return channel.writeAndFlush(defaultFullHttpResponse, channelPromise);
    }
}

