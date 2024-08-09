/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProxyOutputStream
extends FilterOutputStream {
    public ProxyOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(int n) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.write(n);
            this.afterWrite(1);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        try {
            int n = byArray != null ? byArray.length : 0;
            this.beforeWrite(n);
            this.out.write(byArray);
            this.afterWrite(n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        try {
            this.beforeWrite(n2);
            this.out.write(byArray, n, n2);
            this.afterWrite(n2);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.out.close();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    protected void beforeWrite(int n) throws IOException {
    }

    protected void afterWrite(int n) throws IOException {
    }

    protected void handleIOException(IOException iOException) throws IOException {
        throw iOException;
    }
}

