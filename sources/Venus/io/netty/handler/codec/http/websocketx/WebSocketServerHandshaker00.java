/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocket00FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketUtil;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import java.util.regex.Pattern;

public class WebSocketServerHandshaker00
extends WebSocketServerHandshaker {
    private static final Pattern BEGINNING_DIGIT = Pattern.compile("[^0-9]");
    private static final Pattern BEGINNING_SPACE = Pattern.compile("[^ ]");

    public WebSocketServerHandshaker00(String string, String string2, int n) {
        super(WebSocketVersion.V00, string, string2, n);
    }

    @Override
    protected FullHttpResponse newHandshakeResponse(FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders) {
        if (!fullHttpRequest.headers().containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, false) || !HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(fullHttpRequest.headers().get(HttpHeaderNames.UPGRADE))) {
            throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
        }
        boolean bl = fullHttpRequest.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) && fullHttpRequest.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2);
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, bl ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
        if (httpHeaders != null) {
            defaultFullHttpResponse.headers().add(httpHeaders);
        }
        defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.UPGRADE, (Object)HttpHeaderValues.WEBSOCKET);
        defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.UPGRADE);
        if (bl) {
            String string;
            defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, (Object)fullHttpRequest.headers().get(HttpHeaderNames.ORIGIN));
            defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_LOCATION, (Object)this.uri());
            String string2 = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            if (string2 != null) {
                string = this.selectSubprotocol(string2);
                if (string == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Requested subprotocol(s) not supported: {}", (Object)string2);
                    }
                } else {
                    defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, (Object)string);
                }
            }
            string = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY1);
            String string3 = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY2);
            int n = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(string).replaceAll("")) / (long)BEGINNING_SPACE.matcher(string).replaceAll("").length());
            int n2 = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(string3).replaceAll("")) / (long)BEGINNING_SPACE.matcher(string3).replaceAll("").length());
            long l = fullHttpRequest.content().readLong();
            ByteBuf byteBuf = Unpooled.buffer(16);
            byteBuf.writeInt(n);
            byteBuf.writeInt(n2);
            byteBuf.writeLong(l);
            defaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(byteBuf.array()));
        } else {
            defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.WEBSOCKET_ORIGIN, (Object)fullHttpRequest.headers().get(HttpHeaderNames.ORIGIN));
            defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.WEBSOCKET_LOCATION, (Object)this.uri());
            String string = fullHttpRequest.headers().get(HttpHeaderNames.WEBSOCKET_PROTOCOL);
            if (string != null) {
                defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.WEBSOCKET_PROTOCOL, (Object)this.selectSubprotocol(string));
            }
        }
        return defaultFullHttpResponse;
    }

    @Override
    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame, ChannelPromise channelPromise) {
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
    }

    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket00FrameDecoder(this.maxFramePayloadLength());
    }

    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket00FrameEncoder();
    }
}

