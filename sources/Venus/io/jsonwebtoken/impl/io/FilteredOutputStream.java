/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.lang.Bytes;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FilteredOutputStream
extends FilterOutputStream {
    public FilteredOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    protected void afterWrite(int n) throws IOException {
    }

    protected void beforeWrite(int n) throws IOException {
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    protected void onThrowable(Throwable throwable) throws IOException {
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        throw new IOException("IO Exception " + throwable.getMessage(), throwable);
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        try {
            int n = Bytes.length(byArray);
            this.beforeWrite(n);
            this.out.write(byArray);
            this.afterWrite(n);
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        try {
            this.beforeWrite(n2);
            this.out.write(byArray, n, n2);
            this.afterWrite(n2);
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }

    @Override
    public void write(int n) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.write(n);
            this.afterWrite(1);
        } catch (Throwable throwable) {
            this.onThrowable(throwable);
        }
    }
}

