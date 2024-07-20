/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public interface HttpResponse
extends HttpMessage {
    @Deprecated
    public HttpResponseStatus getStatus();

    public HttpResponseStatus status();

    public HttpResponse setStatus(HttpResponseStatus var1);

    @Override
    public HttpResponse setProtocolVersion(HttpVersion var1);
}

