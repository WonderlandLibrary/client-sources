/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpResponseDecoder
extends HttpObjectDecoder {
    private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");

    public HttpResponseDecoder() {
    }

    public HttpResponseDecoder(int n, int n2, int n3) {
        super(n, n2, n3, true);
    }

    public HttpResponseDecoder(int n, int n2, int n3, boolean bl) {
        super(n, n2, n3, true, bl);
    }

    public HttpResponseDecoder(int n, int n2, int n3, boolean bl, int n4) {
        super(n, n2, n3, true, bl, n4);
    }

    @Override
    protected HttpMessage createMessage(String[] stringArray) {
        return new DefaultHttpResponse(HttpVersion.valueOf(stringArray[0]), HttpResponseStatus.valueOf(Integer.parseInt(stringArray[5]), stringArray[5]), this.validateHeaders);
    }

    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, UNKNOWN_STATUS, this.validateHeaders);
    }

    @Override
    protected boolean isDecodingRequest() {
        return true;
    }
}

