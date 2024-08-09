/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.pool.PoolEntry;

@Deprecated
class HttpPoolEntry
extends PoolEntry<HttpRoute, OperatedClientConnection> {
    private final Log log;
    private final RouteTracker tracker;

    public HttpPoolEntry(Log log2, String string, HttpRoute httpRoute, OperatedClientConnection operatedClientConnection, long l, TimeUnit timeUnit) {
        super(string, httpRoute, operatedClientConnection, l, timeUnit);
        this.log = log2;
        this.tracker = new RouteTracker(httpRoute);
    }

    @Override
    public boolean isExpired(long l) {
        boolean bl = super.isExpired(l);
        if (bl && this.log.isDebugEnabled()) {
            this.log.debug("Connection " + this + " expired @ " + new Date(this.getExpiry()));
        }
        return bl;
    }

    RouteTracker getTracker() {
        return this.tracker;
    }

    HttpRoute getPlannedRoute() {
        return (HttpRoute)this.getRoute();
    }

    HttpRoute getEffectiveRoute() {
        return this.tracker.toRoute();
    }

    @Override
    public boolean isClosed() {
        OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.getConnection();
        return !operatedClientConnection.isOpen();
    }

    @Override
    public void close() {
        OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.getConnection();
        try {
            operatedClientConnection.close();
        } catch (IOException iOException) {
            this.log.debug("I/O error closing connection", iOException);
        }
    }
}

