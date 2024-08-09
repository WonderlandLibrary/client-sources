/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class UnixLineEndingInputStream
extends InputStream {
    private boolean slashNSeen = false;
    private boolean slashRSeen = false;
    private boolean eofSeen = false;
    private final InputStream target;
    private final boolean ensureLineFeedAtEndOfFile;

    public UnixLineEndingInputStream(InputStream inputStream, boolean bl) {
        this.target = inputStream;
        this.ensureLineFeedAtEndOfFile = bl;
    }

    private int readWithUpdate() throws IOException {
        int n = this.target.read();
        boolean bl = this.eofSeen = n == -1;
        if (this.eofSeen) {
            return n;
        }
        this.slashNSeen = n == 10;
        this.slashRSeen = n == 13;
        return n;
    }

    @Override
    public int read() throws IOException {
        boolean bl = this.slashRSeen;
        if (this.eofSeen) {
            return this.eofGame(bl);
        }
        int n = this.readWithUpdate();
        if (this.eofSeen) {
            return this.eofGame(bl);
        }
        if (this.slashRSeen) {
            return 1;
        }
        if (bl && this.slashNSeen) {
            return this.read();
        }
        return n;
    }

    private int eofGame(boolean bl) {
        if (bl || !this.ensureLineFeedAtEndOfFile) {
            return 1;
        }
        if (!this.slashNSeen) {
            this.slashNSeen = true;
            return 1;
        }
        return 1;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.target.close();
    }

    @Override
    public synchronized void mark(int n) {
        throw new UnsupportedOperationException("Mark notsupported");
    }
}

