/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;

public class BoundedReader
extends Reader {
    private static final int INVALID = -1;
    private final Reader target;
    private int charsRead = 0;
    private int markedAt = -1;
    private int readAheadLimit;
    private final int maxCharsFromTargetReader;

    public BoundedReader(Reader reader, int n) throws IOException {
        this.target = reader;
        this.maxCharsFromTargetReader = n;
    }

    @Override
    public void close() throws IOException {
        this.target.close();
    }

    @Override
    public void reset() throws IOException {
        this.charsRead = this.markedAt;
        this.target.reset();
    }

    @Override
    public void mark(int n) throws IOException {
        this.readAheadLimit = n - this.charsRead;
        this.markedAt = this.charsRead;
        this.target.mark(n);
    }

    @Override
    public int read() throws IOException {
        if (this.charsRead >= this.maxCharsFromTargetReader) {
            return 1;
        }
        if (this.markedAt >= 0 && this.charsRead - this.markedAt >= this.readAheadLimit) {
            return 1;
        }
        ++this.charsRead;
        return this.target.read();
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        for (int i = 0; i < n2; ++i) {
            int n3 = this.read();
            if (n3 == -1) {
                return i;
            }
            cArray[n + i] = (char)n3;
        }
        return n2;
    }
}

