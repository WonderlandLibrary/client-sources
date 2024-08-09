/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.FutureRequestExecutionMetrics;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.impl.client.HttpRequestTaskCallable;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.SAFE)
public class FutureRequestExecutionService
implements Closeable {
    private final HttpClient httpclient;
    private final ExecutorService executorService;
    private final FutureRequestExecutionMetrics metrics = new FutureRequestExecutionMetrics();
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public FutureRequestExecutionService(HttpClient httpClient, ExecutorService executorService) {
        this.httpclient = httpClient;
        this.executorService = executorService;
    }

    public <T> HttpRequestFutureTask<T> execute(HttpUriRequest httpUriRequest, HttpContext httpContext, ResponseHandler<T> responseHandler) {
        return this.execute(httpUriRequest, httpContext, responseHandler, null);
    }

    public <T> HttpRequestFutureTask<T> execute(HttpUriRequest httpUriRequest, HttpContext httpContext, ResponseHandler<T> responseHandler, FutureCallback<T> futureCallback) {
        if (this.closed.get()) {
            throw new IllegalStateException("Close has been called on this httpclient instance.");
        }
        this.metrics.getScheduledConnections().incrementAndGet();
        HttpRequestTaskCallable<T> httpRequestTaskCallable = new HttpRequestTaskCallable<T>(this.httpclient, httpUriRequest, httpContext, responseHandler, futureCallback, this.metrics);
        HttpRequestFutureTask<T> httpRequestFutureTask = new HttpRequestFutureTask<T>(httpUriRequest, httpRequestTaskCallable);
        this.executorService.execute(httpRequestFutureTask);
        return httpRequestFutureTask;
    }

    public FutureRequestExecutionMetrics metrics() {
        return this.metrics;
    }

    @Override
    public void close() throws IOException {
        this.closed.set(false);
        this.executorService.shutdownNow();
        if (this.httpclient instanceof Closeable) {
            ((Closeable)((Object)this.httpclient)).close();
        }
    }
}

