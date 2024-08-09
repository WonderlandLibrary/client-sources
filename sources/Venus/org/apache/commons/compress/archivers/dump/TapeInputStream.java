/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.archivers.dump.DumpArchiveException;
import org.apache.commons.compress.archivers.dump.DumpArchiveUtil;
import org.apache.commons.compress.archivers.dump.ShortFileException;
import org.apache.commons.compress.archivers.dump.UnsupportedCompressionAlgorithmException;
import org.apache.commons.compress.utils.IOUtils;

class TapeInputStream
extends FilterInputStream {
    private byte[] blockBuffer = new byte[1024];
    private int currBlkIdx = -1;
    private int blockSize = 1024;
    private static final int recordSize = 1024;
    private int readOffset = 1024;
    private boolean isCompressed = false;
    private long bytesRead = 0L;

    public TapeInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public void resetBlockSize(int n, boolean bl) throws IOException {
        this.isCompressed = bl;
        this.blockSize = 1024 * n;
        byte[] byArray = this.blockBuffer;
        this.blockBuffer = new byte[this.blockSize];
        System.arraycopy(byArray, 0, this.blockBuffer, 0, 1024);
        this.readFully(this.blockBuffer, 1024, this.blockSize - 1024);
        this.currBlkIdx = 0;
        this.readOffset = 1024;
    }

    public int available() throws IOException {
        if (this.readOffset < this.blockSize) {
            return this.blockSize - this.readOffset;
        }
        return this.in.available();
    }

    public int read() throws IOException {
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (n2 % 1024 != 0) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        int n3 = 0;
        while (n3 < n2) {
            if (this.readOffset == this.blockSize && !this.readBlock(true)) {
                return 1;
            }
            int n4 = 0;
            n4 = this.readOffset + (n2 - n3) <= this.blockSize ? n2 - n3 : this.blockSize - this.readOffset;
            System.arraycopy(this.blockBuffer, this.readOffset, byArray, n, n4);
            this.readOffset += n4;
            n3 += n4;
            n += n4;
        }
        return n3;
    }

    public long skip(long l) throws IOException {
        long l2;
        long l3;
        if (l % 1024L != 0L) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        for (l2 = 0L; l2 < l; l2 += l3) {
            if (this.readOffset == this.blockSize && !this.readBlock(l - l2 < (long)this.blockSize)) {
                return -1L;
            }
            l3 = 0L;
            l3 = (long)this.readOffset + (l - l2) <= (long)this.blockSize ? l - l2 : (long)(this.blockSize - this.readOffset);
            this.readOffset = (int)((long)this.readOffset + l3);
        }
        return l2;
    }

    public void close() throws IOException {
        if (this.in != null && this.in != System.in) {
            this.in.close();
        }
    }

    public byte[] peek() throws IOException {
        if (this.readOffset == this.blockSize && !this.readBlock(true)) {
            return null;
        }
        byte[] byArray = new byte[1024];
        System.arraycopy(this.blockBuffer, this.readOffset, byArray, 0, byArray.length);
        return byArray;
    }

    public byte[] readRecord() throws IOException {
        byte[] byArray = new byte[1024];
        if (-1 == this.read(byArray, 0, byArray.length)) {
            throw new ShortFileException();
        }
        return byArray;
    }

    private boolean readBlock(boolean bl) throws IOException {
        boolean bl2 = true;
        if (this.in == null) {
            throw new IOException("input buffer is closed");
        }
        if (!this.isCompressed || this.currBlkIdx == -1) {
            bl2 = this.readFully(this.blockBuffer, 0, this.blockSize);
            this.bytesRead += (long)this.blockSize;
        } else {
            boolean bl3;
            if (!this.readFully(this.blockBuffer, 0, 4)) {
                return true;
            }
            this.bytesRead += 4L;
            int n = DumpArchiveUtil.convert32(this.blockBuffer, 0);
            boolean bl4 = bl3 = (n & 1) == 1;
            if (!bl3) {
                bl2 = this.readFully(this.blockBuffer, 0, this.blockSize);
                this.bytesRead += (long)this.blockSize;
            } else {
                int n2 = n >> 1 & 7;
                int n3 = n >> 4 & 0xFFFFFFF;
                byte[] byArray = new byte[n3];
                bl2 = this.readFully(byArray, 0, n3);
                this.bytesRead += (long)n3;
                if (!bl) {
                    Arrays.fill(this.blockBuffer, (byte)0);
                } else {
                    switch (1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.find(n2 & 3).ordinal()]) {
                        case 1: {
                            try {
                                Inflater inflater = new Inflater();
                                inflater.setInput(byArray, 0, byArray.length);
                                n3 = inflater.inflate(this.blockBuffer);
                                if (n3 != this.blockSize) {
                                    throw new ShortFileException();
                                }
                                inflater.end();
                                break;
                            } catch (DataFormatException dataFormatException) {
                                throw new DumpArchiveException("bad data", dataFormatException);
                            }
                        }
                        case 2: {
                            throw new UnsupportedCompressionAlgorithmException("BZLIB2");
                        }
                        case 3: {
                            throw new UnsupportedCompressionAlgorithmException("LZO");
                        }
                        default: {
                            throw new UnsupportedCompressionAlgorithmException();
                        }
                    }
                }
            }
        }
        ++this.currBlkIdx;
        this.readOffset = 0;
        return bl2;
    }

    private boolean readFully(byte[] byArray, int n, int n2) throws IOException {
        int n3 = IOUtils.readFully(this.in, byArray, n, n2);
        if (n3 < n2) {
            throw new ShortFileException();
        }
        return false;
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE = new int[DumpArchiveConstants.COMPRESSION_TYPE.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.ZLIB.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.BZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.LZO.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

