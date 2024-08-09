/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.lang.Bytes;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class FilteredInputStream
extends FilterInputStream {
    public FilteredInputStream(InputStream inputStream) {
        super(inputStream);
    }

    protected void afterRead(int n) throws IOException {
    }

    @Override
    public int available() throws IOException {
        try {
            return super.available();
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
            return 1;
        }
    }

    protected void beforeRead(int n) throws IOException {
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    protected void onThrowable(Throwable throwable) throws IOException {
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        throw new IOException("IO Exception: " + throwable.getMessage(), throwable);
    }

    @Override
    public synchronized void mark(int n) {
        this.in.mark(n);
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override
    public int read() throws IOException {
        try {
            this.beforeRead(1);
            int n = this.in.read();
            this.afterRead(n != -1 ? 1 : -1);
            return n;
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
            return 1;
        }
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        try {
            this.beforeRead(Bytes.length(byArray));
            int n = this.in.read(byArray);
            this.afterRead(n);
            return n;
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
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
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
            return 1;
        }
    }

    @Override
    public synchronized void reset() throws IOException {
        try {
            this.in.reset();
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    @Override
    public long skip(long l) throws IOException {
        try {
            return this.in.skip(l);
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
            return 0L;
        }
    }
}

