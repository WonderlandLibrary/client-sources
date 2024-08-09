/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.execchain.MinimalClientExec;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
class MinimalHttpClient
extends CloseableHttpClient {
    private final HttpClientConnectionManager connManager;
    private final MinimalClientExec requestExecutor;
    private final HttpParams params;

    public MinimalHttpClient(HttpClientConnectionManager httpClientConnectionManager) {
        this.connManager = Args.notNull(httpClientConnectionManager, "HTTP connection manager");
        this.requestExecutor = new MinimalClientExec(new HttpRequestExecutor(), httpClientConnectionManager, DefaultConnectionReuseStrategy.INSTANCE, DefaultConnectionKeepAliveStrategy.INSTANCE);
        this.params = new BasicHttpParams();
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpRequest, "HTTP request");
        HttpExecutionAware httpExecutionAware = null;
        if (httpRequest instanceof HttpExecutionAware) {
            httpExecutionAware = (HttpExecutionAware)((Object)httpRequest);
        }
        try {
            HttpRequestWrapper httpRequestWrapper = HttpRequestWrapper.wrap(httpRequest);
            HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext != null ? httpContext : new BasicHttpContext());
            HttpRoute httpRoute = new HttpRoute(httpHost);
            RequestConfig requestConfig = null;
            if (httpRequest instanceof Configurable) {
                requestConfig = ((Configurable)((Object)httpRequest)).getConfig();
            }
            if (requestConfig != null) {
                httpClientContext.setRequestConfig(requestConfig);
            }
            return this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        } catch (HttpException httpException) {
            throw new ClientProtocolException(httpException);
        }
    }

    @Override
    public HttpParams getParams() {
        return this.params;
    }

    @Override
    public void close() {
        this.connManager.shutdown();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return new ClientConnectionManager(this){
            final MinimalHttpClient this$0;
            {
                this.this$0 = minimalHttpClient;
            }

            @Override
            public void shutdown() {
                MinimalHttpClient.access$000(this.this$0).shutdown();
            }

            @Override
            public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void releaseConnection(ManagedClientConnection managedClientConnection, long l, TimeUnit timeUnit) {
                throw new UnsupportedOperationException();
            }

            @Override
            public SchemeRegistry getSchemeRegistry() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void closeIdleConnections(long l, TimeUnit timeUnit) {
                MinimalHttpClient.access$000(this.this$0).closeIdleConnections(l, timeUnit);
            }

            @Override
            public void closeExpiredConnections() {
                MinimalHttpClient.access$000(this.this$0).closeExpiredConnections();
            }
        };
    }

    static HttpClientConnectionManager access$000(MinimalHttpClient minimalHttpClient) {
        return minimalHttpClient.connManager;
    }
}

