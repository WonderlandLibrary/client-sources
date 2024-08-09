/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.compression.Bzip2BitWriter;
import io.netty.handler.codec.compression.Bzip2DivSufSort;
import io.netty.handler.codec.compression.Bzip2HuffmanStageEncoder;
import io.netty.handler.codec.compression.Bzip2MTFAndRLE2StageEncoder;
import io.netty.handler.codec.compression.Crc32;
import io.netty.util.ByteProcessor;

final class Bzip2BlockCompressor {
    private final ByteProcessor writeProcessor = new ByteProcessor(this){
        final Bzip2BlockCompressor this$0;
        {
            this.this$0 = bzip2BlockCompressor;
        }

        @Override
        public boolean process(byte by) throws Exception {
            return this.this$0.write(by);
        }
    };
    private final Bzip2BitWriter writer;
    private final Crc32 crc = new Crc32();
    private final byte[] block;
    private int blockLength;
    private final int blockLengthLimit;
    private final boolean[] blockValuesPresent = new boolean[256];
    private final int[] bwtBlock;
    private int rleCurrentValue = -1;
    private int rleLength;

    Bzip2BlockCompressor(Bzip2BitWriter bzip2BitWriter, int n) {
        this.writer = bzip2BitWriter;
        this.block = new byte[n + 1];
        this.bwtBlock = new int[n + 1];
        this.blockLengthLimit = n - 6;
    }

    private void writeSymbolMap(ByteBuf byteBuf) {
        int n;
        int n2;
        Bzip2BitWriter bzip2BitWriter = this.writer;
        boolean[] blArray = this.blockValuesPresent;
        boolean[] blArray2 = new boolean[16];
        for (int i = 0; i < blArray2.length; ++i) {
            n2 = 0;
            n = i << 4;
            while (n2 < 16) {
                if (blArray[n]) {
                    blArray2[i] = true;
                }
                ++n2;
                ++n;
            }
        }
        for (boolean bl : blArray2) {
            bzip2BitWriter.writeBoolean(byteBuf, bl);
        }
        for (int i = 0; i < blArray2.length; ++i) {
            if (!blArray2[i]) continue;
            n2 = 0;
            n = i << 4;
            while (n2 < 16) {
                bzip2BitWriter.writeBoolean(byteBuf, blArray[n]);
                ++n2;
                ++n;
            }
        }
    }

    private void writeRun(int n, int n2) {
        int n3 = this.blockLength;
        byte[] byArray = this.block;
        this.blockValuesPresent[n] = true;
        this.crc.updateCRC(n, n2);
        byte by = (byte)n;
        switch (n2) {
            case 1: {
                byArray[n3] = by;
                this.blockLength = n3 + 1;
                break;
            }
            case 2: {
                byArray[n3] = by;
                byArray[n3 + 1] = by;
                this.blockLength = n3 + 2;
                break;
            }
            case 3: {
                byArray[n3] = by;
                byArray[n3 + 1] = by;
                byArray[n3 + 2] = by;
                this.blockLength = n3 + 3;
                break;
            }
            default: {
                this.blockValuesPresent[n2 -= 4] = true;
                byArray[n3] = by;
                byArray[n3 + 1] = by;
                byArray[n3 + 2] = by;
                byArray[n3 + 3] = by;
                byArray[n3 + 4] = (byte)n2;
                this.blockLength = n3 + 5;
            }
        }
    }

    boolean write(int n) {
        if (this.blockLength > this.blockLengthLimit) {
            return true;
        }
        int n2 = this.rleCurrentValue;
        int n3 = this.rleLength;
        if (n3 == 0) {
            this.rleCurrentValue = n;
            this.rleLength = 1;
        } else if (n2 != n) {
            this.writeRun(n2 & 0xFF, n3);
            this.rleCurrentValue = n;
            this.rleLength = 1;
        } else if (n3 == 254) {
            this.writeRun(n2 & 0xFF, 255);
            this.rleLength = 0;
        } else {
            this.rleLength = n3 + 1;
        }
        return false;
    }

    int write(ByteBuf byteBuf, int n, int n2) {
        int n3 = byteBuf.forEachByte(n, n2, this.writeProcessor);
        return n3 == -1 ? n2 : n3 - n;
    }

    void close(ByteBuf byteBuf) {
        if (this.rleLength > 0) {
            this.writeRun(this.rleCurrentValue & 0xFF, this.rleLength);
        }
        this.block[this.blockLength] = this.block[0];
        Bzip2DivSufSort bzip2DivSufSort = new Bzip2DivSufSort(this.block, this.bwtBlock, this.blockLength);
        int n = bzip2DivSufSort.bwt();
        Bzip2BitWriter bzip2BitWriter = this.writer;
        bzip2BitWriter.writeBits(byteBuf, 24, 3227993L);
        bzip2BitWriter.writeBits(byteBuf, 24, 2511705L);
        bzip2BitWriter.writeInt(byteBuf, this.crc.getCRC());
        bzip2BitWriter.writeBoolean(byteBuf, true);
        bzip2BitWriter.writeBits(byteBuf, 24, n);
        this.writeSymbolMap(byteBuf);
        Bzip2MTFAndRLE2StageEncoder bzip2MTFAndRLE2StageEncoder = new Bzip2MTFAndRLE2StageEncoder(this.bwtBlock, this.blockLength, this.blockValuesPresent);
        bzip2MTFAndRLE2StageEncoder.encode();
        Bzip2HuffmanStageEncoder bzip2HuffmanStageEncoder = new Bzip2HuffmanStageEncoder(bzip2BitWriter, bzip2MTFAndRLE2StageEncoder.mtfBlock(), bzip2MTFAndRLE2StageEncoder.mtfLength(), bzip2MTFAndRLE2StageEncoder.mtfAlphabetSize(), bzip2MTFAndRLE2StageEncoder.mtfSymbolFrequencies());
        bzip2HuffmanStageEncoder.encode(byteBuf);
    }

    int availableSize() {
        if (this.blockLength == 0) {
            return this.blockLengthLimit + 2;
        }
        return this.blockLengthLimit - this.blockLength + 1;
    }

    boolean isFull() {
        return this.blockLength > this.blockLengthLimit;
    }

    boolean isEmpty() {
        return this.blockLength == 0 && this.rleLength == 0;
    }

    int crc() {
        return this.crc.getCRC();
    }
}

