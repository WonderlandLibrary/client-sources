/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.config.Lookup;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public final class CookieSpecRegistry
implements Lookup<CookieSpecProvider> {
    private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs = new ConcurrentHashMap();

    public void register(String string, CookieSpecFactory cookieSpecFactory) {
        Args.notNull(string, "Name");
        Args.notNull(cookieSpecFactory, "Cookie spec factory");
        this.registeredSpecs.put(string.toLowerCase(Locale.ENGLISH), cookieSpecFactory);
    }

    public void unregister(String string) {
        Args.notNull(string, "Id");
        this.registeredSpecs.remove(string.toLowerCase(Locale.ENGLISH));
    }

    public CookieSpec getCookieSpec(String string, HttpParams httpParams) throws IllegalStateException {
        Args.notNull(string, "Name");
        CookieSpecFactory cookieSpecFactory = this.registeredSpecs.get(string.toLowerCase(Locale.ENGLISH));
        if (cookieSpecFactory != null) {
            return cookieSpecFactory.newInstance(httpParams);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + string);
    }

    public CookieSpec getCookieSpec(String string) throws IllegalStateException {
        return this.getCookieSpec(string, null);
    }

    public List<String> getSpecNames() {
        return new ArrayList<String>(this.registeredSpecs.keySet());
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSpecs.clear();
        this.registeredSpecs.putAll(map);
    }

    @Override
    public CookieSpecProvider lookup(String string) {
        return new CookieSpecProvider(this, string){
            final String val$name;
            final CookieSpecRegistry this$0;
            {
                this.this$0 = cookieSpecRegistry;
                this.val$name = string;
            }

            @Override
            public CookieSpec create(HttpContext httpContext) {
                HttpRequest httpRequest = (HttpRequest)httpContext.getAttribute("http.request");
                return this.this$0.getCookieSpec(this.val$name, httpRequest.getParams());
            }
        };
    }

    @Override
    public Object lookup(String string) {
        return this.lookup(string);
    }
}

