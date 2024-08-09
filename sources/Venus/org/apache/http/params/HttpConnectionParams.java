/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public final class HttpConnectionParams
implements CoreConnectionPNames {
    private HttpConnectionParams() {
    }

    public static int getSoTimeout(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.timeout", 0);
    }

    public static void setSoTimeout(HttpParams httpParams, int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.timeout", n);
    }

    public static boolean getSoReuseaddr(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.socket.reuseaddr", false);
    }

    public static void setSoReuseaddr(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.socket.reuseaddr", bl);
    }

    public static boolean getTcpNoDelay(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.tcp.nodelay", true);
    }

    public static void setTcpNoDelay(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.tcp.nodelay", bl);
    }

    public static int getSocketBufferSize(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.buffer-size", -1);
    }

    public static void setSocketBufferSize(HttpParams httpParams, int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.buffer-size", n);
    }

    public static int getLinger(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.socket.linger", -1);
    }

    public static void setLinger(HttpParams httpParams, int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.socket.linger", n);
    }

    public static int getConnectionTimeout(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.connection.timeout", 0);
    }

    public static void setConnectionTimeout(HttpParams httpParams, int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.connection.timeout", n);
    }

    public static boolean isStaleCheckingEnabled(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.connection.stalecheck", true);
    }

    public static void setStaleCheckingEnabled(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.connection.stalecheck", bl);
    }

    public static boolean getSoKeepalive(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.socket.keepalive", false);
    }

    public static void setSoKeepalive(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.socket.keepalive", bl);
    }
}

