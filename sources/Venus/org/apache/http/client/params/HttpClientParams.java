/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.params;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public class HttpClientParams {
    private HttpClientParams() {
    }

    public static boolean isRedirecting(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.handle-redirects", true);
    }

    public static void setRedirecting(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.handle-redirects", bl);
    }

    public static boolean isAuthenticating(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.handle-authentication", true);
    }

    public static void setAuthenticating(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.handle-authentication", bl);
    }

    public static String getCookiePolicy(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String string = (String)httpParams.getParameter("http.protocol.cookie-policy");
        if (string == null) {
            return "best-match";
        }
        return string;
    }

    public static void setCookiePolicy(HttpParams httpParams, String string) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.cookie-policy", string);
    }

    public static void setConnectionManagerTimeout(HttpParams httpParams, long l) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setLongParameter("http.conn-manager.timeout", l);
    }

    public static long getConnectionManagerTimeout(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        Long l = (Long)httpParams.getParameter("http.conn-manager.timeout");
        if (l != null) {
            return l;
        }
        return HttpConnectionParams.getConnectionTimeout(httpParams);
    }
}

