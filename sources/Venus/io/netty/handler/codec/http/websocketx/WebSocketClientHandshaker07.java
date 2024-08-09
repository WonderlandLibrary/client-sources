/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocket07FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket07FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketUtil;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.URI;

public class WebSocketClientHandshaker07
extends WebSocketClientHandshaker {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker07.class);
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private String expectedChallengeResponseString;
    private final boolean allowExtensions;
    private final boolean performMasking;
    private final boolean allowMaskMismatch;

    public WebSocketClientHandshaker07(URI uRI, WebSocketVersion webSocketVersion, String string, boolean bl, HttpHeaders httpHeaders, int n) {
        this(uRI, webSocketVersion, string, bl, httpHeaders, n, true, false);
    }

    public WebSocketClientHandshaker07(URI uRI, WebSocketVersion webSocketVersion, String string, boolean bl, HttpHeaders httpHeaders, int n, boolean bl2, boolean bl3) {
        super(uRI, webSocketVersion, string, httpHeaders, n);
        this.allowExtensions = bl;
        this.performMasking = bl2;
        this.allowMaskMismatch = bl3;
    }

    @Override
    protected FullHttpRequest newHandshakeRequest() {
        URI uRI = this.uri();
        String string = WebSocketClientHandshaker07.rawPath(uRI);
        byte[] byArray = WebSocketUtil.randomBytes(16);
        String string2 = WebSocketUtil.base64(byArray);
        String string3 = string2 + MAGIC_GUID;
        byte[] byArray2 = WebSocketUtil.sha1(string3.getBytes(CharsetUtil.US_ASCII));
        this.expectedChallengeResponseString = WebSocketUtil.base64(byArray2);
        if (logger.isDebugEnabled()) {
            logger.debug("WebSocket version 07 client handshake key: {}, expected response: {}", (Object)string2, (Object)this.expectedChallengeResponseString);
        }
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, string);
        HttpHeaders httpHeaders = defaultFullHttpRequest.headers();
        httpHeaders.add((CharSequence)HttpHeaderNames.UPGRADE, (Object)HttpHeaderValues.WEBSOCKET).add((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.UPGRADE).add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY, (Object)string2).add((CharSequence)HttpHeaderNames.HOST, (Object)WebSocketClientHandshaker07.websocketHostValue(uRI)).add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, (Object)WebSocketClientHandshaker07.websocketOriginValue(uRI));
        String string4 = this.expectedSubprotocol();
        if (string4 != null && !string4.isEmpty()) {
            httpHeaders.add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, (Object)string4);
        }
        httpHeaders.add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION, (Object)"7");
        if (this.customHeaders != null) {
            httpHeaders.add(this.customHeaders);
        }
        return defaultFullHttpRequest;
    }

    @Override
    protected void verify(FullHttpResponse fullHttpResponse) {
        HttpResponseStatus httpResponseStatus = HttpResponseStatus.SWITCHING_PROTOCOLS;
        HttpHeaders httpHeaders = fullHttpResponse.headers();
        if (!fullHttpResponse.status().equals(httpResponseStatus)) {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + fullHttpResponse.status());
        }
        String string = httpHeaders.get(HttpHeaderNames.UPGRADE);
        if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(string)) {
            throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + string);
        }
        if (!httpHeaders.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, false)) {
            throw new WebSocketHandshakeException("Invalid handshake response connection: " + httpHeaders.get(HttpHeaderNames.CONNECTION));
        }
        String string2 = httpHeaders.get(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
        if (string2 == null || !string2.equals(this.expectedChallengeResponseString)) {
            throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", string2, this.expectedChallengeResponseString));
        }
    }

    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket07FrameDecoder(false, this.allowExtensions, this.maxFramePayloadLength(), this.allowMaskMismatch);
    }

    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket07FrameEncoder(this.performMasking);
    }
}

