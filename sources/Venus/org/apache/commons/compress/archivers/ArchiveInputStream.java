/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;

public abstract class ArchiveInputStream
extends InputStream {
    private final byte[] SINGLE = new byte[1];
    private static final int BYTE_MASK = 255;
    private long bytesRead = 0L;

    public abstract ArchiveEntry getNextEntry() throws IOException;

    public int read() throws IOException {
        int n = this.read(this.SINGLE, 0, 1);
        return n == -1 ? -1 : this.SINGLE[0] & 0xFF;
    }

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

    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return false;
    }
}

