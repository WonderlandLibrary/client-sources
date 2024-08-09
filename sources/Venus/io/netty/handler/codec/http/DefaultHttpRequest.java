/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.DefaultHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMessageUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultHttpRequest
extends DefaultHttpMessage
implements HttpRequest {
    private static final int HASH_CODE_PRIME = 31;
    private HttpMethod method;
    private String uri;

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string) {
        this(httpVersion, httpMethod, string, true);
    }

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, boolean bl) {
        super(httpVersion, bl, false);
        this.method = ObjectUtil.checkNotNull(httpMethod, "method");
        this.uri = ObjectUtil.checkNotNull(string, "uri");
    }

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, HttpHeaders httpHeaders) {
        super(httpVersion, httpHeaders);
        this.method = ObjectUtil.checkNotNull(httpMethod, "method");
        this.uri = ObjectUtil.checkNotNull(string, "uri");
    }

    @Override
    @Deprecated
    public HttpMethod getMethod() {
        return this.method();
    }

    @Override
    public HttpMethod method() {
        return this.method;
    }

    @Override
    @Deprecated
    public String getUri() {
        return this.uri();
    }

    @Override
    public String uri() {
        return this.uri;
    }

    @Override
    public HttpRequest setMethod(HttpMethod httpMethod) {
        if (httpMethod == null) {
            throw new NullPointerException("method");
        }
        this.method = httpMethod;
        return this;
    }

    @Override
    public HttpRequest setUri(String string) {
        if (string == null) {
            throw new NullPointerException("uri");
        }
        this.uri = string;
        return this;
    }

    @Override
    public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = 31 * n + this.method.hashCode();
        n = 31 * n + this.uri.hashCode();
        n = 31 * n + super.hashCode();
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttpRequest)) {
            return true;
        }
        DefaultHttpRequest defaultHttpRequest = (DefaultHttpRequest)object;
        return this.method().equals(defaultHttpRequest.method()) && this.uri().equalsIgnoreCase(defaultHttpRequest.uri()) && super.equals(object);
    }

    public String toString() {
        return HttpMessageUtil.appendRequest(new StringBuilder(256), this).toString();
    }

    @Override
    public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
        return this.setProtocolVersion(httpVersion);
    }
}

