/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

public class ContentLengthInputStream
extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private final long contentLength;
    private long pos = 0L;
    private boolean closed = false;
    private SessionInputBuffer in = null;

    public ContentLengthInputStream(SessionInputBuffer sessionInputBuffer, long l) {
        this.in = Args.notNull(sessionInputBuffer, "Session input buffer");
        this.contentLength = Args.notNegative(l, "Content length");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            try {
                if (this.pos < this.contentLength) {
                    byte[] byArray = new byte[2048];
                    while (this.read(byArray) >= 0) {
                    }
                }
            } finally {
                this.closed = true;
            }
        }
    }

    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            int n = ((BufferInfo)((Object)this.in)).length();
            return Math.min(n, (int)(this.contentLength - this.pos));
        }
        return 1;
    }

    @Override
    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.pos >= this.contentLength) {
            return 1;
        }
        int n = this.in.read();
        if (n == -1) {
            if (this.pos < this.contentLength) {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: %,d; received: %,d)", this.contentLength, this.pos);
            }
        } else {
            ++this.pos;
        }
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.pos >= this.contentLength) {
            return 1;
        }
        int n4 = n2;
        if (this.pos + (long)n2 > this.contentLength) {
            n4 = (int)(this.contentLength - this.pos);
        }
        if ((n3 = this.in.read(byArray, n, n4)) == -1 && this.pos < this.contentLength) {
            throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: %,d; received: %,d)", this.contentLength, this.pos);
        }
        if (n3 > 0) {
            this.pos += (long)n3;
        }
        return n3;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public long skip(long l) throws IOException {
        int n;
        if (l <= 0L) {
            return 0L;
        }
        byte[] byArray = new byte[2048];
        long l2 = 0L;
        for (long i = Math.min(l, this.contentLength - this.pos); i > 0L && (n = this.read(byArray, 0, (int)Math.min(2048L, i))) != -1; i -= (long)n) {
            l2 += (long)n;
        }
        return l2;
    }
}

