/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestUserAgent
implements HttpRequestInterceptor {
    private final String userAgent;

    public RequestUserAgent(String string) {
        this.userAgent = string;
    }

    public RequestUserAgent() {
        this(null);
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("User-Agent")) {
            String string = null;
            HttpParams httpParams = httpRequest.getParams();
            if (httpParams != null) {
                string = (String)httpParams.getParameter("http.useragent");
            }
            if (string == null) {
                string = this.userAgent;
            }
            if (string != null) {
                httpRequest.addHeader("User-Agent", string);
            }
        }
    }
}

