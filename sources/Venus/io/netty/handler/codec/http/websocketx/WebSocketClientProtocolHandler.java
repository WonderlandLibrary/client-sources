/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.Utf8FrameValidator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandshakeHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import java.net.URI;
import java.util.List;

public class WebSocketClientProtocolHandler
extends WebSocketProtocolHandler {
    private final WebSocketClientHandshaker handshaker;
    private final boolean handleCloseFrames;

    public WebSocketClientHandshaker handshaker() {
        return this.handshaker;
    }

    public WebSocketClientProtocolHandler(URI uRI, WebSocketVersion webSocketVersion, String string, boolean bl, HttpHeaders httpHeaders, int n, boolean bl2, boolean bl3, boolean bl4) {
        this(WebSocketClientHandshakerFactory.newHandshaker(uRI, webSocketVersion, string, bl, httpHeaders, n, bl3, bl4), bl2);
    }

    public WebSocketClientProtocolHandler(URI uRI, WebSocketVersion webSocketVersion, String string, boolean bl, HttpHeaders httpHeaders, int n, boolean bl2) {
        this(uRI, webSocketVersion, string, bl, httpHeaders, n, bl2, true, false);
    }

    public WebSocketClientProtocolHandler(URI uRI, WebSocketVersion webSocketVersion, String string, boolean bl, HttpHeaders httpHeaders, int n) {
        this(uRI, webSocketVersion, string, bl, httpHeaders, n, true);
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker webSocketClientHandshaker, boolean bl) {
        this.handshaker = webSocketClientHandshaker;
        this.handleCloseFrames = bl;
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this(webSocketClientHandshaker, true);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (this.handleCloseFrames && webSocketFrame instanceof CloseWebSocketFrame) {
            channelHandlerContext.close();
            return;
        }
        super.decode(channelHandlerContext, webSocketFrame, list);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        if (channelPipeline.get(WebSocketClientProtocolHandshakeHandler.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
        }
        if (channelPipeline.get(Utf8FrameValidator.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        super.exceptionCaught(channelHandlerContext, throwable);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }

    public static enum ClientHandshakeStateEvent {
        HANDSHAKE_ISSUED,
        HANDSHAKE_COMPLETE;

    }
}

