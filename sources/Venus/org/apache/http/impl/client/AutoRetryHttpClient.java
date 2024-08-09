/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class AutoRetryHttpClient
implements HttpClient {
    private final HttpClient backend;
    private final ServiceUnavailableRetryStrategy retryStrategy;
    private final Log log = LogFactory.getLog(this.getClass());

    public AutoRetryHttpClient(HttpClient httpClient, ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        Args.notNull(httpClient, "HttpClient");
        Args.notNull(serviceUnavailableRetryStrategy, "ServiceUnavailableRetryStrategy");
        this.backend = httpClient;
        this.retryStrategy = serviceUnavailableRetryStrategy;
    }

    public AutoRetryHttpClient() {
        this(new DefaultHttpClient(), new DefaultServiceUnavailableRetryStrategy());
    }

    public AutoRetryHttpClient(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this(new DefaultHttpClient(), serviceUnavailableRetryStrategy);
    }

    public AutoRetryHttpClient(HttpClient httpClient) {
        this(httpClient, new DefaultServiceUnavailableRetryStrategy());
    }

    @Override
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        HttpContext httpContext = null;
        return this.execute(httpHost, httpRequest, httpContext);
    }

    @Override
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }

    @Override
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        HttpResponse httpResponse = this.execute(httpHost, httpRequest, httpContext);
        return responseHandler.handleResponse(httpResponse);
    }

    @Override
    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        HttpContext httpContext = null;
        return this.execute(httpUriRequest, httpContext);
    }

    @Override
    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException {
        URI uRI = httpUriRequest.getURI();
        HttpHost httpHost = new HttpHost(uRI.getHost(), uRI.getPort(), uRI.getScheme());
        return this.execute(httpHost, (HttpRequest)httpUriRequest, httpContext);
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        return this.execute(httpUriRequest, responseHandler, null);
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        HttpResponse httpResponse = this.execute(httpUriRequest, httpContext);
        return responseHandler.handleResponse(httpResponse);
    }

    @Override
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        int n = 1;
        while (true) {
            block8: {
                HttpResponse httpResponse = this.backend.execute(httpHost, httpRequest, httpContext);
                try {
                    if (this.retryStrategy.retryRequest(httpResponse, n, httpContext)) {
                        EntityUtils.consume(httpResponse.getEntity());
                        long l = this.retryStrategy.getRetryInterval();
                        try {
                            this.log.trace("Wait for " + l);
                            Thread.sleep(l);
                            break block8;
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                            throw new InterruptedIOException();
                        }
                    }
                    return httpResponse;
                } catch (RuntimeException runtimeException) {
                    try {
                        EntityUtils.consume(httpResponse.getEntity());
                    } catch (IOException iOException) {
                        this.log.warn("I/O error consuming response content", iOException);
                    }
                    throw runtimeException;
                }
            }
            ++n;
        }
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }

    @Override
    public HttpParams getParams() {
        return this.backend.getParams();
    }
}

