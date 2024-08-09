/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class BasicHttpContext
implements HttpContext {
    private final HttpContext parentContext;
    private final Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public BasicHttpContext() {
        this(null);
    }

    public BasicHttpContext(HttpContext httpContext) {
        this.parentContext = httpContext;
    }

    @Override
    public Object getAttribute(String string) {
        Args.notNull(string, "Id");
        Object object = this.map.get(string);
        if (object == null && this.parentContext != null) {
            object = this.parentContext.getAttribute(string);
        }
        return object;
    }

    @Override
    public void setAttribute(String string, Object object) {
        Args.notNull(string, "Id");
        if (object != null) {
            this.map.put(string, object);
        } else {
            this.map.remove(string);
        }
    }

    @Override
    public Object removeAttribute(String string) {
        Args.notNull(string, "Id");
        return this.map.remove(string);
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }
}

