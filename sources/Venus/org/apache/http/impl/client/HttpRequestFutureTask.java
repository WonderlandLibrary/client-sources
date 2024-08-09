/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.concurrent.FutureTask;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpRequestTaskCallable;

public class HttpRequestFutureTask<V>
extends FutureTask<V> {
    private final HttpUriRequest request;
    private final HttpRequestTaskCallable<V> callable;

    public HttpRequestFutureTask(HttpUriRequest httpUriRequest, HttpRequestTaskCallable<V> httpRequestTaskCallable) {
        super(httpRequestTaskCallable);
        this.request = httpUriRequest;
        this.callable = httpRequestTaskCallable;
    }

    @Override
    public boolean cancel(boolean bl) {
        this.callable.cancel();
        if (bl) {
            this.request.abort();
        }
        return super.cancel(bl);
    }

    public long scheduledTime() {
        return this.callable.getScheduled();
    }

    public long startedTime() {
        return this.callable.getStarted();
    }

    public long endedTime() {
        if (this.isDone()) {
            return this.callable.getEnded();
        }
        throw new IllegalStateException("Task is not done yet");
    }

    public long requestDuration() {
        if (this.isDone()) {
            return this.endedTime() - this.startedTime();
        }
        throw new IllegalStateException("Task is not done yet");
    }

    public long taskDuration() {
        if (this.isDone()) {
            return this.endedTime() - this.scheduledTime();
        }
        throw new IllegalStateException("Task is not done yet");
    }

    public String toString() {
        return this.request.getRequestLine().getUri();
    }
}

