/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class StandardHttpRequestRetryHandler
extends DefaultHttpRequestRetryHandler {
    private final Map<String, Boolean> idempotentMethods = new ConcurrentHashMap<String, Boolean>();

    public StandardHttpRequestRetryHandler(int n, boolean bl) {
        super(n, bl);
        this.idempotentMethods.put("GET", Boolean.TRUE);
        this.idempotentMethods.put("HEAD", Boolean.TRUE);
        this.idempotentMethods.put("PUT", Boolean.TRUE);
        this.idempotentMethods.put("DELETE", Boolean.TRUE);
        this.idempotentMethods.put("OPTIONS", Boolean.TRUE);
        this.idempotentMethods.put("TRACE", Boolean.TRUE);
    }

    public StandardHttpRequestRetryHandler() {
        this(3, false);
    }

    @Override
    protected boolean handleAsIdempotent(HttpRequest httpRequest) {
        String string = httpRequest.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
        Boolean bl = this.idempotentMethods.get(string);
        return bl != null && bl != false;
    }
}

