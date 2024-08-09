/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.FutureRequestExecutionMetrics;
import org.apache.http.protocol.HttpContext;

class HttpRequestTaskCallable<V>
implements Callable<V> {
    private final HttpUriRequest request;
    private final HttpClient httpclient;
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private final long scheduled = System.currentTimeMillis();
    private long started = -1L;
    private long ended = -1L;
    private final HttpContext context;
    private final ResponseHandler<V> responseHandler;
    private final FutureCallback<V> callback;
    private final FutureRequestExecutionMetrics metrics;

    HttpRequestTaskCallable(HttpClient httpClient, HttpUriRequest httpUriRequest, HttpContext httpContext, ResponseHandler<V> responseHandler, FutureCallback<V> futureCallback, FutureRequestExecutionMetrics futureRequestExecutionMetrics) {
        this.httpclient = httpClient;
        this.responseHandler = responseHandler;
        this.request = httpUriRequest;
        this.context = httpContext;
        this.callback = futureCallback;
        this.metrics = futureRequestExecutionMetrics;
    }

    public long getScheduled() {
        return this.scheduled;
    }

    public long getStarted() {
        return this.started;
    }

    public long getEnded() {
        return this.ended;
    }

    @Override
    public V call() throws Exception {
        if (!this.cancelled.get()) {
            try {
                V v;
                this.metrics.getActiveConnections().incrementAndGet();
                this.started = System.currentTimeMillis();
                try {
                    this.metrics.getScheduledConnections().decrementAndGet();
                    V v2 = this.httpclient.execute(this.request, this.responseHandler, this.context);
                    this.ended = System.currentTimeMillis();
                    this.metrics.getSuccessfulConnections().increment(this.started);
                    if (this.callback != null) {
                        this.callback.completed(v2);
                    }
                    v = v2;
                } catch (Exception exception) {
                    this.metrics.getFailedConnections().increment(this.started);
                    this.ended = System.currentTimeMillis();
                    if (this.callback != null) {
                        this.callback.failed(exception);
                    }
                    throw exception;
                }
                return v;
            } finally {
                this.metrics.getRequests().increment(this.started);
                this.metrics.getTasks().increment(this.started);
                this.metrics.getActiveConnections().decrementAndGet();
            }
        }
        throw new IllegalStateException("call has been cancelled for request " + this.request.getURI());
    }

    public void cancel() {
        this.cancelled.set(false);
        if (this.callback != null) {
            this.callback.cancelled();
        }
    }
}

