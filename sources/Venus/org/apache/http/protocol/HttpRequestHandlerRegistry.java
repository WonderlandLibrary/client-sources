/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.util.Map;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerResolver;
import org.apache.http.protocol.UriPatternMatcher;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class HttpRequestHandlerRegistry
implements HttpRequestHandlerResolver {
    private final UriPatternMatcher<HttpRequestHandler> matcher = new UriPatternMatcher();

    public void register(String string, HttpRequestHandler httpRequestHandler) {
        Args.notNull(string, "URI request pattern");
        Args.notNull(httpRequestHandler, "Request handler");
        this.matcher.register(string, httpRequestHandler);
    }

    public void unregister(String string) {
        this.matcher.unregister(string);
    }

    public void setHandlers(Map<String, HttpRequestHandler> map) {
        this.matcher.setObjects(map);
    }

    public Map<String, HttpRequestHandler> getHandlers() {
        return this.matcher.getObjects();
    }

    @Override
    public HttpRequestHandler lookup(String string) {
        return this.matcher.lookup(string);
    }
}

