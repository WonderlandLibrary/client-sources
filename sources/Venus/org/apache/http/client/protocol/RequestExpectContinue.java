/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestExpectContinue
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("Expect") && httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpClientContext httpClientContext;
            RequestConfig requestConfig;
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            HttpEntity httpEntity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            if (httpEntity != null && httpEntity.getContentLength() != 0L && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0) && (requestConfig = (httpClientContext = HttpClientContext.adapt(httpContext)).getRequestConfig()).isExpectContinueEnabled()) {
                httpRequest.addHeader("Expect", "100-continue");
            }
        }
    }
}

