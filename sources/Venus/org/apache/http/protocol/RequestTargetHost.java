/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestTargetHost
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        HttpCoreContext httpCoreContext = HttpCoreContext.adapt(httpContext);
        ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
        String string = httpRequest.getRequestLine().getMethod();
        if (string.equalsIgnoreCase("CONNECT") && protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
            return;
        }
        if (!httpRequest.containsHeader("Host")) {
            HttpHost httpHost = httpCoreContext.getTargetHost();
            if (httpHost == null) {
                HttpConnection httpConnection = httpCoreContext.getConnection();
                if (httpConnection instanceof HttpInetConnection) {
                    InetAddress inetAddress = ((HttpInetConnection)httpConnection).getRemoteAddress();
                    int n = ((HttpInetConnection)httpConnection).getRemotePort();
                    if (inetAddress != null) {
                        httpHost = new HttpHost(inetAddress.getHostName(), n);
                    }
                }
                if (httpHost == null) {
                    if (protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                        return;
                    }
                    throw new ProtocolException("Target host missing");
                }
            }
            httpRequest.addHeader("Host", httpHost.toHostString());
        }
    }
}

