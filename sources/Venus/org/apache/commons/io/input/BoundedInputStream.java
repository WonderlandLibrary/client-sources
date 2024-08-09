/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class BoundedInputStream
extends InputStream {
    private final InputStream in;
    private final long max;
    private long pos = 0L;
    private long mark = -1L;
    private boolean propagateClose = true;

    public BoundedInputStream(InputStream inputStream, long l) {
        this.max = l;
        this.in = inputStream;
    }

    public BoundedInputStream(InputStream inputStream) {
        this(inputStream, -1L);
    }

    @Override
    public int read() throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return 1;
        }
        int n = this.in.read();
        ++this.pos;
        return n;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return 1;
        }
        long l = this.max >= 0L ? Math.min((long)n2, this.max - this.pos) : (long)n2;
        int n3 = this.in.read(byArray, n, (int)l);
        if (n3 == -1) {
            return 1;
        }
        this.pos += (long)n3;
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        long l2 = this.max >= 0L ? Math.min(l, this.max - this.pos) : l;
        long l3 = this.in.skip(l2);
        this.pos += l3;
        return l3;
    }

    @Override
    public int available() throws IOException {
        if (this.max >= 0L && this.pos >= this.max) {
            return 1;
        }
        return this.in.available();
    }

    public String toString() {
        return this.in.toString();
    }

    @Override
    public void close() throws IOException {
        if (this.propagateClose) {
            this.in.close();
        }
    }

    @Override
    public synchronized void reset() throws IOException {
        this.in.reset();
        this.pos = this.mark;
    }

    @Override
    public synchronized void mark(int n) {
        this.in.mark(n);
        this.mark = this.pos;
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    public boolean isPropagateClose() {
        return this.propagateClose;
    }

    public void setPropagateClose(boolean bl) {
        this.propagateClose = bl;
    }
}

