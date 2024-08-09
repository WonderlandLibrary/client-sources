/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.params;

import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.params.HttpParams;

@Deprecated
public final class HttpClientParamConfig {
    private HttpClientParamConfig() {
    }

    public static RequestConfig getRequestConfig(HttpParams httpParams) {
        return HttpClientParamConfig.getRequestConfig(httpParams, RequestConfig.DEFAULT);
    }

    public static RequestConfig getRequestConfig(HttpParams httpParams, RequestConfig requestConfig) {
        String string;
        Collection collection;
        Collection collection2;
        InetAddress inetAddress;
        RequestConfig.Builder builder = RequestConfig.copy(requestConfig).setSocketTimeout(httpParams.getIntParameter("http.socket.timeout", requestConfig.getSocketTimeout())).setStaleConnectionCheckEnabled(httpParams.getBooleanParameter("http.connection.stalecheck", requestConfig.isStaleConnectionCheckEnabled())).setConnectTimeout(httpParams.getIntParameter("http.connection.timeout", requestConfig.getConnectTimeout())).setExpectContinueEnabled(httpParams.getBooleanParameter("http.protocol.expect-continue", requestConfig.isExpectContinueEnabled())).setAuthenticationEnabled(httpParams.getBooleanParameter("http.protocol.handle-authentication", requestConfig.isAuthenticationEnabled())).setCircularRedirectsAllowed(httpParams.getBooleanParameter("http.protocol.allow-circular-redirects", requestConfig.isCircularRedirectsAllowed())).setConnectionRequestTimeout((int)httpParams.getLongParameter("http.conn-manager.timeout", requestConfig.getConnectionRequestTimeout())).setMaxRedirects(httpParams.getIntParameter("http.protocol.max-redirects", requestConfig.getMaxRedirects())).setRedirectsEnabled(httpParams.getBooleanParameter("http.protocol.handle-redirects", requestConfig.isRedirectsEnabled())).setRelativeRedirectsAllowed(!httpParams.getBooleanParameter("http.protocol.reject-relative-redirect", !requestConfig.isRelativeRedirectsAllowed()));
        HttpHost httpHost = (HttpHost)httpParams.getParameter("http.route.default-proxy");
        if (httpHost != null) {
            builder.setProxy(httpHost);
        }
        if ((inetAddress = (InetAddress)httpParams.getParameter("http.route.local-address")) != null) {
            builder.setLocalAddress(inetAddress);
        }
        if ((collection2 = (Collection)httpParams.getParameter("http.auth.target-scheme-pref")) != null) {
            builder.setTargetPreferredAuthSchemes(collection2);
        }
        if ((collection = (Collection)httpParams.getParameter("http.auth.proxy-scheme-pref")) != null) {
            builder.setProxyPreferredAuthSchemes(collection);
        }
        if ((string = (String)httpParams.getParameter("http.protocol.cookie-policy")) != null) {
            builder.setCookieSpec(string);
        }
        return builder.build();
    }
}

