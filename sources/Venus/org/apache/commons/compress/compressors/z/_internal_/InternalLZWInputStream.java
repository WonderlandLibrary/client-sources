/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.z._internal_;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;

public abstract class InternalLZWInputStream
extends CompressorInputStream {
    private final byte[] oneByte = new byte[1];
    protected final InputStream in;
    protected int clearCode = -1;
    protected int codeSize = 9;
    protected int bitsCached = 0;
    protected int bitsCachedSize = 0;
    protected int previousCode = -1;
    protected int tableSize = 0;
    protected int[] prefixes;
    protected byte[] characters;
    private byte[] outputStack;
    private int outputStackLocation;

    protected InternalLZWInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void close() throws IOException {
        this.in.close();
    }

    public int read() throws IOException {
        int n = this.read(this.oneByte);
        if (n < 0) {
            return n;
        }
        return 0xFF & this.oneByte[0];
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.readFromStack(byArray, n, n2);
        while (n2 - n3 > 0) {
            int n4 = this.decompressNextSymbol();
            if (n4 < 0) {
                if (n3 > 0) {
                    this.count(n3);
                    return n3;
                }
                return n4;
            }
            n3 += this.readFromStack(byArray, n + n3, n2 - n3);
        }
        this.count(n3);
        return n3;
    }

    protected abstract int decompressNextSymbol() throws IOException;

    protected abstract int addEntry(int var1, byte var2) throws IOException;

    protected void setClearCode(int n) {
        this.clearCode = 1 << n - 1;
    }

    protected void initializeTables(int n) {
        int n2 = 1 << n;
        this.prefixes = new int[n2];
        this.characters = new byte[n2];
        this.outputStack = new byte[n2];
        this.outputStackLocation = n2;
        int n3 = 256;
        for (int i = 0; i < 256; ++i) {
            this.prefixes[i] = -1;
            this.characters[i] = (byte)i;
        }
    }

    protected int readNextCode() throws IOException {
        int n;
        while (this.bitsCachedSize < this.codeSize) {
            n = this.in.read();
            if (n < 0) {
                return n;
            }
            this.bitsCached |= n << this.bitsCachedSize;
            this.bitsCachedSize += 8;
        }
        n = (1 << this.codeSize) - 1;
        int n2 = this.bitsCached & n;
        this.bitsCached >>>= this.codeSize;
        this.bitsCachedSize -= this.codeSize;
        return n2;
    }

    protected int addEntry(int n, byte by, int n2) {
        if (this.tableSize < n2) {
            int n3 = this.tableSize;
            this.prefixes[this.tableSize] = n;
            this.characters[this.tableSize] = by;
            ++this.tableSize;
            return n3;
        }
        return 1;
    }

    protected int addRepeatOfPreviousCode() throws IOException {
        if (this.previousCode == -1) {
            throw new IOException("The first code can't be a reference to its preceding code");
        }
        byte by = 0;
        int n = this.previousCode;
        while (n >= 0) {
            by = this.characters[n];
            n = this.prefixes[n];
        }
        return this.addEntry(this.previousCode, by);
    }

    protected int expandCodeToOutputStack(int n, boolean bl) throws IOException {
        int n2 = n;
        while (n2 >= 0) {
            this.outputStack[--this.outputStackLocation] = this.characters[n2];
            n2 = this.prefixes[n2];
        }
        if (this.previousCode != -1 && !bl) {
            this.addEntry(this.previousCode, this.outputStack[this.outputStackLocation]);
        }
        this.previousCode = n;
        return this.outputStackLocation;
    }

    private int readFromStack(byte[] byArray, int n, int n2) {
        int n3 = this.outputStack.length - this.outputStackLocation;
        if (n3 > 0) {
            int n4 = Math.min(n3, n2);
            System.arraycopy(this.outputStack, this.outputStackLocation, byArray, n, n4);
            this.outputStackLocation += n4;
            return n4;
        }
        return 1;
    }
}

