/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class ResponseAuthCache
implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Object object;
        Object object2;
        Args.notNull(httpResponse, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        AuthCache authCache = (AuthCache)httpContext.getAttribute("http.auth.auth-cache");
        HttpHost httpHost = (HttpHost)httpContext.getAttribute("http.target_host");
        AuthState authState = (AuthState)httpContext.getAttribute("http.auth.target-scope");
        if (httpHost != null && authState != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Target auth state: " + (Object)((Object)authState.getState()));
            }
            if (this.isCachable(authState)) {
                object2 = (SchemeRegistry)httpContext.getAttribute("http.scheme-registry");
                if (httpHost.getPort() < 0) {
                    object = ((SchemeRegistry)object2).getScheme(httpHost);
                    httpHost = new HttpHost(httpHost.getHostName(), ((Scheme)object).resolvePort(httpHost.getPort()), httpHost.getSchemeName());
                }
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute("http.auth.auth-cache", authCache);
                }
                switch (1.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()]) {
                    case 1: {
                        this.cache(authCache, httpHost, authState.getAuthScheme());
                        break;
                    }
                    case 2: {
                        this.uncache(authCache, httpHost, authState.getAuthScheme());
                    }
                }
            }
        }
        object2 = (HttpHost)httpContext.getAttribute("http.proxy_host");
        object = (AuthState)httpContext.getAttribute("http.auth.proxy-scope");
        if (object2 != null && object != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Proxy auth state: " + (Object)((Object)((AuthState)object).getState()));
            }
            if (this.isCachable((AuthState)object)) {
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute("http.auth.auth-cache", authCache);
                }
                switch (1.$SwitchMap$org$apache$http$auth$AuthProtocolState[((AuthState)object).getState().ordinal()]) {
                    case 1: {
                        this.cache(authCache, (HttpHost)object2, ((AuthState)object).getAuthScheme());
                        break;
                    }
                    case 2: {
                        this.uncache(authCache, (HttpHost)object2, ((AuthState)object).getAuthScheme());
                    }
                }
            }
        }
    }

    private boolean isCachable(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete()) {
            return true;
        }
        String string = authScheme.getSchemeName();
        return string.equalsIgnoreCase("Basic") || string.equalsIgnoreCase("Digest");
    }

    private void cache(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
        }
        authCache.put(httpHost, authScheme);
    }

    private void uncache(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
        }
        authCache.remove(httpHost);
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.CHALLENGED.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.FAILURE.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

