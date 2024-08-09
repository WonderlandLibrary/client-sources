/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.snappy.PureJavaCrc32C;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class FramedSnappyCompressorInputStream
extends CompressorInputStream {
    static final long MASK_OFFSET = 2726488792L;
    private static final int STREAM_IDENTIFIER_TYPE = 255;
    private static final int COMPRESSED_CHUNK_TYPE = 0;
    private static final int UNCOMPRESSED_CHUNK_TYPE = 1;
    private static final int PADDING_CHUNK_TYPE = 254;
    private static final int MIN_UNSKIPPABLE_TYPE = 2;
    private static final int MAX_UNSKIPPABLE_TYPE = 127;
    private static final int MAX_SKIPPABLE_TYPE = 253;
    private static final byte[] SZ_SIGNATURE = new byte[]{-1, 6, 0, 0, 115, 78, 97, 80, 112, 89};
    private final PushbackInputStream in;
    private SnappyCompressorInputStream currentCompressedChunk;
    private final byte[] oneByte = new byte[1];
    private boolean endReached;
    private boolean inUncompressedChunk;
    private int uncompressedBytesRemaining;
    private long expectedChecksum = -1L;
    private final PureJavaCrc32C checksum = new PureJavaCrc32C();

    public FramedSnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this.in = new PushbackInputStream(inputStream, 1);
        this.readStreamIdentifier();
    }

    public int read() throws IOException {
        return this.read(this.oneByte, 0, 1) == -1 ? -1 : this.oneByte[0] & 0xFF;
    }

    public void close() throws IOException {
        if (this.currentCompressedChunk != null) {
            this.currentCompressedChunk.close();
            this.currentCompressedChunk = null;
        }
        this.in.close();
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.readOnce(byArray, n, n2);
        if (n3 == -1) {
            this.readNextBlock();
            if (this.endReached) {
                return 1;
            }
            n3 = this.readOnce(byArray, n, n2);
        }
        return n3;
    }

    public int available() throws IOException {
        if (this.inUncompressedChunk) {
            return Math.min(this.uncompressedBytesRemaining, this.in.available());
        }
        if (this.currentCompressedChunk != null) {
            return this.currentCompressedChunk.available();
        }
        return 1;
    }

    private int readOnce(byte[] byArray, int n, int n2) throws IOException {
        int n3 = -1;
        if (this.inUncompressedChunk) {
            int n4 = Math.min(this.uncompressedBytesRemaining, n2);
            if (n4 == 0) {
                return 1;
            }
            n3 = this.in.read(byArray, n, n4);
            if (n3 != -1) {
                this.uncompressedBytesRemaining -= n3;
                this.count(n3);
            }
        } else if (this.currentCompressedChunk != null) {
            long l = this.currentCompressedChunk.getBytesRead();
            n3 = this.currentCompressedChunk.read(byArray, n, n2);
            if (n3 == -1) {
                this.currentCompressedChunk.close();
                this.currentCompressedChunk = null;
            } else {
                this.count(this.currentCompressedChunk.getBytesRead() - l);
            }
        }
        if (n3 > 0) {
            this.checksum.update(byArray, n, n3);
        }
        return n3;
    }

    private void readNextBlock() throws IOException {
        this.verifyLastChecksumAndReset();
        this.inUncompressedChunk = false;
        int n = this.readOneByte();
        if (n == -1) {
            this.endReached = true;
        } else if (n == 255) {
            this.in.unread(n);
            this.pushedBackBytes(1L);
            this.readStreamIdentifier();
            this.readNextBlock();
        } else if (n == 254 || n > 127 && n <= 253) {
            this.skipBlock();
            this.readNextBlock();
        } else {
            if (n >= 2 && n <= 127) {
                throw new IOException("unskippable chunk with type " + n + " (hex " + Integer.toHexString(n) + ")" + " detected.");
            }
            if (n == 1) {
                this.inUncompressedChunk = true;
                this.uncompressedBytesRemaining = this.readSize() - 4;
                this.expectedChecksum = FramedSnappyCompressorInputStream.unmask(this.readCrc());
            } else if (n == 0) {
                long l = this.readSize() - 4;
                this.expectedChecksum = FramedSnappyCompressorInputStream.unmask(this.readCrc());
                this.currentCompressedChunk = new SnappyCompressorInputStream(new BoundedInputStream(this.in, l));
                this.count(this.currentCompressedChunk.getBytesRead());
            } else {
                throw new IOException("unknown chunk type " + n + " detected.");
            }
        }
    }

    private long readCrc() throws IOException {
        byte[] byArray = new byte[4];
        int n = IOUtils.readFully(this.in, byArray);
        this.count(n);
        if (n != 4) {
            throw new IOException("premature end of stream");
        }
        long l = 0L;
        for (int i = 0; i < 4; ++i) {
            l |= ((long)byArray[i] & 0xFFL) << 8 * i;
        }
        return l;
    }

    static long unmask(long l) {
        l -= 2726488792L;
        return ((l &= 0xFFFFFFFFL) >> 17 | l << 15) & 0xFFFFFFFFL;
    }

    private int readSize() throws IOException {
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < 3; ++i) {
            n = this.readOneByte();
            if (n == -1) {
                throw new IOException("premature end of stream");
            }
            n2 |= n << i * 8;
        }
        return n2;
    }

    private void skipBlock() throws IOException {
        int n = this.readSize();
        long l = IOUtils.skip(this.in, n);
        this.count(l);
        if (l != (long)n) {
            throw new IOException("premature end of stream");
        }
    }

    private void readStreamIdentifier() throws IOException {
        byte[] byArray = new byte[10];
        int n = IOUtils.readFully(this.in, byArray);
        this.count(n);
        if (10 != n || !FramedSnappyCompressorInputStream.matches(byArray, 10)) {
            throw new IOException("Not a framed Snappy stream");
        }
    }

    private int readOneByte() throws IOException {
        int n = this.in.read();
        if (n != -1) {
            this.count(1);
            return n & 0xFF;
        }
        return 1;
    }

    private void verifyLastChecksumAndReset() throws IOException {
        if (this.expectedChecksum >= 0L && this.expectedChecksum != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        this.expectedChecksum = -1L;
        this.checksum.reset();
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < SZ_SIGNATURE.length) {
            return true;
        }
        byte[] byArray2 = byArray;
        if (byArray.length > SZ_SIGNATURE.length) {
            byArray2 = new byte[SZ_SIGNATURE.length];
            System.arraycopy(byArray, 0, byArray2, 0, SZ_SIGNATURE.length);
        }
        return Arrays.equals(byArray2, SZ_SIGNATURE);
    }
}

