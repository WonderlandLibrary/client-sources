/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractClientConnAdapter;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract class AbstractPooledConnAdapter
extends AbstractClientConnAdapter {
    protected volatile AbstractPoolEntry poolEntry;

    protected AbstractPooledConnAdapter(ClientConnectionManager clientConnectionManager, AbstractPoolEntry abstractPoolEntry) {
        super(clientConnectionManager, abstractPoolEntry.connection);
        this.poolEntry = abstractPoolEntry;
    }

    @Override
    public String getId() {
        return null;
    }

    @Deprecated
    protected AbstractPoolEntry getPoolEntry() {
        return this.poolEntry;
    }

    protected void assertValid(AbstractPoolEntry abstractPoolEntry) {
        if (this.isReleased() || abstractPoolEntry == null) {
            throw new ConnectionShutdownException();
        }
    }

    @Deprecated
    protected final void assertAttached() {
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
    }

    @Override
    protected synchronized void detach() {
        this.poolEntry = null;
        super.detach();
    }

    @Override
    public HttpRoute getRoute() {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        return abstractPoolEntry.tracker == null ? null : abstractPoolEntry.tracker.toRoute();
    }

    @Override
    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.open(httpRoute, httpContext, httpParams);
    }

    @Override
    public void tunnelTarget(boolean bl, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.tunnelTarget(bl, httpParams);
    }

    @Override
    public void tunnelProxy(HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.tunnelProxy(httpHost, bl, httpParams);
    }

    @Override
    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.layerProtocol(httpContext, httpParams);
    }

    @Override
    public void close() throws IOException {
        OperatedClientConnection operatedClientConnection;
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        if (abstractPoolEntry != null) {
            abstractPoolEntry.shutdownEntry();
        }
        if ((operatedClientConnection = this.getWrappedConnection()) != null) {
            operatedClientConnection.close();
        }
    }

    @Override
    public void shutdown() throws IOException {
        OperatedClientConnection operatedClientConnection;
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        if (abstractPoolEntry != null) {
            abstractPoolEntry.shutdownEntry();
        }
        if ((operatedClientConnection = this.getWrappedConnection()) != null) {
            operatedClientConnection.shutdown();
        }
    }

    @Override
    public Object getState() {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        return abstractPoolEntry.getState();
    }

    @Override
    public void setState(Object object) {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.setState(object);
    }
}

