/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthState;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpParamsNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
class InternalHttpClient
extends CloseableHttpClient
implements Configurable {
    private final Log log = LogFactory.getLog(this.getClass());
    private final ClientExecChain execChain;
    private final HttpClientConnectionManager connManager;
    private final HttpRoutePlanner routePlanner;
    private final Lookup<CookieSpecProvider> cookieSpecRegistry;
    private final Lookup<AuthSchemeProvider> authSchemeRegistry;
    private final CookieStore cookieStore;
    private final CredentialsProvider credentialsProvider;
    private final RequestConfig defaultConfig;
    private final List<Closeable> closeables;

    public InternalHttpClient(ClientExecChain clientExecChain, HttpClientConnectionManager httpClientConnectionManager, HttpRoutePlanner httpRoutePlanner, Lookup<CookieSpecProvider> lookup, Lookup<AuthSchemeProvider> lookup2, CookieStore cookieStore, CredentialsProvider credentialsProvider, RequestConfig requestConfig, List<Closeable> list) {
        Args.notNull(clientExecChain, "HTTP client exec chain");
        Args.notNull(httpClientConnectionManager, "HTTP connection manager");
        Args.notNull(httpRoutePlanner, "HTTP route planner");
        this.execChain = clientExecChain;
        this.connManager = httpClientConnectionManager;
        this.routePlanner = httpRoutePlanner;
        this.cookieSpecRegistry = lookup;
        this.authSchemeRegistry = lookup2;
        this.cookieStore = cookieStore;
        this.credentialsProvider = credentialsProvider;
        this.defaultConfig = requestConfig;
        this.closeables = list;
    }

    private HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        HttpHost httpHost2 = httpHost;
        if (httpHost2 == null) {
            httpHost2 = (HttpHost)httpRequest.getParams().getParameter("http.default-host");
        }
        return this.routePlanner.determineRoute(httpHost2, httpRequest, httpContext);
    }

    private void setupContext(HttpClientContext httpClientContext) {
        if (httpClientContext.getAttribute("http.auth.target-scope") == null) {
            httpClientContext.setAttribute("http.auth.target-scope", new AuthState());
        }
        if (httpClientContext.getAttribute("http.auth.proxy-scope") == null) {
            httpClientContext.setAttribute("http.auth.proxy-scope", new AuthState());
        }
        if (httpClientContext.getAttribute("http.authscheme-registry") == null) {
            httpClientContext.setAttribute("http.authscheme-registry", this.authSchemeRegistry);
        }
        if (httpClientContext.getAttribute("http.cookiespec-registry") == null) {
            httpClientContext.setAttribute("http.cookiespec-registry", this.cookieSpecRegistry);
        }
        if (httpClientContext.getAttribute("http.cookie-store") == null) {
            httpClientContext.setAttribute("http.cookie-store", this.cookieStore);
        }
        if (httpClientContext.getAttribute("http.auth.credentials-provider") == null) {
            httpClientContext.setAttribute("http.auth.credentials-provider", this.credentialsProvider);
        }
        if (httpClientContext.getAttribute("http.request-config") == null) {
            httpClientContext.setAttribute("http.request-config", this.defaultConfig);
        }
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        HttpExecutionAware httpExecutionAware = null;
        if (httpRequest instanceof HttpExecutionAware) {
            httpExecutionAware = (HttpExecutionAware)((Object)httpRequest);
        }
        try {
            Object object;
            HttpRequestWrapper httpRequestWrapper = HttpRequestWrapper.wrap(httpRequest, httpHost);
            HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext != null ? httpContext : new BasicHttpContext());
            RequestConfig requestConfig = null;
            if (httpRequest instanceof Configurable) {
                requestConfig = ((Configurable)((Object)httpRequest)).getConfig();
            }
            if (requestConfig == null) {
                object = httpRequest.getParams();
                if (object instanceof HttpParamsNames) {
                    if (!((HttpParamsNames)object).getNames().isEmpty()) {
                        requestConfig = HttpClientParamConfig.getRequestConfig((HttpParams)object, this.defaultConfig);
                    }
                } else {
                    requestConfig = HttpClientParamConfig.getRequestConfig((HttpParams)object, this.defaultConfig);
                }
            }
            if (requestConfig != null) {
                httpClientContext.setRequestConfig(requestConfig);
            }
            this.setupContext(httpClientContext);
            object = this.determineRoute(httpHost, httpRequestWrapper, httpClientContext);
            return this.execChain.execute((HttpRoute)object, httpRequestWrapper, httpClientContext, httpExecutionAware);
        } catch (HttpException httpException) {
            throw new ClientProtocolException(httpException);
        }
    }

    @Override
    public RequestConfig getConfig() {
        return this.defaultConfig;
    }

    @Override
    public void close() {
        if (this.closeables != null) {
            for (Closeable closeable : this.closeables) {
                try {
                    closeable.close();
                } catch (IOException iOException) {
                    this.log.error(iOException.getMessage(), iOException);
                }
            }
        }
    }

    @Override
    public HttpParams getParams() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return new ClientConnectionManager(this){
            final InternalHttpClient this$0;
            {
                this.this$0 = internalHttpClient;
            }

            @Override
            public void shutdown() {
                InternalHttpClient.access$000(this.this$0).shutdown();
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
                InternalHttpClient.access$000(this.this$0).closeIdleConnections(l, timeUnit);
            }

            @Override
            public void closeExpiredConnections() {
                InternalHttpClient.access$000(this.this$0).closeExpiredConnections();
            }
        };
    }

    static HttpClientConnectionManager access$000(InternalHttpClient internalHttpClient) {
        return internalHttpClient.connManager;
    }
}

