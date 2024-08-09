/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class ProxyInputStream
extends FilterInputStream {
    public ProxyInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read() throws IOException {
        try {
            this.beforeRead(1);
            int n = this.in.read();
            this.afterRead(n != -1 ? 1 : -1);
            return n;
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        try {
            this.beforeRead(byArray != null ? byArray.length : 0);
            int n = this.in.read(byArray);
            this.afterRead(n);
            return n;
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        try {
            this.beforeRead(n2);
            int n3 = this.in.read(byArray, n, n2);
            this.afterRead(n3);
            return n3;
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        try {
            return this.in.skip(l);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 0L;
        }
    }

    @Override
    public int available() throws IOException {
        try {
            return super.available();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.in.close();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public synchronized void mark(int n) {
        this.in.mark(n);
    }

    @Override
    public synchronized void reset() throws IOException {
        try {
            this.in.reset();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    protected void beforeRead(int n) throws IOException {
    }

    protected void afterRead(int n) throws IOException {
    }

    protected void handleIOException(IOException iOException) throws IOException {
        throw iOException;
    }
}

