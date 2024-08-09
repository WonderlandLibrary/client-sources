/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.net.URI;
import java.util.List;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Lookup;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

public class HttpClientContext
extends HttpCoreContext {
    public static final String HTTP_ROUTE = "http.route";
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    public static final String COOKIESPEC_REGISTRY = "http.cookiespec-registry";
    public static final String COOKIE_SPEC = "http.cookie-spec";
    public static final String COOKIE_ORIGIN = "http.cookie-origin";
    public static final String COOKIE_STORE = "http.cookie-store";
    public static final String CREDS_PROVIDER = "http.auth.credentials-provider";
    public static final String AUTH_CACHE = "http.auth.auth-cache";
    public static final String TARGET_AUTH_STATE = "http.auth.target-scope";
    public static final String PROXY_AUTH_STATE = "http.auth.proxy-scope";
    public static final String USER_TOKEN = "http.user-token";
    public static final String AUTHSCHEME_REGISTRY = "http.authscheme-registry";
    public static final String REQUEST_CONFIG = "http.request-config";

    public static HttpClientContext adapt(HttpContext httpContext) {
        return httpContext instanceof HttpClientContext ? (HttpClientContext)httpContext : new HttpClientContext(httpContext);
    }

    public static HttpClientContext create() {
        return new HttpClientContext(new BasicHttpContext());
    }

    public HttpClientContext(HttpContext httpContext) {
        super(httpContext);
    }

    public HttpClientContext() {
    }

    public RouteInfo getHttpRoute() {
        return this.getAttribute(HTTP_ROUTE, HttpRoute.class);
    }

    public List<URI> getRedirectLocations() {
        return this.getAttribute(REDIRECT_LOCATIONS, List.class);
    }

    public CookieStore getCookieStore() {
        return this.getAttribute(COOKIE_STORE, CookieStore.class);
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.setAttribute(COOKIE_STORE, cookieStore);
    }

    public CookieSpec getCookieSpec() {
        return this.getAttribute(COOKIE_SPEC, CookieSpec.class);
    }

    public CookieOrigin getCookieOrigin() {
        return this.getAttribute(COOKIE_ORIGIN, CookieOrigin.class);
    }

    private <T> Lookup<T> getLookup(String string, Class<T> clazz) {
        return this.getAttribute(string, Lookup.class);
    }

    public Lookup<CookieSpecProvider> getCookieSpecRegistry() {
        return this.getLookup(COOKIESPEC_REGISTRY, CookieSpecProvider.class);
    }

    public void setCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        this.setAttribute(COOKIESPEC_REGISTRY, lookup);
    }

    public Lookup<AuthSchemeProvider> getAuthSchemeRegistry() {
        return this.getLookup(AUTHSCHEME_REGISTRY, AuthSchemeProvider.class);
    }

    public void setAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        this.setAttribute(AUTHSCHEME_REGISTRY, lookup);
    }

    public CredentialsProvider getCredentialsProvider() {
        return this.getAttribute(CREDS_PROVIDER, CredentialsProvider.class);
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.setAttribute(CREDS_PROVIDER, credentialsProvider);
    }

    public AuthCache getAuthCache() {
        return this.getAttribute(AUTH_CACHE, AuthCache.class);
    }

    public void setAuthCache(AuthCache authCache) {
        this.setAttribute(AUTH_CACHE, authCache);
    }

    public AuthState getTargetAuthState() {
        return this.getAttribute(TARGET_AUTH_STATE, AuthState.class);
    }

    public AuthState getProxyAuthState() {
        return this.getAttribute(PROXY_AUTH_STATE, AuthState.class);
    }

    public <T> T getUserToken(Class<T> clazz) {
        return this.getAttribute(USER_TOKEN, clazz);
    }

    public Object getUserToken() {
        return this.getAttribute(USER_TOKEN);
    }

    public void setUserToken(Object object) {
        this.setAttribute(USER_TOKEN, object);
    }

    public RequestConfig getRequestConfig() {
        RequestConfig requestConfig = this.getAttribute(REQUEST_CONFIG, RequestConfig.class);
        return requestConfig != null ? requestConfig : RequestConfig.DEFAULT;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.setAttribute(REQUEST_CONFIG, requestConfig);
    }
}

