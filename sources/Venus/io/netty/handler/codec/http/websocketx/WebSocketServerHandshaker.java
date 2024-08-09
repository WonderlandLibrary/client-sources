/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class WebSocketServerHandshaker {
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), WebSocketServerHandshaker.class, "handshake(...)");
    private final String uri;
    private final String[] subprotocols;
    private final WebSocketVersion version;
    private final int maxFramePayloadLength;
    private String selectedSubprotocol;
    public static final String SUB_PROTOCOL_WILDCARD = "*";

    protected WebSocketServerHandshaker(WebSocketVersion webSocketVersion, String string, String string2, int n) {
        this.version = webSocketVersion;
        this.uri = string;
        if (string2 != null) {
            String[] stringArray = string2.split(",");
            for (int i = 0; i < stringArray.length; ++i) {
                stringArray[i] = stringArray[i].trim();
            }
            this.subprotocols = stringArray;
        } else {
            this.subprotocols = EmptyArrays.EMPTY_STRINGS;
        }
        this.maxFramePayloadLength = n;
    }

    public String uri() {
        return this.uri;
    }

    public Set<String> subprotocols() {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        Collections.addAll(linkedHashSet, this.subprotocols);
        return linkedHashSet;
    }

    public WebSocketVersion version() {
        return this.version;
    }

    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }

    public ChannelFuture handshake(Channel channel, FullHttpRequest fullHttpRequest) {
        return this.handshake(channel, fullHttpRequest, null, channel.newPromise());
    }

    public final ChannelFuture handshake(Channel channel, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders, ChannelPromise channelPromise) {
        String string;
        ChannelHandlerContext channelHandlerContext;
        if (logger.isDebugEnabled()) {
            logger.debug("{} WebSocket version {} server handshake", (Object)channel, (Object)this.version());
        }
        FullHttpResponse fullHttpResponse = this.newHandshakeResponse(fullHttpRequest, httpHeaders);
        ChannelPipeline channelPipeline = channel.pipeline();
        if (channelPipeline.get(HttpObjectAggregator.class) != null) {
            channelPipeline.remove(HttpObjectAggregator.class);
        }
        if (channelPipeline.get(HttpContentCompressor.class) != null) {
            channelPipeline.remove(HttpContentCompressor.class);
        }
        if ((channelHandlerContext = channelPipeline.context(HttpRequestDecoder.class)) == null) {
            channelHandlerContext = channelPipeline.context(HttpServerCodec.class);
            if (channelHandlerContext == null) {
                channelPromise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
                return channelPromise;
            }
            channelPipeline.addBefore(channelHandlerContext.name(), "wsdecoder", this.newWebsocketDecoder());
            channelPipeline.addBefore(channelHandlerContext.name(), "wsencoder", this.newWebSocketEncoder());
            string = channelHandlerContext.name();
        } else {
            channelPipeline.replace(channelHandlerContext.name(), "wsdecoder", (ChannelHandler)this.newWebsocketDecoder());
            string = channelPipeline.context(HttpResponseEncoder.class).name();
            channelPipeline.addBefore(string, "wsencoder", this.newWebSocketEncoder());
        }
        channel.writeAndFlush(fullHttpResponse).addListener(new ChannelFutureListener(this, string, channelPromise){
            final String val$encoderName;
            final ChannelPromise val$promise;
            final WebSocketServerHandshaker this$0;
            {
                this.this$0 = webSocketServerHandshaker;
                this.val$encoderName = string;
                this.val$promise = channelPromise;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ChannelPipeline channelPipeline = channelFuture.channel().pipeline();
                    channelPipeline.remove(this.val$encoderName);
                    this.val$promise.setSuccess();
                } else {
                    this.val$promise.setFailure(channelFuture.cause());
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        return channelPromise;
    }

    public ChannelFuture handshake(Channel channel, HttpRequest httpRequest) {
        return this.handshake(channel, httpRequest, null, channel.newPromise());
    }

    public final ChannelFuture handshake(Channel channel, HttpRequest httpRequest, HttpHeaders httpHeaders, ChannelPromise channelPromise) {
        ChannelPipeline channelPipeline;
        ChannelHandlerContext channelHandlerContext;
        if (httpRequest instanceof FullHttpRequest) {
            return this.handshake(channel, (FullHttpRequest)httpRequest, httpHeaders, channelPromise);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("{} WebSocket version {} server handshake", (Object)channel, (Object)this.version());
        }
        if ((channelHandlerContext = (channelPipeline = channel.pipeline()).context(HttpRequestDecoder.class)) == null && (channelHandlerContext = channelPipeline.context(HttpServerCodec.class)) == null) {
            channelPromise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
            return channelPromise;
        }
        String string = "httpAggregator";
        channelPipeline.addAfter(channelHandlerContext.name(), string, new HttpObjectAggregator(8192));
        channelPipeline.addAfter(string, "handshaker", new SimpleChannelInboundHandler<FullHttpRequest>(this, channel, httpHeaders, channelPromise){
            final Channel val$channel;
            final HttpHeaders val$responseHeaders;
            final ChannelPromise val$promise;
            final WebSocketServerHandshaker this$0;
            {
                this.this$0 = webSocketServerHandshaker;
                this.val$channel = channel;
                this.val$responseHeaders = httpHeaders;
                this.val$promise = channelPromise;
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
                channelHandlerContext.pipeline().remove(this);
                this.this$0.handshake(this.val$channel, fullHttpRequest, this.val$responseHeaders, this.val$promise);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                channelHandlerContext.pipeline().remove(this);
                this.val$promise.tryFailure(throwable);
                channelHandlerContext.fireExceptionCaught(throwable);
            }

            @Override
            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                this.val$promise.tryFailure(WebSocketServerHandshaker.access$000());
                channelHandlerContext.fireChannelInactive();
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                this.channelRead0(channelHandlerContext, (FullHttpRequest)object);
            }
        });
        try {
            channelHandlerContext.fireChannelRead(ReferenceCountUtil.retain(httpRequest));
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
        return channelPromise;
    }

    protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest var1, HttpHeaders var2);

    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.close(channel, closeWebSocketFrame, channel.newPromise());
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame, ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise).addListener(ChannelFutureListener.CLOSE);
    }

    protected String selectSubprotocol(String string) {
        String[] stringArray;
        if (string == null || this.subprotocols.length == 0) {
            return null;
        }
        for (String string2 : stringArray = string.split(",")) {
            String string3 = string2.trim();
            for (String string4 : this.subprotocols) {
                if (!SUB_PROTOCOL_WILDCARD.equals(string4) && !string3.equals(string4)) continue;
                this.selectedSubprotocol = string3;
                return string3;
            }
        }
        return null;
    }

    public String selectedSubprotocol() {
        return this.selectedSubprotocol;
    }

    protected abstract WebSocketFrameDecoder newWebsocketDecoder();

    protected abstract WebSocketFrameEncoder newWebSocketEncoder();

    static ClosedChannelException access$000() {
        return CLOSED_CHANNEL_EXCEPTION;
    }
}

