/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.TokenIterator;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultConnectionReuseStrategy
implements ConnectionReuseStrategy {
    public static final DefaultConnectionReuseStrategy INSTANCE = new DefaultConnectionReuseStrategy();

    @Override
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        Object object;
        Object object2;
        Object object3;
        block25: {
            Object object4;
            Object object5;
            Args.notNull(httpResponse, "HTTP response");
            Args.notNull(httpContext, "HTTP context");
            if (httpResponse.getStatusLine().getStatusCode() == 204) {
                object5 = httpResponse.getFirstHeader("Content-Length");
                if (object5 != null) {
                    try {
                        int n = Integer.parseInt(object5.getValue());
                        if (n > 0) {
                            return false;
                        }
                    } catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                if ((object3 = httpResponse.getFirstHeader("Transfer-Encoding")) != null) {
                    return true;
                }
            }
            if ((object5 = (HttpRequest)httpContext.getAttribute("http.request")) != null) {
                try {
                    object3 = new BasicTokenIterator(object5.headerIterator("Connection"));
                    while (object3.hasNext()) {
                        object4 = object3.nextToken();
                        if (!"Close".equalsIgnoreCase((String)object4)) continue;
                        return false;
                    }
                } catch (ParseException parseException) {
                    return true;
                }
            }
            object3 = httpResponse.getStatusLine().getProtocolVersion();
            object4 = httpResponse.getFirstHeader("Transfer-Encoding");
            if (object4 != null) {
                if (!"chunked".equalsIgnoreCase(object4.getValue())) {
                    return true;
                }
            } else if (this.canResponseHaveBody((HttpRequest)object5, httpResponse)) {
                object2 = httpResponse.getHeaders("Content-Length");
                if (((Header[])object2).length == 1) {
                    object = object2[0];
                    try {
                        long l = Long.parseLong(object.getValue());
                        if (l < 0L) {
                            return false;
                        }
                        break block25;
                    } catch (NumberFormatException numberFormatException) {
                        return true;
                    }
                }
                return true;
            }
        }
        if (!(object2 = httpResponse.headerIterator("Connection")).hasNext()) {
            object2 = httpResponse.headerIterator("Proxy-Connection");
        }
        if (object2.hasNext()) {
            try {
                object = new BasicTokenIterator((HeaderIterator)object2);
                boolean bl = false;
                while (object.hasNext()) {
                    String string = object.nextToken();
                    if ("Close".equalsIgnoreCase(string)) {
                        return false;
                    }
                    if (!"Keep-Alive".equalsIgnoreCase(string)) continue;
                    bl = true;
                }
                if (bl) {
                    return true;
                }
            } catch (ParseException parseException) {
                return true;
            }
        }
        return !((ProtocolVersion)object3).lessEquals(HttpVersion.HTTP_1_0);
    }

    protected TokenIterator createTokenIterator(HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }

    private boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest != null && httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) {
            return true;
        }
        int n = httpResponse.getStatusLine().getStatusCode();
        return n >= 200 && n != 204 && n != 304 && n != 205;
    }
}

