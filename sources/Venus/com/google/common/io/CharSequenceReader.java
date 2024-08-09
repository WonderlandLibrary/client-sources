/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

@GwtIncompatible
final class CharSequenceReader
extends Reader {
    private CharSequence seq;
    private int pos;
    private int mark;

    public CharSequenceReader(CharSequence charSequence) {
        this.seq = Preconditions.checkNotNull(charSequence);
    }

    private void checkOpen() throws IOException {
        if (this.seq == null) {
            throw new IOException("reader closed");
        }
    }

    private boolean hasRemaining() {
        return this.remaining() > 0;
    }

    private int remaining() {
        return this.seq.length() - this.pos;
    }

    @Override
    public synchronized int read(CharBuffer charBuffer) throws IOException {
        Preconditions.checkNotNull(charBuffer);
        this.checkOpen();
        if (!this.hasRemaining()) {
            return 1;
        }
        int n = Math.min(charBuffer.remaining(), this.remaining());
        for (int i = 0; i < n; ++i) {
            charBuffer.put(this.seq.charAt(this.pos++));
        }
        return n;
    }

    @Override
    public synchronized int read() throws IOException {
        this.checkOpen();
        return this.hasRemaining() ? (int)this.seq.charAt(this.pos++) : -1;
    }

    @Override
    public synchronized int read(char[] cArray, int n, int n2) throws IOException {
        Preconditions.checkPositionIndexes(n, n + n2, cArray.length);
        this.checkOpen();
        if (!this.hasRemaining()) {
            return 1;
        }
        int n3 = Math.min(n2, this.remaining());
        for (int i = 0; i < n3; ++i) {
            cArray[n + i] = this.seq.charAt(this.pos++);
        }
        return n3;
    }

    @Override
    public synchronized long skip(long l) throws IOException {
        Preconditions.checkArgument(l >= 0L, "n (%s) may not be negative", l);
        this.checkOpen();
        int n = (int)Math.min((long)this.remaining(), l);
        this.pos += n;
        return n;
    }

    @Override
    public synchronized boolean ready() throws IOException {
        this.checkOpen();
        return false;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public synchronized void mark(int n) throws IOException {
        Preconditions.checkArgument(n >= 0, "readAheadLimit (%s) may not be negative", n);
        this.checkOpen();
        this.mark = this.pos;
    }

    @Override
    public synchronized void reset() throws IOException {
        this.checkOpen();
        this.pos = this.mark;
    }

    @Override
    public synchronized void close() throws IOException {
        this.seq = null;
    }
}

