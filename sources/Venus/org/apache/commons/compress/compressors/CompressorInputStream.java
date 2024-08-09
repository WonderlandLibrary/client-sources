/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors;

import java.io.InputStream;

public abstract class CompressorInputStream
extends InputStream {
    private long bytesRead = 0L;

    protected void count(int n) {
        this.count((long)n);
    }

    protected void count(long l) {
        if (l != -1L) {
            this.bytesRead += l;
        }
    }

    protected void pushedBackBytes(long l) {
        this.bytesRead -= l;
    }

    @Deprecated
    public int getCount() {
        return (int)this.bytesRead;
    }

    public long getBytesRead() {
        return this.bytesRead;
    }
}

