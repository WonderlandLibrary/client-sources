/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.z;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.z._internal_.InternalLZWInputStream;

public class ZCompressorInputStream
extends InternalLZWInputStream {
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead = 0L;

    public ZCompressorInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
        int n = this.in.read();
        int n2 = this.in.read();
        int n3 = this.in.read();
        if (n != 31 || n2 != 157 || n3 < 0) {
            throw new IOException("Input is not in .Z format");
        }
        this.blockMode = (n3 & 0x80) != 0;
        this.maxCodeSize = n3 & 0x1F;
        if (this.blockMode) {
            this.setClearCode(this.codeSize);
        }
        this.initializeTables(this.maxCodeSize);
        this.clearEntries();
    }

    private void clearEntries() {
        this.tableSize = 256;
        if (this.blockMode) {
            ++this.tableSize;
        }
    }

    protected int readNextCode() throws IOException {
        int n = super.readNextCode();
        if (n >= 0) {
            ++this.totalCodesRead;
        }
        return n;
    }

    private void reAlignReading() throws IOException {
        long l = 8L - this.totalCodesRead % 8L;
        if (l == 8L) {
            l = 0L;
        }
        for (long i = 0L; i < l; ++i) {
            this.readNextCode();
        }
        this.bitsCached = 0;
        this.bitsCachedSize = 0;
    }

    protected int addEntry(int n, byte by) throws IOException {
        int n2 = 1 << this.codeSize;
        int n3 = this.addEntry(n, by, n2);
        if (this.tableSize == n2 && this.codeSize < this.maxCodeSize) {
            this.reAlignReading();
            ++this.codeSize;
        }
        return n3;
    }

    protected int decompressNextSymbol() throws IOException {
        int n = this.readNextCode();
        if (n < 0) {
            return 1;
        }
        if (this.blockMode && n == this.clearCode) {
            this.clearEntries();
            this.reAlignReading();
            this.codeSize = 9;
            this.previousCode = -1;
            return 1;
        }
        boolean bl = false;
        if (n == this.tableSize) {
            this.addRepeatOfPreviousCode();
            bl = true;
        } else if (n > this.tableSize) {
            throw new IOException(String.format("Invalid %d bit code 0x%x", this.codeSize, n));
        }
        return this.expandCodeToOutputStack(n, bl);
    }

    public static boolean matches(byte[] byArray, int n) {
        return n > 3 && byArray[0] == 31 && byArray[1] == -99;
    }
}

