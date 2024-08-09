/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class ChunkedWriter
extends FilterWriter {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedWriter(Writer writer, int n) {
        super(writer);
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = n;
    }

    public ChunkedWriter(Writer writer) {
        this(writer, 4096);
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        int n3 = n2;
        int n4 = n;
        while (n3 > 0) {
            int n5 = Math.min(n3, this.chunkSize);
            this.out.write(cArray, n4, n5);
            n3 -= n5;
            n4 += n5;
        }
    }
}

