/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ChunkedOutputStream
extends FilterOutputStream {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedOutputStream(OutputStream outputStream, int n) {
        super(outputStream);
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = n;
    }

    public ChunkedOutputStream(OutputStream outputStream) {
        this(outputStream, 4096);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        int n3 = n2;
        int n4 = n;
        while (n3 > 0) {
            int n5 = Math.min(n3, this.chunkSize);
            this.out.write(byArray, n4, n5);
            n3 -= n5;
            n4 += n5;
        }
    }
}

