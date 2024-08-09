/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPooledConnAdapter;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class ThreadSafeClientConnManager
implements ClientConnectionManager {
    private final Log log;
    protected final SchemeRegistry schemeRegistry;
    protected final AbstractConnPool connectionPool;
    protected final ConnPoolByRoute pool;
    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }

    public ThreadSafeClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry, long l, TimeUnit timeUnit) {
        this(schemeRegistry, l, timeUnit, new ConnPerRouteBean());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry, long l, TimeUnit timeUnit, ConnPerRouteBean connPerRouteBean) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = schemeRegistry;
        this.connPerRoute = connPerRouteBean;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.pool = this.createConnectionPool(l, timeUnit);
        this.connectionPool = this.pool;
    }

    @Deprecated
    public ThreadSafeClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = schemeRegistry;
        this.connPerRoute = new ConnPerRouteBean();
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.pool = (ConnPoolByRoute)this.createConnectionPool(httpParams);
        this.connectionPool = this.pool;
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

    @Deprecated
    protected AbstractConnPool createConnectionPool(HttpParams httpParams) {
        return new ConnPoolByRoute(this.connOperator, httpParams);
    }

    protected ConnPoolByRoute createConnectionPool(long l, TimeUnit timeUnit) {
        return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, l, timeUnit);
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    @Override
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    @Override
    public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
        PoolEntryRequest poolEntryRequest = this.pool.requestPoolEntry(httpRoute, object);
        return new ClientConnectionRequest(this, poolEntryRequest, httpRoute){
            final PoolEntryRequest val$poolRequest;
            final HttpRoute val$route;
            final ThreadSafeClientConnManager this$0;
            {
                this.this$0 = threadSafeClientConnManager;
                this.val$poolRequest = poolEntryRequest;
                this.val$route = httpRoute;
            }

            @Override
            public void abortRequest() {
                this.val$poolRequest.abortRequest();
            }

            @Override
            public ManagedClientConnection getConnection(long l, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                Args.notNull(this.val$route, "Route");
                if (ThreadSafeClientConnManager.access$000(this.this$0).isDebugEnabled()) {
                    ThreadSafeClientConnManager.access$000(this.this$0).debug("Get connection: " + this.val$route + ", timeout = " + l);
                }
                BasicPoolEntry basicPoolEntry = this.val$poolRequest.getPoolEntry(l, timeUnit);
                return new BasicPooledConnAdapter(this.this$0, (AbstractPoolEntry)basicPoolEntry);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseConnection(ManagedClientConnection managedClientConnection, long l, TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof BasicPooledConnAdapter, "Connection class mismatch, connection not obtained from this manager");
        BasicPooledConnAdapter basicPooledConnAdapter = (BasicPooledConnAdapter)managedClientConnection;
        if (basicPooledConnAdapter.getPoolEntry() != null) {
            Asserts.check(basicPooledConnAdapter.getManager() == this, "Connection not obtained from this manager");
        }
        BasicPooledConnAdapter basicPooledConnAdapter2 = basicPooledConnAdapter;
        synchronized (basicPooledConnAdapter2) {
            BasicPoolEntry basicPoolEntry = (BasicPoolEntry)basicPooledConnAdapter.getPoolEntry();
            if (basicPoolEntry == null) {
                return;
            }
            try {
                if (basicPooledConnAdapter.isOpen() && !basicPooledConnAdapter.isMarkedReusable()) {
                    basicPooledConnAdapter.shutdown();
                }
            } catch (IOException iOException) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Exception shutting down released connection.", iOException);
                }
            } finally {
                boolean bl = basicPooledConnAdapter.isMarkedReusable();
                if (this.log.isDebugEnabled()) {
                    if (bl) {
                        this.log.debug("Released connection is reusable.");
                    } else {
                        this.log.debug("Released connection is not reusable.");
                    }
                }
                basicPooledConnAdapter.detach();
                this.pool.freeEntry(basicPoolEntry, bl, l, timeUnit);
            }
        }
    }

    @Override
    public void shutdown() {
        this.log.debug("Shutting down");
        this.pool.shutdown();
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        return this.pool.getConnectionsInPool(httpRoute);
    }

    public int getConnectionsInPool() {
        return this.pool.getConnectionsInPool();
    }

    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + l + " " + (Object)((Object)timeUnit));
        }
        this.pool.closeIdleConnections(l, timeUnit);
    }

    @Override
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpiredConnections();
    }

    public int getMaxTotal() {
        return this.pool.getMaxTotalConnections();
    }

    public void setMaxTotal(int n) {
        this.pool.setMaxTotalConnections(n);
    }

    public int getDefaultMaxPerRoute() {
        return this.connPerRoute.getDefaultMaxPerRoute();
    }

    public void setDefaultMaxPerRoute(int n) {
        this.connPerRoute.setDefaultMaxPerRoute(n);
    }

    public int getMaxForRoute(HttpRoute httpRoute) {
        return this.connPerRoute.getMaxForRoute(httpRoute);
    }

    public void setMaxForRoute(HttpRoute httpRoute, int n) {
        this.connPerRoute.setMaxForRoute(httpRoute, n);
    }

    static Log access$000(ThreadSafeClientConnManager threadSafeClientConnManager) {
        return threadSafeClientConnManager.log;
    }
}

