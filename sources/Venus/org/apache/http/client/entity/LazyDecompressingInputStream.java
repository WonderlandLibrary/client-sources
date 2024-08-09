/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.entity.InputStreamFactory;

class LazyDecompressingInputStream
extends InputStream {
    private final InputStream wrappedStream;
    private final InputStreamFactory inputStreamFactory;
    private InputStream wrapperStream;

    public LazyDecompressingInputStream(InputStream inputStream, InputStreamFactory inputStreamFactory) {
        this.wrappedStream = inputStream;
        this.inputStreamFactory = inputStreamFactory;
    }

    private void initWrapper() throws IOException {
        if (this.wrapperStream == null) {
            this.wrapperStream = this.inputStreamFactory.create(this.wrappedStream);
        }
    }

    @Override
    public int read() throws IOException {
        this.initWrapper();
        return this.wrapperStream.read();
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        this.initWrapper();
        return this.wrapperStream.read(byArray);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        this.initWrapper();
        return this.wrapperStream.read(byArray, n, n2);
    }

    @Override
    public long skip(long l) throws IOException {
        this.initWrapper();
        return this.wrapperStream.skip(l);
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int available() throws IOException {
        this.initWrapper();
        return this.wrapperStream.available();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        try {
            if (this.wrapperStream != null) {
                this.wrapperStream.close();
            }
        } finally {
            this.wrappedStream.close();
        }
    }
}

