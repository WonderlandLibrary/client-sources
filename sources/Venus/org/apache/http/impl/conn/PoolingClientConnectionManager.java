/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.HttpConnPool;
import org.apache.http.impl.conn.HttpPoolEntry;
import org.apache.http.impl.conn.ManagedClientConnectionImpl;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class PoolingClientConnectionManager
implements ClientConnectionManager,
ConnPoolControl<HttpRoute> {
    private final Log log = LogFactory.getLog(this.getClass());
    private final SchemeRegistry schemeRegistry;
    private final HttpConnPool pool;
    private final ClientConnectionOperator operator;
    private final DnsResolver dnsResolver;

    public PoolingClientConnectionManager(SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeRegistry, DnsResolver dnsResolver) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS, dnsResolver);
    }

    public PoolingClientConnectionManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeRegistry, long l, TimeUnit timeUnit) {
        this(schemeRegistry, l, timeUnit, new SystemDefaultDnsResolver());
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeRegistry, long l, TimeUnit timeUnit, DnsResolver dnsResolver) {
        Args.notNull(schemeRegistry, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = dnsResolver;
        this.operator = this.createConnectionOperator(schemeRegistry);
        this.pool = new HttpConnPool(this.log, this.operator, 2, 20, l, timeUnit);
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

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry, this.dnsResolver);
    }

    @Override
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    private String format(HttpRoute httpRoute, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[route: ").append(httpRoute).append("]");
        if (object != null) {
            stringBuilder.append("[state: ").append(object).append("]");
        }
        return stringBuilder.toString();
    }

    private String formatStats(HttpRoute httpRoute) {
        StringBuilder stringBuilder = new StringBuilder();
        PoolStats poolStats = this.pool.getTotalStats();
        PoolStats poolStats2 = this.pool.getStats(httpRoute);
        stringBuilder.append("[total kept alive: ").append(poolStats.getAvailable()).append("; ");
        stringBuilder.append("route allocated: ").append(poolStats2.getLeased() + poolStats2.getAvailable());
        stringBuilder.append(" of ").append(poolStats2.getMax()).append("; ");
        stringBuilder.append("total allocated: ").append(poolStats.getLeased() + poolStats.getAvailable());
        stringBuilder.append(" of ").append(poolStats.getMax()).append("]");
        return stringBuilder.toString();
    }

    private String format(HttpPoolEntry httpPoolEntry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[id: ").append(httpPoolEntry.getId()).append("]");
        stringBuilder.append("[route: ").append(httpPoolEntry.getRoute()).append("]");
        Object object = httpPoolEntry.getState();
        if (object != null) {
            stringBuilder.append("[state: ").append(object).append("]");
        }
        return stringBuilder.toString();
    }

    @Override
    public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
        Args.notNull(httpRoute, "HTTP route");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection request: " + this.format(httpRoute, object) + this.formatStats(httpRoute));
        }
        Future future = this.pool.lease(httpRoute, object);
        return new ClientConnectionRequest(this, future){
            final Future val$future;
            final PoolingClientConnectionManager this$0;
            {
                this.this$0 = poolingClientConnectionManager;
                this.val$future = future;
            }

            @Override
            public void abortRequest() {
                this.val$future.cancel(true);
            }

            @Override
            public ManagedClientConnection getConnection(long l, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return this.this$0.leaseConnection(this.val$future, l, timeUnit);
            }
        };
    }

    ManagedClientConnection leaseConnection(Future<HttpPoolEntry> future, long l, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
        try {
            HttpPoolEntry httpPoolEntry = future.get(l, timeUnit);
            if (httpPoolEntry == null || future.isCancelled()) {
                throw new InterruptedException();
            }
            Asserts.check(httpPoolEntry.getConnection() != null, "Pool entry with no connection");
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection leased: " + this.format(httpPoolEntry) + this.formatStats((HttpRoute)httpPoolEntry.getRoute()));
            }
            return new ManagedClientConnectionImpl(this, this.operator, httpPoolEntry);
        } catch (ExecutionException executionException) {
            Throwable throwable = executionException.getCause();
            if (throwable == null) {
                throwable = executionException;
            }
            this.log.error("Unexpected exception leasing connection from pool", throwable);
            throw new InterruptedException();
        } catch (TimeoutException timeoutException) {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseConnection(ManagedClientConnection managedClientConnection, long l, TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
        ManagedClientConnectionImpl managedClientConnectionImpl = (ManagedClientConnectionImpl)managedClientConnection;
        Asserts.check(managedClientConnectionImpl.getManager() == this, "Connection not obtained from this manager");
        ManagedClientConnectionImpl managedClientConnectionImpl2 = managedClientConnectionImpl;
        synchronized (managedClientConnectionImpl2) {
            HttpPoolEntry httpPoolEntry = managedClientConnectionImpl.detach();
            if (httpPoolEntry == null) {
                return;
            }
            try {
                block13: {
                    if (managedClientConnectionImpl.isOpen() && !managedClientConnectionImpl.isMarkedReusable()) {
                        try {
                            managedClientConnectionImpl.shutdown();
                        } catch (IOException iOException) {
                            if (!this.log.isDebugEnabled()) break block13;
                            this.log.debug("I/O exception shutting down released connection", iOException);
                        }
                    }
                }
                if (managedClientConnectionImpl.isMarkedReusable()) {
                    httpPoolEntry.updateExpiry(l, timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS);
                    if (this.log.isDebugEnabled()) {
                        String string = l > 0L ? "for " + l + " " + (Object)((Object)timeUnit) : "indefinitely";
                        this.log.debug("Connection " + this.format(httpPoolEntry) + " can be kept alive " + string);
                    }
                }
            } finally {
                this.pool.release(httpPoolEntry, managedClientConnectionImpl.isMarkedReusable());
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection released: " + this.format(httpPoolEntry) + this.formatStats((HttpRoute)httpPoolEntry.getRoute()));
            }
        }
    }

    @Override
    public void shutdown() {
        this.log.debug("Connection manager is shutting down");
        try {
            this.pool.shutdown();
        } catch (IOException iOException) {
            this.log.debug("I/O exception shutting down connection manager", iOException);
        }
        this.log.debug("Connection manager shut down");
    }

    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + l + " " + (Object)((Object)timeUnit));
        }
        this.pool.closeIdle(l, timeUnit);
    }

    @Override
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpired();
    }

    @Override
    public int getMaxTotal() {
        return this.pool.getMaxTotal();
    }

    @Override
    public void setMaxTotal(int n) {
        this.pool.setMaxTotal(n);
    }

    @Override
    public int getDefaultMaxPerRoute() {
        return this.pool.getDefaultMaxPerRoute();
    }

    @Override
    public void setDefaultMaxPerRoute(int n) {
        this.pool.setDefaultMaxPerRoute(n);
    }

    @Override
    public int getMaxPerRoute(HttpRoute httpRoute) {
        return this.pool.getMaxPerRoute(httpRoute);
    }

    @Override
    public void setMaxPerRoute(HttpRoute httpRoute, int n) {
        this.pool.setMaxPerRoute(httpRoute, n);
    }

    @Override
    public PoolStats getTotalStats() {
        return this.pool.getTotalStats();
    }

    @Override
    public PoolStats getStats(HttpRoute httpRoute) {
        return this.pool.getStats(httpRoute);
    }

    @Override
    public PoolStats getStats(Object object) {
        return this.getStats((HttpRoute)object);
    }

    @Override
    public int getMaxPerRoute(Object object) {
        return this.getMaxPerRoute((HttpRoute)object);
    }

    @Override
    public void setMaxPerRoute(Object object, int n) {
        this.setMaxPerRoute((HttpRoute)object, n);
    }
}

