/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Contract(threading=ThreadingBehavior.SAFE)
public abstract class AbstractCookieSpec
implements CookieSpec {
    private final Map<String, CookieAttributeHandler> attribHandlerMap;

    public AbstractCookieSpec() {
        this.attribHandlerMap = new ConcurrentHashMap<String, CookieAttributeHandler>(10);
    }

    protected AbstractCookieSpec(HashMap<String, CookieAttributeHandler> hashMap) {
        Asserts.notNull(hashMap, "Attribute handler map");
        this.attribHandlerMap = new ConcurrentHashMap<String, CookieAttributeHandler>(hashMap);
    }

    protected AbstractCookieSpec(CommonCookieAttributeHandler ... commonCookieAttributeHandlerArray) {
        this.attribHandlerMap = new ConcurrentHashMap<String, CookieAttributeHandler>(commonCookieAttributeHandlerArray.length);
        for (CommonCookieAttributeHandler commonCookieAttributeHandler : commonCookieAttributeHandlerArray) {
            this.attribHandlerMap.put(commonCookieAttributeHandler.getAttributeName(), commonCookieAttributeHandler);
        }
    }

    @Deprecated
    public void registerAttribHandler(String string, CookieAttributeHandler cookieAttributeHandler) {
        Args.notNull(string, "Attribute name");
        Args.notNull(cookieAttributeHandler, "Attribute handler");
        this.attribHandlerMap.put(string, cookieAttributeHandler);
    }

    protected CookieAttributeHandler findAttribHandler(String string) {
        return this.attribHandlerMap.get(string);
    }

    protected CookieAttributeHandler getAttribHandler(String string) {
        CookieAttributeHandler cookieAttributeHandler = this.findAttribHandler(string);
        Asserts.check(cookieAttributeHandler != null, "Handler not registered for " + string + " attribute");
        return cookieAttributeHandler;
    }

    protected Collection<CookieAttributeHandler> getAttribHandlers() {
        return this.attribHandlerMap.values();
    }
}

