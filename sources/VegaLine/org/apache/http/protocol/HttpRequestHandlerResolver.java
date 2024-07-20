/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.protocol.HttpRequestHandler;

@Deprecated
public interface HttpRequestHandlerResolver {
    public HttpRequestHandler lookup(String var1);
}

