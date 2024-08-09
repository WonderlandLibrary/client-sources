/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.util.Arrays;
import org.apache.http.HttpHost;

public class ConnectTimeoutException
extends InterruptedIOException {
    private static final long serialVersionUID = -4816682903149535989L;
    private final HttpHost host;

    public ConnectTimeoutException() {
        this.host = null;
    }

    public ConnectTimeoutException(String string) {
        super(string);
        this.host = null;
    }

    public ConnectTimeoutException(IOException iOException, HttpHost httpHost, InetAddress ... inetAddressArray) {
        super("Connect to " + (httpHost != null ? httpHost.toHostString() : "remote host") + (inetAddressArray != null && inetAddressArray.length > 0 ? " " + Arrays.asList(inetAddressArray) : "") + (iOException != null && iOException.getMessage() != null ? " failed: " + iOException.getMessage() : " timed out"));
        this.host = httpHost;
        this.initCause(iOException);
    }

    public HttpHost getHost() {
        return this.host;
    }
}

