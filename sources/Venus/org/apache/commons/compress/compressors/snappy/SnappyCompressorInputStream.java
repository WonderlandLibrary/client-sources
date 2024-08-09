/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class SnappyCompressorInputStream
extends CompressorInputStream {
    private static final int TAG_MASK = 3;
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    private final byte[] decompressBuf;
    private int writeIndex;
    private int readIndex;
    private final int blockSize;
    private final InputStream in;
    private final int size;
    private int uncompressedBytesRemaining;
    private final byte[] oneByte = new byte[1];
    private boolean endReached = false;

    public SnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, 32768);
    }

    public SnappyCompressorInputStream(InputStream inputStream, int n) throws IOException {
        this.in = inputStream;
        this.blockSize = n;
        this.decompressBuf = new byte[n * 3];
        this.readIndex = 0;
        this.writeIndex = 0;
        this.uncompressedBytesRemaining = this.size = (int)this.readSize();
    }

    public int read() throws IOException {
        return this.read(this.oneByte, 0, 1) == -1 ? -1 : this.oneByte[0] & 0xFF;
    }

    public void close() throws IOException {
        this.in.close();
    }

    public int available() {
        return this.writeIndex - this.readIndex;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (this.endReached) {
            return 1;
        }
        int n3 = this.available();
        if (n2 > n3) {
            this.fill(n2 - n3);
        }
        int n4 = Math.min(n2, this.available());
        System.arraycopy(this.decompressBuf, this.readIndex, byArray, n, n4);
        this.readIndex += n4;
        if (this.readIndex > this.blockSize) {
            this.slideBuffer();
        }
        return n4;
    }

    private void fill(int n) throws IOException {
        if (this.uncompressedBytesRemaining == 0) {
            this.endReached = true;
        }
        int n2 = Math.min(n, this.uncompressedBytesRemaining);
        while (n2 > 0) {
            int n3 = this.readOneByte();
            int n4 = 0;
            long l = 0L;
            switch (n3 & 3) {
                case 0: {
                    n4 = this.readLiteralLength(n3);
                    if (!this.expandLiteral(n4)) break;
                    return;
                }
                case 1: {
                    n4 = 4 + (n3 >> 2 & 7);
                    l = (n3 & 0xE0) << 3;
                    if (!this.expandCopy(l |= (long)this.readOneByte(), n4)) break;
                    return;
                }
                case 2: {
                    n4 = (n3 >> 2) + 1;
                    l = this.readOneByte();
                    if (!this.expandCopy(l |= (long)(this.readOneByte() << 8), n4)) break;
                    return;
                }
                case 3: {
                    n4 = (n3 >> 2) + 1;
                    l = this.readOneByte();
                    l |= (long)(this.readOneByte() << 8);
                    l |= (long)(this.readOneByte() << 16);
                    if (!this.expandCopy(l |= (long)this.readOneByte() << 24, n4)) break;
                    return;
                }
            }
            n2 -= n4;
            this.uncompressedBytesRemaining -= n4;
        }
    }

    private void slideBuffer() {
        System.arraycopy(this.decompressBuf, this.blockSize, this.decompressBuf, 0, this.blockSize * 2);
        this.writeIndex -= this.blockSize;
        this.readIndex -= this.blockSize;
    }

    private int readLiteralLength(int n) throws IOException {
        int n2;
        switch (n >> 2) {
            case 60: {
                n2 = this.readOneByte();
                break;
            }
            case 61: {
                n2 = this.readOneByte();
                n2 |= this.readOneByte() << 8;
                break;
            }
            case 62: {
                n2 = this.readOneByte();
                n2 |= this.readOneByte() << 8;
                n2 |= this.readOneByte() << 16;
                break;
            }
            case 63: {
                n2 = this.readOneByte();
                n2 |= this.readOneByte() << 8;
                n2 |= this.readOneByte() << 16;
                n2 = (int)((long)n2 | (long)this.readOneByte() << 24);
                break;
            }
            default: {
                n2 = n >> 2;
            }
        }
        return n2 + 1;
    }

    private boolean expandLiteral(int n) throws IOException {
        int n2 = IOUtils.readFully(this.in, this.decompressBuf, this.writeIndex, n);
        this.count(n2);
        if (n != n2) {
            throw new IOException("Premature end of stream");
        }
        this.writeIndex += n;
        return this.writeIndex >= 2 * this.blockSize;
    }

    private boolean expandCopy(long l, int n) throws IOException {
        if (l > (long)this.blockSize) {
            throw new IOException("Offset is larger than block size");
        }
        int n2 = (int)l;
        if (n2 == 1) {
            byte by = this.decompressBuf[this.writeIndex - 1];
            for (int i = 0; i < n; ++i) {
                this.decompressBuf[this.writeIndex++] = by;
            }
        } else if (n < n2) {
            System.arraycopy(this.decompressBuf, this.writeIndex - n2, this.decompressBuf, this.writeIndex, n);
            this.writeIndex += n;
        } else {
            int n3 = n / n2;
            int n4 = n - n2 * n3;
            while (n3-- != 0) {
                System.arraycopy(this.decompressBuf, this.writeIndex - n2, this.decompressBuf, this.writeIndex, n2);
                this.writeIndex += n2;
            }
            if (n4 > 0) {
                System.arraycopy(this.decompressBuf, this.writeIndex - n2, this.decompressBuf, this.writeIndex, n4);
                this.writeIndex += n4;
            }
        }
        return this.writeIndex >= 2 * this.blockSize;
    }

    private int readOneByte() throws IOException {
        int n = this.in.read();
        if (n == -1) {
            throw new IOException("Premature end of stream");
        }
        this.count(1);
        return n & 0xFF;
    }

    private long readSize() throws IOException {
        int n = 0;
        long l = 0L;
        int n2 = 0;
        do {
            n2 = this.readOneByte();
            l |= (long)((n2 & 0x7F) << n++ * 7);
        } while (0 != (n2 & 0x80));
        return l;
    }

    public int getSize() {
        return this.size;
    }
}

