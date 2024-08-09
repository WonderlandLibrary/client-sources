/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.CombinedHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpObject;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.internal.ObjectUtil;

public abstract class DefaultHttpMessage
extends DefaultHttpObject
implements HttpMessage {
    private static final int HASH_CODE_PRIME = 31;
    private HttpVersion version;
    private final HttpHeaders headers;

    protected DefaultHttpMessage(HttpVersion httpVersion) {
        this(httpVersion, true, false);
    }

    protected DefaultHttpMessage(HttpVersion httpVersion, boolean bl, boolean bl2) {
        this(httpVersion, bl2 ? new CombinedHttpHeaders(bl) : new DefaultHttpHeaders(bl));
    }

    protected DefaultHttpMessage(HttpVersion httpVersion, HttpHeaders httpHeaders) {
        this.version = ObjectUtil.checkNotNull(httpVersion, "version");
        this.headers = ObjectUtil.checkNotNull(httpHeaders, "headers");
    }

    @Override
    public HttpHeaders headers() {
        return this.headers;
    }

    @Override
    @Deprecated
    public HttpVersion getProtocolVersion() {
        return this.protocolVersion();
    }

    @Override
    public HttpVersion protocolVersion() {
        return this.version;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = 31 * n + this.headers.hashCode();
        n = 31 * n + this.version.hashCode();
        n = 31 * n + super.hashCode();
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttpMessage)) {
            return true;
        }
        DefaultHttpMessage defaultHttpMessage = (DefaultHttpMessage)object;
        return this.headers().equals(defaultHttpMessage.headers()) && this.protocolVersion().equals(defaultHttpMessage.protocolVersion()) && super.equals(object);
    }

    @Override
    public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
        if (httpVersion == null) {
            throw new NullPointerException("version");
        }
        this.version = httpVersion;
        return this;
    }
}

