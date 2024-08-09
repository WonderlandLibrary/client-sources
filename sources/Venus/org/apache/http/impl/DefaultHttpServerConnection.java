/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.impl.SocketHttpServerConnection;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public class DefaultHttpServerConnection
extends SocketHttpServerConnection {
    @Override
    public void bind(Socket socket, HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        Args.notNull(httpParams, "HTTP parameters");
        this.assertNotOpen();
        socket.setTcpNoDelay(httpParams.getBooleanParameter("http.tcp.nodelay", true));
        socket.setSoTimeout(httpParams.getIntParameter("http.socket.timeout", 0));
        socket.setKeepAlive(httpParams.getBooleanParameter("http.socket.keepalive", false));
        int n = httpParams.getIntParameter("http.socket.linger", -1);
        if (n >= 0) {
            socket.setSoLinger(n > 0, n);
        }
        if (n >= 0) {
            socket.setSoLinger(n > 0, n);
        }
        super.bind(socket, httpParams);
    }
}

