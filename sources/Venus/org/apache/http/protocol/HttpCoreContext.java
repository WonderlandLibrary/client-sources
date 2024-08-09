/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class HttpCoreContext
implements HttpContext {
    public static final String HTTP_CONNECTION = "http.connection";
    public static final String HTTP_REQUEST = "http.request";
    public static final String HTTP_RESPONSE = "http.response";
    public static final String HTTP_TARGET_HOST = "http.target_host";
    public static final String HTTP_REQ_SENT = "http.request_sent";
    private final HttpContext context;

    public static HttpCoreContext create() {
        return new HttpCoreContext(new BasicHttpContext());
    }

    public static HttpCoreContext adapt(HttpContext httpContext) {
        Args.notNull(httpContext, "HTTP context");
        return httpContext instanceof HttpCoreContext ? (HttpCoreContext)httpContext : new HttpCoreContext(httpContext);
    }

    public HttpCoreContext(HttpContext httpContext) {
        this.context = httpContext;
    }

    public HttpCoreContext() {
        this.context = new BasicHttpContext();
    }

    @Override
    public Object getAttribute(String string) {
        return this.context.getAttribute(string);
    }

    @Override
    public void setAttribute(String string, Object object) {
        this.context.setAttribute(string, object);
    }

    @Override
    public Object removeAttribute(String string) {
        return this.context.removeAttribute(string);
    }

    public <T> T getAttribute(String string, Class<T> clazz) {
        Args.notNull(clazz, "Attribute class");
        Object object = this.getAttribute(string);
        if (object == null) {
            return null;
        }
        return clazz.cast(object);
    }

    public <T extends HttpConnection> T getConnection(Class<T> clazz) {
        return (T)((HttpConnection)this.getAttribute(HTTP_CONNECTION, clazz));
    }

    public HttpConnection getConnection() {
        return this.getAttribute(HTTP_CONNECTION, HttpConnection.class);
    }

    public HttpRequest getRequest() {
        return this.getAttribute(HTTP_REQUEST, HttpRequest.class);
    }

    public boolean isRequestSent() {
        Boolean bl = this.getAttribute(HTTP_REQ_SENT, Boolean.class);
        return bl != null && bl != false;
    }

    public HttpResponse getResponse() {
        return this.getAttribute(HTTP_RESPONSE, HttpResponse.class);
    }

    public void setTargetHost(HttpHost httpHost) {
        this.setAttribute(HTTP_TARGET_HOST, httpHost);
    }

    public HttpHost getTargetHost() {
        return this.getAttribute(HTTP_TARGET_HOST, HttpHost.class);
    }
}

