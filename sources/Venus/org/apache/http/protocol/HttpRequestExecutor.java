/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class HttpRequestExecutor {
    public static final int DEFAULT_WAIT_FOR_CONTINUE = 3000;
    private final int waitForContinue;

    public HttpRequestExecutor(int n) {
        this.waitForContinue = Args.positive(n, "Wait for continue time");
    }

    public HttpRequestExecutor() {
        this(3000);
    }

    protected boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        if ("HEAD".equalsIgnoreCase(httpRequest.getRequestLine().getMethod())) {
            return true;
        }
        int n = httpResponse.getStatusLine().getStatusCode();
        return n >= 200 && n != 204 && n != 304 && n != 205;
    }

    public HttpResponse execute(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        try {
            HttpResponse httpResponse = this.doSendRequest(httpRequest, httpClientConnection, httpContext);
            if (httpResponse == null) {
                httpResponse = this.doReceiveResponse(httpRequest, httpClientConnection, httpContext);
            }
            return httpResponse;
        } catch (IOException iOException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw iOException;
        } catch (HttpException httpException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw httpException;
        } catch (RuntimeException runtimeException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw runtimeException;
        }
    }

    private static void closeConnection(HttpClientConnection httpClientConnection) {
        try {
            httpClientConnection.close();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    public void preProcess(HttpRequest httpRequest, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.request", httpRequest);
        httpProcessor.process(httpRequest, httpContext);
    }

    protected HttpResponse doSendRequest(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse httpResponse = null;
        httpContext.setAttribute("http.connection", httpClientConnection);
        httpContext.setAttribute("http.request_sent", Boolean.FALSE);
        httpClientConnection.sendRequestHeader(httpRequest);
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            boolean bl = true;
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            if (((HttpEntityEnclosingRequest)httpRequest).expectContinue() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpClientConnection.flush();
                if (httpClientConnection.isResponseAvailable(this.waitForContinue)) {
                    int n;
                    httpResponse = httpClientConnection.receiveResponseHeader();
                    if (this.canResponseHaveBody(httpRequest, httpResponse)) {
                        httpClientConnection.receiveResponseEntity(httpResponse);
                    }
                    if ((n = httpResponse.getStatusLine().getStatusCode()) < 200) {
                        if (n != 100) {
                            throw new ProtocolException("Unexpected response: " + httpResponse.getStatusLine());
                        }
                        httpResponse = null;
                    } else {
                        bl = false;
                    }
                }
            }
            if (bl) {
                httpClientConnection.sendRequestEntity((HttpEntityEnclosingRequest)httpRequest);
            }
        }
        httpClientConnection.flush();
        httpContext.setAttribute("http.request_sent", Boolean.TRUE);
        return httpResponse;
    }

    protected HttpResponse doReceiveResponse(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse httpResponse = null;
        int n = 0;
        while (httpResponse == null || n < 200) {
            httpResponse = httpClientConnection.receiveResponseHeader();
            n = httpResponse.getStatusLine().getStatusCode();
            if (n < 100) {
                throw new ProtocolException("Invalid response: " + httpResponse.getStatusLine());
            }
            if (!this.canResponseHaveBody(httpRequest, httpResponse)) continue;
            httpClientConnection.receiveResponseEntity(httpResponse);
        }
        return httpResponse;
    }

    public void postProcess(HttpResponse httpResponse, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.response", httpResponse);
        httpProcessor.process(httpResponse, httpContext);
    }
}

