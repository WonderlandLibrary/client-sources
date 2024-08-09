/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream
extends FilterInputStream {
    private long bytesRead;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public int read() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            this.count(1L);
        }
        return n;
    }

    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.in.read(byArray, n, n2);
        if (n3 >= 0) {
            this.count(n3);
        }
        return n3;
    }

    protected final void count(long l) {
        if (l != -1L) {
            this.bytesRead += l;
        }
    }

    public long getBytesRead() {
        return this.bytesRead;
    }
}

