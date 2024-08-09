/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

public class DeflateInputStream
extends InputStream {
    private final InputStream sourceStream;

    public DeflateInputStream(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 2);
        int n = pushbackInputStream.read();
        int n2 = pushbackInputStream.read();
        if (n == -1 || n2 == -1) {
            throw new ZipException("Unexpected end of stream");
        }
        pushbackInputStream.unread(n2);
        pushbackInputStream.unread(n);
        boolean bl = true;
        int n3 = n & 0xFF;
        int n4 = n3 & 0xF;
        int n5 = n3 >> 4 & 0xF;
        int n6 = n2 & 0xFF;
        if (n4 == 8 && n5 <= 7 && (n3 << 8 | n6) % 31 == 0) {
            bl = false;
        }
        this.sourceStream = new DeflateStream(pushbackInputStream, new Inflater(bl));
    }

    @Override
    public int read() throws IOException {
        return this.sourceStream.read();
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.sourceStream.read(byArray);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        return this.sourceStream.read(byArray, n, n2);
    }

    @Override
    public long skip(long l) throws IOException {
        return this.sourceStream.skip(l);
    }

    @Override
    public int available() throws IOException {
        return this.sourceStream.available();
    }

    @Override
    public void mark(int n) {
        this.sourceStream.mark(n);
    }

    @Override
    public void reset() throws IOException {
        this.sourceStream.reset();
    }

    @Override
    public boolean markSupported() {
        return this.sourceStream.markSupported();
    }

    @Override
    public void close() throws IOException {
        this.sourceStream.close();
    }

    static class DeflateStream
    extends InflaterInputStream {
        private boolean closed = false;

        public DeflateStream(InputStream inputStream, Inflater inflater) {
            super(inputStream, inflater);
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.inf.end();
            super.close();
        }
    }
}

