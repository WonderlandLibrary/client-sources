/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class WindowsLineEndingInputStream
extends InputStream {
    private boolean slashRSeen = false;
    private boolean slashNSeen = false;
    private boolean injectSlashN = false;
    private boolean eofSeen = false;
    private final InputStream target;
    private final boolean ensureLineFeedAtEndOfFile;

    public WindowsLineEndingInputStream(InputStream inputStream, boolean bl) {
        this.target = inputStream;
        this.ensureLineFeedAtEndOfFile = bl;
    }

    private int readWithUpdate() throws IOException {
        int n = this.target.read();
        boolean bl = this.eofSeen = n == -1;
        if (this.eofSeen) {
            return n;
        }
        this.slashRSeen = n == 13;
        this.slashNSeen = n == 10;
        return n;
    }

    @Override
    public int read() throws IOException {
        if (this.eofSeen) {
            return this.eofGame();
        }
        if (this.injectSlashN) {
            this.injectSlashN = false;
            return 1;
        }
        boolean bl = this.slashRSeen;
        int n = this.readWithUpdate();
        if (this.eofSeen) {
            return this.eofGame();
        }
        if (n == 10 && !bl) {
            this.injectSlashN = true;
            return 0;
        }
        return n;
    }

    private int eofGame() {
        if (!this.ensureLineFeedAtEndOfFile) {
            return 1;
        }
        if (!this.slashNSeen && !this.slashRSeen) {
            this.slashRSeen = true;
            return 0;
        }
        if (!this.slashNSeen) {
            this.slashRSeen = false;
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
        throw new UnsupportedOperationException("Mark not supported");
    }
}

