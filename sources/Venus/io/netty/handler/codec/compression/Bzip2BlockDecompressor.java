/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.compression.Bzip2BitReader;
import io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder;
import io.netty.handler.codec.compression.Bzip2MoveToFrontTable;
import io.netty.handler.codec.compression.Bzip2Rand;
import io.netty.handler.codec.compression.Crc32;
import io.netty.handler.codec.compression.DecompressionException;

final class Bzip2BlockDecompressor {
    private final Bzip2BitReader reader;
    private final Crc32 crc = new Crc32();
    private final int blockCRC;
    private final boolean blockRandomised;
    int huffmanEndOfBlockSymbol;
    int huffmanInUse16;
    final byte[] huffmanSymbolMap = new byte[256];
    private final int[] bwtByteCounts = new int[256];
    private final byte[] bwtBlock;
    private final int bwtStartPointer;
    private int[] bwtMergedPointers;
    private int bwtCurrentMergedPointer;
    private int bwtBlockLength;
    private int bwtBytesDecoded;
    private int rleLastDecodedByte = -1;
    private int rleAccumulator;
    private int rleRepeat;
    private int randomIndex;
    private int randomCount = Bzip2Rand.rNums(0) - 1;
    private final Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();
    private int repeatCount;
    private int repeatIncrement = 1;
    private int mtfValue;

    Bzip2BlockDecompressor(int n, int n2, boolean bl, int n3, Bzip2BitReader bzip2BitReader) {
        this.bwtBlock = new byte[n];
        this.blockCRC = n2;
        this.blockRandomised = bl;
        this.bwtStartPointer = n3;
        this.reader = bzip2BitReader;
    }

    boolean decodeHuffmanData(Bzip2HuffmanStageDecoder bzip2HuffmanStageDecoder) {
        Bzip2BitReader bzip2BitReader = this.reader;
        byte[] byArray = this.bwtBlock;
        byte[] byArray2 = this.huffmanSymbolMap;
        int n = this.bwtBlock.length;
        int n2 = this.huffmanEndOfBlockSymbol;
        int[] nArray = this.bwtByteCounts;
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = this.symbolMTF;
        int n3 = this.bwtBlockLength;
        int n4 = this.repeatCount;
        int n5 = this.repeatIncrement;
        int n6 = this.mtfValue;
        while (true) {
            byte by;
            if (!bzip2BitReader.hasReadableBits(0)) {
                this.bwtBlockLength = n3;
                this.repeatCount = n4;
                this.repeatIncrement = n5;
                this.mtfValue = n6;
                return true;
            }
            int n7 = bzip2HuffmanStageDecoder.nextSymbol();
            if (n7 == 0) {
                n4 += n5;
                n5 <<= 1;
                continue;
            }
            if (n7 == 1) {
                n4 += n5 << 1;
                n5 <<= 1;
                continue;
            }
            if (n4 > 0) {
                if (n3 + n4 > n) {
                    throw new DecompressionException("block exceeds declared block size");
                }
                by = byArray2[n6];
                int n8 = by & 0xFF;
                nArray[n8] = nArray[n8] + n4;
                while (--n4 >= 0) {
                    byArray[n3++] = by;
                }
                n4 = 0;
                n5 = 1;
            }
            if (n7 == n2) break;
            if (n3 >= n) {
                throw new DecompressionException("block exceeds declared block size");
            }
            n6 = bzip2MoveToFrontTable.indexToFront(n7 - 1) & 0xFF;
            by = byArray2[n6];
            int n9 = by & 0xFF;
            nArray[n9] = nArray[n9] + 1;
            byArray[n3++] = by;
        }
        this.bwtBlockLength = n3;
        this.initialiseInverseBWT();
        return false;
    }

    private void initialiseInverseBWT() {
        int n;
        int n2 = this.bwtStartPointer;
        byte[] byArray = this.bwtBlock;
        int[] nArray = new int[this.bwtBlockLength];
        int[] nArray2 = new int[256];
        if (n2 < 0 || n2 >= this.bwtBlockLength) {
            throw new DecompressionException("start pointer invalid");
        }
        System.arraycopy(this.bwtByteCounts, 0, nArray2, 1, 255);
        for (n = 2; n <= 255; ++n) {
            int n3 = n;
            nArray2[n3] = nArray2[n3] + nArray2[n - 1];
        }
        for (n = 0; n < this.bwtBlockLength; ++n) {
            int n4;
            int n5 = n4 = byArray[n] & 0xFF;
            int n6 = nArray2[n5];
            nArray2[n5] = n6 + 1;
            nArray[n6] = (n << 8) + n4;
        }
        this.bwtMergedPointers = nArray;
        this.bwtCurrentMergedPointer = nArray[n2];
    }

    public int read() {
        while (this.rleRepeat < 1) {
            if (this.bwtBytesDecoded == this.bwtBlockLength) {
                return 1;
            }
            int n = this.decodeNextBWTByte();
            if (n != this.rleLastDecodedByte) {
                this.rleLastDecodedByte = n;
                this.rleRepeat = 1;
                this.rleAccumulator = 1;
                this.crc.updateCRC(n);
                continue;
            }
            if (++this.rleAccumulator == 4) {
                int n2;
                this.rleRepeat = n2 = this.decodeNextBWTByte() + 1;
                this.rleAccumulator = 0;
                this.crc.updateCRC(n, n2);
                continue;
            }
            this.rleRepeat = 1;
            this.crc.updateCRC(n);
        }
        --this.rleRepeat;
        return this.rleLastDecodedByte;
    }

    private int decodeNextBWTByte() {
        int n = this.bwtCurrentMergedPointer;
        int n2 = n & 0xFF;
        this.bwtCurrentMergedPointer = this.bwtMergedPointers[n >>> 8];
        if (this.blockRandomised && --this.randomCount == 0) {
            n2 ^= 1;
            this.randomIndex = (this.randomIndex + 1) % 512;
            this.randomCount = Bzip2Rand.rNums(this.randomIndex);
        }
        ++this.bwtBytesDecoded;
        return n2;
    }

    public int blockLength() {
        return this.bwtBlockLength;
    }

    int checkCRC() {
        int n = this.crc.getCRC();
        if (this.blockCRC != n) {
            throw new DecompressionException("block CRC error");
        }
        return n;
    }
}

