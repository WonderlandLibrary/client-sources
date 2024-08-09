/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class DefaultHttpRoutePlanner
implements HttpRoutePlanner {
    protected final SchemeRegistry schemeRegistry;

    public DefaultHttpRoutePlanner(SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
    }

    @Override
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        Scheme scheme;
        Args.notNull(httpRequest, "HTTP request");
        HttpRoute httpRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (httpRoute != null) {
            return httpRoute;
        }
        Asserts.notNull(httpHost, "Target host");
        InetAddress inetAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        HttpHost httpHost2 = ConnRouteParams.getDefaultProxy(httpRequest.getParams());
        try {
            scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
        } catch (IllegalStateException illegalStateException) {
            throw new HttpException(illegalStateException.getMessage());
        }
        boolean bl = scheme.isLayered();
        httpRoute = httpHost2 == null ? new HttpRoute(httpHost, inetAddress, bl) : new HttpRoute(httpHost, inetAddress, httpHost2, bl);
        return httpRoute;
    }
}

