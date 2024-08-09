/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;

class BitStream {
    private final InputStream in;
    private long bitCache;
    private int bitCacheSize;
    private static final int[] MASKS = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255};

    BitStream(InputStream inputStream) {
        this.in = inputStream;
    }

    private boolean fillCache() throws IOException {
        long l;
        boolean bl = false;
        while (this.bitCacheSize <= 56 && (l = (long)this.in.read()) != -1L) {
            bl = true;
            this.bitCache |= l << this.bitCacheSize;
            this.bitCacheSize += 8;
        }
        return bl;
    }

    int nextBit() throws IOException {
        if (this.bitCacheSize == 0 && !this.fillCache()) {
            return 1;
        }
        int n = (int)(this.bitCache & 1L);
        this.bitCache >>>= 1;
        --this.bitCacheSize;
        return n;
    }

    int nextBits(int n) throws IOException {
        if (this.bitCacheSize < n && !this.fillCache()) {
            return 1;
        }
        int n2 = (int)(this.bitCache & (long)MASKS[n]);
        this.bitCache >>>= n;
        this.bitCacheSize -= n;
        return n2;
    }

    int nextByte() throws IOException {
        return this.nextBits(8);
    }
}

