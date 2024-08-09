/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Arrays;
import org.apache.http.HttpHost;

public class HttpHostConnectException
extends ConnectException {
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost host;

    @Deprecated
    public HttpHostConnectException(HttpHost httpHost, ConnectException connectException) {
        this(connectException, httpHost, null);
    }

    public HttpHostConnectException(IOException iOException, HttpHost httpHost, InetAddress ... inetAddressArray) {
        super("Connect to " + (httpHost != null ? httpHost.toHostString() : "remote host") + (inetAddressArray != null && inetAddressArray.length > 0 ? " " + Arrays.asList(inetAddressArray) : "") + (iOException != null && iOException.getMessage() != null ? " failed: " + iOException.getMessage() : " refused"));
        this.host = httpHost;
        this.initCause(iOException);
    }

    public HttpHost getHost() {
        return this.host;
    }
}

