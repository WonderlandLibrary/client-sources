/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class ResponseConnControl
implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Object object;
        Args.notNull(httpResponse, "HTTP response");
        HttpCoreContext httpCoreContext = HttpCoreContext.adapt(httpContext);
        int n = httpResponse.getStatusLine().getStatusCode();
        if (n == 400 || n == 408 || n == 411 || n == 413 || n == 414 || n == 503 || n == 501) {
            httpResponse.setHeader("Connection", "Close");
            return;
        }
        Header header = httpResponse.getFirstHeader("Connection");
        if (header != null && "Close".equalsIgnoreCase(header.getValue())) {
            return;
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            object = httpResponse.getStatusLine().getProtocolVersion();
            if (httpEntity.getContentLength() < 0L && (!httpEntity.isChunked() || ((ProtocolVersion)object).lessEquals(HttpVersion.HTTP_1_0))) {
                httpResponse.setHeader("Connection", "Close");
                return;
            }
        }
        if ((object = httpCoreContext.getRequest()) != null) {
            Header header2 = object.getFirstHeader("Connection");
            if (header2 != null) {
                httpResponse.setHeader("Connection", header2.getValue());
            } else if (object.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                httpResponse.setHeader("Connection", "Close");
            }
        }
    }
}

