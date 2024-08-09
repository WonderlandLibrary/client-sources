/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
public final class DefaultedHttpContext
implements HttpContext {
    private final HttpContext local;
    private final HttpContext defaults;

    public DefaultedHttpContext(HttpContext httpContext, HttpContext httpContext2) {
        this.local = Args.notNull(httpContext, "HTTP context");
        this.defaults = httpContext2;
    }

    @Override
    public Object getAttribute(String string) {
        Object object = this.local.getAttribute(string);
        if (object == null) {
            return this.defaults.getAttribute(string);
        }
        return object;
    }

    @Override
    public Object removeAttribute(String string) {
        return this.local.removeAttribute(string);
    }

    @Override
    public void setAttribute(String string, Object object) {
        this.local.setAttribute(string, object);
    }

    public HttpContext getDefaults() {
        return this.defaults;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[local: ").append(this.local);
        stringBuilder.append("defaults: ").append(this.defaults);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

