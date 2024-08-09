/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.impl.conn.Wire;

class LoggingOutputStream
extends OutputStream {
    private final OutputStream out;
    private final Wire wire;

    public LoggingOutputStream(OutputStream outputStream, Wire wire) {
        this.out = outputStream;
        this.wire = wire;
    }

    @Override
    public void write(int n) throws IOException {
        try {
            this.wire.output(n);
        } catch (IOException iOException) {
            this.wire.output("[write] I/O error: " + iOException.getMessage());
            throw iOException;
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        try {
            this.wire.output(byArray);
            this.out.write(byArray);
        } catch (IOException iOException) {
            this.wire.output("[write] I/O error: " + iOException.getMessage());
            throw iOException;
        }
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        try {
            this.wire.output(byArray, n, n2);
            this.out.write(byArray, n, n2);
        } catch (IOException iOException) {
            this.wire.output("[write] I/O error: " + iOException.getMessage());
            throw iOException;
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (IOException iOException) {
            this.wire.output("[flush] I/O error: " + iOException.getMessage());
            throw iOException;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.out.close();
        } catch (IOException iOException) {
            this.wire.output("[close] I/O error: " + iOException.getMessage());
            throw iOException;
        }
    }
}

