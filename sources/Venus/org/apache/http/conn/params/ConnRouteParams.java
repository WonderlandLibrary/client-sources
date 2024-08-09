/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class ConnRouteParams
implements ConnRoutePNames {
    public static final HttpHost NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
    public static final HttpRoute NO_ROUTE = new HttpRoute(NO_HOST);

    private ConnRouteParams() {
    }

    public static HttpHost getDefaultProxy(HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        HttpHost httpHost = (HttpHost)httpParams.getParameter("http.route.default-proxy");
        if (httpHost != null && NO_HOST.equals(httpHost)) {
            httpHost = null;
        }
        return httpHost;
    }

    public static void setDefaultProxy(HttpParams httpParams, HttpHost httpHost) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.default-proxy", httpHost);
    }

    public static HttpRoute getForcedRoute(HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        HttpRoute httpRoute = (HttpRoute)httpParams.getParameter("http.route.forced-route");
        if (httpRoute != null && NO_ROUTE.equals(httpRoute)) {
            httpRoute = null;
        }
        return httpRoute;
    }

    public static void setForcedRoute(HttpParams httpParams, HttpRoute httpRoute) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.forced-route", httpRoute);
    }

    public static InetAddress getLocalAddress(HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        InetAddress inetAddress = (InetAddress)httpParams.getParameter("http.route.local-address");
        return inetAddress;
    }

    public static void setLocalAddress(HttpParams httpParams, InetAddress inetAddress) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.local-address", inetAddress);
    }
}

