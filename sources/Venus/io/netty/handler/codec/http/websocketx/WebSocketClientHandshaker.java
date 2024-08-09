/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketScheme;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ThrowableUtil;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public abstract class WebSocketClientHandshaker {
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), WebSocketClientHandshaker.class, "processHandshake(...)");
    private static final String HTTP_SCHEME_PREFIX = HttpScheme.HTTP + "://";
    private static final String HTTPS_SCHEME_PREFIX = HttpScheme.HTTPS + "://";
    private final URI uri;
    private final WebSocketVersion version;
    private volatile boolean handshakeComplete;
    private final String expectedSubprotocol;
    private volatile String actualSubprotocol;
    protected final HttpHeaders customHeaders;
    private final int maxFramePayloadLength;

    protected WebSocketClientHandshaker(URI uRI, WebSocketVersion webSocketVersion, String string, HttpHeaders httpHeaders, int n) {
        this.uri = uRI;
        this.version = webSocketVersion;
        this.expectedSubprotocol = string;
        this.customHeaders = httpHeaders;
        this.maxFramePayloadLength = n;
    }

    public URI uri() {
        return this.uri;
    }

    public WebSocketVersion version() {
        return this.version;
    }

    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }

    public boolean isHandshakeComplete() {
        return this.handshakeComplete;
    }

    private void setHandshakeComplete() {
        this.handshakeComplete = true;
    }

    public String expectedSubprotocol() {
        return this.expectedSubprotocol;
    }

    public String actualSubprotocol() {
        return this.actualSubprotocol;
    }

    private void setActualSubprotocol(String string) {
        this.actualSubprotocol = string;
    }

    public ChannelFuture handshake(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.handshake(channel, channel.newPromise());
    }

    public final ChannelFuture handshake(Channel channel, ChannelPromise channelPromise) {
        HttpClientCodec httpClientCodec;
        FullHttpRequest fullHttpRequest = this.newHandshakeRequest();
        HttpResponseDecoder httpResponseDecoder = channel.pipeline().get(HttpResponseDecoder.class);
        if (httpResponseDecoder == null && (httpClientCodec = channel.pipeline().get(HttpClientCodec.class)) == null) {
            channelPromise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            return channelPromise;
        }
        channel.writeAndFlush(fullHttpRequest).addListener(new ChannelFutureListener(this, channelPromise){
            final ChannelPromise val$promise;
            final WebSocketClientHandshaker this$0;
            {
                this.this$0 = webSocketClientHandshaker;
                this.val$promise = channelPromise;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    ChannelPipeline channelPipeline = channelFuture.channel().pipeline();
                    ChannelHandlerContext channelHandlerContext = channelPipeline.context(HttpRequestEncoder.class);
                    if (channelHandlerContext == null) {
                        channelHandlerContext = channelPipeline.context(HttpClientCodec.class);
                    }
                    if (channelHandlerContext == null) {
                        this.val$promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
                        return;
                    }
                    channelPipeline.addAfter(channelHandlerContext.name(), "ws-encoder", this.this$0.newWebSocketEncoder());
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

    protected abstract FullHttpRequest newHandshakeRequest();

    public final void finishHandshake(Channel channel, FullHttpResponse fullHttpResponse) {
        ChannelHandlerContext channelHandlerContext;
        HttpObjectAggregator httpObjectAggregator;
        this.verify(fullHttpResponse);
        String string = fullHttpResponse.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
        string = string != null ? string.trim() : null;
        String string2 = this.expectedSubprotocol != null ? this.expectedSubprotocol : "";
        boolean bl = false;
        if (string2.isEmpty() && string == null) {
            bl = true;
            this.setActualSubprotocol(this.expectedSubprotocol);
        } else if (!string2.isEmpty() && string != null && !string.isEmpty()) {
            for (String object2 : string2.split(",")) {
                if (!object2.trim().equals(string)) continue;
                bl = true;
                this.setActualSubprotocol(string);
                break;
            }
        }
        if (!bl) {
            throw new WebSocketHandshakeException(String.format("Invalid subprotocol. Actual: %s. Expected one of: %s", string, this.expectedSubprotocol));
        }
        this.setHandshakeComplete();
        String[] stringArray = channel.pipeline();
        HttpContentDecompressor httpContentDecompressor = stringArray.get(HttpContentDecompressor.class);
        if (httpContentDecompressor != null) {
            stringArray.remove(httpContentDecompressor);
        }
        if ((httpObjectAggregator = stringArray.get(HttpObjectAggregator.class)) != null) {
            stringArray.remove(httpObjectAggregator);
        }
        if ((channelHandlerContext = stringArray.context(HttpResponseDecoder.class)) == null) {
            ChannelHandlerContext channelHandlerContext2 = stringArray.context(HttpClientCodec.class);
            if (channelHandlerContext2 == null) {
                throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
            }
            HttpClientCodec httpClientCodec = (HttpClientCodec)channelHandlerContext2.handler();
            httpClientCodec.removeOutboundHandler();
            stringArray.addAfter(channelHandlerContext2.name(), "ws-decoder", this.newWebsocketDecoder());
            channel.eventLoop().execute(new Runnable(this, (ChannelPipeline)stringArray, httpClientCodec){
                final ChannelPipeline val$p;
                final HttpClientCodec val$codec;
                final WebSocketClientHandshaker this$0;
                {
                    this.this$0 = webSocketClientHandshaker;
                    this.val$p = channelPipeline;
                    this.val$codec = httpClientCodec;
                }

                @Override
                public void run() {
                    this.val$p.remove(this.val$codec);
                }
            });
        } else {
            if (stringArray.get(HttpRequestEncoder.class) != null) {
                stringArray.remove(HttpRequestEncoder.class);
            }
            ChannelHandlerContext channelHandlerContext3 = channelHandlerContext;
            stringArray.addAfter(channelHandlerContext3.name(), "ws-decoder", this.newWebsocketDecoder());
            channel.eventLoop().execute(new Runnable(this, (ChannelPipeline)stringArray, channelHandlerContext3){
                final ChannelPipeline val$p;
                final ChannelHandlerContext val$context;
                final WebSocketClientHandshaker this$0;
                {
                    this.this$0 = webSocketClientHandshaker;
                    this.val$p = channelPipeline;
                    this.val$context = channelHandlerContext;
                }

                @Override
                public void run() {
                    this.val$p.remove(this.val$context.handler());
                }
            });
        }
    }

    public final ChannelFuture processHandshake(Channel channel, HttpResponse httpResponse) {
        return this.processHandshake(channel, httpResponse, channel.newPromise());
    }

    public final ChannelFuture processHandshake(Channel channel, HttpResponse httpResponse, ChannelPromise channelPromise) {
        if (httpResponse instanceof FullHttpResponse) {
            try {
                this.finishHandshake(channel, (FullHttpResponse)httpResponse);
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else {
            ChannelPipeline channelPipeline = channel.pipeline();
            ChannelHandlerContext channelHandlerContext = channelPipeline.context(HttpResponseDecoder.class);
            if (channelHandlerContext == null && (channelHandlerContext = channelPipeline.context(HttpClientCodec.class)) == null) {
                return channelPromise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            }
            String string = "httpAggregator";
            channelPipeline.addAfter(channelHandlerContext.name(), string, new HttpObjectAggregator(8192));
            channelPipeline.addAfter(string, "handshaker", new SimpleChannelInboundHandler<FullHttpResponse>(this, channel, channelPromise){
                final Channel val$channel;
                final ChannelPromise val$promise;
                final WebSocketClientHandshaker this$0;
                {
                    this.this$0 = webSocketClientHandshaker;
                    this.val$channel = channel;
                    this.val$promise = channelPromise;
                }

                @Override
                protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
                    channelHandlerContext.pipeline().remove(this);
                    try {
                        this.this$0.finishHandshake(this.val$channel, fullHttpResponse);
                        this.val$promise.setSuccess();
                    } catch (Throwable throwable) {
                        this.val$promise.setFailure(throwable);
                    }
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                    channelHandlerContext.pipeline().remove(this);
                    this.val$promise.setFailure(throwable);
                }

                @Override
                public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                    this.val$promise.tryFailure(WebSocketClientHandshaker.access$000());
                    channelHandlerContext.fireChannelInactive();
                }

                @Override
                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                    this.channelRead0(channelHandlerContext, (FullHttpResponse)object);
                }
            });
            try {
                channelHandlerContext.fireChannelRead(ReferenceCountUtil.retain(httpResponse));
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        }
        return channelPromise;
    }

    protected abstract void verify(FullHttpResponse var1);

    protected abstract WebSocketFrameDecoder newWebsocketDecoder();

    protected abstract WebSocketFrameEncoder newWebSocketEncoder();

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
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
    }

    static String rawPath(URI uRI) {
        String string = uRI.getRawPath();
        String string2 = uRI.getRawQuery();
        if (string2 != null && !string2.isEmpty()) {
            string = string + '?' + string2;
        }
        return string == null || string.isEmpty() ? "/" : string;
    }

    static CharSequence websocketHostValue(URI uRI) {
        int n = uRI.getPort();
        if (n == -1) {
            return uRI.getHost();
        }
        String string = uRI.getHost();
        if (n == HttpScheme.HTTP.port()) {
            return HttpScheme.HTTP.name().contentEquals(uRI.getScheme()) || WebSocketScheme.WS.name().contentEquals(uRI.getScheme()) ? string : NetUtil.toSocketAddressString(string, n);
        }
        if (n == HttpScheme.HTTPS.port()) {
            return HttpScheme.HTTPS.name().contentEquals(uRI.getScheme()) || WebSocketScheme.WSS.name().contentEquals(uRI.getScheme()) ? string : NetUtil.toSocketAddressString(string, n);
        }
        return NetUtil.toSocketAddressString(string, n);
    }

    static CharSequence websocketOriginValue(URI uRI) {
        int n;
        String string;
        String string2 = uRI.getScheme();
        int n2 = uRI.getPort();
        if (WebSocketScheme.WSS.name().contentEquals(string2) || HttpScheme.HTTPS.name().contentEquals(string2) || string2 == null && n2 == WebSocketScheme.WSS.port()) {
            string = HTTPS_SCHEME_PREFIX;
            n = WebSocketScheme.WSS.port();
        } else {
            string = HTTP_SCHEME_PREFIX;
            n = WebSocketScheme.WS.port();
        }
        String string3 = uRI.getHost().toLowerCase(Locale.US);
        if (n2 != n && n2 != -1) {
            return string + NetUtil.toSocketAddressString(string3, n2);
        }
        return string + string3;
    }

    static ClosedChannelException access$000() {
        return CLOSED_CHANNEL_EXCEPTION;
    }
}

