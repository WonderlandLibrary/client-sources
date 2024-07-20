/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpRequestHandler;

public interface HttpRequestHandlerMapper {
    public HttpRequestHandler lookup(HttpRequest var1);
}

