/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.impl.execchain.ConnectionHolder;
import org.apache.http.impl.execchain.HttpResponseProxy;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.Args;
import org.apache.http.util.VersionInfo;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class MinimalClientExec
implements ClientExecChain {
    private final Log log = LogFactory.getLog(this.getClass());
    private final HttpRequestExecutor requestExecutor;
    private final HttpClientConnectionManager connManager;
    private final ConnectionReuseStrategy reuseStrategy;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final HttpProcessor httpProcessor;

    public MinimalClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        this.httpProcessor = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", this.getClass())));
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
    }

    static void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute, boolean bl) throws ProtocolException {
        try {
            URI uRI = httpRequestWrapper.getURI();
            if (uRI != null) {
                uRI = uRI.isAbsolute() ? URIUtils.rewriteURI(uRI, null, bl ? URIUtils.DROP_FRAGMENT_AND_NORMALIZE : URIUtils.DROP_FRAGMENT) : URIUtils.rewriteURI(uRI);
                httpRequestWrapper.setURI(uRI);
            }
        } catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException("Invalid URI: " + httpRequestWrapper.getRequestLine().getUri(), uRISyntaxException);
        }
    }

    @Override
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        HttpClientConnection httpClientConnection;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        MinimalClientExec.rewriteRequestURI(httpRequestWrapper, httpRoute, httpClientContext.getRequestConfig().isNormalizeUri());
        ConnectionRequest connectionRequest = this.connManager.requestConnection(httpRoute, null);
        if (httpExecutionAware != null) {
            if (httpExecutionAware.isAborted()) {
                connectionRequest.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware.setCancellable(connectionRequest);
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        try {
            int n = requestConfig.getConnectionRequestTimeout();
            httpClientConnection = connectionRequest.get(n > 0 ? (long)n : 0L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", interruptedException);
        } catch (ExecutionException executionException) {
            Throwable throwable = executionException.getCause();
            if (throwable == null) {
                throwable = executionException;
            }
            throw new RequestAbortedException("Request execution failed", throwable);
        }
        ConnectionHolder connectionHolder = new ConnectionHolder(this.log, this.connManager, httpClientConnection);
        try {
            Object object;
            int n;
            if (httpExecutionAware != null) {
                if (httpExecutionAware.isAborted()) {
                    connectionHolder.close();
                    throw new RequestAbortedException("Request aborted");
                }
                httpExecutionAware.setCancellable(connectionHolder);
            }
            if (!httpClientConnection.isOpen()) {
                n = requestConfig.getConnectTimeout();
                this.connManager.connect(httpClientConnection, httpRoute, n > 0 ? n : 0, httpClientContext);
                this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
            }
            if ((n = requestConfig.getSocketTimeout()) >= 0) {
                httpClientConnection.setSocketTimeout(n);
            }
            HttpHost httpHost = null;
            HttpRequest httpRequest = httpRequestWrapper.getOriginal();
            if (httpRequest instanceof HttpUriRequest && ((URI)(object = ((HttpUriRequest)httpRequest).getURI())).isAbsolute()) {
                httpHost = new HttpHost(((URI)object).getHost(), ((URI)object).getPort(), ((URI)object).getScheme());
            }
            if (httpHost == null) {
                httpHost = httpRoute.getTargetHost();
            }
            httpClientContext.setAttribute("http.target_host", httpHost);
            httpClientContext.setAttribute("http.request", httpRequestWrapper);
            httpClientContext.setAttribute("http.connection", httpClientConnection);
            httpClientContext.setAttribute("http.route", httpRoute);
            this.httpProcessor.process(httpRequestWrapper, (HttpContext)httpClientContext);
            object = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
            this.httpProcessor.process((HttpResponse)object, (HttpContext)httpClientContext);
            if (this.reuseStrategy.keepAlive((HttpResponse)object, httpClientContext)) {
                long l = this.keepAliveStrategy.getKeepAliveDuration((HttpResponse)object, httpClientContext);
                connectionHolder.setValidFor(l, TimeUnit.MILLISECONDS);
                connectionHolder.markReusable();
            } else {
                connectionHolder.markNonReusable();
            }
            HttpEntity httpEntity = object.getEntity();
            if (httpEntity == null || !httpEntity.isStreaming()) {
                connectionHolder.releaseConnection();
                return new HttpResponseProxy((HttpResponse)object, null);
            }
            return new HttpResponseProxy((HttpResponse)object, connectionHolder);
        } catch (ConnectionShutdownException connectionShutdownException) {
            InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
            interruptedIOException.initCause(connectionShutdownException);
            throw interruptedIOException;
        } catch (HttpException httpException) {
            connectionHolder.abortConnection();
            throw httpException;
        } catch (IOException iOException) {
            connectionHolder.abortConnection();
            throw iOException;
        } catch (RuntimeException runtimeException) {
            connectionHolder.abortConnection();
            throw runtimeException;
        } catch (Error error2) {
            this.connManager.shutdown();
            throw error2;
        }
    }
}

