/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.IOException;
import java.io.InputStream;

public class BoundedInputStream
extends InputStream {
    private final InputStream in;
    private long bytesRemaining;

    public BoundedInputStream(InputStream inputStream, long l) {
        this.in = inputStream;
        this.bytesRemaining = l;
    }

    public int read() throws IOException {
        if (this.bytesRemaining > 0L) {
            --this.bytesRemaining;
            return this.in.read();
        }
        return 1;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (this.bytesRemaining == 0L) {
            return 1;
        }
        int n4 = n2;
        if ((long)n4 > this.bytesRemaining) {
            n4 = (int)this.bytesRemaining;
        }
        if ((n3 = this.in.read(byArray, n, n4)) >= 0) {
            this.bytesRemaining -= (long)n3;
        }
        return n3;
    }

    public void close() {
    }
}

