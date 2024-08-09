/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.impl.io.AbstractSessionInputBuffer;
import org.apache.http.io.EofSensor;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Deprecated
public class SocketInputBuffer
extends AbstractSessionInputBuffer
implements EofSensor {
    private final Socket socket;
    private boolean eof;

    public SocketInputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        this.socket = socket;
        this.eof = false;
        int n2 = n;
        if (n2 < 0) {
            n2 = socket.getReceiveBufferSize();
        }
        if (n2 < 1024) {
            n2 = 1024;
        }
        this.init(socket.getInputStream(), n2, httpParams);
    }

    @Override
    protected int fillBuffer() throws IOException {
        int n = super.fillBuffer();
        this.eof = n == -1;
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isDataAvailable(int n) throws IOException {
        boolean bl = this.hasBufferedData();
        if (!bl) {
            int n2 = this.socket.getSoTimeout();
            try {
                this.socket.setSoTimeout(n);
                this.fillBuffer();
                bl = this.hasBufferedData();
            } finally {
                this.socket.setSoTimeout(n2);
            }
        }
        return bl;
    }

    @Override
    public boolean isEof() {
        return this.eof;
    }
}

