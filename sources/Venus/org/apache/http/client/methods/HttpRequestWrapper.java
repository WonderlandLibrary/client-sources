/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

public class HttpRequestWrapper
extends AbstractHttpMessage
implements HttpUriRequest {
    private final HttpRequest original;
    private final HttpHost target;
    private final String method;
    private RequestLine requestLine;
    private ProtocolVersion version;
    private URI uri;

    private HttpRequestWrapper(HttpRequest httpRequest, HttpHost httpHost) {
        this.original = Args.notNull(httpRequest, "HTTP request");
        this.target = httpHost;
        this.version = this.original.getRequestLine().getProtocolVersion();
        this.method = this.original.getRequestLine().getMethod();
        this.uri = httpRequest instanceof HttpUriRequest ? ((HttpUriRequest)httpRequest).getURI() : null;
        this.setHeaders(httpRequest.getAllHeaders());
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.version != null ? this.version : this.original.getProtocolVersion();
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
        this.requestLine = null;
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    public void setURI(URI uRI) {
        this.uri = uRI;
        this.requestLine = null;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAborted() {
        return true;
    }

    @Override
    public RequestLine getRequestLine() {
        if (this.requestLine == null) {
            String string = this.uri != null ? this.uri.toASCIIString() : this.original.getRequestLine().getUri();
            if (string == null || string.isEmpty()) {
                string = "/";
            }
            this.requestLine = new BasicRequestLine(this.method, string, this.getProtocolVersion());
        }
        return this.requestLine;
    }

    public HttpRequest getOriginal() {
        return this.original;
    }

    public HttpHost getTarget() {
        return this.target;
    }

    public String toString() {
        return this.getRequestLine() + " " + this.headergroup;
    }

    public static HttpRequestWrapper wrap(HttpRequest httpRequest) {
        return HttpRequestWrapper.wrap(httpRequest, null);
    }

    public static HttpRequestWrapper wrap(HttpRequest httpRequest, HttpHost httpHost) {
        Args.notNull(httpRequest, "HTTP request");
        return httpRequest instanceof HttpEntityEnclosingRequest ? new HttpEntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest, httpHost) : new HttpRequestWrapper(httpRequest, httpHost);
    }

    @Override
    @Deprecated
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = this.original.getParams().copy();
        }
        return this.params;
    }

    HttpRequestWrapper(HttpRequest httpRequest, HttpHost httpHost, 1 var3_3) {
        this(httpRequest, httpHost);
    }

    static class 1 {
    }

    static class HttpEntityEnclosingRequestWrapper
    extends HttpRequestWrapper
    implements HttpEntityEnclosingRequest {
        private HttpEntity entity;

        HttpEntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest, HttpHost httpHost) {
            super(httpEntityEnclosingRequest, httpHost, null);
            this.entity = httpEntityEnclosingRequest.getEntity();
        }

        @Override
        public HttpEntity getEntity() {
            return this.entity;
        }

        @Override
        public void setEntity(HttpEntity httpEntity) {
            this.entity = httpEntity;
        }

        @Override
        public boolean expectContinue() {
            Header header = this.getFirstHeader("Expect");
            return header != null && "100-continue".equalsIgnoreCase(header.getValue());
        }
    }
}

