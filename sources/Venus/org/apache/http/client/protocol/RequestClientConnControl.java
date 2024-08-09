/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestClientConnControl
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        String string = httpRequest.getRequestLine().getMethod();
        if (string.equalsIgnoreCase("CONNECT")) {
            httpRequest.setHeader(PROXY_CONN_DIRECTIVE, "Keep-Alive");
            return;
        }
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        RouteInfo routeInfo = httpClientContext.getHttpRoute();
        if (routeInfo == null) {
            this.log.debug("Connection route not set in the context");
            return;
        }
        if ((routeInfo.getHopCount() == 1 || routeInfo.isTunnelled()) && !httpRequest.containsHeader("Connection")) {
            httpRequest.addHeader("Connection", "Keep-Alive");
        }
        if (routeInfo.getHopCount() == 2 && !routeInfo.isTunnelled() && !httpRequest.containsHeader(PROXY_CONN_DIRECTIVE)) {
            httpRequest.addHeader(PROXY_CONN_DIRECTIVE, "Keep-Alive");
        }
    }
}

