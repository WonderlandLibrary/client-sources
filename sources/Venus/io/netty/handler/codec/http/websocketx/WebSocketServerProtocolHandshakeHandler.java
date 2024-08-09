/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;

class WebSocketServerProtocolHandshakeHandler
extends ChannelInboundHandlerAdapter {
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean allowMaskMismatch;
    private final boolean checkStartsWith;

    WebSocketServerProtocolHandshakeHandler(String string, String string2, boolean bl, int n, boolean bl2) {
        this(string, string2, bl, n, bl2, false);
    }

    WebSocketServerProtocolHandshakeHandler(String string, String string2, boolean bl, int n, boolean bl2, boolean bl3) {
        this.websocketPath = string;
        this.subprotocols = string2;
        this.allowExtensions = bl;
        this.maxFramePayloadSize = n;
        this.allowMaskMismatch = bl2;
        this.checkStartsWith = bl3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest)object;
        if (this.isNotWebSocketPath(fullHttpRequest)) {
            channelHandlerContext.fireChannelRead(object);
            return;
        }
        try {
            if (fullHttpRequest.method() != HttpMethod.GET) {
                WebSocketServerProtocolHandshakeHandler.sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                return;
            }
            WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(WebSocketServerProtocolHandshakeHandler.getWebSocketLocation(channelHandlerContext.pipeline(), fullHttpRequest, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize, this.allowMaskMismatch);
            WebSocketServerHandshaker webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
            if (webSocketServerHandshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
            } else {
                ChannelFuture channelFuture = webSocketServerHandshaker.handshake(channelHandlerContext.channel(), fullHttpRequest);
                channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext, fullHttpRequest, webSocketServerHandshaker){
                    final ChannelHandlerContext val$ctx;
                    final FullHttpRequest val$req;
                    final WebSocketServerHandshaker val$handshaker;
                    final WebSocketServerProtocolHandshakeHandler this$0;
                    {
                        this.this$0 = webSocketServerProtocolHandshakeHandler;
                        this.val$ctx = channelHandlerContext;
                        this.val$req = fullHttpRequest;
                        this.val$handshaker = webSocketServerHandshaker;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            this.val$ctx.fireExceptionCaught(channelFuture.cause());
                        } else {
                            this.val$ctx.fireUserEventTriggered((Object)WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                            this.val$ctx.fireUserEventTriggered(new WebSocketServerProtocolHandler.HandshakeComplete(this.val$req.uri(), this.val$req.headers(), this.val$handshaker.selectedSubprotocol()));
                        }
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
                WebSocketServerProtocolHandler.setHandshaker(channelHandlerContext.channel(), webSocketServerHandshaker);
                channelHandlerContext.pipeline().replace(this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
            }
        } finally {
            fullHttpRequest.release();
        }
    }

    private boolean isNotWebSocketPath(FullHttpRequest fullHttpRequest) {
        return this.checkStartsWith ? !fullHttpRequest.uri().startsWith(this.websocketPath) : !fullHttpRequest.uri().equals(this.websocketPath);
    }

    private static void sendHttpResponse(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, HttpResponse httpResponse) {
        ChannelFuture channelFuture = channelHandlerContext.channel().writeAndFlush(httpResponse);
        if (!HttpUtil.isKeepAlive(httpRequest) || httpResponse.status().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline channelPipeline, HttpRequest httpRequest, String string) {
        String string2 = "ws";
        if (channelPipeline.get(SslHandler.class) != null) {
            string2 = "wss";
        }
        String string3 = httpRequest.headers().get(HttpHeaderNames.HOST);
        return string2 + "://" + string3 + string;
    }
}

