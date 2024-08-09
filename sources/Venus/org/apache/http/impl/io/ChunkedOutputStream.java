/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class ChunkedOutputStream
extends OutputStream {
    private final SessionOutputBuffer out;
    private final byte[] cache;
    private int cachePosition = 0;
    private boolean wroteLastChunk = false;
    private boolean closed = false;

    @Deprecated
    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer, int n) throws IOException {
        this(n, sessionOutputBuffer);
    }

    @Deprecated
    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer) throws IOException {
        this(2048, sessionOutputBuffer);
    }

    public ChunkedOutputStream(int n, SessionOutputBuffer sessionOutputBuffer) {
        this.cache = new byte[n];
        this.out = sessionOutputBuffer;
    }

    protected void flushCache() throws IOException {
        if (this.cachePosition > 0) {
            this.out.writeLine(Integer.toHexString(this.cachePosition));
            this.out.write(this.cache, 0, this.cachePosition);
            this.out.writeLine("");
            this.cachePosition = 0;
        }
    }

    protected void flushCacheWithAppend(byte[] byArray, int n, int n2) throws IOException {
        this.out.writeLine(Integer.toHexString(this.cachePosition + n2));
        this.out.write(this.cache, 0, this.cachePosition);
        this.out.write(byArray, n, n2);
        this.out.writeLine("");
        this.cachePosition = 0;
    }

    protected void writeClosingChunk() throws IOException {
        this.out.writeLine("0");
        this.out.writeLine("");
    }

    public void finish() throws IOException {
        if (!this.wroteLastChunk) {
            this.flushCache();
            this.writeClosingChunk();
            this.wroteLastChunk = true;
        }
    }

    @Override
    public void write(int n) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        this.cache[this.cachePosition] = (byte)n;
        ++this.cachePosition;
        if (this.cachePosition == this.cache.length) {
            this.flushCache();
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        if (n2 >= this.cache.length - this.cachePosition) {
            this.flushCacheWithAppend(byArray, n, n2);
        } else {
            System.arraycopy(byArray, n, this.cache, this.cachePosition, n2);
            this.cachePosition += n2;
        }
    }

    @Override
    public void flush() throws IOException {
        this.flushCache();
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.finish();
            this.out.flush();
        }
    }
}

