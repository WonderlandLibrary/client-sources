/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.ChainBuilder;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.ImmutableHttpProcessor;

public class HttpProcessorBuilder {
    private ChainBuilder<HttpRequestInterceptor> requestChainBuilder;
    private ChainBuilder<HttpResponseInterceptor> responseChainBuilder;

    public static HttpProcessorBuilder create() {
        return new HttpProcessorBuilder();
    }

    HttpProcessorBuilder() {
    }

    private ChainBuilder<HttpRequestInterceptor> getRequestChainBuilder() {
        if (this.requestChainBuilder == null) {
            this.requestChainBuilder = new ChainBuilder();
        }
        return this.requestChainBuilder;
    }

    private ChainBuilder<HttpResponseInterceptor> getResponseChainBuilder() {
        if (this.responseChainBuilder == null) {
            this.responseChainBuilder = new ChainBuilder();
        }
        return this.responseChainBuilder;
    }

    public HttpProcessorBuilder addFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        this.getRequestChainBuilder().addFirst(httpRequestInterceptor);
        return this;
    }

    public HttpProcessorBuilder addLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        this.getRequestChainBuilder().addLast(httpRequestInterceptor);
        return this;
    }

    public HttpProcessorBuilder add(HttpRequestInterceptor httpRequestInterceptor) {
        return this.addLast(httpRequestInterceptor);
    }

    public HttpProcessorBuilder addAllFirst(HttpRequestInterceptor ... httpRequestInterceptorArray) {
        if (httpRequestInterceptorArray == null) {
            return this;
        }
        this.getRequestChainBuilder().addAllFirst((HttpRequestInterceptor[])httpRequestInterceptorArray);
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpRequestInterceptor ... httpRequestInterceptorArray) {
        if (httpRequestInterceptorArray == null) {
            return this;
        }
        this.getRequestChainBuilder().addAllLast((HttpRequestInterceptor[])httpRequestInterceptorArray);
        return this;
    }

    public HttpProcessorBuilder addAll(HttpRequestInterceptor ... httpRequestInterceptorArray) {
        return this.addAllLast(httpRequestInterceptorArray);
    }

    public HttpProcessorBuilder addFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        this.getResponseChainBuilder().addFirst(httpResponseInterceptor);
        return this;
    }

    public HttpProcessorBuilder addLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        this.getResponseChainBuilder().addLast(httpResponseInterceptor);
        return this;
    }

    public HttpProcessorBuilder add(HttpResponseInterceptor httpResponseInterceptor) {
        return this.addLast(httpResponseInterceptor);
    }

    public HttpProcessorBuilder addAllFirst(HttpResponseInterceptor ... httpResponseInterceptorArray) {
        if (httpResponseInterceptorArray == null) {
            return this;
        }
        this.getResponseChainBuilder().addAllFirst((HttpResponseInterceptor[])httpResponseInterceptorArray);
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpResponseInterceptor ... httpResponseInterceptorArray) {
        if (httpResponseInterceptorArray == null) {
            return this;
        }
        this.getResponseChainBuilder().addAllLast((HttpResponseInterceptor[])httpResponseInterceptorArray);
        return this;
    }

    public HttpProcessorBuilder addAll(HttpResponseInterceptor ... httpResponseInterceptorArray) {
        return this.addAllLast(httpResponseInterceptorArray);
    }

    public HttpProcessor build() {
        return new ImmutableHttpProcessor(this.requestChainBuilder != null ? this.requestChainBuilder.build() : null, this.responseChainBuilder != null ? this.responseChainBuilder.build() : null);
    }
}

