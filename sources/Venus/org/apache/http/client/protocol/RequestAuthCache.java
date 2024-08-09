/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestAuthCache
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        AuthScheme authScheme;
        Object object;
        AuthState authState;
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        AuthCache authCache = httpClientContext.getAuthCache();
        if (authCache == null) {
            this.log.debug("Auth cache not set in the context");
            return;
        }
        CredentialsProvider credentialsProvider = httpClientContext.getCredentialsProvider();
        if (credentialsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return;
        }
        RouteInfo routeInfo = httpClientContext.getHttpRoute();
        if (routeInfo == null) {
            this.log.debug("Route info not set in the context");
            return;
        }
        HttpHost httpHost = httpClientContext.getTargetHost();
        if (httpHost == null) {
            this.log.debug("Target host not set in the context");
            return;
        }
        if (httpHost.getPort() < 0) {
            httpHost = new HttpHost(httpHost.getHostName(), routeInfo.getTargetHost().getPort(), httpHost.getSchemeName());
        }
        if ((authState = httpClientContext.getTargetAuthState()) != null && authState.getState() == AuthProtocolState.UNCHALLENGED && (object = authCache.get(httpHost)) != null) {
            this.doPreemptiveAuth(httpHost, (AuthScheme)object, authState, credentialsProvider);
        }
        object = routeInfo.getProxyHost();
        AuthState authState2 = httpClientContext.getProxyAuthState();
        if (object != null && authState2 != null && authState2.getState() == AuthProtocolState.UNCHALLENGED && (authScheme = authCache.get((HttpHost)object)) != null) {
            this.doPreemptiveAuth((HttpHost)object, authScheme, authState2, credentialsProvider);
        }
    }

    private void doPreemptiveAuth(HttpHost httpHost, AuthScheme authScheme, AuthState authState, CredentialsProvider credentialsProvider) {
        AuthScope authScope;
        Credentials credentials;
        String string = authScheme.getSchemeName();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Re-using cached '" + string + "' auth scheme for " + httpHost);
        }
        if ((credentials = credentialsProvider.getCredentials(authScope = new AuthScope(httpHost, AuthScope.ANY_REALM, string))) != null) {
            authState.update(authScheme, credentials);
        } else {
            this.log.debug("No credentials for preemptive authentication");
        }
    }
}

