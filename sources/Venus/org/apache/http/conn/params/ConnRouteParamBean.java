/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ConnRouteParamBean
extends HttpAbstractParamBean {
    public ConnRouteParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setDefaultProxy(HttpHost httpHost) {
        this.params.setParameter("http.route.default-proxy", httpHost);
    }

    public void setLocalAddress(InetAddress inetAddress) {
        this.params.setParameter("http.route.local-address", inetAddress);
    }

    public void setForcedRoute(HttpRoute httpRoute) {
        this.params.setParameter("http.route.forced-route", httpRoute);
    }
}

