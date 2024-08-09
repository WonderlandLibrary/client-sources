/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public abstract class ProxyReader
extends FilterReader {
    public ProxyReader(Reader reader) {
        super(reader);
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
    public int read(char[] cArray) throws IOException {
        try {
            this.beforeRead(cArray != null ? cArray.length : 0);
            int n = this.in.read(cArray);
            this.afterRead(n);
            return n;
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        try {
            this.beforeRead(n2);
            int n3 = this.in.read(cArray, n, n2);
            this.afterRead(n3);
            return n3;
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return 1;
        }
    }

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        try {
            this.beforeRead(charBuffer != null ? charBuffer.length() : 0);
            int n = this.in.read(charBuffer);
            this.afterRead(n);
            return n;
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
    public boolean ready() throws IOException {
        try {
            return this.in.ready();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
            return true;
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
    public synchronized void mark(int n) throws IOException {
        try {
            this.in.mark(n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
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

