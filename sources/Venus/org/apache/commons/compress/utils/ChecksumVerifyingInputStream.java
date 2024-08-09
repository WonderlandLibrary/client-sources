/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;

public class ChecksumVerifyingInputStream
extends InputStream {
    private final InputStream in;
    private long bytesRemaining;
    private final long expectedChecksum;
    private final Checksum checksum;

    public ChecksumVerifyingInputStream(Checksum checksum, InputStream inputStream, long l, long l2) {
        this.checksum = checksum;
        this.in = inputStream;
        this.expectedChecksum = l2;
        this.bytesRemaining = l;
    }

    public int read() throws IOException {
        if (this.bytesRemaining <= 0L) {
            return 1;
        }
        int n = this.in.read();
        if (n >= 0) {
            this.checksum.update(n);
            --this.bytesRemaining;
        }
        if (this.bytesRemaining == 0L && this.expectedChecksum != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        return n;
    }

    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.in.read(byArray, n, n2);
        if (n3 >= 0) {
            this.checksum.update(byArray, n, n3);
            this.bytesRemaining -= (long)n3;
        }
        if (this.bytesRemaining <= 0L && this.expectedChecksum != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        return n3;
    }

    public long skip(long l) throws IOException {
        if (this.read() >= 0) {
            return 1L;
        }
        return 0L;
    }

    public void close() throws IOException {
        this.in.close();
    }
}

