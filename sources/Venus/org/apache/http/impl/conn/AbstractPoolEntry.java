/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
public abstract class AbstractPoolEntry {
    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected volatile HttpRoute route;
    protected volatile Object state;
    protected volatile RouteTracker tracker;

    protected AbstractPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        Args.notNull(clientConnectionOperator, "Connection operator");
        this.connOperator = clientConnectionOperator;
        this.connection = clientConnectionOperator.createConnection();
        this.route = httpRoute;
        this.tracker = null;
    }

    public Object getState() {
        return this.state;
    }

    public void setState(Object object) {
        this.state = object;
    }

    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException {
        Args.notNull(httpRoute, "Route");
        Args.notNull(httpParams, "HTTP parameters");
        if (this.tracker != null) {
            Asserts.check(!this.tracker.isConnected(), "Connection already open");
        }
        this.tracker = new RouteTracker(httpRoute);
        HttpHost httpHost = httpRoute.getProxyHost();
        this.connOperator.openConnection(this.connection, httpHost != null ? httpHost : httpRoute.getTargetHost(), httpRoute.getLocalAddress(), httpContext, httpParams);
        RouteTracker routeTracker = this.tracker;
        if (routeTracker == null) {
            throw new InterruptedIOException("Request aborted");
        }
        if (httpHost == null) {
            routeTracker.connectTarget(this.connection.isSecure());
        } else {
            routeTracker.connectProxy(httpHost, this.connection.isSecure());
        }
    }

    public void tunnelTarget(boolean bl, HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        Asserts.check(!this.tracker.isTunnelled(), "Connection is already tunnelled");
        this.connection.update(null, this.tracker.getTargetHost(), bl, httpParams);
        this.tracker.tunnelTarget(bl);
    }

    public void tunnelProxy(HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        Args.notNull(httpHost, "Next proxy");
        Args.notNull(httpParams, "Parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        this.connection.update(null, httpHost, bl, httpParams);
        this.tracker.tunnelProxy(httpHost, bl);
    }

    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        Asserts.check(this.tracker.isTunnelled(), "Protocol layering without a tunnel not supported");
        Asserts.check(!this.tracker.isLayered(), "Multiple protocol layering not supported");
        HttpHost httpHost = this.tracker.getTargetHost();
        this.connOperator.updateSecureConnection(this.connection, httpHost, httpContext, httpParams);
        this.tracker.layerProtocol(this.connection.isSecure());
    }

    protected void shutdownEntry() {
        this.tracker = null;
        this.state = null;
    }
}

