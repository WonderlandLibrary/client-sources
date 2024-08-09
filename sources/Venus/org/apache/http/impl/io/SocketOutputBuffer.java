/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.impl.io.AbstractSessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public class SocketOutputBuffer
extends AbstractSessionOutputBuffer {
    public SocketOutputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        int n2 = n;
        if (n2 < 0) {
            n2 = socket.getSendBufferSize();
        }
        if (n2 < 1024) {
            n2 = 1024;
        }
        this.init(socket.getOutputStream(), n2, httpParams);
    }
}

