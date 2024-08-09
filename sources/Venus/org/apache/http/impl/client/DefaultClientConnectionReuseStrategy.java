/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHeaderIterator;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.HttpContext;

public class DefaultClientConnectionReuseStrategy
extends DefaultConnectionReuseStrategy {
    public static final DefaultClientConnectionReuseStrategy INSTANCE = new DefaultClientConnectionReuseStrategy();

    @Override
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        Header[] headerArray;
        HttpRequest httpRequest = (HttpRequest)httpContext.getAttribute("http.request");
        if (httpRequest != null && (headerArray = httpRequest.getHeaders("Connection")).length != 0) {
            BasicTokenIterator basicTokenIterator = new BasicTokenIterator(new BasicHeaderIterator(headerArray, null));
            while (basicTokenIterator.hasNext()) {
                String string = basicTokenIterator.nextToken();
                if (!"Close".equalsIgnoreCase(string)) continue;
                return true;
            }
        }
        return super.keepAlive(httpResponse, httpContext);
    }
}

