/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

@Deprecated
public class DecompressingHttpClient
implements HttpClient {
    private final HttpClient backend;
    private final HttpRequestInterceptor acceptEncodingInterceptor;
    private final HttpResponseInterceptor contentEncodingInterceptor;

    public DecompressingHttpClient() {
        this(new DefaultHttpClient());
    }

    public DecompressingHttpClient(HttpClient httpClient) {
        this(httpClient, new RequestAcceptEncoding(), new ResponseContentEncoding());
    }

    DecompressingHttpClient(HttpClient httpClient, HttpRequestInterceptor httpRequestInterceptor, HttpResponseInterceptor httpResponseInterceptor) {
        this.backend = httpClient;
        this.acceptEncodingInterceptor = httpRequestInterceptor;
        this.contentEncodingInterceptor = httpResponseInterceptor;
    }

    @Override
    public HttpParams getParams() {
        return this.backend.getParams();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }

    @Override
    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), (HttpRequest)httpUriRequest, (HttpContext)null);
    }

    public HttpClient getHttpClient() {
        return this.backend;
    }

    HttpHost getHttpHost(HttpUriRequest httpUriRequest) {
        URI uRI = httpUriRequest.getURI();
        return URIUtils.extractHost(uRI);
    }

    @Override
    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), (HttpRequest)httpUriRequest, httpContext);
    }

    @Override
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, (HttpContext)null);
    }

    @Override
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        try {
            HttpContext httpContext2 = httpContext != null ? httpContext : new BasicHttpContext();
            RequestWrapper requestWrapper = httpRequest instanceof HttpEntityEnclosingRequest ? new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest) : new RequestWrapper(httpRequest);
            this.acceptEncodingInterceptor.process(requestWrapper, httpContext2);
            HttpResponse httpResponse = this.backend.execute(httpHost, (HttpRequest)requestWrapper, httpContext2);
            try {
                this.contentEncodingInterceptor.process(httpResponse, httpContext2);
                if (Boolean.TRUE.equals(httpContext2.getAttribute("http.client.response.uncompressed"))) {
                    httpResponse.removeHeaders("Content-Length");
                    httpResponse.removeHeaders("Content-Encoding");
                    httpResponse.removeHeaders("Content-MD5");
                }
                return httpResponse;
            } catch (HttpException httpException) {
                EntityUtils.consume(httpResponse.getEntity());
                throw httpException;
            } catch (IOException iOException) {
                EntityUtils.consume(httpResponse.getEntity());
                throw iOException;
            } catch (RuntimeException runtimeException) {
                EntityUtils.consume(httpResponse.getEntity());
                throw runtimeException;
            }
        } catch (HttpException httpException) {
            throw new ClientProtocolException(httpException);
        }
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), (HttpRequest)httpUriRequest, responseHandler);
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(this.getHttpHost(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }

    @Override
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        HttpResponse httpResponse = this.execute(httpHost, httpRequest, httpContext);
        try {
            T t = responseHandler.handleResponse(httpResponse);
            return t;
        } finally {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
            }
        }
    }
}

