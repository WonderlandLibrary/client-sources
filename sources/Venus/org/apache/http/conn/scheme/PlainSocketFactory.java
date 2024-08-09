/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class PlainSocketFactory
implements SocketFactory,
SchemeSocketFactory {
    private final HostNameResolver nameResolver;

    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
    }

    @Deprecated
    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public PlainSocketFactory() {
        this.nameResolver = null;
    }

    @Override
    public Socket createSocket(HttpParams httpParams) {
        return new Socket();
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException, ConnectTimeoutException {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        Socket socket2 = socket;
        if (socket2 == null) {
            socket2 = this.createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket2.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket2.bind(inetSocketAddress2);
        }
        int n = HttpConnectionParams.getConnectionTimeout(httpParams);
        int n2 = HttpConnectionParams.getSoTimeout(httpParams);
        try {
            socket2.setSoTimeout(n2);
            socket2.connect(inetSocketAddress, n);
        } catch (SocketTimeoutException socketTimeoutException) {
            throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
        }
        return socket2;
    }

    @Override
    public final boolean isSecure(Socket socket) {
        return true;
    }

    @Override
    @Deprecated
    public Socket connectSocket(Socket socket, String string, int n, InetAddress inetAddress, int n2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, n2 > 0 ? n2 : 0);
        }
        InetAddress inetAddress2 = this.nameResolver != null ? this.nameResolver.resolve(string) : InetAddress.getByName(string);
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress(inetAddress2, n);
        return this.connectSocket(socket, inetSocketAddress2, inetSocketAddress, httpParams);
    }
}

