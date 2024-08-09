/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
class SocketFactoryAdaptor
implements SocketFactory {
    private final SchemeSocketFactory factory;

    SocketFactoryAdaptor(SchemeSocketFactory schemeSocketFactory) {
        this.factory = schemeSocketFactory;
    }

    @Override
    public Socket createSocket() throws IOException {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        return this.factory.createSocket(basicHttpParams);
    }

    @Override
    public Socket connectSocket(Socket socket, String string, int n, InetAddress inetAddress, int n2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, n2 > 0 ? n2 : 0);
        }
        InetAddress inetAddress2 = InetAddress.getByName(string);
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress(inetAddress2, n);
        return this.factory.connectSocket(socket, inetSocketAddress2, inetSocketAddress, httpParams);
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }

    public SchemeSocketFactory getFactory() {
        return this.factory;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        return object instanceof SocketFactoryAdaptor ? this.factory.equals(((SocketFactoryAdaptor)object).factory) : this.factory.equals(object);
    }

    public int hashCode() {
        return this.factory.hashCode();
    }
}

