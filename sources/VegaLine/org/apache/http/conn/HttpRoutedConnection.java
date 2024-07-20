/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import javax.net.ssl.SSLSession;
import org.apache.http.HttpInetConnection;
import org.apache.http.conn.routing.HttpRoute;

@Deprecated
public interface HttpRoutedConnection
extends HttpInetConnection {
    public boolean isSecure();

    public HttpRoute getRoute();

    public SSLSession getSSLSession();
}

