/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.HttpPoolEntry;
import org.apache.http.impl.conn.ManagedClientConnectionImpl;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class BasicClientConnectionManager
implements ClientConnectionManager {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final AtomicLong COUNTER = new AtomicLong();
    public static final String MISUSE_MESSAGE = "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    private final SchemeRegistry schemeRegistry;
    private final ClientConnectionOperator connOperator;
    private HttpPoolEntry poolEntry;
    private ManagedClientConnectionImpl conn;
    private volatile boolean shutdown;

    public BasicClientConnectionManager(SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
    }

    public BasicClientConnectionManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void finalize() throws Throwable {
        try {
            this.shutdown();
        } finally {
            super.finalize();
        }
    }

    @Override
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    @Override
    public final ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
        return new ClientConnectionRequest(this, httpRoute, object){
            final HttpRoute val$route;
            final Object val$state;
            final BasicClientConnectionManager this$0;
            {
                this.this$0 = basicClientConnectionManager;
                this.val$route = httpRoute;
                this.val$state = object;
            }

            @Override
            public void abortRequest() {
            }

            @Override
            public ManagedClientConnection getConnection(long l, TimeUnit timeUnit) {
                return this.this$0.getConnection(this.val$route, this.val$state);
            }
        };
    }

    private void assertNotShutdown() {
        Asserts.check(!this.shutdown, "Connection manager has been shut down");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ManagedClientConnection getConnection(HttpRoute httpRoute, Object object) {
        Args.notNull(httpRoute, "Route");
        BasicClientConnectionManager basicClientConnectionManager = this;
        synchronized (basicClientConnectionManager) {
            long l;
            this.assertNotShutdown();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Get connection for route " + httpRoute);
            }
            Asserts.check(this.conn == null, MISUSE_MESSAGE);
            if (this.poolEntry != null && !this.poolEntry.getPlannedRoute().equals(httpRoute)) {
                this.poolEntry.close();
                this.poolEntry = null;
            }
            if (this.poolEntry == null) {
                String string = Long.toString(COUNTER.getAndIncrement());
                OperatedClientConnection operatedClientConnection = this.connOperator.createConnection();
                this.poolEntry = new HttpPoolEntry(this.log, string, httpRoute, operatedClientConnection, 0L, TimeUnit.MILLISECONDS);
            }
            if (this.poolEntry.isExpired(l = System.currentTimeMillis())) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
            this.conn = new ManagedClientConnectionImpl(this, this.connOperator, this.poolEntry);
            return this.conn;
        }
    }

    private void shutdownConnection(HttpClientConnection httpClientConnection) {
        block2: {
            try {
                httpClientConnection.shutdown();
            } catch (IOException iOException) {
                if (!this.log.isDebugEnabled()) break block2;
                this.log.debug("I/O exception shutting down connection", iOException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseConnection(ManagedClientConnection managedClientConnection, long l, TimeUnit timeUnit) {
        ManagedClientConnectionImpl managedClientConnectionImpl;
        Args.check(managedClientConnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
        ManagedClientConnectionImpl managedClientConnectionImpl2 = managedClientConnectionImpl = (ManagedClientConnectionImpl)managedClientConnection;
        synchronized (managedClientConnectionImpl2) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Releasing connection " + managedClientConnection);
            }
            if (managedClientConnectionImpl.getPoolEntry() == null) {
                return;
            }
            ClientConnectionManager clientConnectionManager = managedClientConnectionImpl.getManager();
            Asserts.check(clientConnectionManager == this, "Connection not obtained from this manager");
            BasicClientConnectionManager basicClientConnectionManager = this;
            synchronized (basicClientConnectionManager) {
                if (this.shutdown) {
                    this.shutdownConnection(managedClientConnectionImpl);
                    return;
                }
                try {
                    if (managedClientConnectionImpl.isOpen() && !managedClientConnectionImpl.isMarkedReusable()) {
                        this.shutdownConnection(managedClientConnectionImpl);
                    }
                    if (managedClientConnectionImpl.isMarkedReusable()) {
                        this.poolEntry.updateExpiry(l, timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS);
                        if (this.log.isDebugEnabled()) {
                            String string = l > 0L ? "for " + l + " " + (Object)((Object)timeUnit) : "indefinitely";
                            this.log.debug("Connection can be kept alive " + string);
                        }
                    }
                } finally {
                    managedClientConnectionImpl.detach();
                    this.conn = null;
                    if (this.poolEntry.isClosed()) {
                        this.poolEntry = null;
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeExpiredConnections() {
        BasicClientConnectionManager basicClientConnectionManager = this;
        synchronized (basicClientConnectionManager) {
            this.assertNotShutdown();
            long l = System.currentTimeMillis();
            if (this.poolEntry != null && this.poolEntry.isExpired(l)) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        BasicClientConnectionManager basicClientConnectionManager = this;
        synchronized (basicClientConnectionManager) {
            this.assertNotShutdown();
            long l2 = timeUnit.toMillis(l);
            if (l2 < 0L) {
                l2 = 0L;
            }
            long l3 = System.currentTimeMillis() - l2;
            if (this.poolEntry != null && this.poolEntry.getUpdated() <= l3) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        BasicClientConnectionManager basicClientConnectionManager = this;
        synchronized (basicClientConnectionManager) {
            this.shutdown = true;
            try {
                if (this.poolEntry != null) {
                    this.poolEntry.close();
                }
            } finally {
                this.poolEntry = null;
                this.conn = null;
            }
        }
    }
}

