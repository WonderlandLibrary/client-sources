/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream
extends FilterOutputStream {
    private long bytesWritten = 0L;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void write(int n) throws IOException {
        this.out.write(n);
        this.count(1L);
    }

    public void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
        this.count(n2);
    }

    protected void count(long l) {
        if (l != -1L) {
            this.bytesWritten += l;
        }
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }
}

