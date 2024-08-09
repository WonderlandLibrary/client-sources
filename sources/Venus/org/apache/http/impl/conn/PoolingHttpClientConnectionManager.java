/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.CPool;
import org.apache.http.impl.conn.CPoolEntry;
import org.apache.http.impl.conn.CPoolProxy;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.pool.PoolEntry;
import org.apache.http.pool.PoolEntryCallback;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class PoolingHttpClientConnectionManager
implements HttpClientConnectionManager,
ConnPoolControl<HttpRoute>,
Closeable {
    private final Log log = LogFactory.getLog(this.getClass());
    private final ConfigData configData = new ConfigData();
    private final CPool pool;
    private final HttpClientConnectionOperator connectionOperator;
    private final AtomicBoolean isShutDown;

    private static Registry<ConnectionSocketFactory> getDefaultRegistry() {
        return RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", (PlainConnectionSocketFactory)((Object)SSLConnectionSocketFactory.getSocketFactory())).build();
    }

    public PoolingHttpClientConnectionManager() {
        this(PoolingHttpClientConnectionManager.getDefaultRegistry());
    }

    public PoolingHttpClientConnectionManager(long l, TimeUnit timeUnit) {
        this(PoolingHttpClientConnectionManager.getDefaultRegistry(), null, null, null, l, timeUnit);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry) {
        this(registry, null, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, DnsResolver dnsResolver) {
        this(registry, null, dnsResolver);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this(registry, httpConnectionFactory, null);
    }

    public PoolingHttpClientConnectionManager(HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this(PoolingHttpClientConnectionManager.getDefaultRegistry(), httpConnectionFactory, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, DnsResolver dnsResolver) {
        this(registry, httpConnectionFactory, null, dnsResolver, -1L, TimeUnit.MILLISECONDS);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, SchemePortResolver schemePortResolver, DnsResolver dnsResolver, long l, TimeUnit timeUnit) {
        this(new DefaultHttpClientConnectionOperator(registry, schemePortResolver, dnsResolver), httpConnectionFactory, l, timeUnit);
    }

    public PoolingHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, long l, TimeUnit timeUnit) {
        this.pool = new CPool(new InternalConnectionFactory(this.configData, httpConnectionFactory), 2, 20, l, timeUnit);
        this.pool.setValidateAfterInactivity(2000);
        this.connectionOperator = Args.notNull(httpClientConnectionOperator, "HttpClientConnectionOperator");
        this.isShutDown = new AtomicBoolean(false);
    }

    PoolingHttpClientConnectionManager(CPool cPool, Lookup<ConnectionSocketFactory> lookup, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
        this.pool = cPool;
        this.connectionOperator = new DefaultHttpClientConnectionOperator(lookup, schemePortResolver, dnsResolver);
        this.isShutDown = new AtomicBoolean(false);
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
    public void close() {
        this.shutdown();
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
        stringBuilder.append("[total available: ").append(poolStats.getAvailable()).append("; ");
        stringBuilder.append("route allocated: ").append(poolStats2.getLeased() + poolStats2.getAvailable());
        stringBuilder.append(" of ").append(poolStats2.getMax()).append("; ");
        stringBuilder.append("total allocated: ").append(poolStats.getLeased() + poolStats.getAvailable());
        stringBuilder.append(" of ").append(poolStats.getMax()).append("]");
        return stringBuilder.toString();
    }

    private String format(CPoolEntry cPoolEntry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[id: ").append(cPoolEntry.getId()).append("]");
        stringBuilder.append("[route: ").append(cPoolEntry.getRoute()).append("]");
        Object object = cPoolEntry.getState();
        if (object != null) {
            stringBuilder.append("[state: ").append(object).append("]");
        }
        return stringBuilder.toString();
    }

    private SocketConfig resolveSocketConfig(HttpHost httpHost) {
        SocketConfig socketConfig = this.configData.getSocketConfig(httpHost);
        if (socketConfig == null) {
            socketConfig = this.configData.getDefaultSocketConfig();
        }
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        return socketConfig;
    }

    @Override
    public ConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
        Args.notNull(httpRoute, "HTTP route");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection request: " + this.format(httpRoute, object) + this.formatStats(httpRoute));
        }
        Asserts.check(!this.isShutDown.get(), "Connection pool shut down");
        Future future = this.pool.lease(httpRoute, object, null);
        return new ConnectionRequest(this, future, httpRoute){
            final Future val$future;
            final HttpRoute val$route;
            final PoolingHttpClientConnectionManager this$0;
            {
                this.this$0 = poolingHttpClientConnectionManager;
                this.val$future = future;
                this.val$route = httpRoute;
            }

            @Override
            public boolean cancel() {
                return this.val$future.cancel(true);
            }

            @Override
            public HttpClientConnection get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
                HttpClientConnection httpClientConnection = this.this$0.leaseConnection(this.val$future, l, timeUnit);
                if (httpClientConnection.isOpen()) {
                    HttpHost httpHost = this.val$route.getProxyHost() != null ? this.val$route.getProxyHost() : this.val$route.getTargetHost();
                    SocketConfig socketConfig = PoolingHttpClientConnectionManager.access$000(this.this$0, httpHost);
                    httpClientConnection.setSocketTimeout(socketConfig.getSoTimeout());
                }
                return httpClientConnection;
            }
        };
    }

    protected HttpClientConnection leaseConnection(Future<CPoolEntry> future, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
        try {
            CPoolEntry cPoolEntry = future.get(l, timeUnit);
            if (cPoolEntry == null || future.isCancelled()) {
                throw new ExecutionException(new CancellationException("Operation cancelled"));
            }
            Asserts.check(cPoolEntry.getConnection() != null, "Pool entry with no connection");
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection leased: " + this.format(cPoolEntry) + this.formatStats((HttpRoute)cPoolEntry.getRoute()));
            }
            return CPoolProxy.newProxy(cPoolEntry);
        } catch (TimeoutException timeoutException) {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseConnection(HttpClientConnection httpClientConnection, Object object, long l, TimeUnit timeUnit) {
        Args.notNull(httpClientConnection, "Managed connection");
        HttpClientConnection httpClientConnection2 = httpClientConnection;
        synchronized (httpClientConnection2) {
            ManagedHttpClientConnection managedHttpClientConnection;
            CPoolEntry cPoolEntry;
            block9: {
                cPoolEntry = CPoolProxy.detach(httpClientConnection);
                if (cPoolEntry == null) {
                    return;
                }
                managedHttpClientConnection = (ManagedHttpClientConnection)cPoolEntry.getConnection();
                try {
                    if (!managedHttpClientConnection.isOpen()) break block9;
                    TimeUnit timeUnit2 = timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS;
                    cPoolEntry.setState(object);
                    cPoolEntry.updateExpiry(l, timeUnit2);
                    if (this.log.isDebugEnabled()) {
                        String string = l > 0L ? "for " + (double)timeUnit2.toMillis(l) / 1000.0 + " seconds" : "indefinitely";
                        this.log.debug("Connection " + this.format(cPoolEntry) + " can be kept alive " + string);
                    }
                    managedHttpClientConnection.setSocketTimeout(0);
                } catch (Throwable throwable) {
                    this.pool.release(cPoolEntry, managedHttpClientConnection.isOpen() && cPoolEntry.isRouteComplete());
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Connection released: " + this.format(cPoolEntry) + this.formatStats((HttpRoute)cPoolEntry.getRoute()));
                    }
                    throw throwable;
                }
            }
            this.pool.release(cPoolEntry, managedHttpClientConnection.isOpen() && cPoolEntry.isRouteComplete());
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection released: " + this.format(cPoolEntry) + this.formatStats((HttpRoute)cPoolEntry.getRoute()));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void connect(HttpClientConnection httpClientConnection, HttpRoute httpRoute, int n, HttpContext httpContext) throws IOException {
        ManagedHttpClientConnection managedHttpClientConnection;
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        Object object = httpClientConnection;
        synchronized (object) {
            CPoolEntry cPoolEntry = CPoolProxy.getPoolEntry(httpClientConnection);
            managedHttpClientConnection = (ManagedHttpClientConnection)cPoolEntry.getConnection();
        }
        object = httpRoute.getProxyHost() != null ? httpRoute.getProxyHost() : httpRoute.getTargetHost();
        this.connectionOperator.connect(managedHttpClientConnection, (HttpHost)object, httpRoute.getLocalSocketAddress(), n, this.resolveSocketConfig((HttpHost)object), httpContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void upgrade(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) throws IOException {
        ManagedHttpClientConnection managedHttpClientConnection;
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        HttpClientConnection httpClientConnection2 = httpClientConnection;
        synchronized (httpClientConnection2) {
            CPoolEntry cPoolEntry = CPoolProxy.getPoolEntry(httpClientConnection);
            managedHttpClientConnection = (ManagedHttpClientConnection)cPoolEntry.getConnection();
        }
        this.connectionOperator.upgrade(managedHttpClientConnection, httpRoute.getTargetHost(), httpContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void routeComplete(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        HttpClientConnection httpClientConnection2 = httpClientConnection;
        synchronized (httpClientConnection2) {
            CPoolEntry cPoolEntry = CPoolProxy.getPoolEntry(httpClientConnection);
            cPoolEntry.markRouteComplete();
        }
    }

    @Override
    public void shutdown() {
        if (this.isShutDown.compareAndSet(false, false)) {
            this.log.debug("Connection manager is shutting down");
            try {
                this.pool.enumLeased(new PoolEntryCallback<HttpRoute, ManagedHttpClientConnection>(this){
                    final PoolingHttpClientConnectionManager this$0;
                    {
                        this.this$0 = poolingHttpClientConnectionManager;
                    }

                    @Override
                    public void process(PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry) {
                        block3: {
                            ManagedHttpClientConnection managedHttpClientConnection = poolEntry.getConnection();
                            if (managedHttpClientConnection != null) {
                                try {
                                    managedHttpClientConnection.shutdown();
                                } catch (IOException iOException) {
                                    if (!PoolingHttpClientConnectionManager.access$100(this.this$0).isDebugEnabled()) break block3;
                                    PoolingHttpClientConnectionManager.access$100(this.this$0).debug("I/O exception shutting down connection", iOException);
                                }
                            }
                        }
                    }
                });
                this.pool.shutdown();
            } catch (IOException iOException) {
                this.log.debug("I/O exception shutting down connection manager", iOException);
            }
            this.log.debug("Connection manager shut down");
        }
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

    protected void enumAvailable(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        this.pool.enumAvailable(poolEntryCallback);
    }

    protected void enumLeased(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        this.pool.enumLeased(poolEntryCallback);
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

    public Set<HttpRoute> getRoutes() {
        return this.pool.getRoutes();
    }

    public SocketConfig getDefaultSocketConfig() {
        return this.configData.getDefaultSocketConfig();
    }

    public void setDefaultSocketConfig(SocketConfig socketConfig) {
        this.configData.setDefaultSocketConfig(socketConfig);
    }

    public ConnectionConfig getDefaultConnectionConfig() {
        return this.configData.getDefaultConnectionConfig();
    }

    public void setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.configData.setDefaultConnectionConfig(connectionConfig);
    }

    public SocketConfig getSocketConfig(HttpHost httpHost) {
        return this.configData.getSocketConfig(httpHost);
    }

    public void setSocketConfig(HttpHost httpHost, SocketConfig socketConfig) {
        this.configData.setSocketConfig(httpHost, socketConfig);
    }

    public ConnectionConfig getConnectionConfig(HttpHost httpHost) {
        return this.configData.getConnectionConfig(httpHost);
    }

    public void setConnectionConfig(HttpHost httpHost, ConnectionConfig connectionConfig) {
        this.configData.setConnectionConfig(httpHost, connectionConfig);
    }

    public int getValidateAfterInactivity() {
        return this.pool.getValidateAfterInactivity();
    }

    public void setValidateAfterInactivity(int n) {
        this.pool.setValidateAfterInactivity(n);
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

    static SocketConfig access$000(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, HttpHost httpHost) {
        return poolingHttpClientConnectionManager.resolveSocketConfig(httpHost);
    }

    static Log access$100(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        return poolingHttpClientConnectionManager.log;
    }

    static class InternalConnectionFactory
    implements ConnFactory<HttpRoute, ManagedHttpClientConnection> {
        private final ConfigData configData;
        private final HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory;

        InternalConnectionFactory(ConfigData configData, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> managedHttpClientConnectionFactory) {
            this.configData = configData != null ? configData : new ConfigData();
            this.connFactory = managedHttpClientConnectionFactory != null ? managedHttpClientConnectionFactory : ManagedHttpClientConnectionFactory.INSTANCE;
        }

        @Override
        public ManagedHttpClientConnection create(HttpRoute httpRoute) throws IOException {
            ConnectionConfig connectionConfig = null;
            if (httpRoute.getProxyHost() != null) {
                connectionConfig = this.configData.getConnectionConfig(httpRoute.getProxyHost());
            }
            if (connectionConfig == null) {
                connectionConfig = this.configData.getConnectionConfig(httpRoute.getTargetHost());
            }
            if (connectionConfig == null) {
                connectionConfig = this.configData.getDefaultConnectionConfig();
            }
            if (connectionConfig == null) {
                connectionConfig = ConnectionConfig.DEFAULT;
            }
            return this.connFactory.create(httpRoute, connectionConfig);
        }

        @Override
        public Object create(Object object) throws IOException {
            return this.create((HttpRoute)object);
        }
    }

    static class ConfigData {
        private final Map<HttpHost, SocketConfig> socketConfigMap = new ConcurrentHashMap<HttpHost, SocketConfig>();
        private final Map<HttpHost, ConnectionConfig> connectionConfigMap = new ConcurrentHashMap<HttpHost, ConnectionConfig>();
        private volatile SocketConfig defaultSocketConfig;
        private volatile ConnectionConfig defaultConnectionConfig;

        ConfigData() {
        }

        public SocketConfig getDefaultSocketConfig() {
            return this.defaultSocketConfig;
        }

        public void setDefaultSocketConfig(SocketConfig socketConfig) {
            this.defaultSocketConfig = socketConfig;
        }

        public ConnectionConfig getDefaultConnectionConfig() {
            return this.defaultConnectionConfig;
        }

        public void setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
            this.defaultConnectionConfig = connectionConfig;
        }

        public SocketConfig getSocketConfig(HttpHost httpHost) {
            return this.socketConfigMap.get(httpHost);
        }

        public void setSocketConfig(HttpHost httpHost, SocketConfig socketConfig) {
            this.socketConfigMap.put(httpHost, socketConfig);
        }

        public ConnectionConfig getConnectionConfig(HttpHost httpHost) {
            return this.connectionConfigMap.get(httpHost);
        }

        public void setConnectionConfig(HttpHost httpHost, ConnectionConfig connectionConfig) {
            this.connectionConfigMap.put(httpHost, connectionConfig);
        }
    }
}

