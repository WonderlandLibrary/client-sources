/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.Utf8FrameValidator;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandshakeHandler;
import io.netty.util.AttributeKey;
import java.util.List;

public class WebSocketServerProtocolHandler
extends WebSocketProtocolHandler {
    private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadLength;
    private final boolean allowMaskMismatch;
    private final boolean checkStartsWith;

    public WebSocketServerProtocolHandler(String string) {
        this(string, null, false);
    }

    public WebSocketServerProtocolHandler(String string, boolean bl) {
        this(string, null, false, 65536, false, bl);
    }

    public WebSocketServerProtocolHandler(String string, String string2) {
        this(string, string2, false);
    }

    public WebSocketServerProtocolHandler(String string, String string2, boolean bl) {
        this(string, string2, bl, 65536);
    }

    public WebSocketServerProtocolHandler(String string, String string2, boolean bl, int n) {
        this(string, string2, bl, n, false);
    }

    public WebSocketServerProtocolHandler(String string, String string2, boolean bl, int n, boolean bl2) {
        this(string, string2, bl, n, bl2, false);
    }

    public WebSocketServerProtocolHandler(String string, String string2, boolean bl, int n, boolean bl2, boolean bl3) {
        this.websocketPath = string;
        this.subprotocols = string2;
        this.allowExtensions = bl;
        this.maxFramePayloadLength = n;
        this.allowMaskMismatch = bl2;
        this.checkStartsWith = bl3;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        if (channelPipeline.get(WebSocketServerProtocolHandshakeHandler.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch, this.checkStartsWith));
        }
        if (channelPipeline.get(Utf8FrameValidator.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker webSocketServerHandshaker = WebSocketServerProtocolHandler.getHandshaker(channelHandlerContext.channel());
            if (webSocketServerHandshaker != null) {
                webSocketFrame.retain();
                webSocketServerHandshaker.close(channelHandlerContext.channel(), (CloseWebSocketFrame)webSocketFrame);
            } else {
                channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
            return;
        }
        super.decode(channelHandlerContext, webSocketFrame, list);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (throwable instanceof WebSocketHandshakeException) {
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(throwable.getMessage().getBytes()));
            channelHandlerContext.channel().writeAndFlush(defaultFullHttpResponse).addListener(ChannelFutureListener.CLOSE);
        } else {
            channelHandlerContext.fireExceptionCaught(throwable);
            channelHandlerContext.close();
        }
    }

    static WebSocketServerHandshaker getHandshaker(Channel channel) {
        return channel.attr(HANDSHAKER_ATTR_KEY).get();
    }

    static void setHandshaker(Channel channel, WebSocketServerHandshaker webSocketServerHandshaker) {
        channel.attr(HANDSHAKER_ATTR_KEY).set(webSocketServerHandshaker);
    }

    static ChannelHandler forbiddenHttpRequestResponder() {
        return new ChannelInboundHandlerAdapter(){

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                if (object instanceof FullHttpRequest) {
                    ((FullHttpRequest)object).release();
                    DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                    channelHandlerContext.channel().writeAndFlush(defaultFullHttpResponse);
                } else {
                    channelHandlerContext.fireChannelRead(object);
                }
            }
        };
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }

    public static final class HandshakeComplete {
        private final String requestUri;
        private final HttpHeaders requestHeaders;
        private final String selectedSubprotocol;

        HandshakeComplete(String string, HttpHeaders httpHeaders, String string2) {
            this.requestUri = string;
            this.requestHeaders = httpHeaders;
            this.selectedSubprotocol = string2;
        }

        public String requestUri() {
            return this.requestUri;
        }

        public HttpHeaders requestHeaders() {
            return this.requestHeaders;
        }

        public String selectedSubprotocol() {
            return this.selectedSubprotocol;
        }
    }

    public static enum ServerHandshakeStateEvent {
        HANDSHAKE_COMPLETE;

    }
}

