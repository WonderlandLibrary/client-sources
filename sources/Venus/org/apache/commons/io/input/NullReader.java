/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class NullReader
extends Reader {
    private final long size;
    private long position;
    private long mark = -1L;
    private long readlimit;
    private boolean eof;
    private final boolean throwEofException;
    private final boolean markSupported;

    public NullReader(long l) {
        this(l, true, false);
    }

    public NullReader(long l, boolean bl, boolean bl2) {
        this.size = l;
        this.markSupported = bl;
        this.throwEofException = bl2;
    }

    public long getPosition() {
        return this.position;
    }

    public long getSize() {
        return this.size;
    }

    @Override
    public void close() throws IOException {
        this.eof = false;
        this.position = 0L;
        this.mark = -1L;
    }

    @Override
    public synchronized void mark(int n) {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        this.mark = this.position;
        this.readlimit = n;
    }

    @Override
    public boolean markSupported() {
        return this.markSupported;
    }

    @Override
    public int read() throws IOException {
        if (this.eof) {
            throw new IOException("Read after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        ++this.position;
        return this.processChar();
    }

    @Override
    public int read(char[] cArray) throws IOException {
        return this.read(cArray, 0, cArray.length);
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        if (this.eof) {
            throw new IOException("Read after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        this.position += (long)n2;
        int n3 = n2;
        if (this.position > this.size) {
            n3 = n2 - (int)(this.position - this.size);
            this.position = this.size;
        }
        this.processChars(cArray, n, n3);
        return n3;
    }

    @Override
    public synchronized void reset() throws IOException {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        if (this.mark < 0L) {
            throw new IOException("No position has been marked");
        }
        if (this.position > this.mark + this.readlimit) {
            throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
        }
        this.position = this.mark;
        this.eof = false;
    }

    @Override
    public long skip(long l) throws IOException {
        if (this.eof) {
            throw new IOException("Skip after end of file");
        }
        if (this.position == this.size) {
            return this.doEndOfFile();
        }
        this.position += l;
        long l2 = l;
        if (this.position > this.size) {
            l2 = l - (this.position - this.size);
            this.position = this.size;
        }
        return l2;
    }

    protected int processChar() {
        return 1;
    }

    protected void processChars(char[] cArray, int n, int n2) {
    }

    private int doEndOfFile() throws EOFException {
        this.eof = true;
        if (this.throwEofException) {
            throw new EOFException();
        }
        return 1;
    }
}

