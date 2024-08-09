/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import net.optifine.http.HttpListener;
import net.optifine.http.HttpRequest;

public class HttpPipelineRequest {
    private HttpRequest httpRequest = null;
    private HttpListener httpListener = null;
    private boolean closed = false;

    public HttpPipelineRequest(HttpRequest httpRequest, HttpListener httpListener) {
        this.httpRequest = httpRequest;
        this.httpListener = httpListener;
    }

    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }

    public HttpListener getHttpListener() {
        return this.httpListener;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean bl) {
        this.closed = bl;
    }
}

