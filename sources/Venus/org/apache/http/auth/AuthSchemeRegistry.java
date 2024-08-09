/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.config.Lookup;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public final class AuthSchemeRegistry
implements Lookup<AuthSchemeProvider> {
    private final ConcurrentHashMap<String, AuthSchemeFactory> registeredSchemes = new ConcurrentHashMap();

    public void register(String string, AuthSchemeFactory authSchemeFactory) {
        Args.notNull(string, "Name");
        Args.notNull(authSchemeFactory, "Authentication scheme factory");
        this.registeredSchemes.put(string.toLowerCase(Locale.ENGLISH), authSchemeFactory);
    }

    public void unregister(String string) {
        Args.notNull(string, "Name");
        this.registeredSchemes.remove(string.toLowerCase(Locale.ENGLISH));
    }

    public AuthScheme getAuthScheme(String string, HttpParams httpParams) throws IllegalStateException {
        Args.notNull(string, "Name");
        AuthSchemeFactory authSchemeFactory = this.registeredSchemes.get(string.toLowerCase(Locale.ENGLISH));
        if (authSchemeFactory != null) {
            return authSchemeFactory.newInstance(httpParams);
        }
        throw new IllegalStateException("Unsupported authentication scheme: " + string);
    }

    public List<String> getSchemeNames() {
        return new ArrayList<String>(this.registeredSchemes.keySet());
    }

    public void setItems(Map<String, AuthSchemeFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }

    @Override
    public AuthSchemeProvider lookup(String string) {
        return new AuthSchemeProvider(this, string){
            final String val$name;
            final AuthSchemeRegistry this$0;
            {
                this.this$0 = authSchemeRegistry;
                this.val$name = string;
            }

            @Override
            public AuthScheme create(HttpContext httpContext) {
                HttpRequest httpRequest = (HttpRequest)httpContext.getAttribute("http.request");
                return this.this$0.getAuthScheme(this.val$name, httpRequest.getParams());
            }
        };
    }

    @Override
    public Object lookup(String string) {
        return this.lookup(string);
    }
}

