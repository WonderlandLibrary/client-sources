/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestAcceptEncoding
implements HttpRequestInterceptor {
    private final String acceptEncoding;

    public RequestAcceptEncoding(List<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(list.get(i));
            }
            this.acceptEncoding = stringBuilder.toString();
        } else {
            this.acceptEncoding = "gzip,deflate";
        }
    }

    public RequestAcceptEncoding() {
        this(null);
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        if (!httpRequest.containsHeader("Accept-Encoding") && requestConfig.isContentCompressionEnabled()) {
            httpRequest.addHeader("Accept-Encoding", this.acceptEncoding);
        }
    }
}

