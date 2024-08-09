/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.apache.http.protocol.HttpResponseInterceptorList;
import org.apache.http.util.Args;

@Deprecated
public final class BasicHttpProcessor
implements HttpProcessor,
HttpRequestInterceptorList,
HttpResponseInterceptorList,
Cloneable {
    protected final List<HttpRequestInterceptor> requestInterceptors = new ArrayList<HttpRequestInterceptor>();
    protected final List<HttpResponseInterceptor> responseInterceptors = new ArrayList<HttpResponseInterceptor>();

    @Override
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(httpRequestInterceptor);
    }

    @Override
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int n) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(n, httpRequestInterceptor);
    }

    @Override
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int n) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(n, httpResponseInterceptor);
    }

    @Override
    public void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> clazz) {
        Iterator<HttpRequestInterceptor> iterator2 = this.requestInterceptors.iterator();
        while (iterator2.hasNext()) {
            HttpRequestInterceptor httpRequestInterceptor = iterator2.next();
            if (!httpRequestInterceptor.getClass().equals(clazz)) continue;
            iterator2.remove();
        }
    }

    @Override
    public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> clazz) {
        Iterator<HttpResponseInterceptor> iterator2 = this.responseInterceptors.iterator();
        while (iterator2.hasNext()) {
            HttpResponseInterceptor httpResponseInterceptor = iterator2.next();
            if (!httpResponseInterceptor.getClass().equals(clazz)) continue;
            iterator2.remove();
        }
    }

    public void addInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        this.addRequestInterceptor(httpRequestInterceptor);
    }

    public void addInterceptor(HttpRequestInterceptor httpRequestInterceptor, int n) {
        this.addRequestInterceptor(httpRequestInterceptor, n);
    }

    @Override
    public int getRequestInterceptorCount() {
        return this.requestInterceptors.size();
    }

    @Override
    public HttpRequestInterceptor getRequestInterceptor(int n) {
        if (n < 0 || n >= this.requestInterceptors.size()) {
            return null;
        }
        return this.requestInterceptors.get(n);
    }

    @Override
    public void clearRequestInterceptors() {
        this.requestInterceptors.clear();
    }

    @Override
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(httpResponseInterceptor);
    }

    public void addInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        this.addResponseInterceptor(httpResponseInterceptor);
    }

    public void addInterceptor(HttpResponseInterceptor httpResponseInterceptor, int n) {
        this.addResponseInterceptor(httpResponseInterceptor, n);
    }

    @Override
    public int getResponseInterceptorCount() {
        return this.responseInterceptors.size();
    }

    @Override
    public HttpResponseInterceptor getResponseInterceptor(int n) {
        if (n < 0 || n >= this.responseInterceptors.size()) {
            return null;
        }
        return this.responseInterceptors.get(n);
    }

    @Override
    public void clearResponseInterceptors() {
        this.responseInterceptors.clear();
    }

    @Override
    public void setInterceptors(List<?> list) {
        Args.notNull(list, "Inteceptor list");
        this.requestInterceptors.clear();
        this.responseInterceptors.clear();
        for (Object obj : list) {
            if (obj instanceof HttpRequestInterceptor) {
                this.addInterceptor((HttpRequestInterceptor)obj);
            }
            if (!(obj instanceof HttpResponseInterceptor)) continue;
            this.addInterceptor((HttpResponseInterceptor)obj);
        }
    }

    public void clearInterceptors() {
        this.clearRequestInterceptors();
        this.clearResponseInterceptors();
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

    protected void copyInterceptors(BasicHttpProcessor basicHttpProcessor) {
        basicHttpProcessor.requestInterceptors.clear();
        basicHttpProcessor.requestInterceptors.addAll(this.requestInterceptors);
        basicHttpProcessor.responseInterceptors.clear();
        basicHttpProcessor.responseInterceptors.addAll(this.responseInterceptors);
    }

    public BasicHttpProcessor copy() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpProcessor basicHttpProcessor = (BasicHttpProcessor)super.clone();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }
}

