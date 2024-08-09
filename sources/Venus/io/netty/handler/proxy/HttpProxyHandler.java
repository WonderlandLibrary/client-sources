/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.proxy.ProxyConnectException;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class HttpProxyHandler
extends ProxyHandler {
    private static final String PROTOCOL = "http";
    private static final String AUTH_BASIC = "basic";
    private final HttpClientCodec codec = new HttpClientCodec();
    private final String username;
    private final String password;
    private final CharSequence authorization;
    private final boolean ignoreDefaultPortsInConnectHostHeader;
    private HttpResponseStatus status;
    private HttpHeaders headers;

    public HttpProxyHandler(SocketAddress socketAddress) {
        this(socketAddress, null);
    }

    public HttpProxyHandler(SocketAddress socketAddress, HttpHeaders httpHeaders) {
        this(socketAddress, httpHeaders, false);
    }

    public HttpProxyHandler(SocketAddress socketAddress, HttpHeaders httpHeaders, boolean bl) {
        super(socketAddress);
        this.username = null;
        this.password = null;
        this.authorization = null;
        this.headers = httpHeaders;
        this.ignoreDefaultPortsInConnectHostHeader = bl;
    }

    public HttpProxyHandler(SocketAddress socketAddress, String string, String string2) {
        this(socketAddress, string, string2, null);
    }

    public HttpProxyHandler(SocketAddress socketAddress, String string, String string2, HttpHeaders httpHeaders) {
        this(socketAddress, string, string2, httpHeaders, false);
    }

    public HttpProxyHandler(SocketAddress socketAddress, String string, String string2, HttpHeaders httpHeaders, boolean bl) {
        super(socketAddress);
        if (string == null) {
            throw new NullPointerException("username");
        }
        if (string2 == null) {
            throw new NullPointerException("password");
        }
        this.username = string;
        this.password = string2;
        ByteBuf byteBuf = Unpooled.copiedBuffer(string + ':' + string2, CharsetUtil.UTF_8);
        ByteBuf byteBuf2 = Base64.encode(byteBuf, false);
        this.authorization = new AsciiString("Basic " + byteBuf2.toString(CharsetUtil.US_ASCII));
        byteBuf.release();
        byteBuf2.release();
        this.headers = httpHeaders;
        this.ignoreDefaultPortsInConnectHostHeader = bl;
    }

    @Override
    public String protocol() {
        return PROTOCOL;
    }

    @Override
    public String authScheme() {
        return this.authorization != null ? AUTH_BASIC : "none";
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    @Override
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        String string = channelHandlerContext.name();
        channelPipeline.addBefore(string, null, this.codec);
    }

    @Override
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.codec.removeOutboundHandler();
    }

    @Override
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.codec.removeInboundHandler();
    }

    @Override
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress)this.destinationAddress();
        String string = HttpUtil.formatHostnameForHttp(inetSocketAddress);
        int n = inetSocketAddress.getPort();
        String string2 = string + ":" + n;
        String string3 = this.ignoreDefaultPortsInConnectHostHeader && (n == 80 || n == 443) ? string : string2;
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.CONNECT, string2, Unpooled.EMPTY_BUFFER, false);
        defaultFullHttpRequest.headers().set((CharSequence)HttpHeaderNames.HOST, (Object)string3);
        if (this.authorization != null) {
            defaultFullHttpRequest.headers().set((CharSequence)HttpHeaderNames.PROXY_AUTHORIZATION, (Object)this.authorization);
        }
        if (this.headers != null) {
            defaultFullHttpRequest.headers().add(this.headers);
        }
        return defaultFullHttpRequest;
    }

    @Override
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        boolean bl;
        if (object instanceof HttpResponse) {
            if (this.status != null) {
                throw new ProxyConnectException(this.exceptionMessage("too many responses"));
            }
            this.status = ((HttpResponse)object).status();
        }
        if (bl = object instanceof LastHttpContent) {
            if (this.status == null) {
                throw new ProxyConnectException(this.exceptionMessage("missing response"));
            }
            if (this.status.code() != 200) {
                throw new ProxyConnectException(this.exceptionMessage("status: " + this.status));
            }
        }
        return bl;
    }
}

