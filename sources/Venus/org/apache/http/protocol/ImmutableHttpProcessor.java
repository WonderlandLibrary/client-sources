/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.apache.http.protocol.HttpResponseInterceptorList;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public final class ImmutableHttpProcessor
implements HttpProcessor {
    private final HttpRequestInterceptor[] requestInterceptors;
    private final HttpResponseInterceptor[] responseInterceptors;

    public ImmutableHttpProcessor(HttpRequestInterceptor[] httpRequestInterceptorArray, HttpResponseInterceptor[] httpResponseInterceptorArray) {
        int n;
        if (httpRequestInterceptorArray != null) {
            n = httpRequestInterceptorArray.length;
            this.requestInterceptors = new HttpRequestInterceptor[n];
            System.arraycopy(httpRequestInterceptorArray, 0, this.requestInterceptors, 0, n);
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorArray != null) {
            n = httpResponseInterceptorArray.length;
            this.responseInterceptors = new HttpResponseInterceptor[n];
            System.arraycopy(httpResponseInterceptorArray, 0, this.responseInterceptors, 0, n);
        } else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    public ImmutableHttpProcessor(List<HttpRequestInterceptor> list, List<HttpResponseInterceptor> list2) {
        int n;
        if (list != null) {
            n = list.size();
            this.requestInterceptors = list.toArray(new HttpRequestInterceptor[n]);
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (list2 != null) {
            n = list2.size();
            this.responseInterceptors = list2.toArray(new HttpResponseInterceptor[n]);
        } else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    @Deprecated
    public ImmutableHttpProcessor(HttpRequestInterceptorList httpRequestInterceptorList, HttpResponseInterceptorList httpResponseInterceptorList) {
        int n;
        int n2;
        if (httpRequestInterceptorList != null) {
            n2 = httpRequestInterceptorList.getRequestInterceptorCount();
            this.requestInterceptors = new HttpRequestInterceptor[n2];
            for (n = 0; n < n2; ++n) {
                this.requestInterceptors[n] = httpRequestInterceptorList.getRequestInterceptor(n);
            }
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorList != null) {
            n2 = httpResponseInterceptorList.getResponseInterceptorCount();
            this.responseInterceptors = new HttpResponseInterceptor[n2];
            for (n = 0; n < n2; ++n) {
                this.responseInterceptors[n] = httpResponseInterceptorList.getResponseInterceptor(n);
            }
        } else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor ... httpRequestInterceptorArray) {
        this(httpRequestInterceptorArray, (HttpResponseInterceptor[])null);
    }

    public ImmutableHttpProcessor(HttpResponseInterceptor ... httpResponseInterceptorArray) {
        this((HttpRequestInterceptor[])null, httpResponseInterceptorArray);
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws IOException, HttpException {
        for (HttpRequestInterceptor httpRequestInterceptor : this.requestInterceptors) {
            httpRequestInterceptor.process(httpRequest, httpContext);
        }
    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws IOException, HttpException {
        for (HttpResponseInterceptor httpResponseInterceptor : this.responseInterceptors) {
            httpResponseInterceptor.process(httpResponse, httpContext);
        }
    }
}

