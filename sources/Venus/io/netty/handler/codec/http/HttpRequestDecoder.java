/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpVersion;

public class HttpRequestDecoder
extends HttpObjectDecoder {
    public HttpRequestDecoder() {
    }

    public HttpRequestDecoder(int n, int n2, int n3) {
        super(n, n2, n3, true);
    }

    public HttpRequestDecoder(int n, int n2, int n3, boolean bl) {
        super(n, n2, n3, true, bl);
    }

    public HttpRequestDecoder(int n, int n2, int n3, boolean bl, int n4) {
        super(n, n2, n3, true, bl, n4);
    }

    @Override
    protected HttpMessage createMessage(String[] stringArray) throws Exception {
        return new DefaultHttpRequest(HttpVersion.valueOf(stringArray[0]), HttpMethod.valueOf(stringArray[5]), stringArray[5], this.validateHeaders);
    }

    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", this.validateHeaders);
    }

    @Override
    protected boolean isDecodingRequest() {
        return false;
    }
}

