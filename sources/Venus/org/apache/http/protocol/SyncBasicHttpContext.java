/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class SyncBasicHttpContext
extends BasicHttpContext {
    public SyncBasicHttpContext(HttpContext httpContext) {
        super(httpContext);
    }

    public SyncBasicHttpContext() {
    }

    @Override
    public synchronized Object getAttribute(String string) {
        return super.getAttribute(string);
    }

    @Override
    public synchronized void setAttribute(String string, Object object) {
        super.setAttribute(string, object);
    }

    @Override
    public synchronized Object removeAttribute(String string) {
        return super.removeAttribute(string);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }
}

